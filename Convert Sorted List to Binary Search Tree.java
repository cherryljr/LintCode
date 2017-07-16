两种解法

1. Divide and Conquer   
用快慢指针找到List的preMiddle node (注意这里找的是preMid而不是mid,mid是作为root不参与sort)
 
然后把root = premid.next (即mid)    

然后开始sortedListToBST(premid.next.next); //后半段 (mid.next ~ end)    
premid.next = null;	// 将前面的节点与后面排过序的断掉    
sortedListToBST(head); // 从头开始的前半段 (0 ~  premid)     

最后将root.left与root.right这两个部分merge起来。 

缺点：该题与Convert Sorted Arrays to Binary Search Tree的不同在于LinkedList无法通过index直接对Mid进行访问
所以每次递归都要执行findMiddle这一步，而findMiddle函数的复杂度是O(N)的。
故该解法的复杂度为：Nlog(N) (T(N) = 2T(N/2) + N, 采用二叉树分析法可得，每层复杂度为O(N)，有logN层)

2. Recursive
该解法利用了size这个变量来存储每次每次递归时List的长度 


/*
Given a singly linked list where elements are sorted in ascending order, convert it to a height balanced BST.

Example
Tags Expand 
Recursion Linked List

Thinking Process:
Find the middle point of the list.
Left of the mid will be left-tree, right of the mid node will be right-tree.

*/

//	Version 1: Just like Convert Sorted Arrays. Divide and Conquer, Fast / Slow Pointers

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
    private ListNode findpreMid(ListNode head) {
        ListNode slow = head;
        ListNode fast = head.next;
        
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return slow;
    }
    
    public TreeNode sortedListToBST(ListNode head) {  
        // write your code here
        if (head == null) {
            return null;
        } else if (head.next == null) {
            return new TreeNode(head.val);
        }
        
        ListNode premid = findpreMid(head);
        TreeNode root = new TreeNode(premid.next.val);
        
        TreeNode right = sortedListToBST(premid.next.next);
        premid.next = null;
        TreeNode left = sortedListToBST(head);
        
        root.left = left;
        root.right = right;
        
        return root;
    }
}

// Version 2: Recursive (Use size to save the list length)

public class Solution {
    private ListNode current;

    private int getListLength(ListNode head) {
        int size = 0;

        while (head != null) {
            size++;
            head = head.next;
        }

        return size;
    }

    public TreeNode sortedListToBST(ListNode head) {
        int size;

        current = head;
        size = getListLength(head);

        return sortedListToBSTHelper(size);
    }

    public TreeNode sortedListToBSTHelper(int size) {
        if (size <= 0) {
            return null;
        }

        TreeNode left = sortedListToBSTHelper(size / 2);
        TreeNode root = new TreeNode(current.val);
        current = current.next;
        TreeNode right = sortedListToBSTHelper(size - 1 - size / 2);

        root.left = left;
        root.right = right;

        return root;
    }
}