/*
Description
You have a total of n thousand yuan, hoping to apply for a university abroad.
The application is required to pay a certain fee.
Give the cost of each university application and the probability of getting the University's offer,
and the number of university is m. If the economy allows, you can apply for multiple universities.
Find the highest probability of receiving at least one offer.

Notice
0<=n<=10000,0<=m<=10000

Example
Given:
n = 10
prices = [4,4,5]
probability = [0.1,0.2,0.3]
Return:0.440

Tags
Backpack Dynamic Programming
 */

/**
 * Approach: 0-1 Backpack
 * 题目求解的是：至少收到一个offer的最高概率。
 * 因此这里可以稍微应用一下 概率学 上面的知识，即可以把问题转换为：
 * 一个offer都没有收到的最小概率。这样就好计算不少了。
 * dp[i]表示前 i 个大学中，一个 offer 都没收到的最小概率。
 * 对此我们首先需要计算一个收不到 offer 概率的情况。
 * 然后将 dp[] 初始化为 1.
 * 因为每个大学只申请一次，因此之后的就跟 01背包情况一样了。
 *
 * 01背包问题分析：
 *  数组和为sum的方法数：
 *  https://github.com/cherryljr/NowCoder/blob/master/%E6%95%B0%E7%BB%84%E5%92%8C%E4%B8%BAsum%E7%9A%84%E6%96%B9%E6%B3%95%E6%95%B0.java
 */
public class Solution {
    /**
     * @param n: Your money
     * @param prices: Cost of each university application
     * @param probability: Probability of getting the University's offer
     * @return: the  highest probability
     */
    public double backpackIX(int n, int[] prices, double[] probability) {
    	if (probability == null || probability.length == 0) {
    		return 0.0;
    	}

        double[] dp = new double[n + 1];
        Arrays.fill(dp, 1.0);
        for (int i = 0; i < probability.length; i++) {
            probability[i] = 1- probability[i];
        }
        
        for (int i = 0; i < probability.length; i++) {
            // Gurantee the money is enough to pay pricees[i] fee
            for (int j = n; j >= prices[i]; j--) {
                dp[j] = Math.min(dp[j], dp[j - prices[i]] * probability[i]);
            }
        }
        return 1 - dp[n];
    }
}