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
 * 用到的知识点：Quickselect, 详细介绍参见：
 * https://en.wikipedia.org/wiki/Quickselect
 * 
 * 解法的分析历程，一步步推进优化：
 *  1. 对整个数据进行 Sort, 然后取第 k 个元素即可。
 *  O(NlogN) 时间复杂度 + O(1) 额外空间
 *  2. 利用大小为 k 的堆来实现，可以降低时间复杂度
 *  O(NlogK) 时间复杂度 + O(K) 额外空间
 *  3. 利用到了 QuickSort 中的分支方法 (Quickselect算法)
 *  O(N) 时间复杂度 / 最差情况下O(N^2)时间复杂度 + O(1) 额外空间
 *  时间复杂度分析：
 *      该算法实际上就是 利用了快速排序的 Partition 方法。
 *      当我们每次选择一个 pivot 进行 partition 之后，都会将数组分成两个部分。
 *      因此时间复杂度为: n + n/2 + n/4 + n/8 + ... + 1 = 2n => O(n)
 *      而之所以会出现 O(n^2) 的最坏情况原因与 快速排序 相同。
 *      即当我们 pivot 选择不好，每次只能筛去一个元素，使得其退化成 n + n-1 + n-2 + ... + 1 = n(n+1)/2 = O(n^2).
 *  4. 对第 3 种方法进行优化，随机化输入。使得 O(N) 的时间复杂度得以保证 (BFPRT算法)
 *  该算法的一个典型应用便是 Top K 问题
 *  O(N) 时间复杂度 + O(1) 额外空间
 *  BFPRT算法的思想只是在选取 pivot 值时通过 random 的方法进行选取罢了，因此我们可以通过 shuffle 函数来对
 *  数组进行一次随机排序来实现。
 *  关于 BFPRT算法，想要进一步了解的可以查看：http://www.jianshu.com/p/a43b0e1712d1
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
        // write your code here
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (k <= 0) {
            return 0;
        }

        return helper(nums, 0, nums.length - 1, nums.length - k);
    }

    public int helper(int[] nums, int l, int r, int k) {
        if (l == r) {
            return nums[l];
        }

        int position = partition(nums, l, r);
        // Found kth smallest number
        if (position == k) {
            return nums[position];
        } else if (position < k) {
            // Check the right part
            return helper(nums, position + 1, r, k);
        }  else {
            // Check the left part
            return helper(nums, l, position - 1, k);
        }
    }

    // Quick select: kth smallest
    public int partition(int[] nums, int l, int r) {
        // 初始化左右指针和pivot
        int left = l, right = r;
        // Take nums[right] as the pivot
        int pivot = nums[right];

        // 进行partition
        for (int i = l; i < r; i++) {
            // 根据快速排序的思想，将所有 小于 pivot 的数放到 pivot 的左边
            if (nums[i] < pivot) {
                swap(nums, left++, i);
            }
        }
        // 最后,交换 nums[right] (pivot) 和 nums[left]
        swap(nums, left, right);

        // 返回当前pivot所在的index
        return left;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}

/**
 * Approach 2:  BFPRT
 * O(n) guaranteed running time + O(1) space
 */
class Solution {
    /*
     * @param k : description of k
     * @param nums : array of nums
     * @return: description of return
     */
    public int kthLargestElement(int k, int[] nums) {
        // write your code here
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (k <= 0) {
            return 0;
        }

        shuffle(nums);
        return helper(nums, 0, nums.length - 1, nums.length - k);
    }

    public int helper(int[] nums, int l, int r, int k) {
        if (l == r) {
            return nums[l];
        }

        int position = partition(nums, l, r);
        // Found kth smallest number
        if (position == k) {
            return nums[position];
        } else if (position < k) {
            // Check the right part
            return helper(nums, position + 1, r, k);
        }  else {
            // Check the left part
            return helper(nums, l, position - 1, k);
        }
    }

    // Quick select: kth smallest
    public int partition(int[] nums, int l, int r) {
        // 初始化左右指针和pivot
        int left = l, right = r;
        // Take nums[right] as the pivot
        int pivot = nums[right];

        // 进行partition
        for (int i = l; i < r; i++) {
            // 根据快速排序的思想，将所有 小于 pivot 的数放到 pivot 的左边
            if (nums[i] < pivot) {
                swap(nums, left++, i);
            }
        }
        // 最后,交换 nums[right] (pivot) 和 nums[left]
        swap(nums, left, right);

        // 返回当前pivot所在的index
        return left;
    }

    // 将数组元素打乱，使得 pivot 值的选取为随机
    private void shuffle(int nums[]) {
        final Random random = new Random();
        for(int index = 1; index < nums.length; index++) {
            final int r = random.nextInt(index + 1);
            swap(nums, index, r);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

}

