2 stack的做法非常令人惊喜  
   stack1和stack2合作。倒水。记这个做法。。。挺神奇的。

Divide and Conquer 的recursive方法也非常明了！

注意，这些binary tree traversal的题目，常常有多个做法:recursive or iterative

```
/*
Binary Tree Postorder Traversal
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

Tags Expand 
Binary Tree

*/

//	Divide & Conquer
/*

Thinking process:
1. Resursive: （divide and conquer）
    rec on left node
    rec on right node
    rst.addAll(left)
    rst.addAll(right)
    rst.add(curr)

*/
public class Solution {
    /**
     * @param root: The root of binary tree.
     * @return: Postorder in ArrayList which contains node values.
     */
    public ArrayList<Integer> postorderTraversal(TreeNode root) {
        ArrayList<Integer> rst = new ArrayList<Integer>();
        if (root == null) {
            return rst;
        }
        //Recursive:
        ArrayList<Integer> right = postorderTraversal(root.right);
        ArrayList<Integer> left = postorderTraversal(root.left);
        rst.addAll(left);
        rst.addAll(right);
        rst.add(root.val);  
        return rst;
    }
}


// 1. Non-recursive, iterative
public ArrayList<Integer> postorderTraversal(TreeNode root) {
    ArrayList<Integer> result = new ArrayList<Integer>();
    Stack<TreeNode> stack = new Stack<TreeNode>();
    TreeNode prev = null; // previously traversed node
    TreeNode curr = root;

    if (root == null) {
        return result;
    }

    stack.push(root);
    while (!stack.empty()) {
        curr = stack.peek();
        if (prev == null || prev.left == curr || prev.right == curr) { // traverse down the tree
            if (curr.left != null) {
                stack.push(curr.left);
            } else if (curr.right != null) {
                stack.push(curr.right);
            }
        } else if (curr.left == prev) { // traverse up the tree from the left
            if (curr.right != null) {
                stack.push(curr.right);
            }
        } else { // traverse up the tree from the right
            result.add(curr.val);
            stack.pop();
        }
        prev = curr;
    }

    return result;
}

/*
    2. Non-recursive, iterative
    use 2 stacks: pull water from s1 into s2
    in s2, we want: at each level, parentNode at bottom, then rightNode, then leftNode
    loop through s2, then we print out leftNode, rightNode, parentNode ... in postOrder.
*/
public class Solution {
    /**
     * @param root: The root of binary tree.
     * @return: Postorder in ArrayList which contains node values.
     */
    public ArrayList<Integer> postorderTraversal(TreeNode root) {
        ArrayList<Integer> rst = new ArrayList<Integer>();
        if (root == null) {
            return rst;
        }
        //Non-recursive:
        Stack<TreeNode> s1 = new Stack<TreeNode>();
        Stack<TreeNode> s2 = new Stack<TreeNode>();
        s1.push(root);
        while (!s1.empty()) {
            TreeNode curr = s1.pop();
            s2.push(curr);
            if (curr.left != null) {
                s1.push(curr.left);
            }
            if (curr.right != null) {
                s1.push(curr.right);
            }
        }
        while (!s2.empty()) {
            rst.add(s2.pop().val);
        }
        return rst;
    }
}


/*
	Recursive with helper method.
*/
public class Solution {
    /**
     * @param root: The root of binary tree.
     * @return: Postorder in ArrayList which contains node values.
     */
    public ArrayList<Integer> postorderTraversal(TreeNode root) {
        // write your code here
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
            helper(rst, node.right);
            rst.add(node.val);
        }
    }
}


```