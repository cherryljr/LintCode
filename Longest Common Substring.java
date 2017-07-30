最大值问题，存在两个序列，并且每个元素顺序不能调换 => Two Sequence DP

该题与Longest Common Subsequence非常类似，不同点在于要求公共子序列必须连续。
在原有基础上进行稍微的修改即可。
State:
	首先我们需要修改f[i][j]所表示的状态含义。
	受到Longest Incresing Subsequence该题的启发，我们应该将f[i][j]代表为：
	字符串A的前i个字符中以第i个字符结尾的 与 字符串B的前j个字符中以第j个字符结尾的最长公共子序列长度。
Function:
	因为必须是以第i / j个字符为结尾的LCS，故当这两个字符相等时：f[i][j] = f[i - 1][j - 1] + 1
	不相等时则为0
Answer:
	最后结果就是 Math.max(f[0...n][0...m])

/*
Given two strings, find the longest common substring.

Return the length of it.

Example
Given A = "ABCD", B = "CBCE", return 2.

Note
The characters in substring should occur continuously in original string. This is different with subsequence.

Challenge
O(n x m) time and memory.

Tags Expand 
LintCode Copyright Longest Common Subsequence Dynamic Programming

Thoughts:
1. Compare all i X j.
2. Use a D[i][j] to mark the amount of common substring based on D[i - 1][j -1]. Could be 0.
3. track max length

NOTE: create 2D array that's [N + 1][M + 1] because we want to hold D[n][M] in the 2d array

*/

public class Solution {
    /**
     * @param A, B: Two string.
     * @return: the length of the longest common substring.
     */
    public int longestCommonSubstring(String A, String B) {
        // write your code here
        if (A == null || A.length() == 0 || B == null || B.length() == 0) {
            return 0;
        }
        
        // State
        int[][] f = new int[A.length() + 1][B.length() + 1];
        int max = 0;
        
        // Initialize 因为int数组初始化均为0，故在构建数组时，Java已经默认帮我们完成了
        
        // Function
        for (int i = 1; i <= A.length(); i++) {
            for (int j = 1; j <= B.length(); j++) {
                if (A.charAt(i - 1) == B.charAt(j - 1)) {
                    f[i][j] = f[i - 1][j - 1] + 1;
                } else {
                    f[i][j] = 0;
                }
                max = Math.max(f[i][j], max);
            }
        }
        
        // Answer
        return max;
    }
}