/*
Description
Given a grid with n rows and m columns of binary integers, 0 or 1, and a set of rules, simulate k turns of the game on this grid.
The given grid denotes the initial configuration for the game,
where grid[i][j] = 1 denotes that cell in the ith row and jth column is alive, and grid[i][j] = 0 denotes that this cell is dead.
Two cells are neighbors when they share at least one common corner, thus each cell has at most 8 neighbors, as shown in the picture below:
There is a list of 9 rules indexed from 0 to 8, each rule having a value of either "alive" or "dead".
A single rule,i,specifies what happens to the cells with exactly i alive neighbors.
In each turn the new value of a cell is determined by counting the number of "alive" neighbors and applying the rule at index corresponding to the count.
The value corresponding to the rule is used to set new value in the cell
The "Live neughbors for each cell" grid at turn 1 contains the number of adjoining live cells at each grid position for turn 0.
It is used to create the new grid state for turn 1. At each cell with a value of 3 or 5,
the related cell in the new grid is set to alive.
All of the others are set to dead, The resultant grid is similarly analyzed for the second turn.
The returned 2-dimensional array contains the grid rows shown at turn 2 above.
    1<= n*m <= 10^3
    0 <= k <= 10^3
    rules[r] is either "dead" or "alive"(where 0 <= r < 9)

Clarification
Complete the function gridGame in the editor below. The function must return a 2-dimensional integer array.
gridGame has the following parameter(s):
grid[grid[]0[0],...grid[n-1][m-1]]: a 2D array of integers that denote the original grid
k: an integer that denotes the number of steps to perform
rules[rules[0],....rules[8]]: an array of strings that represent the rules of the game

Example:
input:
grid: [[0, 1, 1, 0], [1, 1, 0, 0]]
rules: ["dead", "dead", "dead", "alive", "dead", "alive", "dead", "dead", "dead"]
turn: 2
output:[[0, 1, 1, 0], [1, 1, 0, 0]]
Explanation：
turn 1:
because live neighbors grid:
[[3, 3, 2, 1],
 [2, 3, 3, 1]]
3 and 5 are alive, thus turn 1 grid is:
[[1, 1, 0, 0],
 [0, 1, 1, 0]]
turn 2:
live neighbors grid:
[[2, 3, 3, 1],
 [3, 3, 2, 1]]
thus turn 2 grid is
[[0, 1, 1, 0],
 [1, 1, 0, 0]]
 */

/**
 * Approach: Brute Force
 * 这道题目是很简单的，就是话太多...看着烦。
 * 这边简要说明一下：给出一个 grid[][] 每个位置均为 0 或者 1. 0代表死亡，1代表存活。
 * 给出一个 rules[]，rules[i]代表，如果一个点的周围存活的点的个数为 i，这该点在下一轮的状态为 rules[i] (死亡或者存活)
 * 第三个参数 k 代表经过 k 轮的更新，求最后 k 轮之后，各个节点的状态。
 *
 * 因为数据规模不大，所以解法很简单，暴力模拟 k 轮的更新即可。
 * 每轮更新之后，重新计算一次count数组。
 *
 * 时间复杂度：O(k*m*n)
 * 空间复杂度：O(m*n)
 */
public class Solution {
    int[][] DIRS = new int[][]{{-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}};

    /**
     * @param grid: a 2D array of integers
     * @param rules: an array of strings
     * @param k: an integer that denotes the number of steps to perform
     * @return: return a grid
     */
    public int[][] GridGame(int[][] grid, String[] rules, int k) {
        if (grid == null || grid.length == 0) {
            return grid;
        }
        int m = grid.length, n = grid[0].length;
        int[] alive = new int[rules.length];
        for (int i = 0; i < rules.length; i++) {
            alive[i] = rules[i].equals("alive") ? 1 : 0;
        }

        int[][] count = buildCountMatrix(grid);
        while (k-- > 0) {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    grid[i][j] = alive[count[i][j]];
                }
            }
            count = buildCountMatrix(grid);
        }
        return grid;
    }

    private int[][] buildCountMatrix(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] count = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int[] dir : DIRS) {
                    int nextR = i + dir[0], nextC = j + dir[1];
                    if (nextR < 0 || nextR >= m || nextC < 0 || nextC >= n) {
                        continue;
                    }
                    count[i][j] += grid[nextR][nextC];
                }
            }
        }
        return count;
    }
}