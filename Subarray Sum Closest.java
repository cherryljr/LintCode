Subarray 首先联想到的就是要使用 Prefix Sum 来解决问题。
该题是在 Subarray Sum 基础上的扩展。
思路：
	在 Subarray Sum 中，我们通过了 sum[0~a] = sum[0~b] = x 得到了 sum(a ~ b] = 0.    
	而本题需要的是与 0 最相近的。故同样借助 Prefix Sum,只不过我们需要的是将各个 Prefix Sum 
	之间的差值尽可能小地排列在一起方便我们查找。而能做到这点的便是 排序。
做法：
	1. 我们需要同时保存 Sum 与 index 两个数值，故我们新建了一个类 Pair 来存储它们。
	2. 遍历整个数组，算出每个位置上的 Sum 以及对应的 index 并保存到 Pair 中。
	3. 对所有得到的 Sum，依据 Sum 的大小进行 Sort (O(NlogN))
	4. 遍历得到的 Pair 数组，通过 sums[i].sum - sums[i-1].sum 来比较得到最接近 0 的 Subarray
	5. 最后对 index 进行一次 sort 便可以得到最后答案。

/*
Description
Given an integer array, find a subarray with sum closest to zero. Return the indexes of the first number and last number.

Example
Given [-3, 1, 1, -3, 5], return [0, 2], [1, 3], [1, 1], [2, 2] or [0, 4].

Challenge 
O(nlogn) time

Tags 
Sort Subarray
*/

class Pair {
    int sum;
    int index;
    public Pair(int s, int i) {
        sum = s;
        index = i;
    }
}
    
public class Solution {
    /**
     * @param nums: A list of integers
     * @return: A list of integers includes the index of the first number 
     *          and the index of the last number
     */
    public int[] subarraySumClosest(int[] nums) {
        int[] res = new int[2];
        if (nums == null || nums.length == 0) {
            return res;
        } 
        int len = nums.length;
        if(len == 1) {
            res[0] = res[1] = 0;
            return res;
        }
        
        Pair[] sums = new Pair[len+1];
        sums[0] = new Pair(0, 0);
        for (int i = 1; i <= len; i++) {
            sums[i] = new Pair(sums[i-1].sum + nums[i-1], i);
        }
        
        Arrays.sort(sums, new Comparator<Pair>() {
           public int compare(Pair a, Pair b) {
               return a.sum - b.sum;
           } 
        });
        
        int ans = Integer.MAX_VALUE;
        for (int i = 1; i <= len; i++) {
            if (ans > sums[i].sum - sums[i-1].sum) {
                ans = sums[i].sum - sums[i-1].sum;
                int[] temp = new int[]{sums[i].index - 1, sums[i-1].index - 1};
                Arrays.sort(temp);
                res[0] = temp[0] + 1;
                res[1] = temp[1];
            }
        }
        
        return res;
    }
}