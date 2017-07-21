DFS的经典题目。
N皇后问题的结果必然在每行和每列上有且仅有一个皇后。
故这实质上就是一个排列问题。
所以我们利用了一个大小为N的数组来存储每行皇后的位置，（数组下标表示row，值表示column）
然后判断新加入的皇后是否会攻击到其他的皇后。
当该数组被填满，说明找到了一种解法。便将其加到result中。

两个皇后可以攻击到对方的三种情况如何判断：
	1. 获取每行皇后的位置，确保每列只有一个皇后
	2. 利用两个点的横纵坐标的和是否相等来判断这两点是否在同一斜线上 -- 斜率为 1
	3. 利用两个点的横纵坐标的差是否相等来判断这两点是否在同一斜线上 -- 斜率为 -1
/*
Description:
The n-queens puzzle is the problem of placing n queens on an n×n chessboard such that no two queens attack each other.
Given an integer n, return all distinct solutions to the n-queens puzzle.
Each solution contains a distinct board configuration of the n-queens' placement, where 'Q' and '.' 
both indicate a queen and an empty space respectively.

Example
There exist two distinct solutions to the 4-queens puzzle:

[
  // Solution 1
  [".Q..",
   "...Q",
   "Q...",
   "..Q."
  ],
  // Solution 2
  ["..Q.",
   "Q...",
   "...Q",
   ".Q.."
  ]
] 	

Challenge 
Can you do it without recursion?
*/

class Solution {
    /**
     * Get all distinct N-Queen solutions
     * @param n: The number of queens
     * @return: All distinct solutions
     * For example, A string '...Q' shows a queen on forth position
     */
    ArrayList<ArrayList<String>> solveNQueens(int n) {
        ArrayList<ArrayList<String>> results = new ArrayList<>();
        if (n <= 0) {
            return results;
        }
        
        search(results, new ArrayList<Integer>(), n);
        return results;
    }
    
    /*
     * results store all of the chessboards
     * cols store the column indices for each row
     */
    private void search(ArrayList<ArrayList<String>> results,
                        ArrayList<Integer> cols,
                        int n) {
        if (cols.size() == n) {
            results.add(drawChessboard(cols));
            return;
        }
        
        for (int colIndex = 0; colIndex < n; colIndex++) {
            if (!isValid(cols, colIndex)) {
                continue;
            }
            cols.add(colIndex);
            search(results, cols, n);
            cols.remove(cols.size() - 1);
        }
    }
    
    private ArrayList<String> drawChessboard(ArrayList<Integer> cols) {
        ArrayList<String> chessboard = new ArrayList<>();
        for (int i = 0; i < cols.size(); i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < cols.size(); j++) {
                sb.append(j == cols.get(i) ? 'Q' : '.');
            }
            chessboard.add(sb.toString());
        }
        return chessboard;
    }
    
    private boolean isValid(ArrayList<Integer> cols, int column) {
        int row = cols.size();
        for (int rowIndex = 0; rowIndex < cols.size(); rowIndex++) {
            if (cols.get(rowIndex) == column) {
                return false;
            }
            //	斜率为 1 -- 45度
            if (rowIndex + cols.get(rowIndex) == row + column) {
                return false;
            }
            //	斜率为 -1 -- 135度
            if (rowIndex - cols.get(rowIndex) == row - column) {
                return false;
            }
        }
        return true;
    }
}