相比与N Queens更加简单了。
因为只要求结果的个数，并不要求将其列出来。
故相当于少了drawChessBoard()这个方法。
解题思路与N Queens相同。

/*
Description:
Follow up for N-Queens problem.
Now, instead outputting board configurations, return the total number of distinct solutions.

Example
For n=4, there are 2 distinct solutions.

*/

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
