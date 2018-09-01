/*
Description
There is a non acyclic connected graph. Each edge is described by two vertices x[i] and y[i],
and the length of each edge is described by d[i].
Find a point p such that the sum of distances from point p to other points is the smallest.
If there is more than one such point p, return the smallest number.
2 <= n, d[i] <= 10^5
1 <= x[i], y[i] <= n

Example
Given x = [1], y = [2], d = [3], return 1.
Explanation:
The distance from other points to 1 is 3, the distance from other points to 2 is 3, and the number of 1 is smaller.

Given x = [1,2,2], y = [2,3,4], d = [1,1,1], return 2.
Explanation:
The distance from other points to 1 is 5, the distance from other points to 2 is 3, the distance from oth
 */

public class Solution {
    class Pair {
        public int first;
        public int second;
        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }
    long ans;
    int idx;


    /**
     * @param x: The end points set of edges
     * @param y: The end points set of edges
     * @param d: The length of edges
     * @return: Return the index of the fermat point
     */
    public int getFermatPoint(int[] x, int[] y, int[] d) {
        // Write your code here
        int n = x.length + 1;
        List<List<Pair>> g = new ArrayList<List<Pair>>();
        for(int i = 0; i <= n; i++) {
            g.add(new ArrayList<Pair>());
        }
        for (int i = 0; i < x.length; i++) {
            g.get(x[i]).add(new Pair(y[i], d[i]));
            g.get(y[i]).add(new Pair(x[i], d[i]));
        }
        int [] np = new int[n + 1];
        long [] dp = new long[n + 1];
        dfs1(1, 0, g, np, dp);
        ans = dp[1];
        idx = 1;
        dfs2(1, 0, dp[1], g, np, dp, n);
        return idx;
    }

    void dfs1(int x, int f, List<List<Pair>> g, int[] np, long[] dp) {
        np[x] = 1;
        dp[x] = 0;
        for (int i = 0; i < g.get(x).size(); i++) {
            int y = g.get(x).get(i).first;
            if (y == f) {
                continue;
            }
            dfs1(y, x, g, np, dp);
            np[x] += np[y];
            dp[x] += dp[y] + (long)g.get(x).get(i).second * np[y];
        }
    }
    void dfs2(int x, int f, long sum, List<List<Pair>> g, int[] np, long[] dp, int n) {
        for (int i = 0; i < g.get(x).size(); i++) {
            int y = g.get(x).get(i).first;
            if (y == f) {
                continue;
            }
            long nextSum = dp[y] + (sum - dp[y] - (long)np[y] * g.get(x).get(i).second) + (long)(n - np[y]) * g.get(x).get(i).second;
            if (nextSum < ans || (nextSum == ans && x < idx)) {
                ans = nextSum;
                idx = y;
            }
            dfs2(y, x, nextSum, g, np, dp, n);
        }
    }

}