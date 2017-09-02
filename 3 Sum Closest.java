Two Pointers   O(N^2)
该题与 3 Sum 的关系 与 (Subarray Sum 和 Subarray Sum Closest) 这两题的关系升级思路相同。
因此：	
	我们做如下考虑, 3 Sum 是遍历整个数组，然后将问题转换为 2 Sum 来解决的。
	所以本题我们可以同样将问题转换为 2 Sum Cloeset 来解决。
做法：
	1. 首先对数组按 从小到大 的顺序进行排序
	2. 然后使用 3 个指针分别指向 当前元素nums[i], nums[j] 和 nums[k].
	nums[0] nums[1] nums[2] ... nums[i] .... nums[j] ... nums[k] ... nums[n-2] nums[n-1]
	因为排序过了，所以 nums[i] <= nums[j] <= nums[k],  记 	sum = nums[i] + nums[j] + nums[k]
  3. 遍历整个数组，我们需要寻找 ans = min(Math.abs(target - sum))
  当 sum < target 时，意味着我们需要添加一个更大的元素进来，故 j++
  当 sum > target 时，意味着我们需要添加一个更小的元素进来，故 k--

/*
Given an array S of n integers, are there elements a, b, c in S such that a + b + c = 0? 
Find all unique triplets in the array which gives the sum of zero.

Example
For example, given array S = {-1 0 1 2 -1 -4}, A solution set is:

(-1, 0, 1)
(-1, -1, 2)
Note
Elements in a triplet (a,b,c) must be in non-descending order. (ie, a ≤ b ≤ c)

The solution set must not contain duplicate triplets.

Tags Expand 
Two Pointers Sort Array Facebook
*/

public class Solution {
    /*
     * @param numbers: Give an array numbers of n integer
     * @param target: An integer
     * @return: return the sum of the three integers, the sum closest target.
     */
    public int threeSumClosest(int[] numbers, int target) {
        if (numbers == null || numbers.length < 3) {
            return -1;
        }

        Arrays.sort(numbers);
        
        int len = numbers.length;
        int ans = numbers[0] + numbers[1] + numbers[len-1];
        for (int i = 0; i < len - 2; i++) {
            int start = i + 1;
            int end = len - 1;
            while (start < end) {
                int sum = numbers[i] + numbers[start] + numbers[end];
                if (Math.abs(ans - target) > Math.abs(sum - target)) {
                    ans = sum;
                    if (ans == target) {
                        return ans;
                    }
                }
                if (sum > target) {
                    end--;
                } else {
                    start++;
                }
            }
        }
        
        return ans;
    }
}