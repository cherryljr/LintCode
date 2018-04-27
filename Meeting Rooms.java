/*
Description
Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei),
determine if a person could attend all meetings.

Example
Given intervals = [[0,30],[5,10],[15,20]], return false.

Tags
Sort Facebook
 */

/**
 * Approach: Scan Line (Sweep Line)
 * Reference:
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
     * @return: if a person could attend all meetings
     */
    public boolean canAttendMeetings(List<Interval> intervals) {
        List<TimePoint> list = new LinkedList<>();
        for (Interval interval :intervals) {
            list.add(new TimePoint(interval.start, 1));
            list.add(new TimePoint(interval.end, -1));
        }
        Collections.sort(list);

        int count = 0;
        for (TimePoint p : list) {
            count += p.flag;
            if (count == 2) {
                return false;
            }
        }
        return true;
    }

    class TimePoint implements Comparable<TimePoint> {
        int time, flag;

        TimePoint(int time, int flag) {
            this.time = time;
            this.flag = flag;
        }

        // Attention: 当 两个时间点相同时，需要将 结束 放在 开始之前
        // 因为存在：当一个会议结束时，另外一个会议正好开始的情况
        // （此时我们认为是能够来得及参加的）
        public int compareTo(TimePoint p) {
            return this.time - p.time == 0
                    ? this.flag - p.flag : this.time - p.time;
        }
    }
}