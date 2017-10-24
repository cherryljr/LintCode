分别进行两次Binary Search来查询left bound和right bound

/*
Given a sorted array of integers, find the starting and ending position of a given target value.

Your algorithm's runtime complexity must be in the order of O(log n).

If the target is not found in the array, return [-1, -1].

Example
Given [5, 7, 7, 8, 8, 10] and target value 8,
return [3, 4].

Tags Expand 
Binary Search Array Sorted Array
*/

/*
    input sorted
    2 binary search while loop.
    First one, keep looking for 1st occurnace.
    Second one, keep looking for alst occurance.
    
    check border case:
    A = []
*/

public class Solution {
    /** 
     *@param A : an integer sorted array
     *@param target :  an integer to be inserted
     *return : a list of length 2, [index1, index2]
     */
    public int[] searchRange(int[] A, int target) {
        // write your code here
        int[] rst = new int[2];
        if (A == null || A.length == 0) {
            rst[0] = -1;
            rst[1] = -1;
            return rst;
        }
        
        //  search for left bound
        int start = 0;
        int end = A.length - 1;
        while (start + 1 < end)  {
            int mid = start + (end - start) / 2;
            if (A[mid] == target) {
                end = mid;
            } else if (A[mid] < target) {
                start = mid;
            } else {
                end = mid;
            }
        }
        //  check A[start] first
        if (A[start] == target) {
            rst[0] = start;
        } else if (A[end] == target) {
            rst[0] = end;
        } else {
            rst[0] = rst[1]  = -1;
            return rst;
        }
        
        //  search for right bound
        start = 0;
        end = A.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (A[mid] == target) {
                start = mid;
            } else if (A[mid] < target) {
                start = mid;
            } else {
                end = mid;
            }
        }
        //  check A[end] first
        if (A[end] == target) {
            rst[1] = end;
        } else if (A[start] == target) {
            rst[1] = start;
        } else {
            rst[0] = rst[1] = -1;
            return rst;
        }
        
        return rst;
    }
}