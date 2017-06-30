O(logn)
  画图分析  
  首先比较A[start] < A[mid]? 然后分成1,2两种情况    
  在 1 和 2 里面分别讨论 target 的位置     
      1. A[start] < A[mid] ?     
          说明在前半段    
          - start<target<mid     
          - target > mid      
      2. A[start] > A[mid]     
          说明 start 还在前半段，而mid在后半段     
          - mid < target < end     
          - target < mid     

/*
Suppose a sorted array is rotated at some pivot unknown to you beforehand.

(i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).

You are given a target value to search. If found in the array return its index, otherwise return -1.

You may assume no duplicate exists in the array.

Example
For [4, 5, 1, 2, 3] and target=1, return 2

For [4, 5, 1, 2, 3] and target=0, return -1

Tags Expand 
Binary Search Array Sorted Array
*/

public class Solution {
    public int search(int[] A, int target) {
        if (A == null || A.length == 0) {
            return -1;
        }

        int start = 0;
        int end = A.length - 1;
        int mid;
        
        while (start + 1 < end) {
            mid = start + (end - start) / 2;
            if (A[mid] == target) {
                return mid;
            }
            //	seperate the array into two section
            if (A[start] < A[mid]) {
                // situation 1, red line
                // target in the first section
                if (A[start] <= target && target <= A[mid]) {
                    end = mid;
                } else {
                    start = mid;
                }
            } else {
                // situation 2, green line
                // target in the second section
                if (A[mid] <= target && target <= A[end]) {
                    start = mid;
                } else {
                    end = mid;
                }
            }
        } 
        
        if (A[start] == target) {
            return start;
        }
        if (A[end] == target) {
            return end;
        }
        return -1;
    }
}
