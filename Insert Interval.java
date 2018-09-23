/*
Description
Given a non-overlapping interval list which is sorted by start point.
Insert a new interval into it, make sure the list is still in order and non-overlapping (merge intervals if necessary).

Example
Insert (2, 5) into [(1,2), (5,9)], we get [(1,9)].
Insert (3, 4) into [(1,2), (5,9)], we get [(1,2), (3,4), (5,9)].
 */

/**
 * Approach: Greedy
 * 属于 Merge Intervals 的变形题，比较简单
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * Merge Intervals:
 *  https://github.com/cherryljr/LintCode/blob/master/Merge%20Intervals.java
 */

/**
 * Definition of Interval:
 * public classs Interval {
 *     int start, end;
 *     Interval(int start, int end) {
 *         this.start = start;
 *         this.end = end;
 *     }
 * }
 */

public class Solution {
    /**
     * @param intervals: Sorted interval list.
     * @param newInterval: new interval.
     * @return: A new interval list.
     */
    public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        // 首先按照原来的排列规则（按照start大小排序）将 newInterval 插入到 intervals 中
        int index = 0;
        while (index < intervals.size() && intervals.get(index).start < newInterval.start) {
            index++;
        }
        intervals.add(index, newInterval);

        List<Interval> rst = new ArrayList<>();
        // 然后按照 Merge Intervals 中的方法进行区间合并即可。
        Interval pre = null;
        for (Interval interval : intervals) {
            if (pre == null || pre.end < interval.start) {
                rst.add(interval);
                pre = interval;
            } else {
                pre.end = Math.max(pre.end, interval.end);
            }
        }
        return rst;
    }
}