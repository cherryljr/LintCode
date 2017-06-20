几种不同的方法flip：   
坑： 1. 结尾不能有空格。 2. 注意，如果Input是 ‘ ’的话，split以后就啥也没有了。check split以后 length == 0

另一个题目Reverse Words in String (char[]) 可以in-place，因为条件说char[]里面是没有首尾空格,所以更加简单.

```
/*
Given an input string, reverse the string word by word.

For example,
Given s = "the sky is blue",
return "blue is sky the".

For C programmers: Try to solve it in-place in O(1) space.

click to show clarification.

Clarification:
What constitutes a word?
A sequence of non-space characters constitutes a word.
Could the input string contain leading or trailing spaces?
Yes. However, your reversed string should not contain leading or trailing spaces.
How about multiple spaces between two words?
Reduce them to a single space in the reversed string.
Hide Company Tags Bloomberg
Hide Tags String
Hide Similar Problems Reverse Words in a String II


*/

/*
    Thoughts:
    Have multiple two other ways to do it: 
        1. flip all,then flip each individual word; 
        2. break into parts and append from end to beginning.
    For simplicity of code, try the appending from behind.
*/
public class Solution {
    public static String reverseWords(String s) {
        //write your code
        if (s == null || s.length() == 0 || s.indexOf(" ") == -1) {
            return s;
        }
        
        String[] temp = s.split(" ");
        StringBuilder rst = new StringBuilder();
        
        for (int i = temp.length - 1; i >= 0; i--) {
                rst.append(temp[i] + " ");
        }
        
        return rst.toString();
    }
}




/*
Thinking Process: 
1. Reverse it like reversing a int array
2. Use Split into arrays.
3. When reversing, make sure not empty string ""
*/
public class Solution {
    /**
     * @param s : A string
     * @return : A string
     */
    public String reverseWords(String s) {
        String[] strs = s.split(" ");
        for (int i = 0, j = strs.length - 1; i < j; i++, j--) {
            String temp = new String(strs[j]);
            strs[j] = new String(strs[i]);
            strs[i] = temp;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strs.length; i++){
            if (strs[i].length() > 0) {
                sb.append(strs[i]);
                if (i < strs.length - 1) {
                    sb.append(" ");
                }
            }
        }
        return sb.toString();
    }
}


```