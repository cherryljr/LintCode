/*
Description
Please judge whether there is a cycle in the directed graph with n vertices and m edges.
The parameter is two int arrays. There is a directed edge from start[i] to end[i].

Notice
2 <= n <= 10^5
1 <= m <= 4*10^5
1 <= start[i], end[i] <= n

Example
Given start = [1],end = [2], return "False"。
Explanation:
There is only one edge 1->2, and do not form a cycle.

Given start = [1,2,3],end = [2,3,1], return "True".
Explanation:
There is a cycle 1->2->3->1.

Tags
Google
 */

/**
 * Approach 1: Union Find （Wrong Answer)
 * 使用 并查集 对无向图进行判环
 * 虽然题目给的是 有向图，但是 Case 似乎没有考虑到这点...
 * 竟然还让我过了...很尴尬...不知道最新的 Case 有没有包含这点
 * 关于 无向图判环 的详细解析可以参考：
 * https://github.com/cherryljr/LintCode/blob/master/Graph%20Valid%20Tree.java
 */
public class Solution {
    /**
     * @param start: The start points set
     * @param end: The end points set
     * @return: Return if the graph is cyclic
     */
    public boolean isCyclicGraph(int[] start, int[] end) {
        if (start == null || start.length <= 1) {
            return false;
        }

        // 为了建立 并查集，我们需要获取所有节点中的最大值
        // 当然最好的做法还需要获取最小值，然后依据差值来建立 Union Find
        // 这边我就偷下懒...
        int max = Integer.MIN_VALUE;
        for (int num : start) {
            max = Math.max(max, num);
        }
        for (int num : end) {
            max = Math.max(max, num);
        }

        UnionFind uf = new UnionFind(max + 1);
        for (int i = 0; i < start.length; i++) {
            if (!uf.union(start[i], end[i])) {
                return true;
            }
        }
        return false;
    }

    class UnionFind {
        int[] parent, rank;

        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
            Arrays.fill(rank, 1);
        }

        public int compressedFind(int index) {
            if (index != parent[index]) {
                parent[index] = compressedFind(parent[index]);
                return parent[index];
            }
            return index;
        }

        public boolean union(int a, int b) {
            int aFather = compressedFind(a);
            int bFather = compressedFind(b);
            // if two vertices happen to be in the same set
            // then there's a cycle
            if (aFather == bFather) {
                return false;
            } else {
                // keep balance
                if (rank[aFather] <= rank[bFather]) {
                    parent[aFather] = bFather;
                    rank[bFather] += rank[aFather];
                } else {
                    parent[bFather] = aFather;
                    rank[aFather] += rank[bFather];
                }
            }
            return true;
        }
    }
}

/**
 * Approach 2: Topological Sorting (Based on BFS)
 * 本题实质上考察的是 拓扑排序 的性质：
 *  拓扑排序 只能被应用于对 有线无环图 进行排序。
 * 因此我们可以利用这一点对该 有向图 进行拓扑排序，
 * 如果无法正常完成，则说明该图 带环。
 *
 * 这里使用了 BFS 的写法，当然我们还可以使用 DFS 的做法。
 * 具体可以参考拓扑排序详解：
 * https://github.com/cherryljr/LintCode/blob/master/Topological%20Sorting.java
 */
public class Solution {
    /**
     * @param start: The start points set
     * @param end:   The end points set
     * @return: Return if the graph is cyclic
     */
    public boolean isCyclicGraph(int[] start, int[] end) {
        int max = 0;
        for (int i = 0; i < start.length; i++) {
            max = Math.max(max, start[i]);
            max = Math.max(max, end[i]);
        }
        // 这里不再像模板里面一样使用 Map，而是直接使用 数组 来解决问题了
        int[] indegree = new int[max + 1];
        // 用于标记 0~max 范围内，哪些值是节点
        boolean[] isNode = new boolean[max + 1];
        // 初始化 graph
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < start.length; i++) {
            // Build the graph
            graph.computeIfAbsent(start[i], x -> new ArrayList<>()).add(end[i]);
            // 计算各个节点的 入度 信息，并标记为有效点
            indegree[end[i]]++;
            isNode[start[i]] = true;
            isNode[end[i]] = true;
        }

        // 将所有 入度为0 的节点，加入到 queue 中准备开始 BFS (拓扑排序)
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i <= max; i++) {
            if (isNode[i] && indegree[i] == 0) {
                queue.offer(i);
            }
        }
        // 如果所有的点 入度 都不是0，那么必然存在环，直接 return true
        if (queue.isEmpty()) {
            return true;
        }
        // 开始 BFS
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            if (graph.containsKey(curr)) {
                for (int neigh : graph.get(curr)) {
                    indegree[neigh]--;
                    // 如果因为该条边的消失，导致该邻居节点入度减为 0
                    // 说明该节点可以被完成了，故将其加入到 queue 中
                    if (indegree[neigh] == 0) {
                        queue.offer(neigh);
                    }
                }
            }
        }

        // Judge the Topological Sorting is complete well or not
        for (int i = 0; i <= max; i++) {
            // 拓扑排序完成后，即BFS遍历完成后不应该还存在 入度>=1 的节点
            // 否则说明，BFS的时候，这个节点没被遍历到，即未能正常完成拓扑排序
            if (isNode[i] && indegree[i] != 0) {
                return true;
            }
        }
        return false;
    }
}