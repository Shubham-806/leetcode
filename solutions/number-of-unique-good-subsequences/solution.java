// Number of Unique Good Subsequences
// https://leetcode.com/problems/number-of-unique-good-subsequences/

class Solution {
    public int numberOfUniqueGoodSubsequences(String binary) {
        long mod=1_000_000_007;
        long end0=0; 
        long end1=0;
        boolean has0=false;
        for(char c:binary.toCharArray())
        {
            if(c=='0')
            {
                end0=(end0+end1)%mod;
                has0=true;
            }
            else
            {
                end1=(end0+end1+1)%mod;
            }
        }
        long ans=(end0+end1+(has0?1:0))%mod;
        return (int)ans;
    }
}