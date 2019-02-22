/*
Description
Given an array of integers, find a contiguous subarray which has the largest sum.
The subarray should contain at least one number.

Example
Example1:
Given the array [−2,2,−3,4,−1,2,1,−5,3], the contiguous subarray [4,−1,2,1] has the largest sum = 6.

Example2:
Given the array [1,2,3,4], the contiguous subarray [1,2,3,4] has the largest sum = 10.

Challenge
Can you do it in time complexity O(n)?
 */

/**
 * Approach 1: Greedy
 * 与 Best Time to Buy and Sell Stock I 实质上是相同的。
 * 但是需要注意的是 Stock I 中，如果股票一直在跌我们可以不买使得 profit 最大值为 0.
 * 但是在本题中，如果数组中元素均为负数，Maximum Subarray 是可能为负数的。
 * 因此初始化的时候，max = Integer.MIN_VALUE.
 * 并且要注意 sum += i 之后是先与 max 比较得到 max，
 * 然后再将 sum 与 0 比较，得到下一个用于相加的值。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
public class Solution {
    /*
     * @param nums: A list of integers
     * @return: A integer indicate the sum of max subarray
     */
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int sum = 0;
        int maxSum = Integer.MIN_VALUE;
        for (int i : nums) {
            sum += i;
            maxSum = Math.max(maxSum, sum);
            sum = Math.max(sum, 0);
        }

        return maxSum;
    }
}

/**
 * Approach 2: Kadane’s Algorithm (Prefix Sum)
 * i 到 j 的和可以用 前j个 数的和减去 前i-1 个数的和 来表示。
 * 故我们设置三个变量：
 *  sum 表示前i个数的和;
 *  maxSum 表示当前最大和;
 *  minSum 表示当前最小和。
 * 每次操作指针向后移动一位，考虑要不要新加入这个数时，
 * 要比较 不加入这个数的maxSum 和 加入这个数后总共的和减去最小的和的sum-minSum 这两个数谁大。
 * 最小的和：最小和初始化为0，当指针后移，判断新的数字是否加入这个和时，
 * 要比较不加入这个数的 minSum 和加入这个数的 minSum 谁小，即这个数有没有使最小的和更小，若有则更新最小数。
 * 核心思想是剔除对最大和有副作用的。
 * 进一步详解可以参见：http://blog.csdn.net/u012255731/article/details/52302189
 *
 * 时间复杂度：O(N)
 * 空间复杂度：O(1)
 */
public class Solution {
    /*
     * @param nums: A list of integers
     * @return: A integer indicate the sum of max subarray
     */
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0){
            return 0;
        }

        int maxSum = Integer.MIN_VALUE, minSum = 0;
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            maxSum = Math.max(maxSum, sum - minSum);
            minSum = Math.min(minSum, sum);
        }

        return maxSum;
    }
}