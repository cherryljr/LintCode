/*
Description
If an array contains only one number that has an odd number of times, the array is valid, otherwise the array is invalid.
Enter an array a containing only positive integers to determine if the array is valid.
Return the number which has odd number of times if it is valid, return -1 if it is invalid.

The length of the array does not exceed 10^5, each number in the array is less than or equal to 10^9​

Example
Given a=[1,1,2,2,3,4,4,5,5], return 3.
Explanation:
In this array, only 3 has odd number of times, so return 3.

Given a=[1,1,2,2,3,4,4,5], return -1.
Explanation:
In this array, 3 and 5 have odd number of times, so it is invalid and return -1.
 */

/**
 * Approach 1: HashMap
 * 非常直观的做法，直接使用 HashMap 记录每个数的出现次数。
 * 然后再遍历一遍找出有几个出现次数为奇数的数即可。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 */
public class Solution {
    /**
     * @param a: The array.
     * @return: The number which has odd number of times or -1.
     */
    public int isValid(List<Integer> a) {
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer num : a) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        int rst = -1;
        int count = 0;  // 计算有多少个出现奇数次的数
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if ((entry.getValue() & 1) == 1) {
                rst = entry.getKey();
                count++;
            }
        }
        return count == 1 ? rst : -1;
    }
}

/**
 * Approach 2: Bit Operation (XOR)
 * 这道题目很容易就让我们想到 Single Number.
 * 同样我们可以利用 异或 的性质来解决这道问题。
 * 首先求出全部元素的异或和。
 * 然后再遍历一遍数组，查看有几个元素与 XOR 相同。
 * 如果出现次数为 奇数 则返回 XOR，否则返回 -1.
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
public class Solution {
    /**
     * @param a: The array.
     * @return: The number which has odd number of times or -1.
     */
    public int isValid(List<Integer> a) {
        int XOR = 0;
        for (int num : a) {
            XOR ^= num;
        }

        int count = 0;
        for (int num : a) {
            if (num == XOR) {
                count++;
            }
        }
        return (count & 1) == 1 ? XOR : -1;
    }
}