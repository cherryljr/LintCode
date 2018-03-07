/*
Description
Using O(1) time to check whether an integer n is a power of 2.
Example
For n=4, return true;
For n=5, return false;
Challenge 
O(1) time
Tags 
Bit Manipulation
*/

/**
 * Approach: Bit Operation
 * 编程中优化运算速度的常见技巧之一
 * 使用了 Bit Manipulation	： n & (n - 1)
 * 其效果为：去掉 一个数二进制的 最右边 的一个1
 * 该运算也在下面两个问题中应用到了
 *  1. 求 n 的二进制的最后一位为1的位数。 log2(n - (n & (n-1))) == log2(n & -n)
 *  上面式子的简化如果不懂的话可以参考：Binary Index Tree Template 中 LowBit(index) 函数 的解析
 *  https://github.com/cherryljr/LeetCode/blob/master/Binary%20Index%20Tree%20Template.java
 *  2. 求 n 的二进制中 1 的个数
 *
 * Note:
 * == has higher priority than &. You might want to wrap your operations in () to specify your own priority.
 */
class Solution {
    /*
     * @param n: An integer
     * @return: True or false
     */
    public boolean checkPowerOf2(int n) {
        if (n <= 0) {
            return false;
        }

        return (n & (n-1)) == 0;
    }
}
