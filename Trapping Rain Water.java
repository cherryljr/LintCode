/*
Description
Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it is able to trap after raining.

Trapping Rain Water
image here: http://www.lintcode.com/en/problem/trapping-rain-water/

Example
Given [0,1,0,2,1,0,1,3,2,1,2,1], return 6.

Challenge 
O(n) time and O(1) memory
O(n) time and O(n) memory is also acceptable.

Tags 
Two Pointers Forward-Backward Traversal Array
*/

// Approach 1: Using Stack 
This problem is like Largest Rectangle in Histogram to some extend. 
But, we use stack to find the first bigger number beside it in the arrays in this problem.
This difference between this problem and Largest Rectangle in Histogram is:
In Largest Rectangle in Histogram, we use stack to find the first smaller element beside it.
    Because we want to find a rectangle whose height is heights[stack.peek()]. 
    When we know this position that the height is smaller than it, we can calculate the weight of the rectangle.
In this quesiotn - Trapping Rain Water, we use stack to find the first bigger element beside it.
    Because only when the beside heights is taller than the height[stack.peek()], the water could be trapped.
    Just like a Basin.
    When we know this position that the height is bigger than it, we can calculate the weight of the rectangle.
    And the height is: Math.min(heights[i], heights[stack.peek()]) - heights[top].

Algorithm
	Use stack to store the indices of the bars.
	Iterate the array:
		While stack is not empty and heights[current] > heights[stack.top()]
		It means that the stack element can be popped. Pop the top element as top.
		Find the distance between the current element and the element at top of stack, which is to be filled. width = current − stack.peek() − 1
		Find the bounded heights: bounded_heights = Math.min(heights[current], heights[stack.peek()]) − heights[top]
		Add resulting trapped water to answer ans += width * bounded_heights
	Push current index to top of the stack
	Move current to the next position.
Complexity analysis
	Time  complexity: O(n).
		Single iteration of O(n) in which each bar can be touched at most twice(due to insertion and deletion from stack) 
		and insertion and deletion from stack takes O(1) time.
	Space complexity: O(n). 
		Stack can take upto O(n) space in case of stairs-like or flat structure.

// Code Below
public class Solution {
    /*
     * @param heights: a list of integers
     * @return: a integer
     */
    public int trapRainWater(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }
        
        int ans = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < heights.length; i++) {
            while (!stack.empty() && heights[i] > heights[stack.peek()]) {
                int top = stack.pop();
				// if stack is empty, then it can't trap water.
                if (stack.empty()) {
                    continue;
                }
                int width = i - stack.peek() - 1;
                int height = Math.min(heights[i], heights[stack.peek()]) - heights[top];
                ans += width * height;
            }
            stack.push(i);
        }
        
        return ans;
    }
}

// Approach 2: Two Pointers
Algorithm
	Initialize left pointer to 0 and right pointer to size-1
	While left < right, do:
		If height[left] is smaller than height[right]
			If height[left] >= left_max, update left_max
			Else add left_max−height[left] to ans
			move left to the next position (left++).
		Else
			If height[right] >= right_max, update right_maxright_max
			Else add right_max−height[right] to ans
			move right to the next position (right--).
Complexity analysis
	Time  complexity: O(n). Single iteration of O(n).
	Space complexity: O(1). Only constant space required for left, right, left_max and right_max.

// Code Below
public class Solution {
    /*
     * @param heights: a list of integers
     * @return: a integer
     */
    public int trapRainWater(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }
        
        int left = 0, right = heights.length - 1;
        int left_max = 0, right_max = 0;
        int ans = 0;
        while (left < right) {
            if (heights[left] < heights[right]) {
                if (heights[left] >= left_max) {
                    left_max = heights[left];
                } else {
                    ans += left_max - heights[left];
                }
                left++;
            } else {
                if (heights[right] >= right_max) {
                    right_max = heights[right];
                } else{
                    ans += right_max - heights[right];
                }
                right--;
            }
        }
        
        return ans;
    }
}


















