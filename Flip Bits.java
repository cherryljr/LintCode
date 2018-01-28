/*
Description
Determine the number of bits required to flip if you want to convert integer n to integer m.

Notice
Both n and m are 32-bit integers.

Example
Given n = 31 (11111), m = 14 (01110), return 2.

Tags
Bit Manipulation Cracking The Coding Interview
 */

/**
 * Approach: Bit Operation
 * 该问题是 Count 1 in Binary 的一个升级版应用
 * 思考将整数 A 转换为 B.
 * 如果 A 和 B 在第i（0 <=i < 32）个位上相等，则不需要改变这个Bit位，如果在第i位上不相等，则需要改变这个Bit位。
 * 所以问题转化为了A和B有多少个Bit位不相同！
 * 联想到位运算有一个异或操作，相同为0，相异为1，所以问题转变成了:
 * 计算 A异或B 之后这个数中 1 的个数.
 */
public class Solution {
    /*
     * @param a: An integer
     * @param b: An integer
     * @return: An integer
     */
    public int bitSwapRequired(int a, int b) {
        return countOnes(a ^ b);
    }

    private int countOnes(int num) {
        int count = 0;
        while (num != 0) {
            num = num & (num - 1);
            count++;
        }
        return count;
    }
}