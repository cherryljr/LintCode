/*
Description
Given a matrix size of n x m, element 1 represents policeman, -1 represents wall and 0 represents empty.
Now please output a matrix size of n x m, output the minimum distance between each empty space and the nearest policeman

Notice
Given a matrix size of n x m， n <= 200，m <= 200.
We guarantee that each empty space can be reached by one policeman at least.

Example
Given mat =
[
    [0, -1, 0],
    [0, 1, 1],
    [0, 0, 0]
]
return [[2,-1,1],[1,0,0],[2,1,1]].
The distance between the policeman and himself is 0,
the shortest distance between the two policemen to other empty space is as shown above

Given mat =
[
    [0, -1, -1],
    [0, -1, 1],
    [0, 0, 0]
]
return `[[5,-1,-1],[4,-1,0],[3,2,1]]`。
The shortest distance between the policemen to other 5 empty space is as shown above.

Tags
Google
 */

/**
 * Approach 1: BFS
 * 以 空地 为起点，警察的位置 为终点进行BFS。BFS的过程中计算距离即可。
 */
public class Solution {
    static final int[][] DIRS = new int[][]{{0, -1}, {0, 1}, {1, 0}, {-1, 0}};

    /**
     * @param matrix : the martix
     * @return: the distance of grid to the police
     */
    public int[][] policeDistance(int[][] matrix ) {
        if (matrix == null || matrix.length == 0) {
            return new int[][]{};
        }

        int rows = matrix.length, cols = matrix[0].length;
        int[][] rst = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // 如果是 空地 则开始BFS
                if (matrix[i][j] == 0) {
                    rst[i][j] = bfs(matrix, i, j, rows, cols);
                // 如果是 障碍物 直接是-1
                } else if (matrix[i][j] == -1) {
                    rst[i][j] = -1;
                // 如果是 警察位置 则是 0
                } else {
                    rst[i][j] = 0;
                }
            }
        }

        return rst;
    }

    // BFS Template
    private int bfs(int[][] matrix, int startRow, int startCol, int rows, int cols) {
        boolean[][] visited = new boolean[rows][cols];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startRow, startCol, 0});
        visited[startRow][startCol] = true;

        while (!queue.isEmpty()) {
            int[] currPos = queue.poll();
            if (matrix[currPos[0]][currPos[1]] == 1) {
                return currPos[2];
            }

            for (int[] dir : DIRS) {
                int row = currPos[0] + dir[0];
                int col = currPos[1] + dir[1];
                if (row < 0 || row >= rows || col < 0 || col >= cols
                        || visited[row][col] || matrix[row][col] == -1) {
                    continue;
                }
                queue.offer(new int[]{row, col, currPos[2] + 1});
            }
        }

        return -1;
    }
}

/**
 * Approach 2: BFS
 * 因为这是一道多起点的 BFS 问题（区别于 Portal 的单起点多终点）
 * 因此一开始我们就应该把所有 警察的位置 加入到队列中进行 BFS。
 * 即以 警察的位置 作为起点，空地 作为终点。
 * 这个做法相比与 Approach 1 中的 以空地作为起点的做法速度会快上不少。
 * 
 * Portal：https://github.com/cherryljr/LintCode/blob/master/Portal.java
 */
public class Solution {
    static final int[][] DIRS = new int[][]{{0, -1}, {0, 1}, {1, 0}, {-1, 0}};

    /**
     * @param matrix : the martix
     * @return: the distance of grid to the police
     */
    public int[][] policeDistance(int[][] matrix ) {
        if (matrix == null || matrix.length == 0) {
            return new int[][]{};
        }

        int rows = matrix.length, cols = matrix[0].length;
        int[][] rst = new int[rows][cols];
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rst[i][j] = Integer.MAX_VALUE;
                if (matrix[i][j] == 1) {
                    queue.offer(new int[]{i, j, 0});
                    rst[i][j] = 0;
                } else if (matrix[i][j] == -1) {
                    rst[i][j] = -1;
                }
            }
        }

        // Start BFS from the position of polices
        while (!queue.isEmpty()) {
            int[] currPos = queue.poll();
            for (int[] dir : DIRS) {
                int row = currPos[0] + dir[0];
                int col = currPos[1] + dir[1];
                if (row >= 0 && row < matrix.length && col >= 0 && col < matrix[0].length
                        && matrix[row][col] != -1 && currPos[2] + 1 < rst[row][col]) {
                    rst[row][col] = currPos[2] + 1;
                    queue.offer(new int[]{row, col, currPos[2] + 1});
                }
            }
        }

        return rst;
    }
}
