// Symmetric Tree
// https://leetcode.com/problems/symmetric-tree/

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
    public boolean isSymmetric(TreeNode root) {
     if (root == null)
            return true;

        return mirror(root.left, root.right);
    }

    private boolean mirror(TreeNode left, TreeNode right) {

        // Both nodes are null
        if (left == null && right == null)
            return true;

        // One node is null
        if (left == null || right == null)
            return false;

        // Values are different
        if (left.val != right.val)
            return false;

        // Compare mirror children
        boolean outer = mirror(left.left, right.right);
        boolean inner = mirror(left.right, right.left);

        return outer && inner;   
    }
}