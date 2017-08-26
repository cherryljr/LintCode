Note:
  该题中我们需要创建一个新的矩阵类，其包含了矩阵中各个元素的 Value, Row, Column
  这三个值。具体原因见 Solution1 操作第2点。
  并且该类需要能够互相比较大小。
  在这里有两个实现方法：
  1. 类本身实现了 Comparable 接口. 重写 compareTo 方法（本程序采用了该种写法）
  2. 创建一个新的比较器 Comparator 在这里面实现该类的比较方法. 重写 compare 方法.
  （可利用 内部类 在创建 PriorityQueue 时直接创建,编写代码）

Solution1: PriorityQueue
该题与 Merge K sorted Arrays 思路相同，并且要求时间复杂度为 O(klogn).
故我们想到可以利用到 PriorityQueue 这个数据结构。
矩阵为递增矩阵，要求的元素为 kth samllest number. 故我们可以把范围锁定在
k * k 大小的左上角矩阵中。
具体操作为：
  1. 利用矩阵第一行的元素创建一个 minHeap (若第一行元素长度大于k,则取前 k 个元素).
  2. 每次将堆顶元素 poll 出去，同时我们需要被 poll 出的元素的 行 和 列 的值.故我们堆中所保存的元素应该包含有这些信息.
  然后我们将与该元素同一列的下一行元素 offer 进去替代它。进行 k-1 次操作即可。
Note：
  你也可以利用矩阵的第一列元素来构建 / 初始化 minHeap, 然后重复相似的操作：
  （推出 peek element 后，利用与被 poll 元素同一行的下一列元素替代它）
 
Solution2: BinarySearch
由 Search a 2D Matrix 与 O(klogN) 的时间复杂度，我们想到该题或许也能够使用二分法进行计算。
利用递增矩阵的性质，我们计算好当前元素是第几大的 count 即可。

/*
Description
Find the kth smallest number in at row and column sorted matrix.

Example
Given k = 4 and a matrix:
[
  [1 ,5 ,7],
  [3 ,7 ,8],
  [4 ,8 ,9],
]
return 5

Challenge
Solve it in O(k log n) time where n is the bigger one between row size and column size.

Tags
Priority Queue Heap Matrix
*/

// Solution 1: PriorityQueue
public class Solution {
    /**
     * @param matrix: a matrix of integers
     * @param k: an integer
     * @return: the kth smallest number in the matrix
     */
    class Tuple implements Comparable<Tuple> {
        int x;
        int y;
        int value;
       
        Tuple(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }
       
        public int compareTo(Tuple t) {
            return this.value - t.value;
        }
    }
   
    public int kthSmallest(int[][] matrix, int k) {
        if (matrix == null || matrix[0].length == 0) {
            return -1;
        }
       
        PriorityQueue<Tuple> pq = new PriorityQueue<Tuple>();
        int bound = matrix[0].length > k ? k : matrix[0].length;
        for (int j = 0; j < bound; j++) {
            pq.offer(new Tuple(0, j, matrix[0][j]));
        }
        for (int i = 0; i < k - 1; i++) {
            Tuple t = pq.poll();
            if (t.x == matrix.length - 1) {
                continue;
            }
            pq.offer(new Tuple(t.x+1, t.y, matrix[t.x+1][t.y]));
        }
       
        return pq.peek().value;
    }
}

// Solution2: BinarySearch
public class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        int lo = matrix[0][0];
        int hi = matrix[matrix.length - 1][matrix[0].length - 1] + 1; //[lo, hi)
       
        while(lo < hi) {
            int mid = lo + (hi - lo) / 2;
            int count = 0,  j = matrix[0].length - 1;
           
            for(int i = 0; i < matrix.length; i++) {
                while(j >= 0 && matrix[i][j] > mid) {
                    j--;
                }
                count += (j + 1);
            }
            if(count < k) lo = mid + 1;
            else hi = mid;
        }
       
        return lo;
    }
}