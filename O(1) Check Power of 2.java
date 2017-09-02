编程中优化运算速度的常见技巧之一
使用了 Bit Manipulation	： n & (n - 1)
该运算也在下面两个问题中应用到了
	1. 求 n 的二进制的最后一位为1的位数
	2. 求 n 的二进制中 1 的个数

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
};
