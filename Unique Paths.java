/*
Description
A robot is located at the top-left corner of a m x n grid.
The robot can only move either down or right at any point in time. 
The robot is trying to reach the bottom-right corner of the grid.

How many possible unique paths are there?

Notice
m and n will be at most 100.

Example
Given m = 3 and n = 3, return 6.
Given m = 4 and n = 5, return 35.

Tags 
Dynamic Programming Array
 */

/**
 * Approach 1: Matrix DP
 * 非常基础的一道 二维DP 问题。
 * 设 dp[i][j] 为从起点到坐标(i, j)的不同路径总数
 * 那么 dp[i][j] 的值为 起点到其正上方点的路径总数 + 起点到其左侧点的路径总数.
 * 即 Function 为：dp[i][j] = dp[i - 1][j] + dp[i][j - 1]
 * Initialize:	由题可得起点为左上角，并且这是一个二维数组，可得由起点到矩阵左上侧边缘各点的路径均只有一条故：
 * dp[0][0] = 1; dp[i][0] = 1; dp[0][i] = 1
 * Answer:	右下角为终点故为 dp[m][n].
 * 
 * 时间复杂度：O(mn); 空间复杂度：O(mn)
 */
class Solution {
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m + 1][n + 1];

        // Initialize
        for (int i = 1; i <= m; i++) {
            dp[i][1] = 1;
        }
        for (int i = 1; i <= n; i++) {
            dp[1][i] = 1;
        }

        // Function
        for (int i = 2; i <= m; i++) {
            for (int j = 2; j <= n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }

        return dp[m][n];
    }
}

/**
 * Approach 2: Matrix DP (Optimized by Sliding Array)
 * 由 Approach 1 中的分析可知：
 * dp[i][j] 的值仅仅由 起点到其正上方点的路径总数(dp[i-1][j]) 和 起点到其左侧点的路径总数(dp[i][j-1])
 * 这两参数决定。因此我们可以只记录 (i-1)th 行的结果 与 (i, j) 左侧的结果即可。
 * 因此只需要借助 滚动数组 来存储两行的的结果就够用了（虽然还有会那么一点点的浪费，但是已经节省了非常大的空间了）
 * 至于 滚动数组 如何实现？利用数组下标进行 取余 操作即可。
 * 整体代码几乎没有修改的地方，并且十分便于理解。
 *
 * 时间复杂度：O(mn); 空间复杂度：O(n)
 */
class Solution {
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[2][n];

        // Initialize
        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }
        dp[1][0] = 1;

        // Function
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i & 1][j] = dp[(i-1) & 1][j] + dp[i & 1][j - 1];
            }
        }

        // Answer
        return dp[(m-1) & 1][n - 1];
    }
}

/**
 * Approach 3: Using Combination Formula
 * Besides DP method, it also has a very smart method -- using combination formula.
 * 
 * First of all you should understand that we need to do n + m - 2 movements :
 * m - 1 down, n - 1 right, because we start from cell (1, 1).
 * Secondly, the path it is the sequence of movements( go down / go right),
 * therefore we can say that two paths are different
 * when there is i-th (1 .. m + n - 2) movement in path1 differ i-th movement in path2.
 * 
 * So, how we can build paths.
 * Let's choose (n - 1) movements(number of steps to the right) from (m + n - 2),
 * and rest (m - 1) is (number of steps down).
 * 
 * I think now it is obvious that count of different paths are all combinations (n - 1) movements from (m + n-2).
 * 
 * Time Complexity: O(n); Space Complexity: O(1)
 */
class Solution {
    public int uniquePaths(int m, int n) {
        int N = n + m - 2; // how much steps we need to do
        int k = m - 1; // number of steps that need to go down
        double res = 1;
        // here we calculate the total possible path number
        // Combination(N, k) = n! / (k!(n - k)!)
        // reduce the numerator and denominator and get
        // C = ( (n - k + 1) * (n - k + 2) * ... * n ) / k!
        for (int i = 1; i <= k; i++) {
            res *= (double)(N - k + i) / (double)i;
        }

        return (int)Math.round(res);
    }
}

