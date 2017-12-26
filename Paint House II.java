/*
Description
There are a row of n houses, each house can be painted with one of the k colors. 
The cost of painting each house with a certain color is different. 
You have to paint all the houses such that no two adjacent houses have the same color.

The cost of painting each house with a certain color is represented by a n x k cost matrix. 
For example, costs[0][0] is the cost of painting house 0 with color 0; 
costs[1][2] is the cost of painting house 1 with color 2, and so on... 
Find the minimum cost to paint all houses.

Notice
All costs are positive integers.

Example
Given n = 3, k = 3, costs = [[14,2,11],[11,14,5],[14,3,10]] return 10

house 0 is color 2, house 1 is color 3, house 2 is color 2, 2 + 5 + 3 = 10

Challenge 
Could you solve it in O(nk)?

Tags 
Dynamic Programming Facebook
*/

/**
 * Approach 1: The Same Method in Paint House
 * 这道题与 Paint House 仅仅只有一个地方不同，那就是颜料的数目由原来的 3种 变成了 k种。
 * 因此我们仍然可以按照 Paint House 中的解题方法。
 * 维护一个二维的动态数组dp，其中dp[i][j]表示刷到第 i+1 房子用颜色 j 的最小花费，递推式为:
 *  dp[i][j] += Math.min(dp[i - 1]) except color j；
 * 这样当我们算到最后一个房子时，我们只要取出最后一行的最小值即可.
 *
 * 因为我们可以直接利用 costs 数组原有的空间，这样我们便可以节省 O(nk) 的空间复杂度。
 * 因此空间复杂度为：O(1)
 * 时间复杂度为：O(n*k*k)
 */
public class Solution {
    /*
     * @param costs: n x k cost matrix
     * @return: an integer, the minimum cost to paint all houses
     */
    public int minCostII(int[][] costs) {
        if (costs == null || costs.length == 0) {
            return 0;
        }

        int len = costs.length;
        for (int i = 1; i < len; i++) {
            for (int j = 0; j < costs[i].length; j++) {
                costs[i][j] += getMin(costs[i - 1], j);
            }
        }

        return getMin(costs[len - 1], costs[0].length);
    }

    public int getMin(int[] nums, int k) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            // 相邻的房屋不能使用同一种颜料，因此需要去掉与第 k种 颜料的比较
            if (i != k) {
                min = Math.min(min, nums[i]);
            }
        }
        return min;
    }
}

/**
 * Approach 2: Optimized Method
 * 解法1 是最朴素的方式。其时间复杂度为O(n*k*k)，即三层循环，分别是
 *  ① 遍历所有的房子．
 *  ② 遍历这个房子的所有颜色方案．
 *  ③ 对于每种方案寻找上一个房子除了染这个颜色的最小代价．
 * 但是该方法的时间复杂度也可以优化到O(n*k)．
 * 在上述的循环②中，我们每遍历一种颜色都去寻找上一个房子除这个颜色之外的最小代价，这样会有很多重复的计算．
 * 因此我们可以将循环③拿到和循环②同一层来预先做好，在②中要找最小代价的时候可以在O(1)的时间得到结果．
 * 这样就将时间优化到了O(n*k)．
 *
 * 而如何预先处理能够使得循环②可以在O(1)时间得到上一栋房子除染这种颜色之外其他方案的代价呢？
 * 我们可以利用两个数组一个保存从上一栋房子左到右当前的最小值．
 * 另一个从右到左保存上一栋房子染色方案从右向左的当前最小值．
 * （代码详见：http://blog.csdn.net/qq508618087/article/details/50807874）
 *
 * 但是这样的话我们就需要额外的空间了。有没有更好的方法呢？
 * 答案是有的，我们可以在循环③中将遍历得到的 最小min1 和 次小min2 的值都记录下来就行了，
 * 这样如果当前j的颜色和最小值方案的是一个颜色，就加上次小的开销，反之，则加上最小的开销。
 * 这样优化到最后，该算法的空间复杂度为：O(1)
 * 时间复杂度为：O(n*k)
 */
public class Solution {
    /*
     * @param costs: n x k cost matrix
     * @return: an integer, the minimum cost to paint all houses
     */
    public int minCostII(int[][] costs) {
        if (costs == null || costs.length == 0) {
            return 0;
        }
        // 当前这个house涂完最小的cost
        int min1 = 0;
        //当前这个house涂完次小的cost
        int min2 = 0;
        int lastColor = -1;
        for (int i = 0; i < costs.length; i++) {
            // 选择到当前颜色时的最小cost
            int currMin1 = Integer.MAX_VALUE;
            // 选择到当前颜色时的次小cost
            int currMin2 = Integer.MAX_VALUE;
            int currColor = -1;
            for (int j = 0; j < costs[i].length; j++) {
                costs[i][j] += lastColor == j ? min2 : min1;
                if (costs[i][j] < currMin1) {
                    currMin2 = currMin1;
                    currMin1 = costs[i][j];
                    currColor = j;
                } else if (costs[i][j] < currMin2) {
                    currMin2 = costs[i][j];
                }
            }

            min1 = currMin1;
            min2 = currMin2;
            lastColor = currColor;
        }

        return min1;
    }
}