Template 1: Traverse
//	通常不返回数值
//	传入参数，调用
public class Solution {
    public void traverse(TreeNode root) {
        if (root == null) {
            return;
        }
        // do something with root
        traverse(root.left);
        // do something with root
        traverse(root.right);
        // do something with root
    }
}


Tempate 2: Divide & Conquer
//	有返回值（有可能返回多个参数，如Binary Tree Maximum Path Sum）
//	先分后治，注意临界值与异常的处理
public class Solution {
    public ResultType traversal(TreeNode root) {
        // null or leaf
        if (root == null) {
            // do something and return;
        }
        
        // Divide
        ResultType left = traversal(root.left);
        ResultType right = traversal(root.right);
        
        // Conquer
        ResultType result = Merge from left and right.
        return result;
    }
}