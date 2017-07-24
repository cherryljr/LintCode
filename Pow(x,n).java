注意以下4种情况
 1. n的正负
 2. n == 0的情况
 3.	n == 1的情况
 4. n / 2 为奇偶数的情况
 
该题有两种解法，分别为Divide and Conquer 和 对n进行一个while循环。
二者的算法时间复杂度均为：O(logN)
并且题目中涉及到大量奇偶判断和除以2的运算，故可以利用位运算对程序进行进一步的优化。

/*
Pow(x, n)

Implement pow(x, n).

Have you met this question in a real interview? Yes
Example
Pow(2.1, 3) = 9.261
Pow(0, 1) = 0
Pow(1, 0) = 1
Note
You don't need to care about the precision of your answer, it's acceptable if the expected answer and your answer 's difference is smaller than 1e-3.

Challenge
O(logn) time

Tags Expand 
Binary Search LinkedIn Divide and Conquer Mathematics Facebook
*/

//	Versin 1: Recursive
public class Solution {
    /**
     * @param x the base number
     * @param n the power number
     * @return the result
     */
    public double myPow(double x, int n) {
        // Write your code here
        if (n < 0) {
            x = 1 / x;
            n = -n;
        }
        
        if (n == 0) {
            return 1;
        }
        if (n == 1) {
            return x;
        }
        
        if ((n % 2) != 0) {
            return myPow(x * x, n / 2) * x;
        } else {
            return myPow(x * x, n / 2);
        }
    }
}

//	Version 2： No Recursive ( While Loop )
public class Solution {
    /**
     * @param x the base number
     * @param n the power number
     * @return the result
     */
    public double myPow(double x, int n) {
        // Write your code here
        if (n < 0) {
            x = 1 / x;
            n = -n;
        }

        double ans = 1, tmp = x;

        while (n != 0) {
            if (n % 2 == 1) {
                ans *= tmp;
            }
            tmp *= tmp;
            n /= 2;
        }
        return ans;
    }
}


//	Bit Operation
public class Solution {
    /**
     * @param x the base number
     * @param n the power number
     * @return the result
     */
    public double myPow(double x, int n) {
        // Write your code here
        if (n < 0) {
            x = 1 / x;
            n = -n;
        }

        double ans = 1, tmp = x;

        while (n != 0) {
            if ((n & 1) == 1) {      //change
                ans *= tmp;
            }
            tmp *= tmp;
            n >>= 1;                 //change
        }

        return ans;
    }
}