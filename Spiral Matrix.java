利用 count 来判断螺旋的读取的圈数，
从而进行数学运算。
该题并没有涉及到过多的算法知识，只需要理解其中的数学关系即可。 

/*
Description
Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in spiral order.

Example
Given the following matrix:
[
 [ 1, 2, 3 ],
 [ 4, 5, 6 ],
 [ 7, 8, 9 ]
]
You should return [1,2,3,6,9,8,7,4,5].

Tags 
Array Matrix
*/

public class Solution {
    /*
     * @param matrix: a matrix of m x n elements
     * @return: an integer list
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> rst = new ArrayList<Integer>();
        if (matrix == null || matrix.length == 0) {
            return rst;   
        }
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        int count = 0;
        while (count * 2 < rows && count * 2 < cols) {
            // Left -> Right
            for(int i = count; i < cols-count; i++) {
                rst.add(matrix[count][i]);
            }
            // Up -> Down
            for(int i = count+1; i< rows-count; i++)
                rst.add(matrix[i][cols-count-1]);
            // if only one row /col remains
            if(rows - 2 * count == 1 || cols - 2 * count == 1)  
                break;
            // Left -> Right
            for(int i = cols-count-2; i>=count; i--)
                rst.add(matrix[rows-count-1][i]);
            // Down -> Up    
            for(int i = rows-count-2; i>= count+1; i--)
                rst.add(matrix[i][count]);
            
            count++;
        }
        
        return rst;
    }
}