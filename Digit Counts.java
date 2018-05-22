/*
Description
Count the number of k's between 0 and n. k can be 0 - 9.

Example
if n = 12, k = 1 in

[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
we have FIVE 1's (1, 10, 11, 12)
 */

/**
 * Approach 1: Brute Force
 * 直接遍历 k~n 范围内的数，计算各个数中有多少个k，然后加起来即可。
 * 时间复杂度：O(n)
 */
public class Solution {
    /*
     * @param : An integer
     * @param : An integer
     * @return: An integer denote the count of digit k in 1..n
     */
    public int digitCounts(int k, int n) {
        int count = 0;
        for (int i = k; i <= n; i++) {
            // 计算 i 中有多少个 k
            count += countK(i, k);
        }
        return count;
    }

    private int countK(int num, int k) {
        if (k == 0 && num == 0) {
            return 1;
        }

        int count = 0;
        while (num > 0) {
            if (num % 10 == k) {
                count++;
            }
            num /= 10;
        }
        return count;
    }
};

/**
 * Approach 2: Mathematics
 * 虽然这道题目我们可以像 Approach 1 中采用暴力解法。
 * 但是显然，这种题目存在更优解，并且其通常与 数学知识/规律 相关。
 * 因此我们可以举例并对其进行分析，最后可以得出如下结论：
 *  当某一位的数字 小于k 时，那么该位出现k的次数为：更高位数字(high) * 当前位数(base)
 *  当某一位的数字 等于k 时，那么该位出现k的次数为：更高位数字(high) * 当前位数(base) + 低位数字(low) + 1
 *  当某一位的数字 大于k 时，那么该位出现k的次数为：(更高位数字 + 1) * 当前位数(base)
 * 计算出各个位上的出现次数之后，全部加起来就是答案了
 * 但是值得注意的是，上述规律需要考虑一些特殊情况：
 *  1. 当 k == 0 时，对于最高位而言，其必定会触发 (high+1) * base 的情况
 *  这与实际情况是不符合的，最高位的0是可以被省略掉的，因此 0 不会出现在最高为上。
 *  2. 为了处理 1 中的情况，我们加入了 high == 0 && k == 0 的情况，如果遇到该情况则直接返回 count
 *  但是当 n <= 9, k=0 时就会发生错误，因此我们在开头进行了一次判断。
 *  3. 0 实在太过于特殊了，因此上述规律在面对 k=0, n = 1000... 的这种情况时无法成立
 *  (但是 Case 貌似没有考虑那多...然后竟然过了...emm...)
 *
 * 时间复杂度为：O(logn)
 */
public class Solution {
    /*
     * @param : An integer
     * @param : An integer
     * @return: An integer denote the count of digit k in 1..n
     */
    public int digitCounts(int k, int n) {
        if (k <= n && n <= 9) {
            return 1;
        }

        int base = 1;
        int count = 0;
        while (n / base > 0) {
            int currBit = n % (base * 10) / base;
            int high = n / (base * 10);
            int low = n % base;

            if (high == 0 && k == 0) {
                return count;
            }
            if (currBit < k) {
                count += high * base;
            } else if (currBit == k) {
                count += high * base + low + 1;
            } else {
                count += (high + 1) * base;
            }

            base *= 10;
        }
        return count;
    }
};