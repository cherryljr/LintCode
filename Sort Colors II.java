/*
Description
Given an array of n objects with k different colors (numbered from 1 to k),
sort them so that objects of the same color are adjacent, with the colors in the order 1, 2, ... k.

Notice
You are not suppose to use the library's sort function for this problem.
k <= n

Example
Given colors=[3, 2, 2, 1, 4], k=4, your code should sort colors in-place to [1, 2, 2, 3, 4].

Challenge
A rather straight forward solution is a two-pass algorithm using counting sort.
That will cost O(k) extra memory. Can you do it without using extra memory?

Tags
Sort Two Pointers
 */

/**
 * Approach: Two Pointers (Partition) + BinarySearch
 * 该题需要排序的范围上升到了 k,此时已经和普通的 排序 算法几乎一样了。
 * 我们无法通过仅一次遍历便能够得到结果。因此我们考虑使用排序算法。
 * 而排序算法的时间复杂度最少也需要 O(nlogn),但该题已经给出了排序的范围 1~k.
 * 故我们考虑能够通过该信息将复杂度降低到 O(nlogk) 呢？
 * 
 * 于是我们想到了 二分 的思想。
 * 即处理方法与 Partition Array 相同，只是将 Partition Array 中的 k 每次替换成 colorMid 而已。
 * 做法：
 *  1. 利用while循环，left 指针从左向右遍历，直到 left 指向的节点的值大于 colorMid.
 *  2. 同理 right 指针从右向左遍历，直到 right 指向的节点的值小于 colorMid.
 *  3. 交换left和right节点，直达left与right两个节点相遇或者相交。
 *  4. 递归调用该方法，继续遍历 colorMid 的 左半部分 和 右半部分。 （即实质上也是快排的做法）
 * 算法时间复杂度为 O(nlogk)
 */
class Solution {
    /**
     * @param colors: A list of integer
     * @param k: An integer
     * @return: nothing
     */
    public void sortColors2(int[] colors, int k) {
        if (colors == null || colors.length == 0) {
            return;
        }
        
        sortColors2Helper(colors, 0, colors.length - 1, 1, k);
    }
    
    private void sortColors2Helper(int[] colors,
                                   int left, 
                                   int right, 
                                   int colorFrom,
                                   int colorTo) {
        if (colorFrom == colorTo) {
            return;
        }
        if (left >= right) {
            return;
        }
        
        int l = left, r = right;
        int colorMid = colorFrom + (colorTo - colorFrom) / 2;
        while (l <= r) {
            while (l <= r && colors[l] <= colorMid) {
                l++;
            } 
            while (l <= r && colors[r] > colorMid) {
                r--;
            }
            
            if (l <= r) {
                int temp = colors[l];
                colors[l] = colors[r];
                colors[r] = temp;
                l++;
                r--;
            }
        } 
        
        sortColors2Helper(colors, left, r, colorFrom, colorMid);
        sortColors2Helper(colors, l, right, colorMid + 1, colorTo);
    }
}
