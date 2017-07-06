/* 
Given a binary tree, return the zigzag level order traversal of its nodes' values. 
(ie, from left to right, then right to left for the next level and alternate between).

Example
Given binary tree {3,9,20,#,#,15,7},

    3
   / \
  9  20
    /  \
   15   7
 

return its zigzag level order traversal as:

[
  [3],
  [20,9],
  [15,7]
]
Tags Expand 
Tree Search Breadth First Search Queue Binary Tree

*/


//	version1: 根据层数的奇偶判断level中的元素是否需要reverse,然后再将level添加到rst中

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
     * @return: A list of lists of integer include 
     *          the zigzag level order traversal of its nodes' values 
     */
    public ArrayList<ArrayList<Integer>> zigzagLevelOrder(TreeNode root) {
        // write your code here
        ArrayList<ArrayList<Integer>> rst = new ArrayList<>();
        
        if (root == null) {
            return rst;
        }
        
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        int count = 1;
        
        while (!queue.isEmpty()) {
            ArrayList<Integer> level = new ArrayList<Integer>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }    
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            if (count % 2 == 0) {
                Collections.reverse(level);
            }
            count++;
            
            rst.add(level);
        }
        
        return rst;
    }
}


//	version2: 根据层数的奇偶，直接将值添加在level的不同位置上。与version1相比差距不大，少了reverse这一步。

public class Solution {
    /**
     * @param root: The root of binary tree.
     * @return: A list of lists of integer include 
     *          the zigzag level order traversal of its nodes' values 
     */
    public ArrayList<ArrayList<Integer>> zigzagLevelOrder(TreeNode root) {
        // write your code here
        ArrayList<ArrayList<Integer>> rst = new ArrayList<>();
        
        if (root == null) {
            return rst;
        }
        
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        int count = 1;
        
        while (!queue.isEmpty()) {
            ArrayList<Integer> level = new ArrayList<Integer>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (count % 2 == 0) {
                    level.add(0, node.val);
                } else {
                    level.add(node.val);
                }
                if (node.left != null) {
                    queue.offer(node.left);
                }    
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            count++;
            rst.add(level);
        }
        
        return rst;
    }
}


//	version3: Use two stack

 /*
Thought:
1. realize: queue is no longer can be used. draw a example map to see why.
Instead, use 2 stacks.
Because we can only take the top of stack, and we are constantly adding to the top of the stac, so we need 2 stacks. 
One is the current one, will be empty every time when we finish the level. 
The other one is nextLevel, which holds next level’s nodes temporarily.
2. Use a boolean to track if which level it’s running at.
 */
public class Solution {
    /**
     * @param root: The root of binary tree.
     * @return: A list of lists of integer include 
     *          the zigzag level order traversal of its nodes' values 
     */
    public ArrayList<ArrayList<Integer>> zigzagLevelOrder(TreeNode root) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        if (root == null) {
            return result;
        }
        
        Stack<TreeNode> currentLevel = new Stack<TreeNode>();
        Stack<TreeNode> nextLevel = new Stack<TreeNode>();
        
        currentLevel.push(root);
        boolean regularOrder = false;
        
        while (!currentLevel.empty()) {
            ArrayList<Integer> list = new ArrayList<Integer>();
            
            while (!currentLevel.empty()) {
                TreeNode temp = currentLevel.pop();
                list.add(temp.val);
                if (regularOrder) {
                    addLevel(nextLevel, temp.right);
                    addLevel(nextLevel, temp.left);
                } else {
                    addLevel(nextLevel, temp.left);
                    addLevel(nextLevel, temp.right);
                }
            }
            result.add(list);
            regularOrder = !regularOrder;
            Stack<TreeNode> tmp = currentLevel;
            currentLevel = nextLevel;
            nextLevel = tmp;
        }
        return result;
    }
    
    public void addLevel(Stack<TreeNode> level, TreeNode node) {
        if (node != null) {
            level.push(node);
        }
    }
}
