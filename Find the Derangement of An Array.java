/*
Description
In combinatorial mathematics, a derangement is a permutation of the elements of a set,
such that no element appears in its original position.

There's originally an array consisting of n integers from 1 to n in ascending order,
you need to find the number of derangement it can generate.
Also, since the answer may be very large, you should return the output mod 10^9 + 7.

n is in the range of [1, 10^6].

Example
Given n = 3, return 2.

Explanation:
The original array is [1,2,3]. The two derangements are [2,3,1] and [3,1,2].
 */

/**
 * Approach: DP
 * 这道题目刚刚看到时并没有想到是使用 DP 来解决，
 * 因为没啥思路，所以就写出了前几个数的答案，看能不能找到什么思路。
 *  1 -> 0
 *  2 ->
 *      2 1
 *  3 ->
 *      3 1 2        2 3 1
 *  4 ->                        该列相当于交换
 *      4 1 2 3      4 3 1 2     4 3 2 1
 *      3 4 2 1      2 4 1 3     3 4 1 2
 *      3 1 4 2      2 3 4 1     2 1 4 3
 * 推到 4 基本就能看出规律所在了。我们可以任意将 4 放在 1~3 的任意一个位置上，
 * 而至于原来位置上的数，我们可以选择放在 原来4 的位置上（相当于进行一次交换）
 * 或者也可以放在除去 原来位置和4位置这两个位置 的位置上。
 * 如将 4 放在 1 位置上，则原来的 1 可以被放在 4 上（交换），也可以被放在 2 或 3 的位置上。
 *
 * 找到规律之后，我们开始进一步分析：因为这是一个 无后效性 问题，我们不妨尝试使用 DP 来解决。
 * 假设 dp[n] 表示长度为 n 的数组的错排个数，显然 dp[1]=0,dp[2]=1.
 * 根据以上规律，我们来分析 第n个数，假设 n 放在第 k 位，则有如下两种情况：
 *  数字k放在了第n位，此时，相当于n和k交换了一下位置，还剩下 n-2 个数需要错排，所以 dp[n] += dp[n-2]
 *  数字k不放在第n位，此时，可以理解为k原本的位置是n，也就是1不能放在第1位、2不能放在第2位、...、k不能放在第n位，
 *  也就相当于对 n-1 个数进行错排，所以 dp[n] += dp[n-1]
 *  因为n可以放在 1~n-1 中的任意一个位置，所以总的情况数等于 dp[n] = (n-1) * (dp[n-1] + dp[n-2])。
 * 写出递推方程之后，后面的就非常简单了。只需要不断地向后递推即可。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
public class Solution {
    /**
     * @param n: an array consisting of n integers from 1 to n
     * @return: the number of derangement it can generate
     */
    public int findDerangement(int n) {
        final int MOD = 1000000007;
        long count1 = 0, count2 = 1;
        long rst = n == 1 ? 0 : 1;
        for (int i = 3; i <= n; i++) {
            rst = (count1 + count2) * (i - 1) % MOD;
            count1 = count2;
            count2 = rst;
        }
        return (int)rst;
    }
}