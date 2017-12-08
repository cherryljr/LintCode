方法1: 回溯法（DFS）
    该方法与 Permutations 中的 Approach 1 类似。
    Mark visited. 并且要检查上一层recursive时有没有略过重复element. 因此需要排序，通过permutation规律查看是否排出了重复结果。

    背景1:
    在recursive call里面有for loop, 每次从 i=0 开始, 试着在当下list上加上nums里面的每一个。    
    从 i=0 开始，所以会依次recursive每一个nums, 因此，i=2,肯定比 i=3 先被访问。
    也就是:取 i=2 的那个list permutation肯定先排出来。   

    背景2:
    重复的例子：给出Input[x, y1, y2], 假设y的值是一样的。那么，{x,y1,y2}和{x,y2,y1}是相同结果。

    综上，y1肯定比y2先被访问,{x,y1,y2}先出。 紧随其后，在另一个recursive循环里，{x,y2...}y2被先访问，跳过了y1。    
    重点:规律在此，如果跳过y1，也就是visited[y1] == false, 而num[y2] == num[y1]，那么这就是一个重复的结果，没必要做，越过。

结论:那么，我们需要input像{x,y1,y2}这样数值放一起，那么必须排序。

方法2:
    一个办法就是给一个visited queue。 和queue在所有的地方一同populate. 然后visited里面存得时visited indexes。 
    (Not efficient code. check again)

/*
Given a list of numbers with duplicate number in it. Find all unique permutations.

Example
For numbers [1,2,2] the unique permutations are:

[

    [1,2,2],

    [2,1,2],

    [2,2,1]

]

Challenge
Do it without recursion.

Tags Expand 
LinkedIn Recursion

*/

/*
    Approach 1: Backtracking
    Main idea is the same as Subsets II.
    https://github.com/cherryljr/LintCode/blob/master/Subsets%20II.java
    
    Use visited[i] to mark visited elements.
    So we can make sure the same charactor at same postion won't be reused. 
    Do a backtrack on the dfs, to make sure a element has same chance of 'selectd' or 'non-solectd'
*/
class Solution {
    /**
     * @param nums: A list of integers.
     * @return: A list of unique permutations.
     */
    public List<List<Integer>> permuteUnique(int[] nums) {
    
        ArrayList<List<Integer>> results = new ArrayList<List<Integer>>();
    
        if (nums == null) {
            return results;
        }
    		//	注意：nums == null与nums.length == 0是不同的
    		//	虽然大部分情况下他们的处理方式是相同的，但是在本题中是不一样的
        if(nums.length == 0) {
            results.add(new ArrayList<Integer>());
            return results;
        }

        Arrays.sort(nums);
        ArrayList<Integer> list = new ArrayList<Integer>();
        boolean[] visited = new boolean[nums.length];
     
        helper(results, list, visited, nums);    
        return results;
    }
    
    
    public void helper(ArrayList<List<Integer>> results, 
                   ArrayList<Integer> list, boolean[] visited, int[] nums) {
        
        if(list.size() == nums.length) {
            results.add(new ArrayList<Integer>(list));
            return;
        }
        
        for(int i = 0; i < nums.length; i++) {
            if (visited[i] || ( i != 0 && nums[i] == nums[i - 1]
            && !visited[i-1])){
                continue;
            }
            /*
            上面的判断主要是为了去除重复元素影响。
            比如，给出一个排好序的数组，[1,2,2]，那么第一个2和第二2如果在结果中互换位置，
            我们也认为是同一种方案，所以我们强制要求相同的数字，原来排在前面的，在结果
            当中也应该排在前面，这样就保证了唯一性。所以当前面的2还没有使用的时候，就
            不应该让后面的2使用。
            */
            list.add(nums[i]);
            visited[i] = true;
            helper(results, list, visited, nums);
            list.remove(list.size() - 1);
            visited[i] = false;
        }
     } 
}

/*
    Approach 2: Using Queue
    Same as Permutation I, but check also check for duplicate, if duplicate, continue
    Use a queue of marker to track if that index has been visited.
*/
class Solution {
    /**
     * @param nums: A list of integers.
     * @return: A list of unique permutations.
     */
    public ArrayList<ArrayList<Integer>> permuteUnique(ArrayList<Integer> nums) {
        ArrayList<ArrayList<Integer>> rst = new ArrayList<ArrayList<Integer>>();
       
        if (nums == null || nums.size() == 0) {
            return rst;
        }
       
        Queue<ArrayList<Integer>> queue = new LinkedList<ArrayList<Integer>>();
        Queue<ArrayList<Integer>> visited = new LinkedList<ArrayList<Integer>>();
        ArrayList<Integer> list;
        ArrayList<Integer> mark;
        
        for (int i = 0; i < nums.size(); i++) {
            list = new ArrayList<Integer>();
            list.add(nums.get(i));
            mark = new ArrayList<Integer>();
            mark.add(i);
            queue.offer(new ArrayList<Integer>(list));
            visited.offer(new ArrayList<Integer>(mark));
        }

        while (!queue.isEmpty()) {
            list = queue.poll();
            mark = visited.poll();
            if (list.size() == nums.size()) {
                if (!rst.contains(list)) {
                    rst.add(new ArrayList<Integer>(list));
                }
                continue;
            }
            for (int i = 0; i < nums.size(); i++) {
                if (!mark.contains(i)) {
                    list.add(nums.get(i));
                    mark.add(i);
                    queue.offer(new ArrayList<Integer>(list));
                    visited.offer(new ArrayList<Integer>(mark));
                    list.remove(list.size() - 1);
                    mark.remove(mark.size() - 1);
                }
            }
        }

        return rst;
    }
}