/*
Given a string s, return the last substring of s in lexicographical order.

Example 1:
Input: "abab"
Output: "bab"
Explanation: The substrings are ["a", "ab", "aba", "abab", "b", "ba", "bab"]. The lexicographically maximum substring is "bab".

Example 2:
Input: "leetcode"
Output: "tcode"

Note:
    1. 1 <= s.length <= 4 * 10^5
    2. s contains only lowercase English letters.
 */

/**
 * Approach: Two Pointers + Suffix SubArray
 * 首先我们可以发现：答案就是字符串的字典序最大后缀子串。
 * 为什么一定是后缀呢？如果不是，假设这个字典序最大的子串是 s，并且 s 不是后缀，那么 s 连接上它后面的若干字符构成的新字符串的字典序一定比 s 大，
 * 这和 s 是字典序最大的子串相矛盾。
 * 
 * 因此，如果我们当前得到了最大字符，index是它在字符串s中的下标，我们当前的答案就是 s[index:]
 * 设立左右两个指针，i记录当前遇见的最大字符，j负责往后遍历，寻找是否有更大的字符
 * offset负责的是：如果s[i] == s[j] 则继续比较 s[i+i] 与 s[j+i] (i = 1,2,3,4,5......)
 * 直到我们找到了其中一个更大的或者我们遇见了边界 j+offset == len(s), 否则 offset 将一直增大
 * 一旦找到了一个更大的，我们就把 offset 还原回 0
 *  如果我们的 s[j+offset] 比较大: i=j, j=j+1
 *  如果我们的 s[i+offset] 比较大：j=j+offset+1 (这里不用j = j + 1的原因是 s[j : j+offset] 所有的字母都比 s[i] 小)
 *
 * 时间复杂度：O(n) (快指针j，只会向前不会后退)
 * 空间复杂度：O(1)
 */
class Solution {
    public String lastSubstring(String s) {
        int i = 0, j = 1, offset = 0;
        while (j + offset < s.length()) {
            char a = s.charAt(i + offset), b = s.charAt(j + offset);
            if (a < b) {
                i = j; j = j + 1; offset = 0;
            } else if (a > b) {
                j = j + offset + 1; offset = 0;
            } else {
                offset++;
            }
        }
        return s.substring(i);
    }
}