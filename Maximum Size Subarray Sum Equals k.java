/*
Description
Given an array nums and a target value k, find the maximum length of a subarray that sums to k.
If there isn't one, return 0 instead.

Notice
The sum of the entire nums array is guaranteed to fit within the 32-bit signed integer range.

Example
Given nums = [1, -1, 5, -2, 3], k = 3, return 4.

Explanation:
because the subarray [1, -1, 5, -2] sums to 3 and is the longest.
Given nums = [-2, -1, 2, 1], k = 1, return 2.

Explanation:
because the subarray [-1, 2] sums to 1 and is the longest.

Challenge
Can you do it in O(n) time?

Tags
Hash Table Facebook Palantir
 */

/**
 * Approach: PreSum + HashMap
 * Subarray 系列问题解题思路
 * 我们都知道 Subarray 问题通常有如下解决方式（优化时间复杂度）：
 *  1. 计算 前项和PreSum 对时间复杂度进行优化
 *  2. 利用 滑动窗口(Two Pointers) 对时间复杂度进行优化
 *  3. Forward-Backward Traversal (前后遍历)
 *  4. DP 解法
 *  5. Kadane's Algorithm
 *
 * 这边我们要着重要讨论一下方法1，它蕴含这一个非常重要的解题思想，其常常被应用在 subarray问题 的求解中：
 *  如果要求的是符合 ??? 要求的子串的话，
 *  我们可以求出每一个 以当前元素作为结尾 的 符合??要求的 子串（当然有可能不存在），那么答案肯定在这些子串中。
 *  这边是我们解题的 大体 思路。（非常重要！！！）
 *
 * 但是毫无疑问这是一个时间复杂度为 O(n^2) 的算法，我们怎么优化它呢？
 * 我们可以通过本道题目来引出这个话题
 * 使用上述的思路：
 *  我们需要求出 以每一个以当前元素arr[i]作为结尾 累加和为k的最长数组长度，那么如何快速做到这一点呢？
 *  这里我们可以使用 前项和PreSum 来帮助我们在 O(1) 的时间内解决这个问题。
 *  但是这也使得该方法只能适用于 preSum 能够解决的情况，即我们需要用到 preSum - k 这个信息的时候，可以使用该方法。
 *
 * preSum[i] 指的是数组从 0 开始到 i 位置所有元素的总和。
 * 有了它，我们可以快速地计算出数组某一段区间的和 sum[x...y] = preSum[y] - preSum[x] + arr[x]
 * 因为题目需要我们求得数组长度，所以我们还需要保存求得 preSum 时，所对应的 index 信息。
 * 因此我们的 HashMap 内容为：
 * key：到达 i 位置时的 preSum; value： 第一次 出现 preSum 的位置(index)
 * 接下来回到题目，如果我们想要查询 累加和为k的最长数组长度，那么我们只需要在 map 中查询是否存在 preSum-k 的 key 即可。
 * 因为如果[0...y] 的和为 preSum, [0...x] 的和为 preSum-k, 那么 (x...y] 的和就是 k.
 * 那么以 arr[y] 作为结尾的 累加和为k的最长数组就是 [x+1...y].
 * 通过以上分析，我们知道可以通过用 HashMap 来存储各个位置的 preSum 和对应的 index 来达到优化时间复杂度的目的。
 *
 * 具体做法：
 *  首先在 map 中加入 (0, -1),来初始化 map,这是为了保证第 0 位置的元素不被忽略。
 *  当我们不添加任何元素的时候，preSum 就是 0.因此我们将其 index 置为 -1.
 *  比如：[-3, 1, 2] k=0. 如果我们不加入 (0, -1) 的话，当我们查询是否 containsKey(0) 会发现不存在，使得 len = 0.
 *  这是错误的，因此 根据题目要求 对 map 进行初始化非常重要。
 *  然后我们遍历数组，计算 preSum,每次查询 preSum-k 是否在 map 中，
 *  如果在的话，说明 [map.get(preSum-k) + 1, i] 的和为 k.这样我们就找到了这个最长子数组的左右边界了。
 *  然后我们查看 preSum 是否在 map 中，如果在的话，不进行更新（因为我们要记录第一个能够累加出 preSum 的位置）
 *  否则将 preSum 和 对应的index 放入 map 中。
 *
 * 时间复杂度为：O(n)
 * 空间复杂度为：O(n)
 *
 * 这个方法通常应用在题目提供了确切的 target 信息 k 的时候。
 * 特别是当 滑动串口 这种方法无法解决时，通常可以使用该方法解决
 * 甚至是在 DP 中，也会使用到这个思想。
 * 下面列出几道变形题以供练习，也可以让大家更好地掌握这个方法：
 * 奇偶数个数相等子数组:
 * 0,1个数相等子数组：将 0 转换成 -1，计算 和为0 的最长连续子数组
 * 0,1,2数组中1,2个数相等子数组：将 2 转换成 -1，计算 和为0 的最长连续子数组
 * Subarray Sum: https://github.com/cherryljr/LintCode/blob/master/Subarray%20Sum.java
 * Subarray Sum Closest: https://github.com/cherryljr/LintCode/blob/master/Subarray%20Sum%20Closest.java
 * 异或和为0的子数组:
 * https://github.com/cherryljr/NowCoder/blob/master/%E5%BC%82%E6%88%96%E5%92%8C%E4%B8%BA0%E7%9A%84%E5%AD%90%E6%95%B0%E7%BB%84%E4%B8%AA%E6%95%B0.java
 */
public class Solution {
    /**
     * @param nums: an array
     * @param k: a target value
     * @return: the maximum length of a subarray that sums to k
     */
    public int maxSubArrayLen(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int maxLen = Integer.MIN_VALUE;
        Map<Integer, Integer> map = new HashMap<>();
        // Initialize the values
        map.put(0, -1); // very important
        int preSum = 0;
        for (int i = 0; i < nums.length; i++) {
            preSum += nums[i];
            if (map.containsKey(preSum - k)) {
                // update the len if we find the longer subarray
                maxLen = Math.max(maxLen, i - map.get(preSum - k));
            }
            if (!map.containsKey(preSum)) {
                // record the first occurrence that the preSum showed
                map.put(preSum, i);
            }
        }

        return maxLen;
    }
}