/*
You are given a string s, and an array of pairs of indices in the string pairs where pairs[i] = [a, b] indicates 2 indices(0-indexed) of the string.
You can swap the characters at any pair of indices in the given pairs any number of times.
Return the lexicographically smallest string that s can be changed to after using the swaps.

Example 1:
Input: s = "dcab", pairs = [[0,3],[1,2]]
Output: "bacd"
Explaination:
Swap s[0] and s[3], s = "bcad"
Swap s[1] and s[2], s = "bacd"

Example 2:
Input: s = "dcab", pairs = [[0,3],[1,2],[0,2]]
Output: "abcd"
Explaination:
Swap s[0] and s[3], s = "bcad"
Swap s[0] and s[2], s = "acbd"
Swap s[1] and s[2], s = "abcd"

Example 3:
Input: s = "cba", pairs = [[0,1],[1,2]]
Output: "abc"
Explaination:
Swap s[0] and s[1], s = "bca"
Swap s[1] and s[2], s = "bac"
Swap s[0] and s[1], s = "abc"

Constraints:
    1. 1 <= s.length <= 10^5
    2. 0 <= pairs.length <= 10^5
    3. 0 <= pairs[i][0], pairs[i][1] < s.length
    4. s only contains lower case English letters.
 */

/**
 * Approach 1: DFS + Sort
 * Time Complexity: O(nlogn)
 * Space Complexity: O(n)
 */
class Solution {
    public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (List<Integer> pair : pairs) {
            graph.computeIfAbsent(pair.get(0), x -> new ArrayList<>()).add(pair.get(1));
            graph.computeIfAbsent(pair.get(1), x -> new ArrayList<>()).add(pair.get(0));
        }

        boolean[] visited = new boolean[s.length()];
        char[] chars = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            if (visited[i]) continue;
            List<Character> charList = new ArrayList<>();
            List<Integer> indexList = new ArrayList<>();
            dfs(s, i, graph, charList, indexList, visited);
            Collections.sort(charList);
            Collections.sort(indexList);
            for (int j = 0; j < indexList.size(); j++) {
                chars[indexList.get(j)] = charList.get(j);
            }
        }
        return String.valueOf(chars);
    }

    // 通过DFS获取同一个联通分量的字符和对应的index
    private void dfs(String s, int index,  Map<Integer, List<Integer>> graph, List<Character> charList, List<Integer> indexList, boolean[] visited) {
        if (visited[index]) return;
        visited[index] = true;
        charList.add(s.charAt(index));
        indexList.add(index);
        if (graph.containsKey(index)) {
            for (Integer neigh : graph.get(index)) {
                if (!visited[neigh]) {
                    dfs(s, neigh, graph, charList, indexList, visited);
                }
            }
        }
    }
}

/**
 * Approach 2: Union Find + PriorityQueue
 * Starting there are N separate items.
 * If we union the swap pairs, we end up with a set of disjoint indexes representing separate graphs.
 * For every character index, find the graph the character belongs to parent & find the smallest permutation for every graph (Using PriorityQueue).
 *  1. For each the given pairs, create connected groups using union-find;
 *  2. For each character in s, create mapping from root'index -> a list of candidate char.
 *  Since we want to use the smallest one every time we pick from them, use PriorityQueue.
 *  3. Finally, for each index, choose the first char in the associated pq and append into result.
 *
 * Time Complexity: O(nlogn)
 * Space Complexity: O(n)
 */
class Solution {
    public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
        UnionFind uf = new UnionFind(s.length());
        for (List<Integer> pair : pairs) {
            uf.union(pair.get(0), pair.get(1));
        }

        char[] chars = s.toCharArray();
        // Key:root的index, value:属于该节点下所有字符的 PriorityQueue 集合
        Map<Integer, PriorityQueue<Character>> map = new HashMap<>();
        for (int i = 0; i < chars.length; i++) {
            map.computeIfAbsent(uf.find(i), x -> new PriorityQueue<>()).add(chars[i]);
        }
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            ans.append(map.get(uf.find(i)).poll());
        }
        return ans.toString();
    }

    class UnionFind {
        int[] parent;

        UnionFind(int n) {
            this.parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public int find(int index) {
            if (index != parent[index]) {
                parent[index] = find(parent[index]);
            }
            return parent[index];
        }

        public void union(int a, int b) {
            int aFather = find(a), bFather = find(b);
            if (aFather != bFather) {
                parent[bFather] = aFather;
            }
        }
    }
}
