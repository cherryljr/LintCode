/*
Description
Given n x m non-negative integers representing an elevation map 2d where the area of each cell is 1 x 1,
compute how much water it is able to trap after raining.

Example
Given 5*4 matrix
[12,13,0,12]
[13,4,13,12]
[13,8,10,12]
[12,13,12,12]
[13,13,13,13]

return 14.

Tags
LintCode Copyright Heap Matrix
 */

/**
 * Approach: Heap / PriorityQueue
 * 该题是 Trapping Rain Water 的一个 Follow Up.
 * 为了更好地理解该题，我们先回顾一下 Trapping Rain Water 中 Two Pointers 的解法。
 * 我们有 left, right 两个指针分别指向 数组的起始与末尾，
 * 并维持两个参数 leftMax 与 rightMax 分别代表当前的 左侧最高高度 与 右侧最高高度。（初始值为0）
 * 然后比较 height[left] 与 height[right], 看哪个更低，根据木桶原理（水位高度由短板决定）
 * 我们就从更低的那一侧进行灌水，（假设当前左侧高度比右侧低）
 *  那么如果左侧高度比 leftMax 低，说明能够把水灌进去。灌水的高度为：leftMax - height[left]，然后 left 指针向右移动一位。
 *  如果 左侧高度 比 leftMax 高，说明无法把水灌进去，则更新 leftMax 即可。（对于 right 操作基本类似）
 *  然后继续比较 height[left] 与 height[right]， 直至 left 与 right 相遇。
 * 以上就是 Trapping Rain Water 的解法。
 *
 * 那么在本题中，问题由原来的 一维 转换成了 二维。
 * 但是问题突破口不变，我们仍然可以从 最外层的最低水位高度 开始向内部灌水。
 * 相同的，完成该步灌水操作后，该点向内移动一位（从 heap 中移除该点 并将灌水操作后的水位值 add 到 heap 中），
 * 然后重新计算 外侧的最低水位高度（找短板）。
 * 这个时候，我们遇到了一个问题，在 Trapping Rain Water 中，因为是 一维 的，总共就两个值，
 * 我们直接比较即可。但是这里我们需要比较 外侧的所有水位高度。
 * 并且随着灌水过程的向内推进我们的 外侧水位墙 是不规则的。
 * 如果采取遍历所有值的方法，我们还需要记录其所有的外侧点，并且将花费 O(n) 的时间复杂度。
 * 为了解决这个问题，我们使用了 最小堆 这个数据结构来帮助我们快速获得 外侧水位 中的最小值，时间复杂度为 O(logn).
 *
 * 时间复杂度分析：
 *  利用 PriorityQueue 获得 外侧水位的最小值 时间复杂度为 O(log(m + n)),而我们需要遍历整个 [m, n] 的数组，
 *  因此总体时间复杂为：O(m * n * log(m + n))
 */
public class Solution {
    class Cell implements Comparable<Cell> {
        int row;
        int col;
        int height;

        Cell(int row, int col, int height) {
            this.row = row;
            this.col = col;
            this.height = height;
        }

        @Override
        public int compareTo(Cell other) {
            return this.height - other.height;
        }
    }

    /**
     * @param heights: a matrix of integers
     * @return: an integer
     */
    public int trapRainWater(int[][] heights) {
        if (heights == null || heights.length <= 2 || heights[0].length <= 2) {
            return 0;
        }

        PriorityQueue<Cell> pq = new PriorityQueue<>();
        int rows = heights.length, cols = heights[0].length;
        boolean[][] visited = new boolean[rows][cols];

        // 先将最外围一圈的水位信息放入到优先队列中，并在 visited 中将对应的值置为true
        for (int i = 0; i < cols; i++) {
            visited[0][i] = true;
            pq.offer(new Cell(0, i, heights[0][i]));
            visited[rows - 1][i] = true;
            pq.offer(new Cell(rows - 1, i, heights[rows - 1][i]));
        }
        for (int i = 1; i < rows - 1; i++) {
            visited[i][0] = true;
            pq.offer(new Cell(i, 0, heights[i][0]));
            visited[i][cols - 1] = true;
            pq.offer(new Cell(i, cols - 1, heights[i][cols - 1]));
        }

        int rst = 0;
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        // 从最外层边界开始，选择水位最低的点开始灌水，检测该点 四周 的所有相邻点（未被遍历过的），
        // 如果相邻点水位更低，说明可以成功灌水。可以被 Trap 的水位为：currCell.height - heights[row][col]
        // 然后将该相邻点的水位更新为 currCell.height. visited 状态置为 true. 并将其 add 到 minHeap 中，完成向内移动。
        // 这样一次灌水操作完成。
        // 如果相邻点水位更高，说明无法成功灌水。直接将该点的 visited 状态置为 true, 然后 add 到 minHeap 中，
        // 同样也完成了向内移动，灌水操作完成。就这样利用类似 BFS 的操作，对所有的相邻点都进行一次灌水操作即可。
        while (!pq.isEmpty()) {
            Cell currCell = pq.poll();
            for (int[] dir : dirs) {
                int nextRow = currCell.row + dir[0];
                int nextCol = currCell.col + dir[1];
                // 如果 超出边界 或者 该点已经被遍历过 则直接跳过
                if (nextRow < 0 || nextRow >= rows || nextCol < 0 || nextCol >= cols || visited[nextRow][nextCol]) {
                    continue;
                }
                visited[nextRow][nextCol] = true;
                // 只有当 currCell 的水位更高，才能向相邻点灌水，即能 Trap 到水
                if (currCell.height > heights[nextRow][nextCol]) {
                    rst += currCell.height - heights[nextRow][nextCol];
                    // 成功灌水后，相邻点的水位也变成了 currCell.height
                    heights[nextRow][nextCol] = currCell.height;
                }
                // 完成向内移动
                pq.offer(new Cell(nextRow, nextCol, Math.max(currCell.height, heights[nextRow][nextCol])));
            }
        }

        return rst;
    }
}