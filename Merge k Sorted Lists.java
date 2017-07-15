有三种解决方案：
1. Divide & Conquer

2. 使用优先级队列（基于堆排序实现）————— 推荐此种解法
	 用Priorityqueue来排列所有list的leading node.   

	 时间：n*O(logk)   
	 PriorityQueue: logk   

3. merge two by two

这个题目可以有好几个衍生：   
   比如，如果k很大，一个机器上放不下所有的k list怎么办？ 
   比如，如果Merge起来的很长，一个机器上放不下怎么办？

```

/*

Merge k sorted linked lists and return it as one sorted list.

Analyze and describe its complexity.


Example
Given lists:

[
  2->4->null,
  null,
  -1->null
],
return -1->2->4->null.

Tags Expand 
Divide and Conquer Linked List Priority Queue Heap Uber Google Twitter LinkedIn Airbnb Facebook


*/

// version 1: Divide & Conquer

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
     * @param lists: a list of ListNode
     * @return: The head of one sorted list.
     */
    public ListNode mergeKLists(List<ListNode> lists) {
        if (lists.size() == 0) {
            return null;
        }
        return mergeHelper(lists, 0, lists.size() - 1);
    }
    
    private ListNode mergeHelper(List<ListNode> lists, int start, int end) {
        if (start == end) {
            return lists.get(start);
        }
        
        int mid = start + (end - start) / 2;
        ListNode left = mergeHelper(lists, start, mid);
        ListNode right = mergeHelper(lists, mid + 1, end);
        return mergeTwoLists(left, right);
    }
    
    private ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                tail.next = list1;
                tail = list1;
                list1 = list1.next;
            } else {
                tail.next = list2;
                tail = list2;
                list2 = list2.next;
            }
        }
        if (list1 != null) {
            tail.next = list1;
        } else {
            tail.next = list2;
        }
        
        return dummy.next;
    }
}


// version 2: Heap

public class Solution {
    /**
     * @param lists: a list of ListNode
     * @return: The head of one sorted list.
     */
    public ListNode mergeKLists(List<ListNode> lists) {  
        // write your code here
        if (lists == null || lists.size() == 0) {
            return null;
        }
        
        PriorityQueue<ListNode> heap = 
        new PriorityQueue<ListNode>(lists.size(), new Comparator<ListNode>(){
            public int compare(ListNode a, ListNode b) {
                return a.val - b.val;
            }
        });
        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i) != null) {
                heap.offer(lists.get(i));
            }
        }
        
        ListNode dummy = new ListNode(-1);
        ListNode tail = dummy;
        
        while (!heap.isEmpty()) {
            ListNode head = heap.poll();
            tail.next = head;
            tail = head;
            if (head.next != null) {
                heap.offer(head.next);
            }
        }
        return dummy.next;
    }
}


// Version 3: merge two by two

public class Solution {
    /**
     * @param lists: a list of ListNode
     * @return: The head of one sorted list.
     */
    public ListNode mergeKLists(List<ListNode> lists) {  
        if (lists == null || lists.size() == 0) {
            return null;
        }
        
        while (lists.size() > 1) {
            List<ListNode> new_lists = new ArrayList<ListNode>();
            for (int i = 0; i + 1 < lists.size(); i += 2) {
                ListNode merged_list = merge(lists.get(i), lists.get(i+1));
                new_lists.add(merged_list);
            }
            if (lists.size() % 2 == 1) {
                new_lists.add(lists.get(lists.size() - 1));
            }
            lists = new_lists;
        }
        
        return lists.get(0);
    }
    
    private ListNode merge(ListNode a, ListNode b) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        while (a != null && b != null) {
            if (a.val < b.val) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }
        
        if (a != null) {
            tail.next = a;
        } else {
            tail.next = b;
        }
        
        return dummy.next;
    }
}

