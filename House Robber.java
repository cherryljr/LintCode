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

Basic DP question, we only need to analyze the status that
the current house is robbed or not.
    if rob current house, previous house must not be robbed
    if not rob the current house, take the max value of robbed (i-1)th house and not rob (i-1)th house
    
// Approach 1: DP
// O(n) time, O(n) space
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

// Above all, we can convert this to O(1) space easily
// Approach 2: DP
// O(n) time, O(1) space
public class Solution {
    /*
     * @param A: An array of non-negative integers
     * @return: The maximum amount of money you can rob tonight
     */
    public long houseRobber(int[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }
        
        long rob = 0;   // max monney can get if rob current house
        long notrob = 0;    // max money can get if not rob current house
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

