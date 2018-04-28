/*
Description
Given a collection of intervals, merge all overlapping intervals.

Example
Given intervals => merged intervals:
[                     [
  (1, 3),               (1, 6),
  (2, 6),      =>       (8, 10),
  (8, 10),              (15, 18)
  (15, 18)            ]
]

Challenge
O(n log n) time and O(1) extra space.

Tags
Sort Array LinkedIn Interval Google Facebook Microsoft Bloomberg Yelp Twitter
 */

/**
 * Approach 1: Scan Line (Sweep Line)
 * 出现 interval 区间类问题。一个区间，两个端点，求合并后的区间，想到使用 扫描线 的方法进行解决。
 * 本题用 扫描线 来解决的话，虽然从代码量来说并不是一个最佳的选择，
 * 但是说明了 扫描线 在此类问题上强大的解决能力。
 *
 * 扫描线详解：
 * https://github.com/cherryljr/LintCode/blob/master/Number%20of%20Airplanes%20in%20the%20Sky.java
 * 与本题类似的，求 大楼轮廓 问题：
 * https://github.com/cherryljr/LintCode/blob/master/Building%20Outline.java
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
     * @param intervals: interval list.
     * @return: A new interval list.
     */
    public List<Interval> merge(List<Interval> intervals) {
        List<Point> list = new ArrayList<>();
        for (Interval interval :intervals) {
            list.add(new Point(interval.start, 1));
            list.add(new Point(interval.end, -1));
        }
        Collections.sort(list);

        List<Interval> rst = new LinkedList<>();
        int pre = 0, curr = 0;
        int preIndex = 0;
        for (int i = 0; i < list.size(); i++) {
            curr += list.get(i).flag;
            // 当前面的 pre 为 0,且当前计数高度 > 0 说明找到了一段区间的起始位置
            // 因此更新 pre,并用 preIndex 记录下开始位置
            if (pre == 0 && curr > 0) {
                pre = curr;
                preIndex = list.get(i).index;
            }
            // pre != 0,并且当前计数高度 == 0 说明当前合并后的区间段已经结束了
            // 因此根据 preIndex 和 当前位置建立出一个 interval 并将其加入到 rst 中
            // 同时需要更新 pre = curr (pre = 0)
            if (pre != 0 && curr == 0) {
                rst.add(new Interval(preIndex, list.get(i).index));
                pre = curr;
            }
        }

        return rst;
    }

    class Point implements Comparable<Point> {
        int index, flag;

        Point(int index, int flag) {
            this.index = index;
            this.flag = flag;
        }

        // Attention: 当 两个点的 start 相同时，需要将 后一个点的开始 放在 前一个点的结束 之前
        // 因为我们认为这种情况下，二者是可以连接起来的
        public int compareTo(Point p) {
            return this.index - p.index == 0
                    ? p.flag - this.flag : this.index - p.index;
        }
    }
}

/**
 * Approach 2: Greedy
 * 将所有的区间以 start 作为标准，从小到大进行排序。
 * 对于第一个 interval,我们只需要将其 add 到 rst 中即可。
 * 而对于后面的 intervals,我们可以对比其前一个 preInterval.end 与当前 currInterval.start.
 * 如果 start 在前一个 interval 的 end 之后，
 * 那么说明上一个区间与当前区间无法连起来，因此我们需要加入一个新的区间了。
 * 否则，说明上一个区间与当前区间存在重合部分，
 * 所以将上一个区间的 pre.end 更新为当前区间的 curr.end
 */
public class Solution {
    /**
     * @param intervals, a collection of intervals
     * @return: A new sorted interval list.
     */
    public List<Interval> merge(List<Interval> intervals) {
        List<Interval> rst = new ArrayList<>();
        Collections.sort(intervals, (a, b) -> a.start - b.start);

        Interval pre = null;
        for (Interval curr : intervals) {
            if (pre == null || pre.end < curr.start) {
                rst.add(curr);
                pre = curr;
            } else {
                // Modify the element already in list
                pre.end = Math.max(pre.end, curr.end);
            }
        }
        return rst;
    }
}