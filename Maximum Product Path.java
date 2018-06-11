/*
Description
A two fork tree with n nodes and a root node of 1. Each edge is described by two vertices x[i] and y[i],
and the weight of each point is described by d[i].
We define the weights of all nodes from the root node to the leaf node path to be x.
Find the maximum value of x % 1e9+7.

2 <= n <= 10^5
-10^5 <= d[i] <= 10^5
1 <= x[i], y[i] <= n

Example
Given x = [1], y = [2], d = [1,1]. return 1.
Explanation:
Maximum product path: 1 -> 2.

Given x = [1,2,2], y = [2,3,4], d = [1,1,-1,2]. return 1000000006.
Explanation:
Maximum product path: 1->2->3.
 */

/**
 * Approach: DFS
 * 很简单的题目，但是题目描述写得很恶心...结果必须先 +MOD 然后再进行取余操作。
 * 这点在例子里体现了，但是描述里压根没讲。
 * 做法直接 DFS 暴力做...
 * 利用 Map 建立二叉树即可...
 *
 * 不想多说，差评！！！
 */
class Solution {
    private static final int MOD = 1000000007;
    int max = Integer.MIN_VALUE;

    class TreeNode {
        int val;
        TreeNode left, right;

        public TreeNode(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }
    }

    /**
     * @param x: The end points of edges set
     * @param y: The end points of edges set
     * @param d: The weight of points set
     * @return: Return the maximum product
     */
    public int getProduct(int[] x, int[] y, int[] d) { 
        Map<Integer, TreeNode> tree = new HashMap<>();
        for (int i = 0; i < d.length; i++) {
            tree.put(i + 1, new TreeNode(d[i]));
        }
        for (int i = 0; i < x.length; i++) {
            TreeNode parent = tree.get(x[i]);
            TreeNode child = tree.get(y[i]);
            if (parent.left == null) {
                parent.left = child;
            } else {
                parent.right = child;
            }
        }

        TreeNode root = tree.get(1);
        dfs(root, 1);
        return max;
    }

    private void dfs(TreeNode root, int product) {
        if (root == null) {
            return;
        }
        int rst = (int)(((long)product * root.val + MOD) % MOD);
        if (root.left == null && root.right == null) {
            max = Math.max(max, rst);
            return;
        }
        dfs(root.left, rst);
        dfs(root.right, rst);
    }
}
