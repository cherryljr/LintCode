/*
Given a set of distinct integers, return all possible subsets.

Example
If S = [1,2,3], a solution is:

[
  [3],
  [1],
  [2],
  [1,2,3],
  [1,3],
  [2,3],
  [1,2],
  []
]

Note
Elements in a subset must be in non-descending order.
The solution set must not contain duplicate subsets.
Challenge
Can you do it in both recursively and iteratively?

Tags Expand 
Recursion Facebook Uber
*/

/**
 * Approach 1: DFS / Backtracking
 * DFS / Backtracking 模板
 * 用 startIndex 来 track 到哪一步。
 * 关于何时需要进行 Backtracking 以及 Backtracking 的相关分析?
 * 请参见:
 * Word Search
 * https://github.com/cherryljr/LintCode/blob/master/Word%20Search.java
 */
class Solution {
    /**
     * @param nums: A set of numbers.
     * @return: A list of lists. All valid subsets.
     */
    public ArrayList<ArrayList<Integer>> subsets(int[] nums) {
        ArrayList<ArrayList<Integer>> results = new ArrayList<>();
        if (nums == null) {
            return results;
        }
        if (nums.length == 0) {
            results.add(new ArrayList<Integer>());
            return results;
        }

        Arrays.sort(nums);
        helper(new ArrayList<Integer>(), nums, 0, results);
        return results;
    }


    // 递归三要素
    // 1. 递归的定义：在 Nums 中找到所有以 subset 开头的的集合，并放到 results
    private void helper(ArrayList<Integer> subset,
                        int[] nums,
                        int startIndex,
                        ArrayList<ArrayList<Integer>> results) {
        // 2. 递归的拆解
        // deep copy
        // results.add(new ArrayList<>(subset)); 记住这里要new一个List空间用来存储结果才行
        // 这里是遍历所有的子集，所以无需判断条件
        // 其他条件下，需要判断遍历得到的答案是否满足条件，满足的话将其add到resuts中，
        // 并根据实际情况判断是否需要 return 结束当前调用，来节省相应的时间
        // 可以直接 return 的有：Combination Sum 系列: https://github.com/cherryljr/LintCode/blob/master/Combination%20Sum.java
        // 不可以直接 return 的有：Word Ladder II: https://github.com/cherryljr/LeetCode/blob/master/Word%20Ladder%20II.java
        results.add(new ArrayList<Integer>(subset));

        //  i表示当前loop要取的元素的下标，startIndex表示从该元素开始取.
        //  有些题目中可能会限制i与starIndex的关系.
        for (int i = startIndex; i < nums.length; i++) {
            // [1] -> [1,2]
            // 其他题目中可能存在不符合条件的情况，需要加上一个if语句来判断是否要add到subset中
            subset.add(nums[i]);
            // 寻找所有以 [1,2] 开头的集合，并扔到 results
            // 注意这里递归传入的是 i+1 表示 startIndex 从下一个位置开始
            helper(subset, nums, i + 1, results);
            // [1,2] -> [1]  回溯
            subset.remove(subset.size() - 1);
        }

        // 3. 递归的出口
    }
}

/**
 * Approach 2: Bit Manipulation
 * 思路就是使用 一个正整数 二进制表示 的第i位是1还是0来代表 集合的第i个数 取或者不取。
 * 所以从 0 到 2^n-1 总共 2^n 个整数，正好对应集合的 2^n 个子集。
 * 过程就是 整数 => 二进制 => 对应集合。
 * 当我们判断某一位上的数取或者不取时，仍然可以用到位运算来实现。
 * 只需要用 1 和那一位上的数进行 与操作 即可。
 *
 * 该做法在 High Capacity Backpack 中就使用到了（折半枚举）
 * https://github.com/cherryljr/LintCode/blob/master/High%20Capacity%20Backpack.java
 */
public class Solution {

    /*
     * @param nums: A set of numbers
     * @return: A list of lists
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> rst = new ArrayList<>();
        if (nums == null) {
            return rst;
        }
        Arrays.sort(nums);

        // 1 << n is 2^n
        // each subset equals to an binary integer between 0 .. 2^n - 1
        // 0 -> 000 -> []
        // 1 -> 001 -> [1]
        // 2 -> 010 -> [2]
        // ..
        // 7 -> 111 -> [1,2,3]
        int len = nums.length;
        for (int i = 0; i < (1 << len); i++) {
            List<Integer> temp = new ArrayList<>();
            for (int j = 0; j < len; j++) {
                // 取 i 的第 j 位上的数，判断是否为 0 => 取不取第 j 位上的数
                if ((i & (1 << j)) != 0) {
                    temp.add(nums[j]);
                }
            }
            rst.add(temp);
        }

        return rst;
    }
}
