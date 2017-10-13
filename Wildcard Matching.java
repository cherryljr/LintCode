Approach 1: DP


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

// Solution 2: Two Pointers
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
		   // pattern pointer was *, advancing string pointer
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