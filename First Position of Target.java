/*
For a given sorted array (ascending order) and a target number, find the first index of this number in O(log n) time complexity.
If the target number does not exist in the array, return -1.

Example
If the array is [1, 2, 3, 3, 4, 5, 10], for given target 3, return 2.

Challenge 
If the count of numbers is bigger than 2^32, can your code work properly?

Tags 
Binary Search Array
*/

/**
 * Approach: Binary Search
 * 使用 二分法 的模板即可轻松解决
 *
 * Binary Search Template:
 * https://github.com/cherryljr/NowCoder/blob/master/%E6%95%B0%E5%AD%97%E5%9C%A8%E6%8E%92%E5%BA%8F%E6%95%B0%E7%BB%84%E4%B8%AD%E5%87%BA%E7%8E%B0%E7%9A%84%E6%AC%A1%E6%95%B0.java
 */
public class Solution {
    /**
     * @param nums: The integer array.
     * @param target: Target to find.
     * @return: The first position of target. Position starts from 0.
     */
    public int binarySearch(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int pos = lowerBound(nums, target);
        if (nums[pos] == target) {
            return pos;
        } else {
            return -1;
        }
    }

    private int lowerBound(int[] nums, int target) {
        // 左闭右开
        // left负责一步步向 right 逼近寻找答案; right 只负责缩小范围
        int left = 0, right = nums.length;
        while (left < right) {
            // 中点选取在靠 起点 的一端（求上界方法里的话是 right,因此向上取整）
            int mid = left + ((right - left) >> 1);
            if (target <= nums[mid]) {
                // 当 target <= nums[mid] 时，说明 符合条件，即 下界 存在于 [left, mid] 中
                // 因此我们可以直接去掉 [mid+1, right] 这个部分，即 right 移动到 mid. (mid有可能是最终结果)
                right = mid;
            } else {
                // 否则，我们可以直接去除 [left, mid] 部分，因为他们肯定是不符合条件的。
                // 即 left 移动到 mid+1,靠近 right 来寻找答案
                left = mid + 1;
            }
        }

        return left;
    }
}