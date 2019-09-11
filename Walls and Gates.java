/*
Description
You are given a m x n 2D grid initialized with these three possible values.
    -1 - A wall or an obstacle.
    0 - A gate.
    INF - Infinity means an empty room.
    We use the value 2^31 - 1 = 2147483647 to represent INF as you may assume that the distance to a gate is less than 2147483647.
Fill each empty room with the distance to its nearest gate. If it is impossible to reach a ROOM, that room should remain filled with INF

Example
Example1
Input:
[[2147483647,-1,0,2147483647],[2147483647,2147483647,2147483647,-1],[2147483647,-1,2147483647,-1],[0,-1,2147483647,2147483647]]
Output:
[[3,-1,0,1],[2,2,1,-1],[1,-1,2,-1],[0,-1,3,4]]
Explanation:
the 2D grid is:
INF  -1  0  INF
INF INF INF  -1
INF  -1 INF  -1
  0  -1 INF INF
the answer is:
  3  -1   0   1
  2   2   1  -1
  1  -1   2  -1
  0  -1   3   4

Example2
Input:
[[0,-1],[2147483647,2147483647]]
Output:
[[0,-1],[1,2]]
 */

/**
 * Approach: BFS
 * 求从门到达一个位置的最短路径。第一反应应该就是 BFS。
 * 并且这是一个多起点的 BFS 问题，不过写起来是一样的，遇到目的地直接将步数填入即可。
 * 
 * 时间复杂度：O(m*n)
 * 空间复杂度：O(m*n)
 * 
 * Reference:
 *  https://github.com/cherryljr/LeetCode/blob/master/Shortest%20Bridge.java
 */
public class Solution {
    private static final int INF = 2147483647;
    private static final int[][] DIRS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    /**
     * @param rooms: m x n 2D grid
     * @return: nothing
     */
    public void wallsAndGates(int[][] rooms) {
        if (rooms == null || rooms.length == 0) return;

        int m = rooms.length, n = rooms[0].length;
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (rooms[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                    visited[i][j] = true;
                }
            }
        }

        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                if (rooms[curr[0]][curr[1]] == INF) {
                    rooms[curr[0]][curr[1]] = step;
                }
                for (int[] dir : DIRS) {
                    int nextRow = curr[0] + dir[0], nextCol = curr[1] + dir[1];
                    if (nextRow < 0 || nextRow >= m || nextCol < 0 || nextCol >= n || rooms[nextRow][nextCol] == -1 || visited[nextRow][nextCol]) {
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