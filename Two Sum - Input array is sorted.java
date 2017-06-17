		/**
     * Given an array of integers that is already sorted in ascending order,
     *  find two numbers such that they add up to a specific target number.
     *  The function twoSum should return indices of the two numbers such that 
     *  they add up to the target, where index1 must be less than index2. 
     *  Please note that your returned answers (both index1 and index2) are not zero-based.
     */
     
public int[] twoSum(int[] nums, int target) {
    // write your code here
    int[] result = new int[2];
    int i, j;
    if (nums == null || nums.length <= 1)
        return result;
    for (i = 0; i < nums.length; i++) 		//  Complexity: O(N²)
        for (j = i+1; j < nums.length; j++) {
            if (nums[i] + nums[j] < target)
                ;
            else if (nums[i] + nums[j] ==  target) {
                result[0] = i + 1;
                result[1] = j + 1;
            } else
                break;    
        }
    return result;
}

/****************************************  The other way  ************************************/
/*
Thoughts:
Do a binary search, but do not over-complicate it:
Start, end. Check if nums[start] + nums[end] == target.
binary move it: in fact, moving the two border, 1 position at a time
Complexity: O(N)
*/
public class Solution {
    public int[] twoSum(int[] nums, int target) {
        int[] rst = new int[2];
        if (nums == null || nums.length <= 1) {
            return rst;
        }
        int start = 0;
        int end = nums.length - 1;
        while(start < end) {
            long sum = (long)(nums[start] + nums[end]);
            if (target == sum) {
                rst[0] = start + 1;
                rst[1] = end + 1;
                break;
            } else if (target > sum) {
                start++;
            } else {
                end--;
            }
        }//END while
        return rst;
    }
}