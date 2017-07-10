由题意可知：reorder之后的链表最后的节点为原来的middle.
故我们可以推导出解法如下：
	1. 寻找到链表的middle point.
	2. 将链表的后半段进行一个reverse.
	3. split list,从中点将链表断开.
	4. 根据index来merge链表的前半段和后半段

/*
24% 通过
Given a singly linked list L: L0→L1→…→Ln-1→Ln,
reorder it to: L0→Ln→L1→Ln-1→L2→Ln-2→…

You must do this in-place without altering the nodes' values.

样例
For example,
Given 1->2->3->4->null, reorder it to 1->4->2->3->null.

标签 Expand 
Linked List

Thinking Process:
Similar to sort list: 
find middle.
reverse last section
merge(head, mid) alternatively by using index % 2.
Append whatever left from the 2 lists
Note: re-order in place, does not necessarily mean you can create any variable. As long as the variable is O(1), it should be fine.
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
     * @param head: The head of linked list.
     * @return: void
     */
     
    private ListNode findMid(ListNode head) {
        ListNode slow = head;
        ListNode fast = head.next;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return slow;
    }
    
    private ListNode reverse(ListNode head) {
        ListNode preNode = null;
        
        while (head != null) {
            ListNode temp = head.next;
            head.next = preNode;
            preNode = head;
            head = temp;
        }
        
        return preNode;
    }
    
    private void merge(ListNode head1, ListNode head2) {
        ListNode dummy = new ListNode(-1);
        int index = 0;
        
        while (head1 != null && head2 != null) {
            if (index % 2 == 0) {
                dummy.next = head1;
                head1 = head1.next;
            } else {
                dummy.next = head2;
                head2 = head2.next;
            }
            dummy = dummy.next;
            index++;
        }
        
        if (head1 != null) {
            dummy.next = head1;
        }
        
        if (head2 != null) {
            dummy.next = head2;
        }
    }
    
    public void reorderList(ListNode head) {  
        // write your code here
        if (head == null || head.next == null) {
            return ;
        }
        
        ListNode mid = findMid(head);
        ListNode tail = reverse(mid.next);
        mid.next = null;
        
        merge(head, tail);
    }
}
