/*
Description
Given a string S, find the length of the longest substring T that contains at most k distinct characters.

Example
Example 1:
Input: S = "eceba" and k = 3
Output: 4
Explanation: T = "eceb"

Example 2:
Input: S = "WORLD" and k = 4
Output: 4
Explanation: T = "WORL" or "ORLD"

Challenge
O(n) time
 */

/**
 * Approach: Sliding Window
 * Using Sliding Window Template
 * Detail explanation about the template is here:
 *  https://github.com/cherryljr/LeetCode/blob/master/Sliding%20Window%20Template.java
 */
public class Solution {
    /**
     * @param s: A string
     * @param k: An integer
     * @return: An integer
     */
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        Map<Character,Integer> map = new HashMap<>();
        int count = 0;    // this value could be optimized by map.size()
        int maxLen = 0;

        for (int left = 0, right = 0; right < s.length(); right++) {
            char cRight = s.charAt(right);
            map.put(cRight, map.getOrDefault(cRight, 0) + 1);
            if (map.get(cRight) == 1) {
                count++;
            }

            // counter > 2 means that
            // there are more than k distinct characters in the current window.
            // So we should move the sliding window's left bound.
            while (count > k) {
                char cLeft = s.charAt(left);
                map.put(cLeft, map.get(cLeft) - 1);
                if (map.get(cLeft) == 0) {
                    count--;
                }
                left++;
            }
            // Pay attention here!
            // We don't get/update the result in while loop above
            // Because if the number of distinct character isn't big enough, we won't enter the while loop
            // eg. s = "abc" (We'd better update the result here to avoid getting 0 length)
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }
}