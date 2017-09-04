与 Pow(x, n) 这题相差不大，多了 %b 这个操作而已。
仍然采用 分治 的方法，采用递归的写法，当 n==0 或 n==1 时结束递归

Note: 1. 二分后要conquer，乘积可能大于Integer.MAX_VALUE, 所以用个long.
			2. 要处理 n%2==1 的情况，二分时候自动省掉了一份，要乘一下。

/*
Description
Calculate the an % b where a, b and n are all 32bit integers.

Example
For 231 % 3 = 2

For 1001000 % 1000 = 0

Challenge 
O(logn)

Tags 
Divide and Conquer
*/

class Solution {
    /*
     * @param a, b, n: 32bit integers
     * @return: An integer
     */
    public int fastPower(int a, int b, int n) {
        if (n == 0) {
            return 1 % b;
        }
        if (n == 1) {
            return a % b;
        }
        
        long product = fastPower(a, b, n / 2);
        product = (product * product) % b;
        if (n % 2 == 1) {
            product = (product * a) % b;
        }
        
        return (int) product;
    }
};