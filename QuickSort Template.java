针对模板中的 key point 3.大家或者会存在着疑问，这边进行一个解答。
1. 首先是 A[left] < pivot 而不是 A[left] <= pivot
原因是，如果 pivot 刚好是最大值，那么我们对 n 个元素进行 partition 就会出现
左边得到 n 个，右边得到 0 个元素的情况，这样左边继续递归下去有可能始终都是 n（每次取出的pivot都是最大值），
那么会出现无限递归的情况。
2. A[right] > pivot 而不是 A[right] >= pivot
同理，如果 pivot 恰好是最小值，那么我们对 n 个元素进行 partition 就回会出现
左边得到 0 个，右边得到 n 个元素的情况，这样右边继续递归下去有可能始终都是 n（每次取出的pivot都是最小值），
那么会出现无限递归的情况。

理解了以上原因之后，我们来 compare 一道题目，它同样是用到了 QuickSort 的思想。
https://github.com/cherryljr/LintCode/blob/master/Sort%20Colors%20II.java
但是这道题目中快排的书写为：
    while (l <= r && colors[l] <= colorMid)
这里使用了 <= 而不是 < 又是为什么呢?
其实是这里与所谓的快排还是有些稍稍的不同的。情况以下分析：
 1. 因为 rainbowSort 的递归中(..., colorMid + 1, colorTo), colorMid 的值不能混到右边去，
 因为右边值得区间为 [colorMid + 1, colorTo], 左边值得区间为 [colorFrom, colorMid]，所以
 我们需要把等于 colorMid 的值放在 partition 左侧，而不是像快排一样，等于 pivot 的值放在左侧或者右侧都是无所谓的。
 故这里我们需要使用 colors[l] <= colorMid 和 colors[r] > colorMid.
 2. 因为在条件 colorFrom >= colorTo 的时候我们已经 return 了，所以求 colorMid 的时候，colorFrom 一定不等于 colorTo。
 又因为 colorMid = (colorFrom + colorTo) / 2，所以 colorMid 的值一定不会等于最大值 colorTo，这也就避免了
 因为 pivot 是最大值而导致的无限递归问题。
 综上两个原因，导致了 rainbowSort 和 quickSort Template 略有不同的原因。

/*
Given an integer array, sort it in ascending order. Use quick sort O(nlogn) algorithm.

Example
Given [3, 2, 1, 4, 5], return [1, 2, 3, 4, 5].
*/

public class Solution {
    /**
     * @param A an integer array
     * @return void
     */
    public void sortIntegers2(int[] A) {
        quickSort(A, 0, A.length - 1);
    }
    
    private void quickSort(int[] A, int start, int end) {
        // the condition of quiting recursion
        if (start >= end) {
            return;
        }
        
        int left = start, right = end;
        // key point 1: pivot is the value, not the index
        int pivot = A[(start + end) / 2];

        // key point 2: every time you compare left & right, it should be 
        // left <= right not left < right
        while (left <= right) {
            // key point3: it should be A[left] < pivot not A[left] <= pivot
            // the same as A[right] > pivot not A[right] >= pivot
            while (left <= right && A[left] < pivot) {
                left++;
            }
            while (left <= right && A[right] > pivot) {
                right--;
            }
            if (left <= right) {
                int temp = A[left];
                A[left] = A[right];
                A[right] = temp;
                
                left++;
                right--;
            }
        }
        
        quickSort(A, start, right);
        quickSort(A, left, end);
    }
}
