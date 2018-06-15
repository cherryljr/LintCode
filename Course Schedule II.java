/*
There are a total of n courses you have to take, labeled from 0 to n-1.
Some courses may have prerequisites, for example to take course 0 you have to first take course 1,
which is expressed as a pair: [0,1]

Given the total number of courses and a list of prerequisite pairs,
return the ordering of courses you should take to finish all courses.

There may be multiple correct orders, you just need to return one of them.
If it is impossible to finish all courses, return an empty array.

Example 1:
Input: 2, [[1,0]]
Output: [0,1]
Explanation: There are a total of 2 courses to take. To take course 1 you should have finished
             course 0. So the correct course order is [0,1] .
Example 2:
Input: 4, [[1,0],[2,0],[3,1],[3,2]]
Output: [0,1,2,3] or [0,2,1,3]
Explanation: There are a total of 4 courses to take. To take course 3 you should have finished both
             courses 1 and 2. Both courses 1 and 2 should be taken after you finished course 0.
             So one correct course order is [0,1,2,3]. Another correct ordering is [0,2,1,3] .

Note:
The input prerequisites is a graph represented by a list of edges, not adjacency matrices. Read more about how a graph is represented.
You may assume that there are no duplicate edges in the input prerequisites.
 */

/**
 * 这道题目属于 Course Schedule 的 Fellow Up.
 * 虽然在难度上几乎没有提升（改几行代码就够了），但是作为可以很好地作为一道 模板题 来解释。
 *
 * 下面主要来谈谈 拓扑排序。
 * 拓扑排序 被用于对 有向无环图 进行排序。
 * 它常被用于 Build System 等场合，比如 Java 中有很多个包，
 * 每个包都它各自的依赖，比如 C 依赖于 A 和 B，那么在 A,B 被建立之前，C 是无法被创建的。
 * 那 IDE 如何知道这件事呢？这就使用到了 拓扑排序了。
 */

/**
 * Approach 1: Topological Sort (Based on BFS)
 * 这边主要对 拓扑排序 进行讲解。对于该题为何使用到了拓扑排序，可以参考 Course Schedule:
 *  https://github.com/cherryljr/LintCode/blob/master/Course%20Schedule.java
 *
 * 首先我们知道 有向图 的每个节点具有 出度 和 入度 两个值。
 * 而为了实现 拓扑排序 我们需要使用到 入度，这个值。
 * 当一个节点 拥有 1个 入度，代表其拥有一个依赖，也就意味着：在它的依赖完成之前，它无法被完成。（应该排在后面）
 *
 * 具体做法为：
 *  1. 首先我们可以遍历整个图的 边。
 *  将各个节点的 入度 计算出来，并保存在 List/HashMap 中。(本题中因为具有具体范围，可以直接使用 List)
 *  HashMap 虽然使用简单，泛用性广，但是效率远没有 List 高。（同时使用数组来记录入度表，实现有一定难度的话，可以用 Map）
 *  2. 然后我们遍历整个图的 节点。将 入度为0 的节点加入到 queue 和 rst 中。
 *  （最开始情况下 入度为0 的节点代表一开始就没有任何依赖，可以最先被完成）
 *  3. 然后我们利用 queue 进行 BFS，
 *  每当从队列中 poll 一个元素出来，我们就从图中移除 它 和 它对应的边。
 *  代表它已经完成，这就意味着以它为 入度 的节点的入度将会减少，并且有可能为 0.
 *  在遍历过程中，当一个节点的 入度为0 就意味着它的依赖已经被全部完成了，那么它就可以被完成，
 *  所以将它 add 到 queue 和 rst 中。
 *
 * 时间复杂度：
 *  我们总共需要遍历 3 次图（计算入度 + 得到入度为0的点 + BFS）
 *  因此总体时间复杂度为：O(V + E) => O(V^2)
 *  但是实际上图并不会这么稠密，因此我们可以认为其时间复杂度更加趋近于 O(n)
 */
class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        int[] indegree = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        // Build the graph and indegreeMap
        // 遍历图的边，计算各个节点的 入度
        for (int[] edge : prerequisites) {
            int u = edge[1], v = edge[0];
            graph.get(u).add(v);
            indegree[v]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        List<Integer> rst = new LinkedList<>();
        // 选出 入度为0 的节点，将其 add 到 queue 和 rst 中
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
                rst.add(i);
            }
        }
        if (queue.isEmpty()) {
            return new int[]{};
        }

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            // 移除 curr 节点以及对应的 边
            for (int neigh : graph.get(curr)) {
                // 如果 入度为0 就意味着它的依赖已经被全部完成了，那么它就可以被完成，
                // 所以将它 add 到 queue 和 rst 中
                if (--indegree[neigh] == 0) {
                    queue.offer(neigh);
                    rst.add(neigh);
                }
            }
        }

        // 如果 rst 中的节点个数小于 numCourses，说明还有节点未被遍历到（仍有节点入度不为0）
        // 即图中存在环
        if (rst.size() < numCourses) {
            return new int[]{};
        }
        int[] arr = new int[numCourses];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rst.get(i);
        }
        return arr;
    }
}

/**
 * Approach 2: Topological Sort (Based on DFS)
 * 拓扑排序同样也能通过 DFS 来实现。
 * 具体做法为：
 *  准备一个 visited[] 用来标记已经遍历过的节点。
 *  其有三个状态：
 *      0:表示尚未被遍历过的;  -1:表示正在被遍历的 visiting;  1:表示已经遍历过的 visited.
 *  然后我们从一个节点开始遍历，首先将其置为 -1 表示 visiting。
 *  然后通过 节点的边(neighbors) 检查相邻的其他节点，
 *  如果存在 neighbors（还未被遍历过）, 那么继续递归遍历 neighbors;
 *  如果 neighbors 中有 visiting 状态的节点，则说明存在环，无法完成拓扑排序;
 *  全部遍历后，将该节点置为 visited 表示已经遍历过，并将其加入到 list 的头部。
 *  （这是因为第一个被遍历结束的节点，在拓扑排序中是最后一个，我们也可以使用 Stack 来存储结果
 *  最后再将它们全部 pop() 出来，从而完成逆序）
 *
 * 时间复杂度为：O(V + E) => O(V^2)
 *
 * 参考资料：
 *  http://zxi.mytechroad.com/blog/graph/leetcode-210-course-schedule-ii/
 */
class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        // Build the graph
        for (int[] edge : prerequisites) {
            graph.get(edge[1]).add(edge[0]);
        }

        // States: 0 == unknown, -1 == visiting, 1 == visited
        List<Integer> rst = new LinkedList<>();
        byte[] visited = new byte[numCourses];
        for (int i = 0; i < numCourses; i++) {
            // if can't be topological sorted successfully
            // means it has a cycle in the graph, so we can't choose all courses
            if (!dfs(graph, i, visited, rst)) {
                return new int[]{};
            }
        }

        int[] arr = new int[numCourses];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rst.get(i);
        }
        return arr;
    }

    // Return the graph can be topological sorted successfully or not
    private boolean dfs(List<List<Integer>> graph, int curr, byte[] visited, List<Integer> rst) {
        // if the curr node is visiting
        if (visited[curr] == -1) {
            return false;
        }
        // if the curr node is visited
        if (visited[curr] == 1) {
            return true;
        }

        // Set it to visiting state -1
        visited[curr] = -1;
        for (int neigh : graph.get(curr)) {
            if (!dfs(graph, neigh, visited, rst)) {
                return false;
            }
        }
        // Set it to visited state 1
        visited[curr] = 1;
        // add the current node to rst list's head.
        rst.add(0, curr);
        return true;
    }
}