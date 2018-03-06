/*
Description
Given an integer n, generate a square matrix filled with elements from 1 to n^2 in spiral order.

Example
Given n = 3,

You should return the following matrix:
[
  [ 1, 2, 3 ],
  [ 8, 9, 4 ],
  [ 7, 6, 5 ]
]

Tags 
Array
*/

/**
 * Approach: Layer by Layer
 * 处理方法与 Spiral Matrix 相同，由 整体 出发，
 * 从 最外部的矩形 开始处理，一层层向内。
 * 至于需要在每个位置添加的数 num,我们只需要通过一个全局变量 count
 * 来进行维护即可。每当添加了一个位置的数，count 加1 即可。
 * 
 * 更详细的解析可以参考：
 * 
 */
class Solution {
    int count = 1;

    public int[][] generateMatrix(int n) {
        if (n <= 0) {
            return new int[][]{};
        }

        int[][] matrix = new int[n][n];
        int tR = 0, tC = 0;
        int dR = n - 1, dC = n - 1;
        while (tR <= dR && tC <= dC) {
            addEdge(matrix, tR++, tC++, dR--, dC--);
        }

        return matrix;
    }

    private void addEdge(int[][] matrix, int tR, int tC, int dR, int dC) {
        if (tR == dR) {
            // 当 左上角的点 与 右小角的点 在同一行的时候
            for (int i = tC; i <= dC; i++) {
                matrix[tR][i] = count++;
            }
        } else if (tC == dC) {
            // 当 左上角的点 与 右小角的点 在同一列的时候
            for (int i = tR; i <= dR; i++) {
                matrix[i][tC] = count++;
            }
        } else {
            int currR = tR, currC = tC;
            // 矩形上面的那条边，范围为 [左上角...右上角)
            while (currC < dC) {
                matrix[tR][currC++] = count++;
            }
            // 矩形右侧的那条边，范围为 [右上角...右下角)
            while (currR < dR) {
                matrix[currR++][dC] = count++;
            }
            // 矩形下面的那条边，范围为 [右下角...左下角)
            while (currC > tC) {
                matrix[dR][currC--] = count++;
            }
            // 矩形左侧的那条边，范围为 [左下角...左上角)
            while (currR > tR) {
                matrix[currR--][tC] = count++;
            }
        }
    }
}