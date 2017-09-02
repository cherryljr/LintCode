要计算出 n 阶乘中尾部零的个数.
思路分析：
	假如把1 × 2 × 3 × 4 × ……× N 中每一个因数分解质因数，结果就是：   
  1 × 2 × 3 × (2 × 2) × 5 × (2 × 3) × 7 × (2  × 2 × 2) ×……   
	十进制数结尾的每一个0都表示有一个因数10存在 —— 任何进制都一样，对于一个M进制的数，结尾多一个0就 等价于 乘以M。   
  10 可以分解为 2*5 —— 因为只有质数2和5相乘能产生0，别的任何两个质数相乘都不能产生0，而且2，5相乘只产生一个0。   
  所以，分解后的整个因数式中有多少对(2,5)，结果中就有多少个0，而分解的结果中，2的个数显然是多于5的，因此，有多少个5，就有多少个(2,   5)对。   
  所以，讨论 n 的阶乘结尾有几个0的问题，就被转换成了1到 n 所有这些数的质因数分解式有多少个 5 的问题。   

/*
Description
Write an algorithm which computes the number of trailing zeros in n factorial.

Example
11! = 39916800, so the out should be 2

Challenge 
O(log N) time

Tags 
Mathematics
*/

class Solution {
    /*
     * param n: As desciption
     * return: An integer, denote the number of trailing zeros in n!
     */
    public long trailingZeros(long n) {
        long sum = 0;
        while (n != 0) {
            sum += n / 5;
            n /= 5;
        }
        return sum;
    }
};