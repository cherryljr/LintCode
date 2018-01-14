/*
Given a binary tree, find the maximum path sum.

The path may start and end at any node in the tree.


Example
Given the below binary tree:

  1
 / \
2   3
return 6.

Tags Expand 
Divide and Conquer Dynamic Programming Recursion
*/

/**
 * Approach 1: Divide And Conquer
 * 这里所说的路径和是指：从任意结点开始到任意结点结束的结点值之和，而且要注意的是结点的值也可能为负数 。
 * 思路：
 * 整棵树的最大路径总共有四种情况：
 *  1. 根节点
 *  2. 左子树的最大路径和
 *  3. 右子树的最大路径和
 *  4. 左子树最大路径和 + 根节点 + 右子树最大路径和
 *
 * 我们可以先将问题简化为：从一个根节点到任一点的最大路径。同时我们发现第四种情况其又是由三部分组成。
 * 所以我们程序中需要记录两个数据：
 * 一个是从根节点到任意点的最大路径和singlePath;
 * 一个是从任意点到任意点的最大路径和maxPath.
 * 在singlePath的基础上，再加入根节点我们便可以得到跨越根节点的第四种情况情况.使得问题得以解决.
 * 因为使用分治法，需要返回类型。而本程序中需要返回两个数，故我们自己定了一个ResultType类来储存数据。
 *
 * Divide: 左右子树
 * Conquer: 先处理singlePath,然后利用singlePath的结果来处理maxPath.
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
    /*
     * @param root: The root of binary tree.
     * @return: An integer
     */
    public int maxPathSum(TreeNode root) {
        ResultType result = helper(root);
        return result.maxPath;
    }

    // 在一次递归过程中，我们需要返回两个数据。但是Java中没有指针，因此为了获得我们想要的 maxPath,
    // 我们只能自己创建一个 ResultType 类来满足需求，以同时包含递归过程需要用到的：singlePath 与最终结果 maxPath.
    private class ResultType {
        // singlePath: 从root往下走到任意点的最大路径，这条路径可以不包含任何点
        // maxPath: 从树中任意到任意点的最大路径，这条路径至少包含一个点
        int singlePath, maxPath;
        ResultType(int singlePath, int maxPath) {
            this.singlePath = singlePath;
            this.maxPath = maxPath;
        }
    }

    private ResultType helper(TreeNode root) {
        //	对异常情况的处理
        if (root == null) {
            return new ResultType(0, Integer.MIN_VALUE);
        }
        // Divide
        ResultType left = helper(root.left);
        ResultType right = helper(root.right);

        // Conquer
        int singlePath = Math.max(left.singlePath, right.singlePath) + root.val;
        //	singlePath作为一个辅助数据，这里需要和0比较，看是否需要该singlePath
        singlePath = Math.max(singlePath, 0);

        // maxPath 的值可能是：左子树最大路径和 / 右子树最大路径和
        // 因此这里我们首先取左右字树中较大的最大路径和对其进行初始化
        int maxPath = Math.max(left.maxPath, right.maxPath);

        // 将左右字树中的最大路径和 与 跨越该根节点的路径和进行比较。
        // 得出最大值即为最终结果。
        maxPath = Math.max(maxPath, left.singlePath + right.singlePath + root.val);

        return new ResultType(singlePath, maxPath);
    }
}


/**
 * Approach 2: Divide and Conquer (Optimized)
 * 方法一种的写法毫无疑问，略显臃肿。
 * 虽然利用 ResultType 来封装我们需要的类是不错的选择，但是这样也浪费的空间。
 * 毕竟大家想，最终的结果只是一个值而已啊。
 * 因此我们可以想到：使用一个全局变量便可以完美解决该问题。
 *
 * 其他方面与方法一相同：
 * 求左字树的最大路径和; 求右字树的最大路径和.
 * （注意：这里指的就是 singlePath,因此存在负值，所以要和 0 进行比较看是否需要该路径）
 * 最后将 两边的最大路径和+当前节点的值 与 maxPath 进行比较，取最大值便是我们需要的结果。
 *
 * Note:
 * 大家会发现这个方法里，我们好像省略掉了一些比较的步骤。
 * 比如：maxPath只在 左/右子树 中的情况，或者是maxPath仅由根结点组成的情况。
 * 但实际上大家理解了程序如何运行之后，便会明白该方法实际上都是考虑了以上情况的。
 * 因为程序是递归运行的，所以当前节点的 maxPath,是由其左右子树决定的。
 * 在当前节点的值被计算出来之前，肯定已经计算过了左右子树的情况了。
 * 可以形象地理解为从树的叶子结点开始向上一步步爬。
 * 要知道当前节点的左孩子也左子树的一个根节点。因此所有情况实际上都已经被考虑了。
 * 这也正是写法1臃肿的地方所在。
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
    /*
     * @param root: The root of binary tree.
     * @return: An integer
     */
    // 作为全局变量保存最大路径和
    int maxPath;

    public int maxPathSum(TreeNode root) {
        maxPath = Integer.MIN_VALUE;
        maxPathDown(root);
        return maxPath;
    }

    private int maxPathDown(TreeNode node) {
        if (node == null) {
            return 0;
        }

        // Divide
        int left = Math.max(0, maxPathDown(node.left));
        int right = Math.max(0, maxPathDown(node.right));
        // Conquer
        maxPath = Math.max(maxPath, left + right + node.val);

        return Math.max(left, right) + node.val;
    }
}