/*
Description
Given an array with n integers, you need to find if there are triplets (i, j, k) which satisfies following conditions:
0 < i, i + 1 < j, j + 1 < k < n - 1
Sum of subarrays (0, i - 1), (i + 1, j - 1), (j + 1, k - 1) and (k + 1, n - 1) should be equal.
where we define that subarray (L, R) represents a slice of the original array starting
from the element indexed L to the element indexed R.

Notice
1.1 <= n <= 2000.
2. Elements in the given array will be in range [-1,000,000, 1,000,000].

Example
Given nums = [1,2,1,2,1,2,1], return True
Explanation:
i = 1, j = 3, k = 5.
sum(0, i - 1) = sum(0, 0) = 1
sum(i + 1, j - 1) = sum(2, 2) = 1
sum(j + 1, k - 1) = sum(4, 4) = 1
sum(k + 1, n - 1) = sum(6, 6) = 1

Tags
Alibaba Array
 */

/**
 * Approach: PreSum
 * 本题解法是通过 抽掉三个数 来实现将 array 分为四个相等的部分。
 * 因此我们可以模拟这么一个过程，即：
 *  首先，利用 for loop 遍历 j 的位置，将整个 arr 分成两个相等的 subArray. (3 <= j < nums.size() - 3)
 *  因为是 去除数字 来实现分割的，所以头尾至少要各保留 3 个数。
 *  for loop 遍历 j 位置时，同时建立一个 Set 用于记录 subArray 的 sum 值，
 *  然后对 j的左半部分 进行划分，即遍历 i 的位置。将 i 去掉后看其 左右两部分和是否相等 ，相等的话把该值放入 Set 中
 *  遍历完左半部分后，再对 j的右半部分 进行划分，即遍历 k 的位置。将 k 去掉之后看其 左右两个subArray之和是否相等，
 *  相等的话看这个等值是否在 Set 中出现过，出现过的话则返回true
 *
 * 本题的注意点在于：这四个 subArray 的边界问题，建议大家以题目中的例子：
 * [1,2,1,2,1,2,1]
 *  0,1,2,3,4,5,6
 * 为例进行分析即可。
 */
public class Solution {
    /**
     * @param nums: a list of integer
     * @return: return a boolean
     */
    public boolean splitArray(List<Integer> nums) {
        if (nums == null || nums.size() <= 6) {
            return false;
        }

        // 计算整个数组的前缀和数组
        // 为了方便边界的分析，我们将前缀数组大小设为和 nums 的相同，以此来统一下标
        long[] preSum = new long[nums.size()];
        preSum[0] = nums.get(0);
        for (int i = 1; i < nums.size(); i++) {
            preSum[i] = preSum[i - 1] + nums.get(i);
        }

        // 枚举整个数组的 中间分割点 位置 j
        for (int j = 3; j < nums.size() - 3; j++) {
            Set<Long> set = new HashSet<>();
            // 枚举左半部分分割点的位置 i
            // 注意 i 两侧至少要留出一个数的位置
            for (int i = 1; i < j - 1; i++) {
                if (preSum[i - 1] == preSum[j - 1] - preSum[i]) {
                    set.add(preSum[i - 1]);
                }
            }
            // 枚举右半部分分割点的位置 k
            // 注意 k 两侧至少要留出一个数的位置
            for (int k = j + 2; k < nums.size() - 1; k++) {
                if (preSum[k - 1] - preSum[j] == preSum[nums.size() - 1] - preSum[k]) {
                    if (set.contains(preSum[k - 1] - preSum[j])) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}

