/*
Description
Given an array of strings, group anagrams together.
All inputs will be in lower-case.

Example
Given strs = ["eat", "tea", "tan", "ate", "nat", "bat"],
Return
[
    ["ate", "eat","tea"],
    ["nat","tan"],
    ["bat"]
]
 */

/**
 * Approach: HashMap
 * 使用 HashMap 记录分类即可。
 * 注意：虽然可以直接对单词进行排序，但是这将会耗费 O(nlogn) 的时间
 * 在单词长度较大的时候，是很不划算的，因此我们可以直接使用一个数组来记录即可。
 *
 * 这道题目在 LeetCode 上面也有，详细解析可以参考：
 * https://github.com/cherryljr/LeetCode/blob/master/Group%20Anagrams.java
 */
public class Solution {
    /**
     * @param strs: the given array of strings
     * @return: The anagrams which have been divided into groups
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> rst = new ArrayList<>();
        if (strs == null || strs.length == 0) {
            return rst;
        }

        // Record the occurrence of each character of a word
        int[][] record = new int[strs.length][128];
        for (int i = 0; i < strs.length; i++) {
            String str = strs[i];
            for (int j = 0; j < str.length(); j++) {
                record[i][str.charAt(j)]++;
            }
        }

        Map<String, List<String>> map = new HashMap<>();
        for (int i = 0; i < record.length; i++) {
            int[] asc = record[i];
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < asc.length; j++) {
                if (asc[j] > 0) {
                    // the occurrence of character j
                    sb.append(asc[j]);
                    // character j
                    sb.append((char) j);
                }
            }
            if (!map.containsKey(sb.toString())) {
                map.put(sb.toString(), new ArrayList<>());
            }
            map.get(sb.toString()).add(strs[i]);
        }

        for (String key : map.keySet()) {
            rst.add(map.get(key));
        }
        return rst;

    }
}