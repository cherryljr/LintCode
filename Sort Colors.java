/*
Description
Given an array with n objects colored red, white or blue, sort them so that objects of the same color are adjacent, 
with the colors in the order red, white and blue.
Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.

Notice
You are not suppose to use the library's sort function for this problem. 
You should do it in-place (sort numbers in the original array).

Example
Given [1, 0, 1, 2], sort it in-place to [0, 1, 1, 2].

Challenge 
A rather straight forward solution is a two-pass algorithm using counting sort.
First, iterate the array counting number of 0's, 1's, and 2's, then overwrite array with total number of 0's, then 1's and followed by 2's.
Could you come up with an one-pass algorithm using only constant space?

Tags 
Sort Array Two Pointers Facebook
*/

/**
 * Approach: QuickSelect | Partition
 * 与 Partition Array / Sort Letters by Case 属于同一类题
 * 不同在于该题中需要针对 3 个元素进行排序。
 * 因此我们需要 3 个指针来进行排序，分别代表 0, 1, 2 即 red, white, blue
 * 实际上就是 荷兰国旗问题。
 * 属于经过优化的 快速排序 的 partition 方法。
 * （将数组由原来的分成 2 个部分，改成分为 3 个部分。分别为：<pivot; =pivot; >pivot）
 *
 * 具体做法:
 * 选定 pivot 为 white,大小为1.
 * 定义 小于pivot部分的数组(red)的右边界 less 和 大于pivot部分数组(blue)的左边界 more.
 * 然后利用指针 left 从 left 开始向 right 进行遍历。(直接利用了 left 的空间，省些空间哈）
 *  当 nums[left] = 0 时，说明为 red, 应将该元素放在 小于pivot部分 中，
 *  因此将 小于pivot部分的 下一个位置 与 nums[left] 进行交换（小于pivot部分的数组扩大） 即 swap(nums[++less], nums[left++])
 *  当 nums[left] = 1 时，说明为 white, 应将该元素放在 等于pivot部分 中，故 left 继续向后移动 (left++)
 *  当 nums[left] = 2 时，说明为 blue, 应将该元素放在 大于pivot部分 中，
 *  因此将 大于pivot部分的 前一个位置 与 nums[left] 进行交换（大于pivot部分的数组扩大）故 swap(nums[--more], nums[left])
 *  注意：交换后 nums[left] 位置上的数大小并不能确定(可能为 0,1,2),故需要再次进行判断，不能进行 left++ 的操作.
 *  直至 left 与 more 相遇，说明已经遍历到大于pivot部分数组的边界了，则结束遍历。
 *
 * 算法时间复杂度为 O(N)； 时间复杂度为 O(1)
 */
public class Solution {
    /**
     * @param nums: A list of integer which is 0, 1 or 2
     * @return: nothing
     */
    public void sortColors(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }

        sortColorsHelper(nums, 0, nums.length - 1);
    }

    private void sortColorsHelper(int[] nums, int left, int right) {
        int less = left - 1;    // 小于pivot部分的 右边界
        int more = right + 1;   // 大于pivot部分的 左边界

        while (left < more) {
            if (nums[left] == 0) {
                // 将 小于pivot部分的 下一个位置 与 nums[i] 进行交换（小于pivot部分的数组扩大）
                swap(nums, ++less, left++);
            } else if (nums[left] == 1) {
                // 当 nums[i]==pivot 的时候，直接将 i 移动到下一个位置即可
                left++;
            } else {
                // 将 大于pivot部分的 前一个位置 与 nums[i] 进行交换（大于pivot部分的数组扩大）
                // 交换后 a[i] 位置上的数大小并不能确定(可能为 0,1,2),故需要再次进行判断，不能进行 i++ 的操作
                swap(nums, --more, left);
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}

