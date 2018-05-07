/*
Given a list of numbers, return all possible permutations.

Example
For nums [1,2,3], the permutaions are: 

[

    [1,2,3],

    [1,3,2],

    [2,1,3],

    [2,3,1],

    [3,1,2],

    [3,2,1]

]

Challenge
Do it without recursion

Tags Expand 
Recursion Search
*/

/*
    Approach 1: Backtracking
    Main idea is the same as Subset.
    https://github.com/cherryljr/LintCode/blob/master/Subset.java
    But the results needn't be in non-descending order remove duplicate elements, 
    so we don't need to sort the array.
    
    1. Very similar idea: choose or not choose (1 / 0)
        A key point is: when jumpped into next level of recursion, the 'list' will surely be filled up until it reach the max length.
        That is: when 'not choose', the empty seat will be filled eventually with points not existed in 'list'.
    2. The recursion does not end before the list is filled.
    3. A for loop is doiong the filling of blank. Any order/combination will occur.
*/
class Solution {
    /**
     * @param nums: A list of integers.
     * @return: A list of permutations.
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> rst = new ArrayList<>();
        if (nums == null) {
            return null;
        }
        
        if (nums.length == 0) {
            rst.add(new ArrayList<Integer>());
            return rst;
        }
        
        helper(rst, new ArrayList<Integer>(), nums);
        
        return rst;
    }
    
    private void helper(List<List<Integer>> rst,
                        List<Integer> list, 
                        int[] nums) {
        // results.add(subset); 将符合条件的答案加入到results中，并return
        if (list.size() == nums.length) {
            rst.add(new ArrayList<Integer>(list));
            // 此处可以直接return来节省时间
            return;
        }
        
        for (int i = 0; i < nums.length; i++) {
            // 该题为求 permutaions，故每次都是从 0 开始进行 dfs
            // 所以当遇到已经加入(contains)的元素时需要跳过。
            // 这里要和 求subsets 区分开。（把过程当作一棵树的遍历，便可以理解）
            if (list.contains(nums[i]))  {
                continue;
            }
            list.add(nums[i]);
            helper(rst, list, nums);
            list.remove(list.size() - 1);
        }
    }
}

/*
    Approach 2: Regular Recursive
    Swaps the current element with every other element in the array, lying towards its right, 
    so as to generate a new ordering of the array elements. 
    
*/
public class Solution {
    /*
     * @param nums: A list of integers.
     * @return: A list of permutations.
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> rst = new ArrayList<>();
        if (nums == null) {
            return rst;
        }
        if (nums.length == 0) {
            rst.add(new ArrayList<Integer>());
            return rst;
        }
        
        permute(rst, nums, 0);
        return rst;
    }
    
    private void permute(List<List<Integer>> rst, int[] nums, int start) {
        if (start == nums.length) {
            List<Integer> list = new ArrayList<>();
            for (int i : nums) {
                list.add(i);
            }
            rst.add(list);
            return;
        }
        // 我们可以只将数两两交换，这样便能够枚举出所有的排列组合情况。
        // 不过交换时只能跟自己后面的交换
        for (int i = start; i < nums.length; i++) {
            swap(nums, start, i);
            permute(rst, nums, start + 1);
            swap(nums, start, i);   // Backtracking
        }
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}


/*
    Approach 3: Using Stack 
    pass list, rst, nums.
    when list.size() == nums.size(), add to rst and return.
    Need to re-add all of those non-added spots. So do for loop everytime to try all possible ways.
    note: check if !list.contains(candiate).
*/

// Non-Recursion
class Solution {
    /**
     * @param nums: A list of integers.
     * @return: A list of permutations.
     */
    public List<List<Integer>> permute(int[] nums) {
        ArrayList<List<Integer>> permutations
             = new ArrayList<List<Integer>>();
        if (nums == null) {
            
            return permutations;
        }

        if (nums.length == 0) {
            permutations.add(new ArrayList<Integer>());
            return permutations;
        }
        
        int n = nums.length;
        ArrayList<Integer> stack = new ArrayList<Integer>();
        
        stack.add(-1);
        while (stack.size() != 0) {
            Integer last = stack.get(stack.size() - 1);
            stack.remove(stack.size() - 1);
            
            // increase the last number
            int next = -1;
            for (int i = last + 1; i < n; i++) {
                if (!stack.contains(i)) {
                    next = i;
                    break;
                }
            }
            if (next == -1) {
                continue;
            }
            
            // generate the next permutation
            stack.add(next);
            for (int i = 0; i < n; i++) {
                if (!stack.contains(i)) {
                    stack.add(i);
                }
            }
            
            // copy to permutations set
            ArrayList<Integer> permutation = new ArrayList<Integer>();
            for (int i = 0; i < n; i++) {
                permutation.add(nums[stack.get(i)]);
            }
            permutations.add(permutation);
        }
        
        return permutations;
    }
}
