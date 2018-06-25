/*
Description
Given a collection of intervals, find the minimum number of intervals
you need to remove to make the rest of the intervals non-overlapping.
 1.You may assume the interval's end point is always bigger than its start point.
 2.Intervals like [1,2] and [2,3] have borders "touching" but they don't overlap each other.

Example
Example 1:
Input: [ [1,2], [2,3], [3,4], [1,3] ]
Output: 1
Explanation: [1,3] can be removed and the rest of intervals are non-overlapping.

Example 2:
Input: [ [1,2], [1,2], [1,2] ]
Output: 2
Explanation: You need to remove two [1,2] to make the rest of intervals non-overlapping.

Example 3:
Input: [ [1,2], [2,3] ]
Output: 0
Explanation: You don't need to remove any of the intervals since they're already non-overlapping.
 */

/**
 * Approach: Greedy
 * 区间类问题，看到时第一反应不外乎就是两种做法：
 *  1. 扫描线，在这道题目的话用途为类似求最多需要多少个会议室。
 *  看样子并不能派上用场。
 *  2. 贪心排序。用途为类似求最多可以安排多少场会议。
 *  属于需要求解的问题的补集。
 * 因此这里选用第二种方法，直接对区间安装结束点进行排序。
 * 然后统计最多有几个不重复的区间，那么答案就是:
 *  最少需要移除的区间个数 = 总区间个数 - 最多的不重复区间个数
 *
 * 时间复杂度：O(nlogn)
 *
 * 类似问题：
 * 最多的会议场数：
 *  https://github.com/cherryljr/NowCoder/blob/master/%E6%9C%80%E5%A4%9A%E7%9A%84%E4%BC%9A%E8%AE%AE%E5%9C%BA%E6%95%B0.java
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
     * @param intervals: a collection of intervals
     * @return: the minimum number of intervals you need to remove
     */
    public int eraseOverlapIntervals(List<Interval> intervals) {
        Collections.sort(intervals, (a, b) -> a.end - b.end == 0 ?
                b.start - a.start : a.end - b.end);

        int preEnd = intervals.get(0).end;
        int nonOverlap = 1;
        for (int i = 1; i < intervals.size(); i++) {
            if (intervals.get(i).start >= preEnd) {
                preEnd = intervals.get(i).end;
                nonOverlap++;
            }
        }
        return intervals.size() - nonOverlap;
    }
}