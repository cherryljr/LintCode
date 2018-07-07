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
 * Approach 1: Traversal
 * Single Number 最直接的思路，就是采用 异或运算 来求那个只出现一次的元素。
 * 但是题目还给了一个非常重要的限制条件 重复出现的元素都是 相邻 的。
 * 因此如果直接遍历全部的元素，肯定会超时，这点也在 Notice 中说明了。
 * 于是我们可以利用另一个条件进行稍微的优化：
 *  每个重复的元素出现且仅出现 两次，并且相邻。
 * 那么我们可以观察发现：
 *  Single Number 必定出现在 index 为偶数 的位置上。
 *  因为如果 nums[i] == nums[i+1] 此时这两个元素相同，组成一对，占去两个位置。
 *  故 Single Number 只能出现在这些数对之后。（同样出现在第一个也成立）
 * 因此我们可以对数组进行一次遍历，每次将 nums[i] 与 之后的那个元素进行比较，
 * 如果相等则组成一个数对， i += 2. 直到我们找到 ingle Number.
 * 
 * 时间复杂度：O(n/2) => O(n)
 * 空间复杂度：O(1)
 */
public class Solution {
    /**
     * @param nums: The number array
     * @return: Return the single number
     */
    public int getSingleNumber(int[] nums) {
        for (int i = 0; i < nums.length - 1; i += 2) {
            if (nums[i] != nums[i + 1]) {
                return nums[i];
            }
        }
        return -1;
    }
}

/**
 * Approach 2: Binary Search
 * Approach 1 中 O(n) 的方法虽然能够通过，但是表现并不好。
 * 因此我们想肯定存在 O(logn) 的方法。
 * 首先，我们可以考虑以 二分法 来解决这道问题。
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

/**
 * Approach 3: Binary Search (Optimized)
 * 对于 Approach 2 中的做法，其实并没有将 Approach 1 解法中的优点很好地继承下来。
 * 因为每个重复数字出现且仅出现两次，所以我们依旧可以利用这点在 二分 的时候省去一些判断。
 * 当选取的中点 mid 为 偶数 时，它应该跟其后面的一个元素进行比较。
 * 当其为 奇数 时，应该跟前一个元素进行比较。
 * 如果值相等，那说明在这之前的所有元素都是 成对出现 的。
 * 因此左边界向右移动 left + 1;
 * 如果值不相等，则说明 Single Number 必定出现在 左半部分，则 右边界 向左移动。
 * 
 * 这里在选取需要比较的元素时，使用了位运算的一个小 trick.
 * 当 mid 为奇数时，我们需要取的是 mid - 1;
 * 当 mid 为偶数时，我们需要取的是 mid + 1;
 * 这点与 mid ^ 1 效果是相同的（对 mid二进制 的最后一位进行改变）
 * 
 * 时间复杂度：O(logn)
 */
public class Solution {
    /**
     * @param nums: The number array
     * @return: Return the single number
     */
    public int getSingleNumber(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + ((right - left) >> 1);
            // int index = mid % 2 == 0 ? mid + 1 : mid - 1;
            int index = mid ^ 1;
            if (nums[mid] == nums[index]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return nums[left];
    }
}