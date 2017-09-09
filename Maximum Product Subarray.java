DP 问题。

State:
	max[i]:表示前i个数的最大连续子序列之和
	min[i]:表示前i个数的最小连续子序列之和
Function:
	★因为涉及到负数的问题。所以需要分开讨论：
	当 nums[i] 是正数时：max[i] = Math.max(max[i], max[i - 1] * nums[i]);
											 min[i] = Math.min(min[i], min[i - 1] * nums[i]);
	当 nums[i] 是负数时：max[i] = Math.max(max[i], min[i - 1] * nums[i - 1]);
                			 min[i] = Math.min(min[i], max[i - 1] * nums[i - 1]); 
Intitialize:
	max[0] = min[0] = 1;
	rst = nums[0];

但是通过观察我们发现，该题的 DP 中，当前状态仅仅只与上一个状态有关，与其他状态没有关系。
所以我们只需要用一个变量来保持上一个状态即可。
这样我们便可以把 O(N) 的额外空间优化到 O(1) 的额外空间。

Note：
	负数是在求 Max 和 Min 时非常值得重视的一个问题！！！
	特别是在涉及到 乘法 和 绝对值 运算时。负数常常扮演着十分重要的角色！！！
	与该题相同的还有： 合唱团 这道题目，也是一道输入中含有 负数 的 DP 问题。
	https://github.com/cherryljr/NowCoder/blob/master/%E5%90%88%E5%94%B1%E5%9B%A2.java
	
/*
Description
Find the contiguous subarray within an array (containing at least one number) which has the largest product.

Example
For example, given the array [2,3,-2,4], the contiguous subarray [2,3] has the largest product = 6.

Tags 
Subarray LinkedIn Dynamic Programming
*/

// Solution 1: O(N) Space Complexity
public class Solution {
    /*
     * @param nums: An array of integers
     * @return: An integer
     */
    public int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int len = nums.length;
        int[] max = new int[len + 1];
        int[] min = new int[len + 1];
        int rst = nums[0];
        max[0] = min[0] = 1;
        
        for (int i = 1; i <= len; i++) {
            max[i] = min[i] = nums[i - 1];
            if (nums[i - 1] > 0) {
                max[i] = Math.max(max[i], max[i - 1] * nums[i - 1]);
                min[i] = Math.min(min[i], min[i - 1] * nums[i - 1]);
            } else if (nums[i - 1] < 0) {
                max[i] = Math.max(max[i], min[i - 1] * nums[i - 1]);
                min[i] = Math.min(min[i], max[i - 1] * nums[i - 1]);
            }
            
            rst = Math.max(rst, max[i]);
        }
        
        return rst;
    }
}

// Solution 2: O(1) Space Complexity (Opitimized)
public class Solution {
    /**
     * @param nums: an array of integers
     * @return: an integer
     */
    public int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        // State & Initialize 
        int minPre = nums[0], maxPre = nums[0];
        int max = nums[0], min = nums[0];
        int res = nums[0];
        
        // Function
        for (int i = 1; i < nums.length; i ++) {
            max = Math.max(nums[i], Math.max(maxPre * nums[i], minPre * nums[i]));
            min = Math.min(nums[i], Math.min(maxPre * nums[i], minPre * nums[i]));
            res = Math.max(res, max);
            maxPre = max;
            minPre = min;
        }
        
        // Answer
        return res;
    }
}