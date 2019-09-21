/*
Description
There is a railroad track and a transfer station in the middle of the railroad track.
You can imagine a structure like "T". You can think of this transfer station as a stack - the carriage is FILO(first-in last-out).
There are n train carriages arranged on the rails on the right side of the transfer station from 1 to n.

Now we want to transfer these n carriages to the rail on the left side of the transfer station,
to be in the order of the array arr. And each carriage enters the transfer station at most once.

Your task is to determine if the order of the arr can be reached. If possible, return the number of cars in the transfer station. If not, return -1.
    n ≤ 10^5

Example
Example 1:
Input: arr = [4,5,3,2,1]
Output: 3
Explanation:
  1 enter the transfer station
  2 enter the transfer station
  3 enter the transfer station
  4 directly to the railroad on the left
  5 directly to the railroad on the left
  3 from the transfer station to the railroad on the left
  2 from the transfer station to the railroad on the left
  1 from the transfer station to the railroad on the left
  Therefore, [4, 5, 3, 2, 1] is legal, and the number of transfer stations which is the maximum is 3.

Example 2:
Input: arr = [3,1,2]
Output: -1
Explanation:
  To make the first carriage is 3, we need to let 1, 2 enter the transfer station continuously.
  The transfer station is FILO, that is to say, 1 can not get the railroad on the left before 2.
 */

/**
 * Approach: Stack
 * 与 LeetCode 上的 Validate Stack Sequences 几乎一样，属于变形题。
 * 本质为给定一个 popped 序列，求当前序列能否由一个给定的 pushed 序列，通过栈的转换得到。
 * 本题中 popped 为最终要移动到铁轨左边的列车排列顺序即题目给定的 arr[]；pushed序列为原先在铁轨右边的列车排列顺序 1~n。
 * 求能否成功转换，如果可以的话，在次期间栈最大的深度是多少。代码基本照搬过来即可。
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 * 
 * Reference:
 *  https://github.com/cherryljr/LeetCode/blob/master/Validate%20Stack%20Sequences.java
 */
public class Solution {
    /**
     * @param arr: the arr
     * @return:  the number of train carriages in this transfer station with the largest number of train carriages
     */
    public int trainCompartmentProblem(int[] arr) {
        int n = arr.length, ans = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        int index = 0;
        for (int i = 1; i <= n; i++) {
            stack.push(i);
            while (!stack.isEmpty() && arr[index] == stack.peek()) {
                stack.pop();
                index++;
            }
            ans = Math.max(ans, stack.size());
        }
        return stack.isEmpty() ? ans : -1;
    }
}