可以用递归解决。
因为Array是排序好的，所以要将其转换为BST可以将Array的中点取出来作为root。
而中点左边的点可以构成root的左子树，右边的点可以构成root的右子树。
以此递归下去
时间复杂度为：O(N)

/*
Given a sorted (increasing order) array, Convert it to create a binary tree with minimal height.

Have you met this question in a real interview? Yes
Example
Given [1,2,3,4,5,6,7], return

     4
   /   \
  2     6
 / \    / \
1   3  5   7
Note
There may exist multiple valid solutions, return any of them.

Tags Expand 
Cracking The Coding Interview Recursion Binary Tree

Thoughts:
1. Find middle point x.
2. All index before x, goes to left of the tree. Same apply to right tree
3. Recursion

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
     * @param A: an integer array
     * @return: a tree node
     */
    private TreeNode buildTree(int[] A, int start, int end) {
        if (start > end) {
            return null;
        }
        
        int mid = start + (end - start) / 2;
        TreeNode node = new TreeNode(A[mid]);
        node.left = buildTree(A, start, mid - 1);
        node.right = buildTree(A, mid + 1, end);
        
        return node;
    }
    
    public TreeNode sortedArrayToBST(int[] A) {  
        // write your code here
        if (A == null) {
            return null;
        }
        
        return buildTree(A, 0, A.length - 1);
    }  
}
