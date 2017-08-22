/*
62% Accepted
Given 2*n + 1 numbers, every numbers occurs twice except one, find it.

Example
Given [1,2,2,1,3,4,3], return 4

Challenge
One-pass, constant extra space

Tags Expand 
Greedy

Manipulate bits:
Thinking process:
One-pass and constant extra space.
since all numbers appears twice, consider them as in bits format. Two identical number XOR will be zero. 
At the end, we use 0 XOR our target number, the result is actually the target number.
Very smart trick to use bits.
In order to compare from index 0 to the end, we need to extract index 0 first as result before for loop. And start for loop at i = 1.

对于 XOR 运算的理解：
 ① 相同为0，不同为1
 ② 不进位的加法(important)
 a XOR 0 = a,
 a XOR a = 0.
利用该运算进行抵消，从而达到目标。
*/

public class Solution {
	/**
	 *@param A : an integer array
	 *return : a integer 
	 */
	public int singleNumber(int[] A) {
        if (A == null || A.length == 0) {
           return 0;
        }
        int rst = A[0];
        for (int i = 1; i < A.length; i++) {
        	  //	一个数与0异或仍为其本身
            rst = rst ^ A[i];
        }
        return rst;
	}
}



