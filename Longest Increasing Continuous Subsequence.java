该题有两种解法：
方法一：
	简单明了，分别 从左到右 和 从右到左 遍历一遍数组，如果发现下一个数比当前数要大则加一
	否则置为1，最后返回 Math.max(length) 
方法二：
	Sequence DP
	求最大值，不能调换元素位置 => Sequenc DP
	与 Longest Increasing Subsequence 十分类似，区别在于本题要求为连续子序列。
	并且有 从左到右 与 从右到左 两个方向。
	故我们可以实现一个 DP 算法的方法来找到 LIS,
	第一次调用得到 Left to Right 的 LIS, 然后调换数组顺序再次调用，得到 Right to Left 的 LIS
	二者取最大值便是我们要的结果
	
/*
Description
Give an integer array，find the longest increasing continuous subsequence in this array.
An increasing continuous subsequence:
	Can be from right to left or from left to right.
	Indices of the integers in the subsequence should be continuous.

Notice
O(n) time and O(1) extra space.

Have you met this question in a real interview? Yes

Example
For [5, 4, 2, 1, 3], the LICS is [5, 4, 2, 1], return 4.

For [5, 1, 2, 3, 4], the LICS is [1, 2, 3, 4], return 4.

Tags 
Array Dynamic Programming Enumeration
*/

// Version 1
public class Solution {
    public int longestIncreasingContinuousSubsequence(int[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }
        
        int n = A.length;
        int answer = 1;
        
        // from left to right
        int length = 1; // just A[0] itself
        for (int i = 1; i < n; i++) {
            if (A[i] > A[i - 1]) {
                length++;
            } else {
                length = 1;
            }
            answer = Math.max(answer, length);
        }
        
        // from right to left
        length = 1;
        for (int i = n - 2; i >= 0; i--) {
            if (A[i] > A[i + 1]) {
                length++;
            } else {
                length = 1;
            }
            answer = Math.max(answer, length);
        }
        
        return answer;
    }
}

public class Solution {
    /**
     * @param A an array of Integer
     * @return  an integer
     */
    
    int LIS(int[] A) {
    	  // State
        int[] f = new int[A.length];
        
        // Initialize & Function
        int i, res = 0;
        for (i = 0; i < A.length; ++i) {
            f[i] = 1;
            if (i > 0 && A[i-1] < A[i]) {
                f[i] = f[i-1] + 1;
            }
            if (f[i] > res) {
                res = f[i];
            }
        }
        
        // Answer
        return res;
    }
     
    public int longestIncreasingContinuousSubsequence(int[] A) {
        int n = A.length;
        // Left to Rigth
        int r1 = LIS(A);
        int i = 0, j = n-1, t;
        
        // Change the Order
        while (i < j) {
            t = A[i];
            A[i] = A[j];
            A[j] = t;
            ++i;
            --j;
        }
        
        // Right to Left
        int r2 = LIS(A);
        
        // Answer
        if (r1 > r2) {
            return r1;
        }
        else {
            return r2;
        }
    }
}
