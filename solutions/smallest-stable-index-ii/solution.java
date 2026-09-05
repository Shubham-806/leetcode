// Smallest Stable Index II
// https://leetcode.com/problems/smallest-stable-index-ii/

class Solution {
    public int firstStableIndex(int[] nums, int k) {
        int n=nums.length;
        int prefixmax[]=new int[n];
        int suffixmin[]=new int[n];
        prefixmax[0]=nums[0];
        for(int i=1;i<n;i++)
        {
            prefixmax[i]=Math.max(prefixmax[i-1],nums[i]);
        }
        suffixmin[n-1]=nums[n-1];
        for(int i=n-2;i>=0;i--)
        {
            suffixmin[i]=Math.min(suffixmin[i+1],nums[i]);
        }
        for(int i=0;i<n;i++)
        {
            if(prefixmax[i]-suffixmin[i]<=k)
            return i; 
        }
    return -1;  
    }
}
// int n=nums.length;
        
    //     for(int i=0;i<n;i++)
    //     {
    //         int max=nums[0];
    //     int min=nums[i];
    //         for(int j=0;j<=i;j++)
    //         {
    //             max=Math.max(max,nums[j]);
    //         }
    //         for(int j=i;j<=n-1;j++)
    //         {
    //             min=Math.min(min,nums[j]);
    //         }
    //     int diff=max-min;
    //     if(diff<=k)
    //     return i;
    //     }
    // return -1;  