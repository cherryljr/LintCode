/*
Description
Given n numbers, now we need to merge n numbers into one number.
And each time we can only select and merge two numbers a,b. Each merger needs to consume a+b energy.
Output the minimum energy consumed by merging n numbers.

Notice
2 <= n <= 50000, the combined number will not exceed the int range

Example
Given [1,2,3,4], return 19
Explanation:
Merge 1,2, which consumes 3 energy, and the rest is [3,4,3]. Merge 3,3, which consumes 6 energy, and the rest is [6,4].
Merge the last two numbers, which consumes 10 energy, and a total of 19 energy was consumed.

Given [2,8,4,1], return 25
explanation:
Merge 1,2, which consumes 3 energy, and the rest is [8,4,3]. Merge 3,4, which consumes 7 energy, and the rest is [7,8].
Merge the last two numbers, which consumes 15 energy, and a total of 25 energy was consumed.

Tags
Greedy
 */

/**
 * Approach: Greedy
 * 显然每次选择当前的最小的两个数进行合并的答案是最优的.
 * 因此使用一个优先队列，小根堆，每次选堆顶的两个元素进行合并，然后再扔进堆里即可.
 * 时间复杂度O(nlogn).
 */
public class Solution {
    /**
     * @param numbers: the numbers
     * @return: the minimum cost
     */
    public int mergeNumber(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return 0;
        }

        int rst = 0;
        // 利用 PriorityQueue 储存数组中的元素，并按照从小到大的顺序排序
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int i : numbers) {
            minHeap.add(i);
        }
        // 每次 poll 出两个元素进行相加，然后再 add 到堆里面.
        while (minHeap.size() > 1) {
            int a = minHeap.poll();
            int b = minHeap.poll();
            rst += a + b;
            minHeap.add(a + b);
        }

        return rst;
    }
}