/*
Description
Write a program to check whether a given number is an ugly number.
Ugly numbers are positive numbers whose prime factors only include 2, 3, 5.
For example, 6, 8 are ugly while 14 is not ugly since it includes another prime factor 7.

Note that 1 is typically treated as an ugly number.

Example
Given num = 8 return true
Given num = 14 return false
 */

/**
 * Approach: Mathematics
 * 丑数的定义为 只包含质因子 2, 3, 5 的正整数。
 * 因此我们只需要将判断的数不断不断地除以 2， 3， 5
 * （如果余数为 0 的话），然后判断结果是否能被整除即可。
 *
 * 时间复杂度：O(log2(N) + logn3(N) + log5(N))
 */
public class Solution {
    private static final int[] factors = {2, 3, 5};

    /**
     * @param num: An integer
     * @return: true if num is an ugly number or false
     */
    public boolean isUgly(int num) {
        if (num == 0) {
            return false;
        }
        for (int factor : factors) {
            while (num % factor == 0)  {
                num /= factor;
            }
        }
        return num == 1;
    }
}