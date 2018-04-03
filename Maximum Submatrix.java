/*
Description
Given an n x n matrix of positive and negative integers, find the submatrix with the largest possible sum.

Example
Given matrix =
[
[1,3,-1],
[2,3,-2],
[-1,-2,-3]
]
return 9.

Explanation:
the submatrix with the largest possible sum is:
[
[1,2],
[2,3]
]

Tags
Two Pointers Matrix
 */

/**
 * Approach: Convert into 1D SubArray and  Kadane's Algorithm
 * 这道题目的考点在于是否能够将 Matrix 这个二维的问题转换到 一维 上面。
 * 首先，我们知道对于一个 数组 而言，求其 最大连续子数组之和 我们可以使用 Kadane's Algorithm.
 *
 * 那么对于一个矩阵而言，我们应该怎么做呢？
 * 与 Submatrix Sum 问题相同，我们可以学习这道题目的做法：
 *  1. 首先建立sum矩阵，为 n＋1 行，m＋1列。并将第0行和第0列都初始化为0。
 *  sum[i][j] 表示 matrix[0][0] 到 matrix[i-1][j-1] 所有元素的和。也就是 子矩阵和。
 *  2. 遍历matrix，根据公式 sum[i][j] = matrix[i - 1][j - 1] + sum[i][j - 1] + sum[i - 1][j] -sum[i - 1][j - 1] 计算所有sum。
 *  3. 取两个row：top, down。
 *  然后用两条竖线 k 从左到右扫过top和down，由 上边界top 下边界down 左边界k-1 以及 右边界k 共同围成一个子矩阵，
 *  而我们可以把这个 子矩阵 看作是一维数组中的 一个元素。
 *  那么我们就将一个 二维空间 上的问题转换到了 一维空间 上了。
 *  4. 因为问题实际已经转换成了 Maximum Subarray 问题了，使用 Kadane's Algorithm 即可求解。
 *
 * 时间复杂度：
 *  因为我们需要遍历所有的 高度区间 （top和down之间的部分），时间复杂度为 O(n^2)
 *  Kadane's Algorithm 时间复杂度为 O(n)
 *  因此总体时间复杂度为：O(n^3)
 *
 * 对于 Kadane's Algorithm 不了解的可以参考：
 * Maximum Subarray：https://github.com/cherryljr/LintCode/blob/master/Maximum%20Subarray.java
 */
public class Solution {
    /**
     * @param matrix: the given matrix
     * @return: the largest possible sum
     */
    public int maxSubmatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }

        int rows = matrix.length, cols = matrix[0].length;
        // 考虑到 矩阵端点 在边界上的情况，我们建立 sum 时，比 matrix 多上一行一列即可
        int[][] sum = new int[rows + 1][cols + 1];
        // Calculate the sum matrix
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                sum[i][j] = matrix[i - 1][j - 1] + sum[i - 1][j] + sum[i][j - 1] - sum[i - 1][j - 1];
            }
        }

        int max = 0;
        // 子矩阵上边界
        for (int top = 0; top <= rows; top++) {
            // 子矩阵下边界
            for (int down = top + 1; down <= rows; down++) {
                // Kadane's Algorithm
                int subSum = 0;
                int minSum = 0;
                for (int k = 1; k <= cols; k++) {
                    int temp = sum[down][k] - sum[top][k] - (sum[down][k - 1] - sum[top][k - 1]);
                    subSum += temp;
                    max = Math.max(max, subSum - minSum);
                    minSum = Math.min(minSum, subSum);
                }
            }
        }

        return max;
    }
}
