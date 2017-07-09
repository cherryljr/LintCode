这是一个排序问题，要求时间复杂度为O(nlogn),空间复杂度为常数。
	解题思路如下：
	1. 首先符合nlogn复杂度的排序有快速排序，归并排序以及堆排序。
	2. 但是堆排序的额外空间复杂度不能满足要求。故只身下快速排序与归并排序。
	接下来我们分别对二者进行分析。
		
Merge Sort:
    1. find middle. 快慢指针
    slow指向head, fast指向head.next。然后slow一次走一步, fast一次走两步。当fast走到链表的终点的时候，slow就处于链表的中间位置了。
    2. Sort: 切开两半，先sort后半段,再sort前半段。
        注意：在sort后半段之后，要将mid.next = null使得链表断开。不然head指向的仍然是整个链表
    3. Merge: list A, B 已经是sorted, 然后按照大小，混合。
    要recursively call itself.

Quick Sort:
	快速排序本身并不适合用在list上面。虽然这里也实现了它但是并不推荐大家使用它。
	因为其随机性，故其最坏情况为O(n²)。
	并且Quick Sort需要进行很多random access，但是LinkedList没办法像Arrays一样通过index直接进行访问而是要从head开始遍历。
	
	当应用在Array的时候Quick Sort虽然拥有O(1)额外空间的优点。
	但是得益于LinkedList的特点：其插入操作的时间复杂度和空间复杂度均为：O(1).
	故Meerge Sort中原本需要O(N)额外空间的merge操作只需要O(1)便可以完成.
	
综上所述，
	Quick Sort不建议用在list上面。
	排列list, Merge Sort可能更可行和合理。
	
	List更适合Merge Sort, Array更适合Quick Sort.
	具体分析可以参见如下网页：
	http://www.geeksforgeeks.org/why-quick-sort-preferred-for-arrays-and-merge-sort-for-linked-lists/


/*
28% Accepted
Sort a linked list in O(n log n) time using constant space complexity.

Example
Given 1-3->2->null, sort it to 1->2->3->null.

Tags Expand 
Linked List


*/


// version 1: Merge Sort

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

    public ListNode sortList(ListNode head) {
       if (head == null || head.next == null) {
           return head;
       }
       
       ListNode mid = findMiddle(head);
       //	注意后半段是从mid.next开始.mid是属于前半段的
       ListNode right = sortList(mid.next);
       mid.next = null;
       ListNode left = sortList(head);
       
       return merge(left, right);
    }
}

// version 2: Quick Sort 1
public class Solution {
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        
        ListNode mid = findMedian(head); // O(n)
        
        ListNode leftDummy = new ListNode(0), leftTail = leftDummy;
        ListNode rightDummy = new ListNode(0), rightTail = rightDummy;
        ListNode middleDummy = new ListNode(0), middleTail = middleDummy;
        while (head != null) {
            if (head.val < mid.val) {
                leftTail.next = head;
                leftTail = head;
            } else if (head.val > mid.val) {
                rightTail.next = head;
                rightTail = head;
            } else {
                middleTail.next = head;
                middleTail = head;
            }
            head = head.next;
        }
        
        leftTail.next = null;
        middleTail.next = null;
        rightTail.next = null;
        
        ListNode left = sortList(leftDummy.next);
        ListNode right = sortList(rightDummy.next);
        
        return concat(left, middleDummy.next, right);
    }
    
    private ListNode findMedian(ListNode head) {
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
    
    private ListNode concat(ListNode left, ListNode middle, ListNode right) {
        ListNode dummy = new ListNode(0), tail = dummy;
        
        tail.next = left; tail = getTail(tail);
        tail.next = middle; tail = getTail(tail);
        tail.next = right; tail = getTail(tail);
        return dummy.next;
    }
    
    private ListNode getTail(ListNode head) {
        if (head == null) {
           return null;
        } 
       
        while (head.next != null) {
            head = head.next;
        }
        return head;
    }
}

// version 3: Quick Sort 2
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
class Pair {
    public ListNode first, second; 
    public Pair(ListNode first, ListNode second) {
        this.first = first;
        this.second = second;
    }
}

public class Solution {
    /**
     * @param head: The head of linked list.
     * @return: You should return the head of the sorted linked list,
                    using constant space complexity.
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        
        ListNode mid = findMedian(head); // O(n)
        Pair pair = partition(head, mid.val); // O(n)
        
        ListNode left = sortList(pair.first);
        ListNode right = sortList(pair.second);
        
        getTail(left).next = right; // O(n)
        
        return left;
    }
    
    // 1->2->3 return 2
    // 1->2 return 1
    private ListNode findMedian(ListNode head) {
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
    
    // < value in the left, > value in the right
    private Pair partition(ListNode head, int value) {
        ListNode leftDummy = new ListNode(0), leftTail = leftDummy;
        ListNode rightDummy = new ListNode(0), rightTail = rightDummy;
        ListNode equalDummy = new ListNode(0), equalTail = equalDummy;
        while (head != null) {
            if (head.val < value) {
                leftTail.next = head;
                leftTail = head;
            } else if (head.val > value) {
                rightTail.next = head;
                rightTail = head;
            } else {
                equalTail.next = head;
                equalTail = head;
            }
            head = head.next;
        }
        
        leftTail.next = null;
        rightTail.next = null;
        equalTail.next = null;
        
        if (leftDummy.next == null && rightDummy.next == null) {
            ListNode mid = findMedian(equalDummy.next);
            leftDummy.next = equalDummy.next;
            rightDummy.next = mid.next;
            mid.next = null;
        } else if (leftDummy.next == null) {
            leftTail.next = equalDummy.next;
        } else {
            rightTail.next = equalDummy.next;
        }
        
        return new Pair(leftDummy.next, rightDummy.next);
    }
    
    private ListNode getTail(ListNode head) {
        if (head == null) {
           return null;
        } 
       
        while (head.next != null) {
            head = head.next;
        }
        return head;
    }
}
