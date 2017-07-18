排列组合题 => Recursive Search DFS 
在遍历str的时候，考虑从每个curr spot 到 str 结尾，能有多少种palindorme? 
如果startIndex 到达了字符串的末尾位置说明全部都为回文串，则把该答案加到解集中。
for loop :
	如果所选不是palindrome， 那就 continue.
	若所选的确是palindrome,　加到path里面，DFS去下个level，等遍历到了结尾，这就产生了一种分割成palindrome的串。
	每次DFS结尾，要把这一层加的所选palindrome删掉，backtracking。

/*
Given a string s, partition s such that every substring of the partition is a palindrome.
Return all possible palindrome partitioning of s.
Example
given s = "aab",
Return
  [
    ["aa","b"],
    ["a","a","b"]
  ]
Tags Expand 
Backtracking
*/


// version 1: shorter but slower
public class Solution {
    /**
     * @param s: A string
     * @return: A list of lists of string
     */
    public List<List<String>> partition(String s) {
        // write your code here
        List<List<String>> result = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return result;
        }
        
        helper(s, new ArrayList<String>(), 0, result);
        return result;
    }
    
    private void helper(String s, List<String> partition,
                        int startIndex, List<List<String>> result) {
        //	全部遍历后将partition加入到result中
        if (startIndex == s.length()) {
            result.add(new ArrayList<String>(partition));
            return;
        }               
        
        for (int i = startIndex; i < s.length(); i++) {
        		//	注意这里的substring是从startIndex 到 i + 1. (不是i)
            String subString = s.substring(startIndex, i + 1);	
            if (!isPalindrome(subString)) {
                continue;
            }
            
            partition.add(subString);
            //	递归这里传入的是i + 1,表示startIndex从下一个位置开始
            helper(s, partition, i + 1, result);
            partition.remove(partition.size() - 1);
        }
    }
    
    private boolean isPalindrome(String s) {
        int start = 0;
        int end = s.length() - 1;
        
        for (; start < end; start++, end--) {
            if (s.charAt(start) != s.charAt(end)) {
                return false;
            }
        }
        return true;
    }
}

// version 2: longer but faster
public class Solution {
    List<List<String>> results;
    boolean[][] isPalindrome;
    
    /**
     * @param s: A string
     * @return: A list of lists of string
     */
    public List<List<String>> partition(String s) {
        results = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return results;
        }
        
        getIsPalindrome(s);
        
        helper(s, 0, new ArrayList<Integer>());
        
        return results;
    }
    
    private void getIsPalindrome(String s) {
        int n = s.length();
        isPalindrome = new boolean[n][n];
        
        for (int i = 0; i < n; i++) {
            isPalindrome[i][i] = true;
        }
        for (int i = 0; i < n - 1; i++) {
            isPalindrome[i][i + 1] = (s.charAt(i) == s.charAt(i + 1));
        }
        
        for (int i = n - 3; i >= 0; i--) {
            for (int j = i + 2; j < n; j++) {
                isPalindrome[i][j] = isPalindrome[i + 1][j - 1] && s.charAt(i) == s.charAt(j);
            }
        }
    }
    
    private void helper(String s,
                        int startIndex,
                        List<Integer> partition) {
        if (startIndex == s.length()) {
            addResult(s, partition);
            return;
        }
        
        for (int i = startIndex; i < s.length(); i++) {
            if (!isPalindrome[startIndex][i]) {
                continue;
            }
            partition.add(i);
            helper(s, i + 1, partition);
            partition.remove(partition.size() - 1);
        }
    }
    
    private void addResult(String s, List<Integer> partition) {
        List<String> result = new ArrayList<>();
        int startIndex = 0;
        for (int i = 0; i < partition.size(); i++) {
            result.add(s.substring(startIndex, partition.get(i) + 1));
            startIndex = partition.get(i) + 1;
        }
        results.add(result);
    }
}