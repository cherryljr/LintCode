因为是递增矩阵（行递增 && 列递增）
故根据给定的性质，从左下角开始向右上角查询：每次有任何情况，只能往一个方向运行。
每次删掉一行，或者一列
故算法的时间复杂度为:O(m + n)， 空间复杂度为：O(1)
```
/*
Write an efficient algorithm that searches for a value in an m x n matrix, return the occurrence of it.

This matrix has the following properties:

    * Integers in each row are sorted from left to right.

    * Integers in each column are sorted from up to bottom.

    * No duplicate integers in each row or column.

Example
Consider the following matrix:

[

    [1, 3, 5, 7],

    [2, 4, 7, 8],

    [3, 5, 9, 10]

]

Given target = 3, return 2.

Challenge
O(m+n) time and O(1) extra space

*/

/*
    Understand that:
    1. Each row has exactly 1 instance of that integer, no duplicates.
    2. Each row begins with smallest number. So, if matrix[x][y] < target, first thing to do is y++.
    3. Each colum ends with largest number. So if matrix[x][y] > target, 
        (no need to care x++ since it's alreay too large for this row), then simply just x--.
    4. If match, next step will be x--,y++. 
        x-- because it has to change a row; 
        y++ because [x-1, y] can't be the target since no duplicate in column
    Beneftis of going from bottown-left: No matter which condition, always have 1 possible way to move.
*/

public class Solution {
    /**
     * @param matrix: A list of lists of integers
     * @param: A number you want to search in the matrix
     * @return: An integer indicate the occurrence of target in the given matrix
     */
    public int searchMatrix(int[][] matrix, int target) {
        // check corner case
        if (matrix == null || matrix.length == 0) {
            return 0;
        }
        if (matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }
        
        // from bottom left to top right
        int n = matrix.length;
        int m = matrix[0].length;
        int x = n - 1;
        int y = 0;
        int count = 0;
        
        while (x >= 0 && y < m) {
            if (matrix[x][y] < target) {
                y++;
            } else if (matrix[x][y] > target) {
                x--;
            } else {
                count++;
                x--;
                y++;
            }
        }
        return count;
    }
}



```