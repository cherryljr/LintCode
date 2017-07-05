//  属于简单的问题，可以使用遍历该树，然后得出最大值。
//	也可以使用分治法来解决该问题

/*

Description:
Find the maximum node in a binary tree, return the node.

Example:
Given a binary tree:
     1
   /   \
 -5     2
 / \   /  \
0   3 -4  -5 
return the node with value 3.

Tags 
Binary Tree
*/

public class Solution {
    /**
     * @param root the root of binary tree
     * @return the max ndoe
     */
    public TreeNode maxNode(TreeNode root) {
        // Write your code here
        if (root == null)
            return root;

        TreeNode left = maxNode(root.left);
        TreeNode right = maxNode(root.right);
        return max(root, max(left, right));
    }

    TreeNode max(TreeNode a, TreeNode b) {
        if (a == null)
            return b;
        if (b == null)
            return a;
        if (a.val > b.val) {
            return a;
        }
        return b;
    }
}