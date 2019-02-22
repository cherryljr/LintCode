/*
Description
Given an array of integers, find the subarray with smallest sum.
Return the sum of the subarray.

The subarray should contain one integer at least.

Example
For [1, -1, -2, 1], return -3.
 */

/**
 * Approach: Kadane’s Algorithm
 * 与 Maximum Subarray 一样，可以采用 Kadane’s Algorithm 解决。
 * 主要就是利用到了 preSum。
 * 找到一个位置 k，p[k] - max(p[k- 1],p[k-2]....)，然後尋找這個最小值。
 * 这个最小值就是我们需要的答案。
 *
 * Reference:
 *  https://github.com/cherryljr/LintCode/blob/master/Maximum%20Subarray.java
 */
public class Solution {
    /*
     * @param nums: a list of integers
     * @return: A integer indicate the sum of minimum subarray
     */
    public int minSubArray(List<Integer> nums) {
        if (nums == null || nums.size() == 0) {
            return 0;
        }

        int minSum = Integer.MAX_VALUE, maxSum = 0;
        int sum = 0;
        for (int num : nums) {
            sum += num;
            minSum = Math.min(minSum, sum - maxSum);
            maxSum = Math.max(maxSum, sum);
        }

        return minSum;
    }
}