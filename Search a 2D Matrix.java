/*
Description
Write an efficient algorithm that searches for a value in an m x n matrix.

This matrix has the following properties:
    Integers in each row are sorted from left to right.
    The first integer of each row is greater than the last integer of the previous row.

Example
Consider the following matrix:
[
    [1, 3, 5, 7],
    [10, 11, 16, 20],
    [23, 30, 34, 50]
]
Given target = 3, return true.

Challenge
O(log(n) + log(m)) time

Tags
Binary Search Matrix Yahoo
 */

/**
 * Approach 1: Binary Search Twice
 * Firstly, using binary search in rows, find the ith row that the last number <= target
 * Secondly, using binary search in cols, find the target number exists or not.
 */
public class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }
        if (matrix[0] == null || matrix[0].length == 0) {
            return false;
        }

        int row = matrix.length;
        int column = matrix[0].length;

        // find the row index, the last number <= target
        int start = 0, end = row - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (matrix[mid][0] == target) {
                return true;
            } else if (matrix[mid][0] < target) {
                start = mid;
            } else {
                end = mid;
            }
        }
        if (matrix[end][0] <= target) {
            row = end;
        } else if (matrix[start][0] <= target) {
            row = start;
        } else {
            return false;
        }

        // find the column index, the number equal to target
        start = 0;
        end = column - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (matrix[row][mid] == target) {
                return true;
            } else if (matrix[row][mid] < target) {
                start = mid;
            } else {
                end = mid;
            }
        }
        if (matrix[row][start] == target) {
            return true;
        } else if (matrix[row][end] == target) {
            return true;
        }
        return false;
    }
}

/**
 * Approach 2: Binary Search (Only Once)
 * We can Convert into a 1D array firstly, 
 * Then use BinarySearch only once.
 */
public class Solution {
    /*
     * @param matrix: matrix, a list of lists of integers
     * @param target: An integer
     * @return: a boolean, indicate whether matrix contains target
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }
        if (matrix[0] == null || matrix[0].length == 0) {
            return false;
        }

        int rows = matrix.length, cols = matrix[0].length;
        // Convert the matrix into a 1D Array
        int left = 0, right = rows * cols - 1;
        int row, col;
        while (left < right) {
            int mid = left + ((right - left) >> 2);
            //	use mid / cols to calculate the position in  row
            // 	use mid % cols to calculate the position in column
            row = mid / cols;
            col = mid % cols;
            if (target <= matrix[row][col]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        row = left / cols;
        col = left % cols;
        if (matrix[row][col] == target) {
            return true;
        } else {
            return false;
        }
    }
}