/*
Description
There are now n meetings, the first i meeting is meeting[i], which contains three integers [id,t1,t2],id is the meeting ID number, 
t1 is the start time of the meeting, t2 is the end time of the meeting. In addition there are m meeting pause commands , 
and the first i command is pause[i], which contains three integers [id,t3,t4], representing a meeting with the number id , 
from t3 to t4 will be forcibly paused, and after t4 the meeting will be restarted. Now give num queries, the first i query is query[i], 
and each query contains an integer t5, which queries the number of meeting at the time point t5 is executing.
Finally, an array containing num integers need be returned. The number of index i in the array indicates the answer to the i query.

The meeting ID number of each meeting is guaranteed to be unique
The pause period in the pause command must be included in the meeting start time and end time.
There may be more than one pause command in the one meeting.
There may be overlaps in the pause periods in the multiple pauses for the same meeting.

0≤n≤1e6
0≤m≤1e6
0≤id≤1e6
0≤num≤1e6
0≤t1,t2,t3,t4,t5≤1e9

Example
Given meeting = [[1,1,10]]，pause = [[1,1,2],[1,1,4],[1,9,10],[1,6,7]，query = [1,5,8,10]，return[0,1,1,0].
Explain:
According to the pause array, we can get:
The actual time period for meeting 1 is [5,5],[8,8]
Therefore, when there are only 5 and 8 time points, there is an ongoing meeting and the result is 1 at this time.

Given meeting = [[1,1,5],[2,2,8],[3,1,4]]，pause = [[1,1,2],[1,1,4],[2,5,6],[2,7,7]，query = [1,2,3,4,5]，return[1,2,2,2,1].
Explain:
According to the pause array, we can get:
The actual time period for meeting 1 is [5,5]
The actual time period for meeting 2 is [2,4], [8,8]
The actual time period for meeting 3 is [1,4]
The number of meeting available from the top 5 points of time is easily available from the above information
 */

/**
 * Approach: Scan Line (Sweep Line) + HashMap
 */
public class Solution {
    /**
     * @param meeting: The meetings
     * @param pause: The pause time of meetings
     * @param query: The query
     * @return: Return the answer of each query
     */
    public int[] getQuery(int[][] meeting, int[][] pause, int[] query) {
        // 对 pause 数组进行排序（id从小到大，同一个id的话，开始时间早的在前）
        Arrays.sort(pause, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                if (a[0] == b[0]) {
                    if (a[1] == b[1]) {
                        return a[2] - b[2];
                    } else {
                        return a[1] - b[1];
                    }
                } else {
                    return a[0] - b[0];
                }
            }
        });

        // Merge the pause array. 对同一个会议的暂停时间段进行归并，获得每个会议的暂停时间段
        List<int[]> pauseList = new ArrayList<>();
        int m = pause.length;
        if (m != 0) {
            pauseList.add(pause[0]);
        }
        for (int i = 1; i < m; i++) {
            int[] prev = pauseList.get(pauseList.size() - 1);
            if (pause[i][0] == prev[0]) {
                if (pause[i][1] <= prev[2]) {
                    if (pause[i][2] >= prev[2]) {
                        prev[2] = pause[i][2];
                    }
                } else {
                    pauseList.add(pause[i]);
                }
            } else {
                pauseList.add(pause[i]);
            }
        }

        int len = 0;
        int n = meeting.length;
        for (int i = 0; i < meeting.length; i++) {
            len = Math.max(len, meeting[i][2]);
        }
        int[] sweepLine = new int[len + 2];
        for (int i = 0; i < n; i++) {
            sweepLine[meeting[i][1]]++;
            sweepLine[meeting[i][2] + 1]--;
        }
        for (int i = 0; i < pauseList.size(); i++) {
            int[] tmp = pauseList.get(i);
            sweepLine[tmp[1]]--;
            sweepLine[tmp[2] + 1]++;
        }

        int[] preSum = new int[len + 2];
        preSum[0] = sweepLine[0];
        for (int i = 1; i < len + 2; i++) {
            preSum[i] = preSum[i - 1] + sweepLine[i];
        }
        int num = query.length;
        int[] rst = new int[num];
        for (int i = 0; i < num; i++) {
            rst[i] = preSum[query[i]];
        }
        return rst;
    }

}