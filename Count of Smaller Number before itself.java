/*
Description
Give you an integer array (index from 0 to n-1, where n is the size of this array, data value from 0 to 10000) .
For each element Ai in the array, count the number of element before this element Ai is smaller than it and return count number array.

Notice
We suggest you finish problem Segment Tree Build, Segment Tree Query II and Count of Smaller Number first.

Example
For array [1,2,7,8,5], return [0,1,2,3,2]

Tags
Binary Search LintCode Copyright Segment Tree
 */

/**
 * Approach 1: Segment Tree
 * 如何找到问题的切入点？
 * 对于每一个元素A[i]我们查询比它小数，转换成区间的查询就是查询在它前面的数当中有多少在区间[0, A[i] - 1]当中。
 *
 * 因此我们可以为 0-10000区间 建立线段树，并将所有区间的 count 置为0。每一个最小区间（即叶节点）的 count 代表到目前为止该数的数量。
 * 然后开始遍历数组，遇到A[i]时，去查0 ～ A[i]-1 区间的 count 即这个区间中有多少数存在，这就是比 A[i] 小的数的数量。
 * 查完后将 A[i] 区间的 count 加1即可，也就是把 A[i] 插入到线段树i的位置上。
 * 该解法可以详见：
 * http://www.jiuzhang.com/tutorial/segment-tree/168
 * 但是该解法存在着缺点：
 *  1. 我们需要事先知道题目所给出测试数据的范围
 *  2. 使用了大量的额外空间
 *
 * 对此我们可以对该解法进行一个小小的改进。
 * 首先我们遍历整个数组得到数据的最小值 minValue，然后再利用 diff[] 数组来存储输入数组中每个元素与最小值之间的差值，
 * 并记录下最大的差值 maxValue.
 * 这样我们就能把问题的切入点转换为：查询在它前面的数当中有多少元素的值与最小值差距在区间[0, diff[i]-1]当中。
 * 这样我们便能够通过建立一个范围在 [0...maxVal] 的线段树来解决区间查询的问题。
 * 然后我们开始遍历数组，当遇到 A[i] 时，去查询 [0, diff[i]-1] 区间的 count 即这个区间中有多少数存在，
 * 这就是比 A[i] 小的数的数量。然后对线段树上的 diff[i] 节点进行单点更新即可。
 * 优化之后解法的优点在于：
 * 1. 无需事先知道测试数据的范围
 * 2. 使用了相对较少的额外空间
 */
public class Solution {
    /*
     * @param A: an integer array
     * @return: A list of integers includes the index of the first number and the index of the last number
     */
    class SegmentTreeNode {
        int start, end, count;
        SegmentTreeNode left, right;

        SegmentTreeNode(int start, int end, int count) {
            this.start = start;
            this.end = end;
            this.count = count;
            left = right = null;
        }
    }
    SegmentTreeNode root;

    public List<Integer> countOfSmallerNumberII(int[] A) {
        List<Integer> rst = new ArrayList<>();
        if (A == null || A.length == 0) {
            return rst;
        }

        int minValue = Integer.MAX_VALUE;
        int maxValue = Integer.MIN_VALUE;
        int len = A.length;
        // 遍历数组获得输入数组中的最小值 minValue
        for (int i : A) {
            minValue = Math.min(minValue, i);
        }

        int[] diff = new int[len];
        // 利用数组 diff[] 储存数组中各个元素 A[i] 与最小值之间的差值
        // 并记录下最大差值 maxValue
        for (int i = 0; i < len; i++) {
            diff[i] = A[i] - minValue;
            maxValue = Math.max(maxValue, diff[i]);
        }

        // 以范围 [0...maxValue] 建立线段树,或者以查询范围建立线段树。
        // 注意:实际查询时我们的范围是 [-1...maxValue-1]
        root = buildHelper(0, maxValue);
        int count = 0;
        for(int i = 0; i < diff.length; i++) {
            // 查询当前所有差值小于 diff[i] 的数的个数
            count = query(root, 0, diff[i]-1);
            // 将 diff[i] 插入线段树中
            modify(root, diff[i], 1);
            rst.add(count);
        }
        return rst;
    }

    // Build the Segment Tree
    private SegmentTreeNode buildHelper(int start, int end) {
        // 处理异常情况
        if (start > end) {
            return null;
        }

        SegmentTreeNode root = new SegmentTreeNode(start, end, 0);
        // 当 start == end 时，说明为叶子节点，直接返回节点即可
        if (start == end) {
            return root;
        }
        // 分治
        int mid = root.start + (root.end - root.start) / 2;
        root.left = buildHelper(start, mid);
        root.right = buildHelper(mid + 1, end);
        root.count = 0;

        return root;
    }

    // Query the count of numbers
    private int query(SegmentTreeNode root, int start, int end) {
        // 处理意外情况，如本题中在对 minValue的差值 查询时会出现 query(0, -1)的情况
        if (start > end) {
            return 0;
        }

        // 当节点所记录的范围(线段长度)被包含于需要查询的范围时直接返回结果即可
        if (start <= root.start && end >= root.end) {
            return root.count;
        }

        // 分治
        int mid = root.start + (root.end - root.start) / 2;
        int leftCount = 0, rightCount = 0;

        // 如果查询区间和左边节点区间有交集, 则返回查询区间在左边区间上的count值
        if (start <= mid) {
            leftCount = query(root.left, start, end);
        }

        // 如果查询区间和右边节点区间有交集, 则返回查询区间在右边区间上的count值
        if (end > mid) {
            rightCount = query(root.right, start, end);
        }

        return leftCount + rightCount;
    }

    private void modify(SegmentTreeNode root, int index, int value) {
        // 处理超出范围的请求（异常处理）
        if (root.start > index || root.end < index) {
            return;
        }
        // 当需要修改的点就是 root 时，直接对 root 修改即可
        // 然后可以直接 return，因为是单点修改，其 count 值由自身的value决定.
        if (root.start == index && root.end == index) {
            root.count += value;
            return;
        }

        // 分治
        int mid = root.start + (root.end - root.start) / 2;
        // 需要修改的节点在左子树中
        if (index <= mid) {
            modify(root.left, index, value);
            // 需要修改的节点在右子树中
        } else {
            modify(root.right, index, value);
        }

        // 注意：单点修改完后会影响父节点们的 count 值，
        // 因此需要从叶子节点向上返回到根节点, 去更新线段树上的值
        root.count = root.left.count + root.right.count;
    }
}

/**
 * Approach 2: Binary Index Tree
 * 经过 Approach 1 中的分析，我们可以得知可以将该问题转换为一个 区间和求解 的问题。
 * 那么该类型问题最好的解法无疑是 Binary Index Tree.
 * 它有着比线段树更好的时空复杂度和更简单的代码实现。
 *
 * 主体思路与 Approach 1 中相同，只不过这里需要注意的是 BITree 的下标我们需要从 1 开始使用。
 * 所以在计算 各个元素 A[i] 与最小值 minValue 的差值时，我们需要进行一次 加1 操作。
 * 使得最终结果分布于 [1...maxValue]。而同样查询范围为：[0...maxValue-1]
 * (BITree中查询 BITree[0] 直接返回初始值0)
 * 其他操作与 Segment Tree 解法相同。
 *
 * 若对于 Binary Index Tree 不了解或者想进一步了解的可以看这里:
 * https://github.com/cherryljr/LeetCode/blob/master/Binary%20Index%20Tree%20Template.java
 */
public class Solution {
    /*
     * @param A: an integer array
     * @return: A list of integers includes the index of the first number and the index of the last number
     */
    int[] BITree;

    public List<Integer> countOfSmallerNumberII(int[] A) {
        if (A == null || A.length == 0) {
            return new ArrayList<Integer>();
        }

        int minValue = Integer.MAX_VALUE;
        int maxValue = Integer.MIN_VALUE;
        int len = A.length;
        // 遍历数组获得输入数组中的最小值 minValue
        for (int i : A) {
            minValue = Math.min(minValue, i);
        }

        int[] diff = new int[len];
        // 利用数组 diff[] 储存数组中各个元素 A[i] 与最小值之间的差值
        // 并记录下最大差值 maxValue
        for (int i = 0; i < len; i++) {
            // 与 Segment Tree 不同的地方，值得注意的是 BITree 的大小要比 maxValue 大 1.
            // (我们从 BITree[1] 开始使用)，而 diff[i] 将作为 BITree[] 的数组下标使用.
            // 所以我们要在差值计算的结果上 加1.
            diff[i] = A[i] - minValue + 1;
            maxValue = Math.max(maxValue, diff[i]);
        }

        this.BITree = new int[maxValue + 1];
        Integer[] rst = new Integer[len];
        for (int i = 0; i < len; i++) {
            // 查询当前比 A[i] 小的元素的个数
            // 这也就意味着查询当前有几个与 minValue 差值小于 diff[i] 的数
            // 即对一个以 diff[i] 为下标的数组进行区域求和
            // （该数组 counts[diff[i]] 含义为: 与最小值差值为 diff[i] 的元素有几个）
            rst[i] = query(diff[i] - 1);
            // 更新 BITree[diff[i]]
            update(diff[i]);
        }

        return Arrays.asList(rst);
    }

    private void update(int index) {
        // 因为查询范围介于 [0...maxValue-1] 所以index 的值必定小于 BITree.length(maxValue)
        while (index < BITree.length) {
            BITree[index]++;
            index += index & -index;
        }
    }

    private int query(int index) {
        int sum = 0;
        while (index > 0) {
            sum += BITree[index];
            index -= index & -index;
        }
        return sum;
    }
}

/**
 * Approach 3: Binary Index Tree (with Discretization)
 * 之前我们利用了 数组中各个元素的值 与 数组中最小元素值 的 差值 来建立BITree数组。
 * 这个方法虽然解决了 负数问题 并 节约了一定的额外空间。
 * 但是仍然浪费了一些空间，特别是当出现 [0, 1, 2, 10000] 这种数据时，将浪费巨大的额外空间。
 * 因此我们可以通过对数据进行 离散化 来优化我们的额外空间。
 *
 * 所谓的 离散化 就是指把稀疏的数字映射到一个数组中，而这个数组的元素是紧凑的，这样就可以节约空间。
 * 举个例子：
 * 数组arr = [1, 3, 0, 5]，按照 计算差值的方法 映射到新的数组结果为：new_arr = [1, 1, 0, 1, 0, 1]。
 * 可以看到，中间有两个 0 是没有用的，而离散化的具体表现就是去掉这些0，使存储空间更加紧凑；
 * 由于去掉了没用的元素，因此，下标的意义就发生了变化，new_arr中的下标表示 原数据数组的排列顺序 ，下标对应的值表示 该元素在排序后的数组中所处的位置。
 * 举例来说就是：
 * arr = [1, 3, 0, 5] 排序后对应数组 sorted_arr = [0, 1, 3, 5]，那么对应的 new_arr = [1, 2, 0, 3]。
 * 也就是说，arr[0] = 1 在sorted_arr对应在第二位，因此new_arr[0] = 1；
 * arr[1] = 3在sorted_arr对应在第三位，因此new_arr[1] = 2，……依次类推。这样就完成了离散化。
 * 具体实现就是：先拷贝一份原始数组进行 排序，然后遍历原始数组 data,利用 二分查找法 在 排序后的数组中 查找对应的位置。这个位置就是对应的 new_arr[i] 的值。
 *
 * 然后我们就可以利用 离散化 后的数组 new_arr 来构建我们的 BITree.
 * 注意：这里的 new_arr 并不是 BITree 数组，而是我们为了构建 BITree 数组所进行离散化后的数组。
 * 后面的操作与 Approach 2 相同。
 *
 * Note:
 * 我们可以发现，通过离散化，我们可以大幅地节约额外空间的消耗。
 * 但是同时，它也增加了算法的时间复杂度。（排序O(nlogn) + 对 n 个数进行二分查找 O(nlogn)）
 * 因此，我们需要根据实际情况来决定是否使用离散化。
 * 不使用离散化的话，可以理解为 空间 换 时间。
 */
public class Solution {
    /*
     * @param A: an integer array
     * @return: A list of integers includes the index of the first number and the index of the last number
     */
    int[] BITree;

    public List<Integer> countOfSmallerNumberII(int[] A) {
        if (A == null || A.length == 0) {
            return new ArrayList<Integer>();
        }

        int len = A.length;
        // 离散化
        int[] sorted_arr = Arrays.copyOf(A, len);
        Arrays.sort(sorted_arr);
        int[] discre = new int[len];
        for (int i = 0; i < len; i++) {
            discre[i] = Arrays.binarySearch(sorted_arr, A[i]) + 1;
        }

        this.BITree = new int[len + 1];
        Integer[] rst = new Integer[len];
        for (int i = 0; i < len; i++) {
            // 查询当前比 A[i] 小的元素的个数
            // 这也就意味着查询当前有几个 排序值 小于 当前元素A[i]排序值(discre[i]) 的数。
            rst[i] = query(discre[i] - 1);
            // 更新 BITree[discre[i]]
            update(discre[i]);
        }

        return Arrays.asList(rst);
    }

    private void update(int index) {
        // 因为查询范围介于 [0...len-1] 所以index 的值必定小于 BITree.length (A.length)
        while (index < BITree.length) {
            BITree[index]++;
            index += index & -index;
        }
    }

    private int query(int index) {
        int sum = 0;
        while (index > 0) {
            sum += BITree[index];
            index -= index & -index;
        }
        return sum;
    }
}