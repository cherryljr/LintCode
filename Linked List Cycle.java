O(1) sapce: 用快慢指针。一个一次移动一个节点, 一个一次移动两个节点。 总有一次，fast会因为cycle而追上slow。
	具体证明以用数学归纳法来思考。
	首先，由于链表是个环，所以相遇的过程可以看作是快指针从后边追赶慢指针的过程。
	那么做如下思考：
	1. 快指针与慢指针之间差一步。此时继续往后走，慢指针前进一步，快指针前进两步，两者相遇。
	2. 快指针与慢指针之间差两步。此时唏嘘往后走，慢指针前进一步，快指针前进两步，两者之间相差一步，转化为第一种情况。
	3. 快指针与慢指针之间差N步。此时继续往后走，慢指针前进一步，快指针前进两步，两者之间相差(N+1-2) => N-1步。
	因此得证: 快指针必然与慢指针相遇。又因为快指针速度是慢指针的两倍，所以相遇时必然只绕了一圈。

O(n) space: 用HashMap，一直add elements.  如果有重复，那么很显然是有Cycle 

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
        slow = head;
        fast = head.next;

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
