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
 */
public class Solution {
    /**
     * @param nums: A list of integers.
     * @return: the median of numbers
     */
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b.compareTo(a));
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();

    public int[] medianII(int[] nums) {
        int[] rst = new int[nums.length];
        if (nums == null || nums.length == 0) {
            return rst;
        }

        // Initialize the maxHeap
        // Otherwise, it will throw Exception at addNumber Funtion when we visit maxHeap.peek()
        rst[0] = nums[0];
        maxHeap.offer(nums[0]);

        // Function
        for (int i = 1; i < nums.length; i++) {
            addNumber(nums[i]);
            rst[i] = getMedian();
        }

        return rst;
    }

    private void addNumber(int value) {
        if (value > maxHeap.peek()) {
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
        return maxHeap.peek();
    }
}

// Approach 2: Optimize the add Function
public class Solution {
    /**
     * @param nums: A list of integers.
     * @return: the median of numbers
     */
    PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
    PriorityQueue<Integer> maxHeap = 
        new PriorityQueue<Integer>(1, new Comparator<Integer>() {
            public int compare(Integer left, Integer right) {
                return right - left;
            }    
        });
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