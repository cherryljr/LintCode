因为head无法确定，若使用if语句，会导致使用过多的if语句。
故这里使用Dummy Node解决问题

遍历到M前，
存一下那个点，
从M开始， for loop， reverse [m~n]。 然后把三段链接在一起。

/*
28% Accepted
Reverse a linked list from position m to n.

Note
Given m, n satisfy the following condition: 1 = m = n = length of list.

Example
Given 1->2->3->4->5->NULL, m = 2 and n = 4, return 1->4->3->2->5->NULL.

Challenge
Reverse it in-place and in one-pass

Tags Expand 
Linked List

Thinking process:
0. Use dummy node to store head
1. Find mNode, store the front nodes
2. Reverse from mNode to nNode
3. Link front, middle, end nodes together
Note, when doing reverse, always:
    - reversedList = 1st item
    - postNode = 2nd item   
    - store 3rd item in temp: temp = postNode.next
*/

/**
 * Definition for ListNode
 * public class ListNode {
 *     int val;
 *     ListNode next;
 * }
 */
public class Solution {
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (m >= n || head == null) {
            return head;
        }
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        head = dummy;
        
        for (int i = 1; i < m; i++) {
            if (head == null) {
                return null;
            }
            head = head.next;
        }
        
        ListNode premNode = head;
        ListNode mNode = head.next;
        ListNode nNode = mNode, postnNode = mNode.next;
        for (int i = m; i < n; i++) {
            if (postnNode == null) {
                return null;
            }
            ListNode temp = postnNode.next;
            postnNode.next = nNode;
            nNode = postnNode;
            postnNode = temp;
        }
        //	Connect three sections: [0 ~ m-1], [m ~ n], [n+1 ~ size]
        mNode.next = postnNode;
        premNode.next = nNode;
        
        return dummy.next;
    }
}