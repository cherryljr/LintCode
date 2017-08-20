思路一： 利用 HahsMap 实现
	给了我们一个字符串，让我们找出可以组成的最长的回文串的长度，由于字符顺序可以打乱，
	所以问题就转化为了求偶数个字符的个数，我们了解回文串的都知道，回文串主要有两种形式：
	一个是左右完全对称的，比如noon; 还有一种是以中间字符为中心，左右对称，比如bob，level等。
	那么我们统计出来所有偶数个字符的出现总和，然后如果有奇数个字符的话，我们取取出其最大偶数，
	然后最后结果加1即可。

思路二： 利用 HashSet 实现
	思路一是通过通过哈希表来建立字符串和各个字符出现次数的映射。
	不过我们可以在此基础上换一种思路：
	  1. 找出所有奇数个的字符。
	  我们采用的方法是使用一个set集合，如果遍历到的字符不在set中，那么就将其加入set，
	如果已经在set里了，就将其从set中删去，这样遍历完成后set中就是所有出现个数是奇数个的字符了。
	  2. s的长度减去 Math.max(0, hs.size() - 1), 求出最长回文串长度
	  那么为什么是减去二者的最大值呢？
	  原因是：如果没有出现个数是奇数个的字符，那么t的长度就是0，减1成了-1，那么s的长度只要减去0即可；
	如果有奇数个的字符，那么字符个数减1，就是不能组成回文串的字符，因为回文串最多允许一个不成对出现的字符.

/*
Description
Given a string which consists of lowercase or uppercase letters, find the length of the longest palindromes that can be built with those letters.
This is case sensitive, for example "Aa" is not considered a palindrome here.

Notice
Assume the length of given string will not exceed 1010.

Example
Given s = "abccccdd" return 7
One longest palindrome that can be built is "dccaccd", whose length is 7.

Tags 
Hash Table Amazon
*/

// Version 1: HashMap


// Version 2: HashSet
public class Solution {
    /**
     * @param s a string which consists of lowercase or uppercase letters
     * @return the length of the longest palindromes that can be built
     */
    public int longestPalindrome(String s) {
        if(s == null || s.length() == 0) {
            return 0;
        }
        
        HashSet<Character> hs = new HashSet<Character>();
        
        for(int i = 0; i < s.length(); i++) {
            if(hs.contains(s.charAt(i))) {
                hs.remove(s.charAt(i));
            } else {
                hs.add(s.charAt(i));
            }
        }
        
		return (s.length() - Math.max(0, hs.size() - 1));
    }
}