/*
Description
Follow up for "Unique Paths":
Now consider if some obstacles are added to the grids. How many unique paths would there be?
An obstacle and empty space is marked as 1 and 0 respectively in the grid.

Notice
m and n will be at most 100.

Example
For example,
There is one obstacle in the middle of a 3x3 grid as illustrated below.
[
  [0,0,0],
  [0,1,0],
  [0,0,0]
]
The total number of unique paths is 2.

Tags
Dynamic Programming Array
 */

/**
 * Approach 1: Matrix DP
 * 该题是 Unique Path 问题的一个 Follow Up,只是在原本的基础上增加了一个障碍物条件。
 * 解法思路与之前的相同，只需要增加了一个if语句用来判断：
 * 若该坐标位置(i, j)上是否有障碍物：若没有按照原来方法处理，若有这将其路径值置为0
 */
public class Solution {
    /**
     * @param obstacleGrid: A list of lists of integers
     * @return: An integer
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        // write your code here
        if (obstacleGrid == null || obstacleGrid.length == 0
                || obstacleGrid[0] == null || obstacleGrid[0].length == 0) {
            return 0;
        }

        int rows = obstacleGrid.length;
        int cols = obstacleGrid[0].length;
        int[][] dp = new int[rows][cols];

        //  Initialize
        for (int i = 0; i < rows; i++) {
            if (obstacleGrid[i][0] != 1) {
                dp[i][0] = 1;
            } else {
                break;
            }
        }
        for (int i = 0; i < cols; i++) {
            if (obstacleGrid[0][i] != 1) {
                dp[0][i] = 1;
            } else {
                break;
            }
        }

        //  Function
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                if (obstacleGrid[i][j] != 1) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                } else {
                    dp[i][j] = 0;
                }
            }
        }

        //  Answer
        return dp[rows - 1][cols - 1];
    }
}

/**
 * Approach 2: Matrix DP (Optimized by Sliding Array)
 * 与 Unique Paths 相同，我们可以利用 滚动数组 对 Approach 1 中的代码进行
 * 空间复杂度 的优化。
 * 主要注意点就是 DP 数组第一列的初始化。
 * 只有当 obstacleGrid[i][j] == 0 并且 dp[(i-1) & 1][j] != 0 的时候，
 * 才能说明可以从起点到达该点，并且路径方法只有 1 个。
 * 其他方面按照原来的方法处理即可。
 *
 * 时间复杂度：O(mn); 空间复杂度：O(n)
 */
public class Solution {
    /**
     * @param obstacleGrid: A list of lists of integers
     * @return: An integer
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        // write your code here
        if (obstacleGrid == null || obstacleGrid.length == 0
                || obstacleGrid[0] == null || obstacleGrid[0].length == 0) {
            return 0;
        }

        int rows = obstacleGrid.length;
        int cols = obstacleGrid[0].length;
        int[][] dp = new int[2][cols];

        //  Initialize the first row of dp array
        for (int i = 0; i < cols; i++) {
            if (obstacleGrid[0][i] != 1) {
                dp[0][i] = 1;
            } else {
                break;
            }
        }

        //  Function
        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Initialize the first column of dp array
                if (j == 0) {
                    if (obstacleGrid[i][j] == 0 && dp[(i-1) & 1][j] != 0) {
                        dp[i & 1][j] = 1;
                    } else {
                        dp[i & 1][j] = 0;
                    }
                    continue;
                }

                if (obstacleGrid[i][j] != 1) {
                    dp[i & 1][j] = dp[(i-1) & 1][j] + dp[i & 1][j-1];
                } else {
                    dp[i & 1][j] = 0;
                }
            }
        }

        //  Answer
        return dp[(rows-1) & 1][cols - 1];
    }
}

/**
 * Approach 3: Matrix DP (Optimized)
 * 进一步对空间复杂度的彻底优化
 * 核心公式为：dp[j] += dp[j - 1];
 * 其含义为：
 * dp[j] = dp[j] + dp[j - 1];
 * which is new dp[j] = old dp[j] + dp[j-1]
 * which is current cell = top cell + left cell
 * 至此我们可以发现，其核心仍然是相同的：当前点 = 正上方的点 + 左侧的点
 *
 * 时间复杂度：O(mn); 空间复杂度：O(n)
 */
public class Solution {
    /**
     * @param obstacleGrid: A list of lists of integers
     * @return: An integer
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int cols = obstacleGrid[0].length;
        int[] dp = new int[cols];
        dp[0] = 1;

        for (int[] row : obstacleGrid) {
            for (int j = 0; j < cols; j++) {
                if (row[j] == 1) {
                    dp[j] = 0;
                }
                else if (j > 0) {
                    dp[j] += dp[j - 1];
                }
            }
        }

        return dp[cols - 1];
    }
}