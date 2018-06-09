/*
Description
There is now an order with demand for n items, and the demand for the i-th item is order[i].
The factory has m production modes. Each production mode is shaped like [p[1],p[2],...p[n]],
that is, produce p[1] first items, p[2] second items... You can use multiple production modes.
Please tell me how many items do not meet the demand at least in the case of not exceeding the demand of any kind of items?

1≤n,m≤7
1≤order[i]≤10
0≤pattern[i][j]≤10

Example
Given order=[2,3,1], pattern=[[2,2,0],[0,1,1],[1,1,0]] , return 0.
Explanation:
Use [0,1,1] once, [1,1,0] twice, remaining [0,0,0].

Given order=[2,3,1], pattern=[[2,2,0]] , return 2.
Explanation:
Use [2,2,0] once, remaining [0,1,1].
 */

/**
 * Approach 1: Backtracking (Stack Overflow)
 * 看到题目的数据范围，想到这肯定是考察 DFS 了。
 * 采用暴力的方法，所有的方案都试一遍即可。
 * 对于每一个模式可能的使用次数为：0 ~ min(order[i] / pattern[j][i])
 * 因此这是一个类似求 Combination Sum 的过程。
 *
 * 因为我们没有进优化，本题计算量又比较大（对象为数组，每次需要 O(n) 的空间和时间），所以直接爆了。
 * 但是我们知道一个模式最多只能被使用 min(order[i] / pattern[j][i]) 次，
 * 因此可以利用这个条件进行剪枝操作，具体实现参见 Approach 2.
 *
 * Combination Sum：
 *  https://github.com/cherryljr/LintCode/blob/master/Combination%20Sum.java
 */
public class Solution {
    private int minSum = Integer.MAX_VALUE;

    /**
     * @param order: The order
     * @param pattern: The pattern
     * @return: Return the number of items do not meet the demand at least
     */
    public int getMinRemaining(int[] order, int[][] pattern) {
        if (order == null || order.length == 0) {
            return 0;
        }

        int[] arr = new int[order.length];
        dfs(order, pattern, arr, 0);
        return minSum;
    }

    private void dfs(int[] order, int[][] pattern, int[] arr, int startIndex) {
        // 递归的终止条件，并利用 isGood() 进行了剪枝，使得其不满足时可以提前退出
        if (!isGood(order, arr) || startIndex == pattern.length) {
            return;
        }

        for (int i = startIndex; i < pattern.length; i++) {
            // Use it
            for (int j = 0; j < arr.length; j++) {
                arr[j] += pattern[i][j];
            }
            // 因为一个模式可以被重复使用，所以依然传入位置 i (与 Combination Sum 相同)
            dfs(order, pattern, arr, i);
            // Backtracking
            for (int j = 0; j < arr.length; j++) {
                arr[j] -= pattern[i][j];
            }
        }
    }

    private boolean isGood(int[] order, int[] arr) {
        int diffSum = 0;
        for (int i = 0; i < order.length; i++) {
            int diff = order[i] - arr[i];
            if (diff < 0) {
                return false;
            }
            diffSum += diff;
        }
        minSum = Math.min(minSum, diffSum);
        return true;
    }
}

/**
 * Approach 2: Backtracking
 * 思路与 Approach 1 相同，只不过加入了 剪枝 优化。
 * 主要不同就是在于使用了 getLimit()，其他方面基本相同
 */
public class Solution {
    private int minSum = Integer.MAX_VALUE;

    /**
     * @param order: The order
     * @param pattern: The pattern
     * @return: Return the number of items do not meet the demand at least
     */
    public int getMinRemaining(int[] order, int[][] pattern) {
        if (order == null || order.length == 0) {
            return 0;
        }

        int[] arr = new int[order.length];
        dfs(order, pattern, arr, 0);
        return minSum;
    }

    private void dfs(int[] order, int[][] pattern, int[] arr, int startIndex) {
        // 递归的终止条件，并利用 isGood() 进行了剪枝，使得其不满足时可以提前退出
        if (!isGood(order, arr) || startIndex == pattern.length) {
            return;
        }

        // Not use pattern[startIndex] mode
        dfs(order, pattern, arr, startIndex + 1);

        // Use pattern[startIndex] mode limit times at most
        int limit = getLimit(order, pattern[startIndex]);
        for (int i = 0; i < limit; i++) {
            for (int j = 0; j < order.length; j++) {
                arr[j] += pattern[startIndex][j];
            }
            dfs(order, pattern, arr, startIndex + 1);
        }
        // Backtracking
        for (int i = 0; i < limit; i++) {
            for (int j = 0; j < order.length; j++) {
                arr[j] -= pattern[startIndex][j];
            }
        }
    }

    private int getLimit(int[] order, int[] pattern) {
        int limit = -1;
        for (int i = 0; i < order.length; i++) {
            if (order[i] < pattern[i]) {
                return -1;
            } else {
                if (pattern[i] > 0) {
                    int temp = order[i] / pattern[i];
                    if (temp < limit || limit < 0) {
                        limit = temp;
                    }
                }
            }
        }
        return limit;
    }

    private boolean isGood(int[] order, int[] arr) {
        int diffSum = 0;
        for (int i = 0; i < order.length; i++) {
            int diff = order[i] - arr[i];
            if (diff < 0) {
                return false;
            }
            diffSum += diff;
        }
        minSum = Math.min(minSum, diffSum);
        return true;
    }
}