/*
Description
Given a non-negative integer num, repeatedly add all its digits until the result has only one digit.

Example
Given num = 38.
The process is like: 3 + 8 = 11, 1 + 1 = 2. Since 2 has only one digit, return 2.

Challenge
Could you do it without any loop/recursion in O(1) runtime?

Tags
Mathematics Adobe Microsoft
 */

/**
 * Approach 1: Looping
 * 将每位上的数加起来了循环求解，直到 num < 10
 */
public class Solution {
    /**
     * @param num: a non-negative integer
     * @return: one digit
     */
    public int addDigits(int num) {
        if (num >= 0 && num <= 9) {
            return num;
        }

        while (num / 10 > 0) {
            int sum = 0, temp = num;
            // 取得 num 中各个位上的数并加起来
            while (temp > 0) {
                sum += temp % 10;
                temp = temp / 10;
            }
            num = sum;
        }

        return num;
    }
}

/**
 * Approach 2: Mathematics
 * 数学推导规律，O(1)时间复杂度。
 *  num = a0 + a1 * 10 + a2 * 100 + ... + ak * 10^k
 *  = (a0 + a1 + ... + ak) + 9(a1 + a2 + ... + ak) + 99(a2 + ... + ak) + ... + (10^k - 1)ak
 * 其中a0 + a1 + ... + ak是下一步计算要得到的结果，记为num1，重复上述过程：
 * num = num1 + 9 * x1, 其中 x = (a1 + a2 + ... + ak) + 11 * (a2 + ... + ak) + ...
 * num1 = num2 + 9 * x2
 * ...
 * 直到 numl < 10 为止。
 * 则可知 num = numl + 9 * x
 * numl = num % 9
 */
public class Solution {
    /**
     * @param num: a non-negative integer
     * @return: one digit
     */
    public int addDigits(int num) {
        if (num == 0) {
            return 0;
        }

        return num % 9 == 0 ? 9 : num % 9;
    }
}