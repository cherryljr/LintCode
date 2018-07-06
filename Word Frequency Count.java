/*
Description
Input a string s and a string list excludeList to find all the most frequent words in s that do not exist in excludeList.
Your results will be sorted by lexicographic order by online judge.

The words are case-insensitive and finally return lowercase words
Non-alphabetic characters in the string are considered as spaces, and spaces are word separators
The length of all words does not exceed 10^5 and the length a word does not exceed 100

Example
Given s="I love Amazon.", excludeList=[] , return ["i","love","amazon"].
Explanation:
"i", "love", and "amazon" are the words that appear the most.

Given s="Do not trouble trouble.", excludeList=["do"], return ["trouble"].
Explanation:
"trouble" is the most frequently occurring word.
 */

/**
 * Approach: HashMap
 * 使用 HashMap 统计各个单词的词频（统计的时候利用 excludeSet 过滤即可）
 * 注意点：
 *  1. 所有非英文字母的字符都是分割符号，所以需要一个个字符进行遍历才行。
 *  处理方式和 简单计算器 中的遍历方式相同。
 *  2. 结果需要全部转换成小写
 *  
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 */
public class Solution {
    /**
     * @param s: The string s
     * @param excludeList: The excludeList
     * @return: Return the most frequent words
     */
    public String[] getWords(String s, String[] excludeList) {
        Set<String> excludeSet = new HashSet<>(Arrays.asList(excludeList));
        Map<String, Integer> map = new HashMap<>();
        int index = 0;
        while (index < s.length()) {
            // 所有不是英文字母的字符都是分割符号
            if (Character.isLetter(s.charAt(index))) {
                StringBuilder sb = new StringBuilder();
                while (index < s.length() && Character.isLetter(s.charAt(index))) {
                    sb.append(s.charAt(index++));
                }
                // 这里 index 无需进行一步回退，因为这是没有必要的
                String word = sb.toString();
                if (!excludeSet.contains(word)) {
                    map.put(word, map.getOrDefault(word, 0) + 1);
                }
            } else {
                index++;
            }
        }

        int max = Integer.MIN_VALUE;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            max = Math.max(max, entry.getValue());
        }
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == max) {
                // 结果需要是小写
                list.add(entry.getKey().toLowerCase());
            }
        }
        String[] rst = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            rst[i] = list.get(i);
        }
        return rst;
    }
}