与Jump Game相同有两种解法：

解法一：Sequence Dynamic Programming
	算法时间复杂度为：O(N^2)
	最小值问题，并且数组不能够交换位置 =>  Sequence DP
State:
	f[i]表示从起点跳到第i个位置最少需要几步
Function:
	当第j个位置是可以被跳到的 & j < i & j能够跳到i, 即f[j] + j >= i 时
	f[i] = Math.min(f[i], f[j] + 1)
Initialize: 
	f[0] = 0
Answer:
	f[n - 1]
	
解法二：Greedy
图解 http://www.cnblogs.com/lichen782/p/leetcode_Jump_Game_II.html

维护一个range, 是最远我们能走的. 

index/i 是一步一步往前, 每次当 i <= range, 做一个while loop， 在其中找最远能到的地方 maxRange

然后更新 range = maxRange

其中step也是跟index是一样, 一步一步走.

最后check的condition是，我们最远你能走的range >= nums.length - 1, 说明以最少的Step就到达了重点。


/*
Given an array of non-negative integers, 
you are initially positioned at the first index of the array.

Each element in the array represents your maximum jump length at that position.

Your goal is to reach the last index in the minimum number of jumps.

Example
Given array A = [2,3,1,1,4]

The minimum number of jumps to reach the last index is 2. 
(Jump 1 step from index 0 to 1, then 3 steps to the last index.)

Tags Expand 
Greedy Array

*/

//	Version 1: Sequence Dynamic Programming

public class Solution {
    /**
     * @param A: A list of lists of integers
     * @return: An integer
     */
    public int jump(int[] A) {
        // state
        int[] steps = new int[A.length];
        
        // Initialize
        steps[0] = 0;
        for (int i = 1; i < A.length; i++) {
            steps[i] = Integer.MAX_VALUE;
        }
        
        // Function
        for (int i = 1; i < A.length; i++) {
            for (int j = 0; j < i; j++) {
                if (steps[j] != Integer.MAX_VALUE && A[j] + j >= i) {
                    steps[i] = Math.min(steps[i], steps[j] + 1);
                }
            }
        }
        
        // Answer
        return steps[A.length - 1];
    }
}

// version 2: Greedy

public class Solution {
    public int jump(int[] A) {
        if (A == null || A.length == 0) {
            return -1;
        }
        int start = 0, end = 0, jumps = 0;
        while (end < A.length - 1) {
            jumps++;
            int farthest = end;
            for (int i = start; i <= end; i++) {
                if (A[i] + i > farthest) {
                    farthest = A[i] + i;
                }
            }
            start = end + 1;
            end = farthest;
        }
        return jumps;
    }
}