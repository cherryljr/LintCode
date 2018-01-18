/*
Description
For a Maximum Segment Tree, which each node has an extra value max to store the maximum value in this node's interval.

Implement a modify function with three parameter root, 
index and value to change the node's value with [start, end] = [index, index] to the new given value. 
Make sure after this change, every node in segment tree still has the max attribute with the correct value.

Notice
We suggest you finish problem Segment Tree Build and Segment Tree Query first.

Example
For segment tree:

                      [1, 4, max=3]
                    /                \
        [1, 2, max=2]                [3, 4, max=3]
       /              \             /             \
[1, 1, max=2], [2, 2, max=1], [3, 3, max=0], [4, 4, max=3]
if call modify(root, 2, 4), we can get:

                      [1, 4, max=4]
                    /                \
        [1, 2, max=4]                [3, 4, max=3]
       /              \             /             \
[1, 1, max=2], [2, 2, max=4], [3, 3, max=0], [4, 4, max=3]
or call modify(root, 4, 0), we can get:

                      [1, 4, max=2]
                    /                \
        [1, 2, max=2]                [3, 4, max=0]
       /              \             /             \
[1, 1, max=2], [2, 2, max=1], [3, 3, max=0], [4, 4, max=0]

Challenge 
Do it in O(h) time, h is the height of the segment tree.

Tags 
LintCode Copyright Binary Tree Segment Tree
*/

/**
 * 线段树的单点修改：
 * 每次从父节点走到子节点的时候，区间都是一分为二，那么我们要修改index的时候，
 * 我们从 root 出发，判断 index 会落在左子树还是右子树，然后继续递归，这样就可以很容易从根节点走到叶子节点，
 * 然后更新叶子节点的值，递归返回前不断更新每个节点的最大值即可。
 * （注意单点修改将导致父节点们的 value 也发生变换，所以需要递归返回节点值去更新线段树上的值）。
 *
 * 因为线段树的高度为log(n),所以更新序列中一个节点的时间复杂度为log(n)。
 * 详细分析与示例：
 * http://www.jiuzhang.com/tutorial/segment-tree/89
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
     * @param index: index.
     * @param value: value
     * @return: 
     */
    public void modify(SegmentTreeNode root, int index, int value) {
        // 处理超出范围的请求（异常处理）
        if (root.start > index || root.end < index) {
            return;
        }
        // 当需要修改的点就是 root 时，直接对 root 修改即可
        // 然后可以直接 return，因为是单点修改，其max值由自身的value决定.
        if (root.start == index && root.end == index) {
            root.max = value;
            return;
        }
        
        // 分治
        int mid = root.start + (root.end - root.start) / 2;
        // 需要修改的节点在左子树中
        if (index <= mid) {
            // 递归操作
            modify(root.left, index, value);
        // 需要修改的节点在右子树中
        } else {
            // 递归操作
            modify(root.right, index, value);
        }
        
        // 注意：单点修改完后会影响父节点们的 sum 值，
        // 因此需要从叶子节点向上返回到根节点, 去更新线段树上的值
        root.max = Math.max(root.left.max, root.right.max);
    }
}