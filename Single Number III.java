/*
Given 2*n + 2 numbers, every numbers occurs twice except two, find them.

Example
Given [1,2,2,3,4,4,5,3] return 1 and 5

Challenge
O(n) time, O(1) extra space.

Thinking Process:
The 2 exception must have this feature: a ^ b != 0, since they are different
Still want to do 2n + 1 problem as in Single Number I, then we need to split a and b into 2 groups and deal with two 2n+1 problems
Assume c = a^b, there must be a bit where a and b has the difference, so that bit in c is 1.
Find this bit position and use it to split the group: shift number in the array by ‘bit-position’ indexes. 
If the shifted number has 1 at the ‘bit-position’, set it to one group; otherwise to another group. 

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
        
        int bitPosition = 0;
        for (int i = 0; i < 32; i++) {
            if ((XOR >> i & 1) == 1) {
                bitPosition = i;
            }
        }
        
        int rstA = 0; 
        int rstB = 0;
        
        for (int i = 0; i < A.length; i++) {
            if ((A[i] >> bitPosition & 1) == 1) {
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

