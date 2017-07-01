/*
33% Accepted
Merge two given sorted integer array A and B into a new sorted integer array.

Example
A=[1,2,3,4]

B=[2,4,5,6]

return [1,2,2,3,4,4,5,6]

Challenge
How can you optimize your algorithm if one array is very large and the other is very small?

Tags Expand 
Array Sorted Array


   Since the 2 list A,B are fixed, just add everyting into it.
   Basic implementation
*/

class Solution {
    /**
     * @param A and B: sorted integer array A and B.
     * @return: A new sorted integer array
     */
    public int[] mergeSortedArray(int[] A, int[] B) {
        // Write your code here
        if (A == null || B == null) {
            return A == null ? B : A;
        }
        
        int indA = A.length - 1;
        int indB = B.length - 1;
        int[] rst = new int[A.length + B.length];
        int indR = rst.length - 1;
        
        while (indA >= 0 && indB >= 0) {
            if (A[indA] > B[indB]) {
                rst[indR--] = A[indA--];
            } else {
                rst[indR--] = B[indB--];
            }
        }
        while (indA >= 0) {
            rst[indR--] = A[indA--];
        }
        while (indB >= 0) {
            rst[indR--] = B[indB--];
        }
        
        return rst;
    }
}
