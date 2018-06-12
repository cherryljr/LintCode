/*
Description
Given two integers n and k, you need to construct a list
which contains n different positive integers ranging from 1 to n and obeys the following requirement:
Suppose this list is [a1, a2, a3, ... , an], then the list [|a1 - a2|, |a2 - a3|, |a3 - a4|, ... , |an-1 - an|]
has exactly k distinct integers.

If there are multiple answers, print any of them.

The n and k are in the range 1 <= k < n <= 10^4.

Example
Example1:
Input: n = 3, k = 1
Output: [1, 2, 3]
Explanation: The [1, 2, 3] has three different positive integers ranging from 1 to 3,
and the [1, 1] has exactly 1 distinct integer: 1.

Example2:
Input: n = 3, k = 2
Output: [1, 3, 2]
Explanation: The [1, 3, 2] has three different positive integers ranging from 1 to 3,
and the [2, 1] has exactly 2 distinct integers: 1 and 2.
 */

/**
 * Approach: Construction
 * 观察可得：k的最大值为 n-1.此时排列方式为：
 *  1, n, 2, n-1, 3, n-2...
 * 因此排列的方法取决于 k 的值，我们只需要按照类似：
 *  l++, r--, l++, r--, l++, l++, l++...
 * 的顺序排列即可 l 起始值为 1，r 起始值为 n.
 * 思想就是利用 l, r 交错排列的方法产生 k-1 个 distinct integers.
 * 然后加上最后 l++ 之间所产生的距离 1. 总共 k 个.
 * 至于排列的方式，我们可以写出例子，根据 k 的奇偶来进行排列即可。
 *
 * 参考：
 * https://leetcode.com/problems/beautiful-arrangement-ii/discuss/106948/C++-Java-Clean-Code-4-liner
 */
public class Solution {
    /**
     * @param n: the number of integers
     * @param k: the number of distinct integers
     * @return: any of answers meet the requirment
     */
    public int[] constructArray(int n, int k) {
        int[] rst = new int[n];
        int index = 0;
        int left = 1, right = n;
        while (left <= right) {
            if (k > 1) {
                rst[index++] = (k-- & 1) == 1 ? left++ : right--;
            } else {
                rst[index++] = left++;
            }
        }
        return rst;
    }
}

// Compact Version
public class Solution {
    /**
     * @param n: the number of integers
     * @param k: the number of distinct integers
     * @return: any of answers meet the requirment
     */
    public int[] constructArray(int n, int k) {
        int[] rst = new int[n];
        for (int i = 0, left = 1, right = n; left <= right; i++)
            rst[i] = k > 1 ? ((k-- & 1) == 1 ? left++ : right--) : left++;
        return rst;
    }
}