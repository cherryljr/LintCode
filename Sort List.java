/*
Description
Sort a linked list in O(n log n) time using constant space complexity.

Example
Given 1->3->2->null, sort it to 1->2->3->null.

Challenge 
Solve it by merge sort & quick sort separately.

Tags 
Linked List
Related Problems 
 */

/**
 * Intuition:
 * 这是一个排序问题，要求时间复杂度为O(nlogn),空间复杂度为常数。
 * 因此首先我们需要考虑的就是选取一个合适的排序方法。
 * 经过分析，我们主要讨论 快速排序 和 归并排序 两种方法（它们的实质都是 分治思想）
 * 接下来我们分别对二者进行分析。
 *
 * Merge Sort:
 *  1. 首先我们需要找到 中点 作为划分点，find middle. 使用到了 快慢指针 的知识点
 *  slow, fast指向head。然后slow一次走一步, fast一次走两步。当fast走到链表的终点的时候，slow就处于链表的中间位置了。
 *  2. Sort: 切开两半，先sort后半段,再sort前半段（递归调用）。
 *  注意：在sort后半段之后，要将 mid.next = null 使得链表断开。不然 head 指向的仍然是整个链表
 *  3. Merge: list A, B 已经是sorted, 然后按照大小 merge 起来即可。
 *  （可以参考 Merge Two Sorted List: https://github.com/cherryljr/LintCode/blob/master/Merge%20Two%20Sorted%20List.java）
 *
 * 关于利用 快慢指针 寻找中点：
 *  注意本题的代码实现：代码中我们保存了一个 pre 节点，它表示的是 slow 的前一个节点。
 *  如果我们返回的是 slow 的话，在 slow.next = null 之后，如果链表被划分到只剩下 2个 节点时，
 *  slow 会一直指向 第二个节点（末尾节点），这样就会导致最后这两个节点无法被 Divide,从而陷入无限循环，导致 Stack Overflow.
 *  关于这个，其实有更加简单的方法：
 *      在初始化 slow,fast 的时候，我们将 slow = head, fast = head.next 即可。
 *      此时我们得到的 slow 就是链表的中点（靠前），为什么说靠前呢？
 *      这是因为当链表节点为偶数个时，它返回的是第 size/2 个节点.
 *      并且当只有 2个 节点时，它就不会陷入无限循环的情况了.
 *  当我们将初始化改为：slow = head, fast = head 的时候。
 *      我们得到的 slow 就是链表的中点（靠后），
 *      同理即 当链表节点为偶数个时，它返回的是第 size/2 + 1 个节点.
 *  具体代码见下方，因此我们可以根据情况的不同选择不同的实现方式。
 *  （然而就实际应用而已我们基本都是采用 slow = head, fast = head. 的方法，如果需要取之前的一个节点，那么使用 pre 即可）
 *
 * Quick Sort:
 * 快速排序本身并不适合用在list上面。虽然这里也实现了它但是并不推荐大家使用它。
 * 因为其随机性，故其最坏情况为O(n²)。
 * 并且Quick Sort需要进行很多random access，但是LinkedList没办法像ArrayList一样 通过index直接进行访问 而是要从head开始遍历。
 *
 * 当应用在Array的时候Quick Sort虽然拥有O(logn)额外空间的优点。
 * 但是得益于LinkedList的特点：其插入操作的时间复杂度和空间复杂度均为：O(1).
 * 故Merge Sort中原本需要 O(n) 额外空间的merge操作只需要 O(1) 便可以完成.
 *
 * 综上所述，
 * Quick Sort不建议用在list上面。
 * LinkedList更适合Merge Sort, ArrayList更适合Quick Sort.
 * 具体分析可以参见如下网页：
 * http://www.geeksforgeeks.org/why-quick-sort-preferred-for-arrays-and-merge-sort-for-linked-lists/
 */

/**
 * Approach 1: Merge Sort (Divide and Conquer)
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
     * @param head: The head of linked list.
     * @return: You should return the head of the sorted linked list, using constant space complexity.
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode mid = findPreMid(head);
        // Sort the right part of mid (recursive call)
        ListNode right = sortList(mid.next);
        // Split the list from middle node
        mid.next = null;
        // Sort the left part of mid (recursive call)
        ListNode left = sortList(head);

        return merge(left, right);
    }

    private ListNode findPreMid(ListNode head) {
        ListNode pre = null;    // store the previous node of slow
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            pre = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        return pre;
    }

    private ListNode findMiddle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    private ListNode merge(ListNode head1, ListNode head2) {
        ListNode dummy = new ListNode(-1);
        ListNode node = dummy;

        while (head1 != null && head2 != null) {
            if (head1.val < head2.val) {
                node.next = head1;
                head1 = head1.next;
            } else {
                node.next = head2;
                head2 = head2.next;
            }
            node = node.next;
        }

        if (head1 != null) {
            node.next = head1;
        }
        if (head2 != null) {
            node.next = head2;
        }

        return dummy.next;
    }
}
