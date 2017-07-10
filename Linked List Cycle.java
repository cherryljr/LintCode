O(1) sapce: 用快慢指针。一个跑.next, 一个跑.next.next。 总有一次，fast会因为cycle而追上slow。

O(n) space: 用HashMap，一直add elements.  如果有重复，那么很显然是有Cycle 
```
/*
50% Accepted
Given a linked list, determine if it has a cycle in it.

Example
Challenge
Follow up:
Can you solve it without using extra space?

Tags Expand 
Linked List Two Pointers

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
    public Boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }

        ListNode fast, slow;
        fast = head.next;
        slow = head;
        while (fast != slow) {
            if(fast==null || fast.next==null)
                return false;
            fast = fast.next.next;
            slow = slow.next;
        } 
        return true;
    }
}


/*
Thinking process:
If there is a cycle, then slow pointer and fast pointer will meet at some point.
Beause slow pointer move one step once, and fast pointer move two steps once.
if there is a cycle, the distance between slow and fast will decrease 1 node after one move,
so they will meet at the end.

However, we cannot simply compare slow.val == fast.val.
This is because, there can be different link node with same value, but they are stored on different index, 
so they should not be cycle.
*/
