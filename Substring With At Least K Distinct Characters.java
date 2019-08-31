/*
Description
Given a string S with only lowercase characters.
Return the number of substrings that contains at least k distinct characters.
    1. 10 ≤ length(S) ≤ 1,000,000
    2. 1 ≤ k ≤ 26

Example
Example 1:
Input: S = "abcabcabca", k = 4
Output: 0
Explanation: There are only three distinct characters in the string.

Example 2:
Input: S = "abcabcabcabc", k = 3
Output: 55
Explanation: Any substring whose length is not smaller than 3 contains a, b, c.
    For example, there are 10 substrings whose length are 3, "abc", "bca", "cab" ... "abc"
    There are 9 substrings whose length are 4, "abca", "bcab", "cabc" ... "cabc"
    ...
    There is 1 substring whose length is 12, "abcabcabcabc"
    So the answer is 1 + 2 + ... + 10 = 55.
 */

/**
 * Approach: Sliding Window
 * 与LeetCode上的 Subarrays with K Different Integers 有一定的相似度。
 * 均可以采用 滑动窗口 的做法进行解决，对该知识点不清楚的可以参考下方给出的链接。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * Reference:
 *  https://github.com/cherryljr/LeetCode/blob/master/Subarrays%20with%20K%20Different%20Integers.java
 *  https://github.com/cherryljr/LeetCode/blob/master/Sliding%20Window%20Template.java
 */
public class Solution {
    /**
     * @param s: a string
     * @param k: an integer
     * @return: the number of substrings there are that contain at least k distinct characters
     */
    public long kDistinctCharacters(String s, int k) {
        Map<Character, Integer> map = new HashMap<>();
        int begin = 0, end = 0;

        long ans = 0;
        while (end < s.length()) {
            char c = s.charAt(end);
            map.put(c, map.getOrDefault(c, 0) + 1);
            end++;

            while (map.size() >= k) {
                char pre = s.charAt(begin);
                if (map.containsKey(pre)) {
                    map.put(pre, map.getOrDefault(pre, 0) - 1);
                    if (map.get(pre) == 0) {
                        map.remove(pre);
                    }
                }
                ans += s.length() - end + 1;
                begin++;
            }
        }

        return ans;
    }
}