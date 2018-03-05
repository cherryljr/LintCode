/*
Description
Given an array of n integer, and a moving window(size k),
move the window at each iteration from the start of the array,
find the sum of the element inside the window at each moving.

Example
For array [1,2,7,8,5], moving window size k = 3.
1 + 2 + 7 = 10
2 + 7 + 8 = 17
7 + 8 + 5 = 20
return [10,17,20]

Tags
Amazon
 */

/**
 * Approach: Sliding Window
 * 基本的 Sliding Window 问题。
 * 首先求出 前k项 的和。其值就是 rst[0].
 * 然后开始 滑动窗口，即将 sum 减去 nums[i-k] 再加上 nums[i] 即可。
 * 因为每个数字只会被遍历一遍，因此时间复杂度为：O(n)
 */
public class Solution {
    /**
     * @param nums: a list of integers.
     * @param k: length of window.
     * @return: the sum of the element inside the window at each moving.
     */
    public int[] winSum(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }

        int sum = 0;
        int index = 0;
        int[] rst = new int[nums.length - k + 1];

        // Calculate the preSum of [0...k-1]
        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }
        rst[index++] = sum;
        // Slide the window
        for (int i = k; i < nums.length; i++) {
            sum -= nums[i - k];
            sum += nums[i];
            rst[index++] = sum;
        }

        return rst;
    }
}