/*
Description
There is a circle, divided into n sectors. All the sectors are colored with some of m colors.
The colors of adjacent sectors cannot be the same. Find the total number of plans.

Do not consider symmetry.
Since this number may be large, you only need to return the solution number mod 1e9 + 7.
1≤n≤10^​5
​​1≤m≤10^​5
​​
Example
Given n = 2, m = 3, return 6.
Explanation:
One circle is divided into two sectors. There are six kinds of schemes for coloring in three colors:
black, red, black and white, white and red, white and black, red and white, and red and black.

Given n = 3, m = 2, return 0.
Explanation:
A circle is divided into 3 sectors and colored with 2 colors. No matter how it is colored, t
 */

/**
 * Approach: Sequence DP
 * 根据题目给定的数据范围，我们可以推断出来算法的时间复杂度应该在 O(nlogn) 或者以下。
 * 对于此类题目，我们不妨把前几个例子写出来看看。
 * 我们可以发现：前一个为 颜色k 的话，则当前颜色有 m-1 种选择。
 * 并且因为这是一个圆形，所以可以看成是一个环形结构。
 * 我们用数字来表示不同的颜色种类，则可以看做是一个环形链表，假设我们有 3 种颜料，分成 3 个扇形：
 *  1 -> 2 -> 3 --> 1
 * 以上就是一种解法，因此我们可以将看作是一个环形链表，相邻元素不能相同。
 * 
 * 如果当前元素是最后一个扇形的话，其总共有两种情况：
 *  1.前一个扇形的颜色与第一个扇形的颜色不同，则其总共有 m-2 种选择；
 *  2.前一个扇形的颜色与第一个扇形的颜色相同，则其总共有 m-1 种选择
 * 有了以上分析，我们发现当前状态是依赖于之前的两个位置的状态信息，因此只需要记录对应的信息，然后依次递推下去即可。
 * 即这是一道 DP 的题目。
 * dp[i] 表示以第 ith 个扇形作为最后一个，总共有几种方案。
 * 则根据以上分析有：dp[i] = dp[i - 2] * (m - 1) + dp[i - 1] * (m - 2);
 * 
 * 时间复杂度为：O(n)
 */
public class Solution {
    private static final int MOD = 1000000007;

    /**
     * @param n: the number of sectors
     * @param m: the number of colors
     * @return: The total number of plans.
     */
    public int getCount(int n, int m) {
        if (n == 0 || (n > 1 && m == 1)) {
            return 0;
        } else if (n == 1) {
            return m;
        }

        long[] dp = new long[n + 3];
        dp[1] = m;
        dp[2] = (long)m * (m - 1) % MOD;
        dp[3] = (long)m * (m - 1) * (m - 2) % MOD;
        for (int i = 4; i <= n; i++) {
            dp[i] = (dp[i - 2] * (m - 1) + dp[i - 1] * (m - 2)) % MOD;
        }

        return (int)dp[n];
    }
}