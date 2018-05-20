/*
Description
Assume you are an awesome parent and want to give your children some cookies.
But, you should give each child at most one cookie.
Each child i has a greed factor gi, which is the minimum size of a cookie that the child will be content with;
and each cookie j has a size sj. If sj >= gi, we can assign the cookie j to the child i, and the child i will be content.
Your goal is to maximize the number of your content children and output the maximum number.

1.You may assume the greed factor is always positive.
2.You cannot assign more than one cookie to one child.

Example
Example 1:
Input: [1,2,3], [1,1]
Output: 1

Explanation: You have 3 children and 2 cookies. The greed factors of 3 children are 1, 2, 3.
And even though you have 2 cookies, since their size is both 1, you could only make the child whose greed factor is 1 content.
You need to output 1.

Example 2:
Input: [1,2], [1,2,3]
Output: 2

Explanation: You have 2 children and 3 cookies. The greed factors of 2 children are 1, 2.
You have 3 cookies and their sizes are big enough to gratify all of the children,
You need to output 2.
 */

/**
 * Approach: Greedy
 * 很明显的贪心做法，只有当 饼干的值 >= 孩子的需求值 时，孩子才会得到满足。
 * 要求最多可以满足多少个孩子。因此我们应该将满足孩子需求值的值最小的饼干发给该孩子。
 * 因此我们只需要将 孩子的需求 和 饼干的值 进行排序，然后按照从小到大的顺序发，
 * 然后数可以满足多少个孩子即可。
 *
 * 时间复杂度：O(nlogn)
 *
 * 这道题目与 滴滴_餐馆 的思想相同（当然这道题目简单多了）
 * https://github.com/cherryljr/NowCoder/blob/master/%E6%BB%B4%E6%BB%B4_%E9%A4%90%E9%A6%86.java
 */
public class Solution {
    /**
     * @param g: children's greed factor
     * @param s: cookie's size
     * @return: the maximum number
     */
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int gIndex = 0, sIndex = 0;
        for (; gIndex < g.length && sIndex < s.length; sIndex++) {
            if (g[gIndex] <= s[sIndex]) {
                gIndex++;
            }
        }
        return gIndex;
    }
}