QuickSelet Algorithm
快速排序的思想，整个程序其实就是快速排序过程中的一次排序罢了。

利用两个指针left和right分别指向Array的起始和末尾。
利用while循环，left指针从左向右遍历，直到left指向的节点的值大于k.
同理right指针从右向左遍历，直到right指向的节点的值小于k.
交换left和right节点，直达left与right两个节点相遇或者相交。

算法复杂度为：O(N)

/*
Given an array nums of integers and an int k, partition the array (i.e move the elements in "nums") such that:

All elements < k are moved to the left
All elements >= k are moved to the right
Return the partitioning index, i.e the first index i nums[i] >= k.

Example
If nums=[3,2,2,1] and k=2, a valid answer is 1.

Note
You should do really partition in array nums instead of just counting the numbers of integers smaller than k.

If all elements in nums are smaller than k, then return nums.length

Challenge
Can you partition the array in-place and in O(n)?

Tags Expand 
Two Pointers Sort Array

Thoughts:
Two pointer sort, still similar to quick sort.
Move small to left and large to right.
Until the two pinter meets

*/

public class Solution {
	/** 
     *@param nums: The integer array you should partition
     *@param k: As description
     *return: The index after partition
     */
    public int partitionArray(int[] nums, int k) {
	    //write your code here
	    if (nums == null || nums.length == 0) {
	        return 0;
	    }
	    
	    int left = 0;
	    int right = nums.length - 1;
	    
	    while (left <= right) {
	        while (left <= right && nums[left] < k) {
	            left++;
	        }
	        while (right >= left && nums[right] >= k) {
	            right--;
	        }
	        
	        if (left <= right) {
	            int temp = nums[left];
	            nums[left] = nums[right];
	            nums[right] = temp;
	            left++;
	            right--;
	        }
	    }
	    
	    return left;
    }
}
