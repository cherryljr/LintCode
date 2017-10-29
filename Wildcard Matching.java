Approach 1: DP
Time Complexity:  O(n^2)
Space Complexity: O(n^2)

Approach 2: Two Pointers Traverse
The basic idea is to have one pointer for the string and one pointer for the pattern. 
The complexity of the algorithm is O(p*s), where p and s are the lengths of the pattern and input strings. 

/*
Description
Implement wildcard pattern matching with support for '?' and '*'.
	'?' Matches any single character.
	'*' Matches any sequence of characters (including the empty sequence).
The matching should cover the entire input string (not partial).

Example
	isMatch("aa","a") → false
	isMatch("aa","aa") → true
	isMatch("aaa","aa") → false
	isMatch("aa", "*") → true
	isMatch("aa", "a*") → true
	isMatch("ab", "?*") → true
	isMatch("aab", "c*a*b") → false
Tags 
	Dynamic Programming String Backtracking Greedy Google
*/

// Solutoin 1: DP
public class Solution {
    public boolean isMatch(String str, String pattern) {
        boolean[][] match = new boolean[str.length() + 1][pattern.length() + 1];
        match[str.length()][pattern.length()] = true;
        
        // Initialize, deal with the remaining characters in pattern
        for (int i = pattern.length() - 1; i >= 0; i--) {
            if (pattern.charAt(i) !='*') {
                break;
            }
            else {
                match[str.length()][i] = true;
            }
        }
        
        // Function 
        for (int i = str.length() - 1; i >= 0; i--) {
            for (int j = pattern.length() - 1; j >= 0; j--) {
                if (str.charAt(i) == pattern.charAt(j) || pattern.charAt(j) == '?') {
                    match[i][j] = match[i + 1][j + 1];
                }
                else if (pattern.charAt(j) == '*') {
                    match[i][j] = match[i + 1][j] || match[i][j + 1];
                }
                else {
                    match[i][j] = false;
                } 
            }
        }
        
        // Answer
        return match[0][0];
    }
}

// Solution 2: Two Pointers
We need the traverse the String and compare the sth character in String with the pth character in pattern.
There are only four situation here:
    1. str.charAt(s) == pattern.charAt(p)
    In this situation, we need move pNode and sNode to the next position and continue the process.
    2. str.charAt(s) != pattern.charAt(p) && pattern.charAt(p) == '?'. 
    Cuz '?' Matches any single character, the situation is the same as situation 1. sNode++ and pNode++ then continue the process.
    3. str.charAt(s) != pattern.charAt(p) && pattern.charAt(p) == '*'. 
    Cuz '*' Matches any sequence of characters.
    Here, we use starIndex to store the current position of pNode (the position of character '*')
    and use match to store the current position of sNode (the starting position of String to match '*'). 
    Then we only need to move the pNode to the next position (cuz '*' can alsoo matches the empty sequence, sNode needn't to move).
    4. str.charAt(s) != pattern.charAt(p) && starIndex != -1. 
    It means that we have encountered a '*' in pattern and we stored the position of it.
    If the character in String has matched the '*' already, the match will move to the next position (match++), then sNode = match.
    This is very important, you can think about this case: String - "abcdabcd", Pattern - "a*bcd" then you will understand it. 
    Set pNode as the next position of '*' (pNode = starIndex + 1).
if the situation isn't in above, we will return false.
After traverse the String, we also need to check for remaining characters in pattern.
If the remaining character is '*', then p++.
At the end, we only need to check whether the pNode has traverse to the end or not. 

// Code Beolow
public class Solution {
    /*
     * @param s: A string 
     * @param p: A string includes "?" and "*"
     * @return: is Match?
     */
    public boolean isMatch(String str, String pattern) {
		int s = 0, p = 0, match = 0, starIndex = -1;            
		while (s < str.length()) {
			// advancing both pointers
			if (p < pattern.length() && (pattern.charAt(p) == '?' 
			|| str.charAt(s) == pattern.charAt(p))) {
				s++;
				p++;
			}
			// * found, only advancing pattern pointer
			else if (p < pattern.length() && pattern.charAt(p) == '*') {
				starIndex = p;
				match = s;
				p++;
			}
		   // we have store a * position in pattern string, advancing string pointer
			else if (starIndex != -1) {
				p = starIndex + 1;
				match++;
				s = match;
			}
		   // current pattern pointer is not star, last patter pointer was not *
		   // characters do not match
			else {
			    return false;
			}
		}

		// check for remaining characters in pattern
		while (p < pattern.length() && pattern.charAt(p) == '*') {
			p++;
		}

		return p == pattern.length();
    }
}