// Number of Substrings Containing All Three Characters
// https://leetcode.com/problems/number-of-substrings-containing-all-three-characters/

class Solution {
    public int numberOfSubstrings(String s) {
        int count[]=new int[3];
        int left=0;
        int ans=0;
        for(int right=0;right<s.length();right++)
        {
            count[s.charAt(right)-'a']++;
             while (count[0] > 0 &&
                   count[1] > 0 &&
                   count[2] > 0) {
                    count[s.charAt(left)-'a']--;
                    left++;
                   }
                   ans+=left;
        }
        return ans;
    }
}