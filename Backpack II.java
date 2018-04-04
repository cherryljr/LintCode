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

/**
 * Approach 1: DP
 * 该题与Backpack I有很大程度的相似。
 * 唯一的区别仅仅是State表示的意义不用，但是基本解法思路是相同的。
 *
 * State:
 *  f[i][j]表示前”i”个数，取出一些能组成容量大小j，值为背包中物品的总价值
 * Function:
 *  f[i][j] = f[i - 1][j]  （不取第i个数）
 *  f[i][j] = Math.max(f[i - 1][j], f[i - 1][j - A[i - 1]] + V[i - 1])  （取第i个数） // 当 j >= a[i] 时
 * Initialize:
 *  f[0][i] = 0, f[i][0] = 0
 *  矩阵的第一行/列初始化为 0
 * Answer:
 *  f[A.length][1...m]中的最大值
 *
 * 关于初始化问题，当遇到给定一个 target，
 * 要求只能是 正好达到 target 的话，我们可以将 f[0][0] 初始化为 需要的值，其他全部初始化为 Integer.MAX_VALUE/MIN_VALUE;
 *  如果我们需要取最小值，则令该方案的值为正无穷大
 *  如果我们需要取最大值，则令该方案的值为负无穷大
 * 而要求是 <= target 均可的话，在初始化的时候，全部用 0 即可。
 * 即对应的 正好完全装入 和 装入的最大值（不一定要装满）
 * -- 背包九讲
 *
 * 对于上述讨论到的 初始化 问题，本题属于不一定需要装满的类型。我们可以对比必须 正好达到target 类型的题目来加深印象：
 * https://github.com/cherryljr/NowCoder/blob/master/%E6%8D%A2%E9%92%B1%E7%9A%84%E6%9C%80%E5%B0%91%E8%B4%A7%E5%B8%81%E6%95%B0.java
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
        int[][] f = new int[A.length + 1][m + 1];

        // Initialize
        // 因为求最大的总价值，而非正好装满(背包九讲 01背包问题)
        // 故初始化的值为0，Java在new出该数组时已经帮我么做好了

        // Function
        for (int i = 1; i <= A.length; i++) {
            for (int j = 0; j <= m; j++) {
                // 不取当前值
                f[i][j] = f[i - 1][j];
                if (j >= A[i - 1]) {
                    // 取当前值
                    f[i][j] = Math.max(f[i - 1][j], f[i - 1][j - A[i - 1]] + V[i - 1]);
                }
            }
        }

        return f[A.length][m];
    }
}

/**
 * Approach 2: DP (Optimize Space Complexity)
 * 由 Approach 1 可得，我们当前状态仅仅由上一行的 两个元素 决定：dp[i-1][j] 和 dp[i-1][j-A[i-1]]。
 * 因此实际上我们只需要保留 一行 元素就够用了。
 * 注意：计算的时候，我们要先从 最后一列 开始，向前递推计算。
 * 这样才能保证我们在计算 dp[j] 的时候，使用的dp[j-A[i-1]] 是 上一行的数据。
 * 如果 从头向后 计算的话，我们使用的 dp[j-A[i-1]] 就变成了当前行的数据了，
 * 相当于 dp[i][j-A[i-1]]. 就变成了 完全背包问题 了。而这二者是有本质区别的。
 * 
 * 如果不能理解上述过程，可以先去看看 完全背包问题 详解：
 * https://github.com/cherryljr/NowCoder/blob/master/%E6%8D%A2%E9%9B%B6%E9%92%B1.java
 * 
 * 上述优化是建立在已经非常熟悉 递推 过程并且具备良好空间感的基础上的。
 * 如果感到吃力，请先把基础学好...
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
            // 注意与 完全背包问题 区分，j是从 m 向 0 进行遍历的
            for (int j = m; j > 0; j--) {
                // gurantee the size is big enough to put A[i-1] into the back.
                if (j >= A[i - 1]) {
                    // 此处的 dp[j] 相当于 dp[i-1][j]
                    // dp[j-A[i-1]] 相当于 dp[i-1][j-A[i-1]]
                    dp[j] = Math.max(dp[j], dp[j - A[i- 1]] + V[i - 1]);
                }
            }
        }

        // Answer
        return dp[m];
    }
}
