/*
Description
Find K-th largest element in an array.

Notice
You can swap elements in the array

Example
In array [9,3,2,4,8], the 3rd largest element is 4.
In array [1,2,3,4,5], the 1st largest element is 5, 2nd largest element is 4, 3rd largest element is 3 and etc.

Challenge 
O(n) time, O(1) extra memory.

Tags 
Sort Quick Sort
*/

/**
 * 用到的知识点：QuickSelect, 详细介绍参见：
 * https://en.wikipedia.org/wiki/Quickselect
 *
 * 解法的分析历程，一步步推进优化：
 *  1. 对整个数据进行 Sort, 然后取第 k 个元素即可。
 *  O(nlogn) 时间复杂度 + O(1) 额外空间
 *  2. 利用大小为 k 的堆来实现，可以降低时间复杂度
 *  O(nlogk) 时间复杂度 + O(K) 额外空间
 *  3. 利用到了 QuickSort 中的 Partition 方法 (QuickSelect算法)
 *  O(N) 时间复杂度 / 最差情况下O(N^2)时间复杂度 + O(1) 额外空间
 *  时间复杂度分析：
 *      该算法实际上就是 利用了快速排序的 Partition 方法。
 *      当我们每次选择一个 pivot 进行 partition 之后，都会将数组分成两个部分。
 *      因此时间复杂度为: n + n/2 + n/4 + n/8 + ... + 1 = 2n => O(n)
 *      而之所以会出现 O(n^2) 的最坏情况原因与 快速排序 相同。
 *      即当我们 pivot 选择不好，每次只能筛去一个元素，使得其退化成 n + n-1 + n-2 + ... + 1 = n(n+1)/2 = O(n^2).
 *  4. 对第 3 种方法进行优化，随机化输入。使得均摊时间复杂度为 O(n)。
 *  即我们可以随机选择一个值来作为我们的划分点 pivot.
 *  为了保持代码的复用性，我们直接 随机选取了一个元素 与 数组的最后一个元素 进行了交换。其他代码保持不变即可。
 *  其本身对应的也就是 随机快速排序 的 partition 方法。
 *  5. BFPRT算法
 *  关于 BFPRT算法，想要了解的可以查看：http://www.jianshu.com/p/a43b0e1712d1
 *
 * Get more details here：https://discuss.leetcode.com/topic/14597/solution-explained
 */

/**
 * Approach 1: Quickselect
 * O(n) best case, O(n^2) worst case runnig time + O(1) memory
 */
class Solution {
    /*
     * @param k : description of k
     * @param nums : array of nums
     * @return: description of return
     */
    public int kthLargestElement(int k, int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (k <= 0) {
            return 0;
        }

        return helper(nums, 0, nums.length - 1, nums.length - k);
    }

    public int helper(int[] nums, int left, int right, int k) {
        if (left == right) {
            return nums[left];
        }

        int position = partition(nums, left, right);
        // Found kth smallest number
        if (position == k) {
            return nums[position];
        } else if (position < k) {
            // Check the right part
            return helper(nums, position + 1, right, k);
        }  else {
            // Check the left part
            return helper(nums, left, position - 1, k);
        }
    }

    // Quick select: kth smallest
    public int partition(int[] nums, int left, int right) {
        // 初始化左右指针和pivot
        int less = left - 1, more = right;
        // Take nums[right] as the pivot
        // int pivot = nums[right];

        // 进行partition
        while (left < more) {
            if (nums[left] < nums[right]) {
                swap(nums, ++less, left++);
            } else if (nums[left] > nums[right]) {
                swap(nums, --more, left);
            } else {
                left++;
            }
        }
        // 最后,交换 nums[right] (pivot) 和 nums[more]
        swap(nums, more, right);

        // 返回 等于pivot部分数组的左边界
        return less + 1;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}

/**
 * Approach 2:  QuickSelect (Shuffle the Array)
 * O(n) running time (Average) + O(1) space
 */
class Solution {
    /*
     * @param k : description of k
     * @param nums : array of nums
     * @return: description of return
     */
    public int kthLargestElement(int k, int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (k <= 0) {
            return 0;
        }

        return helper(nums, 0, nums.length - 1, nums.length - k);
    }

    public int helper(int[] nums, int left, int right, int k) {
        if (left == right) {
            return nums[left];
        }
        
        // swap a random value with nums[right] (shuffle the array)
        swap(nums, left + (int)(Math.random() * (right - left)), right);
        int position = partition(nums, left, right);
        // Found kth smallest number
        if (position == k) {
            return nums[position];
        } else if (position < k) {
            // Check the right part
            return helper(nums, position + 1, right, k);
        }  else {
            // Check the left part
            return helper(nums, left, position - 1, k);
        }
    }

    // Quick select: kth smallest
    public int partition(int[] nums, int left, int right) {
        // 初始化左右指针和pivot
        int less = left - 1, more = right;
        // Take nums[right] as the pivot
        // int pivot = nums[right];

        // 进行partition
        while (left < more) {
            if (nums[left] < nums[right]) {
                swap(nums, ++less, left++);
            } else if (nums[left] > nums[right]) {
                swap(nums, --more, left);
            } else {
                left++;
            }
        }
        // 最后,交换 nums[right] (pivot) 和 nums[more]
        swap(nums, more, right);

        // 返回 等于pivot部分数组的左边界
        return less + 1;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}

