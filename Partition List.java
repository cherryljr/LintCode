建造两个list,并使用两个指针left和right跟踪其当前位置

把满足条件（<x, >=x）的数字分别放到两个list里面

记得用dummyNode track head.

最终使用left.next = rightDummy.next将两个Lsit连接起来。

/*
33% Accepted
Given a linked list and a value x, 
partition it such that all nodes less than x come before nodes greater than or equal to x.

You should preserve the original relative order of the nodes in each of the two partitions.

For example,
Given 1->4->3->2->5->2->null and x = 3,
return 1->2->2->4->3->5->null.

Example
Tags Expand 
Linked List Two Pointers
*/

/*
Thinking process:
0. dummyPre, dummyPost to store the head of the 2 list
1. Append node.val < x to listPre
2. Append node.val >= x to listPost
3. Link them togeter
4. return dummyPre.next
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
     * @param x: an integer
     * @return: a ListNode 
     */
    public ListNode partition(ListNode head, int x) {
        // write your code here
        if (head == null || head.next == null) {
            return head;
        }
        
        ListNode leftDummy = new ListNode(-1);
        ListNode rightDummy = new ListNode(-1);
        ListNode left = leftDummy;
        ListNode right = rightDummy;
        
        while (head != null) {
            if (head.val < x) {
                left.next = head;
                left = head;
            } else if (head.val >= x) {
                right.next = head;
                right = head;
            }
            head = head.next;
        }
        //	right.next是链表的末尾，要记得置空
        right.next = null;
        left.next = rightDummy.next;
        return leftDummy.next;
    }
}
