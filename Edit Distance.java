/*
Description
Given two words word1 and word2, find the minimum number of steps required to convert word1 to word2.
(each operation is counted as 1 step.)

You have the following 3 operations permitted on a word:
Insert a character
Delete a character
Replace a character

Example
Given word1 = "mart" and word2 = "karma", return 3.

Tags
Dynamic Programming String
 */

/**
 * Approach 1: Two Sequence DP
 * 与 LCS 具有一定的类似性,因此有人就想是否可以根据LCS的值计算出该题的值呢？
 *      EditDistance(word1, word2) = MaxLength(word1, word2) - LCS(word1, word2)
 * 即计算出二者之间不需要编辑的部分，然后word1，word2中用最长的长度减去这个 LCS 的长度即可。
 * 但实际上该做法是错误的。原因在于：
 * LCS 中，求得是 最长公共子序列，它并不在意每个字符匹配的位置，因此可能导致
 * 就算 存在LCS, 但是进行修改时，这些匹配的字符仍然需要被 edit.
 * 比如："sea" 和 "ate", 虽然它们存在 LCS 'a',但是为了使得两个字符串相同进行 edit 时，'a'
 * 并不能发挥任何作用，因为位置关系，它仍然需要被修改。
 *
 * 虽然我们不能直接使用 LCS 的方法，但是我们仍然能够使用其 DP 的思想方法。
 * State:
 *  dp[i][j]表示 word1 的前 i 个字符配上 word2 的前 j 个字符最少需要几次编辑才能够使它们相等
 * Function:
 *  因为 word1进行一个insert操作 与 word2进行一次remove操作 其实是相同的，故这边仅仅针对 A=>B 的操作进行分析
 *  当 word1[i] == word2[j] 时：
 *      dp[i][j] = dp[i-1][j-1]
 *  当 word1[i] != word2[j] 时：
 *      进行 replace 操作时，将A中 0...i-1 部分转成B中 0...j-1 部分：dp[i][j] = dp[i-1][j-1] + 1
 *      进行 delete 操作时，将A中 0...i-1 部分转成B中 0...j 部分，再删除 ith 位置的字母：dp[i][j] = dp[i-1][j] + 1
 *      进行 insert 操作时，将A中 0...i 部分转成B中 0...j-1 部分，再插入 jth 位置的字母：dp[i][j] = dp[i][j-1] + 1
 *      dp[i][j] 取以上 3 个操作中的最小值即可。
 * Initialize:
 *      dp[i][0] = i // 当 word2 为空时
 *      dp[0][j] = j // 当 word1 为空时
 * Answer:
 *      dp[word1.length()][word2.length()]
 *
 * 时间复杂度：O(mn) ； 空间复杂度：O(mn)
 */
public class Solution {
    /**
     * @param word1 & word2: Two string.
     * @return: The minimum number of steps.
     */
    public int minDistance(String word1, String word2) {
        // write your code here
        if (word1 == null || word1.length() == 0) {
            return word2.length();
        }
        if (word2 == null || word2.length() == 0) {
            return word1.length();
        }

        // State
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];

        // Initialize
        for (int i = 0; i <= word1.length(); i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= word2.length(); j++) {
            dp[0][j] = j;
        }

        // Function
        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1; j <= word2.length(); j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j -1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
                }
            }
        }

        // Answer
        return dp[word1.length()][word2.length()];
    }
}

/**
 * Approach 2: Two Sequence DP (Optimized by Sliding Array)
 * 由 Approach 1 中的分析可得：
 *  dp[i][j] 仅仅由 上一行的状态 与 前一个状态（左侧点） 所决定。
 * 因此我们可以利用 滚动数组 对其 空间复杂度 进行优化。
 * 由分析可得，我们只需要保存 两行的状态值 就足够了。
 * 滚动数组由 数组的下标 进行 取余 操作来实现。
 *
 * 时间复杂度：O(mn) ； 空间复杂度：O(n)
 */
public class Solution {
    /**
     * @param word1 & word2: Two string.
     * @return: The minimum number of steps.
     */
    public int minDistance(String word1, String word2) {
        // write your code here
        if (word1 == null || word1.length() == 0) {
            return word2.length();
        }
        if (word2 == null || word2.length() == 0) {
            return word1.length();
        }

        // State
        int[][] dp = new int[2][word2.length() + 1];

        // Initialize
        for (int j = 0; j <= word2.length(); j++) {
            dp[0][j] = j;
        }

        // Function
        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 0; j <= word2.length(); j++) {
                if (j == 0) {
                    dp[i & 1][j] = i;
                    continue;
                }

                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i & 1][j] = dp[(i-1) & 1][j - 1];
                } else {
                    dp[i & 1][j] = Math.min(dp[(i-1) & 1][j - 1], Math.min(dp[(i-1) & 1][j], dp[i & 1][j - 1])) + 1;
                }
            }
        }

        // Answer
        return dp[word1.length() & 1][word2.length()];
    }
}