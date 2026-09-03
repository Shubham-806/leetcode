// Construct Uniform Parity Array II
// https://leetcode.com/problems/construct-uniform-parity-array-ii/

class Solution {
    public boolean uniformArray(int[] nums1) {
        int mineven = Integer.MAX_VALUE;
        int minodd = Integer.MAX_VALUE;
        for (int n : nums1) {
            if (n % 2 == 0)
                mineven = Math.min(mineven, n);
            else
                minodd = Math.min(minodd, n);
        }
        if(minodd==Integer.MAX_VALUE)
        return true;
        
        return minodd<mineven;
    }
}