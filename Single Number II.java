/*
Given 3*n + 1 numbers, every numbers occurs triple times except one, find it.
Example
Given [1,1,2,3,3,3,2,2,4,1] return 4

Challenge
One-pass, constant extra space

Thinking process:
Still using bit manipulation. We need to erase all of the 3-appearance number and leave the single number out. A few steps:
	将所有的数转换成二进制，因为是int类型，共32位. 申请常数级（32位）的额外空间bits[32]，
	然后每个数对应的位相加，最后对应位上的和模3. 若出现3次，则模3后结果必然为0.
	最后的结果就是单个数对应的二进制数.
*/

public class Solution {
    public int singleNumberII(int[] A) {
        if (A == null || A.length == 0) {
            return -1;
        }

        int[] bits = new int[32];
        int rst = 0;
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < A.length; j++){
            //	将数组A中每个数的第i位进行相加，后再对每位上的数模3.
                bits[i] += A[j] >> i & 1;
                bits[i] %= 3;
            }
						//	结果为未满足条件数的二进制表示，利用位运算将其转换为十进制.
            rst |= bits[i] << i;
        }
        return rst;
    }
}

