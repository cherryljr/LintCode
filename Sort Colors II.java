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
 * 做法与 Sort Colors 几乎相同。只是将每次用于划分数组的 pivot 改成了 colorMid.
 * 而 colorMid 则是每次通过二分的方法进行确定。
 * 当前数组划分完毕后将形成 <colorMid; =colorMid; >colorMid 这三部分的数组。
 * 然后我们再分别对 <colorMid 和 >colorMid 部分递归处理即可。
 *
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
                                   int  left,
                                   int right,
                                   int colorFrom,
                                   int colorTo) {
        if (colorFrom >= colorTo) {
            return;
        }
        if (left >= right) {
            return;
        }

        int less = left - 1;    // 小于pivot部分的 右边界
        int more = right + 1;   // 大于pivot部分的 左边界
        // take colorMid as the pivot
        int colorMid = colorFrom + (colorTo - colorFrom) / 2;
        int i = left;
        while (i < more) {
            if (colors[i] < colorMid) {
                swap(colors, ++less, i++);
            } else if (colors[i] > colorMid) {
                swap(colors, --more, i);
            } else {
                i++;
            }
        }

        // 对 <colorMid 部分的数组进行排序(partition)
        sortColors2Helper(colors, left, less, colorFrom, colorMid - 1);
        // 对 >colorMid 部分的数组进行排序(partition)
        sortColors2Helper(colors, more, right, colorMid + 1, colorTo);
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}