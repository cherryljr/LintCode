/*
Description
Given the root of a tree, you are asked to find the most frequent subtree sum.
The subtree sum of a node is defined as the sum of all the node values formed by the subtree rooted at that node (including the node itself).
So what is the most frequent subtree sum value? If there is a tie,
return all the values with the highest frequency in any order.

You may assume the sum of values in any subtree is in the range of 32-bit signed integer.

Example
Examples 1
Input:
  5
 /  \
2   -3
return [2, -3, 4], since all the values happen only once, return all of them in any order.

Examples 2
Input:
  5
 /  \
2   -5
return [2], since 2 happens twice, however -5 only occur once.
 */

/**
 * Approach: Divide and Conquer
 * 采用分治的方法，递归算出每个结点的子树和。
 * 然后使用 map 记录下来即可。后面的流程就十分清晰了...
 *
 * 与这道题目相似的还有 Equal Tree Partition, 他们都用到了相同的做法：
 * https://github.com/cherryljr/LintCode/blob/master/Equal%20Tree%20Partition.java
 */

/**
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int val;
 *     public TreeNode left, right;
 *     public TreeNode(int val) {
 *         this.val = val;
 *         this.left = this.right = null;
 *     }
 * }
 */
public class Solution {
    /**
     * @param root: the root
     * @return: all the values with the highest frequency in any orider
     */
    // map wil contain all subtree sum values and their frequency
    private Map<Integer, Integer> map = new HashMap<>();
    // record the maximum frequency
    private int maxFreq = 0;

    public int[] findFrequentTreeSum(TreeNode root) {
        if (root == null) {
            return new int[]{};
        }

        getSum(root);
        List<Integer> list = new ArrayList<>();
        // find all values with maximum frequency
        for (Integer num : map.keySet()) {
            if (map.get(num) == maxFreq) {
                list.add(num);
            }
        }

        // convert list to array
        int[] rst = new int[list.size()];
        int index = 0;
        for (Integer num : list) {
            rst[index++] = num;
        }
        return rst;
    }


    // Divide and Conquer
    // increment the frequency of the subtree sum value in the map, and return the subtree sum value
    private int getSum(TreeNode root) {
        int sum = root.val;
        if (root.left != null) {
            sum += getSum(root.left);
        }
        if (root.right != null) {
            sum += getSum(root.right);
        }
        map.put(sum, map.getOrDefault(sum, 0) + 1);
        maxFreq = map.get(sum) > maxFreq ? map.get(sum) : maxFreq;
        return sum;
    }
}