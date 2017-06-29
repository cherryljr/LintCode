/*
	Keypoints:
		1. start + 1 < end;  当两个指针相邻或者相交时退出while循环
		2. start = start + (end - start) / 2;
		3. nums[mid] ==, <, > 三种情况如何处理
		4. nums[start], nums[end] ? target.
*/

public class Solution {
    public int bianrySearch(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        
        int start = 0;
        int end = nums.length - 1;
        
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            //  	为什么不写成 mid = (start + end) / 2;
            //    防止溢出，当start与end的值都是接近Integer.MAX_VALUE时，将会发生溢出现象
            if (nums[mid] == target) {
                end = mid;
                //  if you want to find the last position, change it to :
                //	start = mid;
            } else if (nums[mid] < target) {
                start = mid;
            } else if (nums[mid] > target) {
                end = target;
            }
        }
        
        //	find the first position when target occurs
        //  if you want to find the end position
        //  change the order of the two if :
        /*
        if (nums[end] == target) {
            return end;
        }
        if (nums[start] == target) {
            return start;
        } 
        */
        if (nums[start] == target) {
            return start;
        } 
        if (nums[end] == target) {
            return end;
        }

        return -1;
    }
}