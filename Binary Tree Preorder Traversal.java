/*
Given a binary tree, return the preorder traversal of its nodes' values.

Note
Given binary tree {1,#,2,3},

   1
    \
     2
    /
   3
 

return [1,2,3].

Example
Challenge
Can you do it without recursion?

Tags Expand 
Tree Binary Tree
*/


/**
 * Approach 1: Recursion
 * If we want to implement the Preorder Traversal.
 * We just need to print/store the curr.node
 * when we visited the curr node at the first time.
 *
 * Time Complexity:  O(n)
 * Space Complexity: O(n)
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
     * @param root: A Tree
     * @return: Preorder in ArrayList which contains node values.
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<Integer> rst = new ArrayList<>();
        preorder(rst, root);
        return rst;
    }

    private void preorder(List<Integer> rst, TreeNode node) {
        if (node != null) {
            // Store the node when we visited it firstly
            rst.add(node.val);
            // inorder left subtree
            preorder(rst, node.left);
            // inorder right subtree
            preorder(rst, node.right);
        }
    }
}

/**
 * Approach 2: Using Stack
 * 1. Check if root is null
 * 2. use a container to save results
 *  store current node
 *  put right on stack
 *  put left on stack
 * Note:
 *  In next run, the ‘left’ will be on top of stack, and will be taken first.
 *  So the order becomes: parent -> left -> right
 *
 * Time Complexity:  O(n)
 * Space Complexity: O(n)
 */
public class Solution {
    /**
     * @param root: A Tree
     * @return: Preorder in ArrayList which contains node values.
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<Integer> rst = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()) {
            TreeNode node = stack.pop();
            rst.add(node.val);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }

        return rst;
    }
}

/**
 * Approach 3: Morris Traversal
 * Reference:
 * https://github.com/cherryljr/LintCode/blob/master/Morris%20Traversal%20Template.java
 *
 * Time Complexity:  O(n)
 * Space Complexity: O(1)
 */
public class Solution {
    /**
     * @param root: A Tree
     * @return: Preorder in ArrayList which contains node values.
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        TreeNode curr = root;
        TreeNode rightMost = null;
        List<Integer> rst = new ArrayList<>();
        while (curr != null) {
            if (curr.left == null) {
                /*
                 * If there is no left subtree, then we can visit this node and
                 * continue traversing right.
                 */
                // Store the curr.val when we visit the node firstly
                rst.add(curr.val);
                // move to next right node
                curr = curr.right;
            } else {
                // if curr node has a left subtree
                // then get rightmost node of left subtree
                rightMost = getRightMostNode(curr);
                if (rightMost.right == null) {
                    /*
                     * If the rightMost node's right subtree is null, then we have never been here before.
                     * (the first time that we visit the curr node)
                     * the current node should be the right child of the rightMost node.
                     */
                    // Store the curr.val when we visit the node firstly
                    rst.add(curr.val);
                    rightMost.right = curr;
                    curr = curr.left;
                } else {
                    /*
                     * If there is a right subtree, it is a link that we created on a previous pass,
                     * (the second time that we visit the curr node)
                     * so we should unlink it and visit this node to avoid infinite loops
                     */
                    rightMost.right = null;
                    curr = curr.right;
                }
            }
        }

        return rst;
    }

    // Get the node with the right most position of left subtree of curr.
    // Attention: node.right != curr, cuz the node.right may be linked to curr node before.
    public TreeNode getRightMostNode(TreeNode curr) {
        TreeNode node = curr.left;
        while (node.right != null && node.right != curr) {
            node = node.right;
        }
        return node;
    }
}