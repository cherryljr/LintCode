/*
Description
Given a binary tree, determine if it is height-balanced.
For this problem, a height-balanced binary tree is defined as a binary tree
in which the depth of the two subtrees of every node never differ by more than 1.

Example
Given binary tree A = {3,9,20,#,#,15,7}, B = {3,#,20,15,7}

A)  3            B)    3
   / \                  \
  9  20                 20
    /  \                / \
   15   7              15  7
The binary tree A is a height-balanced binary tree, but B is not.

Tags
Divide and Conquer Recursion
 */

/**
 * Approach 1: Divide and Conquer
 * 需要求解一棵树是否为 平衡树，即要求其所有 子树 都是平衡树。
 * 因此我们可以通过 分治 的方法来解决。
 * 即如果一个树的 左子树 和 右子树 均为平衡树 并且 左右子树高度差 小于等于1
 * 则这棵树为平衡树，否则只要有一个条件不符合，就说明其不是平衡树。
 *
 * 因此在 分治 的过程中，我们需要两个信息：
 *  1. 该子树是否为平衡树；   2. 该子树的高度是多少(用于判断是否为平衡树)
 * 然后进行 递归 判断即可。
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
     * @return: True if this Binary tree is Balanced, or false.
     */
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

    private int maxDepth(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return Math.max(maxDepth(node.left), maxDepth(node.right)) + 1;
    }
}

/**
 * Approach 2: Divide anc Conquer
 * 在 Approach 1 中，我们分析出来了可以利用 分治 的方法解决这道题目。
 * 并且 分治 的过程中需要用到 两个 信息。
 * 但是考虑到 高度 这个信息的 非负性 这个特点。
 * 我们可以利用 -1 这个值来表示其为 非平衡树 这个信息，从而节约空间。
 * 其他做法与 Approach 1 相同，具体实现请看代码。
 */
public class Solution {
    /**
     * @param root: The root of binary tree.
     * @return: True if this Binary tree is Balanced, or false.
     */
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }

        return maxDepth(root) != -1;
    }

    private int maxDepth(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int leftDepth = maxDepth(node.left);
        int rightDepth = maxDepth(node.right);
        if (leftDepth == -1 || rightDepth == -1 || Math.abs(leftDepth - rightDepth) > 1) {
            return -1;
        }

        return Math.max(leftDepth, rightDepth) + 1;
    }
}