/*
Description
Given the length k, find all substrings of length k in the string str.
The characters of a substring can not be repeated and output the number of substrings that satisfy such conditions
(the same substring is counted only 1 times).

|str| <= 100000.
k <= 100000.
All characters are lowercase.

Example
Given str = "abc", k = 2, output 2.
Explanation:
Characters are not repeated, and substrings of length k have "ab", "bc".

Given str = "abc", k = 2, output 2.
Explanation:
Characters are not repeated, and substrings of length k have "a", "b".”c”.
 */

/**
 * Approach 1: Traverse
 * 从 i 开始遍历之后的 k 个字符，如果 k 个字符中均无重复字符，则找到了一个符合条件的 subString.
 * 将有效的 subString 记录到结果中即可。因为需要对结果进行去重，所以使用一个 Set 来存储结果即可。
 *
 * 时间复杂度为：O(n*k)
 */
public class Solution {
    /**
     * @param str: The string
     * @param k: The length of the substring
     * @return: The answer
     */
    public int findSubstring(String str, int k) {
        if (str == null || str.length() < k) {
            return 0;
        }

        Set<String> stringSet = new HashSet<>();
        int rst = 0;
        for (int i = 0; i + k - 1 < str.length(); i++) {
            Set<Character> set = new HashSet<>();
            boolean isLegal = true;
            for (int j = i; j < i + k; j++) {
                // 如果出现重复字符，则该段无效无效。
                if (set.contains(str.charAt(j))) {
                    isLegal = false;
                    break;
                } else {
                    set.add(str.charAt(j));
                }
            }

            if (isLegal) {
                String subString = str.substring(i, i + k);
                // 检查该 subString 是否已经被统计过了
                if (!stringSet.contains(subString)) {
                    stringSet.add(subString);
                    rst++;
                } else {
                    continue;
                }
            }
        }

        return rst;
    }
}

/**
 * Approach 2: Sliding Window
 * 维护一个大小为 k 的滑动窗口。窗口内使用 Set 来记录各个字符是否出现过。
 * 如果该字符出现过，就会使得该窗口无效，因此向右滑动窗口直至窗口重新有效。
 *
 * 时间复杂度为：O(n)
 */
public class Solution {
    /**
     * @param str: The string
     * @param k:   The length of the substring
     * @return: The answer
     */
    public int findSubstring(String str, int k) {
        if (str == null || str.length() < k) {
            return 0;
        }
        
        Set<String> stringSet = new HashSet<>();
        Map<Character, Integer> map = new HashMap<>();
        char[] chars = str.toCharArray();
        // Initialize the sliding window that consist of first k elements
        for (int i = 0; i < k; i++) {
            map.put(chars[i], map.getOrDefault(chars[i], 0) + 1);
        }
        // if there are on duplicated elements
        if (map.size() == k) {
            stringSet.add(str.substring(0, k));
        }

        for (int i = k; i < str.length(); i++) {
            map.put(chars[i], map.getOrDefault(chars[i], 0) + 1);
            map.put(chars[i - k], map.getOrDefault(chars[i - k], 0) - 1);
            if (map.get(chars[i - k]) == 0) {
                map.remove(chars[i - k]);
            }
            String subString = str.substring(i - k + 1, i + 1);
            if (map.size() == k) {
                stringSet.add(subString);
            }
        }
        return stringSet.size();
    }
}

/**
 * Approach 3: Sliding Window (Space Optimized)
 * 使用数组替代 HashMap
 */
public class Solution {
    /**
     * @param str: The string
     * @param k:   The length of the substring
     * @return: The answer
     */
    public int findSubstring(String str, int k) {
        if (str == null || str.length() < 1) {
            return 0;
        }

        int start = 0, end = 0;
        int n = str.length();
        // 记录窗口中各个字符的出现次数
        int[] visited = new int[26];
        Set<String> set = new HashSet<>();
        while (start < n) {
            if (start + k > n) {
                break;
            }
            // 如果找到了一个符合条件的 subString 就将其 add 到 set 中
            // 然后 start 向右移动一个位置继续寻找
            if (end - start == k) {
                set.add(str.substring(start, end));
                visited[str.charAt(start) - 'a']--;
                start++;
            }
            // 当窗口中有重复元素的时候，左边界需要不断向右移动直到将重复元素移出去为止
            while (end < n && visited[str.charAt(end) - 'a'] > 0) {
                visited[str.charAt(start) - 'a']--;
                start++;
            }
            if (end < n) {
                visited[str.charAt(end) - 'a']++;
                end++;
            }
        }
        return set.size();
    }
}