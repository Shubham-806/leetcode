// Removing Minimum and Maximum From Array
// https://leetcode.com/problems/removing-minimum-and-maximum-from-array/

class Solution {
    public int minimumDeletions(int[] nums) {
        int max=Integer.MIN_VALUE;int min=Integer.MAX_VALUE;
        int maxin=0;int minin=0;
        int n=nums.length;
        if(n<=2)return n;
        for(int i=0;i<n;i++)
        {
           if(nums[i]>nums[maxin])
           {
           maxin=i;
           }
           if(nums[i]<nums[minin])
           {
            minin=i;
           }
        }
        int l=Math.min(minin,maxin);
        int r=Math.max(minin,maxin);
        int bothfront=r+1;
        int bothback=n-l;
        int onefoneb=(l+1)+(n-r);
        int ans=Math.min(bothfront,Math.min(bothback,onefoneb));
        return ans;
    }
}