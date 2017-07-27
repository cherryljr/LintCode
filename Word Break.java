该题在AC前我曾尝试过两种解法：
分别为DFS与DP，但是当TestCase中的Input过大时，都会出现问题，无法100% AC
故最后参考	http://www.jiuzhang.com/solution/word-break
在这之后，我们可以分析出来：当输入String非常大的时候，使用原来的DP算法就会出现超时的现象
而这正是由于我们循环的范围不当所导致的。
分析总结如下：
	字符串String与单词word是存在着不同点的，那就是它们的长度！
	字符串可以非常的长，但是一个单词长度是有限的。它不可能超过词典中最长英文单词的长度。
	所以，我们在for loop时，仅仅需要考虑 i - maxLength ~ i 内的范围即可，而不需要考虑 0 ~ i 全部的范围了。

改进后算法复杂度为：O(NL)
N: 字符串长度 	L：最长单词长度

/*
Given a string s and a dictionary of words dict, determine if s can be break into a space-separated 
sequence of one or more dictionary words.

Example
Given s = "lintcode", dict = ["lint", "code"].

Return true because "lintcode" can be break as "lint code".

Tags Expand 
String Dynamic Programming

*/

/*
Dynamic Programming

*/
public class Solution {
	  private int getMaxLength(Set<String> dict) {
        int maxLength = 0;
        for (String word : dict) {
            maxLength = Math.max(maxLength, word.length());
        }
        return maxLength;
    }
    
    public boolean wordBreak(String s, Set<String> dict) {
        if (s == null || dict.contains(s)) {
            return true;
        }
        int maxLength = getMaxLength(dict);
        
        // State
        boolean[] rst = new boolean[s.length() + 1];
        
        // Initialize
        rst[0] = true;
        for (int i = 1; i <= s.length(); i++) {
        	rst[i] = false;
        }
        
        // Function
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= maxLength && j <= i; j++) {
                if (rst[i - j] && dict.contains(s.substring(i - j, i))) {
                    rst[i] = true;
                    break;
                }
            }
        }
        
        // Answer
        return rst[s.length()];
    }
}

/*
DFS
just think as Palindrome Partitioning.
Use Recursive Research to deal with it.
But the way will cause StackOverflow when the input is too large.
*/

public class Solution {
    /**
     * @param s: A string s
     * @param dict: A dictionary of words dict
     */
    boolean result = false;

    public boolean wordBreak(String s, Set<String> dict) {
        // write your code here  
        if (s == null) {
            return result;
        }
        
        helper(s, dict, 0);
        return result;
    }
    
    private void helper(String s, Set<String> dict, int startIndex) {
        if (startIndex == s.length()) {
            result = true;
        }
        for (int i = startIndex; i < s.length(); i++) {
            if (!dict.contains(s.substring(startIndex, i + 1))) {
                continue;
            }
            helper(s, dict, i + 1);
        }
    }
}


/*
DP
State: 
	f[i]表示“前i”个字符能否被完美切分
Funciton：
  当f[j]能被完美切分，并且j < i && j+1 ~ i是一个词典当中的词，那么f[i] = true
Initialize:
	f[0] = true
Answer:
	f[s.length()]
  
This way is not also a good way for this problem.
Because it will cost too much time when the Input is too large.
*/

public class Solution {    
    public boolean wordBreak(String s, Set<String> dict) {
        if (s == null || dict.contains(s)) {
            return true;
        }

        // State
        boolean[] rst = new boolean[s.length() + 1];
        
        // Initialize
        rst[0] = true;
        for (int i = 1; i <= s.length(); i++) {
        	rst[i] = false;
        }
        
        // Function
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= i; j++) {
                if (rst[i - j] && dict.contains(s.substring(i - j, i))) {
                    rst[i] = true;
                    break;
                }
            }
        }
        
        // Answer
        return rst[s.length()];
    }
}