/*
Description
Our normal words do not have more than two consecutive letters.
If there are three or more consecutive letters, this is a tics.
Now give a word, from left to right, to find out the starting point and ending point of all tics.

The input string length is n, n <= 100000.

Example
Given str = "whaaaaatttsup", return [[2,6],[7,9]].
Explanation:
"aaaa" and "ttt" are twitching letters, and output their starting and ending points.

Given str = "whooooisssbesssst", return [[2,5],[7,9],[12,15]].
Explanation:
"ooo", "sss" and "ssss" are twitching letters, and output their starting and ending points.
 */

/**
 * Approach: Sliding Window
 * 当 str.charAt(i) == str.charAt(i+1) 的时候，窗口右边界开始滑动。
 * 当其不成立的时候，检查窗口大小是否大于 2 即可。
 *
 * 时间复杂度：O(n)
 */
public class Solution {
    /**
     * @param str: the origin string
     * @return: the start and end of every twitch words
     */
    public int[][] twitchWords(String str) {
        if (str == null || str.length() <= 2) {
            return new int[][]{};
        }

        List<List<Integer>> rst = new LinkedList<>();
        for (int i = 0; i + 1 < str.length(); i++) {
            if (str.charAt(i) == str.charAt(i + 1)) {
                int start = i;
                while (i + 1 < str.length() && str.charAt(i) == str.charAt(i + 1)) {
                    i++;
                }
                if (i - start > 1) {
                    rst.add(Arrays.asList(new Integer[]{start, i}));
                }
            }
        }

        int[][] ans = new int[rst.size()][2];
        for (int i = 0; i < rst.size(); i++) {
            ans[i][0] = rst.get(i).get(0);
            ans[i][1] = rst.get(i).get(1);
        }
        return ans;
    }
}