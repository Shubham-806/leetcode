// Count Good Nodes in Binary Tree
// https://leetcode.com/problems/count-good-nodes-in-binary-tree/

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
    public int goodNodes(TreeNode root) {
        return dfs(root,root.val);
    }
    private int dfs(TreeNode root,int max)
    {
        if(root==null)
        return 0;
        int c=0;
        if(root.val>=max){
            c=1;
            max=root.val;
        }
        c+=dfs(root.left,max);
        c+=dfs(root.right,max);
        return c;
    }
}