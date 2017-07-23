相比与N Queens更加简单了。
因为只要求结果的个数，并不要求将其列出来。
故相当于少了drawChessBoard()这个方法。
解题思路与N Queens相同。
故我们有了解法1：使用DFS，递归遍历得到结果。

同时我们发现这题只需要解的方案个数，并不需要我们将具体的方案列出来，
故我们可以使用动态规划来解决该问题。
因此我们有了解法2：DP

/*
Description:
Follow up for N-Queens problem.
Now, instead outputting board configurations, return the total number of distinct solutions.

Example
For n=4, there are 2 distinct solutions.

*/

//	Version 1: DFS Traverse
class Solution {
    /**
     * Get all distinct N-Queen solutions
     * @param n: The number of queens
     * @return: All distinct solutions
     * For example, A string '...Q' shows a queen on forth position
     */
    int result = 0;
    
    public int totalNQueens(int n) {
        //write your code here
        if (n <= 0) {
            return result;
        }
        
        helper(new ArrayList<Integer>(), n);
        return result;
    }
    
    private void helper(ArrayList<Integer> list,int n)  {
        if (list.size() == n) {
             result++;
             return;
        }
        
        for (int i = 0; i < n; i++) {
            if (!isValid(list, i)) { 
                continue;
            }
            
            list.add(i);
            helper(list, n);
            list.remove(list.size() - 1);
        }
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


//	Version 2: DP
public class Solution {
    public static int sum;
    public int totalNQueens(int n) {
        sum = 0;
        int[] usedColumns = new int[n];
        placeQueen(usedColumns, 0);
        return sum;
    }
    public void placeQueen(int[] usedColumns, int row) {
        int n = usedColumns.length;
        
        if (row == n) {
            sum ++;
            return;
        }
        
        for (int i = 0; i < n; i++) {
            if (isValid(usedColumns, row, i)) {
                usedColumns[row] = i;
                placeQueen(usedColumns, row + 1);
            }
        }
    }
    public boolean isValid(int[] usedColumns, int row, int col) {
        for (int i = 0; i < row; i++) {
            if (usedColumns[i] == col) {
                return false;
            }
            if ((row - i) == Math.abs(col-usedColumns[i])) {
                return false;
            }
        }
        return true;
    }
}