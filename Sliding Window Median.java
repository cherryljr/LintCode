/*
Description
Given an array of n integer, and a moving window(size k),
move the window at each iteration from the start of the array,
find the median of the element inside the window at each moving.
(If there are even numbers in the array, return the N/2-th number after sorting the element in the window. )

Example
For array [1,2,7,8,5], moving window size k = 3. return [2,7,7]

At first the window is at the start of the array like this

[ | 1,2,7 | ,8,5] , return the median 2;

then the window move one step forward.

[1, | 2,7,8 | ,5], return the median 7;

then the window move one step forward again.

[1,2, | 7,8,5 | ], return the median 7;

Challenge
O(nlog(n)) time

Tags
LintCode Copyright Heap 
 */

/**
 * Approach: Two PriorityQueue (maxHeap + minHeap)
 * 与 LeetCode 上的 Sliding Window Median 几乎相同。
 * 只在 median 的定义上有点小小的区别（LintCode上的处理更加简单）
 * 
 * 该题的更好的解决方法是采用 TreeSet / TreeMap 这个数据结构来实现。时间复杂度为 O(nlogn)
 * 因为基本相同，这边就不提供 LintCode 的 Two TreeSet 的解法了。
 * 详细解析可以参见：
 * https://github.com/cherryljr/LeetCode/blob/master/Sliding%20Window%20Median.java
 */
public class Solution {
    // 这里写 比较方法 的时候不能写成：b-a,不让会因为 int 类型越界导致排序发生错误（test case 的数非常大）
    // 这也告诉我们数值比较时，务必使用自带的 compareTo 方法！！！（要自己写的话，务必考虑越界问题）
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b.compareTo(a));
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();

    /*
     * @param nums: A list of integers
     * @param k: An integer
     * @return: The median of the element inside the window at each moving
     */
    public List<Integer> medianSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        Integer[] rst = new Integer[nums.length - k + 1];
        for (int i = 0; i <= nums.length; i++) {
            if (i >= k) {
                rst[i - k] = getMedian();
                remove(nums[i - k]);
            }
            if (i < nums.length) {
                add(nums[i]);
            }
        }

        return Arrays.asList(rst);
    }

    private void add(int value) {
        if (value > getMedian()) {
            minHeap.offer(value);
        } else {
            maxHeap.offer(value);
        }

        // Check the size of two heap is whether balanced or not
        // if not equal then maxHeap will be larger by one
        if (maxHeap.size() - minHeap.size() > 1) {
            minHeap.offer(maxHeap.poll());
        } else if (maxHeap.size() < minHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }

    private int getMedian() {
        if (maxHeap.isEmpty() && minHeap.isEmpty()) {
            return 0;
        }

        return maxHeap.peek();
    }

    private void remove(int value) {
        if (value <= getMedian()) {
            maxHeap.remove(value);
        } else {
            minHeap.remove(value);
        }

        // Remove a value may cause the data structure lost balance,
        // so remember to rebalance the two heap here.
        if (maxHeap.size() - minHeap.size() > 1) {
            minHeap.offer(maxHeap.poll());
        } else if (maxHeap.size() < minHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }
}

