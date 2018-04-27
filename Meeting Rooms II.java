/*
Description
Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei),
find the minimum number of conference rooms required.

Example
Given intervals = [(0,30),(5,10),(15,20)], return 2.

Tags
Sort Greedy Facebook Snapchat Interval Heap Google
 */

/**
 * Approach: Scan Line (Sweep Line)
 * 与 Number of Airplanes in the Sky 如出一辙...换个问法罢了
 * https://github.com/cherryljr/LintCode/blob/master/Number%20of%20Airplanes%20in%20the%20Sky.java
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
     * @param intervals: an array of meeting time intervals
     * @return: the minimum number of conference rooms required
     */
    public int minMeetingRooms(List<Interval> intervals) {
        List<TimePoint> list = new LinkedList<>();
        for (Interval interval :intervals) {
            list.add(new TimePoint(interval.start, 1));
            list.add(new TimePoint(interval.end, -1));
        }
        Collections.sort(list);

        int rst = 0;
        int count = 0;
        for (TimePoint p : list) {
            count += p.flag;
            rst = Math.max(rst, count);
        }
        return rst;
    }

    class TimePoint implements Comparable<TimePoint> {
        int time, flag;

        TimePoint(int time, int flag) {
            this.time = time;
            this.flag = flag;
        }

        // Attention: 当 两个时间点相同时，需要将 结束 放在 开始之前
        // 因为存在：当一个会议结束时，另外一个会议正好开始的情况
        // （此时我们认为是不需要安排另外会议室的）
        public int compareTo(TimePoint p) {
            return this.time - p.time == 0
                    ? this.flag - p.flag : this.time - p.time;
        }
    }
}