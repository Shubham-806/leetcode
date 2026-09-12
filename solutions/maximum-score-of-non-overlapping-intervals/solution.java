// Maximum Score of Non-overlapping Intervals
// https://leetcode.com/problems/maximum-score-of-non-overlapping-intervals/

import java.util.*;

class Solution {

    static class Interval {
        int l, r, w, idx;

        Interval(int l, int r, int w, int idx) {
            this.l = l;
            this.r = r;
            this.w = w;
            this.idx = idx;
        }
    }

    static class State {
        long score;
        List<Integer> indices;

        State(long score, List<Integer> indices) {
            this.score = score;
            this.indices = indices;
        }
    }

    Interval[] arr;
    State[][] dp;
    boolean[][] seen;
    int n;

    public int[] maximumWeight(List<List<Integer>> intervals) {

        n = intervals.size();
        arr = new Interval[n];

        for (int i = 0; i < n; i++) {
            arr[i] = new Interval(
                intervals.get(i).get(0),
                intervals.get(i).get(1),
                intervals.get(i).get(2),
                i
            );
        }

        Arrays.sort(arr, (a, b) -> {
            if (a.l != b.l)
                return Integer.compare(a.l, b.l);
            return Integer.compare(a.r, b.r);
        });

        dp = new State[n + 1][5];
        seen = new boolean[n + 1][5];

        State ans = solve(0, 4);

        // IMPORTANT:
        // Answer must be in increasing index order.
        Collections.sort(ans.indices);

        int[] result = new int[ans.indices.size()];

        for (int i = 0; i < ans.indices.size(); i++) {
            result[i] = ans.indices.get(i);
        }

        return result;
    }

    private State solve(int pos, int remaining) {

        if (pos == n || remaining == 0) {
            return new State(0, new ArrayList<>());
        }

        if (seen[pos][remaining]) {
            return dp[pos][remaining];
        }

        seen[pos][remaining] = true;

        // Skip
        State skip = solve(pos + 1, remaining);

        // Take
        int next = findNext(pos);

        State nextState = solve(next, remaining - 1);

        List<Integer> takeList = new ArrayList<>();
        takeList.add(arr[pos].idx);
        takeList.addAll(nextState.indices);

        State take = new State(
            arr[pos].w + nextState.score,
            takeList
        );

        dp[pos][remaining] = better(take, skip);

        return dp[pos][remaining];
    }

    private int findNext(int pos) {

        int target = arr[pos].r;

        int lo = pos + 1;
        int hi = n;

        while (lo < hi) {

            int mid = lo + (hi - lo) / 2;

            // Strictly greater because touching endpoints overlap
            if (arr[mid].l > target) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }

        return lo;
    }

    private State better(State a, State b) {

        // Maximum score first
        if (a.score != b.score) {
            return a.score > b.score ? a : b;
        }

        // Compare sorted index lists
        List<Integer> x = new ArrayList<>(a.indices);
        List<Integer> y = new ArrayList<>(b.indices);

        Collections.sort(x);
        Collections.sort(y);

        int len = Math.min(x.size(), y.size());

        for (int i = 0; i < len; i++) {

            if (!x.get(i).equals(y.get(i))) {
                return x.get(i) < y.get(i) ? a : b;
            }
        }

        // If identical prefix, shorter is lexicographically smaller
        return x.size() <= y.size() ? a : b;
    }
}