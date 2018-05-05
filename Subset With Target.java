/*
Description
Give an array and a target. We need to find the number of subsets which meet the following conditions:
The sum of the minimum value and the maximum value in the subset is less than the target.

Notice
The length of the given array does not exceed 50.
target <= 100000.

Example
Give array = [1,5,2,4,3], target = 4, return 2.
Explanation:
Only subset [1],[1,2] satisfy the condition, so the answer is 2.

Give array = [1,5,2,4,3], target = 5, return 5.
Explanation:
Only subset [1],[2],[1,3],[1,2],[1,2,3] satisfy the condition, so the answer is 5.

Tags
Facebook
 */

/**
 * Approach: Sorting + Sliding Window + Enumeration
 * 这是一道 Subset 类的问题，如果使用暴力解法的话，可以使用 DFS 枚举所有可能性。
 * 但是毫无疑问，这会超时。因此，我们可以对题目进行一些分析：
 * 题目需要求 Subset 中 最大值 和 最小值 之和小于 target.
 * 那么一旦涉及到这些大小问题，又不限制顺序，我们通常会对其进行排序。
 * （不管是为了找思路，还是优化运行速度，这种情况下排序都是非常好的选择）
 *
 * 排序后，我们得到了一个 SortedArray.然后按照有序数组来进行分析。
 * 此时，因为已经排序过的原因，如果前面部分的 subset 不满足条件的话，后面的直接就不用考虑了。
 * 因此我们利用两个Point: start, end 从头开始对其进行遍历 SubArray. (即维护一个 Sliding Window)
 * 每个 SubArray 这必须包含 nums[start]，即以 nums[start] 作为开头.
 * （这个做法在解决 SubArray 问题时，经常会被使用到哦，即：枚举所有以 nums[i] 作为开头/结尾的 SubArray）
 * 每次 end 向后移动直到 nums[start] + nums[end] >= target.
 * 那么 [start, end] 之间的数，我们可以任意选择 (start, end] 之间的任意数，他们都是满足条件的。
 * 因此总共的方法数有：2^(end-start) 种方法。
 * （除了nums[start],每个数都可以有 选取 和 不选取 两种选择）
 *
 * 同样应用了这个枚举思想的问题（当然下面问题考点还是不一样的）：
 * https://github.com/cherryljr/LintCode/blob/master/Maximum%20Size%20Subarray%20Sum%20Equals%20k.java
 *
 * 思考：如果是求 SubArray 而不是 SubSet 呢？
 * 提示可以使用 Deque 来解决，具体解决方案可以参考：
 * 最大值减去最小值小于等于k的子数组数量：
 * https://github.com/cherryljr/NowCoder/blob/master/%E6%9C%80%E5%A4%A7%E5%80%BC%E5%87%8F%E5%8E%BB%E6%9C%80%E5%B0%8F%E5%80%BC%E5%B0%8F%E4%BA%8E%E7%AD%89%E4%BA%8Ek%E7%9A%84%E5%AD%90%E6%95%B0%E7%BB%84%E6%95%B0%E9%87%8F.java
 */
public class Solution {
    /**
     * @param nums: the array
     * @param target: the target
     * @return: the number of subsets which meet the following conditions
     */
    public long subsetWithTarget(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0L;
        }

        Arrays.sort(nums);
        long rst = 0L;
        for (int start = 0; start < nums.length; start++) {
            int end = start;
            // 如果当前 最小值nums[start] + 下一个最大值nums[end+1] 仍小于 target
            // 说明 end 仍然可以向后扩
            while (end + 1 < nums.length && nums[start] + nums[end + 1] < target) {
                end++;
            }
            // 如果以 nums[start] 作为最小值的 subset 存在的话
            if (nums[start] + nums[end] < target) {
                rst += ((long)1 << (end - start));
            }
        }

        return rst;
    }
}