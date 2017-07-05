1. DFS using depth marker: 每个depth都存一下。然后如果不平衡，值就设为-1.
   一旦有-1， 就全部返回。
   最后比较返回结果是不是-1. 是-1，那就false.
   Traverse 整个tree, O(n)

2. Only calculate depth using maxDepth function. Same concept as in 1, but cost more traversal efforts.

```
/*
Given a binary tree, determine if it is height-balanced.

For this problem, a height-balanced binary tree is defined as a binary tree,  
in which the depth of the two subtrees of every node never differ by more than 1.

Example
Given binary tree A={3,9,20,#,#,15,7}, B={3,#,20,15,7}

A)  3            B)    3 
   / \                  \
  9  20                 20
    /  \                / \
   15   7              15  7
The binary tree A is a height-balanced binary tree, but B is not.

Tags Expand 
Binary Search Divide and Conquer Recursion

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


/*

Thinking process:
making use depth first search.
same process as Maximum Depth of Binary Tree.java method.
after recursive call, check if Math.abs(left - right) > 1. If so, return -1.
If any case return -1, they all return -1.
at the top return, check if -1.

*/
/*  
    Recursive 1:
    Use helper to calculate depth, and also check if left/right depth differ by 1. If all good, return actual depth
*/
 
 public class Solution {
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        return helper(root) != -1;
    }
    
    public int helper(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int leftDepth = helper(node.left);
        int rightDepth = helper(node.right);
        
        if (leftDepth == -1 || rightDepth == -1 || Math.abs(leftDepth - rightDepth) > 1) {
            return -1;
        }
        
        return Math.max(leftDepth, rightDepth) + 1;
    }
}


/*
    Recursive 2:
    Calculate a node's maxDepth. Compare a parent node's sub tree for maxDepth
*/
public class Solution {
   public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        
        if (Math.abs(leftDepth - rightDepth) > 1) {
            return false;
        }
        return isBalanced(root.left) && isBalanced(root.right);
    }
    
    public int maxDepth(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return Math.max(maxDepth(node.left), maxDepth(node.right)) + 1;
    }
}
