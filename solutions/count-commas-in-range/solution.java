// Count Commas in Range
// https://leetcode.com/problems/count-commas-in-range/

class Solution {
    public int countCommas(int n) {
        int c=0;
        for(int i=1;i<=n;i++)
        {
            if(i>=1000 && i<=100000)
            c++;
            
        }
        return c;
    }
}