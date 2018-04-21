/*
Description
Given a binary tree with n nodes, your task is to check if
it's possible to partition the tree to two trees which have the equal sum of values
after removing exactly one edge on the original tree.

Notice
The range of tree node value is in the range of [-100000, 100000].
1 <= n <= 10000

Example
Given
    5
   / \
  10 10
    /  \
   2   3
return True

Explanation:
    5
   /
  10
Sum: 15
   10
  /  \
 2    3
Sum: 15

Given
    1
   / \
  2  10
    /  \
   2   20
return False

Explanation:
You can't split the tree into two trees with equal sum after removing exactly one edge on the tree.

Tags
Binary Tree Amazon
 */

/**
 * Approach: Divide and Conquer
 * 这道题让我们划分等价树，就是说当移除一条边后，被分成的两棵树的结点之和需要相等。
 * 那么通过观察题目中的例子我们可以发现，如果我们将每个结点的 结点值 变成其 所有子结点的结点值之和 再 加上当前的结点值，
 * 那么对于例子1来说，根结点的结点值就变成了30，断开位置的结点就变成了15，
 * 那么我们就可以发现其实只要 断开位置的结点值 是 根结点值的一半，就存在等价划分。
 * 所以这道题的考点就是更新每个结点的结点值，我们可以使用 递归 来做。
 * 写法和 Binary Tree Maximum Path Sum's Approach 2 相同
 * https://github.com/cherryljr/LintCode/blob/master/Binary%20Tree%20Maximum%20Path%20Sum.java
 *
 * 时间复杂度为：O(n)
 * 空间复杂度为：O(1)
 *
 * 本题做法为了省空间直接对节点的值进行了修改，在实际应用中并不是一个很好的做法。（不应该破坏原有的数据）
 * 因此这边给出了一个利用 Stack 保存数据的方法以供参考：
 * https://leetcode.com/articles/equal-tree-partition/
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
     * @param root: a TreeNode
     * @return: return a boolean
     */
    public boolean checkEqualTree(TreeNode root) {
        if (root == null) {
            return false;
        }

        // 获得整棵树 所有结点值 之和
        long sum = getSum(root);
        boolean rst = false;
        // 看结点值之和能否被 2 整除（若不能直接返回 false）
        if ((sum & 1) == 0) {
            // 查看 左子树 是否为空，不为空的话如果其 subSum值 等于 所有结点值和的一半，
            // 则我们就可以将其划分出来
            if (root.left != null) {
                rst |= (root.left.val == sum >> 1);
            }
            // 按照同样的方法检查右子树
            if (root.right != null) {
                rst |= (root.right.val == sum >> 1);
            }
        }
        return rst;
    }

    // 将各个结点值更新为 所有子结点的结点值之和 再 加上当前的结点值
    private long getSum(TreeNode root) {
        if (root == null) {
            return 0L;
        }

        root.val +=  getSum(root.left) + getSum(root.right);
        return root.val;
    }
}