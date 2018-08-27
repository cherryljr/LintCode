/*
Description
Given two strings, find the longest common subsequence (LCS).
Your code should return the length of LCS.

Clarification
What's the definition of Longest Common Subsequence?
https://en.wikipedia.org/wiki/Longest_common_subsequence_problem
http://baike.baidu.com/view/2020307.htm

Example
For "ABCD" and "EDCA", the LCS is "A" (or "D", "C"), return 1.
For "ABCD" and "EACB", the LCS is "AC", return 2.
*/

/**
 * Approach 1: Recursion + Memory Search
 */
public class Solution {
    /**
     * @param A: A string
     * @param B: A string
     * @return: The length of longest common subsequence of A and B
     */
    public int longestCommonSubsequence(String A, String B) {
        int[][] dp = new int[A.length()][B.length()];
        return dfs(A, 0, B, 0, dp);
    }

    private int dfs(String A, int i, String B, int j, int[][] dp) {
        if (i >= A.length() || j >= B.length()) {
            return 0;
        }
        if (dp[i][j] != 0) {
            return dp[i][j];
        }

        if (A.charAt(i) == B.charAt(j)) {
            dp[i][j] += dfs(A, i + 1, B, j + 1, dp) + 1;
        } else {
            dp[i][j] = Math.max(dfs(A, i + 1, B, j, dp), dfs(A, i, B, j + 1, dp));
        }
        return dp[i][j];
    }
}

/**
 * Approach 2: Sequence DP
 * 最大值问题，存在两个序列，并且每个元素顺序不能调换 => Two Sequence DP
 * Note: 字符串 + 前i个字符 => 开辟 n+1 个数组空间
 *
 * State:
 *  f[i][j]表示字符串A的前i个字符配上字符串B的前j个字符的LCS长度
 * Function:
 *  f[i][j] = f[i - 1][j - 1] + 1    //  当 A[i] == B[j] 时
 *  = Math.max(f[i - 1][j], f[i][j - 1])   // 当 A[i] != B[j] 时
 * Initialize:
 *  f[0][i] = 0, f[i][0] = 0
 * Answer:
 *  f[A.length()][B.length()]
 *
 * 时间复杂度：O(M * N)
 * 空间复杂度：O(M * N)
 */
public class Solution {
    /**
     * @param A, B: Two strings.
     * @return: The length of longest common subsequence of A and B.
     */
    public int longestCommonSubsequence(String A, String B) {
        if (A == null || B == null || A.length() == 0 || B.length() == 0) {
            return 0;
        }

        // State
        int[][] dp = new int[A.length()  + 1][B.length() + 1];

        // Initialize 因为int数组初始化均为0，故在构建数组时，Java已经默认帮我们完成了

        // Function
        for (int i = 1; i <= A.length(); i++) {
            for (int j = 1; j <= B.length(); j++) {
                if (A.charAt(i - 1) == B.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
            }
        }

        // Answer
        return dp[A.length()][B.length()];
    }
}

/**
 * Approach 3: Sequence DP (Turn into LIS)
 * 本题可以通过转换成 LIS 问题来进一步降低时间复杂度。
 *
 * 举例说明：
 *  A：abdba
 *  B：dbaaba
 * 具体流程为：
 *  1：先顺序扫描A串，取其在B串的所有位置：
 *  2：a(2,3,5) b(1,4) d(0)。
 *  3：用每个字母的反序列替换，则最终的最长严格递增子序列的长度即为解。
 *  替换结果：532 41 0 41 532
 *  则相应的最长递增子序列长度为 3
 *
 * 简单说明：上面的序列和最长公共子串是等价的。
 * 对于一个满足最长严格递增子序列的序列，该序列必对应一个匹配的子串。
 * 反序是为了在递增子串中，每个字母对应的序列最多只有一个被选出。
 * 反证法可知不存在更大的公共子串，因为如果存在，则求得的最长递增子序列不是最长的，矛盾。
 *
 * 时间复杂度：O(nlogn) 因为最长递增子序列可在 O(nlogn) 的时间内算出。
 *
 * 注意：这个解法并不是绝对优于以上两种解法的，即该算法在最终的时间复杂度上不是严格的 O(nlogn)
 * 因为在特定情况下，该算法会发生退化。
 * 举个退化的例子：
 *  A：aaa
 *  B：aaaa
 *  则序列 321032103210
 * 长度变成了n*m ,最终时间复杂度 O(n*m*(lognm)) > O(n*m)。
 */
public class Solution {
    /**
     * @param A: A string
     * @param B: A string
     * @return: The length of longest common subsequence of A and B
     */
    public int longestCommonSubsequence(String A, String B) {
        if (A == null || B == null || A.length() == 0 || B.length() == 0) {
            return 0;
        }

        // 记录 字符串A 中各个字符的出现位置
        Map<Character, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < A.length(); i++) {
            map.computeIfAbsent(A.charAt(i), x -> new ArrayList<>()).add(i);
        }
        // 进行转换后的序列B
        ArrayList<Integer> BList = new ArrayList<>();
        // 使用 字符串B 中各个字符在 字符串A 中的出现位置来表示 字符串B。
        for (int i = 0; i < B.length(); i++) {
            if (map.containsKey(B.charAt(i))) {
                // 注意出现的位置顺序需要进行逆序
                List<Integer> list = map.get(B.charAt(i));
                for (int j = list.size() - 1; j >= 0; j--) {
                    BList.add(list.get(j));
                }
            }
        }

        // 对 BList 求 LIS 就是最后的结果
        int[] minLast = new int[BList.size()];
        Arrays.fill(minLast, Integer.MAX_VALUE);
        for (int i = 0; i < minLast.length; i++) {
            int index = binarySearch(minLast, BList.get(i));
            minLast[index] = BList.get(i);
        }
        for (int i = minLast.length - 1; i >= 0; i--) {
            if (minLast[i] != Integer.MAX_VALUE) {
                return i + 1;
            }
        }

        return 0;
    }

    private int binarySearch(int[] minLast, int target) {
        int left = 0, right = minLast.length;
        while (left < right) {
            int mid = left + (right - left >> 1);
            if (target <= minLast[mid]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
}