/*
Description
There are a row of n houses, each house can be painted with one of the three colors: red, blue or green. 
The cost of painting each house with a certain color is different. 
You have to paint all the houses such that no two adjacent houses have the same color.

The cost of painting each house with a certain color is represented by a n x 3 cost matrix. 
For example, costs[0][0] is the cost of painting house 0 with color red; 
costs[1][2] is the cost of painting house 1 with color green, and so on... 
Find the minimum cost to paint all houses.

Notice
All costs are positive integers.

Example
Given costs = [[14,2,11],[11,14,5],[14,3,10]] return 10

house 0 is blue, house 1 is green, house 2 is blue, 2 + 5 + 3 = 10

Tags 
LinkedIn Dynamic Programming
*/

/**
 * 这道题说有n个房子，每个房子可以用红绿蓝三种颜色刷，每个房子的用每种颜色刷的花费都不同，
 * 限制条件是相邻的房子不能用相同的颜色来刷，现在让我们求刷完所有的房子的最低花费是多少。
 * 这题跟House Robber II和House Robber很类似，不过那题不是每个房子都抢，相邻的房子不抢，
 * 而这道题是每个房子都刷，相邻的房子不能刷同一种颜色。
 * 而Paint Fence那道题主要考察我们有多少种刷法，这几道题很类似，但都不一样，需要我们分别区分。
 *
 * 但是它们的解题思想都一样，需要用动态规划Dynamic Programming来做，
 * 这道题我们需要维护一个二维的动态数组dp，其中dp[i][j]表示刷到第 i+1 房子用颜色 j 的最小花费，递推式为:
 *  dp[i][j] = dp[i][j] + Math.min(dp[i - 1][(j + 1) % 3], dp[i - 1][(j + 2) % 3])；
 * 这个很好理解，如果当前的房子要用红色刷，那么上一个房子只能用绿色或蓝色来刷，
 * 那么我们要求刷到当前房子，且当前房子用红色刷的最小花费就
 * 等于当前房子用红色刷的钱加上刷到上一个房子用绿色和刷到上一个房子用蓝色的较小值，
 * 这样当我们算到最后一个房子时，我们只要取出三个累计花费的最小值即可.
 *
 * 因为我们可以直接利用 costs 数组原有的空间，这样我们便可以节省 O(n) 的空间复杂度。
 * 下面提供两种写法：
 * 第一种十分直接明了；
 * 第二种与第一种并没有什么差异，只是利用了一个 循环 和 取余 的操作对代码进行了一个简化。
 * 二者的时间复杂度均为 O(n)
 */

// Method 1
public class Solution {
    /*
     * @param costs: n x 3 cost matrix
     * @return: An integer, the minimum cost to paint all houses
     */
    public int minCost(int[][] costs) {
        if (costs == null || costs.length == 0) {
            return 0;
        }

        int len = costs.length;
        for (int i = 1; i < len; i++) {
            costs[i][0] += Math.min(costs[i - 1][1], costs[i - 1][2]);
            costs[i][1] += Math.min(costs[i - 1][0], costs[i - 1][2]);
            costs[i][2] += Math.min(costs[i - 1][0], costs[i - 1][1]);
        }

        return Math.min(costs[len - 1][0],
                Math.min(costs[len - 1][1], costs[len - 1][2]));
    }
}

// Method 2
public class Solution {
    /*
     * @param costs: n x 3 cost matrix
     * @return: An integer, the minimum cost to paint all houses
     */
    public int minCost(int[][] costs) {
        if (costs == null || costs.length == 0) {
            return 0;
        }

        int len = costs.length;
        for (int i = 1; i < len; i++) {
            for (int j = 0; j < 3; j++) {
                costs[i][j] += Math.min(costs[i - 1][(j + 1) % 3],
                        costs[i - 1][(j + 2) % 3]);
            }
        }

        return Math.min(costs[len - 1][0],
                Math.min(costs[len - 1][1], costs[len - 1][2]));
    }
}