逆序对是指 a[i]>a[j], i<j. 那么在排序的过程中，会把a[i]和a[j]交换过来，这个交换的过程，每交换一次，就是一个逆序对的“正序”过程。

思路是利用分治的思想：先求前面一半数组的逆序对数，再求后面一半数组的逆序对数，
然后求前面一半数组比后面一半数组中大的数的个数（也就是逆序对数），这三个过程加起来就是整体的逆序数目了。
看到这里是不是有点像归并排序呢？归并排序的思想就是把前一段排序，后一段排序，然后再整体排序。
而且，归并排序的规程中，需要判断前一半数组和后一半数组中当前数字的大小。
这也就是刚刚描述的逆序的判断过程了。如果前一半数组的当前数字大于后一半数组的当前数字，那么这就是一个逆序对。
    我们使用两个指针 i, j分别指向左右两个 subarray 的起始位置。
    当 j <= end && nums[i] > nums[j] 时，右指针 j 继续向右移动。直到逆序数对不成立为止。
    这样我们便能够计算出左边部分指针指向 i 时，有多少个逆序数对。
    最后当 i 遍历到 mid （即遍历完左边的 subarray）后，我们便可以得到总共有多少个逆序对。
    因此，可以在归并排序中的合并过程中计算逆序数.

LeetCode 上还有 BST / BIT 的解答。以及该题的深入详细分析：
https://leetcode.com/problems/reverse-pairs/discuss/

/*
Description
For an array A, if i < j, and A [i] > A [j], called (A [i], A [j]) is a reverse pair.
return total of reverse pairs in A.

Example
Given A = [2, 4, 1, 3, 5] , (2, 1), (4, 1), (4, 3) are reverse pairs. return 3

Tags 
Array Merge Sort
*/

public class Solution {
    /**
     * @param A an array
     * @return total of reverse pairs
     */
    public int reversePairs(int[] nums) {
        return mergeSort(nums, 0, nums.length-1);
    }
    
    private int mergeSort(int[] nums, int start, int end){
        if (start >= end) {
            return 0; 
        }

        int mid = start + (end - start) / 2; 
        int count = mergeSort(nums, start, mid) + mergeSort(nums, mid+1, end); 
        for(int i = start, j = mid+1; i <= mid; i++){
            while(j <= end && nums[i] > nums[j]) {
                j++;    
            }
            count += j - (mid + 1); 
        }
        Arrays.sort(nums, start, end+1); 
        return count; 
    }
}