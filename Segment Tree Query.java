/*
Description
For an integer array (index from 0 to n-1, where n is the size of this array), 
in the corresponding SegmentTree, 
each node stores an extra attribute max to denote the maximum number in the interval of the array (index from start to end).

Design a query method with three parameters root, start and end, 
find the maximum number in the interval [start, end] by the given root of segment tree.

Notice
It is much easier to understand this problem if you finished Segment Tree Build first.

Example
For array [1, 4, 2, 3], the corresponding Segment Tree is:

                  [0, 3, max=4]
                 /             \
          [0,1,max=4]        [2,3,max=3]
          /         \        /         \
   [0,0,max=1] [1,1,max=4] [2,2,max=2], [3,3,max=3]
query(root, 1, 1), return 4

query(root, 1, 2), return 4

query(root, 2, 3), return 3

query(root, 0, 2), return 4

Tags 
LintCode Copyright Binary Tree Segment Tree
*/

/**
 * 线段树的区间查询：
 * 线段树的区间查询操作就是将当前区间分解为较小的子区间,然后由子区间的最大值就可以快速得到需要查询区间的最大值。
 *
 * 因为任意长度的线段，最多被拆分成logn条线段树上存在的线段，所以查询的时间复杂度为O(logn)
 * 详细分析与示例：
 * http://www.jiuzhang.com/tutorial/segment-tree/88
 */

/**
 * Definition of SegmentTreeNode:
 * public class SegmentTreeNode {
 *     public int start, end, max;
 *     public SegmentTreeNode left, right;
 *     public SegmentTreeNode(int start, int end, int max) {
 *         this.start = start;
 *         this.end = end;
 *         this.max = max
 *         this.left = this.right = null;
 *     }
 * }
 */
public class Solution {
    /*
     * @param root: The root of segment tree.
     * @param start: start value.
     * @param end: end value.
     * @return: The maximum number in the interval [start, end]
     */
    public int query(SegmentTreeNode root, int start, int end) {
        // 当节点所记录的范围(线段长度)被包含于需要查询的范围时直接返回结果即可
        if (start <= root.start && end >= root.end) {
            return root.max;
        }
        
        // 给结果赋初值
        int rst = Integer.MIN_VALUE;
        // 将当前节点区间分割为左右2个区间的分割线(分治)
        int mid = root.start + (root.end - root.start) / 2;
        
        // 如果查询区间和左边节点区间有交集, 则寻找查询区间在左边区间上的最大值
        if (mid >= start) {   
            rst = Math.max(rst, query(root.left, start, end));
        }
        // 如果查询区间和右边节点区间有交集, 则寻找查询区间在右边区间上的最大值
        if (mid + 1 <= end) { 
            rst = Math.max(rst, query(root.right, start, end));
        }
        
        return rst;
    }
}
