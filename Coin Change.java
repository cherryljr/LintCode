/*
Description
You are given coins of different denominations and a total amount of money amount.
Write a function to compute the fewest number of coins that you need to make up that amount.
If that amount of money cannot be made up by any combination of the coins, return -1.

You may assume that you have an infinite number of each kind of coin.

Example
Given coins = [1, 2, 5], amount = 11
return 3 (11 = 5 + 5 + 1)

Given coins = [2], amount = 3
return -1.
 */

/**
 * Approach: Completed Backpack
 * 等同于 换钱的最少货币数
 * 详细解析可以参考：
 * https://github.com/cherryljr/NowCoder/blob/master/%E6%8D%A2%E9%92%B1%E7%9A%84%E6%9C%80%E5%B0%91%E8%B4%A7%E5%B8%81%E6%95%B0.java
 */
class Solution {
    /**
     * @param coins: a list of integer
     * @param amount: a total amount of money amount
     * @return: the fewest number of coins that you need to make up
     */
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        // Initialize
        Arrays.fill(dp, Integer.MAX_VALUE);
        for (int i = 0; i * coins[0] <= amount; i++) {
            dp[i * coins[0]] = i;
            // 注意：可能存在 coins[0] == 0 的情况，此时需要跳出循环
            if (coins[0] == 0) {
                break;
            }
        }
        
        // Function
        for (int i = 1; i < coins.length; i++) {
            for (int j = coins[i]; j <= amount; j++) {
                if (dp[j - coins[i]] != Integer.MAX_VALUE) {
                    dp[j] = Math.min(dp[j], dp[j - coins[i]] + 1);
                }
            }
        }

        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }
}