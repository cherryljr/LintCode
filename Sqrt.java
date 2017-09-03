因为精确度只要求到 整形, 故本题是个很简单的 二分查找法的题目.
基本上按照 Binary Search Template 编写即可。在这里就不扯太多了。
但是之所以编写该题有两个原因。
	1. 如果对精确度有进一步要求呢？比如要求精确到 1e-6 呢。
	这个时候需要用到的高效算法便是 牛顿迭代法。 算法第四版 P13 有笔记
	不清楚的朋友们可以移步：https://www.zhihu.com/question/20690553
	这里对它的数学原理进行了很好的分析
	2. 纪念卡马克大神！ Magic Number 
	该段源码被应用于 《雷神之锤3》 
	Click here for detials: https://en.wikipedia.org/wiki/Fast_inverse_square_root

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

Tags 
Binary Search Mathematics Facebook
*/

// Binary Search
class Solution {
    /**
     * @param x: An integer
     * @return: The sqrt of x
     */
    public int sqrt(int x) {
        // find the last number which square of it <= x
        // 注意数据类型要设置成 long 才行，不然会导致计算结果出错
        long start = 1, end = x;
        while (start + 1 < end) {
            long mid = start + (end - start) / 2;
            if (mid * mid <= x) {
                start = mid;
            } else {
                end = mid;
            }
        }
        
        if (end * end <= x) {
            return (int) end;
        }
        return (int) start;
    }
}

// Newton-Raphson method
class Solution {
	   /**
     * @param x a double
     * @return the square root of x
     */
    public int mySqrt(int x) {
        if (x < 0) {
            return Double.NaN;
        }
        
        double err = 1e-12;
        double t = c;
        while (Math.abs(t - c/t) > err * t) {
            t = (c / t + t) / 2.0;
        }
        
        return t;
    }
}


// Magic Number 0x5f3759df !!!  So Amazing
float Q_rsqrt(float number) {
    long i;
    float x2, y;
    const float threehalfs = 1.5F;

    x2 = number * 0.5F;
    y  = number;
    i  = * ( long * ) &y;                       // evil floating point bit level hacking
    i  = 0x5f3759df - ( i >> 1 );               // what the fuck? 卡神威武！卡神千秋万代 ::>_<::
    y  = * ( float * ) &i;
    y  = y * ( threehalfs - ( x2 * y * y ) );   // 1st iteration
    //	y  = y * ( threehalfs - ( x2 * y * y ) );   // 2nd iteration, this can be removed

    return y;
}
