A够长，那么可以从A的尾部开始加新元素。     
注意，从尾部，是大数字优先的。   

```
/*
Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.

Note:
You may assume that nums1 has enough space (size that is greater or equal to m + n) to hold additional elements from nums2. The number of elements initialized in nums1 and nums2 are m and n respectively.

Hide Company Tags Bloomberg Facebook
Hide Tags Array Two Pointers
Hide Similar Problems Merge Two Sorted Lists

*/

/*
Thinking process:
1. start from the end, track back. End index = m + n;
2. when ever check a position, need to do index-1, because index=m+n is the size of array.
3. Make sure to clean up the array A or B.
*/
class Solution {
    /**
     * @param A: sorted integer array A which has m elements, 
     *           but size of A is m+n
     * @param B: sorted integer array B which has n elements
     * @return: void
     */
    public void mergeSortedArray(int[] A, int m, int[] B, int n) {
        int i = m-1, j = n-1, index = m + n - 1;
        while (i >= 0 && j >= 0) {
            if (A[i] > B[j]) {
                A[index--] = A[i--];
            } else {
                A[index--] = B[j--];
            }
        }
        
        //	将数组中剩下还未没添加的元素添加到结果中
        while (i >= 0) {
            A[index--] = A[i--];
        }
        while (j >= 0) {
            A[index--] = B[j--];
        }
    }
}