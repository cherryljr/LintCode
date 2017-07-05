这里所说的路径和是指：从任意结点开始到任意结点结束的结点值之和，而且要注意的是结点的值也可能为负数 。

思路：
	整棵树的最大路径总共有四种情况：
		1. 根节点
		2. 左子树的最大路径和
		3. 右子树的最大路径和
		4. 左子树最大路径和 + 根节点 + 右子树最大路径和

我们可以先将问题简化为：从一个根节点到任一点的最大路径。同时我们发现第四种情况其又是由三部分组成。
所以我们程序中需要记录两个数据：一个是从根节点到任意点的最大路径和singlePath、
																一个是从任意点到任意点的最大路径和maxPath.
在singlePath的基础上，再加入根节点我们便可以得到跨越根节点的第四种情况情况.使得问题得以解决.
因为使用分治法，需要返回类型。而本程序中需要返回两个数，故我们自己定了一个ResultType类来储存数据。

Divide: 左右子树
Conquer: 先处理singlePath,然后利用singlePath的结果来处理maxPath.


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

        int maxPath = Math.max(left.maxPath, right.maxPath);
        //	这里需要注意maxPath还和left.singlePath + right.singlePath + root.val进行比较
        //  而singlePath可能是为0的，而 +root.val 就可以保证结果至少还有一个root.val
        //  比如整棵树都是-1
        maxPath = Math.max(maxPath, left.singlePath + right.singlePath + root.val);

        return new ResultType(singlePath, maxPath);
    }

    public int maxPathSum(TreeNode root) {
        ResultType result = helper(root);
        return result.maxPath;
    }
}


// Version 2:
// SinglePath也定义为，至少包含一个点。
public class Solution {
    /**
     * @param root: The root of binary tree.
     * @return: An integer.
     */
    private class ResultType {
        int singlePath, maxPath;
        ResultType(int singlePath, int maxPath) {
            this.singlePath = singlePath;
            this.maxPath = maxPath;
        }
    }

    private ResultType helper(TreeNode root) {
        if (root == null) {
            return new ResultType(Integer.MIN_VALUE, Integer.MIN_VALUE);
        }
        // Divide
        ResultType left = helper(root.left);
        ResultType right = helper(root.right);

        // Conquer
        int singlePath =
            Math.max(0, Math.max(left.singlePath, right.singlePath)) + root.val;

        int maxPath = Math.max(left.maxPath, right.maxPath);
        maxPath = Math.max(maxPath,
                           Math.max(left.singlePath, 0) + 
                           Math.max(right.singlePath, 0) + root.val);

        return new ResultType(singlePath, maxPath);
    }

    public int maxPathSum(TreeNode root) {
        ResultType result = helper(root);
        return result.maxPath;
    }

}