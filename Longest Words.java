/*
Description
Given a dictionary, find all of the longest words in the dictionary.

Example
Given
{
  "dog",
  "google",
  "facebook",
  "internationalization",
  "blabla"
}
the longest words are(is) ["internationalization"].

Given
{
  "like",
  "love",
  "hate",
  "yes"
}
the longest words are ["like", "love", "hate"].

Challenge
It's easy to solve it in two passes, can you do it in one pass?

Tags
Enumeration LintCode Copyright String
 */

/**
 * Approach: Traverse (Only Once)
 * 遍历字符串数组 dictionary, 利用 maxLen 来记录当前最长的字符串长度。
 * 当发现更长的字符串长度时，直接清空 结果集rst, 然后重新添加即可。
 * 当 word长度 与 结果集中的字符串长度相等时，则将 word 添加到 rst 中。
 *
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 */
public class Solution {
    /*
     * @param dictionary: an array of strings
     * @return: an arraylist of strings
     */
    public List<String> longestWords(String[] dictionary) {
        if (dictionary == null || dictionary.length == 0) {
            return null;
        }

        List<String> rst = new ArrayList<>();
        int maxLen = 0;
        for (String word : dictionary) {
            if (rst == null) {
                rst.add(word);
                maxLen = word.length();
            }
            // If we find a longer word, we clear the array list and add the new word
            if (word.length() > maxLen) {
                rst.clear();
                rst.add(word);
                maxLen = word.length();
            // If the length is the same, then we just add the word into array list.
            } else if (word.length() == maxLen) {
                rst.add(word);
            }
        }

        return rst;
    }
}