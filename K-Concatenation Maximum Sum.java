/*
Given an integer array arr and an integer k, modify the array by repeating it k times.
For example, if arr = [1, 2] and k = 3 then the modified array will be [1, 2, 1, 2, 1, 2].
Return the maximum sub-array sum in the modified array.
Note that the length of the sub-array can be 0 and its sum in that case is 0.

As the answer can be very large, return the answer modulo 10^9 + 7.

Example 1:
Input: arr = [1,2], k = 3
Output: 9

Example 2:
Input: arr = [1,-2,1], k = 5
Output: 2

Example 3:
Input: arr = [-1,-2], k = 7
Output: 0

Constraints:
    1. 1 <= arr.length <= 10^5
    2. 1 <= k <= 10^5
    3. -10^4 <= arr[i] <= 10^4
 */

/**
 * Approach: Kadane’s Algorithm
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 *
 * Reference:
 *  https://www.bilibili.com/video/av68050148
 */
class Solution {
    private static final int MOD = 1000000007;

    public int kConcatenationMaxSum(int[] arr, int k) {
        if (k < 3) return getMaxSum(arr, k);

        int maxSum1 = getMaxSum(arr, 1), maxSum2 = getMaxSum(arr, 2);
        long sum = 0;
        for (int num : arr) sum += num;
        return Math.max(Math.max(maxSum1, maxSum2), (int)((maxSum2 + (k - 2) * sum) % MOD));
    }

    private int getMaxSum(int[] arr, int n) {
        // 根据题目数据规模，arr 中的元素值大小和个数不会超过 10^5
        // 所以这里使用 int 并不会发生越界的情况，避免了类型转换和取模操作，也使得代码更加简洁一些。
        int max = 0, sum = 0, min = 0;
        for (int i = 0; i < n; i++) {
            for (int num : arr) {
                sum += num;
                max = Math.max(max, sum - min);
                min = Math.min(min, sum);
            }
        }
        return max;
    }
}