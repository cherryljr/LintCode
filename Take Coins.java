/*
Description
There aren coins in a row, each time you want to take a coin from the left or the right side.
Take a total of k times to write an algorithm to maximize the value of coins.
1 <= k <= n <= 100000。
The value of the coin is not greater than 10000。

Example
Given list = [5,4,3,2,1], k = 2, return 9.
Explanation:
Take two coins from the left.

Given list = [5,4,3,2,1,6], k = 3, return 15.
Explanation:
Take two coins from the left and one from the right.
 */

/**
 * Approach 1: Sliding Window
 * 可以任意从左边或者右边取走 k 个元素，求其最大值。
 * 因为其取走的元素位置是不好确定的，因此我们不妨换个视角来考虑。
 * 即我们关注剩下的元素，因为其只能在左/右取走元素，因此剩下的必定是一段 连续的大小为 list.length-k 的subArray.
 * 我们只需要求得 和最小的这段subArray 即可。
 * 然后用整个数组的和减去这段值，就是我们需要的答案。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * 用到同样方法的问题还有： Window Sum
 * https://github.com/cherryljr/LintCode/blob/master/Window%20Sum.java
 */
public class Solution {
    /**
     * @param list: The coins
     * @param k: The k
     * @return: The answer
     */
    public int takeCoins(int[] list, int k) {
        if (list == null || list.length == 0 || k == 0) {
            return 0;
        }
        System.out.println(list.length);

        int len = list.length - k;
        int sum = 0, allSum = 0;
        int minSum;
        // Initialize the sliding window (minSum)
        for (int i = 0; i < len; i++) {
            sum += list[i];
            allSum += list[i];
        }
        minSum = sum;

        // Moving the window
        for (int i = len; i < list.length; i++) {
            allSum += list[i];
            sum += list[i] - list[i - len];
            minSum = Math.min(minSum, sum);
        }
        return allSum - minSum;
    }
}

/**
 * Approach 2: Two Pointers
 * 直接枚举左右指针的位置，即枚举出所有可能的方案。
 * 左边取 i 个，则右边就去 k-i 个。求所有方案中的最大值即可。
 * 对此我们需要求出 preSum[] 来加速我们的计算过程。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n) 需要记录 preSum[]
 */
public class Solution {
    /**
     * @param list: The coins
     * @param k: The k
     * @return: The answer
     */
    public int takeCoins(int[] list, int k) {
        int n = list.length;
        int[] preSum = new int[n + 1];
        int maxSum = 0;
        for (int i = 0; i < n; i++) {
            preSum[i + 1] = preSum[i] + list[i];
        }

        for (int i = 0; i <= k; i++) {
            int left = i;
            int right = k - i;
            int cur = preSum[n] - preSum[n - right] + preSum[left];
            maxSum = Math.max(maxSum, cur);
        }
        return maxSum;
    }
}