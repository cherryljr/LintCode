/*
On a 2x3 board, there are 5 tiles represented by the integers 1 through 5, and an empty square represented by 0.
A move consists of choosing 0 and a 4-directionally adjacent number and swapping it.
The state of the board is solved if and only if the board is [[1,2,3],[4,5,0]].
Given a puzzle board, return the least number of moves required so that the state of the board is solved.
If it is impossible for the state of the board to be solved, return -1.

Examples:
Input: board = [[1,2,3],[4,0,5]]
Output: 1
Explanation: Swap the 0 and the 5 in one move.

Input: board = [[1,2,3],[5,4,0]]
Output: -1
Explanation: No number of moves will make the board solved.

Input: board = [[4,1,2],[5,0,3]]
Output: 5
Explanation: 5 is the smallest number of moves that solves the board.
An example path:
After move 0: [[4,1,2],[5,0,3]]
After move 1: [[4,1,2],[0,5,3]]
After move 2: [[0,1,2],[4,5,3]]
After move 3: [[1,0,2],[4,5,3]]
After move 4: [[1,2,0],[4,5,3]]
After move 5: [[1,2,3],[4,5,0]]
Input: board = [[3,2,4],[1,5,0]]
Output: 14

Note:
board will be a 2 x 3 array as described above.
board[i][j] will be a permutation of [0, 1, 2, 3, 4, 5].
 */

/**
 * Approach 1: BFS (General)
 * 本题其实就是八数码问题，给定一个可移动的数字，该数字每次只能朝四个方向移动，问最少经过多少次移动能够完成拼图（给定的序列）。
 * 因为求的是 最短需要移动的次数，因此想到使用 BFS 直接暴力求解出来。
 * （因为题目已经给定是：2*3 的 board,所以不用担心超时）
 * 这里先给出比较通用的版本，只要给定 rows 和 cols 即可。
 * 唯一需要注意的优化点就是使用了 String 来表示 board 的状态，不仅将其压缩到了一维，并且对于之后的比对查找也方便了不少。
 *
 * 时间复杂度为：O((m*n)!)
 * 空间复杂度为：O((m*n)!)
 *
 * 参考资料：https://www.youtube.com/watch?v=ABSjW0p3wsM
 */
class Solution {
    public int slidingPuzzle(int[][] board) {
        int rows = board.length, cols = board[0].length;
        StringBuilder startSB = new StringBuilder();
        StringBuilder goalSB = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                startSB.append(board[i][j]);
                goalSB.append((i * cols + j + 1) % (rows * cols));
            }
        }
        String start = startSB.toString();      // board的起始状态
        String goal = goalSB.toString();        // board的目标(结束)状态

        if (start.equals(goal)) {
            return 0;
        }
        Set<String> visited = new HashSet<>();  // 用于记录出现过的 board 情况
        Queue<String> queue = new LinkedList<>();
        queue.offer(start);
        visited.add(start);
        final int[][] DIRS = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        int step = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String curr = queue.poll();
                // 首先需要找到 '0' 的位置，然后才能进行 swap 操作
                int zeroPos = curr.indexOf('0');
                int x = zeroPos / cols;
                int y = zeroPos % cols;
                // 遍历当前位置'0'与周围相邻数字进行 swap 的情况
                for (int[] dir : DIRS) {
                    int nextX = x + dir[0];
                    int nextY = y + dir[1];
                    if (nextX < 0 || nextX >= rows || nextY < 0 || nextY >= cols) {
                        continue;
                    }
                    String nextBoard = swap(curr, zeroPos, nextX * cols + nextY);
                    // 如果该情况(board)已经出现过，则直接跳过
                    if (visited.contains(nextBoard)) {
                        continue;
                    }
                    // 如果得到了结果，直接return步数
                    if (nextBoard.equals(goal)) {
                        return step + 1;
                    }
                    visited.add(nextBoard);
                    queue.offer(nextBoard);
                }
            }
            step++;
        }

        return -1;
    }

    private String swap(String str, int i, int j) {
        char[] chars = str.toCharArray();
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
        return String.valueOf(chars);
    }
}

/**
 * Approach 2: BFS (Specific)
 * 因为题目中已经明确提出这是一个 2*3 的board,因此我们可以针对它进制准确性的优化。
 * 除了 Approach 1 中利用到了 String 来代表其 Board 的布局情况，这里还使用了其他几个优化点：
 *  1. board 最终状态明确，直接利用一个String goal 保存即可，无需拼接计算。
 *  2. 新建类型 Type 用于存储 BFS 时所需要的信息，包括 board 布局信息 和 当前'0'的位置
 *  避免了每次需要重新计算 '0' 的位置。
 *  3. 限定棋盘的大小为 2*3，因此 '0' 的位置只有6个，我们可以列举出所有其下一步可能移动到的位置。
 *  相比于 BFS 暴力尝试所有4个方向，这里可以实现计算好其可能得方向(邻居)，然后直接进行 swap 即可。
 *  不仅省去了换算坐标寻找邻居的过程，还直接排除了不可能的情况，降低了时间复杂度。
 *  下面为打表过程：（位置指的是字符串中'0'的位置，这样可以省去坐标换算的过程）
 *  当 '0' 在位置 0 时，其可以移动到右边和下边，即{1, 3}位置；
 *  在位置 1 时，其可以移动到左边，右边和下边，即{0, 2, 4}位置；
 *  在位置 2 时，其可以移动到左边和下边，即{1, 5}位置；
 *  在位置 3 时，其可以移动到上边和右边，即{0, 4}位置；
 *  在位置 4 时，其可以移动到左边，右边和上边，即{1, 3, 5}位置；
 *  在位置 5 时，其可以移动到上边和左边，即{2, 4}位置。
 *
 * 利用以上几点优化，虽然没能降低时间复杂度，但是将运行时间从 16ms 降低到了 14ms
 * 随着数据量提升将更加明显。当然这个做法针对性十分强，并且如果数据量太大，打表也还是很费时的。
 * 不过数据量再上去的话，就需要使用 A* 算法了，有兴趣的朋友可以搜索八数码问题哈。
 */
class Solution {
    public int slidingPuzzle(int[][] board) {
        int rows = board.length, cols = board[0].length;
        StringBuilder startSB = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                startSB.append(board[i][j]);
            }
        }
        String start = startSB.toString();
        String goal = "123450";     // 目标状态

        if (start.equals(goal)) {
            return 0;
        }
        Set<String> visited = new HashSet<>();  // 用于记录出现过的 board 情况
        Queue<Type> queue = new LinkedList<>();
        queue.offer(new Type(start, start.indexOf('0')));
        visited.add(start);
        // 事先计算好 '0' 在各个位置可能得邻居，然后直接打表
        int[][] dirs = new int[][]{{1,3}, {0,2,4}, {1,5}, {0,4}, {1,3,5}, {2,4}};
        int step = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Type curr = queue.poll();
                // 遍历当前位置'0'与周围相邻数字进行 swap 的情况
                for (int nextPosition : dirs[curr.position]) {
                    String nextBoard = swap(curr.board, curr.position, nextPosition);
                    // 如果该情况(board)已经出现过，则直接跳过
                    if (visited.contains(nextBoard)) {
                        continue;
                    }
                    // 如果得到了结果，直接return步数
                    if (nextBoard.equals(goal)) {
                        return step + 1;
                    }
                    visited.add(nextBoard);
                    queue.offer(new Type(nextBoard, nextPosition));
                }
            }
            step++;
        }

        return -1;
    }

    private String swap(String str, int i, int j) {
        char[] chars = str.toCharArray();
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
        return String.valueOf(chars);
    }

    class Type {
        String board;
        int position;   // '0'的位置

        Type(String board, int position) {
            this.board = board;
            this.position = position;
        }
    }
}