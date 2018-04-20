/*
Description
Given an integer array nums with all positive numbers and no duplicates,
find the number of possible combinations that add up to a positive integer target.

Notice
A number in the array can be used multiple times in the combination.
Different orders are counted as different combinations.

Example
Given nums = [1, 2, 4], target = 4
The possible combination ways are:
[1, 1, 1, 1]
[1, 1, 2]
[1, 2, 1]
[2, 1, 1]
[2, 2]
[4]
return 6

Tags
Dynamic Programming
*/

/**
 * Approach: 完全背包问题
 * 求组合的方案个数，使其结果恰好是Target
 * 与该题相似的 DFS / Traverse 题目为：Combination Sum, 他求解的是所有的具体方案。
 * 所以使用 DFS 遍历求解。
 * 
 * 这道题目与 换零钱 有一点点区别。
 * 在于：本题中如果 选取的顺序不同 那么就算不同的方法；
 * 而在 换零钱 中，只有选取的币值面值或者张数不同，才算不同的方法。
 * 
 * 不会的可以参考 动态规划的演进 -- 完全背包问题详解：
 * https://github.com/cherryljr/NowCoder/blob/master/%E6%8D%A2%E9%9B%B6%E9%92%B1.java
 */
class Solution {
    /**
     * @param nums   an integer array and all positive numbers, no duplicates
     * @param target an integer
     * @return an integer
     */
    public int backPackVI(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // State
        int[] dp = new int[target + 1];

        // Initialize
        dp[0] = 1;

        // Function
        for (int i = 1; i <= target; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (i >= nums[j]) {
                    dp[i] += dp[i - nums[j]];
                }
            }
        }

        // Answer
        return dp[target];
    }
}