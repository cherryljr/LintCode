/*
Description
Given a linked list and a value x,
partition it such that all nodes less than x come before nodes greater than or equal to x.

You should preserve the original relative order of the nodes in each of the two partitions.

Example
Given 1->4->3->2->5->2->null and x = 3,
return 1->2->2->4->3->5->null.

Tags
Two Pointers Linked List
 */

/**
 * Approach 2: Two Pointers (Dummy Node)
 * 使用到了 两个指针。建立了两个 LinkedList.
 * 将所有 <x 的节点放到了 less 指向的 list 中；
 * 将所有 >=x 的节点放到了 more 指向的 list 中。
 * 然后我们只要将这两个 list 连接起来即可。
 *
 * 在代码的实现上，我们还需要用到 Dummy Node，因为我们这两个 list 的头节点在一开始是不确定的。
 *
 * 时间复杂度为：O(n);  空间复杂度为：O(1)
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
class Solution {
    public ListNode partition(ListNode head, int x) {
        if (head == null) {
            return null;
        }

        ListNode lessDummy = new ListNode(-1);
        ListNode moreDummy = new ListNode(-1);
        ListNode less = lessDummy;
        ListNode more = moreDummy;
        while (head != null) {
            if (head.val < x) {
                // put the smaller node into the less list
                less.next = head;
                less = head;
            } else {
                // put the node into the more list (no less than x)
                more.next = head;
                more = head;
            }
            // move the head to the next node
            head = head.next;
        }

        more.next = null;
        // connect the less list with more list
        less.next = moreDummy.next;
        return lessDummy.next;
    }
}
