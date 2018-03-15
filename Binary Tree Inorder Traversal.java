/*
Given a binary tree, return the inorder traversal of its nodes' values.

Example
Given binary tree {1,#,2,3},

   1
    \
     2
    /
   3
 

return [1,3,2].

Challenge
Can you do it without recursion?

Tags Expand 
Recursion Binary Tree Binary Tree Traversal
*/

/**
 * Approach 1: Recursion
 * If we want to implement the Inorder Traversal.
 * We just need to print/store the curr.node
 * when we visited the curr node at the second time.
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
     * @param root: The root of binary tree.
     * @return: Inorder in ArrayList which contains node values.
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<Integer> rst = new ArrayList<>();
        inorder(rst, root);
        return rst;
    }

    private void inorder(List<Integer> rst, TreeNode node) {
        if (node != null) {
            // inorder left subtree
            inorder(rst, node.left);
            // Store the node when we visited it at the second time
            rst.add(node.val);
            // inorder right subtree
            inorder(rst, node.right);
        }
    }
}

/**
 * Approach 2: Using Stack
 * Inorder traversal:
 *  use 1 stack, push left till end;
 *  print/store curr;
 *  push right to stack
 * Note: after curr = curr.right, curr could be null;
 * this will skip the while loop, and move on to next element.
 *
 * Trick: in Inorder, we care the right node least. So we keep going with left and curr;
 * only when there is a right node, we add it;
 * even after this, we go deep into that right node's left children all the way down.
 *
 * Time Complexity:  O(n)
 * Space Complexity: O(n)
 */
public class Solution {
    /**
     * @param root: A Tree
     * @return: Inorder in ArrayList which contains node values.
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<Integer> rst = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        // if the curr is not null or the stack isn't empty
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            rst.add(curr.val);
            curr = curr.right;
        }

        return rst;
    }
}

/**
 * Approach 3: Morris Traversal
 * There is a clever way to perform an in-order traversal using only linear time and constant space,
 * first described by J. H. Morris in his 1979 paper "Traversing Binary Trees Simply and Cheaply".
 * In this method, we have to use a new data structure-Threaded Binary Tree, and the strategy is as follows:
 *  Step 1: Initialize current as root
 *  Step 2: While current is not NULL,
 *  If current does not have left child
 *      a. Add current's value
 *      b. Go to the right, i.e., current = current.right
 *  Else
 *      a. In current's left subtree, make current the right child of the rightmost node
 *      b. Go to this left child, i.e., current = current.left
 * 
 * Complexity Analysis
 * Time complexity : O(n).
 *  To prove that the time complexity is O(n),
 *  the biggest problem lies in finding the time complexity of finding the predecessor nodes of all the nodes in the binary tree.
 *  Intuitively, the complexity is O(nlogn), because to find the predecessor node for a single node related to the height of the tree.
 *  But in fact, finding the predecessor nodes for all nodes only needs O(n) time.
 *  Because a binary Tree with n nodes has n−1 edges, the whole processing for each edges up to 2 times,
 *  one is to locate a node, and the other is to find the predecessor node. So the complexity is O(n).
 * Space complexity : O(1)
 */
public class Solution {
    /**
     * @param root: A Tree
     * @return: Inorder in ArrayList which contains node values.
     */
    public List<Integer> inorderTraversal(TreeNode root) {
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
                // if curr don't have left subtree, it will be visited only once
                // Or you can look it like we visit the node twice at the same time
                // so add curr.val to the list directly
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
                    rightMost.right = curr;
                    curr = curr.left;
                } else {
                    /*
                     * If there is a right subtree, it is a link that we created on a previous pass,
                     * (the second time that we visit the curr node)
                     * so we should unlink it and visit this node to avoid infinite loops
                     */
                    rightMost.right = null;
                    // Store the curr.val when we visited it at the second time
                    rst.add(curr.val);
                    curr = curr.right;
                }
            }
        }

        return rst;
    }

    public TreeNode getRightMostNode(TreeNode curr) {
        TreeNode node = curr.left;
        while (node.right != null && node.right != curr) {
            node = node.right;
        }
        return node;
    }
}
