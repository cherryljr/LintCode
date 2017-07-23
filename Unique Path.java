求路径的个数，DP问题。
State: sum[x][y]从起点到坐标(x, y)的不同路径总数
Function: 同一时间内可向左或者向右移动一步， f[x][y] = f[x - 1][y] + f[x][y - 1]; 
Initialize:	由题可得起点为左上角，并且这是一个二维数组，可得由起点到矩阵左上侧边缘各点的路径均只有一条故：
						f[0][0] = 1
					  f[i][0] = 1
					  f[0][i] = 1
Answer:	右下角为终点故为 sum[m - 1][n - 1].


/*
A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).

The robot can only move either down or right at any point in time. 
The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).

How many possible unique paths are there?

Note
m and n will be at most 100.

Example
1,1     1,2     1,3     1,4     1,5     1,6     1,7     
2,1
3,1                                             3,7

Above is a 3 x 7 grid. How many possible unique paths are there?

Tags Expand 
Array Dynamic Programming

*/

public class Solution {
    /**
     * @param n, m: positive integer (1 <= n ,m <= 100)
     * @return an integer
     */
    public int uniquePaths(int m, int n) {
        // write your code here 
        if (m == 0 || n == 0) {
            return 1;
        }
        
        int[][] sum = new int[m][n];
        
        // Initialize
        sum[0][0] = 1;
        for (int i = 1; i < m; i++) {
            sum[i][0] = 1;
        }
        for (int i = 1; i < n; i++) {
            sum[0][i] = 1;
        }
        
        //  Function
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                sum[i][j] = sum[i - 1][j] + sum[i][j - 1];
            }
        }
        
        //  Answer
        return sum[m - 1][n - 1];
    }
}


