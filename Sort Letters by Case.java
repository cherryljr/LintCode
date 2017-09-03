与 Partition Array 是相同的解法
思路：
	快速排序的思想，整个程序其实就是快速排序过程中的一次排序罢了。

	利用两个指针left和right分别指向Array的起始和末尾。

	利用while循环，left指针从左向右遍历，直到left指向的节点为 UpperCase.
	同理right指针从右向左遍历，直到right指向的节点为 LowerCase.

	交换left和right节点，直达left与right两个节点相遇或者相交。

	算法复杂度为：O(N)

/*
Description
Given a string which contains only letters. Sort it by lower case first and upper case second.

Notice
It's NOT necessary to keep the original order of lower-case letters and upper case letters.

Example
For "abAcD", a reasonable answer is "acbAD"

Challenge 
Do it in one-pass and in-place.

Tags 
Sort LintCode Copyright String Two Pointers
*/

public class Solution {
    /** 
     *@param chars: The letter array you should sort by Case
     *@return: void
     */
    public void sortLetters(char[] chars) {
        int left = 0;
        int right = chars.length - 1;
        while (left < right) {
            while (left < right && Character.isLowerCase(chars[left])) {
                left++;
            }
            while (left < right && Character.isUpperCase(chars[right])) {
                right--;
            }
            
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
    }
}
