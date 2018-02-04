/*
Description
There are n cities on an axis, numbers from 0 ~ n - 1.
John intends to do business in these n cities, He is interested in Armani's shipment.
Each city has a price for these goods prices [i].
For city x, John can buy the goods from the city numbered from x - k to x + k, and sell them to city x.
We want to know how much John can earn at most in each city?

Notice
prices.length range is [2, 100000], k <= 100000.

Example
Given prices = [1, 3, 2, 1, 5], k = 2, return [0, 2, 1, 0, 4].

Explanation：
i = 0, John can go to the city 0 ~ 2. He can not make money because the prices in city 1 and city 2 are both higher than the price in city 0, that is, ans[0] = 0;
i = 1, John can go to the city 0~3. He can buy from city 0 or city 3 to earn the largest price difference. That is, ans[1] = 2.
i = 2, John can go to the city 0~4. Obviously, he can earn the largest price difference by buying from city 3. That is, ans[2] = 1.
i = 3, John can go to the city 1~4. He can not make money cause city 3 has the lowest price. That is, ans[3] = 0.
i = 4, John can go to the city 2~4. He can earn the largest price difference by buying from city 3. That is, ans[4] = 4.
Given prices = [1, 1, 1, 1, 1], k = 1, return [0, 0, 0, 0, 0]

Explanation:
All cities are the same price, so John can not make money, that is, all ans are 0.

Tags
Segment Tree
 */
 
 /**
 * Approach 1: Brute Force (Time Limit Exceeded)
 * Traverse the array to get the maximum profit of every city.
 * Time Complexity: O(nk)
 */
public class Solution {
    /**
     * @param A: The prices [i]
     * @param k:
     * @return: The ans array
     */
    public int[] business(int[] A, int k) {
        if (A == null || A.length == 0) {
            return null;
        }

        int len = A.length;
        int[] profit = new int[len];
        for (int i = 0; i < len; i++) {
            for (int count = -k; count <= k; count++) {
                if (i + count < 0 || i + count >= len) {
                    continue;
                }
                profit[i] = Math.max(profit[i], A[i] - A[i + count]);
            }
        }

        return profit;
    }
}

/**
 * Approach 2: Segment Tree
 * 初刷时没想太多，使用的是 Approach 1 中的暴力方法，后来发现 TLE 了。
 * 于是我们需要进一步对题目进行分析，既然 O(nk) 的时间复杂度过不去，那有没有存在 O(klogn) 或者 O(nlogk) 的方法呢？
 * 根据题目的描述：我们可以发现，我们主要的时间全部花费在了 区间[x-k, x+k] 上最小值的查询上面。
 * 那么是否存在降低 区间操作 的时间复杂度的方法呢？ （由 O(k) -> O(logk)）
 *
 * 答案当然存在，那就是 线段树.
 * 得益于该种数据结构，我们可以在 O(logn) 的时间内得到 区间和/最大值/最小值/差值.
 * PS1. 除了 区间和可以利用 Prefix Sum 快速得到，其他都需要使用 O(n) 的时间。
 * 因此线段树在遇到 快速求区间最大值/最小值 时可以发挥非常大的用处。
 * PS2. 区间和一旦发生单点修改的操作时, Prefix Sum 也将达到 O(n) 的时间复杂度，
 * 最佳解法为利用 Binary Index Tree. 这里不再细讲，有兴趣的可以移步：
 * https://github.com/cherryljr/LintCode/blob/master/Interval%20Sum%20II.java
 *
 * 那么本题的解法就可以转换为：
 *  1. 首先构建一棵线段树
 *  2. 利用线段树查询 区间[x-k, x+k] 上的最小值，然后用 A[x] - minValue 得到在城市 x 所能获得的最大利润。
 * Time Complexity: O(nlogk)
 *
 * 本题主要使用到了线段树的 构建 与 查询。详细解释可以参见：
 * 线段树的构建：https://github.com/cherryljr/LintCode/blob/master/Segment%20Tree%20Build%20II.java
 * 线段树的查询：https://github.com/cherryljr/LintCode/blob/master/Segment%20Tree%20Query.java
 * 线段树的单点修改：https://github.com/cherryljr/LintCode/blob/master/Segment%20Tree%20Modify.java
 */
public class Solution {
    class SegmentTreeNode {
        int start, end;
        int val;
        SegmentTreeNode left, right;

        SegmentTreeNode(int start, int end, int val) {
            this.start = start;
            this.end = end;
            this.val = val;
            this.left = this.right = null;
        }
    }

    /**
     * @param A: The prices [i]
     * @param k:
     * @return: The ans array
     */
    public int[] business(int[] A, int k) {
        if (A == null || A.length == 0) {
            return null;
        }

        SegmentTreeNode root = build(A, 0, A.length - 1);
        int[] profit = new int[A.length];
        for (int i = 0; i < A.length; i++) {
            int left = Math.max(0, i - k);
            int right = Math.min(A.length - 1, i + k);
            profit[i] = A[i] - query(root, left, right);
        }

        return profit;
    }

    private SegmentTreeNode build(int[] A, int start, int end) {
        if (start > end) {
            return null;
        }

        SegmentTreeNode node = new SegmentTreeNode(start, end, A[start]);
        if (start == end) {
            return node;
        }
        int mid = start + (end - start) / 2;
        node.left = build(A, start, mid);
        node.right = build(A, mid + 1, end);
        if (node.left != null) {
            node.val = Math.min(node.val, node.left.val);
        }
        if (node.right != null) {
            node.val = Math.min(node.val, node.right.val);
        }

        return node;
    }

    private int query(SegmentTreeNode node, int start, int end) {
        if (start <= node.start && node.end <= end) {
            return node.val;
        }

        int rst = Integer.MAX_VALUE;
        int mid = node.start + (node.end - node.start) / 2;
        if (start <= mid) {
            rst = Math.min(rst, query(node.left, start, end));
        }
        if (end > mid) {
            rst = Math.min(rst, query(node.right, start, end));
        }

        return rst;
    }
}
