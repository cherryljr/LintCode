与 Single Number 的解题思路相同，都用到了 抵消 的思想。
Single Number 利用 XOR 运算实现了相同数字的抵消，最后得到需要的那个数字。
而我们这里这需要求占了超过了数组一半的数字。
所以我们仍然利用 抵消 的思想：
	取两个数，若这两个数相等，则 count++
	否则将这两个数remove, count--.
	因为Majority Number 至少占一半以上，所以必定会被剩下。（数量多就是任性...随便 remvoe 都不怕）

Majority Number II，超1/3, 那么就分三份处理，三三不同的话抵消，countA, countB来计算最多出现的两个。

Majority Number III, 超1/k, 那么自然分k份.这里用到 HashMap.

/*
Description
Given an array of integers, the majority number is the number that occurs more than half of the size of the array. Find it.

Notice
You may assume that the array is non-empty and the majority number always exist in the array.

Example
Given [1, 1, 1, 1, 2, 2, 2], return 1

Challenge 
O(n) time and O(1) extra space

Tags 
Greedy LintCode Copyright Enumeration Zenefits
*/

public class Solution {
    /*
     * @param nums: a list of integers
     * @return: find a  majority number
     */
    public int majorityNumber(List<Integer> nums) {
    	// count 作用为计算当前情况下（抵消之后的）还剩下的相同数字的数目
    	// candidate 作用为储存当前情况下相同的数字
        int count = 0;
        int candidate = -1;
        
        for (int i : nums) {
            if (candidate == i) {
                count++;
            } else {
                if (count == 0) {
                    candidate = i;
                    count = 1;
                } else {
                    count--;
                }
            }
        }
        
        return candidate;
    }
}
