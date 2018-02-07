/*
Description
Give a number of arrays, find their intersection, and output their intersection size.

Notice
The total number of all array elements is not more than 500000.
There are no duplicated elements in each array.

Example
Given [[1,2,3],[3,4,5],[3,9,10]], return 1
explanation:
Only element 3 appears in all arrays, the intersection is [3], and the size is 1.

Given [[1,2,3,4],[1,2,5,6,7][9,10,1,5,2,3]], return 2
explanation:
Only element 1,2 appear in all arrays, the intersection is [1,2], the size is 2.
 */

/**
 * Approach: HashMap
 * 题目想要求的是在所有数组中都出现了的元素个数，并且各个数组中不包含重复元素。
 * 因此我们可以对所有数据进行一次遍历，然后存储在一个 HashMap 中。
 * HashMap结构为 <K, V>, K:为出现的元素值, V:为对应元素的出现次数。
 * 然后遍历该 HashMap 看里面有几个出现次数等于 数组个数 的元素即可。
 */
public class Solution {
    /**
     * @param arrs: the arrays
     * @return: the number of the intersection of the arrays
     */
    public int intersectionOfArrays(int[][] arrs) {
        if (arrs == null || arrs.length == 0) {
            return 0;
        }

        // 遍历全部元素，将各个元素及其对应的出现次数存储在 HashMap 中
        Map<Integer, Integer> map = new HashMap<>();
        for (int[] arr : arrs) {
            for (int temp : arr) {
                int count = map.getOrDefault(temp, 0) + 1;
                map.put(temp, count);
            }
        }

        int rst = 0;
        // 遍历 HashMap,看里面有几个 出现次数 等于 数组个数 的元素即可
        // (注意：因为本题中各个数组中均没有 重复元素，所以可以使用该方法）
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            // System.out.println(entry.getKey() + " " + entry.getValue());
            if (entry.getValue() == arrs.length) {
                rst++;
            }
        }
        return rst;
    }
}