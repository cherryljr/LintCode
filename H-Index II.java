/*
Given an array of citations in ascending order (each citation is a non-negative integer) of a researcher,
write a function to compute the researcher's h-index.

According to the definition of h-index on Wikipedia: 
"A scientist has index h if h of his/her N papers have at least h citations each, 
and the other N − h papers have no more than hcitations each."

Example:
Input: citations = [0,1,3,5,6]
Output: 3
Explanation: [0,1,3,5,6] means the researcher has 5 papers in total and each of them had
             received 0, 1, 3, 5, 6 citations respectively.
             Since the researcher has 3 papers with at least 3 citations each and the remaining
             two with no more than 3 citations each, his h-index is 3.
             
Note: If there are several possible values for h, the maximum one is taken as the h-index.
 */

/**
 * Approach 1: Binary Search
 * 相比于 H-index 这道题给出的文章是按照引用数排序好的（从小到到）
 * 毫无疑问可以直接使用二分查找来确定结果。
 * 要做的只是分析好二分时的情况。这里需要注意 index 是从 0 开始的哈。
 * 我们使用 citations[mid](当前的引用数) 与 n - mid(大于等于当前引用数的文章)
 * 二者的大小关系来作为二分的依据。则结果 H-index 就是 n - left.
 * 因此我们可以发现，需要二分求解的是 下界。
 *  1. 当 citations[mid] >= n - mid 时，说明当前 H-index 至少为 n-mid
 *  因此我们需要缩小右边界 right 到 mid,即相当于left有了更小的范围，n-left 会更大。
 *  2. 当 citations[mid] < n - mid 时，说明当前 n-mid 篇文章并不满足，
 *  H-index比当前值更小，因此需要取 left = mid + 1
 * 其实上述分析了这些看上去可能比较抽象，推荐大家直接写个例子分析就好了。
 * 会比直接分析快上不少，也更加简单。
 * 
 * 时间复杂度：O(logn)
 */
class Solution {
    public int hIndex(int[] citations) {
        int n = citations.length;
        int left = 0, right = n;
        while (left < right) {
            int mid = left + ((right - left) >> 1);
            if (citations[mid] >= n - mid) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return n - left;
    }
}