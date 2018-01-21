/*
Description
The structure of Segment Tree is a binary tree which each node has two attributes start and end denote an segment / interval.
start and end are both integers, they should be assigned in following rules:

The root's start and end is given by build method.
The left child of node A has start=A.left, end=(A.left + A.right) / 2.
The right child of node A has start=(A.left + A.right) / 2 + 1, end=A.right.
if start equals to end, there will be no children for this node.
Implement a build method with a given array, so that we can create a corresponding segment tree with every node value represent the corresponding interval max value in the array, return the root of this segment tree.

Clarification
Segment Tree (a.k.a Interval Tree) is an advanced data structure which can support queries like:

which of these intervals contain a given point
which of these points are in a given interval
See wiki:
Segment Tree
Interval Tree

Example
Given [3,2,1,4]. The segment tree will be:

                 [0,  3] (max = 4)
                  /            \
        [0,  1] (max = 3)     [2, 3]  (max = 4)
        /        \               /             \
[0, 0](max = 3)  [1, 1](max = 2)[2, 2](max = 1) [3, 3] (max = 4)
Tags 
Segment Tree
*/

/**
 * 线段树也是一种二叉树，它储存了某一段区间内的数据信息，因此一个节点至少需要：start, end, val(不唯一)这几个元素。
 * 得益于此它能够高效的处理区间修改查询等问题。如 查询区间的 最大值/最小值/和 等方面。
 *
 * 这道题目考察的是线段树的构造，采用 分治的思想即可解决。
 *  root.max = Math.max(root.left.max, root.right.max)
 * 因为每次将区间的长度一分为二,所有创造的节点个数，即底层有n个节点，那么倒数第二次约n/2个节点，倒数第三次约n/4个节点，
 * 依次类推：
 *  n + 1/2 * n + 1/4 * n + 1/8 * n + ...
 *  =   (1 + 1/2 + 1/4 + 1/8 + ...) * n
 *  =   2n
 * 所以构造线段树的时间复杂度和空间复杂度都为O(n)
 *
 * 具体的视频解析可以参考：http://www.jiuzhang.com/tutorial/segment-tree/87
 *
 * 线段树的应用场景：
 *  1.如果问题带有区间操作，或者可以转化成区间操作，可以尝试往线段树方向考虑。
 *  从题目中抽象问题，将问题转化成一列区间操作，注意这步很关键。
 * 当我们分析出问题是一些列区间操作的时候：
 *  1. 对区间的一个点的值进行修改
 *  2. 对区间的一段值进行统一的修改
 *  3. 询问区间的最大值、最小值、和
 * 我们都可以采用线段树来解决这个问题
 * 
 * 什么情况下，无法使用线段树？
 * 如果我们删除或者增加区间中的元素，那么区间的大小将发生变化，此时是无法使用线段树解决这种问题的。
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
     * @param A: a list of integer
     * @return: The root of Segment Tree
     */
    public SegmentTreeNode build(int[] A) {
        if (A == null || A.length == 0) {
            return null;
        }

        return buildHelper(A, 0, A.length - 1);
    }

    private SegmentTreeNode buildHelper(int[] A, int start, int end) {
        if (start > end) {
            return null;
        }

        // 根据节点区间的左边界的序列值为节点赋初值
        SegmentTreeNode root = new SegmentTreeNode(start, end, A[start]);
        if (start == end) {
            return root;
        }

        int mid = start + (end - start) / 2;
        // Divide and Conquer
        root.left = buildHelper(A, start, mid);
        root.right = buildHelper(A, mid + 1, end);
        if (root.left != null && root.left.max > root.max) {
            root.max = root.left.max;
        }
        if (root.right != null && root.right.max > root.max) {
            root.max = root.right.max;
        }
        // 如果需要区间的最小值:
        // root.min = Math.min(root.left.min, root.right.min);
        // 如果需要区间的和:
        // root.sum = root.left.sum + root.right.sum;

        return root;
    }

}