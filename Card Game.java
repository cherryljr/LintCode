/*
Description
A card game that gives you two non-negative integers: totalProfit, totalCost, and n cards'information.
The ith card has a profit value a[i] and a cost value b[i].
It is possible to select any number of cards from these cards, form a scheme.
Now we want to know how many schemes are satisfied that all selected cards' profit values are greater than totalProfit
and the costs are less than totalCost.

Since this number may be large, you only need to return the solution number mod 1e9 + 7.
1000≤n≤100
1000≤a[i]≤100
1000≤b[i]≤100
1000≤totalProfit≤100
1000≤totalCost≤100

Example
Given n = 2，totalProfit = 3，totalCost = 5，a = [2,3]，b = [2,2] ，return1.
Explanation:
At this time, there is only one legal scheme, which is to select both cards.
At this time, a[1]+a[2] = 5 > totalProfit and b[1] + b[2] < totalCost.

Given n = 3，totalProfit = 5，totalCost = 10，a = [6,7,8]，b = [2,3,5]，return6.
Explanation:
Suppose a legal scheme (i,j) indicates that the i-th card and the j-th card are selected.
The legal solutions at this time are:
(1),(2),(3),(1,2),(1,3),(2,3)
 */

/**
 * Approach 1: Recursion + Memory Search
 * 从数据规模来看很明显就是 n^3 的时间复杂度。
 * 并且只问方案数，不需要具体方案，妥妥的 DP 问题。
 * 而其本质说白了就是记忆化搜索，比赛中懒得考虑 DP 写法。
 * 直接 DFS + Memory Search 过就行，写起来还非常简单。
 * (效果还挺好的，Beats 90.32%)
 */
public class Solution {
    public static final int MOD = 1000000007;
    long[][][] mem;

    /**
     * @param n: The number of cards
     * @param totalProfit: The totalProfit
     * @param totalCost: The totalCost
     * @param a: The profit of cards
     * @param b: The cost of cards
     * @return: Return the number of legal plan
     */
    public int numOfPlan(int n, int totalProfit, int totalCost, int[] a, int[] b) {
        mem = new long[n][totalProfit + 2][totalCost + 1];
        return (int)dfs(n, totalProfit, totalCost, a, b, 0, 0, 0);
    }

    private long dfs(int n, int totalProfit, int totalCost, int[] a, int[] b, int index, int currP, int currC) {
        if (currC >= totalCost) {
            return 0;
        }
        if (index == n) {
            if (currP > totalProfit) {
                return 1;
            }
            return 0;
        }
        // 利益最大值就是 totalProfit + 1,超过的值一律取最大值
        currP = Math.min(currP, totalProfit + 1);
        if (mem[index][currP][currC] != 0) {
            return mem[index][currP][currC];
        }

        // Not pick current card
        mem[index][currP][currC] = (mem[index][currP][currC] + dfs(n, totalProfit, totalCost, a, b, index + 1, currP, currC)) % MOD;
        // Pick current card
        mem[index][currP][currC] = (mem[index][currP][currC]
                + dfs(n, totalProfit, totalCost, a, b, index + 1, currP + a[index], currC + b[index])) % MOD;

        return mem[index][currP][currC];
    }
}

/**
 * Approach 2: DP
 * 时间复杂度：O(n^3)
 * 空间复杂度：O(n^3)
 */
public class Solution {
    public static final int MOD = 1000000007;

    /**
     * @param n: The number of cards
     * @param totalProfit: The totalProfit
     * @param totalCost: The totalCost
     * @param a: The profit of cards
     * @param b: The cost of cards
     * @return: Return the number of legal plan
     */
    public int numOfPlan(int n, int totalProfit, int totalCost, int[] a, int[] b) {
        long[][][] dp = new long[n + 1][totalProfit + 2][totalCost + 1];
        // Initialize
        dp[0][0][0] = 1;
        // Function
        for (int k = 1; k <= n; k++) {
            for (int i = 0; i <= totalProfit + 1; i++) {
                for (int j = 0; j < totalCost; j++) {
                    if (dp[k - 1][i][j] > 0) {
                        // Not pick current card
                        dp[k][i][j] = (dp[k][i][j] + dp[k - 1][i][j]) % MOD;
                        // Pick current card
                        if (j + b[k - 1] < totalCost) {
                            int profit = Math.min(i + a[k - 1], totalProfit + 1);
                            dp[k][profit][j + b[k - 1]] = (dp[k][profit][j + b[k - 1]] + dp[k - 1][i][j]) % MOD;
                        }
                    }
                }
            }
        }

        long sum = 0;
        for (int j = 0; j < totalCost; j++) {
            sum = (sum + dp[n][totalProfit + 1][j]) % MOD;
        }
        return (int)sum;
    }
}

/**
 * Approach 3: DP (Space Optimized)
 * 当前状态仅仅依赖于上一行的状态，因此可以进行空间复杂度的优化。
 * 优化方法类似 01背包问题 
 * 
 * 时间复杂度：O(n^3)
 * 空间复杂度：O(n^2)
 */
public class Solution {
    public static final int MOD = 1000000007;

    /**
     * @param n: The number of cards
     * @param totalProfit: The totalProfit
     * @param totalCost: The totalCost
     * @param a: The profit of cards
     * @param b: The cost of cards
     * @return: Return the number of legal plan
     */
    public int numOfPlan(int n, int totalProfit, int totalCost, int[] a, int[] b) {
        long[][] dp = new long[totalProfit + 2][totalCost + 1];
        // Initialize
        dp[0][0] = 1;
        // Function
        for (int k = 1; k <= n; k++) {
            for (int i = totalProfit + 1; i >= 0; i--) {
                for (int j = totalCost - 1 - b[k - 1]; j >= 0; j--) {
                    int profit = Math.min(i + a[k - 1], totalProfit + 1);
                    dp[profit][j + b[k - 1]] = (dp[profit][j + b[k - 1]] + dp[i][j]) % MOD;
                }
            }
        }

        long sum = 0;
        for (int j = 0; j < totalCost; j++) {
            sum = (sum + dp[totalProfit + 1][j]) % MOD;
        }
        return (int)sum;
    }
}