// Binary Tree Zigzag Level Order Traversal
// https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/

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
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ans=new ArrayList<>();
        Queue<TreeNode>q=new LinkedList<>();
        q.offer(root);
        if(root==null)
        return ans;
        boolean l_to_r=true;
        while(!q.isEmpty())
        {
            int s=q.size();
            List<Integer>level=new ArrayList<>();
            for(int i=0;i<s;i++)
            {
                TreeNode curr=q.poll();
                level.add(curr.val);
                if(curr.left!=null)
                q.offer(curr.left);
                if(curr.right!=null)
                q.offer(curr.right);
                
            }
            if(!l_to_r)
            Collections.reverse(level);
            ans.add(level);
            l_to_r=!l_to_r;
            

                    }
                    return ans;

    }

}