n个整数a[1…n]，装m的背包
算法的时间和空间复杂度均为: O(nm)
State:
	f[i][j]表示前”i”个数，取出一些能否组成j
Function:
	f[i][j] = f[i - 1][j – a[i]] && j >= a[i]	(取第i个数，组成j)  
or  f[i - 1][j] 				(不取第i个数，组成j)
Initialize: 
	f[0][j] = false (一个数都不去，无法组成j)
f[i][0] = true (只要一个数不取，均能够组成0)
Answer:
	f[i][j]为true的情况下，使得f[n][x]最大的x (0 <= x <= m)
	
发现f[i][j]的值仅仅与上一层相关，即f[i][j]的值与i-2,i-3无关。
故我们可以联想到使用1维数组即可，这样可以对算法的空间复杂度进一步优化

/*
Given n items with size Ai, an integer m denotes the size of a backpack. 
How full you can fill this backpack?

Example
If we have 4 items with size [2, 3, 5, 7], the backpack size is 11, we can select [2, 3, 5], 
so that the max size we can fill this backpack is 10. 
If the backpack size is 12. we can select [2, 3, 7] so that we can fulfill the backpack.

You function should return the max size we can fill in the given backpack.

Note
You can not divide any item into small pieces.

Challenge
O(n x m) time and O(m) memory.

O(n x m) memory is also acceptable if you do not know how to optimize memory.

Tags Expand 
LintCode Copyright Dynamic Programming Backpack


*/

/* 
    State
    DP[i][j]: i is the index of Ai, and j is the size from (0 ~ m). 
        It means: depending if we added A[i-1], can we full-fill j-space? Return ture/false.
        Note: that is, even j == 0, and I have a item with size == 0. There is nothing to add, which means the backpack can reach j == 0. True.
        However, if we have a item with size == 2, but I need to fill space == 1. 
        I will either pick/not pick item of size 2; either way, can't fill a backpack with size 1. False;
    Function:
        DP[i][j] = DP[i - 1][j] || DP[i - 1][j - A[i - 1]];   // based on if previous value is true/false: 1. didn't really add A[i-1] || 2. added A[i-1].
    Init:
        DP[0][0] = true; // 0 space and 0 items, true.
        All the rest are false;
    
    Return the very last j that makes dp[A.length][j] true.
*/

public class Solution {
    /**
     * @param m: An integer m denotes the size of a backpack
     * @param A: Given n items with size A[i]
     * @return: The maximum size
     */
    public int backPack(int m, int[] A) {
        // State
        boolean[][] f = new boolean[A.length + 1][m + 1];
        
        // Initialize
        f[0][0] = true;
        for (int i = 1; i <= A.length; i++) {
            f[i][0] = true;
        }
        for (int j = 1; j <= m; j++) {
            f[0][j] = false;
        }
        
        // Function
        for (int i = 1; i <= A.length; i++) {
            for (int j = 0; j <= m; j++) {
                f[i][j] = f[i - 1][j];
                if (j >= A[i - 1] && f[i - 1][j - A[i - 1]]) {
                    f[i][j] = true;
                }
            }
        }
        
        // Answer
        for (int j = m; j >= 0; j--) {
            if (f[A.length][j]) {
                return j;
            }
        }
        
        return 0;
    }
}


/*
1D array: memory mxn, space m. Tricky tho ...

Looking at the 2D version, we are really just checking the DP with fixed i=A.length.

DP[j]: can we fit i items into j?
DP[j] == false || DP[j - A[i - 1]].
Similar two cases:
1. Can't fit in, set false;
2. Can fit in, then just return if (j - A[i - 1]) works 

Core difference: only set the DP[j] when (j - A[i - 1] >= 0 && DP[j - A[i - 1]]): since we are running from m ~ 0, we don't want to override some existing values 
*/

public class Solution {
    public int backPack(int m, int[] A) {
        boolean[] DP = new boolean[m + 1];
        DP[0] = true;
        
        for (int i = 1; i <= A.length; i++) {
            for (int j = m; j >= 0; j--) {
                if (j - A[i - 1] >= 0 && DP[j - A[i - 1]]) {
                    DP[j] = true;
                }
            }
        }

        for (int j = m; j >= 0; j--) {
            if (DP[j]) {
                return j;
            }
        }
        return 0;
    }
}
