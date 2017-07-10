首先因为head无法确定，故利用到了Dummy Node.

其次，为了删除倒数第N个节点，我们使用了快慢指针来获取其倒数第第N+1个节点，
即所要删除节点的前一个点。这样才能完成删除操作。

本题解题难点在于快慢指针从哪开始移动，移动几个点。
遇到这种问题，我们只需要带入一组简单的数据实测一边便可以得到正确的结果。如删除倒数第三个节点：1-> 2-> 3 -> null
链表需要多画图，(null也要还画来)，这样有助于我们理解和思考。
切不可只空想。这样不仅解题速度慢，还容易出错。

/*
Given a linked list, remove the nth node from the end of list and return its head.

Note
The minimum number of nodes in list is n.

Example
Given linked list: 1->2->3->4->5->null, and n = 2.
After removing the second node from the end, the linked list becomes 1->2->3->5->null.
Challenge
O(n) time

Tags Expand 
Two Pointers Linked List

*/

/**
 * Definition for ListNode.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int val) {
 *         this.val = val;
 *         this.next = null;
 *     }
 * }
 */ 
public class Solution {
    /**
     * @param head: The first node of linked list.
     * @param n: An integer.
     * @return: The head of linked list.
     */
    ListNode removeNthFromEnd(ListNode head, int n) {
        // write your code here
        if (n <= 0) {
            return head;
        }
        
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode preDel = dummy;
        
        for (int i = 0; i < n; i++) {
            if (head == null) {
                return null;
            }
            head = head.next;
        }
        while (head != null) {
            head = head.next;
            preDel = preDel.next;
        }
        
        preDel.next = preDel.next.next;
        return dummy.next;
    }    
}
