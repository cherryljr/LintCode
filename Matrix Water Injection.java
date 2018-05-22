/*
Description
Given a two-dimensional matrix, the value of each grid represents the height of the terrain.
The flow of water will only flow up, down, right and left, and it must flow from the high ground to the low ground.
As the matrix is surrounded by water, it is now filled with water from (R,C) and asked if water can flow out of the matrix.

The input matrix size is n x n, n <= 200.
Ensure that each height is a positive integer.

Example
Given
mat =
[
    [10,18,13],
    [9,8,7],
    [1,2,3]
]
R = 1, C = 1, return "YES"。

Explanation:
(1,1) →(1,2)→Outflow.

Given
mat =
[
    [10,18,13],
    [9,7,8],
    [1,11,3]
]
R = 1, C = 1, return "NO"。

Explanation:
Since (1,1) cannot flow to any other grid, it cannot flow out.
 */

/**
 * 以本题为例，这里提供了 两种 BFS 做法的的模板
 *  第一种为：在 出队列 的时候进行 结束条件 判断；
 *  第二种为：在 进队列 的时候进行 结束条件 判断。
 * 其本质就是对 结束条件 的 判断时机 不同罢了。
 * 具体的分析与比较可以看下面的详细说明。
 *
 * 这里就本题在使用 BFS 过程中没有涉及到的一些注意点进行一个补充说明：
 *  1. BFS 过程中如要要求 level by level，即 BFS 过程中需要将一层的对象进行处理的话。
 *  我们需要在一轮 BFS 开始之前首先获得当前 队列的长度size()，这就代表了当前层的大小与其相对应的元素，
 *  然后按照 size 的大小进行处理，之所以这么做是因为 循环中queue.size()是动态的，故必须在for循环之前就取好。
 *  当然了，我们也会经常根据 level 来记录相应的 step 信息。
 *  应用示例可以参考：
 *  Binary Tree Level Order Traversal (easy):
 *      https://github.com/cherryljr/LintCode/blob/master/Binary%20Tree%20Level%20Order%20Traversal.java
 *  Bus Routes (hard):
 *      https://github.com/cherryljr/LeetCode/blob/master/Bus%20Routes.java
 *  2. 依据 BFS 遍历过程中的特性(level by level)，其经常被用于求最短路径。
 *  并且有时候我们会进行反向 BFS 来节省时间。（多终点，需要求每个点到终点的最短路径）
 *  应用示例可以参考：
 *  Police Distance：
 *      https://github.com/cherryljr/LintCode/blob/master/Police%20Distance.java
 *  Portal：
 *      https://github.com/cherryljr/LintCode/blob/master/Portal.java
 */

/**
 * Approach 1: BFS
 * 该做法是在 出队列 的时候判断结束，因此在入队列的时候是不需要进行结束条件判断的。
 * 相比于在 入队 时进行结束条件的判断，该写法的代码更加简洁美观一些。
 * 并且不需要对 起始点 进行条件判断。
 * 因此在不追求极致效率的情况下，很多人都会选择这种写法。
 */
public class Solution {
    /**
     * @param matrix: the height matrix
     * @param R: the row of (R,C)
     * @param C: the columns of (R,C)
     * @return: Whether the water can flow outside
     */
    public String waterInjection(int[][] matrix, int R, int C) {
        int rows = matrix.length, cols = matrix[0].length;
        // BFS 的遍历方向，我们可以利用数组存储。
        final int[][] DIRS = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        // 使用队列进行 BFS
        Queue<int[]> queue = new LinkedList<>();
        // 使用一个数据结构来记录已经遍历过的位置（有时候我们也可以通过修改原数据从而达到辨识的目的）
        // 但是修改原数据在实际工程中并不是一个好的做法
        boolean[][] visited = new boolean[rows][cols];
        // 在队列中加入 起始点的信息 并将其记录到 visited 中
        queue.offer(new int[]{R, C});
        visited[R][C] = true;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            // 出队列时进行判断是否符合条件可以结束 BFS
            if (curr[0] == 0 || curr[0] == rows - 1 || curr[1] == 0 || curr[1] == cols - 1) {
                return "YES";
            }

            // 利用 方向数组 进行 BFS 遍历
            for (int[] dir : DIRS) {
                int nextX = curr[0] + dir[0];
                int nextY = curr[1] + dir[1];
                // 如果 超出边界 或者 不符合条件（已经放问过等）则跳过该位置
                if (nextX < 0 || nextX >= rows || nextY < 0 || nextY >= cols || visited[nextX][nextY]) {
                    continue;
                }
                if (matrix[nextX][nextY] < matrix[curr[0]][curr[1]]) {
                    // 将符合条件的位置加入到队列中等待下一轮的 BFS
                    queue.offer(new int[]{nextX, nextY});
                    visited[nextX][nextY] = true;
                }
            }
        }

        return "NO";
    }
}

/**
 * Approach 2: BFS
 * 该方法是在 进队列 的时候判断是否可以结束 BFS，
 * 因此对于 所有 进队列的元素都要进行条件判断才行。
 * 包括起始点（初始值），这个很容易被忘记，请务必要注意！
 * 以该题为例，如果我们没有对 起始点 进行判断是否在边界上的话，
 * 我们将其加入 Queue 之后，我们不会再对其进行判断了，这就导致我们漏掉了一个解的情况。
 * 其他部分与 Approach 1 完全相同，参考上述的注释即可。
 *
 * 相比于 出队列 时进行判断，该做法的优点在于：
 *  在进队列时直接判断是否能够结束BFS，节省了后续其他不必要的 入队 和 出队 操作。
 *  当数据量较大或者较为特殊时，可以节省不少时间。
 *  应用实例可以参考：
 *  Bus Routes: https://github.com/cherryljr/LeetCode/blob/master/Bus%20Routes.java
 */
public class Solution {
    /**
     * @param matrix: the height matrix
     * @param R: the row of (R,C)
     * @param C: the columns of (R,C)
     * @return: Whether the water can flow outside
     */
    public String waterInjection(int[][] matrix, int R, int C) {
        int rows = matrix.length, cols = matrix[0].length;
        // 判断起始点是否符合条件，如果在边界上可以直接 return 无需 BFS
        if (R == 0 || R == rows - 1 || C == 0 || C == cols - 1) {
            return "YES";
        }

        final int[][] DIRS = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];
        queue.offer(new int[]{R, C});
        visited[R][C] = true;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            for (int[] dir : DIRS) {
                int nextX = curr[0] + dir[0];
                int nextY = curr[1] + dir[1];
                if (nextX < 0 || nextX >= rows || nextY < 0 || nextY >= cols || visited[nextX][nextY]) {
                    continue;
                }
                if (matrix[nextX][nextY] < matrix[curr[0]][curr[1]]) {
                    if (nextX == 0 || nextX == rows - 1 || nextY == 0 || nextY == cols - 1) {
                        return "YES";
                    }
                    queue.offer(new int[]{nextX, nextY});
                    visited[nextX][nextY] = true;
                }
            }
        }

        return "NO";
    }
}