/*
Reverse digits of an integer. Returns 0 when the reversed integer overflows (signed 32-bit integer).

Example
Given x = 123, return 321

Given x = -123, return -321

Tags Expand 
Integer

Thoughts:
1. Use long to capture the result. If > Integer.MAX_VALUE,return 0;
2. Use string to reverse, the conver to long
3. use string builder to reverse string
4. Compared with \u5224\u65AD\u56DE\u6587.c, it is much easier if you use string to complete this.

*/


public class Solution {
    /**
     * @param n the integer to be reversed
     * @return the reversed integer
     */
    public int reverseInteger(int n) {
        long num = (long)n;
        int sign = n > 0 ? 1 : -1;
        String rst = new StringBuilder(Math.abs(num)+"").reverse().toString();
				num = Long.parseLong(rst) * sign;
				
				if (num > Integer.MAX_VALUE || num < Integer.MIN_VALUE) {
					return 0;
				} else {
					return (int)num;
				}
    }
}
