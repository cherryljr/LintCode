/*
Given 3 * n + 1 numbers, every numbers occurs triple times except one, find it.
Example
Given [1,1,2,3,3,3,2,2,4,1] return 4

Challenge
One-pass, constant extra space
*/

/**
 * Approach 1: Bit Manipulation
 * 使用位运算进行操作，我们需要除去所有出现了3次的数字然后找出剩下的那个只出现一次的数字。
 * 由 Single Number I 我们得到启发，能否同样利用 XOR 运算将出现了3次的数字进行抵消呢？
 * 
 * 于是我们需要创造一种运算，即：3进制的XOR运算。(异或的重要实质：不进位加法)
 * 这里每个数字出现 3 次，所以我们将进行 3进制的XOR. 该运算的功能是：
 * 3个相同的数的 3进制 进行 XOR运算 得到的结果是0.
 * 这与 Single Number中两个相同的数的二进制异或结果为0一样。同样是为了达到抵消的效果。
 * 即： a XOR3 0 = a;  a XOR3 a XOR3 a = 0
 * 将所有的数转换成二进制，因为是int类型，共32位. 申请常数级（32位）的额外空间bits[32]，
 * 然后每个数对应的位相加，最后对应位上的和模3. 若出现3次，则模3后结果必然为0.
 * 最后的结果就是单个数对应的二进制数.
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

/**
 * Approach 2: Bit Manipulation (Optimized)
 * 因为其他数是出现三次的，也就是说，对于每一个二进制位，如果只出现一次的数在该二进制位为1，那么这个二进制位在全部数字中出现次数无法被3整除。
 * 对于每一位，我们让Two，One表示当前位的状态。
 * 我们看 Two 和 One 里面的每一位的定义，对于ith(表示第i位)：
 * 如果 Two 里面ith是1，则ith当前为止出现1的次数 模3 的结果是2
 * 如果 One 里面ith是1，则ith目前为止出现1的次数 模3 的结果是1
 * 注意 Two 和 One 里面不可能ith同时为1，因为这样就是3次，每出现3次我们就可以抹去（消去）。
 * 那么最后 One 里面存储的就是每一位模3是1的那些位，综合起来One也就是最后我们要的结果。
 *
 * 如果 B 表示输入数字的对应位，Two+ 和 One+ 表示更新后的状态
 * 那么新来的一个数B，此时跟原来出现1次的位做一个异或运算，& 上 ~Two 的结果(也就是不是出现2次的)，那么剩余的就是当前状态是1的结果。
 * 同理Two ^ B （2次加1次是3次，也就是Two里面ith是1，B里面ith也是1，那么ith应该是出现了3次，此时就可以消去，设置为0），我们相当于会消去出现3次的位。
 * 
 * 但是Two ^ B也可能是ith上Two是0，B的ith是1，这样Two里面就混入了模3是1的那些位.怎么办？
 * 我们得消去这些,我们只需要保留不是出现模3余1的那些位ith，而One是恰好保留了那些模3余1次数的位，
 * 那么对One+取反不就是不是模3余1的那些位ith么？最终对(~One+)取一个&即可。
 * 综合起来就是：
 * One+ = (One ^ B) & (~Two)
 * Two+ = (~One+) & (Two ^ B)
 */
public class Solution {
    public int singleNumberII(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int ones = 0, twos = 0;
        for(int i = 0; i < nums.length; i++){
            ones = (ones ^ nums[i]) & ~twos;
            twos = (twos ^ nums[i]) & ~ones;
        }
        return ones;
    }
}