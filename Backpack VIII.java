/*
Description
Give some coins of different value and their quantity.
Find how many values which are in range 1 ~ n can these coins be combined

Example
Given:
n = 10
value = [1,2,4]
amount = [2,1,1]

Return: 8
They can combine all the values in 1 ~ 8

Tags
Backpack Dynamic Programming
 */

/**
 * Approach: Multiple Backpack
 * 该题属于多重背包问题。
 * dp[i][j]表示：在前 i 个元素中，取得价值为 j 的方案是否成立
 * 因此 dp[i][j] |= dp[i - 1][j - k * value[i]] (j >= k * value[i])
 * 最后我们只需要统计 dp[n-1][m] 中有几个方案为 true 即可。
 *
 * 注意点：题目要求至少取一个值，即全部都不取（结果为0）的方案是不成立的
 * 而我们这里是认为一个都不取的方案是成立的，因此结果需要 减去1.
 *
 * 关于 多重背包问题 的详细解析与解法优化可以参考：
 * https://github.com/cherryljr/LintCode/edit/master/Backpack%20VII.java
 */
public class Solution {
    /**
     * @param m: the value from 1 - m
     * @param value: the value of coins
     * @param amount: the number of coins
     * @return: how many different value
     */
    public int backPackVIII(int m, int[] value, int[] amount) {
        int n = value.length;
        boolean[][] dp = new boolean[n][m + 1];
        // Initialize
        for (int i = 0; i <= amount[0]; i++) {
            if (i * value[0] <= m) {
                dp[0][i * value[0]] = true;
            }
        }

        // Function
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= m; j++) {
                for (int k = 0; k <= amount[i]; k++) {
                    if (j >= k * value[i]) {
                        dp[i][j] |= dp[i - 1][j - k * value[i]];
                    } else {
                        // 如果当前价值超过了总价值 j,那么后面就没有继续计算的必要了
                        break;
                    }
                }
            }
        }

        // Answer
        int rst = 0;
        for (int i = 0; i <= m; i++) {
            if (dp[n - 1][i]) {
                rst++;
            }
        }
        return rst - 1;
    }
}

/**
 * Approach 2: Multiple Backpack (Using 01 Backpack)
 */
public class Solution {
    /**
     * @param m: the value from 1 - m
     * @param value: the value of coins
     * @param amount: the number of coins
     * @return: how many different value
     */
    public int backPackVIII(int m, int[] value, int[] amount) {
        int n = value.length;
        boolean[] dp = new boolean[m + 1];
        // Initialize
        for (int i = 0; i <= amount[0]; i++) {
            if (i * value[0] <= m) {
                dp[i * value[0]] = true;
            }
        }

        // Function
        for (int i = 1; i < n; i++) {
            for (int k = 0; k < amount[i]; k++) {
                for (int j = m; j >= value[i]; j++) {
                    dp[j] |= dp[j - value[i]];
                }
            }
        }

        // Answer
        int rst = 0;
        for (int i = 0; i <= m; i++) {
            if (dp[i]) {
                rst++;
            }
        }
        return rst - 1;
    }
}

/**
 * Approach 3: Multiple Backpack (Using Binary Code)
 * 使用 二进制 的思想对时间复杂度进行优化
 */
public class Solution {
    /**
     * @param m: the value from 1 - m
     * @param value: the value of coins
     * @param amount: the number of coins
     * @return: how many different value
     */
    public int backPackVIII(int m, int[] value, int[] amount) {
        int n = value.length;
        boolean[] dp = new boolean[m + 1];
        // Initialize
        for (int i = 0; i <= amount[0]; i++) {
            if (i * value[0] <= m) {
                dp[i * value[0]] = true;
            }
        }

        // Function
        for (int i = 1; i < n; i++) {
            if (value[i] * amount[i] > m) {
                completedBackpack(dp, value[i], m);
            } else {
                int k = 1;
                while (k < amount[i]) {
                    zeroOneBackpack(dp, k * value[i], m);
                    amount[i] -= k;
                    k <<= 1;    // 二进制的思想
                }
                // 最后对余数部分进行一次 01背包 处理
                zeroOneBackpack(dp, amount[i] * value[i], m);
            }
        }

        // Answer
        int rst = 0;
        for (int i = 0; i <= m; i++) {
            if (dp[i]) {
                rst++;
            }
        }
        return rst - 1;
    }

    // 完全背包问题代码
    private void completedBackpack(boolean[] dp, int price, int m) {
        for (int j = price; j <= m; j++) {
            dp[j] |= dp[j - price];
        }
    }

    // 01背包问题代码
    private void zeroOneBackpack(boolean[] dp, int price, int m) {
        for (int j = m; j >= price; j--) {
            dp[j] |= dp[j - price];
        }
    }
}
