// Distinct Subsequences II
// https://leetcode.com/problems/distinct-subsequences-ii/

class Solution {
    public int distinctSubseqII(String s) {
        long dp=0;
        int mod=1_000_000_007;
        long end[]=new long[26];
        for(char c:s.toCharArray())
        {
            int index=c-'a';
            long newsub=(dp+1)%mod;
            dp=(dp+newsub-end[index]+mod)%mod;
            end[index]=newsub;
        }
        return (int)dp;
    }
}