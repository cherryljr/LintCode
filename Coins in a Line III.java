/*
Description
There are n coins in a line. 
Two players take turns to take a coin from one of the ends of the line until there are no more coins left. 
The player with the larger amount of money wins.
Could you please decide the first player will win or lose?

Example
Given array A = [3,2,2], return true.
Given array A = [1,2,4], return true.
Given array A = [1,20,4], return false.

Challenge 
Follow Up Question:
If n is even. Is there any hacky algorithm that can decide whether first player will win or lose in O(1) memory and O(n) time?

Tags 
Array Dynamic Programming Game Theory
*/

/**
 * Approach: DP
 * 在 Coins in a Line II 上面的进一步加大难度。
 * 但是主要解体思路仍然与 II 相同。
 * 仍然要使得每一次取完之后，对手取的都是较差的情况。使得对手拿到的硬币总价值最小。
 * 因为每次取可以在 左/右 任意一边取一个值。故我们需要知道数组中每一段之和是多少。
 * 然后对每次操作进行分析，得出 状态转移方程（和 II 几乎一样）
 *
 * 游戏局面 (State):
 * dp[i][j] 剩下 i~j 段的硬币时，可以取得的最大值（i为左端位置，j为右端位置）
 * 玩家决策 (Function):
 * 因为硬币总价值一定，为了保证 先手最大，保证取完后对手能取到的最小即可。
 * (死命想法办法坑对方即可)
 * dp[i][j] = preSum[j + 1] - preSum[i] - Math.min(dp[i+1][j], dp[i][j-1]);
 * 注意：当 i==j 时，我们只能取到 values[i].
 * 游戏终止 (Initialize)：
 * 当唯一剩下的一枚硬币被取走之后，游戏结束。
 * 故当 i==j 时，dp[i][j] = values[i];
 *
 * 实际的流程就是一个 填矩阵 的过程。
 * 我们发现 dp[i][j] 依赖与 dp[i+1][j] 和 dp[i][j-1] 这两个状态。
 * 并且 左端位置i 永远不可能大于 右端位置j
 * 因此我们要填的其实是这个矩阵的 右上部分 的一个三角形。
 * 而初始化的 base 就是这个矩阵的 对角线。
 * 按照 从下到上，从左到右 的顺序进行递推，得到最终结果：
 * 矩阵的 右上角 (dp[0][n-1])
 */
public class Solution {
    /**
     * @param values: a vector of integers
     * @return: a boolean which equals to true if the first player will win
     */
    public boolean firstWillWin(int[] values) {
        int n = values.length;
        int[] preSum = new int[n + 1];
        // 计算前缀和以便后面快速计算出各个区间段之和
        for (int i = 1; i <= n; i++) {
            preSum[i] = preSum[i - 1] + values[i - 1];
        }

        // Initialize and Function
        int[][] dp = new int[n][n];
        // bottom to top
        for (int i = n - 1; i >= 0; i--) {
            // left to right
            for (int j = i; j < n; j++) {
                if (i == j) {
                    dp[i][j] = values[i];
                } else {
                    int sum = preSum[j + 1] - preSum[i];
                    dp[i][j] = sum - Math.min(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[0][n - 1] > preSum[n] / 2;
    }
}