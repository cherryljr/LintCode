/*
Description
Given some points and a point origin in two dimensional space, find k points out of the some points which are nearest to origin.
Return these points sorted by distance, if they are same with distance, sorted by x-axis, otherwise sorted by y-axis.

Example
Given points = [[4,6],[4,7],[4,4],[2,5],[1,1]], origin = [0, 0], k = 3
return [[1,1],[2,5],[4,4]]

Tags
LinkedIn Amazon Heap
 */

 /**
 * 遇到寻找 前k个数 /  最大的k个数 / 最小的k个数，通常都是使用 PriorityQueue 来解决问题
 * 根据题目的要求使用 maxHeap 或者 minHeap.
 * 由此可见，该题是对于 优先级队列 这个数据结构的考察。
 *
 * 解题注意点：
 * 1. 因为答案需要的是各个Point的坐标，而我们的堆是根据 点之间的距离 来排序的。
 *    故我们新建了一个 Result 类以便于我们处理和获得结果。
 * 2. 因为我们只需要距离最近的前 k 个点，故PriorityQueue大小为 k 即可。
 *
 * 因此时间复杂度为：O(nlogk); 空间复杂度为: O(k)
 * Note: 对 PriorityQueue 不仅仅可以应用在求 前k个 元素的问题上，还能应用在求中值上。
 * 如：Data Stream Median 同时使用了 maxHeap 和 minHeap 来获得 Median.
 * https://github.com/cherryljr/LintCode/blob/master/Data%20Stream%20Median.java
 */

 
 /**
 * Definition for a point.
 * class Point {
 *     int x;
 *     int y;
 *     Point() { x = 0; y = 0; }
 *     Point(int a, int b) { x = a; y = b; }
 * }
 */
public class Solution {
    /*
     * @param points: a list of points
     * @param origin: a point
     * @param k: An integer
     * @return: the k closest points
     */
    class Result {
        Point point;
        float distance;

        public Result(Point point, float distance) {
            this.point = point;
            this.distance = distance;
        }
    }

    public Point[] kClosest(Point[] points, Point origin, int k) {
        if (points == null || points.length == 0) {
            return null;
        }
        Point[] rst = new Point[k];
        PriorityQueue<Result> minHeap = new PriorityQueue<>(k, (a, b) ->
            {
                if (a.distance - b.distance != 0) {
                    return (int) (a.distance - b.distance);
                } else if (a.point.x - b.point.x != 0) {
                    return a.point.x - b.point.x;
                } else {
                    return a.point.y - b.point.y;
                }
            });
        for (Point point : points) {
            float dis = getDistance(point, origin);
            minHeap.add(new Result(point, dis));
        }

        for (int i = 0; i < k; i++) {
            rst[i] = minHeap.poll().point;
        }
        return rst;
    }

    public float getDistance(Point p1, Point p2) {
        return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
    }
}