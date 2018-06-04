/*
Description
Given an unsorted array nums, reorder it in-place such that
nums[0] <= nums[1] >= nums[2] <= nums[3]....
Please complete the problem in-place.

Example
Given nums = [3, 5, 2, 1, 6, 4], one possible answer is [1, 6, 2, 5, 3, 4].
 */

/**
 * Approach 1: QuickSort
 * 根据题目的定义，摇摆排序的方法将会很多种。
 * 我们可以先将数组排序，然后从第3个元素开始，将第3个元素和第2个元素交换。
 * 然后再从第5个元素开始，将第5个元素和第4个元素交换，以此类推。
 * 这样交换之后的结果就能满足题目要求。
 *
 * 时间复杂度：O(nlogn)
 * 空间复杂度：O(1) (这里不计算快速排序所需要的空间)
 */
public class Solution {
    /*
     * @param nums: A list of integers
     * @return: nothing
     */
    public void wiggleSort(int[] nums) {
        Arrays.sort(nums);
        for (int i = 2; i < nums.length; i += 2) {
            int temp = nums[i - 1];
            nums[i - 1] = nums[i];
            nums[i] = temp;
        }
    }
}

/**
 * Approach 2: Discover the rule (Swap numbers)
 * 题目对摇摆排序的定义有两部分：
 *  如果i是奇数，nums[i] >= nums[i - 1]
 *  如果i是偶数，nums[i] <= nums[i - 1]
 * 所以我们只要遍历一遍数组，把不符合的情况交换一下就行了。
 * 因为当 nums[i] > nums[i - 1] 时， 则交换以后肯定有 nums[i] < nums[i - 1]。
 * 那么根据上述结论，我们需要进行交换的情况有如下两种：
 *  1. i为奇数，且 nums[i] < nums[i - 1]
 *  2. i位偶数，且 nums[i] > nums[i - 1
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
public class Solution {
    /*
     * @param nums: A list of integers
     * @return: nothing
     */
    public void wiggleSort(int[] nums) {
        Arrays.sort(nums);
        for (int i = 1; i < nums.length; i++) {
            if (((i & 1) == 1 && nums[i] < nums[i - 1])
                    || ((i & 1) == 0 && nums[i] > nums[i - 1])) {
                int temp = nums[i - 1];
                nums[i - 1] = nums[i];
                nums[i] = temp;
            }
        }
    }
}