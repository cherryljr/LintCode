/*
Description
Gives nn coins, each weighing 10g, but the weight of one coin is 11g.
There is now a balance that can be accurately weighed. Ask at least a few times to be sure to find the 11g gold coin.
3 ≤ n ≤10^9
​​
Example
Given n = 3, return 1.

Explanation:
Select two gold coins on the two ends of the balance.
If the two ends of the balance are level, the third gold coin is 11g, otherwise the heavy one is 11g.
Given n = 4, return 2.

Explanation:
Four gold coins can be divided into two groups and placed on both ends of the scale. According to the
 */

/**
 * Approach: Greedy
 * 根据贪心的思想，我们应该每次尽可能大地缩小下次称量金币的个数。
 * 因此每次可以将金币尽可能均匀地分成三份：
 *  若 n%3 = 0，则可以分成n/3, n/3, n/3，然后任意取两份进行比较即可得出硬币在哪一堆里。最坏情况下一轮还需称 n/3 枚
 *  若 n%3 = 1，则可以分成n/3, n/3, n/3+1，则可以取个数为 n/3 和 n/3 的两份进行比较。最坏情况下一轮还需称 n/3+1 枚
 *  若 n%3 = 2，则可以分成n/3, n/3+1, n/3+1，则可以取个数为 n/3+1 和 n/3+1 的两份进行比较。最坏情况下一轮还需称 n/3+1 枚
 * 然后根据这样的称重方式迭代即可。
 */
public class Solution {
    /**
     * @param n: The number of coins
     * @return: The Minimum weighing times int worst case
     */
    public int minimumtimes(int n) {
        int count = 0;
        while (n > 3) {
            // 求最坏情况下一轮还需要称多少枚
            n = n / 3 + (n % 3 == 0 ? 0 : 1);
            count++;
        }
        // 如果还剩下 2 枚以上的硬币，则需要再称 1 轮
        return n > 1 ? count + 1 : count;
    }
}