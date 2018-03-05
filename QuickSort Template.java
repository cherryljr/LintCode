/*
Given an integer array, sort it in ascending order. Use quick sort O(nlogn) algorithm.

Example
Given [3, 2, 1, 4, 5], return [1, 2, 3, 4, 5].
*/

/**
 * Approach: QuickSort
 * 快速排序的核心就是选定一个 pivot, 根据它来进行 partition.
 * 使得所有 <pivot 的数都在其左边；所有 >pivot 的数都在其右边。
 * 从而将数组划分成 3 个部分 (小于pivot的；等于pivot的；大于pivot的）
 * 以上就是经典的 荷兰国旗 问题。均摊时间复杂度为 O(n)；空间复杂度为 O(1).
 * 对于这个 partition 部分的详细解析可以参考：
 * https://github.com/cherryljr/LintCode/blob/master/Sort%20Colors.java
 *
 * 然后我们需要对被划分好的左右两个部分再次进行 partition 操作，直到被划分的长度为 1 时 (left >= right).
 * 而整个数组总共可以被划分多少次呢？答案是 logn 次.
 * 因此 随机快速排序 的总体的时间复杂度为 O(nlogn) （均摊）
 *
 * 但是为什么这个时间复杂度是均摊的呢？（具有一定概率）
 * 我们都知道 pivot 的选取，直接关系到了时间复杂度的大小。（不合适的 pivot 将导致非常糟糕的数据分布）
 * 因此为了尽可能地保证 低时间复杂度，从而随机性地选择 pivot。这也就导致了时间复杂度为 O(nlogn) 是一个 概率性事件。
 * 具体优化过程可以参考：
 * https://github.com/cherryljr/LintCode/blob/master/Kth%20Largest%20Element.java
 *
 * partition 部分在代码实现上与 Sort Colors 相差不多，但是二者对于 pivot 的选择不同。
 * QuickSort 中选择的是 nums[right] 作为 pivot,在数组内部；Sort Colors 的 pivot 则是由外界直接给定。
 * 这就导致了 QuickSort 中 大于pivot部分左边界初始化有一点点不同。
 * 这里 more 指针初始化为 right,而不是 right+1. 因为我们需要维持 nums[right] 来作为 pivot,
 * 并在最后交换 nums[more] 和 nums[right]，完成 等于pivot数组部分的 右边界。
 *
 * Tips:
 * 对于快速排序的时间复杂度为什么是均摊 O(nlogn)，最坏O(n^2) 呢？
 * 因为 pivot 的选取是随机的，每个值被选取到作为 pivot 的概率是相同的。而每次
 * partition 的时间复杂度为 O(n),因此经过数学分析证明可得：均摊时间复杂度为 O(nlogn).
 * 但是在少数情况下 ，pivot 每次都选取到了最右边界的那个值，导致退化成了 O(n^2)的时间复杂度。
 *
 * 对于快速排序（随机快排）的空间复杂度为什么是均摊 O(logn)，最坏 O(n) 呢？
 * 因为快速排序必须每次记录下 pivot 的 index,这样才能根据这个 index 对数组进行划分。
 * 因此需要记录的划分点个数，就是其所需要的 额外空间 的大小。
 * 因为 pivot 是被随机选取的，所以经数学证明可得：我们说需要记录的index 个数均摊值为 O(logn) 个.即空间复杂度为 O(logn).
 * 但是同样存在因为 pivot 选择不当未能成功划分数组的情况，导致每个点都被选择作为了 pivot,使得时间复杂度上升到了 O(n).
 *
 * 注意：
 * 工程应用中，当样本为基础类型时，可以直接使用 快排。因为基础类型都一样，先后顺序不会存在问题。
 * 如果为自定义类型，使用 归并。这是考虑到了 排序稳定性 的原因。
 * 快排的 partition 做不到稳定性。比如：4 4 4 3，partition的时候，第一个4 会和 3 进行交换。
 *
 * 快速排序在工程应用上的一步步优化：
 * http://blog.csdn.net/hacker00011000/article/details/52176100
 */
public class Solution {
    /**
     * @param A: an integer array
     * @return: nothing
     */
    public void sortIntegers2(int[] A) {
        if (A == null || A.length < 2) {
            return;
        }

        quickSort(A, 0, A.length - 1);
    }

    private void quickSort(int[] nums, int left, int right) {
        if (left < right) {
            // swap a random value with nums[right] (shuffle the array)
            swap(nums, left + (int)(Math.random() * (right - left)), right);
            int[] positioin = partition(nums, left, right);
            // Sort the left part ( <pivot )
            quickSort(nums, left, positioin[0] - 1);
            // Sort the right part ( >pivot )
            quickSort(nums, positioin[1] + 1, right);
        }
    }

    private int[] partition(int[] nums, int left, int right) {
        // 初始化左右指针 和 pivot (注意 more 指针初始化的位置)
        int less = left - 1, more = right;
        // int pivot = nums[right];

        // 遍历到 大于pivot数组部分的 左边界 即可
        while (left < more) {
            // 当前元素小于 pivot 则放到 pivot 的左边
            if (nums[left] < nums[right]) {
                swap(nums, ++less, left++);
                // 当前元素大于 pivot 则放到 pivot 的右边
                // 注意 右边部分的最右端是 right-1. 因为 right 要最后用来与 more 进行 swap 以组成右边界.
            } else if (nums[left] > nums[right]) {
                swap(nums, left, --more);
            } else {
                left++;
            }
        }
        // 最后,交换 nums[more] 和 nums[right] (pivot) 完成 partition
        swap(nums, more, right);

        // 返回 等于pivot部分数组的 左右边界
        return new int[]{less + 1, more};
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
