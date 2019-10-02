/*
Description
Let f(x) be the number of zeroes at the end of x!. (Recall that x! = 1 * 2 * 3 * ... * x, and by convention, 0! = 1.)
For example, f(3) = 0 because 3! = 6 has no zeroes at the end, while f(11) = 2 because 11! = 39916800 has 2 zeroes at the end.
Given K, find how many non-negative integers x have the property that f(x) = K.

K will be an integer in the range [0, 10^9].

Example
Example 1:
Input: K = 0
Output: 5
Explanation: 0!, 1!, 2!, 3!, and 4! end with K = 0 zeroes.

Example 2:
Input: K = 5
Output: 0
Explanation: There is no x such that x! ends in K = 5 zeroes.
 */

/**
 * Approach 1: Binary Search
 * 题目要求末尾0的个数正好为 k 个，这对于我们而言其实并不好处理。
 * 因此不妨将问题转换成 求末尾至少有 k 个0，这样利用 func(k+1)-func(k) 就能够得到答案了， 
 * 也就是所谓的将问题降维到我们所熟悉的一个层次上。
 *
 * 因为一个数 n 的阶乘，随着 n 的增大，末尾0的个数只增不减。
 * 因此我们可以使用二分法来找到第一个末尾0个数不小于 k 的元素。
 * 然后再找到 k+1 的第一个元素，相减就是我们需要的答案。
 * （至于求 n! 末尾0的个数问题，就是一个非常经典的问题了，这里就不再赘述。）
 *
 * 时间复杂度：O(logn)
 * 空间复杂度：O(1)
 *
 * References:
 *  https://github.com/cherryljr/LintCode/blob/master/Trailing%20Zeros.java
 *  https://github.com/cherryljr/LeetCode/blob/master/Subarrays%20with%20K%20Different%20Integers.java
 */
public class Solution {
    /**
    * @param K: an integer
    * @return: how many non-negative integers x have the property that f(x) = K
    */
    public int preimageSizeFZF(int K) {
        return (int)(getNum(K + 1) - getNum(K));
    }

    private long getNum(int k) {
        long left = 0, right = Long.MAX_VALUE;
        while (left < right) {
            long mid = left + (right - left >> 1);
            long zeros = getTrailingZeros(mid);
            if (zeros >= k) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    private long getTrailingZeros(long num) {
        long count = 0;
        while (num != 0) {
            count += num / 5;
            num /= 5;
        }
        return count;
    }
}

/**
 * Approach 2: Mathematics + Binary Search
 * 其实本题只需要多考虑些数学因素就能够进行一些优化。
 * 即：不存在时因为阶乘的元素里面包括了 5^x(x>2) 的元素，使得末尾一次性多出了不止1个0的情况。
 * 而当 n 每增加 5，其阶乘的结果就会多出：(n+1)*(n+2)*(n+3)*(n+4)*(n+5)
 * 如果进行因式分解，我们就能看出又多出了一个 5，即当 n 每增加5，n!末尾至少会多出 1 个0。
 * 而又因为末尾0的个数是只增不减的，所以就意味着：
 *  只要末尾0个数正好为k个的 n! 存在，那么其必定正好存在 5 个，否则就是 0 个。
 *  即答案只有 0 和 5 两种情况。
 *  
 * 时间复杂度：O(logn)
 * 空间复杂度：O(1)
 */
public class Solution {
    /**
     * @param K: an integer
     * @return: how many non-negative integers x have the property that f(x) = K
     */
    public int preimageSizeFZF(int K) {
        long left = 0, right = Long.MAX_VALUE;
        while (left < right) {
            long mid = left + (right - left >> 1);
            long zeros = getTrailingZeros(mid);
            if (zeros == K) {
                return 5;
            } else if (zeros > K){
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return 0;
    }
    
    private long getTrailingZeros(long num) {
        long count = 0;
        while (num != 0) {
            count += num / 5;
            num /= 5;
        }
        return count;
    }
}
