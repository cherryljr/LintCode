/*
Description
Given an integer array, find a subarray where the sum of numbers is zero.
Your code should return the index of the first number and the index of the last number.

Notice
There is at least one subarray that it's sum equals to zero.

Example
Given [-3, 1, 2, -3, 4], return [0, 2] or [1, 3].

Tags
Hash Table Subarray
 */

/**
 * Approach: PreSum + HashMap
 * 累加和为K的最长子数组 的简化版。
 * https://github.com/cherryljr/NowCoder/blob/master/%E7%B4%AF%E5%8A%A0%E5%92%8C%E4%B8%BAK%E7%9A%84%E6%9C%80%E9%95%BF%E5%AD%90%E6%95%B0%E7%BB%84.java
 * 此处 K = 0,并且题目默认只有一个子串和为 K.
 * 我们只需要求出子串的 左右边界即可。
 * 同样的利用 preSum + HashMap 来优化我们的时间复杂度。
 * map 中的 key 为 preSum, value 为对应的 index.
 * 当 map.containsKey(preSum - 0) 说明 preSum 在这之前已经被累加出来过,
 * 这也就说明从 [map.get(preSum)+1...i] 这段子数组的累加和为 0.
 * 注意：
 *  我们需要对 map 进行一次初始化，即将 (0, -1) 加到表中，表示当数组中没有元素是 preSum 已经出现了，index 为 -1.
 *  这样可以保证头元素被考虑进去。如[-1, 1]
 *
 * 时间复杂度为：O(n)
 * 空间辅助度为：O(n)
 */
public class Solution {
    /**
     * @param nums: A list of integers
     * @return: A list of integers includes the index of the first number and the index of the last number
     */
    public List<Integer> subarraySum(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }

        ArrayList<Integer> rst = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        // Initialize
        map.put(0, -1);     // very important
        int preSum = 0;

        // we know that sub-array (a,b) has zero sum if Sum(0 ... a-1) = Sum(0 ... b)
        for (int i = 0; i < nums.length; i++) {
            preSum += nums[i];
            if (map.containsKey(preSum)) {
                rst.add(map.get(preSum) + 1);   // left bound
                rst.add(i);                     // right bound
                return rst;
            }
            map.put(preSum, i);
        }

        return rst;
    }
}