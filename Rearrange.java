/*
Description
Given an array required to be rearranged, 
which means all the numbers on the even-numbered bits are less than those on the odd-numbered bits.
At the same time, the even-numbered bits are sorted in ascending order, 
and the odd-numbered bits are also sorted in ascending order.

Notice
The length of the array is n，n <= 100000。
The index of the array is started from 0.

Example
Given array = [-1,0,1,-1,5,10], return [-1,1,-1,5,0,10].

Explanation:
[-1,1,-1,5,0,10] meets the requirements.
Given array = [2,0,1,-1,5,10], return [-1,2,0,5,1,10].

Explanation:
[-1,2,0,5,1,10] meets the requirements.

Tags 
Airbnb
 */

public class Solution {
    /**
     * @param nums: the num arrays
     * @return: the num arrays after rearranging
     */
    public int[] rearrange(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        Arrays.sort(nums);
        int len = nums.length;
        int[] rst = new int[len];
        int odd, even;
        if ((len & 1) == 0) {
            odd = len / 2;
            even = 0;
        } else {
            odd = len / 2 + 1;
            even = 0;
        }
        for (int i = 0; i < len; i++) {
            if ((i & 1) == 0) {
                if (even < len)
                    rst[i] = nums[even++];
            } else {
                if (odd < len)
                    rst[i] = nums[odd++];
            }
        }
        
        return rst;
    }
}