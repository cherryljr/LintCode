/*
Description
Given two strings S and T, determine if they are both one edit distance apart.

Example
Given s = "aDb", t = "adb"
return true

Tags
String Uber Twitter Snapchat Facebook
 */

/**
 * Approach: Traverse
 * 看到本题的题目 Edit Distance II, 以为是 Edit Distance 的 Follow Up.
 * 会让人以为仍然使用 DP 来进行解决，最后看 edit 的次数是否为 1.
 * 然而只需要稍微考虑一下，本题实际上通过进行 遍历 和 equalsTo 方法就能解决了。
 *
 * 具体做法：
 * 对比两个字符串对应位置上的字符。如果遇到不同的时候，
 * 这时我们看两个字符串的长度关系，
 * 如果 相等，那么我们比较当前位置 之后 的字串是否相同，
 * 如果 s 的长度更长，那么我们比较 s从下一个位置开始 的子串，和 t从当前位置开始 的子串是否相同，
 * 反之如果 t 的长度大，那么我们比较 t从下一个位置开始 的子串，和 s从当前位置开始 的子串是否相同。
 * 如果循环结束，都没有找到不同的字符，那么此时我们比较两个字符串的 长度是否相差 1
 */
public class Solution {
    /**
     * @param s: a string
     * @param t: a string
     * @return: true if they are both one edit distance apart or false
     */
    public boolean isOneEditDistance(String s, String t) {
        if (s == null || t == null || Math.abs(s.length() - t.length()) > 1) {
            return false;
        }

        for (int i = 0; i < Math.min(s.length(), t.length()); i++) {
            if (s.charAt(i) != t.charAt(i)) {
                // 如果字符串长度相等，比较当前位置 之后 的字串是否相同
                if (s.length() == t.length()) {
                    return s.substring(i + 1).equals(t.substring(i + 1));
                // 如果 s 的长度更短，比较 s从当前位置开始 的子串 和 t从下一个位置开始 的子串是否相同    
                } else if (s.length() < t.length()) {
                    return s.substring(i).equals(t.substring(i + 1));
                // 反之，比较 s从下一个位置开始 的子串，和 t从当前位置开始 的子串是否相同       
                } else {
                    return s.substring(i + 1).equals(t.substring(i));
                }
            }
        }

        // 如果循环结束，都没有找到不同的字符，那么此时我们比较两个字符串的 长度是否相差 1
        return Math.abs(s.length() - t.length()) == 1;
    }
}