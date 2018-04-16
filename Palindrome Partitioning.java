/*
Description
Given a string s, partition s such that every substring of the partition is a palindrome.
Return all possible palindrome partitioning of s.

Example
Given s = "aab", return:
[
  ["aa","b"],
  ["a","a","b"]
]

Tags 
Backtracking Bloomberg Depth First Search
 */

/**
 * Approach 1: DFS (Backtracking)
 * 排列组合题 => Recursive Search DFS
 * 在遍历 str 的时候，考虑从每个 index 到 str 结尾，能有多少种 palindrome?
 * 如果 Index 到达了 字符串的末尾位置+1 说明全部都为回文串，则把该答案加到解集中。
 * for loop :
 *  如果所选不是palindrome， 那就 continue.
 *  若所选的确是palindrome,　加到path里面，DFS去下个level，等遍历到了结尾，这就产生了一种分割成palindrome的串。
 *  每次DFS结尾，要把这一层加的所选 palindrome 删掉，Backtracking。
 */
public class Solution {
    /*
     * @param s: A string
     * @return: A list of lists of string
     */
    public List<List<String>> partition(String s) {
        if (s == null || s.length() == 0) {
            return new LinkedList<>();
        }

        List<List<String>> rst = new LinkedList<>();
        helper(s, 0, new LinkedList<>(), rst);
        return rst;
    }

    private void helper(String s, int index, List<String> list, List<List<String>> rst) {
        // 如果当前位置到达字符串长度时（说明字符串末尾也已经遍历过了，即整个字符串均成立）
        // 将 list 加入到 result 中
        if (index == s.length()) {
            rst.add(new LinkedList<>(list));
        }

        for (int i = index; i < s.length(); i++) {
            // substring 函数为 前必后开，所以结束位置是 i+1.
            String subString = s.substring(index, i + 1);
            if (!isPalindrome(subString)) {
                continue;
            }
            list.add(subString);
            // 递归遍历下一个位置开始的分割情况
            helper(s, i + 1, list, rst);
            // Backtracking
            list.remove(list.size() - 1);
        }
    }

    private boolean isPalindrome(String str) {
        for (int start = 0, end = str.length() - 1; start < end; start++, end--) {
            if (str.charAt(start) != str.charAt(end)) {
                return false;
            }
        }
        return true;
    }
}

/**
 * Approach 2: Backtracking + DP
 * 在 Approach 1 中，对于某一个字串是否为回文串的判断，我们每次都要花费 O(n) 的时间复杂度。
 * 因此我们可以通过 时间换空间 的方法，实现计算出 s 中各个字串是否为回文串并保存下来。
 * 这样当我们需要判断的时候，直接查表即可，时间复杂度为 O(1).
 *
 * 那么如何计算出各个字串是否为回文串呢？如果采用暴力枚举的方法，时间复杂度为 O(n^3).
 * 这显然有点高了（虽然对比于 DFS 是挺好的，但是我们还是希望能够有更快的解法）
 * 我们发现这是一个无后效性的问题，而 s[i...j] 的状态依赖于 s[i+1...j-1]
 * （即 s[i...j] 为 s[i+1...j-1] 往外扩一步的状态，当 s[i] == s[j] 我们就能够往外扩）
 * 根据以上信息我们就可以通过 DP 来解决这个问题了。
 * 初始状态为 单个字符，故其本身就能构成回文，不存在状态依赖；
 * 而 只有两个字符时，只需要比较这两个字符是否相等就可以，也不存在状态依赖；(对应矩阵的对角线）
 * 因此我们将他们初始化为 Base Case,然后进行递推即可。(对应矩阵对角线的上一条线)
 * 注：对于 Base Case 这里采用了分析的方法，实际上我们也可以不这么做，直接通过 递推方程
 * 去找我们需要那些状态即可。这里我们初始化好对角线后，发现递推信息不足，因此我们需要再初始化一条线即可。
 * 
 * 之后的操作与 Approach 1 完全相同
 */
public class Solution {
    /*
     * @param s: A string
     * @return: A list of lists of string
     */
    public List<List<String>> partition(String s) {
        if (s == null || s.length() == 0) {
            return new LinkedList<>();
        }

        List<List<String>> rst = new LinkedList<>();
        boolean[][] isPalindrome = new boolean[s.length()][s.length()];
        getIsPalindromeTable(s, isPalindrome);
        helper(s, 0, isPalindrome, new LinkedList<>(), rst);
        return rst;
    }

    private void helper(String s, int index, boolean[][] isPalindrome, List<String> list, List<List<String>> rst) {
        // 如果当前位置到达字符串长度时（说明字符串末尾也已经遍历过了，即整个字符串均成立）
        // 将 list 加入到 result 中
        if (index == s.length()) {
            rst.add(new LinkedList<>(list));
        }

        for (int i = index; i < s.length(); i++) {
            if (!isPalindrome[index][i]) {
                continue;
            }
            list.add(s.substring(index, i + 1));
            // 递归遍历下一个位置开始的分割情况
            helper(s, i + 1, isPalindrome, list, rst);
            // Backtracking
            list.remove(list.size() - 1);
        }
    }

    private void getIsPalindromeTable(String s, boolean[][] isPalindrome) {
        int len = s.length();
        // Initialize (初始化对角线上的值)
        for (int i = 0; i < len; i++) {
            isPalindrome[i][i] = true;
        }
        // 初始化对角线上面一条对角线的值 (因为其也不依赖其他位置的值，可以直接算出）
        for (int i = 0; i < len - 1; i++) {
            isPalindrome[i][i + 1] = (s.charAt(i) == s.charAt(i + 1));
        }

        // Function (根据初始化好的 base case,推算出各个位置的值)
        // 从下到上，一条一条对角线地递推
        for (int i = len - 3; i >= 0; i--) {
            for (int j = i + 2; j < len; j++) {
                isPalindrome[i][j] = isPalindrome[i + 1][j - 1] && s.charAt(i) == s.charAt(j);
            }
        }
    }
}