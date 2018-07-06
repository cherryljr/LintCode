/*
Description
Given a target word targets and a collection of n words words,
can you select some words from words and select one letter from each word to compose the target word target ?

Only lowercase letters a-z will be included in the string
target is no longer than 20, each word in words is no more than 20, and words is no more than 20.

Example
Given target="ally"，words=["buy","discard","lip","yep"]，return false
Explanation：
buy can match y, discard can match a, lip can match l, yep cannot correspond to any one letter,
so there is still one l in target that cannot be matched.

Given target="ray"，words=["buy","discard","lip","rep"]，return true
Explanation：
buy can match y, discard can match a, rep can match r.
 */

/**
 * Approach: Backtracking
 * 看到这个规模的数据，第一反应就是使用 DFS 递归枚举所有的方案即可。
 * 做法和 Permutation 有点类似，我们可以从 target 的第一个字母开始匹配。
 * 如果 words 中的当前单词还未被使用，并且 words[i] 中包含当前需要匹配的字符，
 * 则可以使用该单词中的当前字符，并将其标记为已使用状态。
 * 如果这条路走得通（即子过程返回 true）则说明可以拼凑出来，否则进行 Backtracking.
 *
 * 注意：
 *  这里的做法是类似与 Permutation 而不是 SubSet.
 *  这是因为下一个可能选取的单词不一定就在当前的单词之后，也可能在这之前。
 *  target的组成可以是无序的。只要是没被使用过的单词即可。
 *  因此我们每次都需要从头开始找一边。
 *
 * 时间复杂度：O(n!) 但是因为进行了剪枝优化，所以实际的时间复杂度远没有这么高
 */
public class Solution {
    /**
     * @param target: the target string
     * @param words: words array
     * @return: whether the target can be matched or not
     */
    public boolean matchFunction(String target, String[] words) {
        return dfs(target, words, new boolean[words.length], 0);
    }

    /**
     * @param target 需要拼凑出来的字符串
     * @param words 所有的单词数组
     * @param visited 标记被使用过的单词
     * @param count 当前需要组合出来的是 target, count 位置上的数
     * @return 能否成功拼凑出target
     */
    private boolean dfs(String target, String[] words, boolean[] visited, int count) {
        if (count == target.length()) {
            return true;
        }

        for (int i = 0; i < words.length; i++) {
            if (!visited[i] && words[i].indexOf(target.charAt(count)) != -1) {
            	// 取当前单词中的相应字符来拼凑 target[count] 位置的字符
                visited[i] = true;
                // 如果当前方案能行得通，则返回 true
                if (dfs(target, words, visited, count + 1)) {
                    return true;
                }
                // Backtracking
                visited[i] = false;
            }
        }
        return false;
    }
}