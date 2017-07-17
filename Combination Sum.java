是一个排列组合问题，首先想到可以使用Recursive Search的模板
递归，DFS.   
记得求sum时候也pass一个sum进去，backtracking一下sum，这样就不必每次都sum the list了。   
（这里Version2可以进一步优化，从而节省sum的这个空间）

但是本题存在着一个问题：
题目里面所同一个元素可以用n次，但是，同一种solution不能重复出现。如何handle?

1. 用一个index（我们这里用了startIndex）来mark每次recursive的起始点。
2. 每个recursive都从for loop里面的i开始，而i = startIndex。也就是,下一个Recursion,这个数字就会有机会被重复使用。
（相比于Template中的每次递归传入的值为：startIndex + 1，但这里不能加上1。若加上1就表示下次从下一个位置开始取，这样就会导致该数字无法被重复使用）
3. 同时，要确保解集中不能有重复的组合。这里可以直接使用Template中的方法解决。

假如[x1, x2, y, z], where x1 == x2， 上面做法的效果: 
我们可能有这样的结果: x1,x1,x1,y,z    
但是不会有:x1,x2,x2,y,z   
两个solution从数字上是一样的，也就是duplicated solution, 要杜绝第二种。

该题还有其他两种解法，其中第二种解法与第一种解法几乎相同。
第三种解法采用了首先对Arrays进行了一个去重的预处理的方法。

/*
Given a set of candidate numbers (C) and a target number (T), find all unique combinations in C where the candidate numbers sums to T.

The same repeated number may be chosen from C unlimited number of times.



For example, given candidate set 2,3,6,7 and target 7, 
A solution set is: 
[7] 
[2, 2, 3] 

Note
All numbers (including target) will be positive integers.
Elements in a combination (a1, a2, … , ak) must be in non-descending order. (ie, a1 ≤ a2 ≤ … ≤ ak).
The solution set must not contain duplicate combinations.
Example
given candidate set 2,3,6,7 and target 7, 
A solution set is: 
[7] 
[2, 2, 3] 

Tags Expand 
Backtracking Array

*/

// version 1: DFS Recursive Search
public class Solution {
    /**
     * @param candidates: A list of integers
     * @param target:An integer
     * @return: A list of lists of integers
     */
    public List<List<Integer>> combinationSum(int[] num, int target) {
        List<List<Integer>> rst = new ArrayList<List<Integer>>();

        if (num == null || num.length == 0 || target < 0) {
            return rst;
        }
        
        Arrays.sort(num);
        helper(rst, new ArrayList<Integer>(), num, target, 0, 0);
        return rst;
    }
    
    public void helper(List<List<Integer>> rst,
    		               List<Integer> list,
                	     int[] num, 
                	     int target, 
                	     int sum, 
                	     int startIndex) {
        if (sum == target) {
            rst.add(new ArrayList(list));
            return;
        } else if (sum > target) {	// Stop if greater than target
            return;
        }
        
        for (int i = startIndex; i < num.length; i++) {
            if (i > 0 && i != startIndex && num[i - 1] == num[i]) {
                continue;
            }
            
            list.add(num[i]);
            sum += num[i];
            helper(rst, list, num, target, sum, i);
            // Back track:
            list.remove(list.size() - 1);
            sum -= num[i];
            // Repeat Detection
        }
    }
}

// version 2: reuse candidates array
public class Solution {
    public  List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (candidates == null) {
            return result;
        }

        List<Integer> combination = new ArrayList<>();
        Arrays.sort(candidates);
        helper(candidates, 0, target, combination, result);

        return result;
    }

     void helper(int[] candidates,
                 int index,
                 int remainTarget,
                 List<Integer> combination,
                 List<List<Integer>> result) {
        if (remainTarget == 0) {
            result.add(new ArrayList<Integer>(combination));
            return;
        }

        for (int i = index; i < candidates.length; i++) {
            if (candidates[i] > remainTarget) {
                break;
            }

            if (i != index && candidates[i] == candidates[i - 1]) {
                continue;
            }

            combination.add(candidates[i]);
            helper(candidates, i, remainTarget - candidates[i], combination, result);
            combination.remove(combination.size() - 1);
        }
    }
}

// version 3: Remove duplicates & generate a new array
public class Solution {
    /**
     * @param candidates: A list of integers
     * @param target:An integer
     * @return: A list of lists of integers
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> results = new ArrayList<>();
        if (candidates == null || candidates.length == 0) {
            return results;
        }
        
        int[] nums = removeDuplicates(candidates);
        
        dfs(nums, 0, new ArrayList<Integer>(), target, results);
        
        return results;
    }
    
    private int[] removeDuplicates(int[] candidates) {
        Arrays.sort(candidates);
        
        int index = 0;
        for (int i = 0; i < candidates.length; i++) {
            if (candidates[i] != candidates[index]) {
                candidates[++index] = candidates[i];
            }
        }
        
        int[] nums = new int[index + 1];
        for (int i = 0; i < index + 1; i++) {
            nums[i] = candidates[i];
        }
        
        return nums;
    }
    
    private void dfs(int[] nums,
                     int startIndex,
                     List<Integer> combination,
                     int remainTarget,
                     List<List<Integer>> results) {
        if (remainTarget == 0) {
            results.add(new ArrayList<Integer>(combination));
            return;
        }
        
        for (int i = startIndex; i < nums.length; i++) {
            if (remainTarget < nums[i]) {
                break;
            }
            combination.add(nums[i]);
            dfs(nums, i, combination, remainTarget - nums[i], results);
            combination.remove(combination.size() - 1);
        }
    }
}

