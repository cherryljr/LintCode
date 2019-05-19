/*
模板问题来源：https://www.geeksforgeeks.org/kmp-algorithm-for-pattern-searching/ （自带解析）

Given a text txt and a pattern pattern, write a function search(String txt, String pattern)
that prints all occurrences of pattern in txt. You may assume that n > m.

Examples:
Input:  txt = "THIS IS A TEST TEXT"
        pattern = "TEST"
Output: Pattern found at index 10

Input:  txt =  "AABAACAADAABAABA"
        pattern =  "AABA"
Output: Pattern found at index 0
        Pattern found at index 9
        Pattern found at index 12
 */

/**
 * 详细解析可以参考我的Blog:
 * http://www.cnblogs.com/cherryljr/p/6519748.html
 *
 * 题目要求：字符串 pattern 在 文本字符串 txt 中所有的出现位置。
 *
 * 一个小问题：
 *  为什么 模式字符串p 在跳跃移动的时候直接将 j 指针移动到了 next[j] 位置呢？
 *  有没有可能存在 在 0~next[j] 使得整个字符串匹配的答案呢？
 *  答案是不可能的，具体证明可以使用 反证法。
 *  假设存在的话，我们就可以在 字符串p 中找到一个更长的 相同前后缀，
 *  这与 next[] 的定义是互相违背的，即除非你 next[] 计算错误，否则不可能发生这种情况。
 *
 * 时间复杂度：O(n) 利用了 next[]，使得 search 过程中，i 只会向前移动，不会后退（即只遍历了 txt 一遍）
 * 空间复杂度：O(p)
 *
 * 相关练习：
 *  https://github.com/cherryljr/LeetCode/blob/master/Repeated%20String%20Match.java
 *  https://github.com/cherryljr/LeetCode/blob/master/Repeated%20Substring%20Pattern.java
 *  https://github.com/cherryljr/LintCode/blob/master/Minimum%20Cycle%20Section.java
 *  https://github.com/cherryljr/LeetCode/blob/master/Shortest%20Palindrome.java
 *  https://github.com/cherryljr/NowCoder/blob/master/%E5%90%AB%E6%9C%89%E8%BF%9E%E7%BB%AD%E4%B8%A4%E4%B8%AAstr%E4%BD%9C%E4%B8%BA%E5%AD%90%E4%B8%B2%E7%9A%84%E6%9C%80%E7%9F%AD%E5%AD%97%E7%AC%A6%E4%B8%B2.java
 */
public class KMP {

    public static void search(String txt, String p) {
        if (txt == null || p == null || p.length() < 1 || txt.length() < p.length()) {
            return;
        }
        char[] arr_s = txt.toCharArray();
        char[] arr_p = p.toCharArray();
        int i = 0, j = 0;
        // 计算 模式串 的 next 数组，以供 KMP 比较时进行跳跃
        int[] next = getNextArray(arr_p);

        while (i < arr_s.length && j < arr_p.length) {
            if (arr_s[i] == arr_p[j]) {
                // 当两个字符相等时，两个指针同时向后移动
                i++;
                j++;
            }
            // 如果 j 能够成功遍历到 arr_p 的结束位置，说明能够在字符串 txt 中匹配到模式串pattern.
            // 匹配的结束位置为 i,因此起始位置为 i-j.
            if (j == arr_p.length) {
                System.out.println("Found pattern " + "at index " + (i - j));
                // 找到匹配之后，我们需要重置 j 的位置为 next[j]
                j = next[j];
            } else if (i < arr_s.length && arr_s[i] != arr_p[j]) {
                if (j > 0) {
                    // 当两个字符不想等，则依据模式串p的 next[],我们可以将指针 j 向前跳跃到 next[j] 处，
                    // 相当于将 模式串 右移 j-next[j]，然后继续将 arr_s[i] 与 arr_p[j] 进行匹配
                    j = next[j];
                } else {
                    // 当 j <= 0 (next[j]=-1) 说明，j已经跳跃到模式串p的起始位置了，无法再次进行跳跃。
                    // 说明 arr_s[i] 与 模式串p 无法进行匹配，因此将 i 移动到下一个位置
                    i++;
                }
            }
        }
    }

    // 计算 arr[] 的 next 数组
    public static int[] getNextArray(char[] arr) {
        if (arr.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[arr.length + 1];

        // 根据定义初始化next数组，0位置为-1，1位置为0.
        next[0] = -1;
        // next[1] = 0; 0的赋值在初始化时已经完成，因此该步骤可以省略
        int pos = 2;    // 当前位置
        int cn = 0;     // 当前位置前一个字符的 next[] 值(最长相等前后缀的长度)
        while (pos < next.length) {
            if (arr[pos - 1] == arr[cn]) {
                // 当字符串的 pos-1 位置与 pos-1 位置字符所对应的最长相同前后缀的下一个字符 arr[next[pos-1]] 相等时
                // 我们就能确定 next[pos] 的值为 pos-1 位置所对应的 next[pos-1] + 1,即 ++cn.
                next[pos++] = ++cn;
            } else if (cn > 0) {
                // 当着两个字符 不相等 时，cn向前跳跃到 next[cn] 的位置，去寻找长度更短的相同前后缀。
                cn = next[cn];
            } else {
                // cn<=0; 此时说明前面已经没有相同前后缀了，即 cn 已经没办法再跳跃了，
                // 此时 pos 对应的 next[pos] 值为 0 （无相同前后缀）
                next[pos++] = 0;
            }
        }

        return next;
    }

    public static void main(String[] args) {
        String txt = "AABAACAADAABAABA";
        String pattern = "AABA";
        search(txt, pattern);
    }

}
