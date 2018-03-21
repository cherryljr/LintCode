/*
Description
Given a singly linked list where elements are sorted in ascending order, convert it to a height balanced BST.

Example
               2
1->2->3  =>   / \
             1   3
Tags
Recursion Linked List
 */

/**
 * Approach 1: Divide and Conquer
 * 该解法与 Sort List 十分相似（整体流程几乎一样呢...)
 * https://github.com/cherryljr/LintCode/blob/master/Sort%20List.java
 *
 * 具体流程：
 *  1. 用快慢指针找到List的 preMiddle node (注意这里找的是 preMid 而不是mid, mid用来构造root节点)
 *  则有 mid = preMid.next, mid 就是我们的 root 节点。
 *  2. 然后利用 mid右边的链表 开始建立 root 的右子树 root.right = sortedListToBST(mid.next); // 右半段 [mid.next...end]
 *  同时注意需要将 preMid.next = null;	// 将前面的节点与后面的节点断开
 *  然后利用 mid左边的链表 开始建立 root 的右左树 root.left = sortedListToBST(head); // 右半段 [head...preMid]
 *  3. 递归调用以上过程即可
 *
 * 时间复杂度分析：
 * 该题与Convert Sorted Arrays to Binary Search Tree的不同在于LinkedList无法通过 index 直接对 middle node 进行访问.
 * 所以每次递归都要执行 findPreMid 这一步，而 findPreMid 函数的复杂度是 O(n) 的。
 * 故该解法的时间复杂度为：T(N) = 2T(N/2) + N => O(nlogn)
 * 采用二叉树分析法可得，每层复杂度为O(n)，有 logn 层
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
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int val;
 *     public TreeNode left, right;
 *     public TreeNode(int val) {
 *         this.val = val;
 *         this.left = this.right = null;
 *     }
 * }
 */
public class Solution {
    /**
     * @param head: The first node of linked list.
     * @return: a tree node
     */
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null || head.next == null) {
            return head == null ? null : new TreeNode(head.val);
        }

        ListNode preMid = findPreMid(head);
        ListNode mid = preMid.next;
        // construct the root node
        TreeNode root = new TreeNode(mid.val);
        // Build the right subtree (recursive call)
        root.right = sortedListToBST(mid.next);
        // Split the list from middle node
        preMid.next = null;
        // Build the right subtree (recursive call)
        root.left = sortedListToBST(head);

        return root;
    }

    private ListNode findPreMid(ListNode head) {
        ListNode pre = null;
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            pre = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        return pre;
    }
}

/**
 * Approach 2: Divide and Conquer (Keep a size value)
 * 如 Approach 1 中的分析，每次递归调用时我们都需要去寻找一次 mid 节点。
 * 那有没有办法优化这个问题，从而使得时间复杂度降低到 O(n) 呢？
 * 当然可以，我们可以通过 维持链表的size 从而达到目的。
 *
 * 具体做法如下：
 *  1. 首先我们遍历整个链表，获得整个链表的长度 size.
 *  2. 然后我们维持一个全局变量 curr, curr 初始值为链表的头结点 head.
 *  3. 建立 BST 的时候，我们使用两个参数：curr 和 size.
 *  表示的是：利用 curr 以及后面 size 个点建立一颗 BST.
 *  然后将 curr 作为 root 建立根节点，然后 curr 继续向后面移动一位，继续构建一棵 BST.
 *  4. 我们可以发现，前后建立的两棵树为 root 的左右子树，因此我们将它们连接起来。
 *  5. 递归调用即可
 *  
 * 时间复杂度分析：
 *  我们可以分析出来，建树过程中 curr 从 head 开始，逐步向后移动直到链表末尾，总共需要遍历 n 个节点。
 *  因此时间复杂度为：O(n)
 */
public class Solution {
    private ListNode curr;

    /**
     * @param head: The first node of linked list.
     * @return: a tree node
     */
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null || head.next == null) {
            return head == null ? null : new TreeNode(head.val);
        }

        curr = head;
        int size = getLength(head);
        return sortedListToBSTHelper(size);
    }

    private int getLength(ListNode head) {
        int size = 0;
        while (head != null) {
            size++;
            head = head.next;
        }
        return size;
    }

    private TreeNode sortedListToBSTHelper(int size) {
        if (size <= 0) {
            return null;
        }

        // Build the left subtree using the next size/2 nodes. (curr node's start position is the head of list)
        TreeNode left = sortedListToBSTHelper(size >>> 1);
        // Curr node is moving to the root position now,
        // Construct the root node
        TreeNode root = new TreeNode(curr.val);
        // Move curr node to the next node, then we can build the right subtree
        curr = curr.next;
        // Build the right subtree using the remaining nodes (exclude left subtree nodes and curr node).
        TreeNode right = sortedListToBSTHelper(size - 1 - (size >>> 1));
        // Link the tree
        root.left = left;
        root.right = right;

        return root;
    }
}
