在 Coins in a Line II 上面的进一步加大难度。
但是主要解体思路仍然与 II 相同。
仍然要使得每一次取完之后，对手取的都是较差的情况。使得对手拿到的硬币总价值最小。
因为每次取可以在 左/右 任意一边取一个值。故我们需要知道数组中每一段之和是多少。
然后对每次操作进行分析，得出 状态转移方程（和 II 几乎一样）

游戏局面 (State):
	dp[i][j] 剩下 i~j 段的硬币时，可以取得的最大值
玩家决策 (Function):
	因为硬币总价值一定，为了保证 先手最大，保证取完后对手能取到的最小即可。
	(死命想法办法坑对方即可)
	dp[i][j] = sum[i][j] - Math.min(dp[i+1][j], dp[i][j-1]);
	注意：当 i==j 时，我们只能取到 values[i].
游戏终止 (Initialize)：
	当唯一剩下的一枚硬币被取走之后，游戏结束。
	故当 i==j 时，dp[i][j] = values[i];

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

public class Solution {
    /*
     * @param values: a vector of integers
     * @return: a boolean which equals to true if the first player will win
     */
    public boolean firstWillWin(int[] values) {
        if (values == null || values.length == 0) {
            return false;
        }
        if (values.length <= 1) {
            return true;
        }
        
        // State & Initialize
        int len = values.length;
        int[][] sum = new int[len + 1][len + 1];
        int[][] dp = new int[len + 1][len + 1];
        // 这里的两个 for-loop 仅仅是为了求 i~j 段中硬币的总价值
        // 因此 循环的方向可以随意，只要能实现功能即可。而 DP 的两个for-loop则不行
        for (int i = 1; i <= len; i++) {
            for (int j = i; j <= len; j++) {
                if (i == j) {
                    sum[i][j] = values[i - 1];
                } else {
                    sum[i][j] = sum[i][j - 1] + values[j - 1];   
                }
            }
        }
        
        // Function & Initialize
        // 注意我们分析的是剩下来的 i~j 段的硬币价值。
        // 因此 loop 中 i,j 应该从 len 开始。表示剩下的硬币 (少 -> 多)
        for (int i = len; i >= 1; i--) {
            for (int j = i; j <= len; j++) {
                if (i == j) {
                    dp[i][j] = values[i - 1];
                } else {
                    dp[i][j] = sum[i][j] - Math.min(dp[i+1][j], dp[i][j-1]);
                }
            }
        }
        
        // Answer
        return dp[1][len] > sum[1][len] / 2;
    }
}