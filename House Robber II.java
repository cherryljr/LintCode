Since this question is a follow-up to House Robber, we can assume we already have a way to solve the simpler question, 
i.e. given a 1 row of house, we know how to rob them. So we already have such a helper function. 
We modify it a bit to rob a given range of houses. 

Now the question is how to rob a circular row of houses. 
It is a bit complicated to solve like the simpler question. 
It is because in the simpler question whether to rob num[lo] is entirely our choice. 
But now, it is also constrained by whether num[hi] is robbed.

However, since we already have a nice solution to the simpler problem. We do not want to throw it away. 
Then, it becomes how can we reduce this problem to the simpler one?
Actually, extending from the logic that if house i is not robbed, then you are free to choose whether to rob house i + 1, 
you can break the circle by assuming a house is not robbed.
For example, 1 -> 2 -> 3 -> 1 becomes 2 -> 3 if 1 is not robbed.

Since every house is either robbed or not robbed and at least half of the houses are not robbed, 
the solution is simply the larger of two cases with consecutive houses, 
i.e. house i not robbed, break the circle, solve it, or house i + 1 not robbed. 
Hence, the following solution: I chose i = n and i + 1 = 0 for simpler coding. But, you can choose whichever two consecutive ones.

Conclusion:
    Break the circle in ith house. So we can turn the question into House Robber.
    Solve it like the simpler question.

/*
Description
After robbing those houses on that street, the thief has found himself a new place for his thievery so that he will not get too much attention. 
This time, all houses at this place are arranged in a circle. 
That means the first house is the neighbor of the last one. 
Meanwhile, the security system for these houses remain the same as for those in the previous street.

Given a list of non-negative integers representing the amount of money of each house, 
determine the maximum amount of money you can rob tonight without alerting the police.

Notice
This is an extension of House Robber.

Example
nums = [3,6,4], return 6

Tags 
Dynamic Programming Microsoft
*/

public class Solution {
    /*
     * @param nums: An array of non-negative integers.
     * @return: The maximum amount of money you can rob tonight
     */
    public int houseRobber2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        
        return Math.max(helper(nums, 0, nums.length - 2),  // the nth house is not robbed, so we break the circle in nth house.
                        helper(nums, 1, nums.length - 1)); // the nth house is robbed, so the 0th house mustn't be robbed, so we break in 0th house.
    }
    
    // Solution of House Robber (the simpler question)
    private int helper(int[] nums, int lo, int hi) {
        int rob = 0, notrob = 0;
        for (int i = lo; i <= hi; i++) {
            int temp = notrob;
            notrob = Math.max(notrob, rob);
            rob = temp + nums[i];
        }
        
        return Math.max(rob, notrob);
    }
}
