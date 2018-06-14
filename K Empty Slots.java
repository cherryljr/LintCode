/*
Description
There is a garden with N slots. In each slot, there is a flower. The N flowers will bloom one by one in N days.
In each day, there will be exactly one flower blooming and it will be in the status of blooming since then.

Given an array flowers consists of number from 1 to N.
Each number in the array represents the place where the flower will open in that day.

For example, flowers[i] = x means that the unique flower that blooms at day i will be at position x,
where i and x will be in the range from 1 to N.
Also given an integer k, you need to output in which day there exists two flowers in the status of blooming,
and also the number of flowers between them is k and these flowers are not blooming.
If there isn't such day, output -1.

The given array will be in the range [1, 20000].

Example
Given flowers = [1,3,2], k = 1, return 2.
Explanation:
In the second day, the first and the third flower have become blooming.

Given flowers = [1,2,3], k = 1, return -1.
 */

/**
 * Approach 1: Brute Force (Traverse Array)
 * 看到数据规模为 20000,因此推测出该题应该是使用算法时间复杂度在 O(nlogn) 或者以下的算法。
 * 但是其实这种规模，数据集不太变态的话 O(n^2) 也是能过的。
 * 本题暴力解法十分清晰明了，直接对于每个位置，进行左右距离为 k 的扫描即可。
 * 如果找到符合的情况，即可返回。
 *
 * 时间复杂度为：O(nk)
 *
 * 注：相比于之后要介绍的两种解法，这个解法看上去是复杂度最高，最无脑的了。
 * 但是运行情况却是最好的...这是因为当 k 的值很大时，我们需要进行 O(k) 的扫描次数其实非常少。
 * 而当 k 值较小时，其时间复杂度将趋近于 O(n)
 * 以上仅是基于运行结果的推测，并未进行确切的数学证明。
 */
public class Solution {
    /**
     * @param flowers: the place where the flower will open in that day
     * @param k:  an integer
     * @return: in which day meet the requirements
     */
    public int kEmptySlots(int[] flowers, int k) {
        int n = flowers.length;
        if (flowers.length == 0 || k >= n - 1) {
            return -1;
        }

        // k++ 是因为这里 k 代表的是间隔距离，而我们需要check对应位置时，需要多走一步的距离。
        //  left = pos - k - 1; right = pos + k + 1  => left = pos - (k+1); right = pos + (k+1)
        // (因此这里只是为了省点代码和计算量...希望不会造成理解上的不便)
        k++;
        boolean[] isBloomed = new boolean[n + 1];
        for (int i = 0; i < n; i++) {
            if (isValid(isBloomed, flowers[i], n, k)) {
                return i + 1;
            }
        }
        return -1;
    }

    private boolean isValid(boolean[] isBloomed, int pos, int n, int k) {
        isBloomed[pos] = true;
        // 检查左边部分
        if (pos - k > 0 && isBloomed[pos - k]) {
            boolean isValid = true;
            for (int i = 1; i < k; i++) {
                if (isBloomed[pos - i]) {
                    isValid = false;
                    break;
                }
            }
            if (isValid) {
                return true;
            }
        }
        // 检查右边部分
        if (pos + k <= n && isBloomed[pos + k]) {
            boolean isValid = true;
            for (int i = 1; i < k; i++) {
                if (isBloomed[pos + i]) {
                    isValid = false;
                    break;
                }
            }
            if (isValid) {
                return true;
            }
        }
        return false;
    }
}

/**
 * Approach 2: Bucket
 * 将其按照 k 的大小分成 Math.ceil(n / k) 个部分，然后进行分析即可
 * 时间复杂度：O(n)
 * 
 * 类似的问题：
 * Maximum Gap:
 *  https://github.com/cherryljr/LeetCode/blob/master/Maximum%20Gap.java
 * 视频讲解:
 *  https://www.youtube.com/watch?v=K8Nk0AiIX4s
 */
public class Solution {
    /**
     * @param flowers: the place where the flower will open in that day
     * @param k:  an integer
     * @return: in which day meet the requirements
     */
    public int kEmptySlots(int[] flowers, int k) {
        int n = flowers.length;
        if (n == 0 || k >= n - 1) {
            return -1;
        }

        k++;
        int size = (n + k - 1) / k;      // The same as (int)Math.ceil(n / k)
        int[] minBucket = new int[size];
        int[] maxBucket = new int[size];
        Arrays.fill(minBucket, Integer.MAX_VALUE);
        Arrays.fill(maxBucket, Integer.MIN_VALUE);

        for (int i = 0; i < n; i++) {
            // 检查左边部分
            int pos = (flowers[i] - 1) / k;
            if (flowers[i] < minBucket[pos]) {
                minBucket[pos] = flowers[i];
                if (pos > 0 && maxBucket[pos - 1] + k == minBucket[pos]) {
                    return i + 1;
                }
            }

            // 检查右边部分
            if (flowers[i] > maxBucket[pos]) {
                maxBucket[pos] = flowers[i];
                if (pos < size - 1 && minBucket[pos + 1] - k == maxBucket[pos]) {
                    return i + 1;
                }
            }
        }

        return -1;
    }
}

/**
 * Approach 3: BST
 * 在 Approach 1 中，我们需要对左右距离为 k 的范围进行一次查找，时间复杂度为 O(k)
 * 因此我们想可以使用 BST 来对这个查找过程进行一个优化，从而达到 O(logn) 的时间复杂度。
 * 因为 BST 的每次插入和查询时间复杂度都为 O(logn)，因此总体时间复杂度为 O(nlogn)
 *
 * Java自带的 BST 就是 TreeSet 了（这里存储的元素值不会重复）。
 * 因此我们可以利用 TreeSet 来帮助我们实现这个做法。
 *
 * 但是实际上这个方法效果是最差的...原因上面已经经过分析了 Approach 1 是最好的，
 * 而该方法又比不过 Approach 2,因此只能是最差的了...
 */
public class Solution {
    /**
     * @param flowers: the place where the flower will open in that day
     * @param k:  an integer
     * @return: in which day meet the requirements
     */
    public int kEmptySlots(int[] flowers, int k) {
        int n = flowers.length;
        if (n == 0 || k >= n - 1) {
            return -1;
        }

        k++;    
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < flowers.length; i++) {
            if (i > 0) {
                // 检查左边部分
                Integer left = set.floor(flowers[i]);
                if (left != null && flowers[i] - left == k) {
                    return i + 1;
                }
                // 检查右边部分
                Integer right = set.ceiling(flowers[i]);
                if (right != null && right - flowers[i] == k) {
                    return i + 1;
                }
            }
            set.add(flowers[i]);
        }

        return -1;
    }
}