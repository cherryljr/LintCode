/*
Description
For an array A, if i < j, and A [i] > A [j], called (A [i], A [j]) is a reverse pair.
return total of reverse pairs in A.

Example
Given A = [2, 4, 1, 3, 5] , (2, 1), (4, 1), (4, 3) are reverse pairs. return 3

Tags 
Array Merge Sort
 */
 
 /**
 * Main Idea: Divide and Conquer
 * 为了求逆序对，最简单粗暴的方法就是每遇到一个数，就对它前面所有的数进行一次遍历。
 * 从而得到这个数可以组成几个逆序对，相应的时间复杂度也为: O(n^2)
 * 但是，显然这个查询操作太过耗费时间，因此我们想有没有更加好的方法使得查询可以花费更少的时间。
 * 因此我们可以想，是否可以对查询操作进行优化，使得每次查询操作只花费 O(logn) 的时间呢？
 * 答案当然是可以的，并且看到 O(logn) 的时间复杂度，我们很容易就联想到 二分查找算法 与 树这么个数据结构。
 * 他们可以使得查询算法得到极大的优化。因此我们想到了使用 二分查找树 来帮我解决该问题。
 * 并且继续观察该题目，实际上我们可以将它拆分成一个个小区间的子问题来解决。
 * 每个 大区间的逆序对 均可以由各个 小区间的逆序对 与 小区间之间组成的逆序对 相加得来。
 * 因此这道题目也可以使用 归并排序 来进行解决。
 * 通过以上分析，我们可以发现该题不管是使用 Tree 还是 Merge Sort 其核心都是：分治思想
 * 下面请看 4 种解决方法的详细分析与代码实现。
 *
 * 参考资料：
 * https://leetcode.com/articles/reverse-pairs/
 * https://leetcode.com/problems/reverse-pairs/discuss/97268/General-principles-behind-problems-similar-to-%22Reverse-Pairs%22
 */

/**
 * Approach 1: Binary Search Tree (Time Limit Exceed)
 * 为了优化查询时间复杂度，我们使用了 BST 该数据结构来进行辅助。
 * 每个节点包含 val:节点值 与 count:大于等于该节点的元素个数。
 * 该数据结构包含两个方法：
 * insert(Node root, int val), 它主要用来将节点值 val 插入树中。
 * search(Node root, int target), 它主要用来查询 target 所能够组成的逆序对个数。
 * （以上两个方法的分析请看代码注释）
 * 借助于以上两个方法，我们只需要对 A[] 进行一次遍历，然后每次先查询比 A[i] 大的数，
 * 将其加到 rst 上，然后再将 A[i] 插入到 BST 中即可。
 * 得益于 BST 的树状结构，我们可以将每次的查找操作简化到 O(logn) 级别。
 * 但是由于 BST 并不能够维持其平衡性，因此在最坏情况下，每次查询的时间为 O(n)。
 * 因此总体的时间复杂度为: O(n^2)
 *
 * 于是，我们想既然 BST 因为无法维持其平衡性从而导致了时间复杂度的恶化，那我们保持其平衡性不好了吗？
 * 没错，因此我们可以使用 红黑树/AVL树/BIT 来帮我们保证 O(nlogn) 的时间复杂度。
 * 详情请看 Approach 2
 */
public class Solution {
    class Node {
        int val, count;
        Node left, right;

        Node(int val) {
            this.val = val; // 节点值
            this.count = 1; // 大于等于该节点的元素个数
            this.left = this.right = null;
        }
    }

    /*
     * @param A: an array
     * @return: total of reverse pairs
     */
    public long reversePairs(int[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }

        Node root = null;
        int rst = 0;
        // 遍历数组，查询每个数所能组成的逆序对个数，然后相加起来
        for (int i : A) {
            rst += search(root, i + 1);
            root = insert(root, i);
        }

        return rst;
    }

    private int search(Node root, int target) {
        // 如果 root 为空，直接返回 0 即可
        if (root == null) {
            return 0;
        // 如果 root.val 与 所要查找的值 相等，则直接返回 root.count 即可
        } else if (root.val == target) {
            return root.count;
        // 如果 root.val 小于 所要查找的值，说明目前还无法组成逆序对，进一步对 root 的右子树进行查找
        } else if (root.val < target) {
            return search(root.right, target);
        // 如果 root.val 大于 所要查找的值，说明可以组成逆序对，并且进一步对 root 的左子树进行查找
        } else {
            return root.count + search(root.left, target);
        }
    }

    private Node insert(Node root, int val) {
        // 如果 root 为空，直接建立新的节点并返回
        if (root == null) {
            return new Node(val);
        // 如果 root.val == val，则 root.count 需要加1
        } else if (root.val == val) {
            root.count++;
        // 如果 root.val < val,则说明新插入的值比 root 要大
        // 所以更新 root.count 使其 加1，并将其插入到 root 的右子树中
        } else if (root.val < val) {
            root.count++;
            root.right = insert(root.right, val);
        // 如果 root.val > val，这说明新插入的值比 root 小，
        // root.count 无需更新，将新节点插入到 root 的左子树中即可。
        } else {
            root.left = insert(root.left, val);
        }
        return root;
    }
}

/**
 * Approach 2: Binary Index Tree
 * 为了保证能够有 O(nlogn) 的时间复杂度，我们需要使用更加高级的数据结构。
 * 但是 AVL树 和 红黑树 在代码实现上实在过于复杂，因此这里使用了较为简单的 BIT。
 * BIT 在计算区间值上面可以发挥非常高效的作用。
 * 实际上该题可以转换为 Count of Smaller Numbers After Self.
 * https://github.com/cherryljr/LeetCode/blob/master/Count%20of%20Smaller%20Numbers%20After%20Self.java
 * 对于求逆序对，我们可以倒过来看：
 * 从数组尾出发，如果该元素之后存在一个元素 A[j]，使得 A[j] < A[i] 且 j > i.
 * 那么 A[i] 和 A[j] 就构成了一个逆序对，因此该问题用如下的方法解决：
 *  1. 计算出各个数后面比他小的元素个数（组成的逆序对数）
 *  2. 将各个数的逆序对数相加起来，即可得到答案。
 *
 * 时间复杂度: O(nlogn)
 */
public class Solution {
    /*
     * @param A: an array
     * @return: total of reverse pairs
     */
    public long reversePairs(int[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }

        int minValue = Integer.MAX_VALUE;
        int maxValue = Integer.MIN_VALUE;
        for (int i : A) {
            minValue = Math.min(minValue, i);
        }
        int[] diff = new int[A.length];
        for (int i = 0; i < diff.length; i++) {
            diff[i] = A[i] - minValue + 1;
            maxValue = Math.max(maxValue, diff[i]);
        }

        int[] BITree = new int[maxValue + 1];
        long rst = 0;
        for (int i = diff.length - 1; i >= 0; i--) {
            rst += query(BITree, diff[i] - 1);
            update(BITree, diff[i]);
        }
        return rst;
    }

    private int query(int[] BITree, int index) {
        int sum = 0;
        for (; index > 0; index -= (index & -index)) {
            sum += BITree[index];
        }
        return sum;
    }

    private void update(int[] BITree, int index) {
        for (; index < BITree.length; index += (index & -index)) {
            BITree[index] += 1;
        }
    }
}

/**
 * Approach 3: Binary Index Tree (with Discretization)
 * 引入了 离散化 处理的 BITree 解决方法。
 * 与 Count of Smaller Numbers After Self 的 Approach 2 相同。
 * https://github.com/cherryljr/LeetCode/blob/master/Count%20of%20Smaller%20Numbers%20After%20Self.java
 *
 * 对于 离散化 的具体分析可以参见：
 * https://github.com/cherryljr/LintCode/blob/master/Count%20of%20Smaller%20Number%20before%20itself.java
 */
public class Solution {
    /*
     * @param A: an array
     * @return: total of reverse pairs
     */
    public long reversePairs(int[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }

        int len = A.length;
        // Discretization
        int[] sorted_arr = Arrays.copyOf(A, len);
        Arrays.sort(sorted_arr);
        int[] discre = new int[len];
        for (int i = 0; i < len; i++) {
            discre[i] = Arrays.binarySearch(sorted_arr, A[i]) + 1;
        }

        int[] BITree = new int[len + 1];
        long rst = 0;
        for (int i = A.length - 1; i >= 0; i--) {
            rst += query(BITree, discre[i] - 1);
            update(BITree, discre[i]);
        }
        return rst;
    }

    private int query(int[] BITree, int index) {
        int sum = 0;
        for (; index > 0; index -= (index & -index)) {
            sum += BITree[index];
        }
        return sum;
    }

    private void update(int[] BITree, int index) {
        for (; index < BITree.length; index += (index & -index)) {
            BITree[index] += 1;
        }
    }
}

/**
 * Approach 4: Merge Sort
 * 逆序对是指 i<j 并且 nums[i] > nums[j].
 * 那么在排序的过程中，会把 nums[i] 和 nums[j] 交换过来，这个交换的过程，每交换一次，就是一个逆序对的“正序”过程。
 *
 * 解题思路是利用分治的思想：
 * 先求前面一半数组的逆序对数；再求后面一半数组的逆序对数；
 * 最后求前面一半数组比后面一半数组中大的数的个数（也就是逆序对数）,这三个过程加起来就是整体的逆序数目了。
 * 看到这里是不是有点像归并排序呢？
 * 归并排序的思想就是把前一段排序，后一段排序，然后再整体排序。（先局部有序，再整体有序）
 * 而且，归并的规程中，需要判断前一半数组和后一半数组中当前数字的大小，这也就是刚刚描述的逆序的判断过程了。
 * 如果前一半数组的当前数字大于后一半数组的当前数字，那么这就是一个逆序对。
 * 因此我们可以利用 merge 方法，在 merge 的过程中计算出 逆序对 的个数。
 *
 * 过程：
 * 我们使用两个指针 p1, p2分别指向左右两个 subarray 的起始位置。
 * 当 p2 <= right && nums[p1] > nums[p2] 时，右指针 p2 继续向右移动。直到逆序对不成立为止。
 * 这样我们便能够计算出左边部分指针指向 p1 时，有多少个逆序数对。
 * 最后当 p1 遍历到 mid （即遍历完左边的 subarray）后，我们便可以得到总共有多少个逆序对。
 * 最后我们需要将 左半部分的数组 与 右半部分的数组 merge 起来完成排序。
 * 这也体现了 归并排序 是先局部有序，再整体有序。
 * 代码的具体实现与 MergeSort 差不多，可以参照 MergeSort Template：
 * https://github.com/cherryljr/LintCode/blob/master/MergeSort%20Template.java
 * 时间复杂度为：O(nlogn)
 */
public class Solution {
    /*
     * @param A: an array
     * @return: total of reverse pairs
     */
    public long reversePairs(int[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }

        return mergeSort(A, 0, A.length - 1);
    }

    private int mergeSort(int[] nums, int left, int right) {
        if (left >= right) {
            return 0;
        }

        int mid = left + ((right - left) >> 1);
        // 结果为左半段数组中的逆序对数 + 右半段数组中的逆序对数 + 左边段数组中 与 右半段数组中 的元素组成的逆序对数
        return mergeSort(nums, left, mid) + mergeSort(nums, mid + 1, right)
                + merge(nums, left, mid, right);
    }

    private int merge(int[] nums, int left, int mid, int right) {
        int[] helper = new int[right - left + 1];

        int i = 0, rst = 0;
        int p1 = left, p2 = mid + 1, p = mid + 1;
        while (p1 <= mid) {
            // 当 p<=right 并且 能够与 nums[p] 组成逆序对的时候，指针 p 向后移动
            while (p <= right && nums[p1] > nums[p]) {
                p++;
            }
            // 计算出 合并当前左右两个部分时，由 nums[p1] 所产生的逆序对数
            rst += p - (mid + 1);

            while (p2 <= right && nums[p1] > nums[p2]) {
                helper[i++] = nums[p2++];
            }
            helper[i++] = nums[p1++];
        }
        while (p2 <= right) {
            helper[i++] = nums[p2++];
        }

        // 将排序好的 helper 数组的值拷贝覆盖到原来的数组中
        for (i = 0; i < helper.length; i++) {
            nums[left + i] = helper[i];
        }
        return rst;
    }
}
