/*
Description
Write a program to swap odd and even bits in an integer with as few instructions as possible 
(e.g., bit 0 and bit 1 are swapped, bit 2 and bit 3 are swapped, and so on).

Example
5 = (101) => (1010) = 10

Tags 
Bit Manipulation
*/

/**
 * n & 0xaaaaaaaa 是取偶数位 (a的二进制为：1010);
 * n & 0x55555555 是取偶数位（5的二进制为：0101）
 * 然后偶数位无符号右移一位，奇数位左移一位，再取或，这样便实现了交换奇数偶数位。
 *
 * Notes:
 * Similar Question: Reverse Bits in LeetCode
 * https://github.com/cherryljr/LeetCode/blob/master/Reverse%20Bits.java
 */
public class Solution {
    /*
     * @param x: An integer
     * @return: An integer
     */
    public int swapOddEvenBits(int x) {
        x = ((x & 0xaaaaaaaa) >>> 1) | ((x & 0x55555555) << 1);
        return x;
    }
}