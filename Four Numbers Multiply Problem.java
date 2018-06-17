/*
Description
Given an array a of length n and a positive integer k, select four numbers from the array
and require the product of four numbers to be less than or equal to k to find the total number of plans.
Multiple numbers with the same value may appear in the array.
Each time four numbers are selected, the same number cannot be selected twice, but two numbers with the same value can be selected at the same time.
1 ≤ n ≤ 10^​3
1 ≤ a[i] ≤ 10^​6 ​​ (1≤i≤n)
1 ≤ k ≤ 10^​6

Example
Given n = 5, a = [1,1,1,2,2], k = 3, return 2.
Explanation:
Assume that the scheme [i,j,p,q] indicates that the selected four numbers are: a[i], a[j], a[p], a[q]. 0<= i,j,p,q < n.
The solutions that meet the conditions are:
[0,1,2,3]
[0,1,2,4]

Given n = 5, a = [2,4,9,4,3], k = 300, return 4 .
Explanation:
Assume that the scheme [i,j,p,q] indicates that the selected four numbers are: a[i], a[j], a[p], a[q]. 0<= i,j,p,q < n.
The solutions that meet the conditions are:
[0,1,3,4]
[0,1,2,4]
[0,2,3,4]
[0,1,2,3]
 */

/**
 * Approach 1: Enumeration
 * 根据题目所给出的数据量，可以推测出算法的时间复杂度应该在 O(n^2)。
 * 因此我们可以采用枚举的方法，枚举所有的数对。
 * 因为总共有 4 个数，全部枚举的话时间复杂度肯定是超过的，
 * 所以我们可以先对其进行排序，然后采用 两两分开枚举 的方法。
 * 即我们枚举 第一个数 和 第二个数 的位置，并由此计算出乘积。
 * 而目标就是在剩下的数字中找到剩下的两个数，使得总共的乘积 <= k.
 * 为了加快对剩下两个元素的查找，我们可以先对数组进行排序，
 * 然后就可以利用 Two Pointers 的做法找出符合要求的区间，然后直接加上区间长度即可
 *
 * 时间复杂度：O(n^3)
 * 虽然时间复杂度为 O(n^3) 但是处理过程中利用了 Two Pointers 跳过了大量的计算。
 * 因此实际花费的时间远远没有这么多。
 */
public class Solution {
    /**
     * @param n: The length of the array
     * @param a: Known array
     * @param k: The product of the selected four numbers cannot be greater than k
     * @return: The number of legal plan
     */
    public long numofplan(int n, int[] a, int k) {
        // 首先进行排序以便于之后的二分查找
        Arrays.sort(a);
        long rst = 0;
        // 枚举第一个元素和第二个元素的位置
        for (int first = 0; first <= n - 4; first++) {
            for (int second = first + 1; second <= n - 3; second++) {
                long product = (long)a[first] * a[second];
                if (product > k) {
                    continue;
                }
                long need = k / product;
                // Two Pointers
                int third = second + 1, fourth = n - 1;
                while (third < fourth) {
                    long temp = a[third] * a[fourth];
                    if (temp <= need) {
                        rst += fourth - third;
                        third++;
                    } else {
                        fourth--;
                    }
                }
            }
        }
        return rst;
    }
}

/**
 * Approach 2: Binary Index Tree
 * 该解法是在赛后想到的，主要是在 Approach 1 的基础上进一步改进。
 * 思想相同，依然是先枚举两个数，将他们进行相乘，然后用 need = k / product.
 * 去枚举剩下的可能数值。因为需要枚举，所以就算只有两个数的话，也需要用掉 O(n^2) 的时间。
 * 因此我们想能否在 O(logn) 的时间内去获得剩下的那两个数值信息。
 * 
 * 因为涉及到了范围查询与值的修改，又想在 O(logn) 时间内办到的话，我们自然能够想到使用 
 * Segment Tree 或者 Binary Index Tree 来实现，线段树代码量较大，因此我们选择了易于实现的 BIT.
 * 
 * 具体做法：
 *  枚举 第三个元素 和 第四个元素 的位置，并计算出乘积，并以此来建立 BIT.
 *  BITree[i] 表示当前乘积小于等于 i 的乘积个数的值(这里的乘积指的是 第三个元素 和 第四个元素 的乘积)
 *  然后枚举 第一个元素 和 第二个元素 的位置，根据需要的 needProduct = k / (a[first] * a[second])
 *  去 BIT 中查询对应的结果，然后加上即可。
 *  该做法需要我们逆着顺序去推，即利用 3rd*4th 去建树，然后去推 1st*2nd.这样思路会更顺一些.
 *  
 * 时间复杂度：O(n^2*logn)
 */
public class Solution {
    /**
     * @param n: The length of the array
     * @param a: Known array
     * @param k: The product of the selected four numbers cannot be greater than k
     * @return: The number of legal plan
     */
    public long numofplan(int n, int[] a, int k) {
        int[] BITree = new int[k + 1];
        long rst = 0L;

        for (int second = n - 3; second >= 1; second--) {
            int third = second + 1;
            // Build the BIT
            for (int fourth = third + 1; fourth < n; fourth++) {
                long product = (long)a[third] * a[fourth];
                if (product <= k) {
                    update(BITree, (int)product);
                }
            }

            for (int first = 0; first < second; first++) {
                rst += getSum(BITree, k / a[first] / a[second]);
            }
        }

        return rst;
    }

    public void update(int[] BITree, int index) {
        for (; index < BITree.length; index += index & -index) {
            BITree[index] += 1;
        }
    }

    public long getSum(int[] BITree, int index) {
        long sum = 0;
        for (; index > 0; index -= index & -index) {
            sum += BITree[index];
        }
        return sum;
    }
}

/**
 * Approach 3: Binary Enumeration + Binary Search
 * 我们可以采用 折半枚举 + 二分查找 的方法来解决问题。
 * 通过这个方法可以进一步把时间复杂度优化到：O(n^2) 级别。
 * 参考：
 *  https://www.jiuzhang.com/solution/four-numbers-multiply-problem/
 */
public class Solution {
    private static final int MAX = 1000001;

    /**
     * @param n: The length of the array
     * @param a: Known array
     * @param k: The product of the selected four numbers cannot be greater than k
     * @return: The number of legal plan
     */
    public long numofplan(int n, int[] a, int k) {
        long[] count = new long[MAX];
        long[] sum = new long[MAX];
        Arrays.sort(a);

        for (int i = 0; i < n; i++) {
            if (a[i] > k) {
                break;
            }
            count[a[i]]++;
        }
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int product = a[i] * a[j];
                if (product > k) {
                    break;
                }
                sum[product]++;
            }
        }
        for (int i = 1; i <= k; i++) {
            count[i] += count[i - 1];
            sum[i] += sum[i - 1];
        }

        long rst = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                int product = a[i] * a[j];
                if (product > k) {
                    break;
                }
                int need = k / product;
                rst += sum[need];
                if (a[i] <= need) {
                    rst -= count[need / a[i]];
                    if (a[i] <= need / a[i]) {
                        rst++;
                    }
                }
                if (a[j] <= need) {
                    rst -= count[need / a[j]];
                    if (a[j] <= need / a[j]) {
                        rst++;
                    }
                }
                if (a[i] * a[j] <= need) {
                    rst++;
                }
            }
        }

        return rst / 6;
    }
}

