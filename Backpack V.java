/*
Description
Given n items with size nums[i] which an integer array and all positive numbers. 
An integer target denotes the size of a backpack. Find the number of possible fill the backpack.
Each item may only be used once

Example
Given candidate items [1,2,3,3,7] and target 7,

A solution set is: 
[7]
[1, 3, 3]
return 2

Tags 
Dynamic Programming
 */

/**
 * Approach: 0-1 Backpack
 * 与牛客网上 数组和为sum的方法数 一摸一样
 * 详细解析可以参考：
 * https://github.com/cherryljr/NowCoder/blob/master/%E6%95%B0%E7%BB%84%E5%92%8C%E4%B8%BAsum%E7%9A%84%E6%96%B9%E6%B3%95%E6%95%B0.java
 */
public class Solution {
    /**
     * @param nums: an integer array and all positive numbers
     * @param target: An integer
     * @return: An integer
     */
    public int backPackV(int[] nums, int target) {
        int[] dp = new int[target + 1];
        // Initialize
        dp[0] = 1;
        if (nums[0] <= target) {
            dp[nums[0]] = 1;
        }

        // Function
        for (int i = 1; i < nums.length; i++) {
            for (int j = target; j >= nums[i]; j--) {
                dp[j] += dp[j - nums[i]];
            }
        }
        // Answer
        return dp[target];
    }
}