/*
Description
Given two array of integers(the first array is array A, the second array is array B),
now we are going to find a element in array A which is A[i], and another element in array B which is B[j],
so that the difference between A[i] and B[j] (|A[i] - B[j]|) is as small as possible,
return their smallest difference.

Example
For example, given array A = [3,6,7,4], B = [2,8,9,3], return 0

Challenge
O(n log n) time

Tags
Sort Array Two Pointers LintCode Copyright
 */

/**
 * Approach: Sort + Two Pointers
 * 看到两个数组要求最小差值，第一反应就是将两个数组进行排序。
 * 然后我们就可以通过遍历来解决这个问题。
 * （暴力遍历的解法将耗费 O(n^2) 的时间，所以可以直接排除）
 *
 * 在遍历的时候，我们可以利用已经按照 从小到大 排序好了的特点，
 * 使用 Two Pointers 在 O(n) 的时间内完成 min difference 的寻找。
 * pointerA 和 pointerB 初始时均指向数组起点，
 * 然后每次比较 A[pointerA] 和 B[pointerB] 的值，
 * 如果 A[pointerA] 较小，说明 pointerA 移动到下一个节点的时候，有希望缩小差值；
 * 防止则将 pointerB 移动到下一个节点。
 * pointerA, pointerB 均只遍历数组一次(不会发生回退操作),时间复杂度为：O(2n)
 * 因此本算法的总体时间复杂为：O(nlogn)
 */
public class Solution {
    /**
     * @param A: An integer array
     * @param B: An integer array
     * @return: Their smallest difference.
     */
    public int smallestDifference(int[] A, int[] B) {
        if (A == null || A.length == 0 || B == null || B.length == 0) {
            return 0;
        }

        Arrays.sort(A);
        Arrays.sort(B);

        int pointerA = 0, pointerB = 0;
        int min = Integer.MAX_VALUE;
        while (pointerA < A.length && pointerB < B.length) {
            min = Math.min(min, Math.abs(A[pointerA] - B[pointerB]));
            if (A[pointerA] < B[pointerB]) {
                pointerA++;
            } else {
                pointerB++;
            }
        }

        return min;
    }
}