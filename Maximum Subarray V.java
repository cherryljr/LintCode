/*
Description
Given an integer arrays, find a contiguous subarray which has the largest sum
and length should be between k1 and k2 (include k1 and k2).
Return the largest sum, return 0 if there are fewer than k1 elements in the array.

Notice
Ensure that the result is an integer type.

Example
Given the array [-2,2,-3,4,-1,2,1,-5,3] and k1 = 2, k2 = 4, the contiguous subarray [4,-1,2,1] has the largest sum = 6.

Tags
Array Subarray
 */

/**
 * Approach: Deque
 * 这道题目经过了一点小小的修改，使得难度有了一定的上升。
 * 原本是：求长度 大于等于k 的最大子数组和。
 * 那样的话，我们可以用过 preSum + Kadane's Algorithm 来解决。
 * 方法与 Maximum Average Subarray II 中的处理方式相同
 * https://github.com/cherryljr/LintCode/blob/master/Maximum%20Average%20Subarray%20II.java
 *
 * 那么修改后的题目我们应该怎么做呢？
 * 首先，我们仍然先计算出前缀和数组 preSum[]
 * 那么任意一段子数组和就是 preSum[i] - preSum[j]
 * 我们要最大化 preSum[i] - preSum[j] 且满足条件 k1 <= i - j + 1 <= k2
 * 那么当 i 固定的时候，j的范围就是 i - k2 + 1 <= j <= i - k1 + 1
 * 因为 i 固定且 preSum[i] 已知，所以为了最大化，我们要找出[i - k2 + 1, i - k1 + 1] 区间内的最小值。
 * 所以我们可以使用 单调队列 来求出一个区间的最小值。
 * 即我们需要维护一个 单调递增的 双端队列。
 *
 * 如何维护一个 单调队列 可以参考：
 * https://github.com/cherryljr/LeetCode/blob/master/Sliding%20Window%20Maximum.java
 * https://github.com/cherryljr/NowCoder/blob/master/%E6%9C%80%E5%A4%A7%E5%80%BC%E5%87%8F%E5%8E%BB%E6%9C%80%E5%B0%8F%E5%80%BC%E5%B0%8F%E4%BA%8E%E7%AD%89%E4%BA%8Ek%E7%9A%84%E5%AD%90%E6%95%B0%E7%BB%84%E6%95%B0%E9%87%8F.java
 */
public class Solution {
    /**
     * @param nums: an array of integers
     * @param k1: An integer
     * @param k2: An integer
     * @return: the largest sum
     */
    public int maxSubarray5(int[] nums, int k1, int k2) {
        if (nums == null || nums.length < k1) {
            return 0;
        }

        Deque<Integer> deque = new LinkedList<>();
        int len = nums.length;
        int rst = Integer.MIN_VALUE;
        int[] preSum = new int[len + 1];
        for (int i = 1; i <= len; i++) {
            // 计算前缀和 preSum[]
            preSum[i] = preSum[i - 1] + nums[i - 1];
            // 当前长度已经超过 k2 了，因此需要移除队列头部的元素
            if (!deque.isEmpty() && deque.getFirst() < i - k2) {
                deque.removeFirst();
            }
            // 确保窗口大小是 >= k1 的
            if (i >= k1) {
                while (!deque.isEmpty() && preSum[deque.getLast()] >= preSum[i - k1]) {
                    deque.removeLast();
                }
                // 因为要求的是 sum,所以当 i >= k1 的时候，我们才需要向队列中添加元素
                // 注意：这里我们添加的是 subarray 的起始位置。而 i 代表的是 结束位置
                deque.addLast(i - k1);
            }
            // 当找到更大的子数组时，更新结果
            if (!deque.isEmpty() && preSum[i] - preSum[deque.getFirst()] > rst) {
                rst = preSum[i] - preSum[deque.getFirst()];
            }
        }

        return rst;
    }
}