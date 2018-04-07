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
    public List<Integer> mergekSortedArrays(int[][] arrays) {
        List<Integer> rst = new ArrayList<>();
        if (arrays == null || arrays.length == 0) {
            return rst;
        }

        PriorityQueue<Node> minHeap = new PriorityQueue<>((a, b) -> a.val - b.val);
        // Initialize
        for (int i = 0; i < arrays.length; i++) {
            // Similar to merge k sorted lists, check the array is empty or not
            if (arrays[i].length != 0) {
                minHeap.offer(new Node(arrays[i][0], i, 0));
            }
        }

        while (!minHeap.isEmpty()) {
            Node node = minHeap.poll();
            rst.add(node.val);
            if (node.y < arrays[node.x].length - 1) {
                minHeap.offer(new Node(arrays[node.x][node.y + 1], node.x, node.y + 1));
            }
        }

        return rst;
    }
}