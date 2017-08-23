/*
Given 3 * n + 1 numbers, every numbers occurs triple times except one, find it.
Example
Given [1,1,2,3,3,3,2,2,4,1] return 4

Challenge
One-pass, constant extra space

Thinking process:
仍然使用位运算进行操作，我们需要除去所有出现了3次的数字然后找出剩下的那个只出现一次的数字。
由 Single Number I 我们得到启发，能否同样利用 XOR 运算将出现了3次的数字进行抵消呢？
于是我们需要创造一种运算，即：3进制的XOR运算。(异或的重要实质：不进位加法)
这里每个数字出现 3 次，所以我们将进行 3进制的XOR.该运算的功能是：
3个相同的数的 3进制 进行 XOR运算 得到的结果是0.
这与 Single Number中两个相同的数的二进制异或结果为0一样。同样是为了达到抵消的效果。
即： a XOR3 0 = a,  a XOR3 a XOR3 a = 0 
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
            //  实质就是将该数转为3进制，然后进行不进位的加法运算 (XOR3)
                bits[i] += A[j] >> i & 1;		//	取出一个数的第i位
                bits[i] %= 3;
            }
						//	利用位运算将得到的三进制数组（二进制表示）转换为一个十进制的数.
						//	注意顺序，这里的bits[i]表示的就是表示二进制第i位上的数值，与我们平时从高到底的写法顺序是相反的
            rst |= bits[i] << i;
        }
        return rst;
    }
}

