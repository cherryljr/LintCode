Recursive： 取，或者不取。    


```
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

Thinking Process:
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
        // write your code here
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
        //  results.add(subset); 将符合条件的答案加入到results中，并return
        if (list.size() == nums.length) {
            rst.add(new ArrayList<Integer>(list));
            //	记得需要return
            return;
        }
        
        for (int i = 0; i < nums.length; i++) {
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
    recursive: 
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
