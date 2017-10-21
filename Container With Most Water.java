Two Pointers
Algorithm:
The intuition behind this approach is that the area formed between the lines will always be limited by the height of the shorter line. 
Further, the farther the lines, the more will be the area obtained.
We take two pointers, one at the beginning and one at the end of the array constituting the length of the lines. 
Futher, we maintain a variable maxareamaxarea to store the maximum area obtained till now. 
At every step, we find out the area formed between them, update maxareamaxarea and move the pointer pointing to the shorter line towards the other end by one step.

How this approach works?
Initially we consider the area constituting the exterior most lines. 
Now, to maximize the area, we need to consider the area between the lines of larger lengths. 
If we try to move the pointer at the longer line inwards, we won't gain any increase in area, since it is limited by the shorter line. 
But moving the shorter line's pointer could turn out to be beneficial, as per the same argument, despite the reduction in the width. 
This is done since a relatively longer line obtained by moving the shorter line's pointer might overcome the reduction in area caused by the width reduction.

Complexity Analysis
Time  complexity : O(n). Single pass.
Space complexity : O(1). Constant space is used.

/*
Description
Given n non-negative integers a1, a2, ..., an, where each represents a point at coordinate (i, ai). 
n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0). 
Find two lines, which together with x-axis forms a container, such that the container contains the most water.

Notice
You may not slant the container.

Example
Given [1,3,2], the max area of the container is 2.

Tags 
Two Pointers Array
*/

public class Solution {
    /*
     * @param heights: a vector of integers
     * @return: an integer
     */
    public int maxArea(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }
        
        int maxArea = 0;
        int left = 0, right = heights.length - 1;
        while (left < right) {
            maxArea = Math.max(maxArea, Math.min(heights[left], heights[right]) * (right - left));
            if (heights[left] < heights[right]) {
                left++;
            } else {
                right--;
            }
        }
        
        return maxArea;
    }
}