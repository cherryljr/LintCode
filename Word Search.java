Backtracking方法1:  基本依据Template的写法.
用一个boolean visited[][] 来标记是否访问过。

首先遍历整个二维字符数组，找到开头的字符后，进行 DFS : 每到一个字符，就将对应位置的 visited 置为 true.
从一个字符出发，都可以朝四个方向走，只要有一个方向为 true 即可。当走的长度 index 与 word 长度相等时，
则说明 word 能在字符表中被找到。
Note: 记得回溯，即要将 visited 的值置回为 false.

Backtracking方法2:
在原来的基础上进行了改进，节约了 visited 数组的空间。
方法:每次到一个字母，将其值置为特殊字符 ‘#’, 表示已经 check 过它.
4个path recurse回来后，将board[i][j]的值恢复成原来的,即 Backtracking.
总而言之就是这里 board 也充当了方法1中 visited 数组的作用。

/*
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

//DFS: search through the board, going to different directions, while also increasing index. when index == word.length, that's end.
//use visited[][] to mark visited places.

public class Solution {
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
        
        int height = board.length; 
        int width = board[0].length;
        boolean[][] visited = new boolean[height][width];
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (board[i][j] == word.charAt(0)) {
                    boolean rst = dfs(board, word, i, j, visited, 0);
                    if (rst) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    private boolean dfs(char[][] board, String word, int i, int j, 
                        boolean[][] visited, int index) {
        int height = board.length;
        int width = board[0].length;
        if (index == word.length()) {
            return true;
        }
        if (i < 0 || i >= height || j < 0 || j >= width 
            || board[i][j] != word.charAt(index) || visited[i][j]) {
            return false;    
        }
        
        int[] move_i = {1, -1, 0, 0};
        int[] move_j = {0, 0, 1, -1};
        visited[i][j] = true;
        for (int k = 0; k < 4; k++) {
            int next_i = i + move_i[k];
            int next_j = j + move_j[k];
            if (dfs(board, word, next_i, next_j, visited, index + 1)) {
                return true;
            }
        }
        // Backtracking
        visited[i][j] = false;
        
        return false;
    }
}

// Optimized, Save space N^2
public class Solution {
    public boolean exist(char[][] board, String word) {
        if (word == null || word.length() == 0) {
            return true;
        }
        if (board == null || board.length == 0) {
            return false;
        }
        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == word.charAt(0)) {
                    boolean rst = search(board, word, i, j, 0);
                    if (rst) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean search(char[][] board, String word, int i, int j, int start) {
        if (start == word.length()) {
            return true;
        }
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] != word.charAt(start)) {
            return false;
        }
        
        board[i][j] = '#';
        boolean rst = search(board, word, i, j - 1, start + 1)
        || search(board, word, i, j + 1, start + 1)
        || search(board, word, i + 1, j, start + 1)
        || search(board, word, i - 1, j, start + 1);   
        // Backtracking
        board[i][j] = word.charAt(start);
        return rst;
    }
}



