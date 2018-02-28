/*
Given an integer array, sort it in ascending order. Use bubble sort.

Example
Given [3, 2, 1, 4, 5], return [1, 2, 3, 4, 5].

Tags 
Sort
 */

/**
 * Approach: Bubble Sort
 * 两两比较，如果后面的数比前面的数要大，则交换这两个数，否则继续。
 * 这样一轮下去，便可以确定 最后一个数（最大的数） 是多少。
 * 然后除去最后一个数，再次从头到尾进行 两两比较，第二轮结束，我们便可以确定 第二大的元素。
 * 依次循环下去，直到 待比较的只有一个数时，说明排序完成。
 * 时间复杂度为：O(n^2) 与数据状况无关
 *
 * http://bubkoo.com/2014/01/12/sort-algorithm/bubble-sort/
 */
public class Solution {
    /*
     * @param A: an integer array
     * @return:
     */
    public void sortIntegers(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        // 每确定一个最大值 end--
        for (int end = nums.length - 1; end > 0; end--) {
            // 两两比较 nums[i] 和 nums[i+1] 直到 end 位置
            for (int i = 0; i < end; i++) {
                // 如果前的数比后面的数大，交换二者位置
                if (nums[i] > nums[i + 1]) {
                    swap(nums, i, i + 1);
                }
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        nums[i] ^= nums[j];
        nums[j] ^= nums[i];
        nums[i] ^= nums[j];
    }
}