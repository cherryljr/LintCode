等于遍历了所有k1<= x <= k2的x node。

如果是用Binary Search Tree搜索，那么一般是if (...) else {...}，也就是一条路走到底，直到找到target.

这里, 把 left/right/match的情况全部cover了，然后把k1,k2的边框限制好，中间就全部遍历了。

```
/*

Given two values k1 and k2 (where k1 < k2) and a root pointer to a Binary Search Tree. 
Find all the keys of tree in range k1 to k2. 
i.e. print all x such that k1<=x<=k2 and x is a key of given BST. Return all the keys in ascending order.

Example
For example, if k1 = 10 and k2 = 22, then your function should print 12, 20 and 22.

          20

       /        \

    8           22

  /     \

4       12

Tags Expand 
Binary Tree Binary Search Tree
Recursive

Thinking Process:
Find lowest and turn around.
  If don’t hit the ground, keep digging:
  if (root.val > k1) dig into root.left
Check current
Find maximum and turn around.
  If don’t hit the ceiling, keep climbing:
  if (root.val < k2) climb to root.right

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
    private ArrayList<Integer> results;
    /**
     * @param root: The root of the binary search tree.
     * @param k1 and k2: range k1 to k2.
     * @return: Return all keys that k1<=key<=k2 in increasing order.
     */
    public ArrayList<Integer> searchRange(TreeNode root, int k1, int k2) {
        results = new ArrayList<Integer>();
        helper(root, k1, k2);
        return results;
    }
    
    private void helper(TreeNode root, int k1, int k2) {
        if (root == null) {
            return;
        }
        if (root.val > k1) {
            helper(root.left, k1, k2);
        }
        if (root.val >= k1 && root.val <= k2) {
            results.add(root.val);
        }
        if (root.val < k2) {
            helper(root.right, k1, k2);
        }
    }
}