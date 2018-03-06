/*
Description
Implement a function to check if a linked list is a palindrome.

Example
Given 1->2->1, return true

Challenge
Could you do it in O(n) time and O(1) space?
 */

/**
 * Approach 1: Using Stack (n/2 extra space)
 * 看到判断是否为回文，最简单的方法就是将整个字符串逆序过来然后进行对比即可。
 * 对于 数组 形式，我们可以直接通过下标访问的方法，对比头尾即可。
 * 但是本题为 链表 形式。
 * 最简明暴力的方法就是开辟大小为 n 的额外空间来存储逆序后的字符串，然后进行对比即可。
 * 至于逆序，我们可以通过 Stack 这个数据结构来实现。（进出一次就实现了逆序）
 * 
 * 这里提供一个优化过的方法，我们比较的是字符串的前半部分与后半部分，
 * 因此实际上我们没有必要把整个字符串进行一个逆序操作，
 * 我们只需要对字符串的 后半部分 进行一个逆序操作即可。
 * 然后将 后半部分 与 前半部分 进行比较即可。
 * 而找链表 中点 的方法，则可以通过 快慢指针 来实现。
 * 
 * 时间复杂度为：O(n)； 空间复杂度为：O(n/2) => O(n)
 */

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    /*
     * @param head: A ListNode.
     * @return: A boolean.
     */
    public boolean isPalindrome(ListNode head) {
        if (head == null) {
            return false;
        }

        Stack<ListNode> stack = new Stack<>();
        ListNode mid = getMid(head);
        // 将链表后半部分的元素入栈以实现逆序
        while (mid != null) {
            stack.push(mid);
            mid = mid.next;
        }

        // 将链表中的元素取出来，依次与链表的前半部分进行比较
        while (!stack.isEmpty()) {
            if (head.val != stack.pop().val) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    private ListNode getMid(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
}

/**
 * Approach 2: Two Pointers (Without Using Extra Space)
 * 根据 Approach 1 中的分析，我们可以知道：
 * 实际上我们只需要将 链表的后半段 进行逆序即可。
 * 那么对于 链表的逆序操作，实际上我们并不需要使用 Stack 来实现。
 * 通过使用 DummyNode 我们便可以在 额外空间O(1) 的情况下实现链表的逆序。
 *
 * 关于链表的逆序可以进一步参考：
 * https://github.com/cherryljr/LintCode/blob/master/Reverse%20Linked%20List%20II%20.java
 */
public class Solution {
    /*
     * @param head: A ListNode.
     * @return: A boolean.
     */
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        ListNode n1 = head;
        ListNode n2 = head;
        // get mid node
        while (n2.next != null && n2.next.next != null) {
            n1 = n1.next; // n1 -> mid
            n2 = n2.next.next; // n2 -> end
        }

        n2 = n1.next; // n2 -> right part first node
        n1.next = null; // mid.next -> null
        ListNode n3 = null;
        while (n2 != null) { // right part convert
            n3 = n2.next; // n3 -> save next node
            n2.next = n1; // next of right node convert
            n1 = n2; // n1 move
            n2 = n3; // n2 move
        }
        n3 = n1; // n3 -> save last node
        n2 = head;// n2 -> left first node

        boolean res = true;
        while (n1 != null && n2 != null) { // check palindrome
            if (n1.val != n2.val) {
                return false;
            }
            n1 = n1.next; // left to mid
            n2 = n2.next; // right to mid
        }

        // recover list, it means reversing the post segment again
        // As from solving the problem, we don't need do this (of course)
        // But it's a good habit for coding.
        n1 = n3.next;
        n3.next = null;
        while (n1 != null) {
            n2 = n1.next;
            n1.next = n3;
            n3 = n1;
            n1 = n2;
        }
        return true;
    }
}