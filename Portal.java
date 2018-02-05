/*
Description
Chell is the protagonist of the Portal Video game series developed by Valve Corporation.
One day, She fell into a maze. The maze can be thought of as an array of 2D characters of size n x m.
It has 4 kinds of rooms. 'S' represents where Chell started(Only one starting point).
'E' represents the exit of the maze(When chell arrives, she will leave the maze, this question may have multiple exits).
'*' represents the room that Chell can pass. '#' represents a wall, Chell can not pass the wall.
She can spend a minute moving up,down,left and right to reach a room, but she can not reach the wall.
Now, can you tell me how much time she needs at least to leave the maze?
If she can not leave, return -1.

Notice
We guarantee that the size of the maze is n x m, and 1<=n<=200,1<=m<=200.
There is only one 'S', and one or more 'E'.

Example
Give
[
['S','E','*'],
['*','*','*'],
['*','*','*']
]
,return 1.

Explanation:
Chell spent one minute walking from (0,0) to (0,1).
Give
[
['S','#','#'],
['#','*','#'],
['#','*','*'],
['#','*','E']
]
,return -1.

Explanation:
Chell can not leave the maze.
Give
[
['S','*','E'],
['*','*','*'],
['#','*','*'],
['#','#','E']
]
,return 2.

Explanation:
First step: Chell move from (0,0) to (0,1).
Second step: Chell move from (0,1) to (0,2).
(Chell can also leave from (3,2), but it would take 5 minutes. So it's better to leave from (0,2).)
Give
[
['E','*','#'],
['#','*','#'],
['#','*','*'],
['#','#','S']
]
,return 5.

Explanation:
First step: Chell move from (0,0) to (0,1).
Second step: Chell move from (0,1) to (1,1).
Third step: Chell move from (1,1) to (2,1).
Fourth step: Chell move from (2,1) to (2,2).
Fifth step: Chell move from (2,2) to (3,2).
Tags
Breadth First Search
 */

/**
 * Approach: BFS
 * 二维数组求最短路径的方法。
 * 与 Maze 非常相似，想到使用 宽度优先遍历 的方法求图的最短路径。
 * 得益于 BFS 的特点（依次向外扩展，类似 infection，
 * 即：先走完所有一步能走到的点，再走完所有两步才能走到的点，然后是三步...依次类推...）
 * 所以我们第一个遇到的 出口 就是离我们最近的出口。
 *
 * 相似题目：The Maze II in LeetCode:
 * https://github.com/cherryljr/LeetCode/blob/master/The%20Maze%20II.java
 */
public class Solution {
    /**
     * @param Maze:
     * @return: nothing
     */
    public int Portal(char[][] Maze) {
        int rows = Maze.length, cols = Maze[0].length;
        int[][] distance = new int[rows][cols];

        // Initialize the distance array
        // And traverse the Maze to get the start position
        int[] start = new int[2];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                distance[i][j] = Integer.MAX_VALUE;
                if (Maze[i][j] == 'S') {
                    start[0] = i;
                    start[1] = j;
                }
            }
        }

        distance[start[0]][start[1]] = 0;
        // Using the direction array to make the code more concise
        int[][] dirs = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
        Queue<int[]> queue = new LinkedList<>();
        queue.add(start);
        while (!queue.isEmpty()) {
            int[] p = queue.poll();
            // Due to BFS, the first encounter exit has the shortest path
            if (Maze[p[0]][p[1]] == 'E') {
                return distance[p[0]][p[1]];
            }

            for (int[] dir : dirs) {
                int x = p[0] + dir[0];
                int y = p[1] + dir[1];

                if (x < 0 || x >= rows || y < 0 || y >= cols || Maze[x][y] == '#') {
                    continue;
                }
                if (distance[p[0]][p[1]] + 1 < distance[x][y]) {
                    distance[x][y] = distance[p[0]][p[1]] + 1;
                    queue.add(new int[] {x, y});
                }
            }
        }

        // if we can escape from the maze
        return -1;
    }
}