/*
Description
Find the number connected component in the undirected graph.
Each node in the graph contains a label and a list of its neighbors. (a connected component (or just component)
of an undirected graph is a subgraph in which any two vertices are connected to each other by paths,
and which is connected to no additional vertices in the supergraph.)
Each connected component should sort by label.

Example
Given graph:
A------B  C
 \     |  |
  \    |  |
   \   |  |
    \  |  |
      D   E
Return {A,B,D}, {C,E}. Since there are two connected component which is {A,B,D}, {C,E}
 */

/**
 * Approach: BFS
 * 这道题目描述得不好，其实就是给你一张图，求图的各个联通块。
 * 输出时各个联通块内的节点按照字符顺序进行排序。
 * 显然是对于 图遍历算法 的一个考察，这里使用 宽度优先搜索 即可轻松解决。
 *
 * 当然本题还能通过 Union Find 方法来解决，但是 并查集 写法还是相对麻烦一些的。
 * 并且 时空复杂度 还会稍微高一些。因此这边就不采用该方法了。
 * 时间复杂度: BFS O(n) + 联通区排序 O(nlogn)
 * 
 * 对 Union Find 有兴趣的可以去看下该题的 Follow Up:
 * Find the Weak Connected Component in the Directed Graph:
 * 参考资料:
 * https://www.cnblogs.com/lz87/p/7496951.html
 * http://www.cnblogs.com/Dylan-Java-NYC/p/5247187.html
 */

/**
 * Definition for Undirected graph.
 * class UndirectedGraphNode {
 *     int label;
 *     ArrayList<UndirectedGraphNode> neighbors;
 *     UndirectedGraphNode(int x) { label = x; neighbors = new ArrayList<UndirectedGraphNode>(); }
 * };
 */
public class Solution {
    public List<List<Integer>> connectedSet(ArrayList<UndirectedGraphNode> nodes) {
        List<List<Integer>> results = new ArrayList<List<Integer>>();
        if (nodes == null || nodes.size() == 0) {
            return results;
        }

        Set<UndirectedGraphNode> visited = new HashSet<UndirectedGraphNode>();
        // bfs on each node to get all the connected components
        for (UndirectedGraphNode node : nodes) {
            if (!visited.contains(node)) {
                bfs(node, visited, results);
            }
        }

        return results;
    }

    private void bfs(UndirectedGraphNode node, Set<UndirectedGraphNode> visited, List<List<Integer>> results) {
        ArrayList<Integer> comp = new ArrayList<Integer>();
        Queue<UndirectedGraphNode> queue = new LinkedList<UndirectedGraphNode>();
        queue.offer(node);
        // mark the node has been visited
        visited.add(node);
        comp.add(node.label);

        while (!queue.isEmpty()) {
            UndirectedGraphNode curr = queue.poll();
            // 开始遍历与该节点连通的节点
            for (UndirectedGraphNode neighbor : curr.neighbors) {
                if (!visited.contains(neighbor)) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                    comp.add(neighbor.label);
                }
            }
        }

        // 对当前的连通块进行排序并将其加入到 rst 中.
        Collections.sort(comp);
        results.add(comp);
    }
}