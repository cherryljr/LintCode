/*
Numbers keep coming, return the median of numbers at every time a new number added.

Example
For numbers coming list: [1, 2, 3, 4, 5], return [1, 1, 2, 2, 3].

For numbers coming list: [4, 5, 1, 3, 2, 6, 0], return [4, 4, 4, 3, 3, 3, 3].

For numbers coming list: [2, 20, 100], return [2, 2, 20].

Challenge
Total run time in O(nlogn).

Clarification
What's the definition of Median? - Median is the number that in the middle of a sorted array. 
If there are n numbers in a sorted array A, the median is A[(n - 1) / 2]. 
For example, if A=[1,2,3], median is 2. If A=[1,19], median is 1.

Tags Expand 
LintCode Copyright Heap Priority Queue
*/

/**
 * Approach 1: Two PriorityQueue (maxHeap + minHeap)
 * 把 Input stream 想成向上的山坡。山坡中间那点，自然就是 median.
 * 前半段，作为 maxHeap,关注点是 PriorityQueue 的峰点，也就是实际上的 median.
 * 后半段，作为 minHeap,实现是Java中默认的 PriorityQueue。开头是最小的。
 * 这边用到了一个小小的 trick:
 *  maxHeap.size() 始终与 minHeap.size() 相等，或者更大 1.
 *  这样我们需要的 median 元素就是 maxHeap.peek().
 *  分析如下：
 *  题目定义 median = A[(n-1)/2],也就是说：
 *  当有 偶数 个元素时，此时 maxHeap.size() 与 minHeap.size() 相等，返回靠前的那个元素，即 median = maxHeap.peek()
 *  当有 奇数 个元素时，此时 maxHeap.size() 比 minHeap.size() 大一，返回中间的那个元素，即 median = maxHeap.peek()
 *  
 * 添加元素的时候，当要添加的元素 大于 中间值median，则 add 到 minHeap 中，否则 add 到 maxHeap 中。
 * 完成添加操作后，因为两个 heap 的 size 发生了变换，
 * 所以还需要判断是否要进行 调整，使其保持在一个我们需要的平衡状态。
 */
public class Solution {
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b.compareTo(a));
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();

    /**
     * @param nums: A list of integers.
     * @return: the median of numbers
     */
    public int[] medianII(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new int[]{};
        }

        int[] rst = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            addNumber(nums[i]);
            rst[i] = getMedian();
        }

        return rst;
    }

    private void addNumber(int value) {
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
        if (maxHeap.isEmpty()) {
            return 0;
        }
        return maxHeap.peek();
    }
}

// Approach 2: Optimize the add Function
public class Solution {
    /**
     * @param nums: A list of integers.
     * @return: the median of numbers
     */
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b.compareTo(a));
    private int numOfElements = 0;

    public int[] medianII(int[] nums) {
        int cnt = nums.length;
        int[] ans = new int[cnt];
        
        for (int i = 0; i < cnt; ++i) {
            addNumber(nums[i]);
            ans[i] = getMedian();
        }
        
        return ans;
    }
    
    private void addNumber(int value) {
        maxHeap.offer(value);
        
        if (numOfElements % 2 == 0) {
            if (minHeap.isEmpty()) {
                numOfElements++;
                return;
            }
            else if (maxHeap.peek() > minHeap.peek()) {
                Integer maxHeapRoot = maxHeap.poll();
                Integer minHeapRoot = minHeap.poll();
                maxHeap.offer(minHeapRoot);
                minHeap.offer(maxHeapRoot);
            }
        }
        else {
            minHeap.offer(maxHeap.poll());
        }
        numOfElements++;
    }
    
    private int getMedian() {
        return maxHeap.peek();
    }
}