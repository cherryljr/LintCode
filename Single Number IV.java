/*
Description
Give an array, all the numbers appear twice except one number
which appears once and all the numbers which appear twice are next to each other.
Find the number which appears once.

Notice
1 <= nums.length < 10^4
In order to limit the time complexity of the program, your program will run 10^5 times.

Example
Given nums = [3,3,2,2,4,5,5], return 4.

Explanation:
4 appears only once.
Given nums = [2,1,1,3,3], return 2.

Explanation:
2 appears only once.

Tags
Binary Search Google
 */

/**
 * Approach 1: Bit Operation and Traversal (Time Limit Exceeded)
 * Single Number 最直接的思路，就是采用 异或运算 来求那个只出现一次的元素。
 * 因为题目中说了 重复出现的元素都是 相邻 的。
 * 因此我们可以维持一个计数器 count，
 * 当两个数不想等时 count++，否则count--.
 * 当其值为 2 时，说明前一个数就是那个只出现一次的元素。然后直接跳出循环即可。
 *
 * 然而...超时了，是啊！这么特殊的数据状况怎么可能采用这种方法呢...
 * 我竟然 naive 地认为找到后退出时间会够...
 *
 * 时间复杂度为：O(n)
 */
class Solution {
    /**
     * @param nums: The number array
     * @return: Return the single number
     */
    public int getSingleNumber(int[] nums) {
        if (nums.length <= 1) {
            return nums[0];
        }

        int temp = nums[0], rst = 0;
        int count = 1;
        for (int i = 1; i < nums.length; i++) {
            temp ^= nums[i];
            if (temp == 0) {
                count--;
            } else {
                count++;
            }
            if (count == 2) {
                rst = i - 1;
                break;
            }
        }

        return nums[rst];
    }
}

/**
 * Approach 2: Binary Search
 * Approach 1 中 O(n) 的方法超时了，那么肯定就需要 O(logn) 的方法了。
 * 并且数据分布又如此特殊，因此我们可以考虑以 二分法 来解决这道问题。
 * 主要考点就是在于利用 重复的元素必是相邻的 这点来对二分后的情况进行讨论，
 * 从而确定边界的收缩。
 * 代码带有详细注释，直接看代码即可。
 */
public class Solution {
    /**
     * @param nums: The number array
     * @return: Return the single number
     */
    public int getSingleNumber(int[] nums) {
        if (nums.length <= 1) {
            return nums[0];
        }

        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + ((right - left) >> 1);
            if (nums[mid] == nums[mid - 1]) {
                // nums[mid] 与 其前面一个元素相等
                if (((mid - left + 1) & 1) == 1) {
                    // 如果 [left...mid] 这段数组上面元素的个数为 奇数 且 nums[mid] 已经等于 nums[mid-1]
                    // 因此 只出现一次的元素必定在 [left...mid-2] 这个范围内
                    right = mid - 2;
                } else {
                    // 否则 left 移动到 mid+1 位置
                    left = mid + 1;
                }
            } else if (nums[mid] == nums[mid + 1]) {
                // nums[mid] 与 其后面一个元素相等
                if (((right - mid + 1) & 1) == 1) {
                    // 如果 [mid...right] 这段数组上面元素的个数为 奇数 且 nums[mid] 已经等于 nums[mid+1]
                    // 因此 只出现一次的元素必定在 [mid+2...right] 这个范围内
                    left = mid + 2;
                } else {
                    // 否则 right 移动到 mid-1 位置
                    right = mid - 1;
                }
            } else {
                // nums[mid] 与左右相邻的两个数均不相等，则直接返回 nums[mid]
                return nums[mid];
            }
        }

        return nums[left];
    }
}