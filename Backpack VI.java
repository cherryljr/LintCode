求组合的方案个数，使其结果恰好是Target => Backpack DP
与该题相似的 DFS / Traverse 题目为：Combination Sum, 他求解的是所有的具体方案。所以使用 Traverse

State:
	f[i] 表示数组中元素相加结果正好为i的组合个数
Function:
	f[i] = f[i] + f[i - nums[j]]   // 当 nums[j] <= i 时
Initialize:
	f[0] = 1;
Answer:
	f[target]

/*

Description
Given an integer array nums with all positive numbers and no duplicates, find the number of possible combinations that add up to a positive integer target.

Notice
A number in the array can be used multiple times in the combination. 
Different orders are counted as different combinations.

Have you met this question in a real interview? Yes

Example
Given nums = [1, 2, 4], target = 4
The possible combination ways are:
[1, 1, 1, 1]
[1, 1, 2]
[1, 2, 1]
[2, 1, 1]
[2, 2]
[4]
return 6

Tags 
Dynamic Programming

*/

public class Solution {
    /**
     * @param nums an integer array and all positive numbers, no duplicates
     * @param target an integer
     * @return an integer
     */
    public int backPackVI(int[] nums, int target) {
        // State
        int[] f = new int[target + 1];
        
        // Initialize
        f[0] = 1;
        
        // Function
        for (int i = 1; i <= target; ++i)
            for (int  j = 0; j < nums.length; ++j)
                if (i >= nums[j])
                    f[i] += f[i - nums[j]];
        
        // Answer
        return f[target];
    }

}