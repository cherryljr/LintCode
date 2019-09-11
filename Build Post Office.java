/*
Description
Given a 2D grid, each cell is either an house 1 or empty 0 (the number zero, one), find the place to build a post office,
the distance that post office to all the house sum is smallest. Return the smallest distance. Return -1 if it is not possible.
    ·You can pass through house and empty.
    ·You only build post office on an empty.
The distance between house and the post office is Manhattan distance

Example
Example 1:
Input：[[0,1,0,0],[1,0,1,1],[0,1,0,0]]
Output： 6
Explanation：
Placing a post office at (1,1), the distance that post office to all the house sum is smallest.

Example 2:
Input：[[0,1,0],[1,0,1],[0,1,0]]
Output： 4
Explanation：
Placing a post office at (1,1), the distance that post office to all the house sum is smallest.
 */

/**
 * Approach: PreSum + SuffixSum
 * 本题需要求一个点使得该点到所有点的曼哈顿距离之和最小，求最小距离的值是多少。
 * 如果对于每一个点都计算一次与其他所有点的曼哈顿距离之和，这将耗费掉 O(n^4) 的时间复杂度。
 * 但是本题要求解的距离是 曼哈顿距离，而曼哈顿距离的定义为 |Xi - Xj| + |Yi - Yj|
 * 对此如果把这个公式拆成两个部分看的话可以发现，我们在计算过程中将多次重复使用到 |Xi-Xj| 和 |Yi - Yj|
 * 因此，可以利用到 PreSum[] 和 SuffixSum[]，即空间换时间的做法对本题的时间复杂度进行优化。
 * 实现计算出其他所有点到 当前行|列 的距离，然后当前位置到其他所有点的曼哈顿距离就是 ansRow[i]+ansCol[j]
 * 然后取个距离和最小的值即可
 *
 * 时间复杂度：O(m*n)
 * 空间复杂度：O(m + n)
 */
public class Solution {
    /**
     * @param grid: a 2D grid
     * @return: An integer
     */
    public int shortestDistance(int[][] grid) {
        if (grid == null || grid.length == 0 || !haveZeros(grid)) {
            return -1;
        }

        int m = grid.length, n = grid[0].length;
        // rowSum表示当前行的office个数，colSum表示当前列的office个数
        int[] rowSum = new int[m], colSum = new int[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    rowSum[i] += 1;
                    colSum[j] += 1;
                }
            }
        }

        int ans = Integer.MAX_VALUE;
        // ansRow[i]代表其他所有点到第i行的距离之和
        int[] ansRow = getSumDistance(rowSum);
        // ansCol[]代表其他所有点到第i列的距离之和
        int[] ansCol = getSumDistance(colSum);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) ans = Math.min(ans, ansRow[i] + ansCol[j]);
            }
        }
        return ans;
    }

    private int[] getSumDistance(int[] arr) {
        int n = arr.length;
        // preSum2[i]表示 i 之前的 行|列 的所有点到当前 行|列 的距离
        int[] preSum1 = new int[n], preSum2 = new int[n];
        preSum1[0] = arr[0];
        for (int i = 1; i < n; i++) {
            preSum1[i] = preSum1[i - 1] + arr[i];
        }
        for (int i = 1; i < n; i++) {
            preSum2[i] = preSum2[i - 1] + preSum1[i - 1];
        }
        // suffixSum2[i]表示 i 之后的 行|列 的所有点到当前 行|列 的距离
        int[] suffixSum1 = new int[n], suffixSum2 = new int[n];
        suffixSum1[n - 1] = arr[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            suffixSum1[i] = suffixSum1[i + 1] + arr[i];
        }
        for (int i = n - 2; i >= 0; i--) {
            suffixSum2[i] = suffixSum2[i + 1] + suffixSum1[i + 1];
        }
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = preSum2[i] + suffixSum2[i];
        }
        return ans;
    }

    private boolean haveZeros(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
