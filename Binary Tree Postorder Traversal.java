/*
Description
Given a binary tree, return the postorder traversal of its nodes' values.

Example
Given binary tree {1,#,2,3},
   1
    \
     2
    /
   3

return [3,2,1].

Challenge
Can you do it without recursion?

Tags
Recursion Binary Tree Binary Tree Traversal
 */

/**
 * Approach 1: Recursion
 * If we want to implement the Postorder Traversal.
 * We just need to print/store the curr.node
 * when we visited the curr node at the third time.
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
     * @return: Postorder in ArrayList which contains node values.
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<Integer> rst = new ArrayList<>();
        postorder(rst, root);
        return rst;
    }

    private void postorder(List<Integer> rst, TreeNode node) {
        if (node != null) {
            // postorder left subtree
            postorder(rst, node.left);
            // postorder right subtree
            postorder(rst, node.right);
            // Store the node when we visited it at the third time
            rst.add(node.val);
        }
    }
}

/**
 * Approach 2: Using Stack (Two Stack)
 * 我们同样也可以利用 Stack 来模拟递归，进行人工压栈，
 * 从而实现 非递归遍历 的方法。
 * 但是我们要怎么实现：左->右->中 这个顺序的遍历呢？
 * 显然只使用 一个栈 并不好实现，因此我们可以利用 Stack 能够将元素逆序的这么个特性。
 * 从而使用两个栈来实现 后序遍历。
 * 具体做法：
 *  首先，同样的，我们需要一个栈 stack1 来模拟树的递归遍历过程 （人工压栈）
 *  然后按照 中->右->左 的顺序将整棵树压入 第二个栈stack2 中，
 *  最后我们只需要从 stack2 中取出各个节点，完成遍历即可。
 *  其顺序自然就是：左->右->中
 *
 * 时间复杂度为：O(n)
 * 空间复杂度为：O(n)
 */
public class Solution {
    /**
     * @param root: The root of binary tree.
     * @return: Postorder in ArrayList which contains node values.
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<Integer> rst = new ArrayList<>();
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        stack1.push(root);
        while (!stack1.isEmpty()) {
            TreeNode curr = stack1.pop();
            stack2.push(curr);
            if (curr.left != null) {
                stack1.push(curr.left);
            }
            if (curr.right != null) {
                stack1.push(curr.right);
            }
        }
        // poll the node from stack2 to implement reversal
        while (!stack2.isEmpty()) {
            rst.add(stack2.pop().val);
        }

        return rst;
    }
}

/**
 * Approach 3: Using Stack (One Stack)
 * 在 Approach 2 中我们实现了使用 两个Stack 完成后序遍历的解决方案。
 * 但实际我们只需要通过 一个栈 就足够了。
 * 只是需要对各种情况进行分析，书写起来相对麻烦罢了。
 * 下面给出参考代码。
 */
public class Solution {
    /**
     * @param root: The root of binary tree.
     * @return: Postorder in ArrayList which contains node values.
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<Integer> rst = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        TreeNode curr = null;
        while (!stack.isEmpty()) {
            curr = stack.peek();
            if (curr.left != null && root != curr.left && root != curr.right) {
                stack.push(curr.left);
            } else if (curr.right != null && root != curr.right) {
                stack.push(curr.right);
            } else {
                rst.add(stack.pop().val);
                root = curr;
            }
        }

        return rst;
    }
}

/**
 * Approach 4: Morris Traversal
 * Reference：
 * https://github.com/cherryljr/LintCode/blob/master/Morris%20Traversal%20Template.java
 *
 * Time Complexity:  O(n)
 * Space Complexity: O(1)
 */
public class Solution {
    /**
     * @param root: The root of binary tree.
     * @return: Postorder in ArrayList which contains node values.
     */
    public List<Integer> postorderTraversal(TreeNode root) {
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
                    // Remember to unlink the rightMost's right pointer to the curr node firstly
                    rightMost.right = null;
                    // Store the curr.val when we visited it at the second time
                    addReverseEdge(rst, curr.left);
                    curr = curr.right;
                }
            }
        }
        // When we quit the loop, add the reversal right edge of root to the result
        addReverseEdge(rst, root);

        return rst;
    }

    private void addReverseEdge(List<Integer> rst, TreeNode root) {
        TreeNode tail = reverseEdge(root);
        TreeNode curr = tail;
        while (curr != null) {
            rst.add(curr.val);
            curr = curr.right;
        }
        reverseEdge(tail);
    }

    private TreeNode reverseEdge(TreeNode root) {
        TreeNode pre = null;
        while (root != null) {
            TreeNode temp = root.right;
            root.right = pre;
            pre = root;
            root = temp;
        }
        return pre;
    }

    // Get the node with the right most position of left subtree of curr.
    // Attention: node.right != curr, cuz the node.right may be linked to curr node before.
    private TreeNode getRightMostNode(TreeNode curr) {
        TreeNode node = curr.left;
        while (node.right != null && node.right != curr) {
            node = node.right;
        }
        return node;
    }
}

