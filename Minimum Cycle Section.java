/*
Description
Given an array of int, find the length of the minimum cycle section.

Notice
The length of array do not exceed 100000。
Each element is in the int range 。

Example
Given array = [1,2,1,2,1,2], return 2.
Explanation:
The minimum cycle section is [1,2]，and the length is 2.

Given array = [1,2,1,2,1], return 2.
Explanation:
The minimum cycle section is [1,2]，and the length is 2, although the last 2 is not given, 
we still consider the cycle section is [1,2].

Given array = [1,2,1,2,1,4], return 6.
Explanation:
The minimum cycle section is [1,2,1,2,1,4], and the length is 6.

Tags 
Google
 */

/**
 * Approach: KMP
 * 这道题目其实考察的是 KMP 中求解 next[] 的方法。
 * 答案是赛后才想到的（一时半会儿确实很难想到是考察 KMP 啊...给 Google 跪了）
 * 想到该解法的契机有以下三点：
 *  1. 涉及到字符串的 子串 问题，且 子串 进行了一定的重复
 *  2. KMP求的是 最长相同前后缀，本题求的是 最短重复子串
 *  3. LeetCode上的 Repeated Substring Pattern 这道题目（有一定的相似度，可以学习）
 * 具体做法为：
 *  求字符串 终止位置 的next值，其表示的含义就是该字符串的最长相同前后缀的长度。
 *  然后我们用 该字符串的长度 - 最长相同前后缀的长度 就能够得到 最小循环节的长度了。
 *  即 minLen = arr.length - next[next.length - 1] = arr.length - cn
 * 这里大家可能会有疑问，那万一最后一个循环节不完整怎么办呢？
 * 答案仍然是成立的，这里理解起来会稍微费劲一些哈。
 *
 * Repeated Substring Pattern:
 * https://github.com/cherryljr/LeetCode/blob/master/Repeated%20Substring%20Pattern.java
 * KMP Template:
 * https://github.com/cherryljr/LintCode/blob/master/KMP%20Template.java
 */
public class Solution {
    /**
     * @param array: The array
     * @return: The answer
     */
    public int minimumCycleSection(int[] array) {
        int[] next = new int[array.length + 1];
        next[0] = -1;
        int pos = 2, cn = 0;
        while (pos < next.length) {
            if (array[pos - 1] == array[cn]) {
                next[pos++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[pos++] = 0;
            }
        }
        return array.length - cn;
    }
}