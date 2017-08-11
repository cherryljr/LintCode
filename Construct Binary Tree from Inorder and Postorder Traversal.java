与 Construct Binary Tree from Preorder and Inorder Traversal 非常类似。
同样使用到了分治法。分析如下:
因为 Postorder 的最后一个元素就是Tree的 root，
而 Inorder 中root左边的节点为左子树，右边的节点为右字树。
因此：position - inStart 是左子树的范围大小, inEnd - position 为右子树的范围大小
对于 Postorder, root.left 的范围为：postStart ~ postStart + (position - Instart) - 1
							 root.right 的范围为：postStart + (postStart - inStart) ~ postEnd - 1
对于 Inorder,   root.left 的范围为：inStart ~ position - 1
						   root.right 的范围为：position + 1 ~ inEnd
依此为思路进行分治。
注：注意数组 index 的范围，不确定时可以用简单的 Example 实验一次即可。这样可以保证正确性

Postorder array 的末尾， 就是当下层的root.   
在Inorder array 里面找到这个root,就刚好把左右两边分割成left/right tree。

这题比较tricky地用了一个helper做recursive。 特别要注意处理index的变化, precisely考虑开头结尾

可惜有个不可避免的O(n) find element in array.

/*
Given inorder and postorder traversal of a tree, construct the binary tree.

Note
You may assume that duplicates do not exist in the tree.

Example
Given inorder [1,2,3] and postorder [1,3,2]

return a tree

  2
 /  \
1    3

     
Tags Expand 
Binary Tree
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
    private int findPosition(int[] arr, int start, int end, int key) {
        int i;
        for (i = start; i <= end; i++) {
            if (arr[i] == key) {
                return i;
            }
        }
        return -1;
    }

    private TreeNode helper(int[] inorder, int inStart, int inEnd,
                            int[] postorder, int postStart, int postEnd) {
        if (inStart > inEnd) {
            return null;
        }

        TreeNode root = new TreeNode(postorder[postEnd]);
        int position = findPosition(inorder, inStart, inEnd, postorder[postEnd]);

        root.left = helper(inorder, inStart, position - 1,
                postorder, postStart, postStart + position - inStart - 1);
        root.right = helper(inorder, position + 1, inEnd,
                postorder, postStart + position - inStart, postEnd - 1);
                
        return root;
    }

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if (inorder.length != postorder.length) {
            return null;
        }
        
        return helper(inorder, 0, inorder.length - 1,
                      postorder, 0, postorder.length - 1);
    }
}