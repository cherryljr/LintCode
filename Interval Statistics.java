/*
Description
Given arr, an array of 01 and an integer k. You need to count how many intervals meet the following conditions:

Both start and the end of the interval are 0 (allowing the length of the interval to be 1).
The number of 1 in the interval is not more than k
The length of arr does not exceed 10^5

Example
Example 1:
Input: arr = [0, 0, 1, 0, 1, 1, 0], k = 1
Output: 7
Explanation: [0, 0], [1, 1], [3, 3], [6, 6], [0, 1], [0, 3], [1, 3] (The interval [i, j] means the elements between index i(included) and index j(included))

Example 2:
Input: arr = [1, 1, 1, 0, 0, 1], k = 2
Output: 3
Explanation: [3, 3], [4, 4], [3, 4] (The interval [i, j] means the elements between index i(included) and index j(included))
 */

/**
 * Approach: Sliding Window
 * 题目简介：给定一个01数组 arr 和 一个整数 k, 统计有多少区间符合如下条件：
 *  1. 区间的两个端点都为 0 (允许区间长度为1)
 *  2. 区间内 1 的个数不多于 k
 *
 * 非常明显的滑动窗口问题，根据题目所给出的两个条件进行窗口的滑动。（依据要求对模板进行稍微更改即可）
 * 详细说明可以参考代码注释。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
public class Solution {
    /**
     * @param arr: the 01 array
     * @param k: the limit
     * @return: the sum of the interval
     */
    public long intervalStatistics(int[] arr, int k) {
        long ans = 0, numOfOnes = 0;
        int begin = 0, end = 0;
        while (end < arr.length) {
            // 首先如果窗口的左右边界其中有一者为1的话，都无法满足条件，对此直接跳过即可
            if (arr[end] == 1) {
                numOfOnes++;
                end++;
                continue;
            }
            if (arr[begin] == 1) {
                numOfOnes--;
                begin++;
                continue;
            }

            // 右边界向右移动
            end++;
            // 如果因为窗口内 1 的个数超过 k，从而使得条件无法满足的话，则不断向右移动左边界，直到1的个数满足条件
            while (numOfOnes > k) {
                if (arr[begin] == 1) {
                    numOfOnes--;
                }
                begin++;
            }
            // 除去以1作为边界的子数组，其余情况均满足条件
            ans += end - begin - numOfOnes;
        }
        return ans;
    }
}