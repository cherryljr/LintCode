/*
Description
Given a string(Given in the way of char array), a right offset and a left offset, rotate the string by offset in place.
(left offest represents the offset of a string to the left,right offest represents the offset of a string to the right,
the total offset is calculated from the left offset and the right offset,split two strings at the total offset and swap positions)。

Example
Example 1:
Input：str ="abcdefg", left = 3, right = 1
Output："cdefgab"
Explanation：The left offset is 3, the right offset is 1, and the total offset is left 2. Therefore, the original string moves to the left and becomes "cdefg"+ "ab".

Example 2:
Input：str="abcdefg", left = 0, right = 0
Output："abcdefg"
Explanation：The left offset is 0, the right offset is 0, and the total offset is 0. So the string remains unchanged.

Example 3:
Input：str = "abcdefg",left = 1, right = 2
Output："gabcdef"
Explanation：The left offset is 1, the right offset is 2, and the total offset is right 1. Therefore, the original string moves to the left and becomes "g" + "abcdef".
 */

/**
 * Approach: Calculate the offset and MOD Length
 * 因为本题涉及到了 offset，因此需要注意到：offset可能大于 len.
 * 其次，因为是环形操作，所以右移和左移其实是可以相互替代的，这使得我们可以对代码进行简化。
 * eg. left = 1, right = 3, str = "abcdefg"
 * ==> left - right = -2，因此需要向右移动 2 ==> "fgabcde"
 * ==> 等同与向左移动 -2+len(str) = 5        ==> "fgabcde"
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 * 
 * PS.本题还有额外空间为O(1)的做法，使用三步翻转法即可。不知道的可以参考：
 * https://github.com/cherryljr/LeetCode/blob/master/Rotate%20Array.java
 */
public class Solution {
    /**
     * @param str: An array of char
     * @param left: a left offset
     * @param right: a right offset
     * @return: return a rotate string
     */
    public String RotateString2(String str, int left, int right) {
        int len = str.length();
        // 第一次取模：(left-right)%len 的作用为处理 offset > len 的情况
        // 第二次驱魔：(offset+len)%len 的作用为将所有移动操作转换成左移操作（原来的右移操作应该为负数）
        int offset = ((left - right) % len + len) % len;
        return str.substring(offset) + str.substring(0, offset);
    }
}