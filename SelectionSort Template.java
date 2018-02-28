/*
Given an integer array, sort it in ascending order. Use bubble sort.

Example
Given [3, 2, 1, 4, 5], return [1, 2, 3, 4, 5].

Tags 
Sort
 */

/**
 * Approach: Selection Sort
 * 选择排序（Selection Sort）是一种简单直观的排序算法。它的工作原理如下：
 * 首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置，
 * 然后，再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。
 * 以此类推，直到所有元素均排序完毕。
 *
 * 选择排序的主要优点与数据移动有关。
 * 如果某个元素位于正确的最终位置上，则它不会被移动。
 * 选择排序每次交换一对元素，它们当中至少有一个将被移到其最终位置上，因此对n个元素的序列进行排序总共进行至多n-1次交换。
 *
 * http://bubkoo.com/2014/01/13/sort-algorithm/selection-sort/
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

        for (int i = 0; i < nums.length - 1; i++) {
            int minIndex = i;
            // 在 未被排序 的部分中寻找 最小值
            for (int j = i + 1; j < nums.length; j++) {
                minIndex = nums[j] < nums[minIndex] ? j : minIndex;
            }
            // 将 最小值 换到 已经排序好 的序列的末尾
            swap(nums, i, minIndex);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}