/*
Description
In a rectangular square of 2×n, a 1×2 domino is used to pave the square. Input n and return the total number of paving schemes.
    1≤n≤50

Example
Example 1:
Given `n=2`, return `2`.
Input:
2
Output:
2
Explanation:
Use two 1*2 or two 2*1 dominos.

Example 2:
Given `n=3`, return `3`.
Input:
3
Output:
3
Explanation:
Use three 1*2 or three 2*1 dominos.
 */

/**
 * Approach: Fibonacci Sequence (DP)
 * 剑指Offer上面的一道经典问题了...不过LintCode这个题搬得挺没水平的...
 * 数据规模太小了（可能是怕超了吧，但是可以取模啊...所以这题出的真的不行）
 *
 * 思路很简单，先初始化前两个数 f1,f2。
 * 然后，比如我要铺 2*4 的区域，此时对于答案 f4 而言有两种方案：
 *  在 f2 的基础上，横着铺两个砖；在 f3 的基础上，竖着铺一个砖头。
 *  因此结果为：f4 = f3 + f2...即典型的费波纳茨数列。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * PS.这道题目的 Follow Up 可以参见 LeetCode 上的 Domino and Tromino Tiling 这道题目。
 * https://github.com/cherryljr/LeetCode/blob/master/Domino%20and%20Tromino%20Tiling.java
 */
public class Solution {
    /**
     * @param n: The param n means 2*n rectangular square.
     * @return: Return the total schemes.
     */
    public long getTotalSchemes(int n) {
        int f1 = 1, f2 = 1, ans = 2;
        if (n < 2) return 1;
        for (int i = 2; i <= n; i++) {
            ans = f1 + f2;
            f1 = f2;
            f2 = ans;
        }
        return ans;
    }
}