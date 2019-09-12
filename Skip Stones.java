/*
Description
There are n stones between the starting point and the end point. The distance between the starting point and the ith (i starts from 0) stone is d[i].
And the distance between the starting point and end point is target. From the starting point, we can only jump to the adjacent stone until the end point.

Now you can remove at most m stones. Return the maximum value of the shortest jump distance in your jumping from start point to end point.
    0≤m≤n≤50,000
    1≤target≤1,000,000,000
These stones are given in order from small to large distances from the starting point, and no two stones will appear in the same place.

Example
Example 1:
Input: n = 5, m = 2, target = 25, d = [2,11,14,17,21]
Output: 4
Explanation: Remove the first stone and the third stone. Then the jump path is :
  1. 0 -> 11  11
  2. 11 -> 17 6
  3. 17 -> 21 4
  4. 21 -> 25 4

Example 2:
Input: n = 0, m = 0, target = 10, d = []
Output: 10
Explanation: No stones and no need to remove any stone. Just jump from starting point (0) to end point (10).
 */

/**
 * Approach: Binary Search
 * 这道问题与 LeetCode 上的 Capacity To Ship Packages Within D Days 很类似。
 * 根据数据规模可得，n的规模在50,000级别，target在1,000,000,000级别。
 * 如果采用 O(n^2) 的算法必定超时，因此可以采用 O(nlog(target)) 的算法，至此，二分就非常明显了。
 * 通过不断地去二分 target 大小，然后判断 mid 是否满足条件，直到找到符合条件的最大 gap。
 *
 * 时间复杂度：O(nlogt)
 * 空间复杂度：O(1)
 *
 * Reference:
 *  https://github.com/cherryljr/LeetCode/blob/master/Capacity%20To%20Ship%20Packages%20Within%20D%20Days.java
 */
public class Solution {
    /**
     * @param n: The total number of stones.
     * @param m: The total number of stones you can remove.
     * @param target: The distance from the end to the starting point.
     * @param d: The array that the distance from the i rock to the starting point is d[i].
     * @return: Return the maximum value of the shortest jump distance.
     */
    public int getDistance(int n, int m, int target, List<Integer> d) {
        d.add(0, 0);
        d.add(target);
        int left = 0, right = target;
        while (left < right) {
            int mid = left + (right - left + 1 >> 1);
            if (isValid(m, mid, d)) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    private boolean isValid(int m, int limit, List<Integer> list) {
        int count = 0, lastPos = 0;
        for (int i = 1; i < list.size(); ++i) {
            if (list.get(i) - lastPos < limit) {
                count++;
            } else {
                lastPos = list.get(i);
            }
        }
        return count <= m;
    }
}