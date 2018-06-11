/*
Description
Given a string s and a dictionary of words dict,
add spaces in s to construct a sentence where each word is a valid dictionary word.

Return all such possible sentences.

Example
Gieve s = lintcode,
dict = ["de", "ding", "co", "code", "lint"].

A solution is ["lint code", "lint co de"].

The description of this problem is better in LeetCode.
LeetCode: https://leetcode.com/problems/word-break-ii/description/
 */

/**
 * Approach: Recursion + Memory Search
 * 这道题目属于 Word Break 的 Fellow Up.
 * 区别主要在于以下两点：
 *  1. 需要具体的分割方案
 *  2. 需要所有的方案，因此找到一个成立的解法后不能直接退出，要遍历所有的方案。
 *
 * 清楚了以上两点之后，在了解 Word Break 的基础上，这道题目基本也就迎刃而解了。
 * 我们依然去枚举所有的分割点 i 的位置。并记录以此产生的字符串 s 的分割结果。
 * 并将该分割点所能产生的结果记录下来。（详见代码注释）
 *
 * 这里偷懒直接贴了我在 LeetCode 上的解答。
 * 另一方面是 LeetCode 上使用 List<String> 作为输入，需要转成 Set<String> 来进行加速。
 * 而 LintCode 却直接使用了 Set.这里提醒一下大家。
 *
 * 关于 wordDict 和 s 二者大小出现严重不平衡的大数据情况时，我们可以参考这里的讨论：
 *  https://leetcode.com/problems/word-break-ii/discuss/44167/My-concise-JAVA-solution-based-on-memorized-DFS
 *
 * Word Break:
 *  https://github.com/cherryljr/LintCode/blob/master/Word%20Break.java
 */
class Solution {
    public List<String> wordBreak(String s, List<String> wordDict) {
        // Deal with the empty String
        if (s == null || s.length() == 0) {
            return new ArrayList<>();
        }

        // Using HashSet for O(1) search
        Set<String> wordSet = new HashSet<>(wordDict);
        // Using mem to record the result
        Map<String, List<String>> mem = new HashMap<>();
        return wordBreak(s, mem, wordSet);
    }

    private List<String> wordBreak(String s, Map<String, List<String>> mem, Set<String> wordSet) {
        // 因为我们需要的所有方案，因此首先我们需要建立一个 rst 来存储所有的结果
        List<String> rst = new LinkedList<>();
        // 如果s本身在 wordSet 中，那么其本身就能作为一种方案
        if (wordSet.contains(s)) {
            // 这里与 Word Break 的区别在于：我们要求的是全部的方案，因此不能直接 return
            rst.add(s);
        }

        for (int i = 1; i < s.length(); i++) {
            String right = s.substring(i);
            // 如果分割点右半部分形成的 word 不在字典里，那么左半部分就没有计算的必要
            if (!wordSet.contains(right)) {
                continue;
            }

            String left = s.substring(0, i);
            // 如果左半部分的结果还未被计算过，那么递归调用子过程计算
            if (!mem.containsKey(left)) {
                mem.put(left, wordBreak(left, mem, wordSet));
            }
            // 在左半部分所有结果的末尾添加上右半部分形成的单词
            // 比如 {"cats and", "cat sand"},都要添加上 "dog"
            List<String> tempRst = append(mem.get(left), right);
            for (String temp : tempRst) {
                rst.add(temp);
            }
        }

        // 最后记录字符串 s 的分割结果，并返回
        mem.put(s, rst);
        return rst;
    }

    private List<String> append(List<String> prefixes, String word) {
        List<String> rst = new LinkedList<>();
        for (String prefix : prefixes) {
            rst.add(prefix + " " + word);
        }
        return rst;
    }
}