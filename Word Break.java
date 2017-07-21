该题在AC前我曾尝试过两种解法：
分别我DFS与DP，但是当TestCase中的Input过大时，都会出现问题，无法100% AC
(注：在88%时会出现问题)
故最后参考	http://www.jiuzhang.com/solution/word-break
使用了for loop来实现。
日后有更好的方法或者对于前面两者有更好的修改方案，我将后续补上。

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
Just use for loop to archieve it.
No Recursive
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
        if (s == null || s.length() == 0) {
            return true;
        }

        int maxLength = getMaxLength(dict);
        boolean[] canSegment = new boolean[s.length() + 1];

        canSegment[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            canSegment[i] = false;
            for (int lastWordLength = 1;
                     lastWordLength <= maxLength && lastWordLength <= i;
                     lastWordLength++) {
                if (!canSegment[i - lastWordLength]) {
                    continue;
                }
                String word = s.substring(i - lastWordLength, i);
                if (dict.contains(word)) {
                    canSegment[i] = true;
                    break;
                }
            }
        }

        return canSegment[s.length()];
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
Thought:
Use boolean to denote rst[i]: s[0,i-1] can be break to match dict. For the ease to explain, 
let's consider rst[i+1] with actually string s[0,i];
How to calculate rst[i+1]? 
    As long as there is at least 1 way to break s[0, i], that would work. so do a for loop to check on string s[0, i]:
    For each i, use another index j, j = 0 ~ i. If rst[j] works and s[j,i+1] is in dict, that makes rst[i+1] = true.
    
This way is not also a good way for this problem.
Because it will cost too much time when the Input is too large.
*/

public class Solution {
    public boolean wordBreak(String s, Set<String> dict) {
        if (s == null || dict.contains(s)) {
            return true;
        }
        
        boolean[] rst = new boolean[s.length() + 1];
        rst[0] = true;
        
        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j <= i; j++) {
                if (rst[j] && dict.contains(s.substring(j, i + 1))) {
                    rst[i + 1] = true;
                    break;
                }
            }
        }
        return rst[s.length()];
    }
}
