/*
Description
Given a m x n grid filled with non-negative numbers,
find a path from top left to bottom right which minimizes the sum of all numbers along its path.

Notice
You can only move either down or right at any point in time.

Example:
[[1,3,1],
 [1,5,1],
 [4,2,1]]
Given the above grid map, return 7. Because the path 1→3→1→1→1 minimizes the sum.

Tags
Dynamic Programming
 */

/**
 * Approach 1: Matrix DP
 * 与 Unique Paths 相似，均属于 矩阵DP 的基础题。
 * 按照矩阵动态规划思路进行解题。
 * 首先建立一个二维数组dp[].
 *  State: dp[i][j]表示从 起点 到 坐标(i, j) 的最小路径和
 *  Function: 同一时间内可向左或者向右移动一步
 *      dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + A[i][j];
 *  Initialize:	由题可得起点为左上角，并且这是一个二维数组，可得由起点到矩阵左上侧边缘各点的路径和为：
 *      dp[0][0] = A[0][0]
 *      dp[i][0] = sum(0,0 -> i,0)
 *      dp[0][i] = sum(0,0 -> 0,i)
 *  Answer:	右下角为终点故为 dp[rows - 1][cols - 1].
 *
 * 时间复杂度：O(mn)； 空间复杂度：O(mn)
 */
public class Solution {
    /**
     * @param grid: a list of lists of integers.
     * @return: An integer, minimizes the sum of all numbers along its path
     */
    public int minPathSum(int[][] grid) {
        // write your code here
        if (grid == null || grid.length == 0
                || grid[0] == null || grid[0].length == 0) {
            return 0;
        }

        int rows = grid.length;
        int cols = grid[0].length;
        int[][] dp = new int[rows][cols];

        //  Initialize
        dp[0][0] = grid[0][0];
        for (int i = 1; i < rows; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }
        for (int i = 1; i < cols; i++) {
            dp[0][i] = dp[0][i - 1] + grid[0][i];
        }

        //  Function
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }

        //  Answer
        return dp[rows - 1][cols - 1];
    }
}

/**
 * Approach 2: Matrix DP (Optimized by Sliding Array)
 * 由 Approach 1 中的分析可得：
 *  dp[i][j] 仅仅由 上一行的元素值 和 前一个元素值（左侧） 所决定。
 * 因此我们可以利用 滚动数组 对其 空间复杂度 进行优化。
 * 根据状态方程分析可得：我们只需要存储 两行的结果 即可。
 *
 * 时间复杂度：O(mn)； 空间复杂度：O(n)
 */
public class Solution {
    /**
     * @param grid: a list of lists of integers.
     * @return: An integer, minimizes the sum of all numbers along its path
     */
    public int minPathSum(int[][] grid) {
        // write your code here
        if (grid == null || grid.length == 0
                || grid[0] == null || grid[0].length == 0) {
            return 0;
        }

        int rows = grid.length;
        int cols = grid[0].length;
        int[][] dp = new int[2][cols];

        //  Initialize the first row of dp array
        dp[0][0] = grid[0][0];
        for (int i = 1; i < cols; i++) {
            dp[0][i] = dp[0][i - 1] + grid[0][i];
        }

        //  Function
        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Initialize the first col of dp array
                if (j == 0) {
                    dp[i & 1][j] = dp[(i-1) & 1][j] + grid[i][j];
                    continue;
                }
                dp[i & 1][j] = Math.min(dp[(i-1) & 1][j], dp[i & 1][j - 1]) + grid[i][j];
            }
        }

        //  Answer
        return dp[(rows-1) & 1][cols - 1];
    }
}