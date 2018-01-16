方法一：HashMap
HashMap很容易便能够实现，但需要O(n)的额外空间.

方法二：快慢指针
O(1)的额外空间
这边要求返回能够返回环的入口。
故，做法与Linked List Cycle相同。
	1. 使用slow和fast进行移动，当slow == fast时跳出循环。表明链表中存在环。
	2. 将slow移动回到head，fast保持不动.继续移动，但是此时移动方式方式变换，
	二者均为一次仅移动一步.（程序中是令head进行移动，而slow(intersect)从相遇点开始移动，效果是相同的）
	3. 当slow再次遇上fast时，该节点便是环的入口
	（这里的证明与相关扩展可以参考：http://www.cnblogs.com/hiddenfox/p/3408931.html）

/*
Given a linked list, return the node where the cycle begins.

If there is no cycle, return null.

Example
Given -21->10->4->5, tail connects to node index 1，return 10

Challenge
Follow up:

Can you solve it without using extra space?

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

// Approach 1: HashMap
public class Solution {
    public ListNode detectCycle(ListNode head) {  
        if (head == null) {
            return null;
        }

        HashMap<ListNode, Integer> map = new HashMap<ListNode, Integer>();//Does not matter of the value
        while (head != null) {
            if (map.containsKey(head)) {
                return head;
            } else {
                map.put(head, head.val);
            }
            head = head.next;
        }
        
        return null;
    }
}


// Approach 2: Slow/Fast Pointer
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
     * @return: The node where the cycle begins. 
     *           if there is no cycle, return null
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        
        // If there is a cycle, the fast/slow pointers will intersect at some node. 
        // Otherwise, there is no cycle, so we cannot find an entrance to a cycle.
        ListNode intersct = getIntersect(head);
        if (intersct == null) {
            return null;
        }
        
        // To find the entrance to the cycle, we have two pointers traverse at
        // the same speed -- one from the front of the list;
        // and the other from the point of intersection.
        while (head != intersct) {
            head = head.next;
            intersct = intersct.next;
        }
        return head;
    }
    
    // The same method as: "Linked List Cycle"
    // Get the point where slow meet fast.
    // If no cycle in the list, return null.
    private ListNode getIntersect(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast != null && fast.next != null) { 
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return slow;
            }
        }
        return null;
    }
}

