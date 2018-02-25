/*
Given an integer array, heapify it into a min-heap array.

For a heap array A, A[0] is the root of heap, and for each A[i], A[i * 2 + 1] is the left child of A[i]
and A[i * 2 + 2] is the right child of A[i].

Example
Given [3,2,1,4,5], return [1,2,3,4,5] or any legal heap array.

Challenge
O(n) time complexity

Clarification

What is heap?
Heap is a data structure, which usually have three methods: push, pop and top.
where "push" add a new element the heap, "pop" delete the minimum/maximum element in the heap,
"top" return the minimum/maximum element.

What is heapify?
Convert an unordered integer array into a heap array.

Tags Expand
LintCode Copyright Heap
*/


/**
 * Approach: siftDown | siftUp
 * 在 Java 中 Default 的 PriorityQueue 就是一个现成的 minHeap：所有后面的对应element都比 curr element 小。
 *
 * Heapify做了什么？
 * 确保在 Heap 里面 curr node 下面的两个孩子，以及下面所有的 node 都遵循一个规律:
 * 比如在这里，若是 minHeap,就是后面的两孩子都要比自己大。若不是，就要swap。
 * 保证 Heap 的 结构性 与 有序性。
 *
 * Heapify (构建堆)时的 siftDown 的操作：
 * 从数组的 中间开始 向前面遍历，进行 siftDown 操作,即：for(i = n/2-1 ~ 0)
 * 从右向左，扫描数组中一半的元素即可。跳过大小为 1 的子堆，这样可以降低时间复杂度。
 *
 * 当然，除了 siftDown 还有 siftUp, 二者思路相同，只是方向不同。
 * siftDown构造堆的方向 从右向左
 * siftUp构造堆的方向   从左向右
 *
 * 现在我们进行时间复杂度分析 （采用 siftDown 操作）
 *  假设总共有 n 个节点，则 Heap 的高度为 h = log(n).
 *  则时间复杂度为：
 *      sum = (0 * n/2) + (1 * n/4) + (2 * n/8) + ... + (h * 1)
 *      经数学证明可得结果为：sum < n,即时间复杂度为 O(n)
 * 具体分析与证明详见：
 *      https://stackoverflow.com/questions/9755721/how-can-building-a-heap-be-on-time-complexity
 *
 * 时间复杂度分析 （采用 siftUp 操作）
 *  因为我们知道插入一个节点的时间复杂度为 O(logn).
 *  因此在起始状态，我们可以把 heapSize 看作是 0，然后依次插入元素，进行 siftUp 操作来维持堆的性质。
 *  则时间复杂度为：
 *      sum = log(1) + log(2) + log(3) + ... + log(n)
 *      经过数学证明可得结果为：sum 收敛于 n,即时间复杂度为 O(n)
 *
 * 由上述分析可得，利用这两个操作实现 Heapify 的时间复杂度都是 O(n)。
 * 但是为什么我们在 Heapify 的时候要采用 siftDown 的方法而不是 siftUp 的方法呢？
 * 原因在于：
 *      siftDown 的complexity，实质上是node相对于 bottom 移动的次数，
 *      而根据 Heap(满二叉树) 本身的特性，决定了越靠近 bottom 的node越多；
 *      相对照 siftUp 的 complexity，实际上是node相对于 root 节点的移动次数。
 *      因此虽然利用这两个操作实现 Heapify 的时间复杂度都是 O(N). 但是 siftDown 会比 siftUp 更快一些。
 *      这边我们也可以延伸出一个重要的规律：
 *      当我们需要对 Heap 中的每个节点都进行某一操作时，往往会选择使用 siftDown 操作来维持 Heap 的性质。
 *
 * Heap 的三种基本操作：
 *  1.Heapify：将一个无序的序列初始化成堆。对每个节点执行 siftDown 操作（记住是从右到左，中间开花） O(N)
 *  2.add：在数组的末尾插入新的元素，然后执行 siftUp 操作. O(logN)
 *  3.pop: 将堆顶元素与数组的末尾元素进行交换，然后删除末尾元素，再对堆顶元素进行 siftDown 操作
 *  3.remove：删除特定某一个value.
 *  遍历整个 heap 找到要删除的元素 O(n),将该元素与 heap 的末尾元素进行交换，删除末尾元素，
 *  然后进行 siftUp / siftDown 操作 O(logn)。
 */
public class Solution {
    /*
     * @param A: Given an integer array
     * @return: nothing
     */
    public void heapify(int[] A) {
        if (A == null || A.length <= 1) {
            return;
        }

        for (int i = A.length / 2; i >= 0; i--) {
            siftDown(A, i);
        }
        // for (int i = 0; i < A.length; i++) {
        //     siftUp(A, i);
        // }
    }

    private void siftDown(int[] A, int index) {
        while (index < A.length) {
            int min = index;
            // Compare A[index] with its left child and right child
            // get the minimum one
            if (2 * index + 1 < A.length && A[2 * index + 1] < A[min]) {
                min = 2 * index + 1;
            }
            if (2 * index + 2 < A.length && A[2 * index + 2] < A[min]) {
                min = 2 * index + 2;
            }
            if (min == index) {
                break;
            }

            // Swap A[index] and the minimum of its children
            int temp = A[index];
            A[index] = A[min];
            A[min] = temp;

            index = min;
        }
    }

    private void siftUp(int[] A, int k) {
        while (k != 0) {
            // if A[father] is bigger than A[index]
            // then swap them
            int father = (k - 1) / 2;
            if (A[k] > A[father]) {
                break;
            }

            int temp = A[k];
            A[k] = A[father];
            A[father] = temp;

            k = father;
        }
    }
}