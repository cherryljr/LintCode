求方案总个数，存在求和动作，使其刚好等于Target => Backpack DP
State:
	f[i][j][t] 表示前i个数中取出j个数出来使得和为t的方案总数
Function:
	f[i][j][t] = f[i-1][j][t]    									// 不取第i个数
						 = f[i-1][j-1][t-a[i]]  t >= a[i]   // 取第i个数
Intialize:
	问是否可行 (DP) => f[x][0][0] = true
	问方案总数 (DP) => f[x][0][0] = 1
	问具体的所有方案 (递归 / 搜索)
Answer:
	f[A.length][k][target]
优化：f[i] 只需要用到 f[i - 1]的数，故可以用滚动数组来对额外空间进行优化 f[i % 2]

此外我们可以在原有基础上，对额外的储存空间进一步优化为二维的数组空间来储存。
详细分析见：http://www.cnblogs.com/yuzhangcmu/p/4279676.html
	
/*

Description
Given n distinct positive integers, integer k (k <= n) and a number target.

Find k numbers where sum is target. Calculate how many solutions there are?

Have you met this question in a real interview? Yes
Example
Given [1,2,3,4], k = 2, target = 5.

There are 2 solutions: [1,4] and [2,3].

Return 2.

Tags 
LintCode Copyright Dynamic Programming

*/

// Version 1
// O(N^3) extra space 3D-Arrays

public class Solution {
    /**
     * @param A: an integer array.
     * @param k: a positive integer (k <= length(A))
     * @param target: a integer
     * @return an integer
     */
    public int kSum(int A[], int k, int target) {
        // State
        int[][][] f = new int[A.length + 1][k + 1][target + 1];
        
        // Initialize
        for (int i = 0; i <= A.length; i++) {
            f[i][0][0] = 1;
        }
        
        // Funciton
        for (int i = 1; i <= A.length; i++) {
            for (int j = 1; j <= k; j++) {
                for (int t = 1; t <= target; t++) {
                    if (t >= A[i - 1]) {
                        f[i][j][t] = f[i - 1][j - 1][t - A[i - 1]];
                    }
                    f[i][j][t] += f[i - 1][j][t];
                }
            }
        }
        
        // Answer
        return f[A.length][k][target];
    }
}

// Version 2: Opitimized 
// O(N^2) extra space 2D-Arrays

public class Solution {
    /**
     * @param A: an integer array.
     * @param k: a positive integer (k <= length(A))
     * @param target: a integer
     * @return an integer
     */
    public int  kSum(int A[], int k, int target) {
        // State
        int[][] dp = new int[A.length + 1][target + 1];
        // Initialize
        // 求方案总数问题 dp[0][0] = 1
        dp[0][0] = 1;
        
        // Function
        for (int i = 1; i <= A.length; i++) {
            for (int j = target; j >= 0; j--) {
                for (int c = 1; c <= k; c++) {
                    // gurantee target is big enough
                    if (j >= A[i - 1]) {
                        dp[c][j] += dp[c - 1][j - A[i - 1]];   
                    }
                }
            }
        }
        
        // Answer
        return dp[k][target];
    }
}
