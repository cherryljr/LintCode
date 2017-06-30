注意本题是允许重复数据的，故我们需要考虑到最坏的情况。
eg. [1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1]
/*
Follow up for "Search in Rotated Sorted Array":
What if duplicates are allowed?

Would this affect the run-time complexity? How and why?

Write a function to determine if a given target is in the array.

Example
Tags Expand 
Binary Search Sorted Array Array

Thinking process:
This seems results in O(n) when allowing duplicates. Other than that, it's quite similar to Search In Rotated Sorted Array I.
*/

// for loop
public class Solution {
    // 这个问题在面试中不会让实现完整程序
    // 只需要举出能够最坏情况的数据是 [1,1,1,1... 1] 里有一个0即可。
    // 在这种情况下是无法使用二分法的，复杂度是O(n)
    // 因此写个for循环最坏也是O(n)，那就写个for循环就好了
    //  如果你觉得，不是每个情况都是最坏情况，你想用二分法解决不是最坏情况的情况，那你就写一个二分吧。
    //  反正面试考的不是你在这个题上会不会用二分法。这个题的考点是你想不想得到最坏情况。
    public boolean search(int[] A, int target) {
        for (int i = 0; i < A.length; i ++) {
            if (A[i] == target) {
                return true;
            }
        }
        return false;
    }
}

//  Binary Search
public class Solution {
    /** 
     * param A : an integer ratated sorted array and duplicates are allowed
     * param target :  an integer to be search
     * return : a boolean 
     */
    public boolean search(int[] A, int target) {
           // write your code here
        if (A.length == 0) {
            return false;
        }
        
        int start = 0;
        int end = A.length - 1;
        int mid;
        
        while (start + 1 < end) {
            mid = start + (end - start) / 2;
            if (A[mid] == target) {//Check central point
                return true;
            }
            if (A[start] < A[mid]){//1st section is continous
                if (A[start] <= target && target <= A[mid]) {//target in 1st section?
                    end = mid;
                } else {
                    start = mid;
                }
            } else {//2nd section is continous
                if (A[mid] <= target && target <= A[end]) {//target in 2nd section?
                    start = mid;
                } else {
                    end = mid;
                }
            }
        }//While
        
        return (A[start] == target)  ||  (A[end] == target);
    }
}
