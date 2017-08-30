典型划分类动态规划, 与 Best Time to Buy and Sell Stock IV 几乎相同

State:
	localMax[i][j] 表示前i个数，取j个子数组，包含第i个数的Maximum Sum
	globalMax[i][j] 表示前i个数，取j个子数组，可以不包含第i个数的Maximum Sum
Function:
	localMax[i][j] = max(localMax[i - 1][j] + nums[i - 1], globalMax[i - 1][j - 1] + nums[i - 1])
	globalMax[i][j] = max(globalMax[i - 1][j], localMax[i][j])
Answer:
	globalMax[nums.length][k]

/*
Description
Given an array of integers and a number k, find k non-overlapping subarrays which have the largest sum.
The number in each subarray should be contiguous.
Return the largest sum.

Notice
The subarray should contain at least one number

Example
Given [-1,4,-2,3,-2,3], k=2, return 8

Tags 
LintCode Copyright Subarray Array Dynamic Programming
*/

public class Solution {  
    /** 
     * @param nums: A list of integers 
     * @param k: An integer denote to find k non-overlapping subarrays 
     * @return: An integer denote the sum of max k non-overlapping subarrays 
     */  
    public int maxSubArray(int[] nums, int k) {  
        if(k > nums.length) {
        	return 0;
        }  
        
        // State
        int[][] local = new int[nums.length + 1][k + 1];  
        int[][] global = new int[nums.length + 1][k + 1];  
        
        // if k=0 => local/global colomn 0 = 0  
        // if nums.length=0 => local/global row 0 = 0 
        // Initialize & Function 
        for(int i = 1; i <= nums.length; i++) {  
            local[i][0] = Integer.MIN_VALUE;  
            for(int j = 1; j <= k; j++) {  
                if(j > i) {	//矩阵中的值不能默认为0，否则影响结果  
                    local[i][j] = Integer.MIN_VALUE;  
                    global[i][j] = Integer.MIN_VALUE;  
                    continue;  
                }  
                local[i][j] = Math.max(local[i - 1][j], global[i - 1][j - 1])  + nums[i - 1];  
                if(i == j)  
                    global[i][j] = local[i][j];  
                else  
                    global[i][j] = Math.max(global[i - 1][j], local[i][j]);  
            }  
        }  
        
        // Answer  
        return global[nums.length][k];  
    }  
}  