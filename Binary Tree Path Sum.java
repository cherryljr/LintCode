典型的 深度优先搜索（DFS） 题目
参考 Combination Sum II 分析即可。
同时，若不知道如何书写 / 分析 DFS 程序可以参考 Subset.java

/*
Description
Given a binary tree, find all paths that sum of the nodes in the path equals to a given number target.
A valid path is from root node to any of the leaf nodes.

Example
Given a binary tree, and target = 5:

     1
    / \
   2   4
  / \
 2   3
return

[
  [1, 2, 2],
  [1, 4]
]

Tags 
Binary Tree Binary Tree Traversal
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
     * @param root the root of binary tree
     * @param target an integer
     * @return all valid paths
     */
    public List<List<Integer>> binaryTreePathSum(TreeNode root, int target) {
        List<List<Integer>> rst = new ArrayList<>();
        if (root == null) {
            return rst;
        }
        
        dfs(rst, new ArrayList<Integer>(), root, target);
        return rst;
    }
    
    private void dfs(List<List<Integer>> rst,
                        ArrayList<Integer> list,
                        TreeNode t, 
                        int remainTarget) {
        if (t == null) {
            return;
        }
        
        list.add(t.val);
        if (t.left == null && t.right == null && t.val == remainTarget) {
            rst.add(new ArrayList<Integer>(list));
            return;
        }
        if (t.left != null) {
            dfs(rst, list, t.left, remainTarget - t.val);
            // Backtracking
            list.remove(list.size() - 1);
        }
        if (t.right != null) {
            dfs(rst, list, t.right, remainTarget - t.val);
            // Backtracking
            list.remove(list.size() - 1);
        }
    }
}

