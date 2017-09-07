经典的博弈问题
	使用 DP 方法解决。
	重点在于：需要学会转换思想来思考问题。
	即：我想要必胜，那么对手就是必败！这点十分重要！那么我们只要做出让对手必败的选择即可
	因为 DP 就是根据之前的 小状态 来推得现在的 大状态（当前状态）
	
游戏局面 (State)：
	dp[i] 表示当局面时有 i 个 coins 时，是否先手必胜
玩家决策 (Function):
	对于有 i 个conis局面的先手，不管他怎么取。取完后都是 先手（对手） 必胜的状态，那么他就必败。
	反之，如果 存在 一种取法，使得他取完后的状态是 先手（对手） 必败的状态，那么他就必胜。
	用数学的语言总结：
	先手必胜状态的条件（N）：当且仅当它的子状态中 存在 一个 先手必败的状态（P）
	先手必败状态的条件（P）：当且仅当它的子状态 全部都是 先手必胜的状态（N）
	在该问题中，可以一次取 1 or 2 枚。
	故 dp[i] = !(dp[i - 1] && dp[i - 2]);
游戏终止 (Initialize)：
	当 coins 为 1 时，先手必胜（N）；
	当 conis 为 2 时，先手必胜（N）；
	当 conis 为 3 时，先手必败（P）；

当然该题还存在着一个巧妙的解法：
	当 n % 3 == 0 时，先手必败（P）；
	否则 先手必胜（N）；
	这是根据规律总结出来的，并不具有泛用性。故还是推荐使用 DP 方法。
	
/*
Description
There are n coins in a line.
Two players take turns to take one or two coins from right side until there are no more coins left. 
The player who take the last coin wins.
Could you please decide the first play will win or lose?

Example
n = 1, return true.
n = 2, return true.
n = 3, return false.
n = 4, return true.
n = 5, return true.

Challenge 
O(n) time and O(1) memory

Tags 
Greedy Array Dynamic Programming Game Theory
*/

// Solution 1: DP
public class Solution {
    /**
     * @param n: an integer
     * @return: a boolean which equals to true if the first player will win
     */
    public boolean firstWillWin(int n) {
        if (n == 0) {
            return false;
        } else if (n == 1) {
            return true;
        } else if (n == 2) {
            return true;
        }
        
        // State
        boolean[] rst = new boolean[n + 1];
        
        // Initialize
        rst[0] = false;
        rst[1] = true;
        rst[2] = true;
        
        // Function
        for (int i = 3; i <= n; i++) {
            rst[i] = !(rst[i - 1] && rst[i - 2]); 
        }
        
        // Answer
        return rst[n];
    } 
}

// Solution 2: Make a Conclusion
public class Solution {
    /**
     * @param n: an integer
     * @return: a boolean which equals to true if the first player will win
     */
    public boolean firstWillWin(int n) {
        return n % 3 != 0;
    } 
}