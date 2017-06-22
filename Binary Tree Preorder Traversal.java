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
     * @return: Preorder in ArrayList which contains node values.
     */
    public ArrayList<Integer> preorderTraversal(TreeNode root) {
         Stack<TreeNode> stack = new Stack<TreeNode>();
         ArrayList<Integer> result = new ArrayList<Integer>();
         //Check top
         if (root == null) {
             return result;
         }
         //save root
         stack.push(root);
         //add to result, and load on stack. Right-side at the bottom
         while (!stack.empty()) {
             TreeNode node = stack.pop();
             result.add(node.val);
             if (node.right != null) {
                 stack.push(node.right);
             }
             if (node.left != null) {
                 stack.push(node.left);
             }
         }//while
         
         return result;
    }
}
