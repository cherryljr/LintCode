/*
Description
An array is ordered from small to large every k digits, ie arr[i] <= arr[i + k] <= arr[i + 2 * k] <= ....
Sort the array from small to large. We expect you to write an algorithm whose time complexity is O(n * logk).

Notice
0 <= arr[i] <= 2^31 - 1
1 <= |arr| <= 10^5
1 <= k <= 10^5

Example
Given arr = [1,2,3], k = 1, return [1,2,3].

Explanation:
The sorted array is [1,2,3].
Given arr = [4,0,5,3,10], k = 2, return [0,3,4,5,10].

Explanation:
The sorted array is [0,3,4,5,10].

Tags
Snapchat
 */

/**
 * Approach: PriorityQueue (minHeap)
 * 是 Merge k Sorted Array 的变形题。解决方法相同。
 * 因为是 array 所以还是需要建立一个 类 来保存其 值 与 位置 的信息，
 * 这样我们才能找到其对应的下一个位置上的数。
 *
 * 因为原始数组中是按照 k 个数字的间隔 从小到大 排序的。
 * 因此这就意味着：原数组的 前k个 元素就是整个数组中的 前k小的数。
 * 那么在初始化 PriorityQueue 时，我们只需要把它们放进去即可
 * （对应 Merge k Sorted Array 里面的就是将 k 个排序数组的第一个元素放进 PriorityQueue）
 * 后面的就是依次从 PriorityQueue 中 poll 出当前最小的元素，添加到 rst 里面即可。
 * 然后检查被 poll 出来的元素所属的排序数组是否还有下一个值(这里就用到了 index 信息了)，有的话就将其 add 到 PriorityQueue.
 * 一直重复下去，直到 PriorityQueue 为空。
 *
 * Merge k Sorted Array：
 * https://github.com/cherryljr/LintCode/blob/master/Merge%20k%20Sorted%20Arrays.java
 */
public class Solution {
    class Node {
        int value;
        int index;

        public Node(int value, int index) {
            this.value = value;
            this.index = index;
        }
    }

    /**
     * @param arr: The K spaced array
     * @param k: The param k
     * @return: Return the sorted array
     */
    public int[] getSortedArray(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k >= arr.length) {
            return arr;
        }

        int[] rst = new int[arr.length];
        // Initialize the minHeap
        PriorityQueue<Node> minHeap = new PriorityQueue<>(k, (a, b) -> a.value - b.value);
        for (int i = 0; i < k; i++) {
            minHeap.offer(new Node(arr[i], i));
        }

        int pos = 0;
        while (!minHeap.isEmpty()) {
            Node curr = minHeap.poll();
            rst[pos++] = curr.value;
            // check the next element exists or not
            if (curr.index + k < arr.length) {
                minHeap.offer(new Node(arr[curr.index + k], curr.index + k));
            }
        }

        return rst;
    }
}