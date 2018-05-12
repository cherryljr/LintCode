/*
Description
Given n kind of items with size Ai and value Vi( each item has an infinite number available) and a backpack with size m.
What's the maximum value can you put into the backpack?

Notice
You cannot divide item into small pieces and the total size of items you choose should smaller or equal to m.

Example
Given 4 items with size [2, 3, 5, 7] and value [1, 5, 2, 4], and a backpack with size 10.
The maximum value is 15.

Tags
Dynamic Programming
 */

/**
 * Approach: Completed Backpack
 * 经典的 完全背包 问题...没啥好解释的...
 */
public class Solution {
    /**
     * @param A: an integer array
     * @param V: an integer array
     * @param m: An integer
     * @return: an array
     */
    public int backPackIII(int[] A, int[] V, int m) {
        if (V == null || V.length == 0) {
            return 0;
        }
        
        int[] dp = new int[m + 1];
        // Initialize
        for (int i = 0; i * A[0] <= m; i++) {
            dp[i * A[0]] = i * V[0];
        }

        // Function
        for (int i = 1; i < A.length; i++) {
            for (int j = A[i]; j <= m; j++) {
                dp[j] = Math.max(dp[j], dp[j - A[i]] + V[i]);
            }
        }

        // Answer
        return dp[m];
    }
}