/*
Description
Given a matrix of m x n elements (m rows, n columns),
return all elements of the matrix in ZigZag-order.

Example
Given a matrix:
[
  [1, 2,  3,  4],
  [5, 6,  7,  8],
  [9,10, 11, 12]
]
return [1, 2, 5, 9, 6, 3, 4, 7, 10, 11, 8, 12]

Tags
LintCode Copyright Matrix
 */

/**
 * Approach: Level by Level (Diagonal Traversal)
 * 这种题目如果纠结于局部怎么实现点的转换，通常会比较复杂，很折磨人。
 * 因此解决此类题目我们通常需要将目光放到题目 全局 上来。
 *
 * 我们可以发现数据的遍历是按照 "之" 字形来遍历的，而每次同一条线上的数
 * 均在 一个小矩形 的 对角线上。如:
 *      1  2  3  4  5  6
 *      2  3  4  5  6  7
 *      3  4  5  6  7  8
 *      4  5  6  7  8  9
 *      5  6  7  8  9  10
 * 所有 2, 3, 4... 均在同一条对角线上，而这也是我们所需要遍历的。
 * 因此我们只需要不断地移动 对角线的 两个端点，就能找到我们单次所需要遍历的数。
 * 并且每次遍历之后，换个方向即可。
 * 对于 对角线的两个端点，我们可以使用 A， B 来确定。
 * 每次 A 向右移动 1 步，B向下移动 1 步。
 * 当 A 移动到 最右端(行的末尾) 时，开始向下移动，依旧每次移动 1 步；
 * 当 B 移动到 最下端(列的末尾) 时，开始向右移动，依旧每次移动 1 步；
 * 这样我们每次需要遍历的数就是 A，B 连线上的数，只是遍历方向不同罢了。
 * 当 A 或者 B 到达 矩阵的右下角时，结束遍历。
 */
class Solution {
    /**
     * @param matrix: An array of integers
     * @return: An array of integers
     */
    public int[] printZMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return new int[]{};
        }

        List<Integer> list = new ArrayList<>();
        int aR = 0, aC = 0;
        int bR = 0, bC = 0;
        int endR = matrix.length - 1;
        int endC = matrix[0].length - 1;
        boolean fromBottom = true;
        while (aR <= endR) {
            printLevel(matrix, aR, aC, bR, bC, fromBottom, list);
            aR = aC == endC ? aR + 1 : aR;
            aC = aC == endC ? aC : aC + 1;
            bC = bR == endR ? bC + 1 : bC;
            bR = bR == endR ? bR : bR + 1;
            fromBottom = !fromBottom;
        }

        int[] rst = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            rst[i] = list.get(i);
        }
        return rst;
    }

    private void printLevel(int[][] matrix, int aR, int aC, int bR, int bC, boolean fromBottom, List<Integer> list) {
        if (fromBottom) {
            while (bR >= aR) {
                list.add(matrix[bR--][bC++]);
            }
        } else {
            while (aR <= bR) {
                list.add(matrix[aR++][aC--]);
            }
        }
    }
}