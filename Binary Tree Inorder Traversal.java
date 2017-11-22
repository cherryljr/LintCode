法一:      
Recursive: Divide and Conquer, with helper(dfs) method

法二:   
Using Stack: 
Add left nodes all the way   
Print curr   
Move to right, add right if possible.   
  
注意stack.pop()在add完 leftMost child 的后，一定要 curr = curr.right.
即开始遍历其右子树。
若不右移，很可能发生窘境：    
curr下一轮还是去找自己的left-most child，不断重复curr and curr.left, 会infinite loop, 永远在左边上下上下。

法三：
Morris Traversal
使用了线索二叉树这种新的数据结构来实现。
https://en.wikipedia.org/wiki/Threaded_binary_tree
https://leetcode.com/articles/binary-tree-inorder-traversal/

二叉树的逆序中序遍历可见：Convert BST to Greater Tree (in leetcode)

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

/*
    Approach 1: Recursive
*/
public class Solution {
    /**
     * @param root: The root of binary tree.
     * @return: Inorder in ArrayList which contains node values.
     */
    public ArrayList<Integer> inorderTraversal(TreeNode root) {
        ArrayList<Integer> rst = new ArrayList<Integer>();
        if (root == null) {
            return rst;
        }
        helper(rst, root);
        
        return rst;
    }

    private void helper(ArrayList<Integer> rst, TreeNode node) {
        if (node != null) {
            helper(rst, node.left);
            rst.add(node.val);
            helper(rst, node.right);
        }
 
    }
}

/*
    Approach 2: Using Stack (Non-recursive)
*/

/*
 Inorder traversal: use 1 stack, push left till end; pirnt/store curr; push right to stack

    Note: after curr = curr.right, curr could be null; this will skip the while loop, and move on to next element.

    Trick: in Inorder, we care the right node least. So we keep going with left and curr; 
    only when there is a right node, we add it;
    even after this, we go deep into that right node's left children all the way down.
    
This method just like : "Binary Search Tree Iterator.java"
The code that how to Traversa the tree is very worth to learn. How smart way!!!
*/

public class Solution {
    /**
     * @param root: The root of binary tree.
     * @return: Inorder in ArrayList which contains node values.
     */
    public ArrayList<Integer> inorderTraversal(TreeNode root) {
         ArrayList<Integer> rst = new ArrayList<Integer>();
        if (root == null) {
            return rst;
        }

        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode curr = root;
        
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

/*
    Approach 3: Morris Traversal
*/
/*
There is a clever way to perform an in-order traversal using only linear time and constant space, 
first described by J. H. Morris in his 1979 paper "Traversing Binary Trees Simply and Cheaply". 
In this method, we have to use a new data structure-Threaded Binary Tree, and the strategy is as follows:
	Step 1: Initialize current as root
	Step 2: While current is not NULL,
	If current does not have left child
		a. Add current’s value
		b. Go to the right, i.e., current = current.right
	Else
		a. In current's left subtree, make current the right child of the rightmost node
		b. Go to this left child, i.e., current = current.left
Complexity Analysis
    Time complexity : O(n). 
        To prove that the time complexity is O(n), 
        the biggest problem lies in finding the time complexity of finding the predecessor nodes of all the nodes in the binary tree. 
        Intuitively, the complexity is O(nlogn), because to find the predecessor node for a single node related to the height of the tree. 
        But in fact, finding the predecessor nodes for all nodes only needs O(n) time. 
        Because a binary Tree with n nodes has n−1 edges, the whole processing for each edges up to 2 times, 
        one is to locate a node, and the other is to find the predecessor node. So the complexity is O(n).
    Space complexity : O(n). Arraylist of size n is used.
*/

class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> rst = new ArrayList<>();
        TreeNode curr = root;
        
        while (curr != null) {
            /* 
             * If there is no left subtree, then we can visit this node and
             * continue traversing right.
             */
            if (curr.left == null) {
                rst.add(curr.val);
                // move to next right node
                curr = curr.right;
            } else {    // has a left subtree
                TreeNode pre = getPredecessor (curr);  // get rightmost node
                /* 
                 * If the right subtree is null, then we have never been here before.
                 * the current node should be the right child of pre.
                 */
                if (pre.right == null) {
                    pre.right = curr;
                    curr = curr.left;
                } else {
                /* 
                 * If there is a right subtree, it is a link that we created on a
                 * previous pass, so we should unlink it and visit this node to avoid infinite loops
                 */
                    pre.right = null;
                    rst.add(curr.val);
                    curr = curr.right;
                }
            }
        }
        
        return rst;
    }
    
    // Get the node with the largest value smaller than current node.
    private TreeNode getPredecessor(TreeNode node) {
        TreeNode pre = node.left;
        while (pre.right != null && pre.right != node) {
            pre = pre.right;
        }
        return pre;
    }
}

