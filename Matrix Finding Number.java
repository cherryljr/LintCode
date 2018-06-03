/*
Description
A matrix mat is given to find out all the numbers that appear in the row.
If there are multiple, return the minimum number . If not, return -1.

The size of the matrix is n * m ,n * m <= 10^6.
Each number is a positive integer that is not more than 100000.

Example
Given mat = [[1,2,3],[3,4,1],[2,1,3]], return 1.
Explanation:
1 and 3 appear every line, 1 to 3 smaller.

Given mat = [[1,2,3],[3,4,2],[2,1,8]], return 2.
Explanation:
2 appears in every row of the matrix.
 */

/**
 * Approach: HashMap
 * 简单地考察 HashMap 的使用。
 * 思路：
 *  用 Map 来记录每个数以及对应出现在第几行的行数。
 *  只有当当前出现并且上一行也出现的时候，Map中的 value(行号) 才会被更新成当前行。
 *  如果是第一行，那么直接 put 到 Map 中即可。
 *  最后只需要遍历一遍Map,检查每个 key 所对应的 value(行号),
 *  如果 行号 到了最后一行，那么说明该元素在每一行都出现过了。
 *  取所有的最小值即可。
 *
 * 时间复杂度：O(N^2)
 */
public class Solution {
    /**
     * @param mat: The matrix
     * @return: The answer
     */
    public int findingNumber(int[][] mat) {
        // Key:矩阵中的数数值;  Value:该数值所在的行
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                if (map.containsKey(mat[i][j])) {
                    // 只有上一行也出现了该值，才更新
                    if (map.get(mat[i][j]) == i - 1) {
                        map.put(mat[i][j], i);
                    }
                } else if (i == 0) {
                    // 如果为第一行，直接put进去
                    map.put(mat[i][j], 0);
                }
            }
        }

        int rst = Integer.MAX_VALUE;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            // 只有行号到达最后一行的元素，才是在每行都出现过的元素
            if (entry.getValue() == mat.length - 1) {
                rst = Math.min(rst, entry.getKey());
            }
        }
        return rst == Integer.MAX_VALUE ? -1 : rst;
    }
}