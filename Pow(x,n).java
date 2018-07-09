/*
Description
Implement pow(x, n).

You don't need to care about the precision of your answer,
it's acceptable if the expected answer and your answer 's difference is smaller than 1e-3.

Example
Pow(2.1, 3) = 9.261
Pow(0, 1) = 0
Pow(1, 0) = 1

Challenge
O(logn) time
 */

/**
 * Approach 1: Recursion
 * 快速幂的做法，类似二分的思想。
 *  x^n = x^(n/2) * x^(n/2)
 * 注意处理：n == Integer.MIN_VALUE 的 case，否则会超时。
 *
 * 时间复杂度：O(logn)
 */
public class Solution {
    /**
     * @param x the base number
     * @param n the power number
     * @return the result
     */
    public double myPow(double x, int n) {
        if (n == Integer.MIN_VALUE) {
            return 0.0;
        }
        if (n < 0) {
            x = 1 / x;
            n = -n;
        }

        if (n == 0) {
            return 1;
        }
        if (n == 1) {
            return x;
        }

        if ((n % 2) != 0) {
            return myPow(x * x, n / 2) * x;
        } else {
            return myPow(x * x, n / 2);
        }
    }
}

/**
 * Approach 2: Bit Operation
 *  1.全面考察指数的正负、底数是否为零等情况。
 *  2.写出指数的二进制表达，例如13表达为二进制1101。
 *  3.举例:10^1101 = 10^0001*10^0100*10^1000。
 *  4.通过 &1 和 >>1 来逐位读取1101，为1时将该位代表的乘数累乘到最终结果。
 */
public class Solution {
    /**
     * @param x the base number
     * @param n the power number
     * @return the result
     */
    public double myPow(double x, int n) {
        if (n == Integer.MIN_VALUE) {
            return 0.0;
        }
        if (n < 0) {
            x = 1 / x;
            n = -n;
        }

        double rst = 1;
        while (n != 0) {
            if ((n & 1) == 1) {
                rst *= x;
            }
            x *= x;
            n >>= 1;
        }

        return rst;
    }
}