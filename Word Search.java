/*
Description
Given a 2D board and a word, find if the word exists in the grid.

The word can be constructed from letters of sequentially adjacent cell, 
where "adjacent" cells are those horizontally or vertically neighboring. 
The same letter cell may not be used more than once.


Example
Given board =

[
  "ABCE",
  "SFCS",
  "ADEE"
]
word = "ABCCED", -> returns true,
word = "SEE", -> returns true,
word = "ABCB", -> returns false.

Tags Expand 
Backtracking
*/

/**
 * Approach: DFS / Backtracking
 * 利用一个数组 visited 来标记已经被遍历过的元素。
 * 然后开始遍历整个二维字符数组，找到开头的字符后，进行 DFS :
 * 每遍历到一个字符，就将对应位置的 visited 置为 true.
 * 从一个字符出发，都可以朝四个方向走，只要有一个方向为 true 即可。
 * 当走的长度 index 与 word 长度相等时，则说明 word 能在字符表中被找到。
 *
 * Note: 记得回溯，即要将 visited 的值置回为 false.
 * 需要回溯的原因是：不能影响到以其他 路径 进行DFS时的情况。
 * 当前visited只是标记以当前路径进行遍历时，遍历过的元素。
 * 但是更换路径时，应该将已经遍历过的点的 visited 置回初始状态，以免对其他遍历过程产生影响。
 * （形象点说就是：相当于顺着原来走过来的足迹走回去并且把它们擦掉）
 * 本题中路径无法确定，并且每个点可能需要被多次遍历，所以需要进行 Backtracking, 类似的还有:
 * Subset: https://github.com/cherryljr/LintCode/blob/master/Subset.java
 * Permutations: https://github.com/cherryljr/LintCode/blob/master/Permutations%20II.java
 * 这些题目中的元素都可能被多次遍历到，因此都使用了 Backtracking 这个方法。
 * 而在 The Maze / Island 中，每个点总共只需要被遍历过一次即可（O(n))
 * 在这种情况下，就是纯粹的 DFS 问题（当然也可以使用 BFS 解决）,
 * 因为每个点只会被遍历一次，所以只要将被遍历过就将 visited[i][j] 置为 true 即可. 
 * 不需要回溯。（不存在影响到其他遍历过程的问题）
 *
 * 为了节省空间，我们可以省去 visited 数组，而直接对原来的 board[][] 进行修改，
 * 来区分是否被访问过。如:将被访问过的元素置为 '#'.
 */
class Solution {
    // Marked the element that has been visited when DFS the board.
    // static boolean[][] visited;

    /**
     * @param board: A list of lists of character
     * @param word: A string
     * @return: A boolean
     */
    public boolean exist(char[][] board, String word) {
        if (board == null || board.length == 0) {
            return false;
        }
        if (word == null || word.length() == 0) {
            return true;
        }

        // visited = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int  j = 0; j < board[0].length; j++) {
                // If the dfs's result it true, it means that we can find the word in board.
                if (dfs(board, i, j, word, 0)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean dfs(char[][] board, int i, int j, String word, int index) {
        if (index == word.length()) {
            return true;
        }
        // if (i < 0 || i >= board.length || j < 0 || j >= board[i].length || board[i][j] != word.charAt(index) || visited[i][j]) {
        //     return false;
        // }
        if (i < 0 || i >= board.length || j < 0 || j >= board[i].length || board[i][j] != word.charAt(index)) {
            return false;
        }

        // visited[i][j] = true;
        char temp = board[i][j];
        board[i][j] = '#';
        // 采用方向数组进行 dfs 个人还是非常青睐这个写法的。
        // 该写法更加简洁便利，但是运行效率比直接写出各个方向的遍历函数要低一些
        // 大家可根据情况自行取舍,当然如果在 DFS 前加入条件判断效率就会提升不少了，请灵活变通哦(●'◡'●)
        // 具体应用可以参见: The Maze 系列问题
        // int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1 }};
        // for (int[] dir : dirs) {
        //     if (dfs(board, i + dir[0], j + dir[1], word, index + 1)) {
        //         return true;
        //     }
        // }
        // go to the four directions, check whether we can find the next character of the word or not.
        if (dfs(board, i-1, j, word, index+1)
                || dfs(board, i+1, j, word, index+1)
                || dfs(board, i, j-1, word, index+1)
                || dfs(board, i, j+1, word, index+1)) {
            return true;
        }
        // Backtracking
        // visited[i][j] = false;
        board[i][j] = temp;

        return false;
    }
}

