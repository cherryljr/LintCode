Sequence DP
方案总个数问题，并且数组不能够交换位置 =>  DP
State:
	f[i]表示从0到i层总共的走法方案个数
Function:
	爬台阶到i点总共有的方法，取决于i-1点和i-2的情况。
	也就是 f[i] = f[i - 1] + f[i - 2]
Initialize: 
	f[0] = 1, f[1] = 1.
Answer:
	f[n]

还可以用滚动数组优化一点：因为用到的变量就只有i,i-1,i-2，可以被替代。
   注意要写好‘滚动’的代码。


/*

You are climbing a stair case. It takes n steps to reach to the top.

Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?

Dynamic Programming

*/

/*
    DP[i] is dependent on wether it was reached by 2 steps or just 1 step:
    DP[i] = DP[i - 1] + DP[i - 2]
*/
public class Solution {
    /**
     * @param n: An integer
     * @return: An integer
     */
    public int climbStairs(int n) {
        // write your code here
        int[] sum = new int[n + 1];
        if (n <= 1) {
            return 1;
        }
        
        //  Initialize
        sum[0] = 1;
        sum[1] = 1;
        
        //  Function
        for (int i = 2; i <= n; i++) {
            sum[i] = sum[i - 1] + sum[i - 2];
        }
        
        //  Answer
        return sum[n];
    }
}

/*
    Based on the DP solution, think about rolling array.
    We only make use of i, i-1, and i-2. 
    Ideally, we can reduce them just to 3 variables

    1. Replace the variable
    2. Implement the rotation process:
        lastlast = last;
        last = now;
*/
public class Solution {
    public int climbStairs(int n) {
        if (n <= 1) {
            return 1;
        }
        
        int last = 1, lastlast = 1;
        int now = 0;
        for (int i = 2; i <= n; i++) {
            now = last + lastlast;
            lastlast = last;
            last = now;
        }
        
        return now;
    }
}
