/*
Description
Given a string s and a dictionary of words dict,
determine if s can be break into a space-separated sequence of one or more dictionary words.

Example
Given s = "lintcode", dict = ["lint", "code"]. 
Return true because "lintcode" can be break as "lint code".
 */

/**
 * Approach 1: Recursion + Memory Search (TLE)
 * 这个解法在 LeetCode 上面是可以过的，但是 LintCode 这边数据集有点大过不去。
 * 主要思路就是：枚举每个划分的位置 i，并使用 Map 记录以此产生的字符串 s 的分割结果，
 * 以便进行记忆化搜索。
 *
 * 类似的问题：
 * Largest Sum of Averages：
 * 
 * Word Break II:
 *  https://github.com/cherryljr/LintCode/blob/master/Word%20Break%20II.java
 */
public class Solution {
    /*
     * @param s: A string
     * @param dict: A dictionary of words dict
     * @return: A boolean
     */
    public boolean wordBreak(String s, Set<String> dict) {
        if (s == null || s.length() == 0) {
            return true;
        }

        int len = s.length();
        Map<String, Boolean> mem = new HashMap<>();
        return wordBreak(s, mem, dict);
    }

    private boolean wordBreak(String s, Map<String, Boolean> mem, Set<String> dict) {
        // If the dict contains the word s, return true directly
        if (dict.contains(s)) {
            return true;
        }

        for (int i = 1; i < s.length(); i++) {
            // the left part (substring)
            String subStr = s.substring(0, i);
            if (!mem.containsKey(subStr)) {
                // the subStr state hasn't been calculated, so we need to do recursion of it
                mem.put(subStr, wordBreak(subStr, mem, dict));
            }
            if (dict.contains(s.substring(i)) && mem.get(subStr)) {
                // if the word dictionary contains the right substring (word)
                // and the left subStr can be break into a space-separated sequence of one or more dictionary words
                mem.put(s, true);
                return true;
            }
        }

        mem.put(s, false);
        return false;
    }
}

/**
 * Approach 2: DP
 * 超时的 Case 是一个长度非常长的 String,但是反观单词字典，里面的单词长度并不长。
 * 出现超时的现象正是由于我们循环的范围不当所导致的。
 * 分析总结如下：
 *  字符串String与单词word是存在着不同点的，那就是它们的长度！
 *  字符串可以非常的长，但是一个单词长度是有限的。它不可能超过词典中最长英文单词的长度。
 *  所以，我们在for loop时，仅仅需要考虑 i - maxLength ~ i 内的范围即可，而不需要考虑 0 ~ i 全部的范围了。
 *
 * 改进后算法复杂度为：O(NL)
 *  N: 字符串长度     L：最长单词长度
 */
public class Solution {
    /*
     * @param s: A string
     * @param dict: A dictionary of words dict
     * @return: A boolean
     */
    public boolean wordBreak(String s, Set<String> dict) {
        if (s == null || dict.contains(s)) {
            return true;
        }
        int maxLength = getMaxLength(dict);

        // State
        boolean[] dp = new boolean[s.length() + 1];

        // Initialize
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            dp[i] = false;
        }

        // Function
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= maxLength && j <= i; j++) {
                if (dp[i - j] && dict.contains(s.substring(i - j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }

        // Answer
        return dp[s.length()];
    }

    private int getMaxLength(Set<String> dict) {
        int maxLength = 0;
        for (String word : dict) {
            maxLength = Math.max(maxLength, word.length());
        }
        return maxLength;
    }
}
