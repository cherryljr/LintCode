与 Majority Number 不同在于这边是 1/3. 故可以将原来的 两两抵消 改为 三三抵消。
这样最后剩下来的可能为两个数，故我们用 candidateA 与 candidateB 来储存他们。
然后再一次遍历数组，看 candidateA 与 candidateB 出现的次数谁更多，谁便是最后的结果

Note：
	不能直接比较 countA 与 countB, 因为他们代表的是当前情况下，即使被抵消之后的剩下的元素中
	相同的个数。

/*

Description
Given an array of integers, the majority number is the number that occurs more than 1/3 of the size of the array.
Find it.

Notice
There is only one majority number in the array.

Example
Given [1, 2, 1, 2, 1, 3, 3], return 1.

Challenge 
O(n) time and O(1) extra space.

Tags 
Greedy LintCode Copyright Enumeration Zenefits

*/

public class Solution {
    /*
     * @param nums: a list of integers
     * @return: The majority number that occurs more than 1/3
     */
    public int majorityNumber(List<Integer> nums) {
        int countA = 0;
        int countB = 0;
        int candidateA = -1;
        int candidateB = -1;
        
        // 注意判断顺序
        for (int i : nums) {
            if (candidateA == i) {
                countA++;
            } else if (candidateB == i) {
                countB++;
            } else if (countA == 0) {
                candidateA = i;
                countA = 1;
            } else if (countB == 0) {
                candidateB = i;
                countB = 1;
            } else {
                countA--;
                countB--;
            }
        }
        
        countA = countB = 0;
        for (int i : nums) {
            if (i == candidateA) {
                countA++;
            } else if (i == candidateB) {
                countB++;
            }
        }
        
        return countA > countB ? candidateA : candidateB;
    }
}