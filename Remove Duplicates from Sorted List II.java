 因为head可能发生变换，故要求链表支持可变换的head
 => 使用Dummy Node

/*
26% Accepted
Given a sorted linked list, delete all nodes that have duplicate numbers, 
leaving only distinct numbers from the original list.

Example
Given 1->2->3->3->4->4->5, return 1->2->5.
Given 1->1->1->2->3, return 2->3.

Tags Expand 
Linked List

Thinking process:
Create a dummyHead
User a pointer node to run through the list
Similar to Remove Duplicates from Sorted List I, but checking next and next.next
If there is a match on head.next && head.next.next, delete head.next node and link to next ones until a different node appear
return dummyHead.next
*/


/**
 * Definition for ListNode
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    /**
     * @param ListNode head is the head of the linked list
     * @return: ListNode head of the linked list
     */
    public static ListNode deleteDuplicates(ListNode head) {
        // write your code here
        if (head == null || head.next == null) {
            return head;
        }
        
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        //	重复利用了head将其指向了dummy，也可以重新new一个node，不过这样做更节省空间
        head = dummy;
        
        while (head.next != null && head.next.next != null) {
            if (head.next.val == head.next.next.val) {
            		//	因为需要将重复的节点全部删除，故使用duplicateVal来记录重复值以便与后面的节点值进行比较
                int duplicateVal = head.next.val;
                while (head.next != null && head.next.val == duplicateVal) {
                    head.next = head.next.next;
                }
            } else {
                head = head.next;
            }
        }
        
        return dummy.next;
    }
}
