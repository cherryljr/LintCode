/*
Description
In material plane "reality", there are n + 1 planets, namely planet 0, planet 1, ..., planet n.
Each planet has a portal through which we can reach the target planet directly without passing through other planets.
But portal has two shortcomings.
First, planet i can only reach the planet whose number is greater than i, and the difference between i can not exceed the limit.
Second, it takes cost[j] gold coins to reach the planet j through the portal.
Now, Rogue Knight Sven arrives at the planet 0 with m gold coins, how many ways does he reach the planet n through the portal?

Notice
1 <= n <= 50, 0 <= m <= 100, 1 <= limit <= 50,0 <= cost[i] <= 100。
The problem guarantees cost [0] = 0, cause cost[0] does not make sense

Example
Give n = 1, m = 1, limit = 1, cost = [0, 1],return 1.
Explanation:
Plan 1: planet 0 → planet 1

Give n = 1, m = 1, limit = 1, cost = [0, 2],return 0.
Explanation:
He can not reach the target planet.

Give n = 2, m = 3, limit = 2, cost = [0, 1, 1],return 2.
Explanation:
Plan 1: planet 0 → planet 1 → planet 2
Plan 2: planet 0 → planet 2

Give n = 2, m = 3, limit = 2, cost = [0, 3, 1],return 1.
Explanation:
Plan 1: planet 0 → planet 2

Tags
Dynamic Programming
 */

/**
 * Approach: DP
 * 求方案个数（无需具体方案内容），且无法进行排序操作 => 动态规划
 *
 * 题目中的约束条件为：每次旅行的距离不能超过limit 与 距离的费用cost[i]不能大于所持有的经费;
 * 需要求方案个数，则我们可以设 dp[i][j] 代表从 星球0 出发到达 星球i 后拥有 j 个金币的方案数.
 *  首先根据题意对 dp[][] 进行一次初始化，dp[0][m] = 1.
 *  然后，我们可以求解它的状态转移方程：
 *  当骑士在 星球k 上，拥有 资金j 的时候，若 j + cost[i] <= m,
 *  则说明骑士能够从 星球k 旅行到 星球i, 则 dp[i][j] += dp[k][j + cost[i]];
 *  最后，答案便是能够到达 星球n 的所有方案数，即不管话费多少资金，能到星球n即可。
 *  所以：rst = sum(dp[n][0...m])
 */
public class Solution {
    /**
     * @param n: the max identifier of planet.
     * @param m: gold coins that Sven has.
     * @param limit: the max difference.
     * @param cost: the number of gold coins that reaching the planet j through the portal costs.
     * @return: return the number of ways he can reach the planet n through the portal.
     */
    public long getNumberOfWays(int n, int m, int limit, int[] cost) {
        if (n == 0) {
            return 1;
        }

        // Initialize the dp array
        long[][] dp = new long[n + 1][m + 1];
        dp[0][m] = 1;

        // Function
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                dp[i][j] = 0;
                // Deduce the value of dp[i][j] from dp[k][j + cost[i]]
                for (int k = Math.max(0, i - limit); k < i; k++) {
                    if (j + cost[i] <= m) {
                        dp[i][j] += dp[k][j + cost[i]];
                    }
                }
            }
        }

        long rst = 0;
        for (int i = 0; i <= m; i++) {
            rst += dp[n][i];
        }
        return rst;
    }
}