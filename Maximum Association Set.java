/*
Description
Amazon sells books, every book has books which are strongly associated with it.
Given ListA and ListB,indicates that ListA [i] is associated with ListB [i]
which represents the book and associated books.
Output the largest set associated with each other(output in any sort).
You can assume that there is only one of the largest set.

Notice
The number of books does not exceed 5000.

Example
Given ListA = ["abc","abc","abc"], ListB = ["bcd","acd","def"], return["abc","acd","bcd","dfe"].

Explanation:
abc is associated with bcd, acd, dfe, so the largest set is the set of all books
Given ListA = ["a","b","d","e","f"], ListB = ["b","c","e","g","g"], return ["d","e","f","g"].

Explanation:
The current set are [a, b, c] and [d, e, g, f], then the largest set is [d, e, g, f]

Tags
Amazon Union Find
 */

/**
 * Approach: Union Find
 * 题目中涉及到了许多的 关系问题。
 * 即我们需要经常性地查询 一个点是否属于某一个集合 与 进行 点/集合 之间的合并。
 * 因此我们使用了并查集来解决问题。
 * 因为本题各个点的 value 为字符串，不能通过 数组 来实现。因此我们选用了 HashMap 来实现我们的 Union Find.
 * 本题的 UnionFind 这个类是直接从模板拿过来的。具体模板与代码解析可以参见：
 * https://github.com/cherryljr/LintCode/blob/master/Find%20the%20Weak%20Connected%20Component%20in%20the%20Directed%20Graph.java
 *
 * 具体解法为：
 *  首先，利用 build 方法对 unionFind 进行初始化。
 *  然后，根据 ListA 和 ListB 的对应关系，将各个 String 合并起来形成连通块。
 *  之后，遍历 ListA 和 ListB 的各个元素，找出其对应的 root(Big Brother)。
 *  利用 map 将 每个root 与 其对应说管理的node 储存起来。用于之后寻找最大的连通块。
 *  （key -> root的值(string); value -> root说管理的各个节点(Set). 这里利用了 Set 对重复的节点进行去重）
 *  最后我们只需要遍历一次 map,找出 size最大 的value即可。
 */
public class Solution {
    /**
     * @param ListA: The relation between ListB's books
     * @param ListB: The relation between ListA's books
     * @return: The answer
     */
    public List<String> maximumAssociationSet(String[] ListA, String[] ListB) {
        if (ListA == null || ListA.length == 0 || ListB == null || ListB.length == 0) {
            return new ArrayList<>();
        }

        UnionFind uf = new UnionFind();
        // Initialize the Union Find according to ListA and ListB
        uf.build(ListA);
        uf.build(ListB);
        // Union all nodes according to the relationship
        for (int i = 0; i < ListA.length; i++) {
            uf.union(ListA[i], ListB[i]);
        }

        // Using map to record all strings that belong to the same father(big brother)
        Map<String, Set<String>> map = new HashMap<>();
        for (String str : ListA) {
            String root = uf.compressedFind(str);
            if (!map.containsKey(root)) {
                map.put(root, new HashSet<>());
            }
            map.get(root).add(str);
        }
        for (String str : ListB) {
            String root = uf.compressedFind(str);
            if (!map.containsKey(root)) {
                map.put(root, new HashSet<>());
            }
            map.get(root).add(str);
        }

        // Find the maximum size set
        List<String> rst = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
            if (entry.getValue().size() > rst.size()) {
                rst = new ArrayList<>(entry.getValue());
            }
        }

        return rst;
    }

    // Union Find Template
    class UnionFind {
        Map<String, String> parent;

        UnionFind() {
            parent = new HashMap<>();
        }

        void build(String[] strs) {
            for (String str : strs) {
                parent.put(str, str);
            }
        }

        String compressedFind(String str) {
            while (!str.equals(parent.get(str))) {
                parent.put(str, parent.get(parent.get(str)));
                str = parent.get(str);
            }
            return str;
        }

        void union(String a, String b) {
            String aFather = compressedFind(a);
            String bFather = compressedFind(b);
            if (!aFather.equals(bFather)) {
                parent.put(aFather, bFather);
            }
        }
    }
}