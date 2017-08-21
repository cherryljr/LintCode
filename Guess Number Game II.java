State:
Definition of dp[i][j]: minimum number of money to guarantee win for subproblem [i, j].

Corner case: dp[i][i] = 0 (because the only element must be correct)

Function:
we can choose k (i <= k <= j) as our guess, and pay price k. After our guess, the problem is divided into two subproblems.
Notice we do not need to pay the money for both subproblems. We only need to pay the worst case. 
(because the system will tell us which side we should go) to guarantee win. 
So dp[i][j] = min (i <= k <= j) { k + max(dp[i][k-1], dp[k+1][j]) }

Answer:
dp[1][n]

/*
Description
We are playing the Guess Game. The game is as follows:
I pick a number from 1 to n. You have to guess which number I picked.
Every time you guess wrong, I'll tell you whether the number I picked is higher or lower.
However, when you guess a particular number x, and you guess wrong, you pay $x. You win the game when you guess the number I picked.

Example
Given n = 10, I pick 8.
First round: You guess 5, I tell you that it's higher. You pay $5.
Second round: You guess 7, I tell you that it's higher. You pay $7.
Third round: You guess 9, I tell you that it's lower. You pay $9.

Game over. 8 is the number I picked.
You end up paying $5 + $7 + $9 = $21.

Given a particular n б▌ 1, find out how much money you need to have to guarantee a win.
So when n = гр10, return16`

Tags 
Dynamic Programming
*/

public class Solution {
    /**
     * @param n an integer
     * @return how much money you need to have to guarantee a win
     */
    public int getMoneyAmount(int n) {
        if (n == 1) {
            return 0;
        }
        
        // State
        int[][] dp = new int[n + 1][n + 1];
        
        // Initialize & Function
        for (int len = 1; len < n; len++) {
            for (int i = 0; i + len <= n; i++) {
                int j = i + len;
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = i; k <= j; k++) {
                    dp[i][j] = Math.min(dp[i][j],
                                        k + Math.max(k - 1 >= i ? dp[i][k - 1] : 0,
                                                     j >= k + 1 ? dp[k + 1][j] : 0));
                }
            }
        }
        
        // Answer
        return dp[1][n];
    }
}