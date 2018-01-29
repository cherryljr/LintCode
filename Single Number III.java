/*
Given 2*n + 2 numbers, every numbers occurs twice except two, find them.

Example
Given [1,2,2,3,4,4,5,3] return 1 and 5

Challenge
O(n) time, O(1) extra space.
*/

/**
 * Approach: Bit Manipulation
 * 该题为 2n+2, 设这两个不同的数为:a, b. 那么如果 XOR 全部的数，则结果相当于 XOR(A) = a^b.
 * 所以我们需要将a,b这两个数分开。
 * 由 Single Number I 联想到，我们是否能将 A 分成两个2n+1 的组，然后分别进行处理得到a, b.而如何找到那个区分点呢？
 * 我们需要用到 XOR(A) 的值。假设 c = a^b, 因为a, b必定不相等，故c的二进制肯定有一位不为0.
 * 而我们便以任意一个为 1 的位对 A 进行划分 (根据异或的定义，即值不等的那个位)。
 *
 * 那么如何快速找到 c 中为 1 的那个位呢，这边为了方便我们选择 c二进制 中最右边的那个1作为区分点？
 * 我们可以利用在 Count 1 in Binary 中使用到的去除一个数二进制最右边1的位运算方法：
 * c & (c - 1), 然后在用原数减去它即可：x = c - c & (c - 1);
 * 而上式又可以进一步简化为： x = c & (-c)
 * 这样 x 代表的就是在 c的二进制 中倒数第一个位上为1的数(the least significant ‘1’ bit of c), 用二进制表示就是 100...
 * eg. c = 12 (binary 1100), then (c & -c) = 4 (binary 100) => x = 4.
 * (如果想到知道 c的二进制 中倒数一个1的位置的话，log2(x)就是其位置 -- 下标从0开始)
 * 因此我们可以通过将每个数和 x 进行与操作看其结果是否为0，来区分两组不同的数从而将 rstA 和 rstB 分开。
 * & x == 0  =>  0 的那组
 * & x != 0  =>  非0的那组
 *
 * PS.
 * 之所以能将 c - (c & (c - 1)) 简化成 c & -c 的原因在于负数补码的计算问题。
 * 1. 首先我们知道一个 正数 的 补码 与 原码 是相等的。
 * 2. 计算机中的计算方式是按照补码进行计算的。
 * 3. 计算一个 负数 的 补码 的快捷方式为: 把这个数对应的正数的二进制写出来。
 *    然后从右向左找到第一个1(这个1就是我们要求的结果，后来的操作就是让这个1能表示出来)，
 *    这个1和这个1右边的二进制不变，左边的二进制依次取反(包括符号位)，这样就求出的一个数的补码。
 *    说这个方法主要是让我们理解一个负数的补码在二进制上的特征。
 * 4. 最后我们把这个负数对应的正数与该负数进行 与运算 操作。
 *    由于这个1的左边的二进制与正数的原码对应的部分是相反的，所以相与一定都为0;
 *    由于这个1和这个1右边的二进制都是不变的，因此，相与后还是原来的样子。
 *    故，这样运算出来的结果就是 c - (c & (c - 1)) 的结果.
 *
 * 两个数的异或是不考虑进位的加法。那么如果考虑进位，完整的加法的位运算可以表示为：
 * C = A^B + (A&B) << 1.
 * 不能理解的可以参看博客：http://blog.csdn.net/hnxijie/article/details/51482274
 */
public class Solution {
    /**
     * @param A : An integer array
     * @return : Two integers
     */
    public List<Integer> singleNumberIII(int[] A) {
        if (A == null || A.length == 0) {
            return null;
        }

        List<Integer> rst = new ArrayList<Integer>();
        int XOR = A[0];
        for (int i = 1; i < A.length; i++) {
            XOR ^= A[i];
        }

        // 此时 XOR = a ^ b, 根据 XOR 计算出来分割点 bitPosition
        int bitPosition;
        // bitPosition = XOR - (XOR & (XOR - 1));
        bitPosition = XOR & (-XOR);

        int rstA = 0;
        int rstB = 0;

        for (int i = 0; i < A.length; i++) {
            if ((A[i] & bitPosition) != 0) {
                rstA ^= A[i];
            } else {
                rstB ^= A[i];
            }
        }

        rst.add(rstA);
        rst.add(rstB);
        return rst;
    }
}
