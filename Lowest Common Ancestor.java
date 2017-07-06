三种情况：   
1. A,B都找到，那么这个level的node就是其中一层的parent。其实，最先recursively return到的那个，就是最底的LCA parent.   
2. A 或者 B 被找到，那就还没有公共parent,return 非null得那个。   
3. A B 都null, 那就说明没有找到, return null


/*

Given the root and two nodes in a Binary Tree. Find the lowest common ancestor(LCA) of the two nodes.

The lowest common ancestor is the node with largest depth which is the ancestor of both nodes.

Example
        4

    /     \

  3         7

          /     \

        5         6

For 3 and 5, the LCA is 4.

For 5 and 6, the LCA is 7.

For 6 and 7, the LCA is 7.

Tags Expand 
Binary Tree LintCode Copyright


*/

/*
  Thoughts:
  To correctly understand this approach when there is not 'parent' atribute available in node.

  We divide and coquer (in this case DFS) into 2 branches, and we are actually asking each node to check:
    May I have a leaf child of nodeA (could be futher down in the tree)?
    May I have a leaf child of nodeB (could be futher down in the tree)?
  1. If I have leaf child of A && B, then i'm the deepest parent in line! Return.
  2. If I only have A, or B: mark myself as an ancestor of A or B.
  3. If I don't have leaf child of A nor B, I'm not an ancestor, failed, return null.

  After the common ancestor is found at any deep level, and returned itself to parent level,
    we can assume other branches must be null (because they are not ancestor, since we are),
    then the this common ancestor node will be passed to highest level.
  
*/
public class Solution {
    // 在root为根的二叉树中找A,B的LCA:
    // 如果找到了就返回这个LCA
    // 如果只碰到A，就返回A
    // 如果只碰到B，就返回B
    // 如果都没有，就返回null
    private TreeNode hepler(TreeNode root, TreeNode A, TreeNode B) {
    	  //	根据定义，当root == 是A或B其中任意一个时，说明其为LCA。如为null，则同样返回nulls
        if (root == null || root == A || root == B) {
            return root;
        }
        
        // Divide
        TreeNode left = lowestCommonAncestor(root.left, A, B);
        TreeNode right = lowestCommonAncestor(root.right, A, B);
        
        // Conquer
        // A,B就在root左右子树中，即root就是LCA
        if (left != null && right != null) {
            return root;
        } 
        // A,B在左子树中
        if (left != null) {
            return left;
        }
        // A,B在右子树种
        if (right != null) {
            return right;
        }
        return null;
    }
    
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode A, TreeNode B) {
        // write your code here
        if (A == null || B == null) {
            return null;
        }
        return hepler(root, A, B);
    }
}