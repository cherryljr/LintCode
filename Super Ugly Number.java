/*
Description
Write a program to find the nth super ugly number.
Super ugly numbers are positive numbers whose all prime factors are in the given prime list primes of size k.
For example, [1, 2, 4, 7, 8, 13, 14, 16, 19, 26, 28, 32] is the sequence of the first 12 super ugly numbers
given primes = [2, 7, 13, 19] of size 4.

1 is a super ugly number for any given primes.
The given numbers in primes are in ascending order.
0 < k ≤ 100, 0 < n ≤ 10^6, 0 < primes[i] < 1000

Example
Given n = 6, primes = [2, 7, 13, 19] return 13
 */

/**
 * Approach 1: Same as Ugly Number II
 * 这道题是 Ugly Number II general情况。
 * 思路是一样的，首先我们考虑一系列的数字是根据什么原则产生的。
 * 很显然，除了第一个数字，其余所有数字都是之前已有数字乘以任意一个在质数数组里的质数.
 * 所以对于每一个已有的数字，我们都可以分别乘以所有在质数数组里的质数得到一系列的数字，这些数字肯定会存在在以后的序列中。
 * 由于我们是要得到从小到大的结果，所以我们可以维护一个 idx 数组，来记录对应质数下一个需要被乘的已有数的index,
 * 即此处的 idx[] 相当于 Ugly Number II 中的 i2, i3, i5 这三个数的作用。
 * 我们取最小的结果当做下个数，对于那个最小的结果，需要增加 idx 数组中那个质数对应的index,表明下一次用下个已有的数来乘对应的质数。
 *
 * Note: 对于已有序列中的数，乘不同质数得到的结果会可能存在重复，
 * 比如题目中例子2, 2*7 == 7*2 == 14 就会产生重复的丑数，解决方法很简单，就是只要是等于最小的结果，就增加对应 idx 数组中的元素。
 *
 * 时间复杂度：O(nk)
 */
public class Solution {
    /**
     * @param n: a positive integer
     * @param primes: the given prime list
     * @return: the nth super ugly number
     */
    public int nthSuperUglyNumber(int n, int[] primes) {
        int[] nums = new int[n];
        nums[0] = 1;
        int[] idx = new int[primes.length];

        for (int i = 1; i < n; i++) {
            int min = Integer.MAX_VALUE;
            // 求 candidate 中的最小值
            for (int j = 0; j < primes.length; j++) {
                min = Math.min(min, primes[j] * nums[idx[j]]);
            }
            nums[i] = min;
            // 增加与最终结果对应的质数的元素
            for (int j = 0; j < idx.length; j++) {
                // 所有符合条件的，都需增加对应index, 避免重复
                if (min == nums[idx[j]] * primes[j]) {
                    idx[j]++;
                }
            }
        }
        return nums[n - 1];
    }
}

/**
 * Approach 2: PriorityQueue
 * 因为我们需要在当前 primes.length 个 candidate 中选出最小值。
 * 因此可以使用 minHeap 来优化这个过程，从而将时间复杂度降低到 O(nlogk)
 */
public class Solution {
    /**
     * @param n: a positive integer
     * @param primes: the given prime list
     * @return: the nth super ugly number
     */
    public int nthSuperUglyNumber(int n, int[] primes) {
        int[] nums = new int[n];
        nums[0] = 1;
        PriorityQueue<UglyNumber> pq = new PriorityQueue<>();
        for (int prime : primes) {
            pq.offer(new UglyNumber(prime, prime, 1));
        }

        for (int i = 1; i < n; i++) {
            UglyNumber curr = pq.peek();
            nums[i] = curr.value;
            while (nums[i] == pq.peek().value) {
                UglyNumber next = pq.poll();
                pq.offer(new UglyNumber(nums[next.index] * next.prime, next.prime, next.index + 1));
            }
        }
        return nums[n - 1];
    }

    class UglyNumber implements Comparable<UglyNumber> {
        int value;
        int prime;
        int index;

        public UglyNumber(int value, int prime, int index) {
            this.value = value;
            this.prime = prime;
            this.index = index;
        }

        @Override
        public int compareTo(UglyNumber other) {
            return this.value - other.value;
        }
    }
}
