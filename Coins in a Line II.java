在 Coins in a Line 问题上的进一步提升，仍还是 博弈 问题。
与之前的思路相同，本题中需要我们拿到的硬币总价值最高，
转换思想：这也就意味着 对手拿到的硬币总价值最低。（再次强调这点很重要！让对方难受）
那怎么让对手拿到的硬币价值尽量低呢？
这就意味着我们取完后，对手能取的都是最差的情况。
故我们需要对 取完后的游戏局面进行分析（剩下的coins） 做出使得对方最难受的选择。
如果上述描述较为抽象，我们来看一个例子：
	Coins: 4 1 2 3 
	第一步，如何使得我们的 dp[4] 值最大呢？我们需要看 dp[3] 和 dp[2] 的情况。
	然后，我们发现 dp[3] = 3, dp[2] = 5. 因此我们需要做出让对方最难受的决定。
	即：只取一枚硬币，剩下 3 枚，让对手去面对 dp[3] 的情况。

游戏局面 (State):
	还剩下 i 枚conis时，可以取得的最大值
	因为我们讨论的是 剩下i枚 的情况，所以是从右到左来分析！这里一定要注意
玩家决策 (Function):
	因为硬币总价值一定，为了保证 先手最大，保证取完后对手能取到的最小即可。
	（死命想法办法坑对方即可）
	dp[i] = sum[i] - Math.min(dp[i - 1], dp[i - 2]);  (sum[i] 表示当前局面下硬币的总价值)
游戏终止 (Initialize)：
	前面也已经提过了，我们讨论的是还剩下 i 枚硬币的情况。
	所以是从右到左。
	因此初始化 dp[1] = values[values.length - 1];

/*
Description
There are n coins with different value in a line.
Two players take turns to take one or two coins from left side until there are no more coins left. 
The player who take the coins with the most value wins.
Could you please decide the first player will win or lose?

Example
Given values array A = [1,2,2], return true.
Given A = [1,2,4], return false.

Tags 
Array Dynamic Programming Game Theory
*/

public class Solution {
    /*
     * @param values: a vector of integers
     * @return: a boolean which equals to true if the first player will win
     */
    public boolean firstWillWin(int[] values) {
        int len = values.length;
        int[] sum = new int[len + 1];
        for (int i = 1; i <= len; i++) {
            sum[i] = sum[i - 1] + values[len - i];
        }
        
        int[] dp = new int[len + 1];
        dp[1] = values[len - 1];
        for (int i = 2; i <= len; i++) {
            dp[i] = sum[i] - Math.min(dp[i - 1], dp[i - 2]);
        }
        
        return dp[len] > sum[len] / 2;
    }
}