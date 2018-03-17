/*
Descriptions
Find the number Weak Connected Component in the directed graph.
Each node in the graph contains a label and a list of its neighbors.
(a connected set of a directed graph is a subgraph in which any two vertices are connected by direct edge path.)

Notice
Sort the element in the set in increasing order

Example
Given graph:
A----->B  C
 \     |  |
  \    |  |
   \   |  |
    \  v  v
     ->D  E <- F
Return {A,B,D}, {C,E,F}. Since there are two connected component which are {A,B,D} and {C,E,F}
 */

/**
 * Approach: Union Find
 * 该题是 Find the Connected Component in the Undirected Graph 的一个 Follow Up.
 * 刚刚开始，打算仍然使用 BFS 进行解决，但是我们可以发现一个问题：
 * 这道题目中的 graph 是 有向图. 所以观察例子：
 * 我们可以看到通过 BFS， C 可以访问到 E，但是 E 是无法访问到 F 的.
 * 但是题目要求的是 弱联通 区，所以 E 是可以通过 weak connection 访问到 F 的，即：答案应该是 C,E,F 一块才对。
 *
 * 为了解决这个问题，本题使用了 并查集(Union Find) 这个数据结构。
 * 该数据结构通常被应用于：集合合并 与 判断点是否在在同一个集合中 这些情况中。
 * 本题中，我们可以用 Union Find 将各个节点 Union 起来形成一个个 弱连通区。
 * Union Find 中有几个 root节点(Big Brother) 就说明有几个 弱连通区。
 * 最后使用 Map 将属于同一个 Big Brother 管理的节点 add 到同一个 List 中，
 * 然后对该 List 进行排序即可。
 * (Union 和 Find 操作都可以进行优化，具体优化请参见代码注释)
 * 时间复杂度：将所有节点 Union 起来 O(n) + 联通区排序 O(nlogn)
 *
 * 数组 版本的模板可以参见：
 * https://github.com/cherryljr/LintCode/blob/master/Graph%20Valid%20Tree.java
 *
 * 参考资料：
 * http://blog.csdn.net/dm_vincent/article/details/7655764
 * http://blog.csdn.net/dm_vincent/article/details/7769159
 */

/**
 * Definition for Directed graph.
 * class DirectedGraphNode {
 *     int label;
 *     ArrayList<DirectedGraphNode> neighbors;
 *     DirectedGraphNode(int x) { label = x; neighbors = new ArrayList<DirectedGraphNode>(); }
 * };
 */
public class Solution {
    public List<List<Integer>> connectedSet2(ArrayList<DirectedGraphNode> nodes) {
        List<List<Integer>> rst = new ArrayList<>();
        if (nodes == null || nodes.size() == 0) {
            return rst;
        }

        // 利用 Set 保存所有节点的 label 用于建立 Union Find
        HashSet<Integer> set = new HashSet<>();
        for (DirectedGraphNode node : nodes) {
            set.add(node.label);
            for (DirectedGraphNode neigh : node.neighbors) {
                set.add(neigh.label);
            }
        }

        UnionFind uf = new UnionFind(set);
        // 遍历所有节点，将所有能够互相访问的节点通过 并查集的Union方法 合并起来
        for (DirectedGraphNode node : nodes) {
            for (DirectedGraphNode neigh : node.neighbors) {
                uf.union(node.label, neigh.label);
            }
        }

        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i : set) {
            // 寻找各个节点的 root (Big Brother)
            int rootParent = uf.compressedFind(i);
            if (!map.containsKey(rootParent)) {
                map.put(rootParent, new ArrayList<>());
            }
            // 将同一个 root (Big Brother) 下面的节点添加到同一个 list 中，包括 root 本身
            map.get(rootParent).add(i);
        }

        // 对每一个 弱连通区 中的节点进行排序
        for (List<Integer> item : map.values()) {
            Collections.sort(item);
            rst.add(item);
        }
        return rst;
    }
}

// Union Find Template (HashMap)
class UnionFind{
    // HashMap maintaining key - > value (child -> parent) relationship
    HashMap<Integer, Integer> parent;
    // HashMap maintaining key -> value (node -> node's rank) relationship
    // rank means that how many nodes in the region. (Big father is the current node)
    // Note: the define of rank could be changed according to your demands.
    HashMap<Integer, Integer> rankMap;

    UnionFind(HashSet<Integer> labels) {
        parent = new HashMap<>();
        rankMap = new HashMap<>();
        for (int label : labels) {
            // 对 Union Find 进行初始化，每个 node 的父亲都是其自身.
            parent.put(label, label);
            // 对各个node的 权重 进行初始化，初始情况下都为 1.（只包含一个node）
            rankMap.put(label, 1);
        }
    }

    /**
     * Compressed Find - 查找方法（带路径压缩优化），用于查询节点的 最顶级管理者(Big Brother)
     * 之所以要进行 路径压缩 是因为：
     * 通过这个优化可以将 Find 的时间复杂度从原来的 O(n) 降低到 O(1) (操作数需要够大).
     * 一旦第一次遍历过后，之后再次查询时就不需要再一级级地往上寻找 Big Brother，而是可以直接一步找到它。
     * 从数据结构上来看就是将原来的 链式结构 转换为了 放射状的树型结构。
     * @param i 需要搜寻的节点
     * @return 当节点的 root (Big Brother)
     */
    public int compressedFind(int i) {
        while (i != parent.get(i)) {
            // Compressed Find
            parent.put(i, parent.get(parent.get(i)));
            i = parent.get(i);
        }
        return i;
    }

    /**
     * Union - 合并方法，用于将两个节点合并起来（带权值优化）
     * 记住：合并的时候是将节点各自的 Big Brother 拿去合并，这样才不会出错（非常重要）
     * 当我们合并两棵树（区域）的时候，很可能会发生树高度的增加。
     * 为了保证树尽量维持平衡，花费的时间尽量少，
     * 我们可以通过 权值 来实现优化。
     * 具体为：将 权值低的树（区域）并入到 权值高的树（区域）中。
     * @param a 需要合并的节点 a
     * @param b 需要合并的节点 b
     */
    public void union(int a, int b) {
        int aFather = compressedFind(a); // 寻找节点 a 的 Big Brother
        int bFather = compressedFind(b); // 寻找节点 b 的 Big Brother
        // 如果两个节点的 Big Brother 不同，则将他们合并起来。
        if (aFather != bFather) {
            // 每次我们比较的时候，只会取 根节点(Big Brother) 的权值进行比较
            int aFRank = rankMap.get(aFather);
            int bFRank = rankMap.get(bFather);
            if (aFRank <= bFRank) {
                // a节点所述的区域的 根节点(Big Brother) 权值更低，
                // 因此将其 并入 b节点所属的区域
                parent.put(aFather, bFather);
                // 更新 b节点所属的区域的 根节点(Big Brother) 的权值
                rankMap.put(bFather, aFRank + bFRank);
            } else {
                // b节点所述的区域的 根节点(Big Brother) 权值更低，
                // 因此将其 并入 a节点所属的区域
                parent.put(bFather, aFather);
                // 更新 a节点所属的区域的 根节点(Big Brother) 的权值
                rankMap.put(aFather, aFRank + bFRank);
            }
        }
    }
}