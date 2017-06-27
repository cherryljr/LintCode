/*
Suppose a sorted array is rotated at some pivot unknown to you beforehand.

(i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).

Find the minimum element.

You may assume no duplicate exists in the array.

Example
Given [4,5,6,7,0,1,2] return 0

Tags Expand 
Binary Search

Thinking process:
Understand how to use binary in this problem: compare the mid point with end point.
In this problem, because the sorted line is cut at one point then rotate, so one of the line is absolutely greater than the other line.
Situation 1:
if mid < end :  that means minimum is on the end point's line. Move end to left. end = mid.
Situation 2:
if mid > end: that means there must be a mountain-jump somewhere after mid and before end, which is the minimum point. Now move start to mid.
*/

public class Solution {
    /**
     * @param nums: a rotated sorted array
     * @return: the minimum number in the array
     */
    public int findMin(int[] nums) {
        // write your code here
        if (nums == null || nums.length == 0) {
            return -1;
        }
        
        int start = 0, end = nums.length - 1;
        
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] > nums[end]) {
                start = mid;
            } else {
                end = mid;
            }
        }
        
        if (nums[start] < nums[end]) {
            return nums[start];
        } else {
            return nums[end];
        }
    }
}

