动态规划(DP)的经典入门问题
Traverse / DFS  O(2^N)  =>  Divide and Conquer  O(2^N)  =>  使用记忆化搜索进行优化 O(N^2)  =>  动态规划（自底向上 / 自顶向下）
通过以上的过程我们可以认识到DP实质上就是解决重复计算的搜索算法。
它可以通过记忆化搜索和loop来实现。
本题给出了3中解法：
	1. Divide and Conquer optimized by Memorize Search(使用一个二维数组来存储已经计算过的值，避免重复计算)
	2. Dynamic Programming 自底向上
	3. Dynamic Programming 自顶向下

/*

Description

Given a triangle, find the minimum path sum from top to bottom. Each step you may move to adjacent numbers on the row below.

Notice

Bonus point if you are able to do this using only O(n) extra space, where n is the total number of rows in the triangle.

Have you met this question in a real interview? Yes

Example
Given the following triangle:

[
     [2],
    [3,4],
   [6,5,7],
  [4,1,8,3]
]
The minimum path sum from top to bottom is 11 (i.e., 2 + 3 + 5 + 1 = 11).

Tags 
Dynamic Programming

*/

//	Version 1 : Divide and Conquer optimized by Memorize Search
//	Use minSum to save the nums, so we can avoid calculate them repeatly.
public class Solution {
    private int n;
    private int[][] minSum;
    private int[][] triangle;

    private int search(int x, int y) {
        if (x >= n) {
            return 0;
        }

        if (minSum[x][y] != Integer.MAX_VALUE) {
            return minSum[x][y];
        }
        //	Divide 
				int leftSum = search(x + 1, y);
				int rightSum = search(x + 1, y + 1);
				//	Merge
        minSum[x][y] = Math.min(leftSum, rightSum) + triangle[x][y];
        
        return minSum[x][y];
    }

    public int minimumTotal(int[][] triangle) {
        if (triangle == null || triangle.length == 0) {
            return -1;
        }
        if (triangle[0] == null || triangle[0].length == 0) {
            return -1;
        }
        
        this.n = triangle.length;
        this.triangle = triangle;
        this.minSum = new int[n][n];
				
				//	Initial the minSum
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                minSum[i][j] = Integer.MAX_VALUE;
            }
        }

        return search(0, 0);
    }
} 

//	Version 2: Bottom-Up

public class Solution {
    /**
     * @param triangle: a list of lists of integers.
     * @return: An integer, minimum path sum.
     */
    public int minimumTotal(int[][] triangle) {
        if (triangle == null || triangle.length == 0) {
            return -1;
        }
        if (triangle[0] == null || triangle[0].length == 0) {
            return -1;
        }
        
        // state: f[x][y] = minimum path value from x,y to bottom
        int n = triangle.length;
        int[][] f = new int[n][n];
        
        // initialize 
        for (int i = 0; i < n; i++) {
            f[n - 1][i] = triangle[n - 1][i];
        }
        
        // bottom up
        for (int i = n - 2; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                f[i][j] = Math.min(f[i + 1][j], f[i + 1][j + 1]) + triangle[i][j];
            }
        }
        
        // answer
        return f[0][0];
    }
}

// version 3: top-down
public class Solution {
    /**
     * @param triangle: a list of lists of integers.
     * @return: An integer, minimum path sum.
     */
    public int minimumTotal(int[][] triangle) {
        if (triangle == null || triangle.length == 0) {
            return -1;
        }
        if (triangle[0] == null || triangle[0].length == 0) {
            return -1;
        }
        
        // state: f[x][y] = minimum path value from 0,0 to x,y
        int n = triangle.length;
        int[][] f = new int[n][n];
        
        // initialize 
        // 注意：自顶向下时如果动态规划是二维的数组，就需要把f[i][0]和f[0][i]都进行初始化，
        // 即将矩阵的边缘进行一次初始化，因为我们基本都是要用到这些值的。
        f[0][0] = triangle[0][0];
        for (int i = 1; i < n; i++) {
            f[i][0] = f[i - 1][0] + triangle[i][0];
            f[i][i] = f[i - 1][i - 1] + triangle[i][i];
        }
        
        // top down
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < i; j++) {
                f[i][j] = Math.min(f[i - 1][j], f[i - 1][j - 1]) + triangle[i][j];
            }
        }
        
        // answer
        int best = f[n - 1][0];
        for (int i = 1; i < n; i++) {
            best = Math.min(best, f[n - 1][i]);
        }
        return best;
    }
}
