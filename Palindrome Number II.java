/*
Description
Determines whether a binary representation of a non-negative integer n is a palindrome

Notice
0 <= n <= 2^32 - 1

Example
Given n = 0, return True

Explanation:
The binary representation of 0 is: 0
Given n = 3, return True

Explanation:
The binary representation of 3 is: 11
Given n = 4, return False

Explanation:
The binary representation of 4 is: 100
Given n = 6, return False

Explanation:
The binary representation of 6 is: 110

Tags
Amazon
 */

/**
 * Approach: Bit Operation and String
 * 采用进制转换的方法，计算出 n 的二进制字符串，
 * 然后利用 reverse 方法将其逆序后比较即可。
 * 
 * 进制转换方法可以参见：
 * https://github.com/cherryljr/NowCoder/blob/master/%E8%BF%9B%E5%88%B6%E8%BD%AC%E6%8D%A2.java
 */
class Solution {
    /**
     * @param n: non-negative integer n.
     * @return: return whether a binary representation of a non-negative integer n is a palindrome.
     */
    public boolean isPalindrome(int n) {
        StringBuilder sb = new StringBuilder();
        while (n >= 2) {
            sb.insert(0, n & 1);
            n >>= 1;
        }
        sb.insert(0, n & 1);

        String str1 = sb.toString();
        String str2 = sb.reverse().toString();
        return str1.equals(str2);
    }
}