/*
Description
Given a string str, we need to extract the symbols and words of the string in order.

Notice
The length of str does not exceed 10000.
The given str contains only lowercase letters, symbols, and spaces.

Example
Given str = "(hi (i am)bye)"，return ["(","hi","(","i","am",")","bye",")"].

Explanation:
Separate symbols and words.
Given str = "#ok yes"，return ["#","ok","yes"]。

Explanation:
Separate symbols and words.
Given str = "##s"，return ["#","#","s"]。

Explanation:
Separate symbols and words.

Tags
Snapchat
 */

/**
 * Approach: StringBuilder
 * 将字符串转换成 字符数组，然后挨个处理即可。
 * 题目有个地方没说明清楚...
 * 即当输入全部都是 空格 的时候，输出是什么...
 * 12周周赛题...巨坑啊！！！
 */
class Solution {
    /**
     * @param str: The input string
     * @return: The answer
     */
    public String[] dataSegmentation(String str) {
        if (str == null || str.length() == 0) {
            return new String[]{};
        }

        char[] chars = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= 'a' && chars[i] <= 'z') {
                // 如果遇到字符，则利用 while loop 将单词提取出来
                StringBuilder temp = new StringBuilder();
                while (i < chars.length && chars[i] >= 'a' && chars[i] <= 'z') {
                    temp.append(chars[i++]);
                }
                sb.append(temp + " ");
                i--;    // i 记得回退一个位置
            } else if (chars[i] == ' '){
                // 跳过空格
                continue;
            } else {
                // 其他字符直接记录即可
                sb.append(chars[i] + " ");
            }
        }

        sb.trimToSize();
        String[] rst = sb.toString().split(" ");
        // 如果字符串中全部都是空格的话...我们需要返回的是 [] 而不是 ""
        // 这里题目没有说明...真的是太坑了！！！
        if (rst == null || rst.length == 0 || (rst.length == 1 && rst[0].equals(""))) {
            return new String[]{};
        }
        return rst;
    }
}