/*
Given a binary tree, determine if it is a valid binary search tree (BST).

Assume a BST is defined as follows:

The left subtree of a node contains only nodes with keys less than the node's key.
The right subtree of a node contains only nodes with keys greater than the node's key.
Both the left and right subtrees must also be binary search trees.
A single node tree is a BST

Example:

  2
 / \
1   4
   / \
  3   5
The above binary tree is serialized as {2,1,4,#,#,3,5} (in level order).

Tags Expand 
Divide and Conquer Recursion Binary Search Tree Binary Tree
*/

/**
 * Approach 1: Inorder Traversal (O(n) Extra Space)
 * The same question in LeetCode.
 * You can get detail explanations here:
 * https://github.com/cherryljr/LeetCode/blob/master/Validate%20Binary%20Search%20Tree.java
 * 
 * Learn one iterative inorder traversal, apply it to multiple tree questions:
 * https://leetcode.com/problems/validate-binary-search-tree/discuss/32112/Learn-one-iterative-inorder-traversal-apply-it-to-multiple-tree-questions-(Java-Solution)
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
     * @param root: The root of binary tree.
     * @return: True if the binary tree is BST, or false
     */
    public boolean isValidBST(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode pre = null;
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if (pre != null && pre.val >= root.val) {
                return false;
            }
            pre = root;
            root = root.right;
        }
        return true;
    }
}

/**
 * Approach 2: Morris Traversal (O(1) Extra Space)
 * 使用了 Morris的中序遍历，解法思想仍然相同。
 * 只是使用了更高级的遍历方式来节约了额外空间。
 *
 * 参考：
 * https://github.com/cherryljr/LintCode/blob/master/Morris%20Traversal%20Template.java
 * https://github.com/cherryljr/LintCode/blob/master/Binary%20Tree%20Inorder%20Traversal.java
 */
class Solution {
    public boolean isValidBST(TreeNode root) {
        TreeNode curr = root;
        TreeNode pre = null;

        while (curr != null) {
            if (curr.left == null) {
                if (pre != null && pre.val >= curr.val) {
                    return false;
                }
                pre = curr;
                curr = curr.right;
            } else {
                TreeNode rightMost = getRightMost(curr);
                if (rightMost.right == null) {
                    rightMost.right = curr;
                    curr = curr.left;
                } else {
                    if (pre != null && pre.val >= curr.val) {
                        return false;
                    }
                    pre = curr;
                    rightMost.right = null;
                    curr = curr.right;
                }
            }
        }

        return true;
    }

    private TreeNode getRightMost(TreeNode curr) {
        TreeNode node = curr.left;
        while (node.right != null && node.right != curr) {
            node = node.right;
        }
        return node;
    }
}