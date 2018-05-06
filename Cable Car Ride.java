/*
Description
When Xiao Jiu came to a place to ride a cable car, he could only ride a cable car once,
so he wanted to extend the cable car as much as possible.
It is known that the cable car station distribution can be seen as an n x m matrix,
each grid point representing the height of the cable car station.
He can start a cable car from any station.
The cable car can only move from a low altitude to a high altitude, taking 1unit of time.
The cable car can move in eight directions. (up and down, left, right, top left, top right, bottom left, bottom right).
Q. How long can Xiao Jiu ride the cable car?

Notice
1 <= n, m <= 20。
The height of the cable car station entered does not exceed 100000.

Example
Given mat =
[
    [1,2,3],
    [4,5,6],
    [7,8,9]
]
return 7.
Explanation:
1→2→3→5→7→8→9 This route is the longest.

Given mat =
[
    [1,2,3],
    [6,5,4],
    [7,8,9]
]
return 9.
Explanation:
1→2→3→4→5→6→7→8→9 This route is the longest.

Tags
Depth First Search
 */

/**
 * Approach: BFS
 * 虽然这道题目 Tag 是 DFS，但是这道题目使用 BFS 毫无疑问要比 DFS 优美不少
 * 基础的 BFS 应用，不多说啥，直接看代码吧
 * 
 * DFS版本可以自行改写...这里就不写了
 */
public class Solution {
    /**
     * @param height: the Cable car station height
     * @return: the longest time that he can ride
     */
    public int cableCarRide(int[][] height) {
        if (height == null || height.length == 0 || height[0] == null || height[0].length == 0) {
            return 0;
        }

        int rows = height.length,  cols = height[0].length;
        int maxStep = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maxStep = Math.max(maxStep, bfs(i, j, height));
            }
        }
        return maxStep;
    }

    private int bfs(int row, int col, int[][] height) {
        final int[][] DIRS = new int[][]{{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
        Queue<Point> queue = new LinkedList<>();
        queue.offer(new Point(row, col, height[row][col], 1));
        int maxStep = 0;

        while (!queue.isEmpty()) {
            Point curr = queue.poll();
            for (int[] dir : DIRS) {
                int nextRow = curr.row + dir[0];
                int nextCol = curr.col + dir[1];
                if (nextRow < 0 || nextRow >= height.length || nextCol < 0
                        || nextCol >= height[0].length || height[nextRow][nextCol] <= height[curr.row][curr.col]) {
                    continue;
                }
                maxStep = Math.max(maxStep, curr.step + 1);
                queue.offer(new Point(nextRow, nextCol, height[nextRow][nextCol], curr.step + 1));
            }
        }
        return maxStep;
    }

    class Point {
        int row, col;
        int height, step;

        public Point(int row, int col, int height, int step) {
            this.row = row;
            this.col = col;
            this.height = height;
            this.step = step;
        }
    }
}