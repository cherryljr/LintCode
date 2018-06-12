/*
Description
Suppose you have N integers from 1 to N.
We define a beautiful arrangement as an array that is constructed by these N numbers successfully
if one of the following is true for the ith position (1 <= i <= N) in this array:

The number at the ith position is divisible by i.
i is divisible by the number at the ith position.
Now given N, how many beautiful arrangements can you construct?
N is a positive integer and will not exceed 15.

Example
Input: 2
Output: 2
Explanation:
The first beautiful arrangement is [1, 2]:
Number at the 1st position (i=1) is 1, and 1 is divisible by i (i=1).
Number at the 2nd position (i=2) is 2, and 2 is divisible by i (i=2).

The second beautiful arrangement is [2, 1]:
Number at the 1st position (i=1) is 2, and 2 is divisible by i (i=1).
Number at the 2nd position (i=2) is 1, and i (i=2) is divisible by 1.
 */

/**
 * Approach: Backtracking (Permutation)
 * 直接枚举每个数在所有位置上的可能性即可。
 * 即相当于求 1~N 数列的 Permutations.
 *
 * 时间复杂度为 O(n!)
 * 虽然这个时间复杂度很高，并且数据规模为 15.
 * 但是根据题目的限制条件，我们可以进行大幅的剪枝操作。
 */
public class Solution {
    private int count = 0;

    /**
     * @param N: The number of integers
     * @return: The number of beautiful arrangements you can construct
     */
    public int countArrangement(int N) {
        boolean[] visited = new boolean[N + 1];
        permute(N, 1, visited);
        return count;
    }

    private void permute(int N, int pos, boolean[] visited) {
        if (pos > N) {
            count++;
        }
        for (int i = 1; i <= N; i++) {
            if (!visited[i] && (i % pos == 0 || pos % i == 0)) {
                visited[i] = true;
                permute(N, pos + 1, visited);
                visited[i] = false;
            }
        }
    }
}