/*
Description
Given an integer matrix, find a submatrix where the sum of numbers is zero.
Your code should return the coordinate of the left-up and right-down number.

Example
Given matrix
[
  [1 ,5 ,7],
  [3 ,7 ,-8],
  [4 ,-8 ,9],
]
return [(1,1), (2,2)]

Challenge
O(n3) time.

Tags
Enumeration Matrix
 */

/**
 * Approach: PreSum + HashMap
 * 这道题和 求和为0的子数组（Subarray Sum） 的解决方法一样。属于 Fellow Up.
 * https://github.com/cherryljr/LintCode/blob/master/Subarray%20Sum.java
 * 在数组中求的是前i个元素和前j个元素和相等，则 [i...j] 子数组和为0，而这里只是变成 2维 的而已。
 * 而我们需要做的就是将 二维 转换到 一维 进行解决。
 * 同样整体思路仍然是：求出每个以 left-up 和 right-down 围成的 subMatrix,答案必在这里面。
 * 然后利用 sum[][] 和 HashMap 来对时间复杂度进行优化即可。
 *
 * 具体做法：
 *  1. 建立sum矩阵，为 n＋1 行，m＋1列。 并将第0行和第0列都初始化为0。
 *  sum[i][j] 表示 matrix[0][0] 到 matrix[i-1][j-1] 所有元素的和。也就是 子矩阵和。
 *  2. 遍历matrix，根据公式 sum[i][j] = matrix[i - 1][j - 1] + sum[i][j - 1] + sum[i - 1][j] -sum[i - 1][j - 1] 计算所有sum。
 *  3. 取两个row：top, down。
 *  然后用一条竖线 k 从左到右扫过top和down，由 上边界top 下边界down 左边界0 以及 右边界k 共同围成一个子矩阵
 *      其和为：preSum = sum[top][k] - sum[down][k]
 *  到这里，我们其实已经将一个 二维空间 上的问题转换到了 一维空间 上了。
 *  如果在同一个 top 和 down 中（上下边界确定），有两条线（k1，k2）的 preSum 相等，
 *  则表示 top-down 和 k1-k2 这个矩形中的元素和为0。
 *  因此利用 Subarray Sum 的方法就能够解决了。
 *  
 * 时间复杂度：O(n^3)
 * 
 * 同样考察将 二维矩阵的子矩阵问题 转换传成 一维数组子数组 问题的还有：
 * 
 */

class Solution {
    /*
     * @param matrix: an integer matrix
     * @return: the coordinate of the left-up and right-down number
     */
    public int[][] submatrixSum(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return null;
        }

        int[][] rst = new int[2][2];
        int rows = matrix.length, cols = matrix[0].length;
        // 考虑到 矩阵端点 在边界上的情况，我们建立 sum 时，比 matrix 多上一行一列即可
        int[][] sum = new int[rows + 1][cols + 1];

        // Calculate the sum matrix
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                sum[i][j] = matrix[i - 1][j - 1] + sum[i - 1][j] + sum[i][j - 1] - sum[i - 1][j - 1];
            }
        }

        // 子矩阵上边界
        for (int top = 0; top < rows; top++) {
            // 子矩阵下边界
            for (int down = top + 1; down <= rows; down++) {
                // key: the sum of subMatrix
                // value: the column of node
                Map<Integer, Integer> map = new HashMap<>();
                // 因为 sum[][] 的原因，我们不再需要推入 (0, -1) 到 map 中
                int preSum = 0;
                // k 为子矩阵右边界
                for (int k = 0; k <= cols; k++) {
                    preSum = sum[down][k] - sum[top][k];
                    if (map.containsKey(preSum)) {
                        rst[0][0] = top;
                        rst[0][1] = map.get(preSum);
                        rst[1][0] = down - 1;
                        rst[1][1] = k - 1;
                        return rst;
                    } else {
                        map.put(preSum, k);
                    }
                }
            }
        }

        return rst;
    }
}