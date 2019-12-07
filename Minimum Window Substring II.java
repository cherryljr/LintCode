/*
Description
Give you a string S and a string T, S is a circular, 
please find in the string S: the smallest substring containing all the letters of T.

If such a substring does not exist in S, you should return an empty string "".
If such a substring exists in S, we guarantee that it is the only answer.

Example
Example1:
Input: 
S = "abcdc"
T = “da”
Output: "dca"
Explanation：you can rotate S into "bcdca", so the minimum coverage substring is "dca"

Example2:
Input:
S = "ADOBECODEBANC"
T = "DAOC"
Output: "CADO"
Explanation：you can rotate S into "BECODEBANCADO", so the minimum coverage substring is "CADO"
*/

/**
 * Approach: Sliding Window
 * Time Complexity : O(n)
 * Space Complexity: O(n)
  *
  *Reference: 
 *  https://github.com/cherryljr/LeetCode/blob/master/Sliding%20Window%20Template.java
 */
public class Solution {
    /**
     * @param S: A source string
     * @param T: A target string
     * @return: The String contains the smallest substring of all T letters.
     */
    public String minWindowII(String S, String T) {
        if (S == null || S.length() < T.length()) {
            return "";
        }
        
        String str = S + S;
        Map<Character, Integer> map = new HashMap<>();
        for (char c : T.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        
        // startIndex表示答案的起始位置，len表示长度（初始化为2倍S的长度）
        int count = map.size(), startIndex = Integer.MIN_VALUE, len = str.length();
        for (int begin = 0, end = 0; end < str.length(); end++) {
            char c = str.charAt(end);
            if (map.containsKey(c)) {
                map.put(c, map.get(c) - 1);
                if (map.get(c) == 0) {
                    count--;
                }
            }
            
            while (count <= 0) {
                char cBegin = str.charAt(begin);
                if (map.containsKey(cBegin)) {
                    map.put(cBegin, map.get(cBegin) + 1);
                    if (map.get(cBegin) > 0) {
                        count++;
                    }
                }
                if (end - begin + 1 < len) {
                    startIndex = begin;
                    len = end - begin + 1;
                }
                begin++;
            }
        }
        
        return startIndex != Integer.MIN_VALUE ? str.substring(startIndex, startIndex + len) : "";
    }
}