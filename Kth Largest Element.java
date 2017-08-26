用到的知识点：Quickselect, 详细介绍参见：
	https://en.wikipedia.org/wiki/Quickselect

解法的分析历程，一步步推进优化：
	1. 对整个数据进行 Sort, 然后取第 k 个元素即可。 
	O(NlogN) 时间复杂度 + O(1) 额外空间
	2. 利用大小为 k 的堆来实现，可以降低时间复杂度
	O(NlogK) 时间复杂度 + O(K) 额外空间
	3. 利用到了 QuickSort 中的分支方法
	O(N) 时间复杂度 / O(N^2) 最差情况下的时间复杂度 + O(1) 额外空间
	4. 对第 3 种方法进行优化，随机化输入。使得 O(N) 的时间复杂度得以保证
	O(N) 时间复杂度 + O(1) 额外空间
具体代码解析详见：
	https://leetcode.com/problems/kth-largest-element-in-an-array/discuss/

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
	    	// 将所有 小于 pivot 的数放到 pivot 的左边
			if (nums[i] < pivot) {
				swap(nums, left++, i);
			}	
		}
		// 最后,交换 nums[right] (pivot) 和 nums[left]
		swap(nums, left, right);
        
        // 返还pivot点到数组里面
        return left;         
    }
    
    private void swap(int[] nums, int i, int j) {
    	int temp = nums[i];
    	nums[i] = nums[j];
    	nums[j] = temp;
    }
};