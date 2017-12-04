The Solution is the same as Next Permutation in LeetCode:
https://github.com/cherryljr/LeetCode/blob/master/Next%20Permutation.java

/*
Description
Given a list of integers, which denote a permutation.
Find the previous permutation in ascending order.

Notice
The list may contains duplicate integers.

Example
For [1,3,2,3], the previous permutation is [1,2,3,3]

For [1,2,3,4], the previous permutation is [4,3,2,1]

Tags 
Permutation LintCode Copyright
*/

public class Solution {
    /*
     * @param nums: A list of integers
     * @return: A list of integers that's previous permuation
     */
    public List<Integer> previousPermuation(List<Integer> nums) {
        int index = nums.size() - 2;
        // find the first pair of two successive numbers nums[index] and nums[index + 1]
        // from the right, which satisfy nums[index] > [index + 1]
        while (index >= 0 && nums.get(index) <= nums.get(index + 1)) {
            index--;
        }
        
        // replace the number nums[index] with the number a[smaller] which is just smaller than itself 
        if (index >= 0) {
            int smaller = nums.size() - 1;
            while (smaller >= 0 && nums.get(index) <= nums.get(smaller)) {
                smaller--;
            }
            swap(nums, index, smaller);
        }
        // reverse the numbers following a[index] to get the next smallest lexicographic permutation
        reverse(nums, index + 1);
        
        return nums;
    }
    
    private void reverse(List<Integer> nums, int start) {
        int i = start, j = nums.size() - 1;
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }
    
    private void swap(List<Integer> nums, int i, int j) {
        int temp = nums.get(i);
        nums.set(i, nums.get(j));
        nums.set(j, temp);
    }
}