/*
Description
Given a string S, find the longest palindromic substring in S.
You may assume that the maximum length of S is 1000, and there exists one unique longest palindromic substring.

Example
Given the string = "abcdzdcab", return "cdzdc".

Challenge
O(n2) time is acceptable. Can you do it in O(n) time.

Tags
String
 */

/**
 * Approach: Manacher
 * Manacher算法的直接考察，没啥好说的了...
 * 对该算法不了解的可以参考：
 * https://github.com/cherryljr/LintCode/blob/master/Manacher%20Template.java
 */
public class Solution {
    /**
     * @param s: input string
     * @return: the longest palindromic substring
     */
    public String longestPalindrome(String s) {
        if (s == null || s.length() <= 1) {
            return s;
        }

        char[] charArr = getManacherString(s);
        int[] pArr = new int[charArr.length];
        int pR = -1, center = -1;
        int max = 0;
        int c = -1;

        for (int i = 0; i < charArr.length; i++) {
            pArr[i] = i < pR ? Math.min(pR - i, pArr[2 * center - i]) : 1;
            while (i + pArr[i] < charArr.length && i - pArr[i] > -1) {
                if (charArr[i + pArr[i]] == charArr[i - pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }
            if (i + pArr[i] > pR) {
                pR = i + pArr[i];
                center = i;
            }
            if (pArr[i] > max) {
                max = pArr[i];
                c = i;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = c - max + 1; i < c + max; i++) {
            if (charArr[i] != '#') {
                sb.append(charArr[i]);
            }
        }
        return sb.toString();
    }
    
    public char[] getManacherString(String str) {
        char[] charArr = str.toCharArray();
        char[] rst = new char[charArr.length * 2 + 1];
        int index = 0;
        for (int i = 0; i < rst.length; i++) {
            rst[i] = (i & 1) == 0 ? '#' : charArr[index++];
        }
        return rst;
    }
}