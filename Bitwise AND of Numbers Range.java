/*
Description
Given a range [m, n] where 0 <= m <= n <= 2147483647,
return the bitwise AND of all numbers in this range, inclusive.

For example, given the range [5, 7], you should return 4.

Example
Input:
m=5
n=7
Output:
4
 */

/**
 * Approach 1: Brute Force
 * 直接暴力求解，遍历一遍将每个数进行 & 运算即可
 * 时间复杂度：O(n)
 */
public class Solution {
    /**
     * @param m: an Integer
     * @param n: an Integer
     * @return: the bitwise AND of all numbers in [m,n]
     */
    public int rangeBitwiseAnd(int m, int n) {
        int rst = m;
        for (int i = m + 1; i <= n; i++) {
            rst &= i;
        }
        return rst;
    }
}

/**
 * Approach 2: Convert to Binary String
 * 这种题目除了暴力解法，必定存在更好的解法。
 * 这里题目明确给出对象是 int 类型的，因此其最多也就只有 32 位。
 * 因此我们不妨换个角度，从二进制的各个位上面去考虑。
 * 我们可以发现，相与的结果是由 m 和 n 二进制的最高位所决定的。
 * 只有当其最高位的位数相等，且均为 1 时，结果为 1 << 最高位位数.
 * 否则其余情况下，结果均为 0.
 * 具体为什么只要是熟悉二进制的朋友，相信很快就能看出来了。
 *
 * 该种做法时间复杂度为：O(32) => O(1)
 *
 * 这种换个角度解决问题的做法在需要遍历解决问题的暴力做法中是非常常见的。
 * 题目中通常会隐含着另外一个限制条件，而那个限制条件的范围通常会小不少。这也就是我们的突破口。
 * 类似的问题还有：
 * Friends Of Appropriate Ages
 * https://github.com/cherryljr/LeetCode/blob/master/Friends%20Of%20Appropriate%20Ages.java
 */
public class Solution {
    /**
     * @param m: an Integer
     * @param n: an Integer
     * @return: the bitwise AND of all numbers in [m,n]
     */
    public int rangeBitwiseAnd(int m, int n) {
        int rst = 0;
        // 求一个数的二进制，其高位全部补0的做法（这里m,n均为非负数）
        String mBit = "0" + Integer.toBinaryString((1 << 31) | m).substring(1);
        String nBit = "0" + Integer.toBinaryString((1 << 31) | n).substring(1);
        for (int i = 0; i < 32; i++) {
            if (mBit.charAt(i) == nBit.charAt(i)) {
                // 当 m, n 的有效最高位相等，则结果为 1 << (31 - i)
                if (mBit.charAt(i) != '0') {
                    rst |= (1 << 31 - i);
                }
            } else {
                break;
            }
        }
        return rst;
    }
}
