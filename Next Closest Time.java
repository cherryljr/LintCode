/*
Description
Given a time represented in the format "HH:MM", form the next closest time by reusing the current digits.
There is no limit on how many times a digit can be reused.

You may assume the given input string is always valid.
For example, "01:34", "12:09" are all valid. "1:34", "12:9" are all invalid.

Example
Given time = "19:34", return "19:39".

Explanation:
The next closest time choosing from digits 1, 9, 3, 4, is 19:39, which occurs 5 minutes later.
It is not 19:33, because this occurs 23 hours and 59 minutes later.
Given time = "23:59", return "22:22".

Explanation:
The next closest time choosing from digits 2, 3, 5, 9, is 22:22. It may be assumed that the returned tim
 */

/**
 * Approach 1: Brute Force
 * 因为一天的时间总共有 1440 分钟，而该题中最小的计时单位就是 分钟。
 * 因此我们可以采用暴力遍历的方法，每次在原来的基础上 加一分钟。
 * 如果组成的时间能够被表示并且合法，那么这就是我们需要的答案。
 * 
 * 最差情况下时间复杂度为：O(1440) => O(1)
 */
public class Solution {
    /**
     * @param time: the given time
     * @return: the next closest time
     */
    public String nextClosestTime(String time) {
        int hour = Integer.parseInt(time.substring(0, 2));
        int minute = Integer.parseInt(time.substring(3, 5));
        while (true) {
            // 每次 +1 min, 注意时间的转换即可
            if (++minute == 60) {
                minute = 0;
                hour++;
                hour %= 24;
            }
            // 注意时间格式的表示
            String currTime = String.format("%02d:%02d", hour, minute);
            boolean valid = true;
            for (int i = 0; i < currTime.length(); i++) {
                if (time.indexOf(currTime.charAt(i)) == -1) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                return currTime;
            }
        }
    }
}

/**
 * Approach 2: DFS (Backtracking)
 * 因为组成时间的总共只有 4 个数字，所以我们可以采用 DFS 的方法枚举所有方案即可。
 * 所有的方案个数为 4^4 = 256.因此时间复杂度为：O(256) => O(1)
 * 本质上来说我们可以将其看做是一个简化版的 Permutation.
 * Permutation中每个数字只能用一次，而本题中每个数字可以被无限次取。
 * 因此我们可以省略掉 visited[] 来记录每个数字。
 *
 * 由此可见，本题总体上还是较为简单的，核心函数就在与 dfs() 与 getDiff() 上。
 * 考察点更加偏向工程方向而不是算法方面。（如果求 Previous Closest Time 代码要做什么修改呢？）
 * 因为本次还涉及到了 时间上的转换 时间差计算 字符串与整数的转换 以及 字符串的格式化。
 * 对于代码能力还是有一定要求的。
 */
public class Solution {
    private static final int MOD = 1440;
    private int bestTime;

    /**
     * @param time: the given time
     * @return: the next closest time
     */
    public String nextClosestTime(String time) {
        int hour = Integer.parseInt(time.substring(0, 2));
        int minute = Integer.parseInt(time.substring(3, 5));
        // 获得开始的时间，以分钟数计量
        int beginTime = getMinutes(hour, minute);
        // 获得可用的数字
        int[] times  = new int[4];
        times[0] = time.charAt(0) - '0';
        times[1] = time.charAt(1) - '0';
        times[2] = time.charAt(3) - '0';
        times[3] = time.charAt(4) - '0';

        // 初始化结果，最差情况下相隔 24 小时，即等于开始时间。如 00:00
        bestTime = beginTime;
        dfs(beginTime, times, new ArrayList<>());
        // 按照时间格式对数据进行格式化
        return (String.format("%02d", bestTime / 60) + ":" + String.format("%02d", bestTime % 60));
    }

    private void dfs(int beginTime, int[] times, ArrayList<Integer> list) {
        if (list.size() == 4) {
            // 计算当前的小时数
            int currHour = list.get(0) * 10 + list.get(1);
            // 计算当前的分钟数
            int currMinute = list.get(2) * 10 + list.get(3);
            // 检查 DFS 得到的时间是否符合规范
            if (currHour > 23 || currMinute > 59) {
                return;
            }
            // 计算 DFS 得到的时间（以分钟数计量）
            int currTime = getMinutes(currHour, currMinute);
            if (getDiff(beginTime, currTime) < getDiff(beginTime, bestTime)) {
                bestTime = currTime;
            }
            return;
        }

        // Just Like Doing Permutation
        for (int num : times) {
            list.add(num);
            dfs(beginTime, times, list);
            list.remove(list.size() - 1);
        }
    }

    private int getDiff(int beginTime, int currTime) {
        if (beginTime == currTime) {
            return MOD;
        }
        // 计算 当前时间 与 开始时间 的时间差
        // 因为差值有可能存在负数，并且题目要求的是 之后的时间(next time)
        // 故我们应该将其当作第二天的时间来计算，所以要进行一次 (diff + MOD) % MOD 操作
        // 因此如果题目要求 Previous Closest Time 的话，直接调换 currTime 和 begin 的顺序即可。
        return ((currTime - beginTime) + MOD) % MOD;
    }

    private int getMinutes(int hour, int minute) {
        return hour * 60 + minute;
    }
}