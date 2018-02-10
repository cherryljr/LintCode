/*
Given an array of integers, how many three numbers can be found in the array,
so that we can build an triangle whose three edges length is the three numbers
that we find?

Example
Given array S = [3,4,6,7], return 3. They are:
[3,4,6]
[3,6,7]
[4,6,7]
 */

/**
 * Approach: QuickSort and Two Pointers
 * 设三角形的三条边为：A, B, C.则它们需要满足以下关系：
 * 	    A + B > C
 * 	    B + C > A
 * 	    A + C > B
 * 但是如果我们对 A, B, C 进行排序，（假如排序后为：A <= B <= C）
 * 则可以去除以上条件中的三个条件，仅保留：
 *      A + B > C
 * 于是这道题目我们可以转换为：枚举最大的边 C，
 * 然后找使得 A + B > C 成立的对数。即 Two Sum II 问题。
 * https://github.com/cherryljr/LintCode/blob/master/Two%20Sum%20-%20Find%20bigger%20pair(not%20sorted).java
 *
 * Time Complexity: O(n^2)
 * Note: don't forget to sort
 */
public class Solution {
    /**
     * @param S: A list of integers
     * @return: An integer
     */
    public int triangleCount(int S[]) {
        // write your code here
        if (S == null || S.length < 3) {
            return 0;
        }
        int count = 0;
        int n = S.length;

        // 这里可直接调用Arrays.sort(S);直接进行快速排序，
        //	这边仅仅对快排进行重写稍作演示，之后的代码将直接调用Java已经实现的方法
        quicksort(S, 0, S.length-1);
        // 枚举 最大边C 作为 target 用于进行 Two Sum 的计算
        for (int i = 2; i < S.length; i++) {
            int left = 0;
            int right = i -1;
            while (left < right) {
                if (S[left] + S[right] > S[i]) {
                    count += (right - left);
                    //  因为已经按从小到大的顺序排序完，故一旦S[left] + S[right] > S[i]，
                    //	那么说明S中下标介于 left~right 的值与 S[right] 相加均大于S[i](C),故代表可right-left个三角形.
                    //	注意：这里是一定包含 S[right] 这条边的意思。
                    right--;
                }
                else{
                    left++;
                }
            }
        }

        return count;
    }

    void quicksort(int a[], int low, int high)  // 利用递归对分割好的数组进行排序
    {
        int middle;

        if (low >= high) {
            return ;
        }
        middle = split(a, low, high);
        quicksort(a, low, middle - 1);
        quicksort(a, middle + 1, high);
    }

    int split (int a[], int low, int high)
    {
        int part_element = a[low];

        while (low < high)
        {
            while (low < high && a[high] > part_element) {
                high--;
            }
            if (low < high) {
                a[low++] = a[high];
            }

            while (low < high && a[low] < part_element) {
                low++;
            }
            if (low < high) {
                a[high--] = a[low];
            }
        }

        a[low] = part_element;
        return low;
    }
}