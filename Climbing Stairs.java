/*
Description
You are climbing a stair case. It takes n steps to reach to the top.
Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?

Example
Given an example n=3 , 1+1+1=2+1=1+2=3
return 3

Tags
Dynamic Programming
 */

/**
 * Approach 1: Sequence DP
 * 方案总个数问题，并且数组不能够交换位置 =>  DP
 * State:
 *  dp[i]表示 从0到i层 总共的走法方案个数
 * Function:
 *  爬台阶到i点总共有的方法，取决于 i-1点 和 i-2点 的情况。
 *  也就是 dp[i] = dp[i - 1] + dp[i - 2]
 * Initialize:
 *  dp[0] = 1, dp[1] = 1.
 * Answer:
 *  dp[n]
 *
 * 时间复杂度：O(n) 空间复杂度：O(n)
 */
public class Solution {
    /**
     * @param n: An integer
     * @return: An integer
     */
    public int climbStairs(int n) {
        int[] dp = new int[n + 1];
        //  Initialize
        dp[0] = 1;
        dp[1] = 1;

        //  Function
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        //  Answer
        return dp[n];
    }
}

/**
 * Approach 2: Sequence DP (Optimized by Sliding Array)
 * 因为 dp[i] 的状态仅仅取决于 dp[i-1] 和 dp[i-2].
 * 所以我们只需要保存这两个状态的值即可，再前面的状态我们无需关心。
 * 即我们可以 滚动数组 的方式，覆盖掉已经没用的状态，仅仅保持需要的这两个状态即可。
 * 而滚动数组最简单的实现方式就是对原本的 数组下标 进行取余操作。
 * （并且如果遇到数组大小为 2^n 的时候，我们还能通过 &(2^n-1) 操作对 %2^n 运算进行优化）
 *
 * 在 DP 问题中，我们经常会使用 滚动数组 对空间复杂度进行优化。
 * 该方法直接简洁明了，并且几乎不需要对原来的代码进行改动。
 * 对于 一维序列的DP问题(Sequence DP) 我们通常可以使用一个固定大小的数组 dp[k] 来作为一个滚动数组。
 * k 的值由分析状态方程确定，即 为了计算出当前状态，我们需要保留多少个 之前的状态。
 * 经过优化后 空间复杂度 可以由 O(n) 降低到 O(1)
 * 类似的问题有：
 * https://github.com/cherryljr/LintCode/blob/master/House%20Robber.java
 *
 * 对于 二维矩阵的DP问题(Matrix DP) 我们通常可以使用一个固定大小的二维数组 dp[k][n] 来作为一个滚动数组。
 * 同样 k 的值由分析状态方程确定。在 二维矩阵问题中，
 * 通常 当前状态 是由前一 行/列 的状态 和 前一个或多个状态(通常就是指其左侧的点) 所决定的。
 * 经过优化后 空间复杂度 可以由 O(mn) 降低到 O(n)
 * 类似的问题有：
 * https://github.com/cherryljr/LintCode/blob/master/Unique%20Path.java
 * https://github.com/cherryljr/LintCode/blob/master/Unique%20Paths%20II.java
 * https://github.com/cherryljr/LintCode/blob/master/Minimum%20Path%20Sum.java
 * https://github.com/cherryljr/LintCode/blob/master/Edit%20Distance.java
 */
public class Solution {
    /**
     * @param n: An integer
     * @return: An integer
     */
    public int climbStairs(int n) {
        if (n <= 0) {
            return 0;
        }

        int[] dp = new int[2];
        // Initialize
        dp[0] = 1;
        dp[1] = 1;

        // Function
        for (int i = 2; i <= n; i++) {
            dp[i & 1] = dp[(i-1) & 1] + dp[(i-2) & 1];
        }

        // Answer
        return dp[n & 1];
    }
}

/**
 * Approach 3: Sequence DP (Optimized by Sliding Array)
 * 同样是利用 滚动数组 进行优化，但是没有那么直白，需要进行一定的理解。
 * 对各个参数赋予了各自的意义，但其本质还是滚动数组的思想。
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
