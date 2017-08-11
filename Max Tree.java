该题与 Largest Rectangle in Histogram 所用到的知识点相同。

1. 自上向下的方法 
递归查找数组中最大的节点作为 root, 
然后分别将 root 左边和右边的元素传递下去，递归找出左边和右边的最大节点。
直到构建完毕。该方法复杂度为 O(N^2)

2. 通过观察我们可以发现：如果采用自下而上的方法。
一个结点的父节点是离它最近的一个大于该节点的数，如果相邻的两个节点均大于该结点，
则取较小者。原因可以通过举例分析得到。若取较大者，则无法构建 Max Tree.
这样问题就转换成了在一个Arrays中寻找一个结点距离它最近的一个，大于它的节点。
该问题可以通过 Stack 以O(N)的时间复杂度巧妙地解决。

/*
Given an integer array with no duplicates. A max tree building on this array is defined as follow:

The root is the maximum number in the array
The left subtree and right subtree are the max trees of the subarray divided by the root number.
Construct the max tree by the given array.

Example
Given [2, 5, 6, 0, 3, 1], the max tree constructed by this array is:

    6
   / \
  5   3
 /   / \
2   0   1
Challenge
O(n) time and memory.

Tags Expand 
LintCode Copyright Stack Cartesian Tree
*/

/*
Good explain here:http://blog.welkinlan.com/2015/06/29/max-tree-lintcode-java/
*/

public class Solution {
    public TreeNode maxTree(int[] A) {
    	if (A == null || A.length == 0) {
    		return null;
    	}
    	
    	Stack<TreeNode> stack = new Stack<TreeNode>();
    	
    	for (int i = 0; i < A.length; i++) {
    		TreeNode node = new TreeNode(A[i]);
    		while (!stack.isEmpty() && node.val >= stack.peek().val) {
    			node.left = stack.pop();
    		}
    		if (!stack.isEmpty()) {
    			stack.peek().right = node;
    		}
    		stack.push(node);
    	}

    	TreeNode rst = stack.pop();
    	while(!stack.isEmpty()) {
    		rst = stack.pop();
    	}
    	return rst;
    }
}