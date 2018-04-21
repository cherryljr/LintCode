/*
Description
Given a string str, find the longest substring with no fewer than k repetitions and return the length.
The substring can have overlapping parts, but cannot completely overlap.

Notice
1 <= str.length <= 1000
1 < k < str.length
We guarantee that the problem will certainly can be solved

Example
Given str = "aaa", k = 2, return 2.
Explanation:
The longest subsequence with no fewer than k repetitions is "aa", and the length is 2.

Given str = "aabcbcbcbc", k = 2, return 6.
Explanation:
Subsequences repeat no fewer than twice are "a", "bc", "bcbc" and "bcbcbc",
and the longest is "bcbcbc", and the length is 6.

Tags
Netflix
 */

/**
 * 前言：对于我而言，这是一道非常好的题目...虽然它在Contest上给我贡献了两个Bug...
 * 
 * Approach 1: HashMap (Memory Limit Exceeded)
 * 就最直观暴力的方法就是：枚举所有可能的子串，并记录相应的出现次数。
 * 然后选出出现次数 不小于k次 的子串，在里面选择长度最长的子串即可。
 * 时间复杂度：O(n^2)
 *
 * 就时间复杂度而言，这道题目还是能过的，但是这道题目我们还需要考虑一下 空间复杂度。
 * 如果我们直接将各个 subString 存到了哈希表中，对于大数据将会直接导致内存爆炸。
 * 但是，我们并不需要获得具体最长子串，而是只需要其长度即可。
 * 因此我们可以只存储子串的 哈希值，这样就可以大幅减少存储空间了。
 * 然而提交之后，我们发现内存竟然爆了...
 * 这是什么原因呢？大家可以思考一下，然后在 Approach 2 中看看是怎么解决的。
 */
public class Solution {
    /**
     * @param str: The input string
     * @param k: The repeated times
     * @return: The answer
     */
    public int longestRepeatingSubsequenceII(String str, int k) {
        if (str == null || str.length() <= 1) {
            return 0;
        }

        Map<Integer, Integer> map = new HashMap<>();
        // 遍历并记录所有的子串，并记录对应的出现次数
        for (int i = 0; i < str.length(); i++) {
            for (int j = i; j < str.length(); j++) {
                int hashValue = str.substring(i, j + 1).hashCode();
                map.put(hashValue, map.getOrDefault(hashValue, 0) + 1);
            }
        }

        int maxLen = 0;
        for (int i = 0; i < str.length(); i++) {
            for (int j = i; j < str.length(); j++) {
                if (map.get(str.substring(i, j + 1).hashCode()) >= k) {
                    maxLen = Math.max(maxLen, j - i + 1);
                }
            }
        }
        return maxLen;
    }
}

/**
 * Approach 2: HashMap (Using StringBuilder) --> Wrong Answer o(╥﹏╥)o
 * 在 Approach 1 中，我们知道为了节省额外空间，应该存储各个子串的 hashValue 而非 其子串本身。
 * 但是仍然出现了 MLE 的情况，这是为什么呢？
 * 原因在于我们使用了 subString() 这个方法。
 * 介于 String 在 Java 中的不变性，该方法每调用一次都会创建出一个 String 对象出来。
 * 因此这对于我们的空间也是一个巨大的消耗（倒不如说这才是本题最吃空间的地方）
 * 本题中我们是采用暴力遍历的方法获得所有可能的子串，所以当前子串基本都是在前一个子串的基础上拼接上一个字符得到。
 * 那么对于拼接字符串，我们肯定要使用 StringBuilder 啊。
 * 因此该解法中采用了 StringBuilder 一次拼接一个字符的方式来替代原本所使用的 subString() 方法。
 *
 * 再次提交，果然不会爆内存了...但是！答案错误了...
 * 这个 Case 是一个非常长的字符串...我们的答案比 Expected Answer 大了点。
 * 针对这个现象，我们很容易就能推测出原因：hashValue 发生了重复，即原本两个不相同的字符串却得到了相同的 hashValue.
 * 对此我们可以看一看 String 系统自带的的 hashCode() 是怎么实现的。（源码为了方便起见已经贴在下面了）
 * 我们发现，根据其计算方式，遇到长字符串时计算出来的 哈希值 很可能超出 int 的范围，从而导致重复。
 * 因此为了解决该问题，我们需要自己实现一个返回值为 long 的 hashCode() 来针对 长字符串hashValue 的计算。
 * 具体实现在源码的基础上进行稍微改动即可。(Approach 3)
 * 因此：系统自带的 hashCode() 使用需谨慎！在阅读了其源码实现之后，相信大家对此有了更加深刻的认识。
 */
public class Solution {
    /**
     * @param str: The input string
     * @param k: The repeated times
     * @return: The answer
     */
    public int longestRepeatingSubsequenceII(String str, int k) {
        if (str == null || str.length() <= 1) {
            return 0;
        }

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = i; j < str.length(); j++) {
                int hashValue = sb.append(str.charAt(j)).toString().hashCode();
                map.put(hashValue, map.getOrDefault(hashValue, 0) + 1);
            }
        }

        int maxLen = 0;
        for (int i = 0; i < str.length(); i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = i; j < str.length(); j++) {
                int hashValue = sb.append(str.charAt(j)).toString().hashCode();
                if (map.get(hashValue) >= k) {
                    maxLen = Math.max(maxLen, j - i + 1);
                }
            }
        }
        return maxLen;
    }

    /**
     * Java源码
     * Returns a hash code for this string. The hash code for a
     * {@code String} object is computed as
     * <blockquote><pre>
     * s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
     * </pre></blockquote>
     * using {@code int} arithmetic, where {@code s[i]} is the
     * <i>i</i>th character of the string, {@code n} is the length of
     * the string, and {@code ^} indicates exponentiation.
     * (The hash value of the empty string is zero.)
     *
     * @return  a hash code value for this object.
     */
    public int hashCode() {
        int h = hash;
        if (h == 0 && value.length > 0) {
            char val[] = value;

            for (int i = 0; i < value.length; i++) {
                h = 31 * h + val[i];
            }
            hash = h;
        }
        return h;
    }
}

/**
 * Approach 3: HashMap (Implement hashCode() Function)
 * 根据 Approach 2 的分析，我们需要自己实现一个 hashCode() 来实现对 长字符串hashValue 的计算。
 */
public class Solution {
    public static final long SEED = 31;
    private static final long MOD = 100000000007L;

    /**
     * @param str: The input string
     * @param k: The repeated times
     * @return: The answer
     */
    public int longestRepeatingSubsequenceII(String str, int k) {
        if (str == null || str.length() <= 1) {
            return 0;
        }

        Map<Long, Integer> map = new HashMap<>();
        for(int i = 0; i < str.length(); i++) {
            long hashValue = 0;
            for(int j = i; j < str.length(); j++) {
                // 对于 hashValue 的计算，因为我们是采用在上一个字符串结尾附加得到当前字符串的
                // 所以 hashValue 可以在上一个基础上直接进行计算（参考源码）
                hashValue = ((SEED * hashValue + str.charAt(j) - 'a' + 1 ) % MOD);
                map.put(hashValue, map.getOrDefault(hashValue, 0) + 1);
            }
        }

        int maxLen = 0;
        for(int i = 0; i < str.length(); i++) {
            long hashValue = 0;
            for(int j = i; j < str.length(); j++) {
                hashValue = ((SEED * hashValue + str.charAt(j) - 'a' + 1 ) % MOD);
                if(map.get(hashValue) >= k) {
                    maxLen = Math.max(maxLen, j - i + 1);
                }
            }
        }
        return maxLen;
    }
}


