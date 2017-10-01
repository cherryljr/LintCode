Kadane's Algorithm 
The same as Maximum Subarray

/*
Description
Given an array of integers, find the subarray with smallest sum.
Return the sum of the subarray.

Notice
The subarray should contain one integer at least.

Example
For [1, -1, -2, 1], return -3.

Tags 
Greedy LintCode Copyright Array Subarray
*/

public class Solution {
    /*
     * @param nums: a list of integers
     * @return: A integer indicate the sum of minimum subarray
     */
    public int minSubArray(List<Integer> nums) {
        if (nums == null || nums.size() == 0) {
            return 0;
        }
        
        int min = Integer.MAX_VALUE;
        int sum = 0;
        
        for (int i : nums) {
            sum += i;
            min = Math.min(min, sum);
            sum = Math.min(sum, 0);
        }
        
        return min;
    }
}
