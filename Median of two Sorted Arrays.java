/*
Median of two Sorted Arrays

There are two sorted arrays A and B of size m and n respectively. 
Find the median of the two sorted arrays.

Example
Given A=[1,2,3,4,5,6] and B=[2,3,4,5], the median is 3.5.

Given A=[1,2,3] and B=[4,5], the median is 3.

Challenge
The overall run time complexity should be O(log (m+n)).

Tags Expand 
Sorted Array Divide and Conquer Array Zenefits Uber Google

*/

/*
The time complexity is O(log(m+n))
so if we merge the first way: merge the two arrays then we find the median position. 
it will cost O((m + n) / 2) == O(N), this way can't meet the challenge.

from O(log(m+n)), we can think about that it shoule delete k / 2 values in a operation.
=> delete k / 2 and the complexity is O(1) 
=> we shoule move the array index to right position to achieve this demand.
=> k / 2 values in A[] or B[]
=> A[k/2] > B[k/2] ? delete the values in B[] : delete the values in A[]
=> use recursion
=> when should we quit recursion...
*/

class Solution {
    /**
     * @param A: An integer array.
     * @param B: An integer array.
     * @return: a double whose format is *.5 or *.0
     */
    public double findMedianSortedArrays(int[] A, int[] B) {
        // write your code here
        int len = A.length + B.length;
        if (len % 2 == 1) {
            return finKth(A, 0, B, 0, len / 2 + 1);
        } else {
            return (
                finKth(A, 0, B, 0, len / 2) + finKth(A, 0, B, 0, len / 2 + 1)
                ) / 2.0;
        }
    }
    
    private double finKth(int[] A, int indA, int[] B, int indB, int k) {
        if (indA >= A.length) {
            return B[indB + k - 1];
        }
        if (indB >= B.length) {
            return A[indA + k - 1];
        }
        if (k == 1) {
            return Math.min(A[indA], B[indB]);
        }
        
        int A_key = indA + k / 2 - 1 < A.length 
                    ? A[indA + k / 2 - 1]
                    : Integer.MAX_VALUE;
        int B_key = indB + k / 2 - 1 < B.length
                    ? B[indB + k / 2 - 1]
                    : Integer.MAX_VALUE;
        if (A_key > B_key) {
            return finKth(A, indA, B, indB + k / 2, k - k / 2);
        } else {
            return finKth(A, indA + k / 2, B, indB, k - k / 2);
        }
        
    }
}

