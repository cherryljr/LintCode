/**
 * Manacher 算法解析：
 * 其流程为：
 *  1. 在原字符串中插入 虚拟节点'#' 以便于我们处理 字符串长度为偶数的情况。
 *  （'#'可以为任意字符，因为虚拟节点 只会与 虚拟节点 匹配，这里使用 '#' 是为了方便区分）
 *  2. 维持一个 回文半径数组radiusArr[]; 最右回文边界pR; 以及最右回文边界所对应的 回文中心center
 *  3. 遍历字符数组 charArr，根据 i 与 pR 的位置，我们可以分为以下四个情况：
 *      ① i 在 pR上 或者 pR的右边 (i >= pR)，此时只能继续暴力向 i 的两侧扩，看是否能够组成回文字符串；
 *      时间复杂度：O(n)
 *      ② i 在 pR 的内部，并且 i 关于回文中心center的对称点 i' 的回文半径范围[L, R] 也在 [pL, pR] 的内部
 *      此时 charArr[i] 的回文半径无需计算，值为 radiusArr[i']
 *      时间复杂度：O(1)
 *      ③ i 在 pR 的内部，但是 i 关于回文中心center的对称点 i' 的回文半径范围[L, R] 不在 [pL, pR] 的内部
 *      即整个大的回文串并没有将 i' 的回文串包裹在里面，此时 charArr[i] 的回文半径也无需计算，值为 pR
 *      时间复杂度：O(1)
 *      ④ i 在 pR 的内部，并且 i 关于回文中心center的对称点 i' 的回文半径范围[L, R]的左边界 L 与 pL 重合
 *      此时我们可以省去判断 [i...pR] 部分，但是后面的部分仍然需要暴力扩，
 *      因为 charArr[pR+1] 有可能与 charArr[i - radius[i']] 相等
 *      时间复杂度：O(n)
 *  4. 以上便是 Manacher 算法的核心部分，然后我们只需要在遍历过程中维护一个 最长回文半径max 就能够得到我们的答案了。
 *
 * Manacher算法的考察，通常就是考察其核心部分。考法与 KMP 算法类似，通常需要我们对其核心部分进行稍微的修改
 * 或者添加判断条件等。因此我们需要对其核心部分有着深刻的理解，同样对于 KMP 算法的求解 next[] 部分也是同理。
 *
 * 时间复杂度分析：我们可以发现 最右回文边界pR 只会向右移动，从来不会后退。
 * 在 4种情况中，当 pR 不会发生更新的时候，时间复杂度均为 O(1)，而 O(n) 的操作也将导致 pR 的向右移动。
 * 因此总体时间复杂度为：O(n)
 *
 * 相关练习：
 * 
 *
 * 参考资料:
 * https://segmentfault.com/a/1190000003914228
 * https://articles.leetcode.com/longest-palindromic-substring-part-ii/
 */

public class Manacher {
    public String longestPalindrome(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }

        char[] charArr = getManacherString(str);    // 处理后获得的Manacher字符数组 （添加了'#'）
        int[] radiusArr = new int[charArr.length];  // 回文半径数组
        int center = -1;                            // 最右回文边界 所对应的 回文中心
        int pR = -1;                                // 最右回文边界
        int max = Integer.MIN_VALUE;                // 最长回文子串 的 回文半径
        int c = -1;                                 // 最长回文子串 的 回文中心
        for (int i = 0; i < charArr.length; i++) {
            // 计算当前字符的 回文半径 (4种情况)
            radiusArr[i] = i < pR ? Math.min(radiusArr[2 * center - i], pR - i) : 1;
            // 尝试以charArr[i]为中心向外扩，如果不匹配就停止
            // 该写法较为简洁，不需要写成 4种if-else 的判断情况
            while (i + radiusArr[i] < charArr.length && i - radiusArr[i] > -1) {
                if (charArr[i + radiusArr[i]] == charArr[i - radiusArr[i]])
                    radiusArr[i]++;
                else {
                    break;
                }
            }
            // 当 i+radiusArr[i] 大于 最右回文边界 时，更新 pR，同时还需要更新其所对应的 回文中心.
            // 即 最右回文边界 向右扩，并更新 对称点 位置
            if (i + radiusArr[i] > pR) {
                pR = i + radiusArr[i];
                center = i;
            }
            // 更新 最大回文半径 与对应的 回文中心
            if (radiusArr[i] > max) {
                max = radiusArr[i];
                c = i;
            }
        }

		// 提取最大回文子串
        StringBuilder sb = new StringBuilder();
        for (int i = c - max + 1; i < c + max; i++) {
            if (charArr[i] != '#') {
                sb.append(charArr[i]);
            }
        }
        return sb.toString();
    }

    public char[] getManacherString(String str) {
        char[] charArr = str.toCharArray();
        char[] rst = new char[str.length() * 2 + 1];
        int index = 0;
        for (int i = 0; i < rst.length; i++) {
            rst[i] = (i & 1) == 0 ? '#' : charArr[index++];
        }
        return rst;
    }

    public static void main(String[] args) {
        String str1 = "abc1234321ab";
        Manacher manacher = new Manacher();
        System.out.println(manacher.longestPalindrome(str1));
    }

}
