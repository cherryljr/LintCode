/*
Given a complete binary tree, count the number of nodes.

Definition of a complete binary tree from Wikipedia:
In a complete binary tree every level, except possibly the last, is completely filled, 
and all nodes in the last level are as far left as possible. 
It can have between 1 and 2h nodes inclusive at the last level h.
*/

/**
 * Approach 1: Divide and Conquer
 * 我们可以知道：假设一棵 满二叉树 的高度为 h，则其节点个数为 2^h - 1.
 * 因此我们可以借助这一点来优化 完全二叉树 的节点统计过程。
 * 因为一颗 完全二叉树 的 子树 很有可能就是一棵 满二叉树。
 * 所以我们计算一个节点到其 最左子节点的高度leftDepth 和 最右子节点的深度rightDepth.
 * 如果二者相等，则说明为一棵 满二叉树，可以直接通过公式计算出答案。
 * 如果不相等，用同样的方法 递归 计算其左右节点。
 *
 * 这个做法的优点就是在于通过 满二叉树 的节点计算公式来优化时间复杂度。
 * 遍历一个节点的左右高度的时间复杂度为：O(2logn)
 * 总共需要遍历 O(logn) 个节点
 *  问题：为什么是 logn 个节点呢？
 *  答案：是由完全二叉树的结构决定的，我们假设 leftDepth != rightDepth.
 *  因为这是一棵 完全二叉树 ，那么只可能是 ：leftDepth = rightDepth + 1.
 *      对应的也意味着 左子树或者右子树 中必定有一棵 满二叉树；
 *  因此在后续的 递归 过程中，我们实际上只会继续递归计算树的一侧节点，而非全部。（相等时直接 O(1) 无需继续递归）
 *  而树的高度是 logn, 因此我们最多需要遍历 logn 个节点。
 * 因此总体时间复杂度为：O(2log(n)^2) => O(log(n)^2)
 */

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftDepth = getLeftDepth(root);
        int rightDepth = getRightDepth(root);
        if (leftDepth == rightDepth) {
            // if it's a full binary tree, then the result is 2^h - 1
            return (1 << leftDepth) - 1;
        } else {
            // if it isn't full binary tree, we call it recursively
            // left-subtree and right-subtree and plus 1.
            return countNodes(root.left) + countNodes(root.right) + 1;
        }
    }

    // get the left-subtree's height (including root node)
    private int getLeftDepth(TreeNode node) {
        int depth = 0;
        while (node != null) {
            depth++;
            node = node.left;
        }
        return depth;
    }

    // get the right-subtree's height (including root node)
    private int getRightDepth(TreeNode node) {
        int depth = 0;
        while (node != null) {
            depth++;
            node = node.right;
        }
        return depth;
    }
}

/**
 * Approach 2: Divide and Conquer (Optimized)
 * 基本实现与 Approach 1 中的相同。
 * 在 Approach 1 中，当 leftDepth != rightDepth 时，
 * 有分析可得：虽然我们只会从其中一侧继续递归计算下去，
 * 但是两个子节点的 leftDepth 和 rightDepth 我们都会计算一次，这样就浪费了一次 logn 的计算了。
 * 因此在这里我们可以对其进行一个优化。
 *
 * 这里我们只是用到了 getMostLeftDepth 函数，它的功能与 Approach 1 中的 getLeftDepth 相同，
 * 都是计算 最左侧子节点的深度。level 表示 当前结点所在的层数。
 * 具体解释可以看代码注释。
 *
 * 时间复杂度不变，只是在常数级别上进行了优化：O(log(n)^2)
 */
class Solution {
    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return getCount(root, 1, getMostLeftDepth(root, 1));
    }

    /**
     * 获得以 node 为 根节点 的树的节点个数
     * @param node 根节点
     * @param level 当前结点所在的层数
     * @param depth 树的总高度
     * @return
     */
    private int getCount(TreeNode node, int level, int depth) {
        if (level == depth) {
            return 1;
        }
        if (getMostLeftDepth(node.right, level + 1) == depth) {
            // 右子树的最左节点深度等于depth,说明左子树为满二叉树
            return (1 << (depth - level)) + getCount(node.right, level + 1, depth);
        } else {
            // 右子树的最左节点深度小于depth(值为depth-1),说明右子树为满二叉树
            return (1 << (depth - level - 1)) + getCount(node.left, level + 1, depth);
        }
    }

    // 获取当前结点 最左子节点的深度
    private int getMostLeftDepth(TreeNode node, int level) {
        while (node != null) {
            level++;
            node = node.left;
        }
        return level - 1;
    }
}