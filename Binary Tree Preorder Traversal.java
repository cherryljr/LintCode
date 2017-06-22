1. recursive with helper method   
2. Stack(NON-recursive) push curr, push right, push left.   


```
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
//Recommend way: using a stack
//Recursive way can be seen here: http://www.ninechapter.com/solutions/binary-tree-preorder-traversal/

*/

/*

    Draw a few nodes and will realize to use stack
        Cannot use queue, because whatever added on it first, will first process. 
        That means if we add curr,left,right; they will be processed first... but we want to traverse all left nodes first.
    IN FACT: binary tree traversal are all using stack...
*/

//recursive
public class Solution {
    public ArrayList<Integer> preorderTraversal(TreeNode root) {
        ArrayList<Integer> rst = new ArrayList<Integer>();
        if (root == null) {
            return rst;
        }   
        helper(rst, root);
        return rst;
    }

    public void helper(ArrayList<Integer>rst, TreeNode node){
        if (node != null) {
            rst.add(node.val);
            helper(rst, node.left);
            helper(rst, node.right);
        }
    }
}


/*
Thinking process:
Check if root is null
use a container to save results

use current node
put right on stack
put left on stack
4. In next run, the ‘left’ will be on top of stack, and will be taken first. So the order becomes: parent -> left -> right

This method just like : "Binary Search Tree Iterator.java"
The code that how to Traversa the tree is very worth to learn. How smart way!!!

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
            TreeNode r = stack.pop();
            curr = r.right;
            rst.add((Integer)r.val);
        }
          
        return rst;
    } 
}