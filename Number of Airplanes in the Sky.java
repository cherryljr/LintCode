/*
Description
Given an interval list which are flying and landing time of the flight.
How many airplanes are on the sky at most?

Notice
If landing and flying happens at the same time, we consider landing should happen at first.

Example
For interval list
[
  [1,10],
  [2,3],
  [5,8],
  [4,7]
]
Return 3

Tags
Array LintCode Copyright Interval
 */

/**
 * Approach 1: HashMap (Memory Limit Exceeded)
 * 对于航班起飞降落时间，直观的想法是，把一段段飞行时间看成线段，将它们都投射到时间坐标上，并进行叠加，
 * 叠加值最高 的 时间坐标点 就是 空中飞机最多的时候。
 * 那么用什么来表示时间坐标呢？
 * 可以利用 HashMap，记录每一个时间段里的每一个飞行时间点，这样可以方便累加；
 * （更简单的，可以直接用一个Array，因为24小时的客观限制，int[24]。
 * 不过最好跟面试官明确一下时间坐标的可能范围。
 * 比如，是否会出现跨天的时间段，[18, 6]，也就是18:00PM - 6:00AM +1，如果有这种情况，则需要考虑进行适当的处理。
 * 不过根据OJ给出的test cases，只会出现 end>start 的情况，并且不受24小时的时间限制，因此使用HashMap更为合适）
 * 
 * PS.听说在更新了 test case 之后内存爆了... So sad...
 */

/**
 * Definition of Interval:
 * public classs Interval {
 *     int start, end;
 *     Interval(int start, int end) {
 *         this.start = start;
 *         this.end = end;
 *     }
 */
public class Solution {
    /*
     * @param airplanes: An interval array
     * @return: Count of airplanes are in the sky.
     */
    public int countOfAirplanes(List<Interval> airplanes) {
        if (airplanes == null || airplanes.size() == 0) {
            return 0;
        }

        int max = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (Interval flight : airplanes) {
            int start = flight.start;
            int end = flight.end;
            for (int i = start; i < end; i++) {
                map.put(i, map.getOrDefault(i, 0) + 1);
                max = Math.max(max, map.get(i));
            }
        }

        return max;
    }
}

/**
 * Approach 2: Scan Line
 * 形象点来说就是首先将飞机的 飞行时间段 看成一条条 平行于x轴的线段，
 * 然后我们用一条 垂直于x轴 的直线从头到尾去扫一遍，看在哪些时间段与飞机的飞行线段的交点最多，
 * 那些时间段就是 飞机最多的时候。交点个数就是 天空中飞机最多的架数。
 * 这就是所谓的 扫描线。
 *
 * 具体实现方法：
 * 为了实现 扫描线，我们首先需要根据给定的区间信息，建立一个 TimePoint[].而一段区间将会产生 两个 我们需要的时间点。
 * 我们知道，只有在 起飞/降落 的这个时间点上，天空上飞机的数目才会发生变化（扫描线的交点个数会发生变化）。
 * 因此我们的 各个时间点 需要包含的信息有：
 *  时刻（相当于X轴的坐标） 和 在该时间点上发生的动作 (flag) 是 起飞 还是 降落。
 * 然后我们对各个 TimePoint 按照 时间顺序 进行排序（如果时间相同，那么我们选择降落动作发生在前面即可）。
 * 这是因为存在一种情况：在一个时间点上，同时有一台飞机要降落，而另一台飞机要起飞。因此我们这边的做法是先让一台降落。
 * 然后使用 扫描线 从左往右 对排序数组进行扫描，也就是相当于在时间线上从前向后顺序移动，
 * 遇到 起飞点 就+1，遇到 降落点 就-1，记录其中的 最大值max 即可。
 * 在 起飞/降落 的表示上，我们直接使用了一个 int 类型，起飞 = 1，降落 = -1。
 * 不仅能够起到标识（flag）的作用，也能直接拿过来计算。
 * 在表示具有一定特点的参数时，均可以采用这个技巧如 Building Outline 中的大楼高度 height.
 *
 * 扫描线问题共同点：一个区间，告诉你开始时间和结束时间。
 * 类似的问题还有：火车 => 需要多少个轨道； 公司 => 需要多少间会议室。都是同一类问题的马甲。
 * 时间交集：https://github.com/cherryljr/LintCode/blob/master/Time%20Intersection.java
 */
public class Solution {
    class TimePoint implements Comparable<TimePoint> {
        int time;
        // 用于标志飞机 起飞/降落；如果是起飞为1，降落则为-1
        int flag;

        TimePoint(int time, int flag) {
            this.time = time;
            this.flag = flag;
        }

        @Override
        public int compareTo(TimePoint point) {
            if (this.time == point.time) {
                // 如果同一个时间点上有 起飞/降落 操作，先降落
                return this.flag - point.flag;
            } else {
                return this.time - point.time;
            }
        }
    }

    /*
     * @param airplanes: An interval array
     * @return: Count of airplanes are in the sky.
     */
    public int countOfAirplanes(List<Interval> airplanes) {
        if (airplanes == null || airplanes.size() == 0) {
            return 0;
        }

        List<TimePoint> timePoints = new ArrayList<>();
        for (Interval flight : airplanes) {
            // 一段区间将会产生两个 point
            timePoints.add(new TimePoint(flight.start, 1));
            timePoints.add(new TimePoint(flight.end, -1));
        }
        // 将飞机的时间点进行排序，为之后的扫描线扫描做好准备
        Collections.sort(timePoints);

        int max = 0, sum = 0;
        for (TimePoint timePoint : timePoints) {
            sum += timePoint.flag;
            max = Math.max(max, sum);
        }
        return max;
    }
}
