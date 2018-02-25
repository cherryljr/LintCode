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
 * Approach: QuickSelect
 * 与 Partition Array / Sort Letters by Case 属于同一类题
 * 不同在于该题中需要针对 3 个元素进行排序。
 * 因此我们需要 3 个指针来进行排序，分别代表 0, 1, 2 即 red, white, blue
 * 实际上就是 荷兰国旗问题。
 * 属于经过优化的 快速排序 的 partition 方法。
 * （将数组由原来的分成 2 个部分，改成分为 3 个部分。分别为：<pivot; =pivot; >pivot）
 *
 * 具体做法:
 * start 指向头节点 red , i 从头节点开始向后遍历 white, end 指向最后一个节点 blue
 * 当 a[i] = 0 时，说明为 red, 应排在最前面，故 swap(a[start], a[i])
 * 当 a[i] = 1 时，说明为 white, 应排在中间，故 i 继续向后移动
 * 当 a[i] = 2 时，说明为 blue, 应排在末尾，故 swap(a[i], a[end])
 * 直至 i 与 end 相遇或相交，则结束遍历。
 * 注意点：
 *  本题的 pivot 是外界给定的，所以我们需要一直遍历到 end (i <= end).
 *  而在 quickSort 中，我们的 pivot 选取的就是 a[end].
 *  因此我们只需要遍历到 pivot的右边界 即可 (i < right)，并且在 swap 的时候，是 swap(nums, i, --end).
 *  而在本题中，swap 的对象则是 swap(nums, i, end--).
 *  原因就是在于：
 *      quickSort 中的 pivot 已经被选定为 a[end].我们需要保持 a[end] 为 pivot.
 *      因此对于 >pivot 的元素，我们都会放在 (right, end-1) 上面。
 *      直到最后一步，将 等于pivot部分的右边界后的那个数 与 a[end](pivot) 交换，以完成 partition.
 *
 * 算法时间复杂度为 O(N)； 时间复杂度为 O(1)
 */
class Solution {
    /**
     * @param nums: A list of integer which is 0, 1 or 2
     * @return: nothing
     */
    public void sortColors(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }

        int start = 0;
        int end = nums.length - 1;
        int i = 0;
        while (i <= end) {
            if (nums[i] == 0) {
                swap(nums, start++, i++);
            } else if (nums[i] == 1) {
                i++;
            } else {
                // 交换后 a[i] 位置上的数可能为 0 或 1,故需要再进行一次判断，不能进行 i++ 的操作
                swap(nums, i, end--);
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
