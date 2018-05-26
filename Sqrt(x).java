/*
Description
Implement int sqrt(int x).
Compute and return the square root of x.

Example
sqrt(3) = 1
sqrt(4) = 2
sqrt(5) = 2
sqrt(10) = 3

Challenge
O(log(x))
 */

/**
 * 因为精确度只要求到 整形, 故本题是个很简单的 二分查找法的题目.
 * 基本上按照 Binary Search Template 编写即可。在这里就不扯太多了。
 *
 * 但是之所以编写该题有两个原因。
 *  1. 如果对精确度有进一步要求呢？
 *  这个时候需要用到的高效算法便是 牛顿迭代法。 算法第四版 P13 有笔记
 *  不清楚的朋友们可以移步：https://www.zhihu.com/question/20690553
 *  这里对它的数学原理进行了很好的分析
 *  2. 瞻仰一下卡神的 Magic Number
 *  该段源码被应用于 《雷神之锤3》
 *  Click here for detials: https://en.wikipedia.org/wiki/Fast_inverse_square_root
 */

// Binary Search (upper bound)
public class Solution {
    /**
     * @param x: An integer
     * @return: The sqrt of x
     */
    public int sqrt(int x) {
        if (x == 0) {
            return 0;
        }
        // find the last number which square of it <= x
        // 注意数据类型要设置成 long 才行，不然会导致计算结果出错
        long start = 1, end = x;
        while (start < end) {
            long mid = start + ((end - start + 1) >> 1);
            if (x >= mid * mid) {
                start = mid;
            } else {
                end = mid - 1;
            }
        }
        return (int)end;
    }
}

// Newton-Raphson method
class Solution {
    /**
     * @param x a double
     * @return the square root of x
     */
    public int sqrt(int x) {
        // 更周全的做法是对 负数进行 判断
        if (x == 0) {
            return 0;
        }

        double err = 1e-12;     // 精度设置
        double t = x;
        while (Math.abs(t - x/t) > err * t) {
            t = (x / t + t) / 2.0;
        }

        return (int)t;
    }
}


// Magic Number 0x5f3759df !!!  So Amazing
// 雷神之锤3 源码    
float Q_rsqrt(float number) {
    long i;
    float x2, y;
const float threehalfs = 1.5F;

    x2 = number * 0.5F;
    y  = number;
    i  = * ( long * ) &y;                       // evil floating point bit level hacking
    i  = 0x5f3759df - ( i >> 1 );               // what the fuck?
    y  = * ( float * ) &i;
    y  = y * ( threehalfs - ( x2 * y * y ) );   // 1st iteration
    //  y  = y * ( threehalfs - ( x2 * y * y ) );   // 2nd iteration, this can be removed

    return y;
}