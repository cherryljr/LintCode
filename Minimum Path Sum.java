求最小路径和，属于Matrix DP
按照矩阵动态规划思路进行解题。
解法采用自顶向下的方法（Top to Buttom）.
首先这是一个二维数组，故对其进行初始化要包含矩阵的边缘。
State: sum[x][y]从起点到坐标(x, y)的最小路径和
Function: 同一时间内可向左或者向右移动一步， f[i][j] = Math.min(f[x - 1][y], f[x][y - 1]) + A[x][y]; 
Initialize:	有上述可得起点为左上角，并且这是一个二位数据，故：
						f[0][0] = A[0][0]
					  f[i][0] = sum(0,0 -> i,0)
					  f[0][i] = sum(0,0 -> 0,i)
Answer:	右下角为终点故为 sum[row - 1][column - 1].

/*
Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right which minimizes the sum of all numbers along its path.

Note
You can only move either down or right at any point in time.

Example
Tags Expand 
Dynamic Programming

*/


public class Solution {
    /**
     * @param grid: a list of lists of integers.
     * @return: An integer, minimizes the sum of all numbers along its path
     */
    public int minPathSum(int[][] grid) {
        // write your code here
        // 检查输入值是否为空
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        
        int row = grid.length;
        int column = grid[0].length;
        int[][] sum = new int[row][column];
        
        //  Initialize
        sum[0][0] = grid[0][0];
        for (int i = 1; i < row; i++) {
            sum[i][0] = sum[i - 1][0] + grid[i][0];
        }
        for (int i = 1; i < column; i++) {
            sum[0][i] = sum[0][i - 1] + grid[0][i];
        }
        
        //  Function
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < column; j++) {
                sum[i][j] = Math.min(sum[i - 1][j], sum[i][j - 1]) + grid[i][j];
            }
        }
        
        //  Answer
        return sum[row - 1][column - 1];
    }
}