/*
Description
Given a binary tree, find its minimum depth.
The minimum depth is the number of nodes along the shortest path from the root node down to the nearest leaf node.

Example
Example 1:
    Input: {}
    Output: 0

Example 2:
    Input:  {1,#,2,3}
    Output: 3   
    Explanation:
    1
     \ 
      2
     /
    3    

Example 3:
    Input:  {1,2,3,#,#4,5}
    Output: 2   
    Explanation: 
      1
     / \ 
    2   3
       / \
      4   5  
 */

/**
 * Approach 1: Divide and Conquer
 * 二叉树的问题几乎都可以使用分治法解决。
 * 
 * 这里注意将该题与 Maximum Depth of Binary Tree 进行比较。
 * 刚开始拿到这道题，就想不是很简单吗，不就是最大深度的代码改为最小就行了吗？ 
 * 但是这样做的结果就是有些测试时通不过的。
 * 因为如果出现斜树的情况，也就是说只有左子树，或只有右子树的时候，
 * 如果按照求最大深度的方法来实现的话，得出的答案是0，明显是错误的。
 * 
 * 故这里有两种解决方法：
 * Approach 1:
 *  第一种就是计算左子树和右子树深度的时候，判断是否等于0，如果等于0，说明该子树不存在，深度赋值为最大值。
 * Approach 2:
 *  第二种就是判断左子树或右子树是否为空，若左子树为空，则返回右子树的深度，反之返回左子树的深度，
 *  如果都不为空，则返回左子树和右子树深度的最小值
 */

/**
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int val;
 *     public TreeNode left, right;
 *     public TreeNode(int val) {
 *         this.val = val;
 *         this.left = this.right = null;
 *     }
 * }
 */
public class Solution {
    /**
     * @param root: The root of binary tree
     * @return: An integer
     */
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return getMin(root);
    }

    public int getMin(TreeNode root){
        if (root == null) {
            return Integer.MAX_VALUE;
        }
        //  将遍历到叶子节点时return 1作为递归的边界
        if (root.left == null && root.right == null) {
            return 1;
        }

        return Math.min(getMin(root.left), getMin(root.right)) + 1;
    }
}

/**
 * Approach 2
 */
public class Solution {
    /**
     * @param root: The root of binary tree
     * @return: An integer
     */
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftMin = minDepth(root.left);
        int rightMin = minDepth(root.right);
        if (root.left == null) {
            return rightMin + 1;
        }
        if (root.right == null) {
            return leftMin + 1;
        }
        return Math.min(leftMin, rightMin) + 1;
    }
}