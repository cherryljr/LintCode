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
 * 这就直接导致了以下两个不同点：
 *  1. quickSort 中，我们只需要遍历到 等于pivot部分数组的右边界 即可，因为再后面的就是 >pivot 的部分了，partition 已经完成；
 *  而 Sort Colors 中，因为 pivot 可能并不存在与数组中，并且 nums[end] 并不确定，所以我们要一直遍历到 end 为止。
 *  2. quickSort 中的我们选定 pivot 为 nums[end]。
 *  因此对于 >pivot 的元素，我们都会放在 (right, end-1) 上面。
 *  ( right 指的是 等于pivot部分数组的右边界+1 )
 *  直到最后一步，将 nums[right] 与 nums[end] 交互，最终完成 partition.
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

    private void quickSort(int[] nums, int l, int r) {
        if (l < r) {
            // swap a random value with nums[right] (shuffle the array)
            swap(nums, l + (int)(Math.random() * (r - l)), r);
            int[] positioin = partition(nums, l, r);
            // Sort the left part ( <pivot )
            quickSort(nums, l, positioin[0] - 1);
            // Sort the right part ( >pivot )
            quickSort(nums, positioin[1] + 1, r);
        }
    }

    private int[] partition(int[] nums, int start, int end) {
        // 初始化左右指针 和 pivot
        int left = start, right = end;
        int pivot = nums[end];

        // 遍历到 等于pivot数组部分的 右边界 即可
        while (start < right) {
            // 当前元素小于 pivot 则放到 pivot 的左边
            if (nums[start] < pivot) {
                swap(nums, left++, start++);
            // 当前元素大于 pivot 则放到 pivot 的右边
            // 注意 右边部分的最右端是 end-1. 因为 end 要最后用来与 right 进行 swap 以组成右边界.
            } else if (nums[start] > pivot) {
                swap(nums, start, --right);
            } else {
                start++;
            }
        }
        // 最后,交换 nums[right] 和 nums[end] (pivot) 完成 partition
        swap(nums, right, end);

        return new int[]{left, right};
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}