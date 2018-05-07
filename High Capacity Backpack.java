/*
Description
Given a backpack capacity s, giving n items, the value of the i-th item is vi,
the volume of the i-th item is ci, ask how much worth of the item the backpack can hold,
and output this maximum value. (Each item can only be used once)

Notice
1 <= s, vi, ci <= 10^13
1 <= n <= 31

Example
Given s = 10, v = [1,2,3], c = [3,5,7], return 4.
Explanation:
Put the 0th item and the 2nd item in the backpack.

Given s = 10, v = [1,5,3], c = [4,5,7], return 6.
Explanation:
Put the 0th item and the 1st item in the backpack.

Tags
Binary Search
 */

/**
 * Approach: Binary Enumeration + Binary Search (折半枚举 + 二分查找)
 * 这个问题初看上去感觉就是一个普通的 01背包问题，所以直接使用 DP 做了...然后就爆内存了...
 * 观察 case 情况，发现 重量 和 价值 的数值特别大，但是个数 n 反而挺小的。
 * 因此如果我们采用 DP 的解法，在申请 DP数组 空间的时候，堆内存就会直接爆掉。
 * 并且时间复杂度为：O(n*s) (n为物品个数，s为背包容量)，很明显时间上也是过不去的。
 *
 * 那么应该如何解决呢？观察题目要求，1 <= n <= 31，即 n 的值还是相当小的。
 * 因此可以以这点作为突破口，采用 枚举 的方法选出所有的方案。
 * 但是 2^n 的复杂度还是太高了，因此我们可以采用 折半枚举 的方法。
 * 即将原来的元素分成一半，然后分开枚举，这样时间复杂度就被缩减为：2*2^15，这样就能够符合要求了。
 *
 * 具体做法如下所示：
 * 首先，分出一半元素 half.然后对其进行枚举所有方案（这里采用了二进制的方法进行优化）
 * 每个数只有 取 和 不取 两种可能性，因此我们可以根据一个数二进制当前位上的数是否为 1 来决定是否要取该元素。
 * 然后我们同样在 后半部分 进行枚举所有方案，然后根据当前方案的 w2
 * 在前半部分中寻找 w1 < W - w2 时， 使得 preValue 最大的选取方法就好了。
 * 为了从枚举得到的 前半部分(w1, v1) 方案集合中高效寻找 max{v1 | w1 < W - w2}的方法，。
 * 我们需要在枚举出所有方案之后，对前半部分按照 wight 进行从小到大排序，当 wight 相等时，按照 value 从大到小进行排序。
 * 排序之后，我们便可以去除掉无用的值，即 pair[i].weight < pair[j].weight && pair[i].value > pair[j].value
 * 那么很明显 pair[j] 是不被需要的。
 * 此后剩余所有的元素满足 w1[i] < w1[j] && v2[i] < v2[j]。 于是我们接下来只需要寻找 w1[i] < W - w2 的最大的 i 就可以了。
 * 而这可以通过 二分搜索（上界） 完成。
 * 假如剩余元素的个数为 M 的话， 一次搜索所需要的时间为O（logM）。
 * 由于 M <= 2^(n/2)， 所以这个算法的总的复杂度是 O（n * 2^(n /2)）。
 *
 * 总结：
 * 在看到一道背包问题时，应该用 搜索 还是 动态规划 呢？
 * 首先，可以从数据范围中得到命题人意图的线索。
 * 如果一个背包问题可以用DP解，V一定不能很大，否则O(VN)的算法无法承受，
 * 而一般的搜索解法都是仅与N有关，与V无关的。
 * 所以，V很大时（例如上百万），命题人的意图就应该是考察搜索。
 * 另一方面，N较大时（例如上百），命题人的意图就很有可能是考察动态规划了。
 * （实际上本题所采用的 折半枚举 的方法，最多承受住 N=42 的规模）
 */
public class Solution {
    /**
     * @param s: The capacity of backpack
     * @param v: The value of goods
     * @param c: The capacity of goods
     * @return: The answer
     */
    public long getMaxValue(int s, int[] v, int[] c) {
        int n = v.length;
        int half = n >> 1;

        // 枚举 前半部分 的所有方案，然后存在 pairs[] 中
        Pair[] pairs = new Pair[1 << half];
        for (int i = 0; i < (1 << half); i++) {
            // 注意 weigh, value 在这种大数据相加场合中，务必开 long 来处理
            long weight = 0, value = 0;
            for (int j = 0; j < half; j++) {
                if ((i >>> j & 1) == 1) {
                    weight += c[j];
                    value += v[j];
                }
            }
            pairs[i] = new Pair(weight, value);
        }
        // 对 pairs[] 按照 weight 进行排序
        Arrays.sort(pairs);
        // 利用以及排序好的信息，去除无用的方案
        int index = 0;
        for (int i = 1; i < pairs.length; i++) {
            if (pairs[index].value < pairs[i].value) {
                pairs[++index] = pairs[i];
            }
        }

        // 枚举后半部分的所有方案
        long max = 0;
        for (int i = 0; i < 1 << (n - half); i++) {
            long weight = 0, value = 0;
            for (int j = 0; j < n - half; j++) {
                if ((i >>> j & 1) == 1) {
                    weight += c[half + j];
                    value += v[half + j];
                }
            }
            if (weight <= s) {
                // 在前半部分 pairs[] 中二分查找符合条件的最大 value
                int pos = upperBound(pairs, s - weight);
                long preValue = pos == -1 ? 0 : pairs[pos].value;
                max = Math.max(max, value + preValue);
            }
        }
        return max;
    }

    private int upperBound(Pair[] pairs, long target) {
        int left = -1, right = pairs.length - 1;
        while (left < right) {
            int mid = left + ((right - left + 1) >> 1);
            if (target >= pairs[mid].weight) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return right;
    }

    class Pair implements Comparable<Pair> {
        long weight, value;

        public Pair(long weight, long value) {
            this.weight = weight;
            this.value = value;
        }

        @Override
        public int compareTo(Pair other) {
            return this.weight - other.weight == 0 ?
                    (int)(other.value - this.value) : (int)(this.weight - other.weight);
        }
    }
}