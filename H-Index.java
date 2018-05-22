/*
Given an array of citations (each citation is a non-negative integer) of a researcher,
write a function to compute the researcher's h-index.

According to the definition of h-index on Wikipedia:
"A scientist has index h if h of his/her N papers have at least h citations each,
and the other N − h papers have no more than h citations each."

Example:
Input: citations = [3,0,6,1,5]
Output: 3
Explanation: [3,0,6,1,5] means the researcher has 5 papers in total and each of them had
             received 3, 0, 6, 1, 5 citations respectively.
             Since the researcher has 3 papers with at least 3 citations each and the remaining
             two with no more than 3 citations each, his h-index is 3.
             
Note: If there are several possible values for h, the maximum one is taken as the h-index.
 */

/**
 * Approach 1: Sort 
 * 直接使用快速排序进行排序，然后根据 H index 的规定进行比较即可。
 * 因为已经排序好了，所以可以直接利用 二分查找 来进行加速。
 * 具体分析可以参考：
 * H-Index II: https://github.com/cherryljr/LintCode/blob/master/H-Index%20II.java
 * 
 * 时间复杂度：O(nlogn)
 * 空间复杂度：O(1)
 */
class Solution {
    public int hIndex(int[] citations) {
        Arrays.sort(citations);
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

/**
 * Approach 2: Bucket Sort
 * H index的规定为：一个人在其所有学术文章中有N篇论文分别被引用了至少N次，他的H指数就是N。
 * 因此其 H index 不可能超过其文章总数 n.
 * 对此范围确定的情况，我们可以使用 桶排序 来解决这道问题。
 * 根据 H index 的定义，对于引用次数大于 N 的文章可以全部加到 buckets[N] 上（因为它肯定不会超过N）
 * 桶排序后，从后向前遍历求 postSum, 当其值大于 i 说明：
 * 引用次数大于 i 的文章数目 >= i.
 * 之所以从后向前遍历，是因为我们要求的是最大的值。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 参考：https://leetcode.com/problems/h-index/discuss/70768/Java-bucket-sort-O(n)-solution-with-detail-explanation?page=2
 */
class Solution {
    public int hIndex(int[] citations) {
        int n = citations.length;
        // buckets[count] 表示被引用次数为 count 的文章数目
        int[] buckets = new int[n + 1];
        for (int citation : citations) {
            if (citation >= n) {
                buckets[n]++;
            } else {
                buckets[citation]++;
            }
        }

        int postSum = 0;
        for (int i = n; i >= 0; i--) {
            postSum += buckets[i];
            if (postSum >= i) {
                return i;
            }
        }
        return 0;
    }
}
