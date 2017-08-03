排列组合题, 求所有的具体方案 => 递归遍历 / DFS
和Combination Sum II 几乎相同. 
 
该题不同点在于多了一个要求只能选取k个数，所以只需要在if语句里面多加上一个关于list.size() == k的判断即可
并且该题给的每个数是不相等的，故不需要进行去重操作。

/*

Description
Given n unique integers, number k (1<=k<=n) and target.

Find all possible k integers where their sum is target.

Have you met this question in a real interview? Yes
Example
Given [1,2,3,4], k = 2, target = 5. Return:

[
  [1,4],
  [2,3]
]
Tags 
LintCode Copyright Depth First Search

*/

public class Solution {
    /**
     * @param A: an integer array.
     * @param k: a positive integer (k <= length(A))
     * @param target: a integer
     * @return a list of lists of integer 
     */ 
    public ArrayList<ArrayList<Integer>> kSumII(int[] A, int k, int target) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        if (A == null || A.length == 0) {
            return result;
        }
        
        Arrays.sort(A);
        helper(result, new ArrayList<Integer>(), A, 0, k, target);
        return result;
    }
    
    private void helper(ArrayList<ArrayList<Integer>> result,
                        ArrayList<Integer> list,
                        int[] A,
                        int startIndex,
                        int k,
                        int remainTarget) {
        if (remainTarget == 0 && list.size() == k) {
            result.add(new ArrayList<Integer>(list));
            return;
        }   
        
        for (int i = startIndex; i < A.length; i++) {
            if (remainTarget < A[i]) {
                break;
            }
            list.add(A[i]);
            helper(result, list, A, i + 1, k, remainTarget - A[i]);
            // Backtracking
            list.remove(list.size() - 1);
        }
    }
}