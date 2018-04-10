/*
Description
Given an array with positive and negative numbers,
find the maximum average subarray which length should be greater or equal to given length k.

Notice
It's guaranteed that the size of the array is greater or equal to k.

Example
Given nums = [1, 12, -5, -6, 50, 3], k = 3
Return 15.667 // (-6 + 50 + 3) / 3 = 15.667

Tags
Binary Search Subarray Google
 */

/**
 * Approach: Binary Search + Kadane's Algorithm
 * Firstly, we know that the value of the average could lie between the range (min, max).
 * Here, min and max refer to the minimum and the maximum values out of the given nums array.
 * This is because, the average can't be lesser than the minimum value and can't be larger than the maximum value.
 * But, in this case, we need to find the maximum average of a subarray with at least k elements.
 * The idea in this method is to try to approximate(guess) the solution and to try to find if this solution really exists.
 * If it exists, we can continue trying to approximate the solution even to a further precise value,
 * but choosing a larger number as the next approximation.
 * But, if the initial guess is wrong, and the initial maximum average value(guessed) isn't possible,
 * we need to try with a smaller number as the next approximate.
 *
 * Now, instead of doing the guesses randomly, we can make use of Binary Search.
 * With min and max as the initial numbers to begin with, we can find out the mid of these two numbers given by (min+max)/2.
 * Now, we need to find if a subarray with length greater than or equal to k is possible with an average sum greater than this mid value.
 * To determine if this is possible in a single scan, let's look at an observation.
 * Suppose, there exist j elements, a1, a2, a3..., aj
 * ​​in a subarray within nums such that their average is greater than mid. In this case, we can say that
 *      (a1+a2+a3...+aj)/j ≥ mid or
 *      (a1+a2+a3...+aj) ≥ j*mid or
 *      (a1−mid)+(a2−mid)+(a3−mid)...+(aj−mid) ≥ 0  (our code using this method)
 * Thus, we can see that if after subtracting the mid number from the elements of a subarray with more than k−1 elements,
 * within nums, (Here we use Kadane's Algorithm)
 * if the sum of elements of this reduced subarray is greater than 0, we can achieve an average value greater than mid.
 * Thus, in this case, we need to set the mid as the new minimum element and continue the process.
 * Otherwise, if this reduced sum is lesser than 0 for all subarrays with greater than or equal to k elements,
 * we can't achieve mid as the average.
 * Thus, we need to set mid as the new maximum element and continue the process.
 *
 * Every time after checking the possibility with a new mid value, at the end, we need to settle at some value as the average.
 * But, we can observe that eventually, we'll reach a point, where we'll keep moving near some same value with very small changes.
 * In order to keep our precision in control, we limit this process to 1e-6 precision,
 * by making use of error and continuing the process till error becomes lesser than 0.000001 .
 *
 * Get more detail about Kadane's Algorithm here:
 * https://github.com/cherryljr/LintCode/blob/master/Maximum%20Subarray.java
 * Good Explanation here:
 * https://mp.weixin.qq.com/s?__biz=MzA5MzE4MjgyMw==&mid=2649458133&idx=1&sn=a27ec0ef7e2a871959598d19ba876573&chksm=887eebddbf0962cb491c35dade815424bc375cf4309929b4d6472eadebeb9561bbbeb411f5e4&mpshare=1&scene=1&srcid=0816fQEJf75jmt6zpk5Tn8W7&key=02a45e2d696653c0434edec9cf3297051d6b8b6658d00ceb1eb3d48a922a7b419b2d5d40fb4ed1ebe236b29e34636adce6272f73544d3dab66694eaf38927b72f2a0efa6549bc173a7881fe7f0b5ff49&ascene=0&uin=Mjg3NzU0NTM4MA%3D%3D&devicetype=iMac+MacBook9%2C1+OSX+OSX+10.12.6+build(16G29)&version=12020810&nettype=WIFI&fontScale=100&pass_ticket=XtCmvn2oZ%2BRx1xG9qCLAeSWvYYd04dPLV2OA7Ozh5i9TyZ71w%2FW9ciFosEayuRbw
 */
public class Solution {
    /*
     * @param nums: an array with positive and negative numbers
     * @param k: an integer
     * @return: the maximum average
     */
    public double maxAverage(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0d;
        }

        int len = nums.length;
        double min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        // get the minimum and maximum element of this array, so we can do binary search based on them.
        for (int i = 0; i < len; i++) {
            min = Math.min(min, (double)nums[i]);
            max = Math.max(max, (double)nums[i]);
        }

        double[] preSum = new double[len + 1];
        // Binary Search
        while (max - min > 1e-6) {
            double mid = min + (max - min) / 2;
            // calculate the prefix sum array (substract mid)
            for (int i = 0; i < len; i++) {
                preSum[i + 1] = preSum[i] + nums[i] - mid;
            }

            // Kadane's Algorithm
            // Using prefix sum to get the biggest sum subarray (length is no smaller than k)
            double maxSum = Integer.MIN_VALUE;
            double minSum = 0;
            for (int i = k; i <= len; i++) {
                maxSum = Math.max(maxSum, preSum[i] - minSum);
                minSum = Math.min(minSum, preSum[i - k + 1]);
            }
            if (maxSum >= 0) {
                min = mid;
            } else {
                max = mid;
            }
        }

        return max;
    }
}
