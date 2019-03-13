/*
There is a game of stone merging. At the beginning, there were n piles of stones arranged in a row.
The goal was to combine all the stones into a pile. The consolidation rules are as follows:

Each time you can merge consecutive x piles, left <= x <= right.
The cost of each merger is the sum of the weight of the combined x piles.
Find the minimum merge cost, if you cannot complete the merge return 0.

Example
Given n = 4, left = 3, right = 3, weight = [1,2,3,4], return 0.
Explanation:
Unable to complete the merge.

Given n = 3, left = 2, right = 3, weight = [1,2,3], return 6.
Explanation:
Merge 1,2,3, the merger cost is 1 + 2 + 3 = 6.

Notice
    1. 1 <= n <= 100，2 <= left <= right <= n
    2. 1 <= weight[i] <= 1000
 */

/**
 * Approach: Interval DP
 * 区间DP问题，与 LeetCode 上的 Minimum Cost to Merge Stones 非常类似。
 * 直接采用对应 Approach 1 解法即可。
 *
 * 时间复杂度：O(n^3 * k)
 * 空间复杂度：O(n^2 * k)
 * 
 * Minimum Cost to Merge Stones：
 *  https://github.com/cherryljr/LeetCode/blob/master/Minimum%20Cost%20to%20Merge%20Stones.java
 */
public class Solution {
    private static final int INF = 0x3f3f3f3f;

    /**
     * @param n:      The number of stones
     * @param left:   The minimum length to merge stones
     * @param right:  The maximum length to merge stones
     * @param weight: The weight array
     * @return: Return the minimum cost
     */
    public int getMinimumCost(int n, int left, int right, int[] weight) {
        int[] preSum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            preSum[i] = preSum[i - 1] + weight[i - 1];
        }

        int[][][] dp = new int[n][n][right + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k <= right; k++) {
                    dp[i][j][k] = (i == j && k == 1) ? 0 : INF;
                }
            }
        }

        // 枚举区间长度
        for (int l = 2; l <= n; l++) {
            // 枚举起始位置
            for (int start = 0; start + l <= n; start++) {
                int end = start + l - 1;
                // 枚举可能的堆数
                for (int k = 2; k <= Math.min(l, right); k++) {
                    // 枚举分割点（注意这里是从 start 开始）
                    for (int pivot = start; pivot < end && end - pivot >= k - 1; pivot++) {
                        dp[start][end][k] = Math.min(dp[start][end][k], dp[start][pivot][1] + dp[pivot + 1][end][k - 1]);
                    }
                    if (k >= left) {
                        dp[start][end][1] = Math.min(dp[start][end][1], dp[start][end][k] + preSum[end + 1] - preSum[start]);
                    }
                }
            }
        }

        return dp[0][n - 1][1] >= INF ? 0 : dp[0][n - 1][1];
    }
}