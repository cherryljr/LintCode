/*
Description
An amicable pair (m,n) consists of two integers m,n for which the sum of proper divisors
(the divisors excluding the number itself) of one number equals the other.

Given an integer k, find all amicable pairs between 1 and k.

Notice
For each pair, the first number should be smaller than the second one.

Example
Given 300, return [[220, 284]].

Tags
Enumeration
 */

// Similar to 筛法求素数 in NowCoder
// https://github.com/cherryljr/NowCoder/blob/master/%E7%AD%9B%E6%B3%95%E6%B1%82%E7%B4%A0%E6%95%B0.java
public class Solution {
    /*
     * @param k: An integer
     * @return: all amicable pairs
     */
    public List<List<Integer>> amicablePair(int k) {
        List<List<Integer>> rst = new ArrayList<>();

        for (int num = 2; num <= k; num++) {
            int sum1 = sumOfFactor(num);
            if (sum1 <= k && sum1 > num) {
                int sum2 = sumOfFactor(sum1);
                if (sum2 == num) {
                    List<Integer> list = new ArrayList<>();
                    list.add(num);
                    list.add(sum1);
                    rst.add(list);
                }
            }
        }
        return rst;
    }

    public int sumOfFactor(int num) {
        int sum = 1;
        for(int i = 2; i * i < num; i++){
            if(num % i == 0){
                sum += (i + num / i);
            }
        }
        return sum;
    }
}