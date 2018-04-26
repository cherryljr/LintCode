/*
Description
Give two users' ordered online time series, and each section records the user's login time point x and offline time point y.
Find out the time periods when both users are online at the same time, and output in ascending order.

Notice
We guarantee that the length of online time series meet 1 <= len <= 1e6.
For a user's online time series, any two of its sections do not intersect.

Example
Given seqA = [(1,2),(5,100)], seqB = [(1,6)], return [(1,2),(5,6)].
Explanation:
In these two time periods (1,2),(5,6), both users are online at the same time.

Given seqA = [(1,2),(10,15)], seqB = [(3,5),(7,9)], return [].
Explanation:
There is no time period, both users are online at the same time.

Tags
Sweep line Interval Facebook
 */

/**
 * Approach: Sweep Line (Scan Line)
 * 扫描线的经典应用...实在没啥好说的了...
 * 题目中只有 两个人。因此要求两个人 同时上线 的区间的话。
 * 我们只需要用一个 preCount 和 preIndex 来记录 count 的前一个状态即可。
 * 当 preCount = 2（两个人同时上线） 并且 count = 1 时（有一个人下线）
 * 就说明我们找到了一段 两个人同时在线 的区间即：[preIndex, currIndex]
 *
 * 对于 扫描线 不清楚的可以参考：
 * https://github.com/cherryljr/LintCode/blob/master/Number%20of%20Airplanes%20in%20the%20Sky.java
 */

/**
 * Definition of Interval:
 * public class Interval {
 *     int start, end;
 *     Interval(int start, int end) {
 *         this.start = start;
 *         this.end = end;
 *     }
 * }
 */
public class Solution {
    /**
     * @param seqA: the list of intervals
     * @param seqB: the list of intervals
     * @return: the time periods
     */
    public List<Interval> timeIntersection(List<Interval> seqA, List<Interval> seqB) {
        // Deal with the null set
        if (seqA == null || seqA.size() == 0 || seqB == null || seqB.size() == 0) {
            return new LinkedList<>();
        }

        List<Point> list = new LinkedList<>();
        for (Interval interval : seqA) {
            list.add(new Point(interval.start, 1));
            list.add(new Point(interval.end, -1));
        }
        for (Interval interval : seqB) {
            list.add(new Point(interval.start, 1));
            list.add(new Point(interval.end, -1));
        }
        // 这里虽然可以使用 Lambda 表达式来书写，
        // 但是如果要对一个类进行比较，最好实现其比较接口。
        // 1.有可能在其他地方还需要用到比较，排序等
        // 2.不使用语法糖，直接实现比较方法会更快一些
        Collections.sort(list);

        List<Interval> rst = new LinkedList<>();
        int count = 0, preCount = 0;
        int preIndex = 0;
        for (Point p : list) {
            if (p.value == 1) {
                count++;
            } else {
                count--;
            }

            if (preCount == 2 && count == 1) {
                rst.add(new Interval(preIndex, p.index));
            }
            // Update the preCount and preIndex
            preCount = count;
            preIndex = p.index;
        }

        return rst;
    }

    class Point implements Comparable<Point> {
        int index;
        int value;

        Point(int index, int value) {
            this.index = index;
            this.value = value;
        }

        @Override
        public int compareTo(Point p) {
            return this.index - p.index;
        }
    }
}











