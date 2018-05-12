/*
Description
Given n items with size Ai, an integer m denotes the size of a backpack. How full you can fill this backpack?

Notice
You can not divide any item into small pieces.

Example
If we have 4 items with size [2, 3, 5, 7], the backpack size is 11, we can select [2, 3, 5],
so that the max size we can fill this backpack is 10. If the backpack size is 12.
we can select [2, 3, 7] so that we can fulfill the backpack.
You function should return the max size we can fill in the given backpack.

Challenge
O(n x m) time and O(m) memory.
O(n x m) memory is also acceptable if you do not know how to optimize memory.

Tags
Backpack LintCode Copyright Dynamic Programming
 */


/**
 * Approach 1: 0-1 Backpack
 * State
 *  dp[i][j]: i is the index of Ai, and j is the size from (0 ~ m).
 *  It means: depending if we added A[i-1], can we full-fill j-space? Return true/false.
 *  Note: that is, even j == 0, and I have a item with size == 0. There is nothing to add, 
 *  which means the backpack can reach j == 0. True.
 *  However, if we have a item with size == 2, but I need to fill space == 1.
 *  I will either pick/not pick item of size 2; either way, can't fill a backpack with size 1. False;
 * Function:
 *  dp[i][j] = dp[i - 1][j] || dp[i - 1][j - A[i - 1]];   
 *  // based on if previous value is true/false: 1. didn't really add A[i-1] || 2. added A[i-1].
 * Init:
 *  dp[0][0] = true; // 0 space and 0 items, true.
 * 	All the rest are false;
 * 
 * Return the very last j that makes dp[A.length][j] true.
 */
public class Solution {
    /**
     * @param m: An integer m denotes the size of a backpack
     * @param A: Given n items with size A[i]
     * @return: The maximum size
     */
    public int backPack(int m, int[] A) {
        // State
        boolean[][] dp = new boolean[A.length + 1][m + 1];

        // Initialize
        dp[0][0] = true;
        for (int i = 1; i <= A.length; i++) {
            dp[i][0] = true;
        }

        // Function
        for (int i = 1; i <= A.length; i++) {
            for (int j = 0; j <= m; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= A[i - 1] && dp[i - 1][j - A[i - 1]]) {
                    dp[i][j] = true;
                }
            }
        }

        // Answer
        for (int j = m; j >= 0; j--) {
            if (dp[A.length][j]) {
                return j;
            }
        }
        return 0;
    }
}

/**
 * Approach 2: 0-1 Backpack (Space Optimized)
 * dp[j]: can we fit i items into j?
 * dp[j] == false || dp[j - A[i - 1]].
 * Similar two cases:
 *  1. Can't fit in, set false;
 *  2. Can fit in, then just return if (j - A[i - 1]) works
 * Core difference: only set the DP[j] when (j - A[i - 1] >= 0 && DP[j - A[i - 1]]): since we are running from m ~ 0,
 * we don't want to override some existing values
 *
 * Reference:
 * https://github.com/cherryljr/NowCoder/blob/master/%E6%95%B0%E7%BB%84%E5%92%8C%E4%B8%BAsum%E7%9A%84%E6%96%B9%E6%B3%95%E6%95%B0.java
 */
public class Solution {
    /**
     * @param m: An integer m denotes the size of a backpack
     * @param A: Given n items with size A[i]
     * @return: The maximum size
     */
    public int backPack(int m, int[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }

        boolean[] dp = new boolean[m + 1];
        dp[0] = true;
        for (int i = 1; i <= A.length; i++) {
            // Guarantee the size is big enough to put A[i-1] into the backpack.
            for (int j = m; j >= A[i - 1]; j--) {
                dp[j] |= dp[j - A[i - 1]];
            }
        }
        for (int j = m; j >= 0; j--) {
            if (dp[j]) {
                return j;
            }
        }
        return 0;
    }
}