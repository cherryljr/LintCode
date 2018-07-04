/*
Description
Given a matrix arr of size n*m, each position of the matrix has a positive or negative integer,
requiring a non-empty submatrix to be taken from the matrix to minimize the sum of it,
output the sum of the minimum submatrix.

Submatrix is not empty
1 ≤ n,m ≤ 2∗10^​2
-1∗10^​9 ​​≤ arr[i][j] ≤ 1∗10^​9
​​
Example
Given a = [[-3,-2,-1],[-2,3,-2],[-1,3,-1]]，return -7.
Explanation:
The upper-left corner of the submatrix is (0,0), and the lowerright corner is (1,2). The minimum sum is -7.

Given a = [[1,1,1],[1,1,1],[1,1,1]], return 1
Explanation:
All numbers are positive, but the submatrix cannot be empty, so we take the smallest one.
 */

/**
 * Approach: Convert into 1D SubArray and Kadane's Algorithm
 * 典型的 SubMatrix 求子矩阵和问题。
 * 首先求出 sum[][]，然后利用 Kadane's Algorithm 求最小子数组和的方法做就可以了。
 * 还有一道求 最大子矩阵和 的问题。做法基本相同，在 Kadane's 的地方改下就行。
 *
 * 时间复杂度：O(n^3)
 * 空间复杂度：O(n^2) 可以优化成 O(n)，但是这种比较水的题直接用模板过不好吗...
 *
 * 详细解析可以参考 Maximum Submatrix：
 *  https://github.com/cherryljr/LintCode/blob/master/Maximum%20Submatrix.java
 */
public class Solution {
    /**
     * @param arr: The given matrix
     * @return: Return the mininum sum
     */
    public long minimumSubmatrix(int[][] arr) {
        if (arr == null || arr.length == 0) {
            return 0L;
        }

        int rows = arr.length, cols = arr[0].length;
        long[][] sum = new long[rows + 1][cols + 1];
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                sum[i][j] = sum[i - 1][j] + sum[i][j - 1] + arr[i - 1][j - 1] - sum[i - 1][j - 1];
            }
        }

        long min = Long.MAX_VALUE;
        for (int top = 0; top <= rows; top++) {
            for (int down = top + 1; down <= rows; down++) {
                long subSum = 0;
                long maxSum = 0;
                for (int k = 1; k <= cols; k++) {
                    subSum = sum[down][k] - sum[top][k];
                    min = Math.min(min, subSum - maxSum);
                    maxSum = Math.max(maxSum, subSum);
                }
            }
        }
        return min;
    }
}