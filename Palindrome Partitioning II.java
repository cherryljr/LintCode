/*
Description
Given a string s, cut s into some substrings such that every substring is a palindrome.
Return the minimum cuts needed for a palindrome partitioning of s.

Example
Given s = "aab",
Return 1 since the palindrome partitioning ["aa", "b"] could be produced using 1 cut.

Tags 
Dynamic Programming
 */

/**
 * Approach: DP
 * 最小值问题，Source为字符串，不能随意更换位置 => Sequence DP
 * State:
 *  dp[i]表示前i个字符最少需要多少次cut才能被分割为回文子字符串 (最少被分割为多少个回文子字符串 - 1)
 * Function:
 *  遍历 [0...i-1] 的各个子串，如果一旦发现子串 [j...i-1] 是一个回文串，那么就可以有：
 *  dp[i] = Math.min(dp[i], dp[j] + 1)
 * Initialize:
 *  dp[0] = -1, dp[i] = i – 1 (因为长度为i的字符串最多可以进行i – 1次cut)
 * Answer:
 *  dp[s.length()]
 * 注意点：对于一个长度为 n 的字符串初始化时我们常常需要开辟 n + 1 的数组空间，把0位留出来。
 * 因为定义是前i个字符，故存在定义：”前0个字符”，即空串。而这是不能被忽略掉的，因为有许多结果是从这里得到的。
 * 这样答案的结果也就是f[s.length()] => dp[n].
 *
 * 该题可优化的点在于我们可以事先就将 j~i 是否为回文串进行判断并存储起来，而不是将其放到DP的for循环中。
 * 因为 isPalindrome 操作的复杂度为O(N),如果将其写DP的Function中的for loop中会使得程序的复杂度
 * 由原来的 O(N^2) 变成 O(N^3)
 * 具体的优化方法也是一个 DP。
 * 可以参考：
 * https://github.com/cherryljr/LintCode/blob/master/Palindrome%20Partitioning.java
 */
public class Solution {
    /**
     * @param s: A string
     * @return: An integer
     */
    public int minCut(String s) {
        if (s == null || s.length() <= 1) {
            return 0;
        }

        int len = s.length();
        boolean[][] isPalindrome = new boolean[len][len];
        getIsPalindromeTable(s, isPalindrome);

        int[] dp = new int[len + 1];
        // 初始化 dp 数组，cut的次数 = 回文字串个数-1
        for (int i = 0; i <= s.length(); i++) {
            dp[i] = i - 1;
        }
        for (int i = 1; i <= len; i++) {
            for (int j = 0; j < i; j++) {
                if (isPalindrome[j][i - 1]) {
                    dp[i] = Math.min(dp[i], dp[j] + 1);
                }
            }
        }

        return dp[len];
    }

    private void getIsPalindromeTable(String s, boolean[][] isPalindrome) {
        int len = s.length();
        // Initialize (初始化对角线上的值)
        for (int i = 0; i < len; i++) {
            isPalindrome[i][i] = true;
        }
        // 初始化对角线上面一条对角线的值 (因为其也不依赖其他位置的值，可以直接算出）
        for (int i = 0; i < len - 1; i++) {
            isPalindrome[i][i + 1] = (s.charAt(i) == s.charAt(i + 1));
        }

        // Function (根据初始化好的 base case,推算出各个位置的值)
        // 从下到上，一条一条对角线地递推
        for (int i = len - 3; i >= 0; i--) {
            for (int j = i + 2; j < len; j++) {
                isPalindrome[i][j] = isPalindrome[i + 1][j - 1] && s.charAt(i) == s.charAt(j);
            }
        }
    }
}