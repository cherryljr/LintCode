/*
Description
Given an integer array in the construct method, implement two methods query(start, end) and modify(index, value):
For query(start, end), return the sum from index start to index end in the given array.
For modify(index, value), modify the number in the given index to value

Notice
We suggest you finish problem Segment Tree Build, Segment Tree Query and Segment Tree Modify first.

Example
Given array A = [1,2,7,8,5].

query(0, 2), return 10.
modify(0, 4), change A[0] from 1 to 4.
query(0, 1), return 6.
modify(2, 1), change A[2] from 7 to 1.
query(2, 4), return 14.

Challenge 
O(logN) time for query and modify.

Tags 
Binary Search LintCode Copyright Segment Tree
*/

/**
 * Approach 1: Segment Tree 
 * 这道题目是对于线段树的典型应用。（但线段树并不是最佳解法）
 * 主要涉及到了：线段树的构建；线段树的查询；线段树的单点修改
 * 对这3个操作不太清楚的可以参见以下代码与解析：
 * 线段树的构建：https://github.com/cherryljr/LintCode/blob/master/Segment%20Tree%20Build%20II.java
 * 线段树的查询：https://github.com/cherryljr/LintCode/blob/master/Segment%20Tree%20Query.java
 * 线段树的单点修改：https://github.com/cherryljr/LintCode/blob/master/Segment%20Tree%20Modify.java
 *
 * Note:
 * 说到求区间和的方法，大家最容易想到的就是维护一个前缀和数组。
 * 因此在这里对于这道题目的解法，我将对 线段树 和 前缀和 这两种不同的方法进行一个比较。
 * 线段树的主要时间复杂度在于：
 * 构建：O(N); 查询：O(logn); 修改：O(logn).
 * 而利用 前缀和(Prefix Sum)数组 的时间复杂度在于:
 * 构建：O(N); 查询:O(1); 修改：O(N).
 * 我们可以看出：二者主要差距在于 查询 和 修改。
 * 因此如果当需求是经常被查询而几乎不会进行修改时可以使用 Prefix Sum.
 * 其他情况下，使用线段树总体效果会更好。
 * 当然线段树的应用还不至于此，这里仅对 区间和 情况进行分析。
 */
public class Solution {
    /* you may need to use some attributes here */
    class SegmentTreeNode {
        int start, end;
        int sum;
        SegmentTreeNode left, right;
        
        SegmentTreeNode(int start, int end, int sum) {
            this.start = start;
            this.end = end;
            this.sum = sum;
            this.left = this.right = null;
        }
    }
    
    SegmentTreeNode root;

    /*
    * @param A: An integer array
    */
    public Solution(int[] A) {
        // do intialization if necessary
        root = buildHelper(A, 0, A.length - 1);
    }
    
    private SegmentTreeNode buildHelper(int[] A, int start, int end) {
        // 处理异常情况
        if (start > end) {
            return null;
        }
        
        // 根据节点区间的左边界的序列值为节点赋初值
        SegmentTreeNode root = new SegmentTreeNode(start, end, A[start]);
        // 当 start == end 时，说明为叶子节点，直接返回节点即可
        if (start == end) {
            return root;
        }
        // 分治
        int mid = root.start + (root.end - root.start) / 2;
        root.left = buildHelper(A, start, mid);
        root.right = buildHelper(A, mid + 1, end);
        root.sum = 0;
        if (root.left != null) {
            root.sum += root.left.sum;
        }
        if (root.right != null) {
            root.sum += root.right.sum;
        }
        return root;
    }

    /*
     * @param start: An integer
     * @param end: An integer
     * @return: The sum from start to end
     */
    public long query(int start, int end) {
        return query(root, start, end);
    }
    
    private long query(SegmentTreeNode root, int start, int end) {
        // 当节点所记录的范围(线段长度)被包含于需要查询的范围时直接返回结果即可
        if (start <= root.start && end >= root.end) {
            return root.sum;
        }
        
        // 分治
        int mid = root.start + (root.end - root.start) / 2;
        long leftSum = 0, rightSum = 0;
        
        // 如果查询区间和左边节点区间有交集, 则返回查询区间在左边区间上的sum值
        if (start <= mid) {
            leftSum = query(root.left, start, end);
        }
        
        // 如果查询区间和右边节点区间有交集, 则返回查询区间在右边区间上的sum值
        if (end > mid) {
            rightSum = query(root.right, start, end);
        }
        
        return leftSum + rightSum;
    }

    /*
     * @param index: An integer
     * @param value: An integer
     * @return: nothing
     */
    public void modify(int index, int value) {
        modify(root, index, value);
    }
    
    private void modify(SegmentTreeNode root, int index, int value) {
        // 处理超出范围的请求（异常处理）
        if (root.start > index || root.end < index) {
            return;
        }
        // 当需要修改的点就是 root 时，直接对 root 修改即可
        // 然后可以直接 return，因为是单点修改，其sum值由自身的value决定.
        if (root.start == index && root.end == index) {
            root.sum = value;
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
        
        // 注意：单点修改完后会影响父节点们的 sum 值，
        // 因此需要从叶子节点向上返回到根节点, 去更新线段树上的值
        root.sum = root.left.sum + root.right.sum;
    }
}

 /**
 * Approach 2: BITree
 * 求区间和（并且支持单点修改）最好的数据结构无疑是树状数组了。
 * 对于树状数组的详细分析与代码注释可以参见：Binary Index Tree Template
 * https://github.com/cherryljr/LeetCode/blob/master/Binary%20Index%20Tree%20Template.java
 * 该题与模板唯一的区别就是在于模板中 update是在原来的基础上加上 val,
 * 而本题中是将 arr[index] 改成 val.
 * 实现方法很简单，多建立一个大小与输入数组相同的数组 nums[] 用于在第一次进行初始化即可。
 * 当然我们还能通过写一个新的 init() 方法来实现 BITree 的建立，这样就能节约 O(n) 的额外空间，
 * 但同样代码的复用性就降低了。大家自行取舍即可。
 */
public class Solution {
    /* you may need to use some attributes here */
    private int size;
    int[] BITree;
    int[] nums;

    /*
    * @param A: An integer array
    */
    public Solution(int[] nums) {
        // do intialization if necessary
        size = nums.length;
        BITree = new int[size + 1];
        // this.nums = new int[size];
        this.nums = nums;  // 不分配额外空间，使用 init() 方法建立BITree
        for (int i = 0; i < size; i++) {
            // update(i, nums[i]);
            init(i, nums[i]);
        }
    }
    
    private void init(int index, int val) {
        index += 1;
        while (index < BITree.length) {
            BITree[index] += val;
            index += index & -index;
        }
    }

    /*
     * @param start: An integer
     * @param end: An integer
     * @return: The sum from start to end
     */
    public long query(int start, int end) {
        return (start > end || start < 0 || end >= size) ? 0 :
                start == end ? nums[start] : getSum(end) - getSum(start - 1);
    }
    
    private int getSum(int index) {
        if (index < 0 || index >= size) {
            return 0;
        }
        int sum = 0;
        index += 1;
        while (index > 0) {
            sum += BITree[index];
            index -= index & -index;
        }

        return sum;
    }

    /*
     * @param index: An integer
     * @param value: An integer
     * @return: nothing
     */
    public void modify(int index, int val) {
        int delta = val - nums[index];
        nums[index] = val;
        index += 1;
        while (index < BITree.length) {
            BITree[index] += delta;
            index += index & -index;
        }
    }
}
