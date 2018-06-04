/*
Description
Given an unsorted array nums, reorder it such that
nums[0] < nums[1] > nums[2] < nums[3]....
You may assume all input has valid answer.

Example
Given nums = [1, 5, 1, 1, 6, 4], one possible answer is [1, 4, 1, 5, 1, 6].
Given nums = [1, 3, 2, 2, 3, 1], one possible answer is [2, 3, 1, 3, 1, 2].

Challenge
Can you do it in O(n) time and/or in-place with O(1) extra space?
 */

/**
 * Approach 1: Quick Sort
 * 这道题目相比于 Wiggle Sort 仅仅是多了一个 不相等 的要求，但是难度却高上了不少。
 * 起码对于我而言，一时间确实没有想出 O(n) 的解法。
 * 取而代之的是，先利用 O(nlogn) 的排序算法进行排序，找出中位数作为分割点，然后再进行 swap 即可。
 * 这个方法还是比较容易想到的，做法与 Wiggle Sort 的 Approach 1 比较像，突破口在于 中位数。
 * 因为如果题目存在答案，那么排序后的元素序列必定为：
 *  a1 <= a2 <= median < a4 <= a5
 * 即 左半段的元素个数(可能含有中位数) - 右半段元素的个数(可能含有中位数) <= 1
 * （考虑到可能存在多个相等中位数的情况，数组实际上是被分为 小于 等于 大于 三段，如[1, 1, 2, 2, 3, 3]）
 * 因为必定存在 大于中位数的元素 来将 小于中位数的元素 分割开来，否则就会出现相邻数相等的情况。
 * 因此我们可以借助 中位数 作为判断的依据，将数组利用 中位数 进行 partition 之后，
 * 分别从 数组的中点 和 数组的最后一个元素 开始进行交叉，从而避免了将相邻的数排在一起的情况。
 *
 * 具体做法如下：
 * 先进行排序，然后找到数组的 中位数，它相当于把有序数组从中间分成三部分：小于中位数的前段 等于中位数的中段 大于中位数的后段
 * (然而这里实际上操作时，我们只需要分成两段即可。但是掌握 partition 这一部分对加深该题的理解是非常有帮助的)
 * 然后从前半段的末尾取一个，再从后半段的末尾取一个，再从后半段的末尾取一个，这样保证了第一个数小于第二个数；
 * 然后再从前半段取倒数第二个，从后半段取倒数第二个，这保证了第二个数大于第三个数，且第三个数小于第四个数。
 * 以此类推直至都取完。之所以这么取就是为了将相等的数分开来放。
 * （这里因为已经排序好了，就不需要再跟中位数比较，然后决定要放哪了，直接利用 leftEnd, rightEnd 两个指针往原数组中放即可）
 *
 * 时间复杂度：O(nlogn)
 * 空间复杂度：O(n)   介于swap的方法，我们需要额外空间来存储排序好的数组
 */
public class Solution {
    /*
     * @param nums: A list of integers
     * @return: nothing
     */
    public void wiggleSort(int[] nums) {
        int len = nums.length;;
        // int[] temp = Arrays.copyOf(nums, len);
        // Arrays.copyOf()底层调用的就是 System.arraycopy() 方法
        int[] temp = new int[len];
        // 数组拷贝使用 System.arraycopy() 无疑是最高效的，它是一个 native 的方法
        System.arraycopy(nums, 0, temp, 0, len);
        Arrays.sort(temp);
        int leftEnd = (len - 1) >> 1;   // 左半段的最后一个数（中位数）
        int rightEnd = len - 1;         // 右半段的最后一个数
        int index = 0;
        while (leftEnd >= 0 && rightEnd > ((len - 1) >> 1)) {
            nums[index++] = temp[leftEnd--];
            nums[index++] = temp[rightEnd--];
        }
        // 因为右半段数的个数 <= 左半段数的个数
        // 所以当 while 循环结束时，我们还需要检查左半段是否还有数可以取
        if (leftEnd >= 0) {
            nums[index] = nums[leftEnd];
        }
    }
}

/**
 * Approach 2: Partition (Find the Median)
 * 有了 Approach 1 中的基础，我们可以发现，其实我们只需要找到中位数，
 * 并将 中位数 作为判断依据把各个数放到合适的位置上，也就是说并不需要对全部的数进行一个排序。
 * 那么问题就可以转换成：寻找中位数，并依据中位数进行 partition 操作。
 * 而我们知道 寻找中位数 和 partition 操作都是平均 O(n) 时间复杂度的。
 * 因此我们也就想出了这道题目 O(n) 的解法。
 * 也就是找到中位数后，遍历数组，如果当前的数 大于中位数 就将其放在 奇数 位置,
 * 如果 小于中位数 就将其放在 偶数 位置。
 * 而我们在利用 partition 找寻中位数的过程中实际上已经将数组分成了：
 *  小于中位数 等于中位数 大于中位数 这三段了。
 * 因此这里我们可以将其看作是排序好的，从而直接使用 Approach 1 中的方法，将各个数字放到合适的位置上。
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 */
public class Solution {
    /*
     * @param nums: A list of integers
     * @return: nothing
     */
    public void wiggleSort(int[] nums) {
        int len = nums.length;
        // Do the partition according to the median
        getKthNumber(nums, 0, len - 1, (len - 1) >> 1);
        int[] temp = new int[len];
        System.arraycopy(nums, 0, temp, 0, len);

        int leftEnd = (len - 1) >> 1, rightEnd = len - 1;
        int index = 0;
        while (leftEnd >= 0 && rightEnd > ((len - 1) >> 1)) {
            nums[index++] = temp[leftEnd--];
            nums[index++] = temp[rightEnd--];
        }
        if (leftEnd >= 0) {
            nums[index] = nums[leftEnd];
        }
    }

    private void getKthNumber(int[] nums, int left, int right, int k) {
        if (left == right) {
            return;
        }

        int position = partition(nums, left, right);
        if (position == k) {
            return;
        } else if (position < k) {
            getKthNumber(nums, position + 1, right, k);
        } else {
            getKthNumber(nums, left, position - 1, k);
        }
    }

    private int partition(int[] nums, int left, int right) {
        int less = left - 1, more = right;
        while (left < more) {
            if (nums[left] < nums[right]) {
                swap(nums, ++less, left++);
            } else if (nums[left] == nums[right]) {
                left++;
            } else {
                swap(nums, --more, left);
            }
        }
        swap(nums, more, right);
        return less + 1;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}

/**
 * Approach 3: Partition (Space Optimized)
 * 本题实际上还可以在 空间复杂度 上进一步优化
 * http://liveasatree.blogspot.sg/2016/01/leetcode-wiggle-sort-ii.html
 */