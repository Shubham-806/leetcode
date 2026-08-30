// Binary Tree Maximum Path Sum
// https://leetcode.com/problems/binary-tree-maximum-path-sum/

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    int maxSum = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        dfs(root);

        return maxSum;
    }
    private int dfs(TreeNode root){

        if(root == null)
        return 0;
        int l=dfs(root.left);
        int maxl=Math.max(0,l);
        int r=dfs(root.right);
        int maxr=Math.max(0,r);
        maxSum = Math.max(maxSum,maxl + maxr + root.val);
        return root.val + Math.max(maxl,maxr);
    }
}