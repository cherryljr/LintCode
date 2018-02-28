/*
Given an integer array, sort it in ascending order. Use bubble sort.

Example
Given [3, 2, 1, 4, 5], return [1, 2, 3, 4, 5].

Tags 
Sort
 */

/**
 * Approach: Insertion Sort
 * 具体算法描述如下：
 *  1. 从第一个元素开始，该元素可以认为已经被排序
 *  2. 取出下一个元素，在已经排序的元素序列中从后向前扫描
 *  3. 如果该元素（已排序）大于新元素，将该元素移到下一位置
 *  4. 重复步骤 3，直到找到已排序的元素小于或者等于新元素的位置
 *  5. 将新元素插入到该位置后
 *  6. 重复步骤 2~5
 * 时间复杂度为：O(n^2) 与数据状态有关
 * 如果数据本来就有序，则时间复杂度为：O(n) （最佳情况）
 *
 * 如果比较操作的代价比交换操作大的话，可以采用二分查找法来减少比较操作的数目。
 * 该算法可以认为是插入排序的一个变种，称为二分查找排序。
 *
 * http://bubkoo.com/2014/01/14/sort-algorithm/insertion-sort/
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

        for (int i = 1; i < nums.length; i++) {
            for (int j = i - 1; j >= 0 && nums[j] > nums[j + 1]; j++) {
                swap(nums, j, j + 1);
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}