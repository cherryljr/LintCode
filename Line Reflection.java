/*
Description
Given n points on a 2D plane, find if there is such a line parallel to y-axis that reflect the given points.

Example
Given points = [[1,1],[-1,1]], return true.
Given points = [[1,1],[-1,-1]], return false.

Challenge
Could you do better than O(n2)?
 */

/**
 * Approach: HashMap (Similar to Two Sum)
 * 既然所有点都应该只有一个 y 对称轴，那么我们可以把这个对称轴设定为 (min + max) / 2.
 * 之所以这样设定，是因为我们认定 max 的对称点一定是 min.
 * 假设不是，那么 max 的对称点会比 min 要大，那么根据对称轴的性质，小于 max 的点对称下来必然应该在 max 对称点的右边，
 * 也就是说，我们找不到一个对称点会比 max 的对称点小，所以 max 的对称点必然是 min.
 * 这样，我们其实这道题就变成了类似 Two Sum:
 * 固定一个点，看另一个点在不在对应对称点的 set 里面即可。如果一个值是 a，另一个值应该是 max + min - a.
 * 并且这两个对称点的 y轴 坐标应该相等才对。
 *
 * 时间复杂度：O(n)
 */
public class Solution {
    /**
     * @param points: n points on a 2D plane
     * @return: if there is such a line parallel to y-axis that reflect the given points
     */
    public boolean isReflected(int[][] points) {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int[] point : points) {
            if (!map.containsKey(point[0])) {
                map.put(point[0], new HashSet<>());
            }
            map.get(point[0]).add(point[1]);
            min = Math.min(min, point[0]);
            max = Math.max(max, point[0]);
        }

        for (int[] point : points) {
            int symmetryPoint = min + max - point[0];
            if (!map.containsKey(symmetryPoint) || !map.get(symmetryPoint).contains(point[1])) {
                return false;
            }
        }
        return true;
    }
}