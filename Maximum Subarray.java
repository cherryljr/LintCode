Kadane’s Algorithm
与 Best Time to Buy and Sell Stock I 实质上是相同的。
但是需要注意的是 Stock I 中，如果股票一直在跌我们可以不买使得 profit 最大值为 0.
但是在本题中，如果数组中元素均为负数，Maximum Subarray 是可能为负数的。
因此初始化的时候，max = Integer.MIN_VALUE. 
并且要注意 sum += i 之后是先与 max 比较得到 max，
然后再将 sum 与 0 比较，得到下一个用于相加的值。

/*
 Description
Given an array of integers, find a contiguous subarray which has the largest sum.

Notice
The subarray should contain at least one number.

Example
Given the array [?2,2,?3,4,?1,2,1,?5,3], the contiguous subarray [4,?1,2,1] has the largest sum = 6.

Challenge 
Can you do it in time complexity O(n)?

Tags 
Greedy LinkedIn Array LintCode Copyright Subarray Enumeration
*/

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