/*
Description
Given an 01 matrix gird of size n*m, 1 is a wall, 0 is a road, now you can turn a 1 in the grid into 0,
Is there a way to go from the upper left corner to the lower right corner?
If there is a way to go, how many steps to take at least?

1≤n≤10^​3
1≤m≤10^​3
​​
Example
Given a = [[0,1,0,0,0],[0,0,0,1,0],[1,1,0,1,0],[1,1,1,1,0]]，return 7.
Explanation:
Change `1` at (0,1) to `0`, the shortest path is as follows:
(0,0)->(0,1)->(0,2)->(0,3)->(0,4)->(1,4)->(2,4)->(3,4) There are many other options of length `7`, not listed here.

Given a = [[0,1,1],[1,1,0],[1,1,0]], return -1.
Explanation:
Regardless of which `1` is changed to `0`, there is no viable path.
 */

/**
 * Approach: BFS
 * 求最短路径，因此可以想到使用 BFS 来解决这道问题。
 * 我们需要求：
 *  从 左上角 到 右下角 不经过障碍点的最短距离
 *  从 右下角 到 左上角 不经过障碍点的最短距离
 *  修改每个障碍点之后，到左上角和右上角的距离之和。
 * 然后在这些值中取最小值即可。
 * 
 * Note:
 *  本题的难点就是在于图的布局是可变的，但是我们不能对每个可变的点都进行一次 BFS.
 *  因为这样时间复杂度肯定会超时的，所以我们可以利用一个 matrix 来存储计算好的结果。
 *  也就是 空间换时间 的做法。
 *
 * 时间复杂度：O(MN)
 * 空间复杂度：O(MN)
 */
public class Solution {
    public static final int[][] DIRS = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

    /**
     * @param grid: The gird
     * @return: Return the steps you need at least
     */
    public int getBestRoad(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        // 各个障碍点到左上角顶点的距离(包括右下角顶点)
        int[][] disToUL = new int[rows][cols];
        // 各个障碍点到右下角顶点的距离(包括左上角顶点)
        int[][] disToLR = new int[rows][cols];
        bfs(disToUL, grid, new int[]{0, 0}, new int[]{rows - 1, cols - 1});
        bfs(disToLR, grid, new int[]{rows - 1, cols - 1}, new int[]{0, 0});

        int minDistance = Integer.MAX_VALUE;
        if (disToUL[rows - 1][cols - 1] != 0) {
            minDistance = Math.min(minDistance, disToUL[rows - 1][cols - 1]);
        }
        if (disToLR[0][0] != 0) {
            minDistance = Math.min(minDistance, disToLR[0][0]);
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1 && disToUL[i][j] != 0 && disToLR[i][j] != 0) {
                    minDistance = Math.min(minDistance, disToUL[i][j] + disToLR[i][j]);
                }
            }
        }

        return minDistance == Integer.MAX_VALUE ? -1 : minDistance;
    }

    private void bfs(int[][] distance, int[][] grid, int[] start, int[] end) {
        int rows = grid.length;
        int cols = grid[0].length;

        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];
        queue.offer(new int[]{start[0], start[1]});
        visited[start[0]][start[1]] = true;

        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                int row = curr[0], col = curr[1];
                // 如果当前顶点为 1，则可以进行一次修改使得当前顶点是可达的。然后 continue
                // 如果是结束的目标位置，同样需要更新步数（距离）值
                if (grid[row][col] == 1 || (row == end[0] && col == end[1])) {
                    distance[row][col] = step;
                    continue;
                }

                for (int[] dir : DIRS) {
                    int nextRow = row + dir[0];
                    int nextCol = col + dir[1];
                    if (nextRow < 0 || nextRow >= rows || nextCol < 0 || nextCol >= cols
                            || visited[nextRow][nextCol]) {
                        continue;
                    }
                    queue.offer(new int[]{nextRow, nextCol});
                    visited[nextRow][nextCol] = true;
                }
            }
            step++;
        }
    }
}
