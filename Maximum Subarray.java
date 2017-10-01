Kadane’s Algorithm

Solution 1: Greedy
与 Best Time to Buy and Sell Stock I 实质上是相同的。
但是需要注意的是 Stock I 中，如果股票一直在跌我们可以不买使得 profit 最大值为 0.
但是在本题中，如果数组中元素均为负数，Maximum Subarray 是可能为负数的。
因此初始化的时候，max = Integer.MIN_VALUE. 
并且要注意 sum += i 之后是先与 max 比较得到 max，
然后再将 sum 与 0 比较，得到下一个用于相加的值。

Solution 2: Prefix Sum
i到j的和可以用 前j个 数的和减去 前i-1 个数的和
故我们设置三个变量：
	sum 表示前i个数的和;
	maxsum表示最大和; 
	minsum表示最小和。
指针向后移动一位，考虑要不要新加入这个数时.要比较 不加入这个数的maxsum 和 加入这个数后总共的和减去最小的和的sum-minsum 这两个数谁大。
最小的和：最小和初始化为0，当指针后移，判断新的数字是否加入这个和时，要比较不加入这个数的minsum和加入这个数的minsum谁小，即这个数有没有使最小的和更小，若有则更新最小数。
核心思想是剔除对最大和有副作用的。

进一步详解可以参见：http://blog.csdn.net/u012255731/article/details/52302189

/*
 Description
Given an array of integers, find a contiguous subarray which has the largest sum.

Notice
The subarray should contain at least one number.

Example
Given the array [2,2,3,4,1,2,1,5,3], the contiguous subarray [4,1,2,1] has the largest sum = 6.

Challenge 
Can you do it in time complexity O(n)

Tags 
Greedy LinkedIn Array LintCode Copyright Subarray Enumeration
*/

// Solution 1: Greedy
public class Solution {
    /*
     * @param nums: A list of integers
     * @return: A integer indicate the sum of max subarray
     */
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int sum = 0;
        int max = Integer.MIN_VALUE;
        for (int i : nums) {
            sum += i;
            max = Math.max(max, sum);
            sum = Math.max(sum, 0);
        }
        
        return max;
    }
}

// Version 2: Prefix Sum
public class Solution {
    public int maxSubArray(int[] A) {
        if (A == null || A.length == 0){
            return 0;
        }
        
        int max = Integer.MIN_VALUE, sum = 0, minSum = 0;
        for (int i = 0; i < A.length; i++) {
            sum += A[i];
            max = Math.max(max, sum - minSum);
            minSum = Math.min(minSum, sum);
        }

        return max;
    }
}
