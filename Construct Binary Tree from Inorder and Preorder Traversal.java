二叉树问题，常用分治法 Divide and Conquer
因为 Preorder 的第一个元素就是Tree的 root，
而 Inorder 中root左边的节点为左子树，右边的节点为右字树。
依此为思路进行分治。

/*
Given preorder and inorder traversal of a tree, construct the binary tree.

Note
You may assume that duplicates do not exist in the tree.

Example
Given inorder [1,2,3] and preorder [2,1,3]

return a tree

  2

 /  \

1    3

Tags Expand 
Binary Tree

Thinking process:
See 'Construct tree from inorder + postorder' as example.
This problem uses divide and conquer idea as well.
For preorder: the front node is the root of the tree.
For inorder: find the root in the middle of the array, then the left-side is left-tree, and the right-side is the right-tree.
*/
 
/**
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int val;
 *     public TreeNode left, right;
 *     public TreeNode(int val) {
 *         this.val = val;
 *         this.left = this.right = null;
 *     }
 * }
 */
 
public class Solution {
    /**
     *@param preorder : A list of integers that preorder traversal of a tree
     *@param inorder : A list of integers that inorder traversal of a tree
     *@return : Root of a tree
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder.length != inorder.length) {
            return null;
        }
        
        return helper(inorder, 0, inorder.length - 1,
                      preorder, 0, preorder.length - 1);
    }
    
    private TreeNode helper(int[] inorder, int inStart, int inEnd, 
                            int[] preorder, int preStart, int preEnd) {
        if (inStart > inEnd) {
            return null;
        }               
        
        TreeNode root = new TreeNode(preorder[preStart]);
        int position = findPosition(inorder, inStart, inEnd, preorder[preStart]);
        
        root.left = helper(inorder, inStart, position - 1, 
                           preorder, preStart + 1, preStart + position - inStart);
        root.right = helper(inorder, position + 1, inEnd,
                            preorder, position - inEnd + preEnd + 1, preEnd);
        
        return root;
    }
    
    private int findPosition (int[] arr, int start, int end, int key) {
        for (int i = start; i <= end; i++) {
            if (arr[i] == key) {
                return i;
            }
        }
        
        return -1;
    }
}