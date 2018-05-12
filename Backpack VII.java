/*
Description
Assume that you have n yuan. There are many kinds of rice in the supermarket.
Each kind of rice is bagged and must be purchased in the whole bag.
Given the weight, price and quantity of each type of rice, find the maximum weight of rice that you can purchase.

Example
Given:
n = 8
prices = [2,4]
weight = [100,100]
amounts = [4,2]

Return:400
 */

/**
 * Approach 1: Multiple Backpack
 * 本题属于 多重背包问题。
 * 相比于 01背包 和 完全背包 问题，其实际上只是增加了一个限制条件 --- 每个物品有特定的个数限制。
 * 如果对其他两个背包问题掌握得足够好的话，仅仅是要解决该类问题还是相当简单的。
 * 从 矩阵的递推 过程出发，新增的条件实际上只是将 dp[i][j] 的状态依赖限制在了某一段特定的区域之中。
 * 因此我们只需要利用 物品个数的限制条件 来计算出当前状态所依赖的区域即可。
 *
 * 以本题为例，dp[i][j] 表示：在前 i 个物品中，花费 j 的金额最多能够买到多少大米。
 * 则其所依赖的状态为：在前 i-1 个物品中，花费 j-k*prices[i] 的金额所能买到最多的大米重量。
 * 即 上一行 中，j-k*prices[i]  (0 <= k <= amounts[i] && j-k*prices[i] >= 0) 的这段区域
 * 因此：dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - k * prices[i]] + k * weight[i])
 * 最终结果为：dp[n - 1][m]
 *
 * 时间复杂度：O(N*M*K);	空间复杂度：O(N*M)
 *
 * 类似的问题还有：
 *  Backpack VIII：
 *
 *  美丽的项链：
 *  https://github.com/cherryljr/NowCoder/blob/master/%E7%BE%8E%E4%B8%BD%E7%9A%84%E9%A1%B9%E9%93%BE.java
 */
public class Solution {
    /**
     * @param m: the money of you
     * @param prices: the price of rice[i]
     * @param weight: the weight of rice[i]
     * @param amounts: the amount of rice[i]
     * @return: the maximum weight
     */
    public int backPackVII(int m, int[] prices, int[] weight, int[] amounts) {
        int n = prices.length;
        int[][] dp = new int[n][m + 1];
        // Initialize
        for (int i = 0; i <= amounts[0]; i++) {
            if (i * prices[0] <= m) {
                dp[0][i * prices[0]] = i * weight[0];
            }
        }

        // Function
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= m; j++) {
                for (int k = 0; k <= amounts[i]; k++) {
                    // Guarantee the money is enough to pay k*prices[i]
                    if (j >= k * prices[i]) {
                        dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - k * prices[i]] + k * weight[i]);
                    } else {
                        // 如果当前价值超过了总价值 j,那么后面就没有继续计算的必要了
                        break;
                    }
                }
            }
        }

        // Answer
        return dp[n - 1][m];
    }
}

/**
 * Approach 2: Multiple Backpack (Using 01 Backpack)
 * 对于 Approach 1 关于空间复杂度上，因为其只依赖于上一行的状态，
 * 所以我们可以使用 滚动数组 来进行空间优化。（比较简单，这里就不写了）
 *
 * 这里要提供给大家的是另外一种思路。
 * 虽然理解 01背包问题 对于我们解决这类题目有着巨大的帮助，但是我们更希望能够
 * 直接利用 01背包 问题来解决这类问题。对此我们需要使用到 拆分 的思想。
 *  我们可以将01背包问题看作是多重背包问题的一个特例。即所有的物品限制个数为 1.
 *  那么对于多重背包问题而言，其不外乎就是 多个01背包问题 的和。
 *  因此介于以上思路，我们可以利用 01背包问题 将原问题拆分出来。（调用amounts[i]次 01背包问题）
 * 同时因为我们已经很好地掌握了 01背包问题 的最优解。
 * 因此可以轻易地写出 多重背包问题 的最佳空间复杂度的解。
 * （这里只是空间复杂度哦，大家可以考虑下时间复杂度还可以进行怎么样的优化哈）
 *
 * 时间复杂度：O(N*M*K);  空间复杂度：O(M)
 */
public class Solution {
    /**
     * @param m: the money of you
     * @param prices: the price of rice[i]
     * @param weight: the weight of rice[i]
     * @param amounts: the amount of rice[i]
     * @return: the maximum weight
     */
    public int backPackVII(int m, int[] prices, int[] weight, int[] amounts) {
        int n = prices.length;
        int[] dp = new int[m + 1];
        // Initialize
        for (int i = 0; i <= amounts[0]; i++) {
            if (i * prices[0] <= m) {
                dp[i * prices[0]] = i * weight[0];
            }
        }

        // Function
        // 每种物品
        for (int i = 1; i < n; i++) {
            // 把该类物品展开，调用 amounts[i] 次01背包代码
            for (int k = 0; k < amounts[i]; k++) {
                // 01背包问题的代码
                for (int j = m; j >= prices[i]; j--) {
                    dp[j] = Math.max(dp[j], dp[j - prices[i]] + weight[i]);
                }
            }
        }

        // Answer
        return dp[m];
    }
}

/**
 * Approach 3: Multiple Backpack (Optimized by Binary Code)
 * 在 Approach 2 的基础上，我们可以进一步采用 二进制 的思想对时间复杂度进行优化。
 * 我们考虑把第 i 种物品换成若干件物品，使得原问题中第 i 种物品可取的每种策略：
 * 取 0~amounts[i] 件 —— 均能等价于取若干件代换以后的物品。
 * (另外，取超过 amounts[i] 件的策略必不能出现。)
 * 方法是：将第 i 种物品分成若干件 01 背包中的物品，其中每件物品有一个系数。
 * 这件物品的费用和价值均是原来的费用和价值乘以这个系数。
 * 令这些系数分别为 1; 2; 2^2 …… 2^(k−1); amounts[i] − 2^k + 1，且 k 是满足 amounts[i] − 2k + 1 > 0 的最大整数。
 * 例如，如果 amounts[i] 为 13，则相应的 k = 3，这种最多取 13 件的物品应被分成系数分别为 1; 2; 4; 6 的四件物品
 *
 * 时间复杂度为：O(N*M*logK)； 空间复杂度为：O(M)
 *
 * 该解法需要对 01背包 和 完全背包 都有着较为深刻的认识，若不清楚的可以参考：
 *  数组和为sum的方法数（01背包问题分析）：
 *  https://github.com/cherryljr/NowCoder/blob/master/%E6%95%B0%E7%BB%84%E5%92%8C%E4%B8%BAsum%E7%9A%84%E6%96%B9%E6%B3%95%E6%95%B0.java
 *  换零钱（完全背包问题分析）：
 *  https://github.com/cherryljr/NowCoder/blob/master/%E6%8D%A2%E9%9B%B6%E9%92%B1.java
 */
public class Solution {
    /**
     * @param m:       the money of you
     * @param prices:  the price of rice[i]
     * @param weight:  the weight of rice[i]
     * @param amounts: the amount of rice[i]
     * @return: the maximum weight
     */
    public int backPackVII(int m, int[] prices, int[] weight, int[] amounts) {
        int n = prices.length;
        int[] dp = new int[m + 1];
        // Initialize
        for (int i = 0; i <= amounts[0]; i++) {
            if (i * prices[0] <= m) {
                dp[i * prices[0]] = i * weight[0];
            }
        }

        // Function
        // 遍历每种物品
        for (int i = 1; i < n; i++) {
            if (prices[i] * amounts[i] > m) {
                /*
                如果全装进去已经超了总金额，相当于这个物品就是无限的
                因为是取不光的，那么就用完全背包去套
                 */
                completedBackpack(dp, prices[i], weight[i], m);
            } else {
                /*
                取得光的话，去遍历每种取法
                这里用到是二进制思想，降低了复杂度
                为什么呢，因为他取的1,2,4,8...与余数个该物品，打包成一个大型的该物品
                这样足够凑出了从0-k个该物品取法
                把复杂度从k变成了logk
                如k=11，则有1,2,4,4，足够凑出0-11个该物品的取法
                 */
                int k = 1;
                while (k < amounts[i]) {
                    zeroOneBackpack(dp, k * prices[i], k * weight[i], m);
                    amounts[i] -= k;
                    k <<= 1;    // 二进制的思想
                }
                // 最后对余数部分进行一次 01背包 处理
                zeroOneBackpack(dp, amounts[i] * prices[i], amounts[i] * weight[i], m);
            }
        }

        // Answer
        return dp[m];
    }

    // 完全背包问题代码
    private void completedBackpack(int[] dp, int price, int weight, int m) {
        for (int j = price; j <= m; j++) {
            dp[j] = Math.max(dp[j], dp[j - price] + weight);
        }
    }

    // 01背包问题代码
    private void zeroOneBackpack(int[] dp, int price, int weight, int m) {
        for (int j = m; j >= price; j--) {
            dp[j] = Math.max(dp[j], dp[j - price] + weight);
        }
    }
}