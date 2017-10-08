Two Pointers Method
We can use two iterations to do that. 
In the first iteration, we will reset the pointer of one linkedlist to the head of another linkedlist after it reaches the tail node. 
In the second iteration, we will move two pointers until they points to the same node. 
Our operations in first iteration will help us counteract the difference. 
So if two linkedlist intersects, the meeting point in second iteration must be the intersection point. 
If the two linked lists have no intersection at all, 
then the meeting pointer in second iteration must be the tail node of both lists, which is null.

Algorithm Analysis
To see why the above trick would work, consider the following two lists: A = {1,3,5,7,9,11} and B = {2,4,9,11}, 
which are intersected at node '9'. Since B.length (= 4) < A.length (= 6), pB would reach the end of the merged list first, 
because pB traverses exactly 2 nodes less than pA does. 
By redirecting pB to head A, and pA to head B, we now ask pB to travel exactly 2 more nodes than pA would. 
So in the second iteration, they are guaranteed to reach the intersection node at the same time.
If two lists have intersection, then their last nodes must be the same one. 
So when pA/pB reaches the end of a list, record the last element of A/B respectively. 
If the two lists have no intersections, the meeting pointer must be null.

Complexity Analysis
Time complexity : O(m+n).
Space complexity : O(1).

You can get more details here:
https://leetcode.com/articles/intersection-two-linked-lists/

/*
Description
Write a program to find the node at which the intersection of two singly linked lists begins.

Notice
 1. If the two linked lists have no intersection at all, return null.
 2. The linked lists must retain their original structure after the function returns.
 3. You may assume there are no cycles anywhere in the entire linked structure. (important!!!)

Example
The following two linked lists:

A:          a1 → a2
                   ↘
                     c1 → c2 → c3
                   ↗            
B:     b1 → b2 → b3

begin to intersect at node c1.

Challenge 
Your code should preferably run in O(n) time and use only O(1) memory.

Tags 
Linked List
*/

/**
 * Definition for singly-linked list.
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
    /*
     * @param headA: the first list
     * @param headB: the second list
     * @return: a ListNode
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        // boundary check
        if (headA == null || headB == null) {
            return null;
        }
        
        ListNode pA = headA;
        ListNode pB = headB;
        
        // if pA & pB have different len, 
        // then we will stop the loop after second iteration
        while (pA != pB) {
            // for the end of first iteration, 
            // we just redirect the pointer to the head of another linkedlist
            pA = pA == null ? headB : pA.next;
            pB = pB == null ? headA : pB.next;
        }
        
        return pA;
    }
}
