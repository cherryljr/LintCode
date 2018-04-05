/*
Description
Given an integer array, find a continuous subarray where the sum of numbers is the biggest.
Your code should return the index of the first number and the index of the last number.
(If their are duplicate answer, return anyone)

Example
Give [-3, 1, 3, -3, 4], return [1,4].

Tags
Subarray Array Facebook
 */

/**
 * Approach: Kadane’s Algorithm
 * 这道题目与 Maximum Subarray 基本相同，区别在于本题我们需要返回 子数组的下标。
 * 而我们知道只有在更新 max 和 sum == 0 的时候，子数组的i下标才会发生变化。
 * 因此我们在这两个地方进行记录即可。
 *
 * Maximum Subarray:
 * https://github.com/cherryljr/LintCode/blob/master/Maximum%20Subarray.java
 */
public class Solution {
    /*
     * @param A: An integer array
     * @return: A list of integers includes the index of the first number and the index of the last number
     */
    public List<Integer> continuousSubarraySum(int[] A) {
        if (A == null || A.length == 0) {
            return new ArrayList<>();
        }

        int left = 0;
        long sum = 0, max = Long.MIN_VALUE;
        int[] rst = new int[2];
        for (int i = 0; i < A.length; i++) {
            sum += A[i];
            // 当找到了和更大的子数组，我们就对 maxSubArray 的下标进行更新
            if (sum > max) {
                max = sum;
                rst[0] = left;
                rst[1] = i;
            }
            // 当 sum < 0 说明前面的部分只会造成消极影响（使得子数组和更小）
            // 因此我们需要舍弃掉它们，因此 left =  + 1
            if (sum < 0) {
                sum = 0;
                left = i + 1;
            }
        }

        return Arrays.asList(rst[0], rst[1]);
    }
}