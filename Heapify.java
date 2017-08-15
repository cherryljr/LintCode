Default 的PriorityQueue就是一个现成的 minHeap：所有后面的对应element都比curr element 小。

Heapify里面的siftdown的部分：
	只能从for(i = n/2-1 ~ 0)， 而不能从for(i = 0 ~ n/2 -1): 必须中间开花，向上跑的时候才能确保脚下是符合heap规则的

Heapify / SiftDown做了什么？    
确保在heap datastructure里面curr node下面的两个孩子，以及下面所有的node都遵循一个规律。   
比如在这里，若是min-heap,就是后面的两孩子都要比自己大。若不是，就要swap。
保证 Heap 的 结构性 与 有序性。 （详见：数据结构与算法分析 Java描述）     

当然，除了 siftDown 还有 siftUp, 二者思路相同，只是方向不同。
	siftDown的时间复杂度： O(N)
	siftUp的时间复杂度：   O(NlogN)
	
Heap 的三种基本操作：
	1.初始化：将一个无序的序列初始化成堆。对每个节点执行 siftDown 操作. O(N)
	2.插入：在数组的末尾插入新的元素，然后执行 siftUp 操作. O(logN)
	3.删除：删除某一个value. 将该节点变为 null, 然后执行 siftDown / siftUp (只能执行其中一种)
					比如 poll() 操作，删除 head节点，则执行 siftDown 操作。

还是要记一下min-heap的判断规律:for each element A[i], we will get A[i * 2 + 1] >= A[i] and A[i * 2 + 2] >= A[i].


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
If it is min-heap, for each element A[i], we will get A[i * 2 + 1] >= A[i] and A[i * 2 + 2] >= A[i].

What if there is a lot of solutions?
Return any of them.
Tags Expand 
LintCode Copyright Heap
*/

// Version 1: siftDown cost O(N)
public class Solution {
    /**
     * @param A: Given an integer array
     * @return: void
     */
    public void heapify(int[] A) {
        for (int i = A.length / 2; i >= 0; i--) {
            siftDown(A, i);
        }
    }
    
    private void siftDown(int[] A, int k) {
        while (k < A.length) {
            int min = k;
           
            if (2 * k + 1 < A.length && A[2 * k + 1] < A[min]) {
                min = 2 * k + 1;
            }
            if (2 * k + 2 < A.length && A[2 * k + 2] < A[min]) {
                min = 2 * k + 2;
            }
            if (k == min) {
                break;
            }
            
            int temp = A[min];
            A[min] = A[k];
            A[k] = temp;
            
            k = min;
        }
    }
}

// Version 2: siftUp cost O(NlogN)
public class Solution {
    /**
     * @param A: Given an integer array
     * @return: void
     */
    private void siftup(int[] A, int k) {
        while (k != 0) {
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
    
    public void heapify(int[] A) {
        for (int i = 0; i < A.length; i++) {
            siftup(A, i);
        }
    }
}