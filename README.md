# LeetCode Cloud Sync

Automatically syncs your accepted LeetCode submissions to this repo, running
entirely in GitHub Actions — no local machine or browser extension required.

## How it works

Every 10 minutes, a GitHub Action:
1. Fetches your recent accepted submissions from LeetCode.
2. Skips anything already synced (tracked in `data/synced.json`).
3. Downloads the code for new submissions and writes it to `solutions/<problem-slug>/solution.<ext>`.
4. Commits and pushes — but only if there's something new (no empty commits).

## Setup

### 1. Get your LeetCode cookies

1. Log into [leetcode.com](https://leetcode.com).
2. Open DevTools (right-click → Inspect) → **Network** tab.
3. Refresh the page, click any request to `leetcode.com`.
4. Under **Request Headers**, find the `Cookie` field and copy the values of:
   - `LEETCODE_SESSION`
   - `csrftoken`

### 2. Add repo secrets

Go to **Settings → Secrets and variables → Actions** in this repo and add:

| Secret name            | Value                          |
|-------------------------|---------------------------------|
| `LEETCODE_SESSION`      | value of the `LEETCODE_SESSION` cookie |
| `LEETCODE_CSRF_TOKEN`   | value of the `csrftoken` cookie |
| `LEETCODE_USERNAME`     | your LeetCode username          |

### 3. Enable workflow write permissions

**Settings → Actions → General → Workflow permissions** → select
**"Read and write permissions"**. Without this the Action can't push commits.

### 4. Push this repo to GitHub

```bash
git init
git add .
git commit -m "Initial commit: LeetCode cloud sync"
git branch -M main
git remote add origin https://github.com/<your-username>/<your-repo>.git
git push -u origin main
```

That's it. From here, just solve problems on LeetCode as normal — within 10
minutes, any new accepted solution shows up as a commit here automatically.

## Notes

- **`LEETCODE_SESSION` expires periodically** (typically every few weeks).
  If commits stop appearing, refresh the cookie and update the secret.
- Cron schedule is every 10 minutes by default — edit the `cron:` line in
  `.github/workflows/sync.yml` to change that (GitHub doesn't reliably
  support intervals shorter than ~5 minutes).
- You can also trigger a sync manually anytime from the **Actions** tab
  via "Run workflow".
