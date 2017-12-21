/*
Description
The thief has found himself a new place for his thievery again.
There is only one entrance to this area, called the "root."
Besides the root, each house has one and only one parent house.
After a tour, the smart thief realized that "all houses in this place forms a binary tree".
It will automatically contact the police if two directly-linked houses were broken into on the same night.
Determine the maximum amount of money the thief can rob tonight without alerting the police.
Example
  3
 / \
2   3
 \   \
  3   1
Maximum amount of money the thief can rob = 3 + 3 + 1 = 7.
    3
   / \
  4   5
 / \   \
1   3   1
Maximum amount of money the thief can rob = 4 + 5 = 9.
Tags
Uber Depth First Search
*/


/**
 * If we want to rob maximum amount of money from current binary tree (rooted at root),
 * we surely hope that we can do the same to its left and right subtrees.
 *
 * Apparently, this question is about tree and the current status is determined by the last status.
 * So can use DP to solve this problem?
 * Let's go back to see the solution of a simpler question House Robber.
 * It use dp[i][0] to represents the current house isn't robbed while dp[i][1] represents the current house is robbed.
 * Now, let's do it in this method.
 * Redefine rob(root) as a new function which will return an array of two elements,
 * the first element of which denotes the maximum amount of money that can be robbed if root is not robbed,
 * while the second element signifies the maximum amount of money robbed if it is robbed.
 * Let's relate rob(root) to rob(root.left) and rob(root.right)..., etc.
 * For the 1st element of rob(root), we only need to sum up the larger elements of rob(root.left) and rob(root.right),
 * respectively, since root is not robbed and we are free to rob its left and right subtrees.
 * For the 2nd element of rob(root), however, we only need to add up the 1st elements of rob(root.left) and rob(root.right),
 * respectively, plus the value robbed from root itself,
 * since in this case it's guaranteed that we cannot rob the nodes of root.left and root.right.
 */

/**
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int val;
 *     public TreeNode left, right;
 *     public TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    /*
     * @param root: The root of binary tree.
     * @return: The maximum amount of money you can rob tonight
     */
    public int houseRobber3(TreeNode root) {
        int[] rst = rob(root);
        return Math.max(rst[0], rst[1]);
    }

    private int[] rob(TreeNode root) {
        // if the node is empty, return the value 0.
        if (root == null) {
            return new int[2];
        }

        int[] left = rob(root.left);
        int[] right = rob(root.right);
        int[] curr = new int[2];
        // the current house isn't robbed
        // so we are free to rob its left and right subtrees
        curr[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        // the current house is robbed
        // so we only need to add up left[0], right[0] and the current value.
        curr[1] = left[0] + right[0] + root.val;

        return curr;
    }
}