/*
Count how many 1 in binary representation of a 32-bit integer.

Example
Given 32, return 1

Given 5, return 2

Given 1023, return 9

Challenge
If the integer is n bits with m 1 bits. Can you do it in O(m) time?

Tags Expand
Binary Bit Manipulation
*/

// 位操作的总结与例题汇总：
// http://blog.csdn.net/black_ox/article/details/46411997
// http://www.jiuzhang.com/tutorial/bit-manipulation/82
// https://www.zhihu.com/question/38206659
// http://www.matrix67.com/blog/archives/263

/**
 * Approach 1: Convert Integer to binaryString
 * 可以把integer -> binaryString -> 计算二进制中有几个1即可
 */
public class Solution {
    /**
     * @param num: an integer
     * @return: an integer, the number of ones in num
     */
    public int countOnes(int num) {
        if (num < 0) {
            return 0;
        }
        String bits = Integer.toBinaryString(num);
        char[] bitArray = bits.toCharArray();
        int sum = 0;
        for (int i = 0; i < bitArray.length; i++) {
            sum += Character.getNumericValue(bitArray[i]);
        }
        return sum;
    }
}

/**
 * Approach 2: Bit Manipulation
 * 使用 num = num & (num - 1) （该操作为去除num的二进制表示的第i位上的数）
 * 
 * 插个有趣的题外话：
 * 可以利用 num & -num 来迅速取出右起连续的 0 以及首次出现的 1 
 * 如果不清楚这个的可以参考：
 *  https://github.com/cherryljr/LeetCode/blob/master/Binary%20Index%20Tree%20Template.java
 *
 * 用0x077CB531计算末尾0的个数：
 *  http://www.matrix67.com/blog/archives/3985
 */
public class Solution {
    /**
     * @param num: an integer
     * @return: an integer, the number of ones in num
     */
    public int countOnes(int num) {
        int count = 0;

        while (num != 0) {
            num = num & (num - 1);
            count++;
        }

        return count;
    }
}
