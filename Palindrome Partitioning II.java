最小值问题，Source为字符串，不能随意更换位置 => Sequence DP

State:
	f[i]表示前i个字符最少需要多少次cut才能被分割为回文子字符串 (最少被分割为多少个回文子字符串 - 1)
Function:
	f[i] = Math.min(f[j] + 1), ( j < i & j + 1 ~ i这一段字符串是一个回文串) 
Initialize: 
	f[0] = -1, f[i] = i – 1 (因为长度为i的字符串最多可以进行i – 1次cut)
Answer:
	f[s.length()], 即f[n].
	
注意点：对于一个长度为n的字符串初始化时我们常常需要开辟 n + 1 的数组空间，把0位留出来。
因为定义是前i个字符，故存在定义：”前0个字符”，即空串。而这是不能被忽略掉的，因为有许多结果是从这里得到的。
这样答案的结果也就是f[s.length()] => f[n].

该题可优化的点在于我们可以事先就将 0~i 是否为回文串进行判断，而不是将其放到DP的for循环中。
因为isPalindrome操作的复杂度为O(N),如果将其写DP的Function中的for loop中会使得程序的复杂度
由原来的 O(N^2) 变成 O(N^3)

判断一个字符串各个子串是否为回文串并储存实际上也是一个DP问题。算法复杂度为：O(N^2)
State:
	f[m][n]表示字符串从 m~n 是否是一个回文串
Function:
	当f[m-1][n-1]的子串为回文串，并且A[m] == A[n] 的时候, f[m][n]为 true 
	即 f[m - 1][n - 1] == true && s.charAt(m) == s.charAt(n)
Initialize: 
	每个字母其本身都是一个回文串，故令f[i][i] = true
Answer:
	f

/*
Given a string s, cut s into some substrings such that every substring is a palindrome.
Return the minimum cuts needed for a palindrome partitioning of s.
Example
For example, given s = "aab",
Return 1 since the palindrome partitioning ["aa","b"] could be produced using 1 cut.
Tags Expand 
Dynamic Programming

Thinking process:
DP problem.
Use a isPal to record if any [i ~ j] is Palindrome, true / false
    for any char s[i] and s[j], if s[i] == s[j], then need to check if [i + 1, j - 1] is Palindrome, which is just isPal[i + 1, j - 1].
Use cut[j] to record the minimal cut from char index [0 ~ j] 
    by default, cut[j] = j because the worst condition is cut j times at each charactor: none 2+ character palindrome, and split into individual chars.
    update cut[j] by comparing existing cut[j] and (cut[i - 1] + 1).
At the end, return cut[s.length() - 1].
*/

/*
version 1
f[i] 表示前i个字母，最少被切割几次可以切割为都是回文串。
最后return f[n]
*/

public class Solution {
    /**
     * @param s a string
     * @return an integer
     */
    public int minCut(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        boolean[][] isPalindrome = getIsPalindrome(s);
        
        // State
        int[] count = new int[s.length() + 1];
        
        // Initialize
        for (int i = 0; i <= s.length(); i++) {
            count[i] = i - 1;
        }
        
        // Function
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
            //  if (isPalindrome(s, j, i - 1)), 如果使用该方法将使得算法复杂度增加至O(N^3)
            //  故事先计算出是否为回文串的值，然后直接查阅即可，而查阅的复杂的是O(1)的.
                if (isPalindrome[j][i - 1]) {
                    count[i] = Math.min(count[i], count[j] + 1);
                }
            }
        }
        
        // Answer
        return count[s.length()];
    }
    
    //	不使用该方法
    private boolean isPalindrome(String s, int start, int end) {
        for (int i = start, j = end; i < j; i++, j--) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
        }
        return true;
    }
    
    //	事先判断好是否为回文串， 也是一个DP的解法
    private boolean[][] getIsPalindrome(String s) {
    	  // State
        boolean[][] isPalindrome = new boolean[s.length()][s.length()];
				
				// Initialize
        for (int i = 0; i < s.length(); i++) {
            isPalindrome[i][i] = true;
        }
        for (int i = 0; i < s.length() - 1; i++) {
            isPalindrome[i][i + 1] = (s.charAt(i) == s.charAt(i + 1));
        }
				
				// Function
        for (int length = 2; length < s.length(); length++) {
            for (int start = 0; start + length < s.length(); start++) {
                isPalindrome[start][start + length]
                    = isPalindrome[start + 1][start + length - 1] && s.charAt(start) == s.charAt(start + length);
            }
        }

        return isPalindrome;
    }
};

/*
version 2
f[i] 表示前i个字母，最少可以被分割为多少个回文串
最后return f[n] - 1
*/
public class Solution {
    private boolean[][] getIsPalindrome(String s) {
        boolean[][] isPalindrome = new boolean[s.length()][s.length()];

        for (int i = 0; i < s.length(); i++) {
            isPalindrome[i][i] = true;
        }
        for (int i = 0; i < s.length() - 1; i++) {
            isPalindrome[i][i + 1] = (s.charAt(i) == s.charAt(i + 1));
        }

        for (int length = 2; length < s.length(); length++) {
            for (int start = 0; start + length < s.length(); start++) {
                isPalindrome[start][start + length]
                    = isPalindrome[start + 1][start + length - 1] && s.charAt(start) == s.charAt(start + length);
            }
        }

        return isPalindrome;
    }

    public int minCut(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        // State
        boolean[][] isPalindrome = getIsPalindrome(s);
        
        // Initialize
        int[] f = new int[s.length() + 1];
        f[0] = 0;
        
        // Function
        for (int i = 1; i <= s.length(); i++) {
            f[i] = Integer.MAX_VALUE; // or f[i] = i
            for (int j = 0; j < i; j++) {
                if (isPalindrome[j][i - 1]) {
                    f[i] = Math.min(f[i], f[j] + 1);
                }
            }
        }
				
				// Answer
        return f[s.length()] - 1;
    }
}
