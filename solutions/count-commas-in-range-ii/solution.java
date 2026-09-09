// Count Commas in Range II
// https://leetcode.com/problems/count-commas-in-range-ii/

class Solution {
    public long countCommas(long n) {
        long ans= 0;
        for(long start=1000,comma=1;start<=n;start*=1000,comma++)
        {
            long end=Math.min(n,start*1000-1);
            ans+=(end-start+1)*comma;
        }
        return ans;
    }
}