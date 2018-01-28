/*
Description
Given two 32-bit numbers, N and M, and two bit positions, i and j.
Write a method to set all bits between i and j in N equal to M (e g , M becomes a substring of N located at i and starting at j)

Notice
In the function, the numbers N and M will given in decimal, you should also return a decimal number.

Clarification
You can assume that the bits j through i have enough space to fit all of M. That is, if M=10011，
you can assume that there are at least 5 bits between j and i.
You would not, for example, have j=3 and i=2, because M could not fully fit between bit 3 and bit 2.

Example
Given N=(10000000000)2, M=(10101)2, i=2, j=6

return N=(10001010100)2

Challenge
Minimum number of operations?

Tags
Bit Manipulation Cracking The Coding Interview
 */

/**
 * 根据题意，有一个想法，将n中第i位到第j位先置为0，然后，按位或上m << i即可。
 * 1. 现在问题是如何将n中 第i位 到 j位置 为0 ?
 *    可以考虑构造一个数，这个数从第i位到第j位是 0，其他位都为 1。然后这个数 和n与 一下，就可以把n的 i~j 位置成0了。
 * 2. 虽然这样的数并不是很好构造，反过来思考我们构造一个数从 第i位 到 第j位 都是 1，其他位为 0 的数,
 *    然后将这个数取反，就可以得到从第i位到第j位是0，其他位是1的数。
 * 3. -1 的二进制表示是 所有位为1 (这一点很重要，32位全是1的二进制对应整数-1.).
 *    我们以这个数为起点，需要的做的是将 高(31-j)位 置0，将 低i位 置0.
 * 4. 所以具体的操作应该是这样的：
 *    将-1先左移(31-j)位，因为高(31-j)位都是不需要的;
 *    然后再在这个基础上逻辑右移(31 - j + i)位，因为要将低i位置0;
 *    最后我们左移i位，将1恢复到正确的位置即可。即得到第i位到第j位是1，其他位是0的数。
 *    
 * 更详细的解析可以参见：
 * http://www.jiuzhang.com/tutorial/bit-manipulation/135
 */
class Solution {
    /*
     * @param n: An integer
     * @param m: An integer
     * @param i: A bit position
     * @param j: A bit position
     * @return: An integer
     */
    public int updateBits(int n, int m, int i, int j) {
        return ((~((((-1) << (31 - j)) >>> (31 - j + i)) << i)) & n) | (m << i);
    }
}