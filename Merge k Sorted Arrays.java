/*
Description
Given k sorted integer arrays, merge them into one sorted array.

Example
Given 3 sorted arrays:
[
  [1, 3, 5, 7],
  [2, 4, 6],
  [0, 8, 9, 10, 11]
]
return [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11].

Challenge
Do it in O(N log k).

N is the total number of integers.
k is the number of arrays.

Tags
Heap Priority Queue
 */

/**
 * Approach: PriorityQueue
 * Since we can't know the node's next element, as creating the PriorityQueue minHeap, let's create a Node{val, x, y}
 * Then it's very similar to merge k sorted lists.
 *
 * 具体做法：
 * 利用整体数组的 前k小 来初始化 PriorityQueue(minHeap).
 * 然后依次从 PriorityQueue 中 poll 出当前的最小值，然后 add 到 rst 中。
 * 并检查当前被 poll 出来的元素所属的排序数组中是否还存在下一个元素（利用 Node 中的位置信息 x,y）
 * 存在的话就将其 add 到 PriorityQueue 中。一直重复下去，直到 PriorityQueue 为空。
 * 
 * 变形题：
 * https://github.com/cherryljr/LintCode/blob/master/K%20Spaced%20Array%20Sorting.java
 */
public class Solution {
    class Node {
        int val, x, y;
        public Node(int val, int x, int y) {
            this.val = val;
            this.x = x;
            this.y = y;
        }
    }

    /**
     * @param arrays: k sorted integer arrays
     * @return: a sorted array
     */
    public int[] mergekSortedArrays(int[][] arrays) {
        if (arrays == null || arrays.length == 0) {
            return null;
        }

        PriorityQueue<Node> minHeap = new PriorityQueue<>((a, b) -> a.val - b.val);
        // Initialize the PriorityQueue and Calculate the all size of arrays
        int total = 0;	
        for (int i = 0; i < arrays.length; i++) {
            // Similar to merge k sorted lists, check the array is empty or not
            if (arrays[i].length != 0) {
                minHeap.offer(new Node(arrays[i][0], i, 0));
            }
            total += arrays[i].length;
        }

        int[] rst = new int[total];
        int index = 0;
        while (!minHeap.isEmpty()) {
            Node node = minHeap.poll();
            rst[index++] = node.val;
            // if the next element exists, put it into the minHeap
            if (node.y < arrays[node.x].length - 1) {
                minHeap.offer(new Node(arrays[node.x][node.y + 1], node.x, node.y + 1));
            }
        }

        return rst;
    }
}