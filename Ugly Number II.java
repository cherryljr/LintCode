/*
Description
Ugly number is a number that only have factors 2, 3 and 5.
Design an algorithm to find the nth ugly number.
The first 10 ugly numbers are 1, 2, 3, 4, 5, 6, 8, 9, 10, 12...

Note that 1 is typically treated as an ugly number.

Example
If n=9, return 10.

Challenge
O(n log n) or O(n) time.
 */

/**
 * Approach 1: Brute Force
 * 直接计算出所有 Integer.MAX_VALUE 以内所有的丑数，然后保存下来，再排序一次即可。
 * 而由题意可知，丑数可被表示为： num = 2^k1 * 3^k2 * 5^k3 (k1, k2, k3 可为 0)
 * 这样我们枚举所有的情况即可。
 *
 * 因为使用的是 static list,所有只会被建立一次，
 * 这样我们只需要全部查询一次即可，之后每次需要时直接取就行了。
 * 相当于建立了一个 Cache，而这也就是我们常说的 单例模式。
 *
 * 时间复杂度：O(nlogn + T) T指测试样例个数
 */
public class Solution {
    private static List<Integer> nums;

    /**
     * @param n: An integer
     * @return: the nth prime number as description.
     */
    public int nthUglyNumber(int n) {
        if (nums == null) {
            nums = new ArrayList<>();
            // a = 2^k1
            for (long a = 1; a < Integer.MAX_VALUE; a *= 2) {
                // b = 2^k1 * 3^k2
                for (long b = a; b < Integer.MAX_VALUE; b *= 3) {
                    // c = 2^k1 * 3^k2 * 5^k3 (k1, k2, k3 可为 0)
                    for (long c = b; c < Integer.MAX_VALUE; c *= 5) {
                        nums.add((int)c);
                    }
                }
            }
            Collections.sort(nums);
        }
        return nums.get(n - 1);
    }
}

/**
 * Approach 2: Scan (Beats 99.9%)
 * Approach 1 中属于暴力解法，其实我们可以按照需求来进行计算第 n 个丑数。
 * 因为一个丑数可以被因数分解为： num = 2^k1 * 3^k2 * 5^k3
 * 所以我们可以一步步枚举 k1, k2, k3，并选出当前最小的数加入到 nums 中
 * 依次进行下去。同样这里也使用到了 static 来避免重复计算。（对 T 个 case 进行优化）
 *
 * 时间复杂度：O(n + T)
 * 
 * Fellow Up:
 * 
 * 参考资料：
 *  http://zxi.mytechroad.com/blog/math/leetcode-264-ugly-number-ii/
 */
public class Solution {
    private static List<Integer> nums;
    private static int i2 = 0, i3 = 0, i5 = 0;

    /**
     * @param n: An integer
     * @return: the nth prime number as description.
     */
    public int nthUglyNumber(int n) {
        if (nums == null) {
            nums = new ArrayList<>();
            nums.add(1);
        }

        // 只有当 n 大于当前计算出来的个数时，我们才需要进行计算，否则直接查询即可
        while (nums.size() < n) {
            int next2 = nums.get(i2) * 2;
            int next3 = nums.get(i3) * 3;
            int next5 = nums.get(i5) * 5;
            int next = Math.min(next2, Math.min(next3, next5));

            if (next == next2) {
                i2++;
            }
            if (next == next3) {
                i3++;
            }
            if (next == next5) {
                i5++;
            }
            nums.add(next);
        }
        return nums.get(n - 1);
    }
}