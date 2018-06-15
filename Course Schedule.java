/*
There are a total of n courses you have to take, labeled from 0 to n-1.
Some courses may have prerequisites, for example to take course 0 you have to first take course 1,
which is expressed as a pair: [0,1]

Given the total number of courses and a list of prerequisite pairs, is it possible for you to finish all courses?

Example 1:
Input: 2, [[1,0]]
Output: true
Explanation: There are a total of 2 courses to take.
             To take course 1 you should have finished course 0. So it is possible.
Example 2:
Input: 2, [[1,0],[0,1]]
Output: false
Explanation: There are a total of 2 courses to take.
             To take course 1 you should have finished course 0, and to take course 0 you should
             also have finished course 1. So it is impossible.

Note:
The input prerequisites is a graph represented by a list of edges, not adjacency matrices. Read more about how a graph is represented.
You may assume that there are no duplicate edges in the input prerequisites.
 */

/**
 * Approach 1: Topological Sort (Based on BFS)
 * 与 Directed Graph Loop 基本相同。
 * 实质为：判断该有向图中是否存在环。
 * 当存在环时，说明有课程互相依赖，如题中的 Example 2.
 * 此时无法选择到全部课程。
 * 而有向图的判断使用 拓扑排序 即可。
 *
 * 因为我们已经明确知道课程 label 的范围在 0~n-1 中。
 * 所以可以直接利用 数组 和 List 替代原本模板中 HashMap 的实现。
 * 即使用 邻接矩阵 和 数组 替代 HashMap 来建立 graph 和 indegreeMap.
 * 该做法可以大幅提升运行效率。（HashMap的常数项还是不小的，并且我还使用了 lambda expression）
 * 同时因为我们并不需要拓普排序后的具体结果，所以不需要一个list来存储它们，只需要使用一个 count 用于计数即可。
 * 效果为从 94ms 提升到了 10ms
 *
 * Directed Graph Loop:
 *  https://github.com/cherryljr/LintCode/blob/master/Directed%20Graph%20Loop.java
 */
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        int[] indegree = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        // Build the graph and indegreeMap
        for (int[] edge : prerequisites) {
            int u = edge[1], v = edge[0];
            graph.get(u).add(v);
            indegree[v]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        int count = 0;
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
                count++;
            }
        }
        if (queue.isEmpty()) {
            return false;
        }

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (int neigh : graph.get(curr)) {
                if (--indegree[neigh] == 0) {
                    queue.offer(neigh);
                    count++;
                }
            }
        }
        return count == numCourses;
    }
}

/**
 * Approach 2: Topological Sort (Based on DFS)
 * 采用 DFS 的模板来实现。
 * 与 Approach 1 相同，仍然使用 数组/List 来替代 HashMap 实现功能，从而进行加速。
 * 时间稍微比 BFS 要快一些。
 */
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        // Build the graph
        for (int[] edge : prerequisites) {
            graph.get(edge[1]).add(edge[0]);
        }

        // States: 0 == unknown, -1 == visiting, 1 == visited
        byte[] visited = new byte[numCourses];
        for (int i = 0; i < numCourses; i++) {
            // if can't be topological sorted successfully
            // means it has a cycle in the graph, so we can't choose all courses
            if (!dfs(graph, i, visited)) {
                return false;
            }
        }
        return true;
    }

    // Return the graph can be topological sorted successfully or not
    private boolean dfs(List<List<Integer>> graph, int curr, byte[] visited) {
        // if the curr node is visiting
        if (visited[curr] == -1) {
            return false;
        }
        // if the curr node is visited
        if (visited[curr] == 1) {
            return true;
        }

        visited[curr] = -1;
        for (int neigh : graph.get(curr)) {
            if (!dfs(graph, neigh, visited)) {
                return false;
            }
        }
        visited[curr] = 1;
        return true;
    }
}