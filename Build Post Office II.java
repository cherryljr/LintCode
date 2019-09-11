/*
Description
Given a 2D grid, each cell is either a wall 2, an house 1 or empty 0 (the number zero, one, two),
find a place to build a post office so that the sum of the distance from the post office to all the houses is smallest.

Return the smallest sum of distance. Return -1 if it is not possible.
You cannot pass through wall and house, but can pass through empty.
You only build post office on an empty.

Example
Example 1:
Input：[[0,1,0,0,0],[1,0,0,2,1],[0,1,0,0,0]]
Output：8
Explanation： Placing a post office at (1,1), the distance that post office to all the house sum is smallest.

Example 2:
Input：[[0,1,0],[1,0,1],[0,1,0]]
Output：4
Explanation： Placing a post office at (1,1), the distance that post office to all the house sum is smallest.
 */

/**
 * Approach: BFS
 * 本以为这个问题会有一个很漂亮的解法，结果并没有。因为引入了 墙 的存在，并且路线无法从房子穿过，使得本题无法适用 曼哈顿距离。
 * 因此，本题的解法实际上是相当暴力的 BFS。通过 BFS 计算出所有空地到所有 house 的最短距离之和，然后取最小值即可。
 * 注意无法穿过房子，所以到达房子之后不能将房子的邻居放入队列中。
 * 其次，本题使用的依旧是 出队列时判断更新 的做法，如果想要稍微加快下速度可以使用 入队列时判断更新 的做法。
 *
 * 时间复杂度：O((m*n)^2)
 * 空间复杂度：O(m*n)
 *
 * Reference:
 *  https://github.com/cherryljr/LintCode/blob/master/Matrix%20Water%20Injection.java
 */
public class Solution {
    private static final int[][] DIRS = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

    /**
     * @param grid: a 2D grid
     * @return: An integer
     */
    public int shortestDistance(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return -1;
        }

        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 0) {
                    ans = Math.min(ans, bfs(grid, i, j));
                }
            }
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    private int bfs(int[][] grid, int row, int col) {
        int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{row, col});
        visited[row][col] = true;

        int step = 0, sum = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                if (grid[curr[0]][curr[1]] == 1) {
                    sum += step;
                    continue;
                }

                for (int[] dir : DIRS) {
                    int nextR = curr[0] + dir[0], nextC = curr[1] + dir[1];
                    if (nextR < 0 || nextR >= m || nextC < 0 || nextC >= n || visited[nextR][nextC] || grid[nextR][nextC] == 2) {
                        continue;
                    }
                    queue.offer(new int[]{nextR, nextC});
                    visited[nextR][nextC] = true;
                }
            }
            step++;
        }

        // 如果在该位置建立邮局存在无法到达的house，则返回Integer.MAX_VALUE
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    return Integer.MAX_VALUE;
                }
            }
        }
        return sum;
    }
}
