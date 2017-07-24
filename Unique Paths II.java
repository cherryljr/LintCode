矩阵动态规划题。
在Uninque Path的基础上增加了一个条件。
解法思路与之前的相同，只是增加了一个if语句用来判断：
若该坐标位置(x, y)上是否有障碍物：若没有按照原来方法处理，若有这将其路径值置为0

/*
Follow up for "Unique Paths":

Now consider if some obstacles are added to the grids. How many unique paths would there be?

An obstacle and empty space is marked as 1 and 0 respectively in the grid.

Note
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

Tags Expand 
Array Dynamic Programming

*/

public class Solution {
    /**
     * @param obstacleGrid: A list of lists of integers
     * @return: An integer
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        // write your code here
        if (obstacleGrid == null || obstacleGrid.length == 0 
                || obstacleGrid[0].length == 0) {
            return 0;
        }
        
        int row = obstacleGrid.length;
        int column = obstacleGrid[0].length;
        int[][] sum = new int[row][column];
        
        //  Initialize
        for (int i = 0; i < row; i++) {
            if (obstacleGrid[i][0] != 1) {
                sum[i][0] = 1;
            } else {
                break;
            }
        }
        for (int i = 0; i < column; i++) {
            if (obstacleGrid[0][i] != 1) {
                sum[0][i] = 1;
            } else {
                break;
            }
        }
        
        //  Function
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < column; j++) {
                if (obstacleGrid[i][j] != 1) {
                    sum[i][j] = sum[i - 1][j] + sum[i][j - 1];
                } else {
                    sum[i][j] = 0;
                }
            }
        }
        
        //  Answer
        return sum[row - 1][column - 1];
    }
}