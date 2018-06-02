/*
Description
Given an array with positive and negative integers.
Re-range it to interleaving with positive and negative integers.

You are not necessary to keep the original order of positive integers or negative integers.

Example
Given [-1, -2, -3, 4, 5, 6], after re-range, it will be [-1, 5, -2, 4, -3, 6] or any other reasonable answer.

Challenge
Do it in-place and without extra memory.
 */

/**
 * Approach: Partition + Two Pointers
 * 题目要求我们将正负数交叉排列，并且要求 O(n) 的时间复杂度 和 O(1) 的空间复杂度。
 * 因此我们首先需要将正负数分开来，然后根据分开好的这个情况进行 交叉 操作。
 * 对此我们很容易想到使用 Partition 操作 + Two Pointers 来解决这道问题。
 *
 * 这里我们采取将 负数 排在左边，正数 排在右边的操作。（当然你可以倒过来）
 * Partition后，数组被分成了两个部分，这时候我们需要进行一次分情况讨论。
 *  当 负数个数 多于 正数个数 时，第一个数应该放负数，即偶数位应该放负数(以index为准)
 *  因此我们从 第二个数 开始与倒数第一个数进行 swap 操作
 *  当 负数个数 等于 正数个数 时，第一个数正负无所谓，这里我们选择偶数位放正数，
 *  因此从 第一个数 开始与 倒数第一个数 进行 swap 操作
 *  当 负数个数 小于 正数个数 时，第一个数应该放正数，即偶数位应该放正数
 *  因此从 第一个数 开始与 倒数第二个 进行swap 操作
 */
class Solution {
    /*
     * @param A: An integer array.
     * @return: nothing
     */
    public void rerange(int[] A) {
        if (A == null || A.length <= 2) {
            return;
        }

        int len = A.length;
        // 进行 partition 操作，并求得当前有多少个负数
        int position = partition(A);
        if (position < len - position) {
            // 负数个数较少
            interleave(A, 0, len - 2);
        } else if (position == len - position) {
            // 正负数个数相等
            interleave(A, 0, len - 1);
        } else {
            // 负数个数较多
            interleave(A, 1, len - 1);
        }
    }

    private int partition(int[] nums) {
        int less = -1, more = nums.length;
        int index = 0;
        while (index < more) {
            if (nums[index] < 0) {
                swap(nums, ++less, index++);
            } else {
                swap(nums, --more, index);
            }
        }
        return less + 1;
    }

    private void interleave(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start += 2;
            end -= 2;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}