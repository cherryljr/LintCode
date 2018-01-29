/*
Description
Give an integer array，find the longest increasing continuous subsequence in this array.
An increasing continuous subsequence:
	Can be from right to left or from left to right.
	Indices of the integers in the subsequence should be continuous.

Notice
O(n) time and O(1) extra space.

Have you met this question in a real interview? Yes

Example
For [5, 4, 2, 1, 3], the LICS is [5, 4, 2, 1], return 4.

For [5, 1, 2, 3, 4], the LICS is [1, 2, 3, 4], return 4.

Tags 
Array Dynamic Programming Enumeration
*/

/**
 * Approach 1: Traverse Array Twice
 * 简单明了，分别 从左到右 和 从右到左 遍历一遍数组.
 * 分别求得两种方式遍历的出的 最长连续递增子序列。最后取较大值即可。
 *  1. 对于 从左向右 遍历的方式，如果发现下一个数比当前数要大则 length + 1, 否则置为1.
 *  2. 对于 从右向左 遍历的方式，如果发现上一个数比当前数要大则 length + 1, 否则置为1.
 *  3. 取两次遍历得到的较大值即可.
 */
public class Solution {
    public int longestIncreasingContinuousSubsequence(int[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }

        int n = A.length;
        int answer = 1;

        // from left to right
        int length = 1;
        for (int i = 1; i < n; i++) {
            if (A[i] > A[i - 1]) {
                length++;
            } else {
                length = 1;
            }
            answer = Math.max(answer, length);
        }

        // from right to left
        length = 1;
        for (int i = n - 2; i >= 0; i--) {
            if (A[i] > A[i + 1]) {
                length++;
            } else {
                length = 1;
            }
            answer = Math.max(answer, length);
        }

        return answer;
    }
}

/**
 * Approach 2: Sliding Window
 * 主要方法与 Approach 1 中的两次遍历相同。
 * 但是在遍历的时候使用了 滑动窗口。
 */
public class Solution {
    public int longestIncreasingContinuousSubsequence(int[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }

        int rst = 1, anchor = 0;
        // from left to right
        for (int i = 0; i < A.length; i++) {
            if (i > 0 && A[i] <= A[i - 1]) {
                anchor = i;
            }
            rst = Math.max(rst, i - anchor + 1);
        }

        // from right to left
        anchor = A.length - 1;
        for (int i = A.length - 1; i >= 0; i--) {
            if (i < A.length - 1 && A[i] <= A[i + 1]) {
                anchor = i;
            }
            rst = Math.max(rst, anchor - i + 1);
        }

        return rst;
    }
}