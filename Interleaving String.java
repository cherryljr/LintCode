Treu or False, 输入的字符串顺序不可调 => Two Sequnce DP
该题虽然有三个参数，但是s3实际上是不会改变的，所以还是一个Two Sequence DP
State:
	f[i][j]: 表示s1的前i个字符与s2的前j个字符能否组成s3的前i+j个字符
Initialize:
	s3全由s1组成
	f[i][0] = true // 当 s1.charAt(i - 1) == s3.charAt(i - 1) && result[i - 1][0] 成立时
	s3全由s2组成
	f[0][j] = true // 当 s2.charAt(j - 1) == s3.charAt(j - 1) && result[0][j - 1] 成立时
Function:
	s1的第i个字符与s3的第i+j个字符相等，并且f[i-1][j]为true时 => f[i][j] = true
  s2的第j个字符与s3的第i+j个字符相等，并且f[i][j-1]为true时 => f[i][j] = true
Answer:
	f[s1.length()][s2.length()]
	
/*
Given three strings: s1, s2, s3, determine whether s3 is formed by the interleaving of s1 and s2.

Example
For s1 = "aabcc", s2 = "dbbca"

When s3 = "aadbbcbcac", return true.
When s3 = "aadbbbaccc", return false.
Challenge
O(n2) time or better

Tags Expand 
Longest Common Subsequence Dynamic Programming

Attempt: 
DP[i][j]: boolean that if first S1(i) chars and first S2(j) chars can interleavign first S3(i + j)
Match one char by one char. We have 2 conditions: match s1 or s2 char, Let's do double-for-loop on s1 and s2
1. match s1: s3.charAt(i + j -1) == s1.charAt(i - 1) && DP[i - 1][j]; // makes sure DP[i-1][j] also works before adding s1[i-1] onto the match list
2. match s2: s3.charAt(i + j -1) == s2.charAt(j - 1) && DP[i][j - 1]// similar as above

Note:
DP ususally start i == 1, and always use (i - 1) in the loop... 
this is all because we are trying to get DP[i][j], which are 1 index more than length
*/

public class Solution {
    /**
     * Determine whether s3 is formed by interleaving of s1 and s2.
     * @param s1, s2, s3: As description.
     * @return: true or false.
     */
    public boolean isInterleave(String s1, String s2, String s3) {
        // write your code here
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }
        
        // State 
        boolean[][] result = new boolean[s1.length() + 1][s2.length() + 1];
        
        // Initialize
        result[0][0] = true;
        for (int i = 1; i <= s1.length(); i++) {
            if (s1.charAt(i - 1) == s3.charAt(i - 1) && result[i - 1][0]) {
                result[i][0] = true;
            }
        }
        for (int j = 1; j <= s2.length(); j++) {
            if (s2.charAt(j - 1) == s3.charAt(j - 1) && result[0][j - 1]) {
                result[0][j] = true;
            }
        }
        
        // Funciton
        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                if ((s1.charAt(i - 1) == s3.charAt(i + j - 1) && result[i - 1][j]) ||
                   (s2.charAt(j - 1) == s3.charAt(i + j - 1) && result[i][j - 1])) {
                    result[i][j] = true;   
                }
            }
        }
        
        // Answer
        return result[s1.length()][s2.length()];
    }
}