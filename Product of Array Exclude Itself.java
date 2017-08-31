前后遍历的方法

Solution 1: O(N^2)
	分别计算每个数字 左右两边Subarray的数值之积，然后相乘得到答案。
	该方法是使用嵌套的 for-loop 来实现的。缺点在于进行了大量的重复性计算。
	比如在计算左边前 4 个数值得积时不使用已经计算过的 前3个数值的积，却重头开始再计算一次。

Solution 2: O(N)
	首先对List进行一次 从前往后 的一次遍历。得到除去第 i 个数值的，左边 i-1 个数值的积。
	然后对List进行一次 从后往前 的一次遍历。得到出去第 i 个数值的，右边 i-1 个数字的积。
	最后将各个位置上对应的 左边数值积 乘以 右边数值积 便可以得到最终的结果。

/*
Description
Given an integers array A.
Define B[i] = A[0] * ... * A[i-1] * A[i+1] * ... * A[n-1], calculate B WITHOUT divide operation.

Example
For A = [1, 2, 3], return [6, 3, 2].

Tags 
LintCode Copyright Forward-Backward Traversal
*/

// Solution 1: O(N^2) for-loop and for-loop again
public class Solution {
    /*
     * @param nums: Given an integers array A
     * @return: A long long array B and B[i]= A[0] * ... * A[i-1] * A[i+1] * ... * A[n-1]
     */
    public List<Long> productExcludeItself(List<Integer> nums) {
        List<Long> rst = new ArrayList<Long>();
        if (nums == null || nums.size() == 0) {
            return rst;
        }
        
        for (int i = 0; i < nums.size(); i++) {
            long left = 1;
            long right = 1;
            for (int j = 0; j < i; j++) {
                left *= nums.get(j);
            }
            for (int k = nums.size() - 1; k > i; k--) {
                right *= nums.get(k);
            }
            rst.add(left * right);
        }
        
        return rst;
    }
}

// Solution 2: O(N) Time without extra space
public class Solution {
    /*
     * @param nums: Given an integers array A
     * @return: A long long array B and B[i]= A[0] * ... * A[i-1] * A[i+1] * ... * A[n-1]
     */
    public List<Long> productExcludeItself(List<Integer> nums) {
        ArrayList<Long> rst = new ArrayList<Long>();
        if (nums == null || nums.size() == 0) {
            return rst;
        }
        // Backword Traverse
        rst.add(1l);
        for (int i = 1; i < nums.size(); i++) {
            rst.add(rst.get(i - 1) * nums.get(i - 1));
        }
        // Forward Traverses
        int right = 1;
        for (int i = nums.size() - 1; i >= 0; i--) {
            rst.set(i, rst.get(i) * right);
            right *= nums.get(i);
        }
        
        return rst;
    }
}

