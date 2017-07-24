思路：
	首先第一种解法，也是最暴力的解法： DFS
		先对numbers进行排序（为了去重）,然后使用DFS对该数组的subset进行遍历，
	然后将符合条件的添加到results中，但是该方法时间复杂度过高（2^n）,
	故我们需要考虑更优解。
	
	第二种解法：	Two Pointers
		依旧相对numbers进行排序（去重所需）,然后使用头尾两个指针从数组头尾分别
	向中间移动。寻找两个数nums[left], nums[rigth].使其加上原来保存的target值为0
	这样我们便得到了一组解。
		
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

//	Version 1: Recursive Search DFS

public class Solution {
    /**
     * @param numbers : Give an array numbers of n integer
     * @return : Find all unique triplets in the array which gives the sum of zero.
     */
    public ArrayList<ArrayList<Integer>> threeSum(int[] numbers) {
        // write your code here
        ArrayList<ArrayList<Integer>> results = new ArrayList<>();
        if (numbers == null) {
            return results;
        }
        
        Arrays.sort(numbers);
        helper(results, new ArrayList<Integer>(), 0, 0, numbers);
        return results;
    }
    
    private void helper(ArrayList<ArrayList<Integer>> results,
                        ArrayList<Integer> list, 
                        int startIndex,
                        int target,
                        int[] numbers) {
        if (list.size() == 3 && target == 0) {
            results.add(new ArrayList<Integer>(list));
            return;
        }
        
        for (int i = startIndex; i < numbers.length; i++) {
            if (target > 0) {
                break;
            }
            if (i > 0 && i != startIndex && numbers[i - 1] == numbers[i]) {
                continue;
            }
            
            list.add(numbers[i]);
            helper(results, list, i + 1, target + numbers[i], numbers);
            list.remove(list.size() - 1);
        }
    }
}


//	Version 2: Two Pointers

public class Solution {
    /**
     * @param nums : Give an array numbers of n integer
     * @return : Find all unique triplets in the array which gives the sum of zero.
     */
    public ArrayList<ArrayList<Integer>> threeSum(int[] nums) {
        ArrayList<ArrayList<Integer>> results = new ArrayList<>();
        
        if (nums == null || nums.length < 3) {
            return results;
        }
        
        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 2; i++) {
            // skip duplicate triples with the same first numebr
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int left = i + 1, right = nums.length - 1;
            int target = -nums[i];
            
            twoSum(nums, left, right, target, results);
        }
        
        return results;
    }
    
    public void twoSum(int[] nums,
                       int left,
                       int right,
                       int target,
                       ArrayList<ArrayList<Integer>> results) {
        while (left < right) {
            if (nums[left] + nums[right] == target) {
                ArrayList<Integer> triple = new ArrayList<>();
                triple.add(-target);
                triple.add(nums[left]);
                triple.add(nums[right]);
                results.add(triple);
                
                left++;
                right--;
                // skip duplicate pairs with the same left
                while (left < right && nums[left] == nums[left - 1]) {
                    left++;
                }
                // skip duplicate pairs with the same right
                while (left < right && nums[right] == nums[right + 1]) {
                    right--;
                }
            } else if (nums[left] + nums[right] < target) {
                left++;
            } else {
                right--;
            }
        }
    }
}