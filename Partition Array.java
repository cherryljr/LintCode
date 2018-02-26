/*
Given an array nums of integers and an int k, partition the array (i.e move the elements in "nums") such that:

All elements < k are moved to the left
All elements >= k are moved to the right
Return the partitioning index, i.e the first index i nums[i] >= k.

Example
If nums=[3,2,2,1] and k=2, a valid answer is 1.

Note
You should do really partition in array nums instead of just counting the numbers of integers smaller than k.

If all elements in nums are smaller than k, then return nums.length

Challenge
Can you partition the array in-place and in O(n)?

Tags Expand 
Two Pointers Sort Array
*/

/**
 * Approach: QuickSelect (Partition)
 * 快速排序的思想，整个程序其实就是快速排序过程中的一次排序罢了。
 * 具体解释可以参考：Sort Colors (荷兰国旗问题)
 * https://github.com/cherryljr/LintCode/blob/master/Sort%20Colors.java
 *
 * 算法复杂度为：O(N)
 */
public class Solution {
    /**
     *@param nums: The integer array you should partition
     *@param k: As description
     *return: The index after partition
     */
    public int partitionArray(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        return partition(nums, 0, nums.length - 1, k);
    }

    private int partition(int[] nums, int left, int right, int k) {
        // 初始化 小于k 和 大于k 部分数组的边界
        int less = left - 1, more = right + 1;

        // 当 left 指针遇到 大于k部分数组的 左边界 时停止遍历
        while (left < more) {
            if (nums[left] < k) {
                swap(nums, ++less, left++);
            } else {
                swap(nums, --more, left);
            }
        }

        return less + 1;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
