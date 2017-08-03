Two Sequence DP
与LCS非常类似,可以根据LCS的值计算出该题的值：
EditDistance(A, B) = Maxlength(A, B) - LCS(A, B)
即计算出二者之间不需要编辑的部分，然后A，B中用最长的长度减去这个LCS的长度即可
至于 A => B 还是 B => A 都不重要，因为这都是一样的

State:
	f[i][j]表示 A 的前i个字符配上 B 的前j个字符最少需要几次编辑才能够使它们相等
Function:
	A进行一个insert操作 与 B进行一次delete操作其实是相同的，故这边仅仅针对A的操作进行分析
	f[i][j] = Math.min(f[i-1][j-1], f[i-1][j] + 1, f[i][j-1] + 1)   //   A[i] == B[j]时
										 // replace		// insert			 // delete
	f[i][j] = Math.min(f[i-1][j-1], f[i-1][j],		 f[i][j-1]) + 1       //   A[i] ！= B[j]时	
Initialize:
	f[i][0] = i
	f[0][j] = j
Answer:
	f[A.length()][B.length()]

/*
Given two words word1 and word2, find the minimum number of steps required to convert word1 to word2. 
(each operation is counted as 1 step.)

You have the following 3 operations permitted on a word:

Insert a character
Delete a character
Replace a character

Example
Given word1 = "mart" and word2 = "karma", return 3.

Tags Expand 
String Dynamic Programming

Thoughts:
Draw a 2D array, consider rows as word1 and cols as word2. 
DP[i][j] means the steps (edit distance) to take to transfer word1[0 ~ i] to word2[0 ~ j]
And, we have 3 different calculations for the 3 methods:
1. Replace: DP[i][j] = word1[i-1] == word2[j-1] ? DP[i - 1][j - 1] : DP[i-1][j-1] + 1;
2. Insert: DP[i][j]  = word1[i - 1][j] + 1; // missing 1 char in word1
3. Delete: DP[i][j]  = word1[i][j - 1] + 1; // extra char in word1

Note: just remember to start from i=1,j=1, because we are using DP[i-1][j-1], becareful with border case
*/

public class Solution {
/**
     * @param word1 & word2: Two string.
     * @return: The minimum number of steps.
     */
    public int minDistance(String word1, String word2) {
        // write your code here
        if (word1 == null || word1.length() == 0) {
            return word2.length();
        }
        if (word2 == null || word2.length() == 0) {
           return word1.length(); 
        }
        
        // State
        int[][] f = new int[word1.length() + 1][word2.length() + 1];
        
        // Initialize
        for (int i = 0; i <= word1.length(); i++) {
            f[i][0] = i;
        }
        for (int j = 0; j <= word2.length(); j++) {
            f[0][j] = j;
        }
        
        // Function
        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1; j <= word2.length(); j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    f[i][j] = Math.min(f[i-1][j-1], Math.min(f[i-1][j] + 1, f[i][j-1] + 1));
                } else {
                    f[i][j] = Math.min(f[i-1][j-1], Math.min(f[i-1][j], f[i][j-1])) + 1; 
                }
            }
        }
        
        // Answer
        return f[word1.length()][word2.length()];
    }
}