/*
Given an integer array, sort it in ascending order. Use quick sort O(nlogn) algorithm.

Example
Given [3, 2, 1, 4, 5], return [1, 2, 3, 4, 5].
*/

/**
 * Approach: Merge Sort
 * 归并操作(Merge)，也叫归并算法，指的是将两个已经排序的序列合并成一个序列的操作。
 * 归并排序算法依赖归并操作。归并排序有多路归并排序、两路归并排序 , 可用于内排序，也可以用于外排序。
 *
 * 算法思路：
 *  1. 把 n 个记录看成 n 个长度为 l 的有序子表
 *  2. 进行两两归并使记录关键字有序，得到 n/2 个长度为 2 的有序子表
 *  3. 重复第 2 步直到所有记录归并成一个长度为 n 的有序表为止。
 *
 * 时间复杂度为：O(nlogn)
 * 空间复杂度为：O(n)
 *
 * http://bubkoo.com/2014/01/15/sort-algorithm/merge-sort/
 */
public class Solution {
    /**
     * @param A: an integer array
     * @return: nothing
     */
    public void sortIntegers2(int[] A) {
        if (A == null || A.length < 2) {
            return;
        }

        mergeSort(A, 0, A.length - 1);
    }

    private void mergeSort(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }

        int mid = left + (right - left) / 2;
        // 归并排序 左半部分数组
        mergeSort(nums, left, mid);
        // 归并排序 右半部分数组
        mergeSort(nums, mid + 1, right);
        // 将排好序的左右部分归并起来
        merge(nums, left, mid, right);
    }

    private void merge(int[] nums, int left, int mid, int right) {
        // 用于存储外部排序后的数组(merge)
        int[] helper = new int[right - left + 1];

        int i = 0;
        // p1 指向 左半部分 数组的起始位置; p2 指向 右半部分 数组的起始位置
        int p1 = left, p2 = mid + 1;
        // 选择左右部分数组中较小的元素填在 helper[i] 位置
        // 然后 i 移动到下一个位置，同时 指向较小的元素的指针 也移动到下一个位置
        while (p1 <= mid && p2 <= right) {
            helper[i++] = nums[p1] < nums[p2] ? nums[p1++] : nums[p2++];
        }
        // 检查 左侧数组 是否还没遍历完
        while (p1 <= mid) {
            helper[i++] = nums[p1++];
        }
        // 检查 右侧数组 是否还没遍历完 (这两个 while 只有一个会被执行)
        while (p2 <= right) {
            helper[i++] = nums[p2++];
        }

        // 将 helper 中排好序的数据覆盖到原始数组 nums 中
        for (i = 0; i < helper.length; i++) {
            nums[left + i] = helper[i];
        }
    }
}