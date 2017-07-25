解法1：Sequence Dynamic Programming
Yes / No 问题，并且数组不能够交换位置 =>  Sequence DP

State:
	f[i]表示能否从起点跳到第i个位置 true or false
Function:
	f[i] = ( f[j]为true,即第j个位置是肯定可以被跳到的 & j < i & j能够跳到i, 即f[j] + j >= i )
Initialize: 
	f[0] = true
Answer:
	f[n - 1]是否为true
	
解法2： Greedy
At each index, check how far we can jump, store this farest-can-jump position in variable ‘farest’. 
							 Take max of current farest and (index + A[index]), store is in farest
At each index, compare if ‘farest’ is greater than the end of array, if so, found solution, return true.
At each index, also check if ‘farest == current index’, that means the farest we can move is to current index and we cannot move forward.
							 Then return false.

/*
Given an array of non-negative integers, you are initially positioned at the first index of the array.

Each element in the array represents your maximum jump length at that position.

Determine if you are able to reach the last index.

Notice

This problem have two method which is Greedy and Dynamic Programming.

The time complexity of Greedy method is O(n).

The time complexity of Dynamic Programming method is O(n^2).

We manually set the small data set to allow you pass the test in both ways. 
This is just to let you learn how to use this problem in dynamic programming ways. 
If you finish it in dynamic programming ways, you can try greedy method to make it accept again.

Example
A = [2,3,1,1,4], return true.

A = [3,2,1,0,4], return false.

*/

// version 1: Dynamic Programming
// 算法时间复杂度是 O(n^2) 可能会超时

public class Solution {
    /**
     * @param A: A list of integers
     * @return: The boolean answer
     */
    public boolean canJump(int[] A) {
        //	State
        boolean[] can = new boolean[A.length];
        
        //  Initialize
        can[0] = true;
        
        //  Function
        for (int i = 1; i < A.length; i++) {
            for (int j = 0; j < i; j++) {
                if (can[j] && A[j] + j >= i) {
                    can[i] = true;
                    break;
                }
            }
        }
        
        //  Answer
        return can[A.length - 1];
    }
}


// version 2: Greedy

public class Solution {
    public boolean canJump(int[] A) {
        // think it as merging n intervals
        if (A == null || A.length == 0) {
            return false;
        }
        int farthest = A[0];
        for (int i = 1; i < A.length; i++) {
            if (i <= farthest && A[i] + i >= farthest) {
                farthest = A[i] + i;
            }
        }
        return farthest >= A.length - 1;
    }
}