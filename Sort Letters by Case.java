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

/**
 * Approach: QuickSelect (Partition)
 * 与 Partition Array 完全相同。
 * 关于 Partition 函数的详细解析可以参考：
 * https://github.com/cherryljr/LintCode/blob/master/Sort%20Colors.java
 *
 * 算法时间复杂度：O(n)； 空间复杂度：O(1)
 */
public class Solution {
    /**
     *@param chars: The letter array you should sort by Case
     *@return: void
     */
    public void sortLetters(char[] chars) {
        if (chars == null || chars.length < 2) {
            return;
        }

        partition(chars, 0, chars.length - 1);
    }

    private void partition(char[] chars, int left, int right) {
        // 初始化 小于k 和 大于k 部分数组的边界
        int less = left - 1, more = right + 1;

        // 当 left 指针遇到 大于k部分数组的 左边界 时停止遍历
        while (left < more) {
            if (Character.isLowerCase(chars[left])) {
                swap(chars, ++less, left++);
            } else {
                swap(chars, --more, left);
            }
        }
    }

    private void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }
}
