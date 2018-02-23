/*
Description
You are a professional robber planning to rob houses along a street. 
Each house has a certain amount of money stashed, the only constraint stopping you from robbing each of them is that
adjacent houses have security system connected and it will automatically contact the police 
if two adjacent houses were broken into on the same night.

Given a list of non-negative integers representing the amount of money of each house, 
determine the maximum amount of money you can rob tonight without alerting the police.

Example
Given [3, 8, 4], return 8.

Challenge 
O(n) time and O(1) memory.

Tags 
Dynamic Programming LinkedIn Airbnb
*/

/***************************************** Main Idea 1 *****************************************/

/**
 * Approach 1: Sequence DP
 * We can't rob two adjacent houses, and the money of each house must be non-negative.
 * So we could use dp[i] to represent that:
 * the max profit could be robbed form the first i house.
 * The Function is:
 *  dp[i] = Math.max(dp[i - 2] + A[i-1], dp[i - 1])
 * The answer is:
 *  dp[A.length]
 * 
 * Time Complexity: O(n); Space Complexity: O(1)
 *  
 * Note: Remember to Initialize the dp array.
 */
public class Solution {
    /*
     * @param A: An array of non-negative integers
     * @return: The maximum amount of money you can rob tonight
     */
    public long houseRobber(int[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }

        long[] dp = new long[A.length + 1];
        // Initialize the first two status
        // which couldn't be deduce from previous status.
        dp[0] = 0;
        dp[1] = A[0];
        for (int i = 2; i <= A.length; i++) {
            dp[i] = Math.max(dp[i - 2] + A[i-1], dp[i - 1]);
        }

        return dp[A.length];
    }
}

/**
 * Approach 2: Sequence DP (Optimized By Sliding Array)
 * The current status is determined by dp[i-1] and dp[i-2], we don't care about the other status,
 * so we just need to save the two status.
 * It means that the size of array could be reduce from O(n) to O(2) by using the modulo operator %.
 * So the function is:
 *  dp[i % 2] = Math.max(dp[(i-2) % 2] + A[i-1], dp[(i-1) % 2])
 *  (i%2 could be optimized as i&1 to speed up the operation)
 * And the result is:
 *  dp[A.length % 2]
 *
 * Time Complexity: O(n); Space Complexity: O(1)
 */
public class Solution {
    /*
     * @param A: An array of non-negative integers
     * @return: The maximum amount of money you can rob tonight
     */
    public long houseRobber(int[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }

        long[] dp = new long[2];
        dp[0] = 0;
        dp[1] = A[0];
        for (int i = 2; i <= A.length; i++) {
            dp[i & 1] = Math.max(dp[(i-2) & 1] + A[i-1], dp[(i-1) & 1]);
        }

        return dp[A.length & 1];
    }
}

/***************************************** Main Idea 2 *****************************************/

/**
 * Approach 1: Sequence DP
 * Basic DP question, we only need to analyze the status that
 * the current house is robbed or not.
 *  if rob current house, previous house must not be robbed
 *  if not rob the current house, take the max value of robbed (i-1)th house and not rob (i-1)th house
 *
 * Time Complexity: O(n); Space Complexity: O(n)
 */
public class Solution {
    /*
     * @param A: An array of non-negative integers
     * @return: The maximum amount of money you can rob tonight
     */
    public long houseRobber(int[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }

        // dp[i][1] means we rob the current house and dp[i][0] means we don't.
        long[][] dp = new long[A.length + 1][2];
        for (int i = 1; i <= A.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]);
            dp[i][1] = A[i - 1] + dp[i - 1][0];
        }

        return Math.max(dp[A.length][0], dp[A.length][1]);
    }
}

/**
 * Approach 2: Sequence DP (Optimized)
 * Above all, we can convert this to O(1) space easily
 * 
 * Time Complexity: O(n); Space Complexity: O(1)
 */
public class Solution {
    /*
     * @param A: An array of non-negative integers
     * @return: The maximum amount of money you can rob tonight
     */
    public long houseRobber(int[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }

        // max monney can get if rob current house
        long rob = 0;
        // max money can get if not rob current house
        long notrob = 0;    
        for(int i = 0; i < A.length; i++) {
            // if rob current value, previous house must not be robbed
            long currob = notrob + A[i];
            //if not rob ith house, take the max value of robbed (i-1)th house and not rob (i-1)th house
            notrob = Math.max(notrob, rob);
            rob = currob;
        }

        return Math.max(rob, notrob);
    }
}

