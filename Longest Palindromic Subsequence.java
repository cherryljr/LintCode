/*
Description
Given a string s, find the longest palindromic subsequence's length in s.
You may assume that the maximum length of s is 1000.

Example
Given s = "bbbab" return 4
One possible longest palindromic subsequence is "bbbb".
 */

/**
 * Approach: Sequence DP
 * 可以从 递归 的方向去思考。
 * Base Case 为只有一个字符时，其本身为回文串，长度为 1.
 * 然后可以以此为基础向外一层层扩出去。
 *
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(n^2)
 *  空间复杂度可以利用滚动数组进行优化，参考：
 *  https://www.youtube.com/watch?v=OZX1nqaQ_9M&t=365s
 */
public class Solution {
    /**
     * @param s: the maximum length of s is 1000
     * @return: the longest palindromic subsequence's length
     */
    public int longestPalindromeSubseq(String s) {
        // Deal with the exceptions
        if (s == null || s.length() == 0) {
            return 0;
        }

        int len = s.length();
        int[][] dp = new int[len][len];

        for (int l = 1; l <= len; l++) {
            for (int i = 0; i + l <= len; i++) {
                int j = i + l - 1;
                // 只有一个字符，长度为1
                if (i == j) {
                    dp[i][j] = 1;
                    continue;
                }
                if (s.charAt(i) == s.charAt(j)) {
                    // 当 ith 的字符 与  jth 的字符相等时，其值为内层字符串的最长回文子序列长度 + 2
                    // 子状态为 长度-2 的字符串的状态
                    dp[i][j] = Math.max(dp[i][j], dp[i + 1][j - 1] + 2);
                } else {
                    // 否则为 长度-1 的子状态中取最大值
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[0][len - 1];
    }
}