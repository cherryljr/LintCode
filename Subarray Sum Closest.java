/*
Description
Given an integer array, find a subarray with sum closest to zero. Return the indexes of the first number and last number.

Example
Given [-3, 1, 1, -3, 5], return [0, 2], [1, 3], [1, 1], [2, 2] or [0, 4].

Challenge 
O(nlogn) time

Tags 
Sort Subarray
*/

/**
 * Approach: PreSum + Sorting
 * 涉及到 Subarray Sum 问题，首先联想到的就是要使用 Prefix Sum 来解决问题。
 * 该题是在 Subarray Sum 基础上的扩展 / Maximum Size Subarray Sum Equals k 的变形。
 * https://github.com/cherryljr/LintCode/blob/master/Maximum%20Size%20Subarray%20Sum%20Equals%20k.java
 *
 * 思路：
 * 在 Subarray Sum 中，我们通过了 sum[0~a] = sum[0~b] = x 得到了 sum(a ~ b] = 0.
 * 而本题需要的是与 0 最相近的。故同样借助 Prefix Sum,只不过我们需要的是将各个 Prefix Sum
 * 之间的差值尽可能小地排列在一起方便我们查找。而能做到这点的便是 排序。
 * 因此我们这里不再使用 HashMap 而是使用了 Array 来实现。
 * 但是因为 Array 无法像 HashMap 储存一个对应的键值对，所以我们需要新建一个类 Point 来储存它们。
 * （当然使用简单的二维数组也可以，这边是为了使得代码更加容易理解）
 * 
 * 做法：
 *  1. 新建 Point 类，包含 preSum 和 index。
 *  2. 遍历整个数组，算出每个位置上的 preSum 以及对应的 index 并保存到 map 中。
 *  3. 对所有得到的 preSum，依据 preSum 的大小进行 Sort (O(nlogn))
 *  4. 遍历得到的 map 数组，通过 map[i].preSum - map[i-1].preSum 来比较得到最接近 0 的 Subarray
 *  5. 最后对 index 进行一次 sort 便可以得到最后答案。
 */
public class Solution {

    class Point {
        int preSum;
        int index;

        Point(int preSum, int index) {
            this.preSum = preSum;
            this.index = index;
        }
    }

    /*
     * @param nums: A list of integers
     * @return: A list of integers includes the index of the first number and the index of the last number
     */
    public int[] subarraySumClosest(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new int[]{};
        }

        Point[] map = new Point[nums.length + 1];
        // 初始化 map (当一个元素都没有的时候，preSum为0，index为-1)
        map[0] = new Point(0, -1);
        int preSum = 0;
        // 因为我们需要对 preSum 进行排序，所以使用 数组 来存储preSum和相应的位置.
        for (int i = 0; i < nums.length; i++) {
            preSum += nums[i];
            map[i + 1] = new Point(preSum, i);
        }

        // 对 Point 按从小到达的顺序进行排序
        Arrays.sort(map, (a, b) ->
                a.preSum == b.preSum ? a.index - b.index : a.preSum - b.preSum);
        int[] rst = new int[2];
        int diff = Integer.MAX_VALUE;
        for (int i = 1; i < map.length; i++) {
            if (map[i].preSum - map[i - 1].preSum < diff) {
                // 当发现 相邻差值更小的 preSum 时，更新 diff 和 子数组左右边界
                diff = map[i].preSum - map[i - 1].preSum;
                int[] temp = new int[]{map[i].index, map[i - 1].index};
                Arrays.sort(temp);
                rst[0] = temp[0] + 1;
                rst[1] = temp[1];
            }
        }

        return rst;
    }
}
