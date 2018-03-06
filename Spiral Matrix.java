/*
Description
Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in spiral order.

Example
Given the following matrix:
[
 [ 1, 2, 3 ],
 [ 4, 5, 6 ],
 [ 7, 8, 9 ]
]
You should return [1,2,3,6,9,8,7,4,5].

Tags 
Array Matrix
*/

/**
 * Approach: Layer by Layer
 * 这道题目如果按一步步模拟的方法去解题，我们会发现，这个过程并不好处理。
 * 并且还会遇到需要处理的边界问题。
 * 因此我们可以 不再去纠结局部 的转换怎么实现，而是从题目的 整体 去入手。
 *
 * 就题目整体来看，我们发现其实质就是 一层层 从 外部 向 内部 遍历矩形 的这么一个过程。
 * 而当我们确定了 左上角 和 右下角 之后，整个矩形的边就可以被确定。
 * 然后我们只需要写一个 按照顺时针遍历矩形四条边 的这么个函数 printEdge 即可。
 * 这个函数就非常好实现了（注意处理好 边界条件 即可）
 * 然后处理完外侧的矩形后，左上角的点 和 右下角的点 向内层移动
 * 直到 左上角点的横纵坐标 超过 右小角点的横纵坐标
 */
class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return new ArrayList<>();
        }

        List<Integer> rst = new ArrayList<>();
        int tR = 0, tC = 0;
        int dR = matrix.length - 1, dC = matrix[0].length - 1;
        // 将当前由 左上角点(tR,tC) 和 右下角点(dR,dC) 所定位好的矩形的边上的数按顺序加入 rst 中
        // 处理完当前的矩形后， 左上角的点 和 右下角的点 向内层移动
        while (tR <= dR && tC <= dC) {
            printEdge(matrix, tR++, tC++, dR--, dC--, rst);
        }

        return rst;
    }

    private void printEdge(int[][] matrix, int tR, int tC, int dR, int dC, List<Integer> rst) {
        if (tR == dR) {
            // 当 左上角的点 与 右小角的点 在同一行的时候，将该行全部元素加入 rst 中
            for (int i = tC; i <= dC; i++) {
                rst.add(matrix[tR][i]);
            }
        } else if (tC == dC) {
            // 当 左上角的点 与 右小角的点 在同一列的时候，将该列元素全部加入 rst 中
            for (int i = tR; i <= dR; i++) {
                rst.add(matrix[i][tC]);
            }
        } else {
            // 按照顺时针的顺序将由 左上角 与 右下角 这两个点定位的矩形的 四条边 上的数
            // 按顺序全部加入到 rst 中（注意处理边界条件，这里我们使用了 前闭后开 的范围）
            int currR = tR, currC = tC;
            // 矩形上面的那条边，范围为 [左上角...右上角)
            while (currC < dC) {
                rst.add(matrix[tR][currC++]);
            }
            // 矩形右侧的那条边，范围为 [右上角...右下角)
            while (currR < dR) {
                rst.add(matrix[currR++][dC]);
            }
            // 矩形下面的那条边，范围为 [右下角...左下角)
            while (currC > tC) {
                rst.add(matrix[dR][currC--]);
            }
            // 矩形左侧的那条边，范围为 [左下角...左上角)
            while (currR > tR) {
                rst.add(matrix[currR--][tC]);
            }
        }
    }
}