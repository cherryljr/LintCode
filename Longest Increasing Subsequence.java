两种解法：

Solution 1: Sequence DP	O(N^2)
求最小值，各个元素不能swap / sort.  故想到使用动态规划解决. 
该解法容易想到，但是算法的时间复杂度为 O(N^2)
State:
	错误的方法：f[i]代表前i个数字中最长上升子序列长度
	正确的方法：f[i]代表前i个数组中以第i个数字结尾的最长上升子序列长度
Funciton:
	f[i] = Math.min(f[j]) + 1. 条件: j < i && A[j] < A[i]
Initialize:
	f[i] = 1
Answer:
	Math.max(f[0...n-1])
	
Solution 2: minLast Array & Binary Search  O(NlogN)
该解法是根据O(NlogN)的复杂度与Tag提示想到的。
首先我们需要引入最小结尾数组 minLast 这个概念：
    它和原本数组长度相同，但是分为 有效区（左边部分） 和 无效区（右边部分）。
    初始化时全部为无效区，随着数组的遍历，有效区将会逐渐扩大。
    有效区中：minLast[i]表示，所有长度为 i+1 的递增子序列中最小结尾的值。
做法：
    遍历数组 nums[], 当遍历到 nums[i] 时，去 minLast[] 的有效区中二分查找第一个比它大的数，
    若存在，更新该位置。若不存在，minLast[] 有效区扩大，将 nums[i] 写入有效区的下一个位置。
    
主要是使用二分查找法找到在minLast中第一个大于nums[i]的数。


/*
Given a sequence of integers, find the longest increasing subsequence (LIS).

You code should return the length of the LIS.

Example
For [5, 4, 1, 2, 3], the LIS  is [1, 2, 3], return 3

For [4, 2, 4, 5, 3, 7], the LIS is [4, 4, 5, 7], return 4

Challenge
Time complexity O(n^2) or O(nlogn)

Clarification
What's the definition of longest increasing subsequence?

    * The longest increasing subsequence problem is to find a subsequence of a given sequence in which the subsequence's elements are in sorted order, lowest to highest, and in which the subsequence is as long as possible. This subsequence is not necessarily contiguous, or unique.  

    * https://en.wikipedia.org/wiki/Longest_common_subsequence_problem

Tags Expand 
Binary Search LintCode Copyright Dynamic Programming
*/


//	Version 1: DP  O(N^2)
public class Solution {
    /**
     * @param nums: The integer array
     * @return: The length of LIS (longest increasing subsequence)
     */
    public int longestIncreasingSubsequence(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        // State
        int[] f = new int[nums.length];
        int max = 0;
        
        // Initialize
        for (int i = 0; i < nums.length; i++) {
            f[i] = 1;
        }
        
        // Function
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    f[i] = f[i] > f[j] + 1 ? f[i] : f[j] + 1;
                }
                //	利用max里存储f[0 ~ n-1]的最大值
                if (f[i] > max) {
                    max = f[i];
                }
            }
        }
        
        // Answer
        return max;
    }
}


//	Version 2: minLast Array & Bianry Search	O(NlogN)
public class Solution {
    /**
     * @param nums: The integer array
     * @return: The length of LIS (longest increasing subsequence)
     */
    public int longestIncreasingSubsequence(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // 初始化 最小结尾数组，全部为无效区
        int[] minLast = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            minLast[i] = Integer.MAX_VALUE;
        }
        
        for (int i = 0; i < nums.length; i++) {
            // find the first number in minLast >= nums[i]
            int index = binarySearch(minLast, nums[i]);
            minLast[index] = nums[i];
        }
        for (int i = nums.length - 1; i >= 0; i--) {
            if (minLast[i] != Integer.MAX_VALUE) {
                return i + 1;
            }
        }
        
        return 0;
    }
    
    // find the first number > num
    private int binarySearch(int[] minLast, int n) {
        int start = 0; 
        int end = minLast.length - 1;
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (minLast[mid] < n) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        
        return start;
    }
}
