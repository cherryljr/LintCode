/*
Description
Given n items with size nums[i] which an integer array and all positive numbers, no duplicates.
An integer target denotes the size of a backpack. Find the number of possible fill the backpack.
Each item may be chosen unlimited number of times

Example
Given candidate items [2,3,6,7] and target 7,

A solution set is:
[7]
[2, 2, 3]
return 2

Tags
Dynamic Programming
 */

/**
 * Approach: Completed Backpack
 * 最基础的 完全背包 问题。
 * 详细解析可以参考：
 * https://github.com/cherryljr/NowCoder/blob/master/%E6%8D%A2%E9%9B%B6%E9%92%B1.java
 */
public class Solution {
    /**
     * @param nums: an integer array and all positive numbers, no duplicates
     * @param target: An integer
     * @return: An integer
     */
    public int backPackIV(int[] nums, int target) {
        int[] dp = new int[target + 1];
        // Initialize
        for (int i = 0; i * nums[0] <= target; i++) {
            dp[i * nums[0]] = 1;
        }

        // Function
        for (int i = 1; i < nums.length; i++) {
            for (int j = nums[i]; j <= target; j++) {
                dp[j] += dp[j - nums[i]];
            }
        }
        // Answer
        return dp[target];
    }
}