/*
Description
Given an array arr and a positive integer k, you need to find a continuous array from this array so that the sum of this array is k.
Output the length of this array. If there are multiple such arrays,return the output starts with the smallest position,
if there are more than one, the output ending position is the smallest.If no such sub-array can be found, return -1.

The length of the array does not exceed 10^6 , each number in the array is less than or equal to 10^6, and k does not exceed 10^6.

Example
Given arr=[1,2,3,4,5], k=5, return 2.
Explanation:
In this array, the earliest contiguous substring is [2,3].

Give arr=[3,5,7,10,2], k=12, return 2.
Explanation:
In this array, the earliest consecutive concatenated substrings with a sum of 12 are [5,7].
 */

/**
 * Approach: Prefix Sum + HashMap
 * 与 Maximum Size Subarray Sum Equals k 基本一样，加了点小条件罢了。
 *
 * 详细解析可以参考：
 *  https://github.com/cherryljr/LintCode/blob/master/Maximum%20Size%20Subarray%20Sum%20Equals%20k.java
 */
public class Solution {
    /**
     * @param arr: The array
     * @param k: the sum
     * @return: The length of the array
     */
    public int searchSubarray(int[] arr, int k) {
        Map<Long, Integer> map = new HashMap<>();
        map.put(0L, -1);
        long preSum = 0L;
        int len = Integer.MAX_VALUE, start = arr.length;
        for (int i = 0; i < arr.length; i++) {
            preSum += arr[i];
            if (!map.containsKey(preSum)) {
                map.put(preSum, i);
            }
            if (map.containsKey(preSum - k)) {
                int position = map.get(preSum - k);
                if (position < start || (position == start && i - position < len)) {
                    start = position;
                    len = i - position;
                }
            }
        }
        return len == Integer.MAX_VALUE ? -1 : len;
    }
}