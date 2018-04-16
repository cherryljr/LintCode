/*
Description
Given the information of a company's personnel.
The time spent by the ith person passing the message is t[i] and the list of subordinates is list[i].
When someone receives a message, he will immediately pass it on to all his subordinates.
Person numbered 0 is the CEO. Now that the CEO has posted a message,
find how much time it takes for everyone in the company to receive the message?

Notice
The number of employees is n，n <= 1000.
Everyone can have multiple subordinates but only one superior.
Time t[i] <= 10000。
-1 represent no subordinates.

Example
Given t = [1,2,3], list = [[1,2],[-1],[-1]], return 1.
Explanation:
The news was passed from the CEO, and the time passed to No. 1 and No. 2 was 1. At this time, all the people in the company received the news.

Given t = [1,2,1,4,5], list = [[1,2],[3,4],[-1],[-1],[-1]], return 3.
Explanation:
The message was passed from the CEO. The time passed to the No. 1 and No. 2 characters was 1, the time passed to the No. 3 character was 3, and the message passed through 2 to 4 was faster than passing through 1  so the time which is costed for passing to 4 was 2. Finally at the time of 3, everyone received the news.

Tags
Netflix
 */

/**
 * Approach 1: BFS
 * 因为是 多叉树/图 类问题，且求传达到所有人所需要的时间。(所有最短时间中取最长)
 * 因此解法很明显了：
 * 从 CEO 开始进行 BFS，维护到达每个点的最短时间，然后取最大值即可。
 * 属于经典的利用 BFS 进行 图的最短路径 求和问题
 */
public class Solution {
    /**
     * @param t: the time of each employee to pass a meeage
     * @param subordinate: the subordinate of each employee
     * @return: the time of the last staff recieve the message
     */
    public int deliverMessage(int[] t, int[][] subordinate) {
        // cost 用于记录从 CEO 通知到 i 所需要花费的时间
        // -1 代表还未被通知到
        int[] cost = new int[t.length + 1];
        for (int i = 1; i < t.length + 1; i++) {
            cost[i] = -1;
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        int rst = 0;
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (int i = 0; i < subordinate[curr].length; i++) {
                int sub = subordinate[curr][i];
                // 该员工存在下属，且该下属还未被通知到
                if (sub != -1 && cost[sub] == -1) {
                    cost[sub] = t[curr] + cost[curr];
                    if (cost[sub] > rst) {
                        rst = cost[sub];
                    }
                    queue.offer(sub);
                }
            }
        }

        return rst;
    }
}

/**
 * Approach 2: 带权并查集
 * 主要就是对于 权(rank) 的处理，这里的 rank[i] 代表从其 Father 将消息传递它所需要的时间.
 * 然后根据题意进行分析得出：rank[bFather] = rank[a] + cost;
 * 注意：带权并查集，往往需要处理的就是 权的定义 以及两点 权之间的关系，这个地方需要根据题意仔细分析来得出。
 * 其他方面几乎都是照搬模板的（大家可以发现与 分数调查 的代码几乎相同）
 * 唯一的区别就是在于 rank 的计算，而关于路径压缩部分的计算。
 * 因为都是求 rank[i] 与其 rank[father] 之间的差值，所以我们可以用相同的代码来解决。
 * 分数调查：
 * https://github.com/cherryljr/NowCoder/blob/master/%E5%88%86%E6%95%B0%E8%B0%83%E6%9F%A5.java
 *
 * 这里对 带权并查集权 方面的做法做个总结：
 * 首先关于定义方面，通常可以有两种用法：
 *  1. rank[i] 代表其下面含有多少个节点，即它所能够管理的信息。
 *  最经典的应用就是：用于维持 Union Find 的平衡性，使其尽量保持在一个较低的树高。
 *  那么因为其 rank[i] 含义是：i节点下面有 XXX 的这类信息。
 *  因此在 union 的时候，改变的都是 父节点 的信息。
 *  比如将 b 区域放到 a 区域的管理之下时，我们就会将 rank[aFather] += rank[bFather].
 *  代表将 bFather 管理的点数现在也归为 aFather 管理，因此 rank[aFather] 增加
 *  这类问题在 路径压缩 的时候，通常是不需要对 rank 经常处理的，因为其权值只有在 union 的时候才会发生变换。
 *  即我们会在 union 的时候对父节点的权值进行变换处理。
 *  2. rank[i] 代表 当前节点i 与 父亲节点 之间的关系（如差值等）。
 *  典型应用就是：本题 与 分数调查。
 *  同样因为其 rank[i] 含义是：i节点与其管理者父节点之间的关系。
 *  因此在 union 的时候，改变的都是 子节点 的信息，即被 union 的那个节点的信息（而父亲节点是作为一个标准，不会进行变换）。
 *  那么子节点的 rank值 具体做怎样的变换，还需要我们对题意进行分析后进行适当的操作。
 *  值得注意的是：
 *  在路径压缩的时候，我们同样需要对 rank 值进行处理，因为我们需要将被压缩路径上的 权值 加到子节点上面去。
 *  这类问题的特点是我们通常可以将 最大管理者的rank 看作为0.因为它的 rank 是不需要变换的.
 */
public class Solution {
    /**
     * @param t: the time of each employee to pass a meeage
     * @param subordinate: the subordinate of each employee
     * @return: the time of the last staff recieve the message
     */
    public int deliverMessage(int[] t, int[][] subordinate) {
        if (t == null || t.length == 0) {
            return 0;
        }
        UnionFind uf = new UnionFind(t.length);
        for (int i = 0; i < subordinate.length; i++) {
            for (int j = 0; j < subordinate[i].length; j++) {
                if (subordinate[i][j] == -1) {
                    break;
                }
                uf.union(i, subordinate[i][j], t[i]);
            }
        }

        int rst = 0;
        // 重新搜索一遍所有的点来从而实现对所有点的路径压缩，从而得到所需花费的最长时间
        for (int i = 0; i < t.length; i++) {
            uf.compressedFind(i);
            rst = Math.max(rst, uf.rank[i]);
        }
        return rst;
    }

    class UnionFind {
        int[] parent, rank;

        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public int compressedFind(int index) {
            if (index == parent[index]) {
                return index;
            } else {
                int pre = parent[index];
                parent[index] = compressedFind(parent[index]);
                rank[index] += rank[pre];
                return parent[index];
            }
        }

        public boolean union(int a, int b, int cost) {
            int aFather = compressedFind(a);
            int bFather = compressedFind(b);
            if (aFather == bFather) {
                return false;
            } else {
                parent[bFather] = aFather;
                rank[bFather] = rank[a] + cost;
            }
            return true;
        }
    }
}