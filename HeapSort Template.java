/*
Description
Given an integer array, sort it in ascending order. Use heap sort algorithm.

Example
Given [3, 2, 1, 4, 5], return [1, 2, 3, 4, 5].

Tags
Sort Quick Sort Merge Sort
 */

/**
 * Approach: HeapSort
 * 下面的讲解全部都是按照 从小到大 进行排序的方法来解释的。
 * 如果想要按照 从大到小 的顺序进行排序，则我们需要采用 最小堆结构（原因在看完下面的解释后就清楚了）
 *
 * 堆排序就是把 最大堆 堆顶的最大数取出放到数组的尾部位置，然后将剩余的堆继续调整为最大堆，（即先确定数组中最大的数）
 * 再次将堆顶的最大数取出，这个过程持续到 剩余数只有一个 时结束。
 * 在堆中定义以下几种操作：
 *  最大堆调整（Max-Heapify）：调整堆的结构，使得各个节点的子节点永远小于父节点
 *  堆排序（Heap-Sort）：将堆顶的最大元素调整到数组尾部，堆的大小减1，然后对堆顶元素执行 siftDown 操作
 *  使得堆仍然保持 最大堆 的 结构性 和有序性。
 *
 * 堆排序的核心就是：
 *  Heapify 与 siftDown 操作。
 *  关于这二者的详细解释可以参考：
 *  https://github.com/cherryljr/LintCode/blob/master/Heapify.java
 * 堆排序的动画演示：
 * https://www.youtube.com/watch?v=MtQL_ll5KhQ
 */
class Solution {
    /**
     * @param A: an integer array
     * @return: nothing
     */
    public void sortIntegers2(int[] A) {
        if (A == null || A.length < 2) {
            return;
        }

        heapSort(A);
    }

    private void heapSort(int[] nums) {
        // 首先构造最大堆结构
        maxHeapify(nums);
        int size = nums.length;
        // 交换堆顶元素（当前最大值） 和 数组的末尾元素。同时堆的大小 减1
        swap(nums, 0, --size);
        while (size > 0) {
            // 移除堆顶元素后，对交换上来的堆顶元素进行 siftDown 操作，以维持最大堆的特性
            siftDown(nums, 0, size);
            // 继续移除 堆顶元素（当前最大值） 以完成排序
            swap(nums, 0, --size);
        }
    }

    private void maxHeapify(int[] nums) {
        for (int i = nums.length / 2; i >= 0; i--) {
            siftDown(nums, i, nums.length);
        }
    }

    private void siftDown(int[] nums, int index, int size) {
        while (index < size) {
            int max = index, left = 2 * index + 1;
            if (left < size && nums[left] > nums[max]) {
                max = left;
            }
            if (left + 1 < size && nums[left + 1] > nums[max]) {
                max = left + 1;
            }
            if (max == index) {
                break;
            }

            swap(nums, index, max);
            index = max;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}