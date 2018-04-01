/*
Description
Given a multitree of n nodes, and the node numbered from 0 to n - 1,
and the root numbered 0. Each node has a revenue,
you can add the revenue of this node when you get to it.
Each side has a cost, we will subtract the cost of this side when walking along it.
Find the maximum total (total score = total return - total cost) score from the root node to any leaf node.

Notice
x[i], y[i] represent two nodes on the ith edge, cost[i] represent the cost of ith edge,
profit[i] represent the revenue of node
numbered i.
1 <= x[i], y[i] <= 10^5
1 <= cost[i], profit[i] <= 100

Example
Given x = [0,0,0],y = [1,2,3], cost = [1,1,1], profit = [1,1,2,3]，return 3.
Route: 0→3

Given x = [0,0],y = [1,2], cost =[1,2], profit = [1,2,5]，return 4.
Route: 0→2

Tags
Airbnb
 */

/**
 * Approach: Tree DP
 * 树形DP的考察，值得注意的是树的 边 是带权值的。
 * 但是并不会增加太多的难度，我们只需要建立一个 Edge 将信息存储在里面即可。
 *
 * 根据分析，当前节点的 最高收益 是等于：
 *  最高的子节点收益 + 当前节点的收益 - 该路径所产生的花费
 * 因此我们只需要收集 子节点的信息 然后处理即可。
 * 对于如何搜集 子节点的信息，我们可以采取 递归调用 的方式。其实也就是 Divide and Conquer 的做法
 *
 * 关于 树形DP 大家可以进一步参考：
 * https://github.com/cherryljr/NowCoder/blob/master/Anniversary%20party.java
 *
 * 这道题目还可以利用 DFS 来进行解决，比较简单，就不写了。
 * 不会的可以参考：
 * http://www.jiuzhang.com/solution/the-biggest-score-on-the-tree/#tag-highlight
 */
class Solution {
    /**
     * @param x: The vertex of edge
     * @param y: The another vertex of edge
     * @param cost: The cost of edge
     * @param profit: The profit of vertex
     * @return: Return the max score
     */
    public int getMaxScore(int[] x, int[] y, int[] cost, int[] profit) {
        if (profit == null || profit.length == 0) {
            return 0;
        }

        Node[] nodes = new Node[profit.length];
        // Initialize the Node
        for (int i = 0; i < profit.length; i++) {
            nodes[i] = new Node(profit[i]);
        }
        // Build the multi tree
        for (int i = 0; i < x.length; i++) {
            // 利用 edge 记录下从点 x[i] 到 y[i] 的花费
            nodes[x[i]].edges.add(new Edge(nodes[y[i]], cost[i]));
        }

        return getMax(nodes[0]);
    }

    // Tree DP
    private int getMax(Node root) {
        int max = root.profit;
        int childMax = Integer.MIN_VALUE;
        if (root.edges.size() > 0) {
            // 如果存在 子节点 的话，求得 profit 最高的子节点信息（路径）
            for (Edge edge : root.edges) {
                childMax = Math.max(childMax, getMax(edge.node) - edge.cost);
            }
            // 当前的 profit + 递归调用得到的子节点 maxProfit 就是当前节点的 maxProfit
            max += childMax;
        }
        return max;
    }

    class Node {
        int profit;
        List<Edge> edges;

        Node(int value) {
            this.profit = value;
            edges = new ArrayList<>();
        }
    }

    class Edge {
        int cost;   // 边的权值（花费）
        Node node;

        Edge(Node node, int cost) {
            this.node = node;
            this.cost = cost;
        }
    }
}