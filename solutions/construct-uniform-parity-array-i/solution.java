// Construct Uniform Parity Array I
// https://leetcode.com/problems/construct-uniform-parity-array-i/

class Solution {
    public boolean uniformArray(int[] nums1) {
        int n=nums1.length;
        int nums2[]=new int[n];
        
        int j=0;
        for(int i=0;i<n;i++)
        {

            if(j!=i)
            {
                nums2[i]=nums1[i]-nums1[j];
            }
            else{
                nums2[i]=nums1[i];

            }
        }
        return true;
    }
}