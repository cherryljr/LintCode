/*
Description
Given N buildings in a x-axis，each building is a rectangle and can be represented by a triple (start, end, height)，
where start is the start position on x-axis, end is the end position on x-axis and height is the height of the building.
Buildings may overlap if you see them from far away，find the outline of them。

An outline can be represented by a triple, (start, end, height), where start is the start position on x-axis of the outline,
end is the end position on x-axis and height is the height of the outline.

Please merge the adjacent outlines if they have the same height and make sure different outlines cant overlap on x-axis.

https://www.lintcode.com/problem/the-skyline-problem/description
Example
Example 1
Input:
[
    [1, 3, 3],
    [2, 4, 4],
    [5, 6, 1]
]
Output:
[
    [1, 2, 3],
    [2, 4, 4],
    [5, 6, 1]
]
Explanation:
The buildings are look like this in the picture. The yellow part is buildings.

Example 2
Input:
[
    [1, 4, 3],
    [6, 9, 5]
]
Output:
[
    [1, 4, 3],
    [6, 9, 5]
]
Explanation:
The buildings are look like this in the picture. The yellow part is buildings.
 */

/**
 * Approach 1: Sweep Line + maxHeap
 * 看到需要获得大楼的外围轮廓，并且题目输入给的是各栋大楼的 起止区间。
 * 我们很容易想到使用 扫描线 来进行解决。（扫描过程中取最高的 height 即可）
 * 如果对 扫描线 不清楚的，可以看一下 Number of Airplanes in the Sky 这个基础应用：
 * https://github.com/cherryljr/LintCode/blob/master/Number%20of%20Airplanes%20in%20the%20Sky.java
 *
 * 具体过程如下所示：
 *  1. 首先，将 buildings 各个区间中的各个端点单独出来，用于建立扫描线扫描时所需的 Point.
 *  Point 主要包含 端点的位置信息(pos) 与 该端点是起始还是末尾(flag) 以及 该端点说对应大楼的高度(height)
 *  但是因为 height 均为非负数，所以可以用 height 的正负值来代表其是大楼的 起始/结束 位置。
 *  这样能够节省一个参数的空间，并且这样我们使用一个二维数组就能够解决 Point,而不必去建立一个 Point 类。
 *  2. 将建立的 points 按照 从小到大的 顺序进行排序，以用于 Scan Line 的扫描。
 *  3. 开始进行扫描，这时候我们发现了一个问题：虽然 扫描线 能够轻易地帮我们扫描出各个区间的情况，
 *  比如 某段区间有几栋大楼（等同于飞机航线问题），但是我们还需要得到这几栋大楼的 最高高度 来作为我们的 outline.
 *  因此我们需要 在几个heights中 快速求得 最高的高度。毫无疑问，我们可以使用 maxHeap 来帮助我们解决这个问题。
 *  这样，当我们遇到一个 起始point 时,我们就将其对应的大楼高度 add 到 maxHeap 中。
 *  当遇到一个 终止point 时，我们就将对应的点从 maxHeap 中 remove 出去。
 *  这样每次 maxHeap 的 peek() 元素就是我们需要的的 最高高度（即outline).
 *
 *  时间复杂度：
 *   建立 Scan Line 所需的 points,并进行排序：O(nlogn)
 *   进行 扫描，利用 maxHeap 来获取 扫描线 当前的最高高度(outline),因为 maxHeap 的 remove() 操作为 O(n)
 *   因此总时间复杂度为：O(n^2)
 *  空间复杂度：O(n)
 *
 * 参考资料：
 *  https://briangordon.github.io/2014/08/the-skyline-problem.html
 */
public class Solution {
    /**
     * @param buildings: A list of lists of integers
     * @return: Find the outline of those buildings
     */
    public List<List<Integer>> buildingOutline(int[][] buildings) {
        List<List<Integer>> ans = new ArrayList<>();
        if (buildings == null || buildings.length == 0) {
            return ans;
        }

        // 建立 Scan Line 所需的 points,并进行排序（从小到大）
        List<int[]> points = new ArrayList<>();
        for (int[] building : buildings) {
            // 大楼区间的 起始点: +height
            points.add(new int[]{building[0], building[2]});
            // 大楼区间的 结束点: -height
            points.add(new int[]{building[1], -building[2]});
        }
        Collections.sort(points, (a, b) -> a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]);

        // 使用 maxHeap 来存储可能的大楼高度
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        // 加入 0 作为初始高度（地平线）
        maxHeap.offer(0);
        // 记录前一个状态
        int prev = 0;
        int start = points.get(0)[0];
        // 开始扫描，遍历所有的端点
        for (int[] point : points) {
            if (point[1] > 0) {
                // 如果是起始点，则将其添加到 maxHeap 中
                maxHeap.offer(point[1]);
            } else {
                // 如果是终止点，则将其从 maxHeap 中移除
                maxHeap.remove(-point[1]);
            }
            // 获取当前的最高高度
            int curr = maxHeap.isEmpty() ? 0 : maxHeap.peek();
            // 将当前的最高高度 与 上一个状态的最高高度 相比，如果高度发生变化，则记录下来，并更新上一个状态
            if (prev != curr) {
                // 跳过高度为 0 的建筑
                if (prev != 0) {
                    ans.add(new ArrayList<Integer>(Arrays.asList(start, point[0], prev)));
                }
                prev = curr;
                start = point[0];
            }
        }

        return ans;
    }
}

/**
 * Approach 2: Scan Line + TreeMap
 * 在 Approach 1 中，我们使用了 PriorityQueue 这个数据结构来存储 可能的轮廓高度。
 * 虽然它能够在 O(1) 的时间内获得 peek() 元素，但是在 remove() 操作上，它将耗费 O(n) 的时间，
 * 使得整体算法的时间复杂度上升到了 O(n^2) 的级别。
 * 对此我们可以使用 TreeMap 这个数据结构来对其进行优化，TreeMap 可以在 O(logn) 的时间复杂度内，
 * 完成对 一个节点的 remove 操作，使得我们算法的整体时间复杂度下降到了 O(nlogn) 级别。
 * TreeMap 中的 key 为该 point 所对应的大楼高度，value 为出现的次数。
 * 其他地方与 Approach 1 相同。
 *
 * 时间复杂度：O(nlogn)
 * 空间复杂度：O(n)
 */
public class Solution {
    /**
     * @param buildings: A list of lists of integers
     * @return: Find the outline of those buildings
     */
    public List<List<Integer>> buildingOutline(int[][] buildings) {
        List<List<Integer>> ans = new ArrayList<>();
        if (buildings == null || buildings.length == 0) {
            return ans;
        }

        // 建立 Scan Line 所需的 points,并进行排序（从小到大）
        List<int[]> points = new ArrayList<>();
        for (int[] building : buildings) {
            // 大楼区间的 起始点: +height
            points.add(new int[]{building[0], building[2]});
            // 大楼区间的 结束点: -height
            points.add(new int[]{building[1], -building[2]});
        }
        Collections.sort(points, (a, b) -> a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]);

        // 使用 TreeMap 来存储可能的大楼高度（按照从大到小的顺序）
        TreeMap<Integer, Integer> map = new TreeMap<>(Collections.reverseOrder());
        // 加入 0 作为初始高度（地平线）
        map.put(0, 1);
        // 记录前一个状态
        int prev = 0;
        int start = points.get(0)[0];
        // 开始扫描，遍历所有的端点
        for (int[] point : points) {
            if (point[1] > 0) {
                // 如果是起始点，则将其添加到 TreeMap 中，出现次数 +1
                map.put(point[1], map.getOrDefault(point[1], 0) + 1);
            } else {
                // 如果是终止点
                int count = map.get(-point[1]);
                if (count == 1) {
                    // 当出现次数只有 1 次时，直接 remove
                    map.remove(-point[1]);
                } else {
                    // 不止一次的话，出现次数-1
                    map.put(-point[1], count - 1);
                }
            }
            // 获取当前的最高高度
            int curr = map.firstKey();
            // 将当前的最高高度 与 上一个状态的最高高度 相比，如果高度发生变化，则记录下来，并更新上一个状态
            if (prev != curr) {
                // 跳过高度为 0 的建筑
                if (prev != 0) {
                    ans.add(new ArrayList<Integer>(Arrays.asList(start, point[0], prev)));
                }
                prev = curr;
                start = point[0];
            }
        }
        return ans;
    }
}