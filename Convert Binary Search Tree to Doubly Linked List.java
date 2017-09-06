其实就是 Binary Tree Inorder Traversal 的 非递归实现方法(Stack) 的变种题。
在此基础上添加了 双向链表的考点。

Note:
	利用 Stack 实现 Tree 的 inorder非递归遍历，得到 中序遍历。
	然后利用将各个节点用双向链表连接起来即可。（无非就是设置好 prev 和 next 指针）
	因此使用中间量 dNode 和 temp 来连接双向链表
	但是因为 DoublyList 的头节点无法确定，故使用了 DummyNode.

/*
Description
Convert a binary search tree to doubly linked list with in-order traversal.

Example
Given a binary search tree:
    4
   / \
  2   5
 / \
1   3
return 1<->2<->3<->4<->5

Tags 
Linked List
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
 * Definition for Doubly-ListNode.
 * public class DoublyListNode {
 *     int val;
 *     DoublyListNode next, prev;
 *     DoublyListNode(int val) {
 *         this.val = val;
 *         this.next = this.prev = null;
 *     }
 * }
 */ 
public class Solution {
    /**
     * @param root: The root of tree
     * @return: the head of doubly list node
     */
    public DoublyListNode bstToDoublyList(TreeNode root) {  
        if (root == null) {
            return null;
        }
        
        DoublyListNode dummy = new DoublyListNode(-1);
        DoublyListNode dNode = dummy;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            DoublyListNode temp = new DoublyListNode(curr.val);
            dNode.next = temp;
            temp.prev = dNode;
            dNode = dNode.next;
            curr = curr.right;
        }
        
        return dummy.next;
    }
}
