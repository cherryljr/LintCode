最大值问题，存在两个序列，并且每个元素顺序不能调换 => Two Sequence DP
Note: 字符串 + 前i个字符 => 开辟 n+1 个数组空间

State:
	f[i][j]表示字符串A的前i个字符配上字符串B的前j个字符的LCS长度
Function:
	f[i][j] = f[i - 1][j - 1] + 1    //  当 A[i] == B[j] 时
		= Math.max(f[i - 1][j], f[i][j - 1])   // 当 A[i] != B[j] 时
Initialize: 
	f[0][i] = 0, f[i][0] = 0
Answer:
	f[A.length()][B.length()]


/*
Given two strings, find the longest comment subsequence (LCS).

Your code should return the length of LCS.

Example
For "ABCD" and "EDCA", the LCS is "A" (or D or C), return 1

For "ABCD" and "EACB", the LCS is "AC", return 2

Clarification
What's the definition of Longest Common Subsequence?

    * The longest common subsequence (LCS) problem is to find the longest subsequence common to all sequences in a set of sequences 
    (often just two). 
    (Note that a subsequence is different from a substring, 
    for the terms of the former need not be consecutive terms of the original sequence.)
    It is a classic computer science problem, the basis of file comparison programs such as diff, and has applications in bioinformatics.

    * https://en.wikipedia.org/wiki/Longest_common_subsequence_problem

Tags Expand 
LintCode Copyright Longest Common Subsequence Dynamic Programming
*/

public class Solution {
    /**
     * @param A, B: Two strings.
     * @return: The length of longest common subsequence of A and B.
     */
    public int longestCommonSubsequence(String A, String B) {
        if (A == null || B == null || A.length() == 0 || B.length() == 0) {
            return 0;
        }
        
        // State
        int[][] check = new int[A.length()  + 1][B.length() + 1];
        
        // Initialize 因为int数组初始化均为0，故在构建数组时，Java已经默认帮我们完成了
        
        // Function
        for (int i = 1; i <= A.length(); i++) {
            for (int j = 1; j <= B.length(); j++) {
                if (A.charAt(i - 1) == B.charAt(j - 1)) {
                    check[i][j] = check[i - 1][j - 1] + 1;
                } else {
                    check[i][j] = Math.max(check[i][j - 1], check[i - 1][j]);
                }
            }
        }
        
        // Answer
        return check[A.length()][B.length()];
    }
}


