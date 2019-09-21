/*
Description
Given a char array representing tasks CPU need to do. It contains capital letters A to Z where different letters represent different tasks.
Tasks could be done without original order. Each task could be done in one interval. For each interval, CPU could finish one task or just be idle.

However, there is a non-negative cooling interval n that means between two same tasks, there must be at least n intervals that CPU are doing different tasks or just be idle.
You need to return the least number of intervals the CPU will take to finish all the given tasks.
    1. The number of tasks is in the range [1, 10000].
    2. The integer n is in the range [0, 100].

Example
Example1
Input: tasks = ['A','A','A','B','B','B'], n = 2
Output: 8
Explanation:
A -> B -> idle -> A -> B -> idle -> A -> B.

Example2
Input: tasks = ['A','A','A','B','B','B'], n = 1
Output: 6
Explanation:
A -> B -> A -> B -> A -> B.
 */

/**
 * Approach: Calculate the Mode
 * 这是一道与 众数(Mode)分析 相关的题目，从举的例子中我们可以得出任务调度的规律。
 * 首先是 n 的值较小，且tasks可用的任务种类较多，可以使得CPU在不空闲的情况下完成所有任务。这也是最简单的情况。其他则是需要插入idle的情况。
 * 如给定：AAABBCD，n=2。那么我们满足个数最多的任务所需的数量，即可以满足任务间隔要求，即：A##A##A；（其中，#表示需要填充任务或者idle的间隔）
 * 如果有两种或两种以上的任务具有相同的最多的任务数，如：AAAABBBBCCDE，n=3。那么我们将具有相同个数的任务A和B视为一个任务对，
 * 最终满足要求的分配为：AB##AB##AB##AB，剩余的任务在不违背要求间隔的情况下穿插进间隔位置即可，空缺位置补idle。
 * 由上面的分析我们可以得到最终需要最少的任务时间：（最多任务数-1）*（n + 1） + 出现次数最多的任务个数
 * 有上面的例子来说就是：(num(A)-1) * (3+1) + (2)。
 *
 * 解释一下这个公式怎么来的 (maxTimes - 1) * (n + 1) + numOfMode
 * 上面执行顺序的规律是： 有count - 1个A，其中每个A需要搭配n个#，再加上最后一个A，所以总时间为 (count - 1) * (n + 1) + 1
 * 要注意可能会出现多个频率相同且都是最高的任务，比如 ["A","A","A","B","B","B","C","C"]，所以最后会剩下一个A和一个B，因此最后要加上频率最高的不同任务的个数 numOfMode
 * 公式算出的值可能会比数组的长度小，如["A","A","B","B"]，n = 0，此时要取数组的长度
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
public class Solution {
    /**
     * @param tasks: the given char array representing tasks CPU need to do
     * @param n: the non-negative cooling interval
     * @return: the least number of intervals the CPU will take to finish all the given tasks
     */
    public int leastInterval(char[] tasks, int n) {
        int maxTimes = 0, numOfMode = 0;
        int[] count = new int[26];
        for (char task : tasks) {
            int index = task - 'A';
            if (++count[index] > maxTimes) {
                maxTimes = count[index];
                numOfMode = 1;
            } else if (count[index] == maxTimes) {
                numOfMode += 1;
            }
        }
        return Math.max(tasks.length, (maxTimes - 1) * (n + 1) + numOfMode);
    }
}