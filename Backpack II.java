该题与Backpack I有很大程度的相似。
唯一的区别仅仅是State表示的意义不用，但是基本解法思路是相同的。
State:
	f[i][j]表示前”i”个数，取出一些能组成j，值为背包中物品的总价值
Function:
	f[i][j] = f[i - 1][j]  （不取第i个数）
	f[i][j] = Math.max(f[i - 1][j], f[i - 1][j - A[i - 1]] + V[i - 1])  （取第i个数） // 当 j >= a[i] 时
Initialize: 
	f[i][0] = 0
	f[0][1...m] = Integer.MIN_VALUE (该方案是不可能被取到的，本题中我们要取的数是最大值，故令其为负无穷大)
	DP问题中我们经常会遇到某些方案是不可能被用到的，那么在初始化的时候我们就需要注意：
	如果我们需要取最小值，则令该方案的值为正无穷大
	如果我们需要取最大值，则令该方案的值为负无穷大
Answer:
	f[A.length][1...m]中的最大值

O(m)的做法:   
想想，我们只care 最后一行，所以一个存value的就够了。    
注意：和bakcpackI的 O(m)一样的，j是倒序的。如果没有更好的j，就不要更新。(背包九讲)

/*
Given n items with size Ai and value Vi, and a backpack with size m. 
What's the maximum value can you put into the backpack?

Example
Given 4 items with size [2, 3, 5, 7] and value [1, 5, 2, 4], and a backpack with size 10. 
The maximum value is 9.

Note
You cannot divide item into small pieces and the total size of items you choose should smaller or equal to m.

Challenge
O(n x m) memory is acceptable, can you do it in O(m) memory?

Tags Expand 
LintCode Copyright Dynamic Programming Backpack
*/


/*
	Thoughts:
	In Backpack I, we store true/false to indicate the largest j in last dp row. 
	Here, we can store dp[i][j] == max value. 

	State:
	dp[i][j] : with i-1 items that fills exaclty size j, what's the max value

	Fn:
	still, picked or did not picked A[i-1]
	1. Didn't pick. Value remains the same as if we didn't add A[i-1]
	2. Picked A[i - 1]. Hence, find out previous record dp[i-1][j - A[i - 1]], then add up the A[i-1] item's value V[i-1].
	3. Compare 1, and 2 for max value.
	dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - A[i - 1]] + V[i - 1])

	Init:
	dp[0][0] = 0; // 0 item, fills size 0, and of course value -> 0

	Return:
	dp[A.length][m]

	Note:
	when creating dp, we do (A.length + 1) for row size, simply because we get used to checking A[i-1] for prevous record ... Just keep this style. Don't get confused.
*/

// 该方法虽然能够AC,但是并不严谨，但是本题所有参数均为非负数，所以该种解法也是可以的
public class Solution {
    /**
     * @param m: An integer m denotes the size of a backpack
     * @param A & V: Given n items with size A[i] and value V[i]
     * @return: The maximum value
     */
    public int backPackII(int m, int[] A, int V[]) {
        // State
        int[][] f = new int[A.length + 1][m + 1];
        
        // Initialize
        // 相当于全部初始化为0
        
        // Function
        for (int i = 1; i <= A.length; i++) {
            for (int j = 0; j <= m; j++) {
                f[i][j] = f[i - 1][j];
                if (j >= A[i - 1]) {
                    f[i][j] = Math.max(f[i - 1][j], f[i - 1][j - A[i - 1]] + V[i - 1]);
                }
            }
        }
        
        // Answer
        return f[A.length][m];
    }
}

// 更加严谨的写法，也是根据上面分析所得的做法

public class Solution {
    /**
     * @param m: An integer m denotes the size of a backpack
     * @param A & V: Given n items with size A[i] and value V[i]
     * @return: The maximum value
     */
    public int backPackII(int m, int[] A, int V[]) {
        // State
        int[][] f = new int[A.length + 1][m + 1];
        
        // Initialize
        // f[i][0]初始化的值为0，Java在new出该数组时已经帮我么做好了
        for (int j = 1; j <= m; j++) {
        	f[0][j] = Integer.MIN_VALUE;
        }
        
        // Function
        for (int i = 1; i <= A.length; i++) {
            for (int j = 0; j <= m; j++) {
                f[i][j] = f[i - 1][j];
                if (j >= A[i - 1]) {
                    f[i][j] = Math.max(f[i - 1][j], f[i - 1][j - A[i - 1]] + V[i - 1]);
                }
            }
        }
        
        // Answer
        int max = 0;
        for (int i = 1; i <= m; i++) {
        	max = Math.max(max, f[A.length][i]);        	
        }
				
	return max;
    }
}

/*
	To use just O(m) sapce.
	Just like in Backpack I, at the end, we only care about the last row. 
    	Why not just maintain a row, always keep the max value.

	Note: Only update dp[j] if adding A[i-1] would be greater than current dp[j]
*/

public class Solution {
    /**
     * @param m: An integer m denotes the size of a backpack
     * @param A & V: Given n items with size A[i] and value V[i]
     * @return: The maximum value
     */
    public int backPackII(int m, int[] A, int V[]) {
        if (A == null || V == null || A.length == 0 || V.length == 0 || A.length != V.length || m <= 0) {
    		return 0;
    	}
    	
        // State
        int[] dp = new int[m + 1];
        
        // Initialize
        // 因为求最大的总价值，而非正好装满(背包九讲 01背包问题)
        // 故dp[i]初始化的值为0，Java在new出该数组时已经帮我么做好了
        
        // Function
        for (int i = 1; i <= A.length; i++) {
            for (int j = m; j > 0; j--) {
		// gurantee the size is big enough to put A[i-1] into the back.
                if (j >= A[i - 1]) {
                    dp[j] = Math.max(dp[j], dp[j - A[i- 1]] + V[i - 1]);   
                }
            }
        }
        
        // Answer
	return dp[m];
    }
}
