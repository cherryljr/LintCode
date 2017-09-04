使用了 分治 的思想。
程序采用 递归 的方法编写即可。因为采用递归的方法，所以我们需要弄清楚退出递归的条件。
思路：
	1. 当 T2 == null 时，不管 T1 是否为 null, 均返回 true；
		 当 T2 != null, T1 == null 时，返回 false;
	2. 比较 T1 和 T2 是否相等。
	3. 看 T2 是否为 T1 的左子树 / 右子树
以上是针对以 isSubtree 方法，这里涉及到了比较两个 subTree 的 isEqual 方法。
该方法同样也是使用了 分治 的思想。
	1. 当 T1 == null || T2 == null 时，看两个节点是否相等即可。
	2. 比较 T1.val 和 T2.val 是否相等，若不相等则返回 false
	3. 继续比较 T1 和 T2 的左右节点
	以此递归下去

/*
You have two every large binary trees: T1, with millions of nodes, and T2, with hundreds of nodes. Create an algorithm to decide if T2 is a subtree of T1.

Example
T2 is a subtree of T1 in the following case:

       1                3
      / \              / 
T1 = 2   3      T2 =  4
        /
       4
T2 isn't a subtree of T1 in the following case:

       1               3
      / \               \
T1 = 2   3       T2 =    4
        /
       4
Note
A tree T2 is a subtree of T1 if there exists a node n in T1 such that the subtree of n is identical to T2. That is, if you cut off the tree at node n, the two trees would be identical.

Tags Expand 
Recursion Binary Tree
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
    /*
     * @param T1: The roots of binary tree T1.
     * @param T2: The roots of binary tree T2.
     * @return: True if T2 is a subtree of T1, or false.
     */
    public boolean isSubtree(TreeNode T1, TreeNode T2) {
        if (T1 == null || T2 == null) {
            return T2 == null;
        }
        
        if (isEqual(T1, T2)) {
            return true;
        } 
        if (isSubtree(T1.left, T2) || isSubtree(T1.right, T2)) {
            return true;
        }
        
        return false;
    }
    
    private boolean isEqual(TreeNode T1, TreeNode T2) {
        if (T1 == null || T2 == null) {
            return T1 == T2;
        }
        if (T1.val != T2.val) {
            return false;
        }
        return isEqual(T1.left, T2.left) && isEqual(T1.right, T2.right);
    }
}