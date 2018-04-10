/*
Description
Given an array consisting of n integers, find the contiguous subarray of given length k that has the maximum average value.
And you need to output the maximum average value.

Notice
1 <= k <= n <= 30,000.
Elements of the given array will be in the range [-10,000, 10,000].

Example
Given nums = [1,12,-5,-6,50,3], k = 4, return 12.75.

Explanation:
Maximum average is (12-5-6+50)/4 = 51/4 = 12.75

Tags
Array Google
 */

/**
 * Approach: PreSum + Sliding Window
 * Algorithm
 *  Instead of creating a cumulative sum array first, and then traversing over it to determine the required sum,
 *  we can simply traverse over nums just once, and on the go keep on determining the sums possible for the subarrays of length k.
 *  To understand the idea, assume that we already know the sum of elements from index i to index i+k, say it is x.
 *
 *  Now, to determine the sum of elements from the index i+1 to the index i+k+1,
 *  all we need to do is to subtract the element nums[i] from x and to add the element nums[i+k+1] to x.
 *  We can carry out our process based on this idea and determine the maximum possible average.
 *
 * Complexity Analysis
 *  Time complexity  : O(n). We iterate over the given numsnums array of length n once only.
 *  Space complexity : O(1). Constant extra space is used.
 */
public class Solution {
    /**
     * @param nums: an array
     * @param k: an integer
     * @return: the maximum average value
     */
    public double findMaxAverage(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0d;
        }

        double preSum = 0;
        for (int i = 0; i < k; i++) {
            preSum += nums[i];
        }
        double max = preSum / k;
        for (int i = k; i < nums.length; i++) {
            preSum = preSum - nums[i - k] + nums[i];
            max = Math.max(max, preSum / k);
        }

        return max;
    }
}