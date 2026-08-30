#!/usr/bin/env python3
"""
LeetCode -> GitHub cloud sync.

Runs on a schedule inside a GitHub Action. Each run:
  1. Fetches your recent accepted submissions from LeetCode (public endpoint).
  2. Skips anything already synced (tracked in data/synced.json).
  3. For new ones, fetches the actual code (needs your LeetCode session cookie).
  4. Writes a file per problem and updates the manifest.

The GitHub Action wrapping this script handles the git commit + push.
"""

import json
import os
import re
import sys
import time
from pathlib import Path

import requests

LEETCODE_GRAPHQL_URL = "https://leetcode.com/graphql"
REPO_ROOT = Path(__file__).resolve().parent.parent
MANIFEST_PATH = REPO_ROOT / "data" / "synced.json"
SOLUTIONS_DIR = REPO_ROOT / "solutions"

LANG_EXTENSIONS = {
    "python3": "py",
    "python": "py",
    "java": "java",
    "cpp": "cpp",
    "c": "c",
    "csharp": "cs",
    "javascript": "js",
    "typescript": "ts",
    "golang": "go",
    "kotlin": "kt",
    "swift": "swift",
    "rust": "rs",
    "ruby": "rb",
    "scala": "scala",
    "php": "php",
}


def load_manifest() -> dict:
    if MANIFEST_PATH.exists():
        return json.loads(MANIFEST_PATH.read_text())
    return {"synced_ids": []}


def save_manifest(manifest: dict) -> None:
    MANIFEST_PATH.parent.mkdir(parents=True, exist_ok=True)
    MANIFEST_PATH.write_text(json.dumps(manifest, indent=2, sort_keys=True))


def get_session():
    """Build a requests session authenticated with LeetCode cookies."""
    leetcode_session = os.environ.get("LEETCODE_SESSION")
    csrf_token = os.environ.get("LEETCODE_CSRF_TOKEN")
    username = os.environ.get("LEETCODE_USERNAME")

    if not leetcode_session or not csrf_token or not username:
        print(
            "Missing one of LEETCODE_SESSION / LEETCODE_CSRF_TOKEN / LEETCODE_USERNAME "
            "environment variables.",
            file=sys.stderr,
        )
        sys.exit(1)

    session = requests.Session()
    session.cookies.set("LEETCODE_SESSION", leetcode_session, domain="leetcode.com")
    session.cookies.set("csrftoken", csrf_token, domain="leetcode.com")
    session.headers.update(
        {
            "Content-Type": "application/json",
            "Referer": "https://leetcode.com",
            "x-csrftoken": csrf_token,
            "User-Agent": "Mozilla/5.0 (leetcode-cloud-sync bot)",
        }
    )
    return session, username


def fetch_recent_accepted(session: requests.Session, username: str, limit: int = 20):
    query = """
    query recentAcSubmissions($username: String!, $limit: Int!) {
      recentAcSubmissionList(username: $username, limit: $limit) {
        id
        title
        titleSlug
        timestamp
        lang
      }
    }
    """
    resp = session.post(
        LEETCODE_GRAPHQL_URL,
        json={"query": query, "variables": {"username": username, "limit": limit}},
        timeout=30,
    )
    resp.raise_for_status()
    data = resp.json()
    submissions = data.get("data", {}).get("recentAcSubmissionList") or []
    return submissions


def fetch_submission_code(session: requests.Session, submission_id: str):
    query = """
    query submissionDetails($submissionId: Int!) {
      submissionDetails(submissionId: $submissionId) {
        code
        lang {
          name
        }
      }
    }
    """
    resp = session.post(
        LEETCODE_GRAPHQL_URL,
        json={"query": query, "variables": {"submissionId": int(submission_id)}},
        timeout=30,
    )
    resp.raise_for_status()
    data = resp.json()
    details = data.get("data", {}).get("submissionDetails")
    if not details:
        return None
    return details.get("code")


def safe_slug(slug: str) -> str:
    return re.sub(r"[^a-zA-Z0-9_-]", "-", slug)


def sync():
    session, username = get_session()
    manifest = load_manifest()
    synced_ids = set(manifest.get("synced_ids", []))

    submissions = fetch_recent_accepted(session, username)
    new_count = 0

    for sub in submissions:
        sub_id = sub["id"]
        if sub_id in synced_ids:
            continue

        code = fetch_submission_code(session, sub_id)
        if not code:
            print(f"Could not fetch code for submission {sub_id}, skipping.")
            continue

        ext = LANG_EXTENSIONS.get(sub["lang"], "txt")
        problem_dir = SOLUTIONS_DIR / safe_slug(sub["titleSlug"])
        problem_dir.mkdir(parents=True, exist_ok=True)
        out_file = problem_dir / f"solution.{ext}"

        header = f"// {sub['title']}\n// https://leetcode.com/problems/{sub['titleSlug']}/\n\n"
        if ext == "py":
            header = f"# {sub['title']}\n# https://leetcode.com/problems/{sub['titleSlug']}/\n\n"

        out_file.write_text(header + code)

        synced_ids.add(sub_id)
        new_count += 1
        print(f"Synced: {sub['title']} ({sub['lang']})")

        time.sleep(1)  # be polite to LeetCode's API

    manifest["synced_ids"] = sorted(synced_ids)
    save_manifest(manifest)

    print(f"Done. {new_count} new solution(s) synced.")
    # Exit code 10 signals "new content" to the workflow so it knows to commit.
    sys.exit(10 if new_count > 0 else 0)


if __name__ == "__main__":
    sync()
