/*
Description
Given a string collection tag_list, an array of strings all_tags,
find the smallest all_tags sub-array contains all the string in the tag_list,
output the length of this sub-array. If there is no return -1.

1 <= |tag_list|,|all_tags| <=10000
All string length <= 50

Example
Given tag_list = ["made","in","china"], all_tags = ["made", "a","b","c","in", "china","made","b","c","d"], return 3.
Explanation:
["in", "china","made"] contains all the strings in tag_list,6 - 4 + 1 = 3.

Given tag_list = ["a"], all_tags = ["b"], return -1.
Explanation:
Does not exist.
 */

/**
 * Approach: Sliding Window
 * 滑动串口的典型问题，时间复杂度为 O(n)。
 * 因为可以是 乱序 出现的，所以使用 Map 来维持即可。
 * 使用模板就可以直接 AC 掉...
 * 与 LeetCode 上的 Minimum Window Substring 一模一样，详细解析可以参考：
 * https://github.com/cherryljr/LeetCode/blob/master/Minimum%20Window%20Substring.java
 *
 * 此类问题的 Sliding Window 模板可以参考：
 * https://github.com/cherryljr/LeetCode/blob/master/Sliding%20Window%20Template.java
 */
public class Solution {
    /**
     * @param tagList: The tag list.
     * @param allTags: All the tags.
     * @return: Return the answer
     */
    public int getMinimumStringArray(String[] tagList, String[] allTags) {
        Map<String, Integer> map = new HashMap<>();
        for (String word : tagList) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }

        int start = 0, end = 0;
        int count = map.size();
        int minLen = Integer.MAX_VALUE;
        while (end < allTags.length) {
            String endWord = allTags[end];
            if (map.containsKey(endWord)) {
                map.put(endWord, map.get(endWord) - 1);
                if (map.get(endWord) == 0) {
                    count--;
                }
            }
            end++;

            while (count == 0) {
                String beginWord = allTags[start];
                if (map.containsKey(beginWord)) {
                    map.put(beginWord, map.get(beginWord) + 1);
                    if (map.get(beginWord) > 0) {
                        count++;
                    }
                }
                minLen = Math.min(minLen, end - start);
                start++;
            }
        }

        return minLen == Integer.MAX_VALUE ? -1 : minLen;
    }
}