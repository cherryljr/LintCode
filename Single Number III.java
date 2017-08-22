/*
Given 2*n + 2 numbers, every numbers occurs twice except two, find them.

Example
Given [1,2,2,3,4,4,5,3] return 1 and 5

Challenge
O(n) time, O(1) extra space.

该题为 2n+2, 故如果 XOR 全部的数，则结果相当于 XOR(A) = a^b. 
所以我们需要将a,b这两个数分开。由 Single Number I 联想到，我们是否能将 A 分成两个 
2n+1 的组，然后分别进行处理得到a, b.而如何找到那个断点呢？
我们需要用到 XOR(A) 的值。假设 c = a^b, 因为a, b必定不相等，故c的二进制肯定有一位不为0.
而我们便以任意一个为 1 的位对 A 进行划分 (根据异或的定义，即值不等的那个位)。那么如何快速找到 c 中为 1 的那个位呢？
我们可以利用运算：
 x = c - c & (c - 1), 这样便能快速找到倒数第一个 1 的位置.
 而上式可以进一步简化为： x = c & (-c)
 & x == 0  =>  0 的那组
 & x != 0  =>  非0的那组

PS.
两个数的异或是不考虑进位的加法。那么如果考虑进位，完整的加法的位运算可以表示为：
C = A^B + (A&B) << 1.
不能理解的可以参看博客：http://blog.csdn.net/hnxijie/article/details/51482274
*/

public class Solution {
    /**
     * @param A : An integer array
     * @return : Two integers
     */
    public List<Integer> singleNumberIII(int[] A) {
        //write your code
        if (A == null || A.length == 0) {
            return null;
        }
        
        List<Integer> rst = new ArrayList<Integer>();
        int XOR = A[0];
        for (int i = 1; i < A.length; i++) {
            XOR ^= A[i];
        }
        
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