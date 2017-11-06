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

// Approach 1: Using trim() and split() method
After trim and split the String s, we will get string array that consist of each indivisual word.
We only need to append them from end to start, we will approach reverse words in the string.
Attention:
    1. the input may be "  " or "   a   b  ", so we should use trim() to remove spaces
    2. there maybe two or more spaces between indivisual words, so we use regular expression to remove them
    3. Instead of using substring, insert the word-characters directly in the StringBuilder. 
       Assuming they're using a linked-list or tree, this could be a whole last faster.
// Code Below
public class Solution {
    public static String reverseWords(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        
        String[] temp = s.trim().split("\\s+");  // Use regular expression to remove spaces
        StringBuilder rst = new StringBuilder();
        
        // for (int i = temp.length - 1; i >= 0; i--) {
        for (int i = temp.length - 1; i > 0; i--) {
            rst.append(temp[i] + " ");   
        }
        
        // remove the last " "
        // return rst.substring(0, rst.length() - 1);
        return rst.append(temp[0]).toString();
    }
}

// Approach 2: Two-pointers (no trim(), no split(), no StringBuilder)
public class Solution { 
    public String reverseWords(String s) {
        if (s == null) {
            return null;
        }

        char[] a = s.toCharArray();
        int n = a.length;

        // step 1. reverse the whole string
        reverse(a, 0, n - 1);
        // step 2. reverse each word
        reverseWords(a, n);
        // step 3. clean up spaces
        return cleanSpaces(a, n);
    }
  
    private void reverseWords(char[] a, int n) {
        int i = 0, j = 0;
        while (i < n) {
            // skip spaces
            while (i < j || i < n && a[i] == ' ') {
                i++; 
            }
            // skip non spaces
            while (j < i || j < n && a[j] != ' ') {
                j++; 
            }
            reverse(a, i, j - 1);   // reverse the word
        }
    }
  
    // trim leading, trailing and multiple spaces
    String cleanSpaces(char[] a, int n) {
        int i = 0, j = 0;
        while (j < n) {
            // skip spaces
            while (j < n && a[j] == ' ') {
                j++;  
            }
            // keep non spaces
            while (j < n && a[j] != ' ') {
                a[i++] = a[j++]; 
            }
            // skip spaces
            while (j < n && a[j] == ' ') {
                j++;         
            }
            // keep only one space
            if (j < n) {
                a[i++] = ' ';    
            }
        }

        return new String(a).substring(0, i);
    }
  
    // reverse a[] from a[i] to a[j]
    private void reverse(char[] a, int i, int j) {
        while (i < j) {
          char t = a[i];
          a[i++] = a[j];
          a[j--] = t;
        }
    }
  
}
