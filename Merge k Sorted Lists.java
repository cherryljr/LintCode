/*
Description
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

Tags
Facebook Priority Queue Divide and Conquer Heap Uber Google Twitter LinkedIn Airbnb Linked List Microsoft Amazon IXL
 */

/**
 * Approach 1: Divide and Conquer
 * 使用 分治 的思想，利用 mergeTwoLists 这个方法逐渐将两个 sorted list 合并起来。
 *
 * 这个题目可以有好几个衍生：
 * 比如，如果k很大，一个机器上放不下所有的k list怎么办？
 * 比如，如果Merge起来的很长，一个机器上放不下怎么办？
 *
 * More Approaches and Detail Explanations:
 * https://leetcode.com/articles/merge-k-sorted-list/
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
        ListNode curr = dummy;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                curr.next = list1;
                curr = list1;
                list1 = list1.next;
            } else {
                curr.next = list2;
                curr = list2;
                list2 = list2.next;
            }
        }
        if (list1 != null) {
            curr.next = list1;
        } else {
            curr.next = list2;
        }

        return dummy.next;
    }
}

/**
 * Approach 2: PriorityQueue
 * 使用优先级队列（基于堆排序实现）————— 推荐此种解法
 * 用 PriorityQueue 来排列所有list的leading node.
 * （在将 node 加入到堆之前，记得判断 node 是否为空）
 *
 * 时间复杂度：O(nlogk)
 */
public class Solution {
    /**
     * @param lists: a list of ListNode
     * @return: The head of one sorted list.
     */
    public ListNode mergeKLists(List<ListNode> lists) {
        if (lists == null || lists.size() == 0) {
            return null;
        }

        PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a, b) -> (a.val - b.val));
        for (ListNode node : lists) {
            if (node != null) {
                minHeap.offer(node);
            }
        }

        ListNode dummy = new ListNode(-1);
        ListNode curr = dummy;
        while (!minHeap.isEmpty()) {
            ListNode node = minHeap.poll();
            curr.next = node;
            curr = node;
            if (node.next != null) {
                minHeap.offer(node.next);
            }
        }

        return dummy.next;
    }
}
