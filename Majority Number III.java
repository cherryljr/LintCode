与 Majority Number 核心思想相同，将 k 个不同的数进行 抵消。
但是该题不能像 Majority Number II 一样使用 candidateA, candidateB... 来储存
因为这样代码书写过于复杂。故我们使用了 HashMap 这个数据结构来帮助我们实现这个算法。
 1. 首先遍历整个List,将元素添加到 HashMap 中。其中 key 为元素值，value 为出现的次数
 2. 当 Map 的大小大于等于 k 时，我们便 remove 这个 k 个不同的数。（抵消）
 3. 最后重新计算 Map 中各个值在List中出现的次数，并取出那个次数最大的，便是我们需要的结果

/*
Description
Given an array of integers and a number k, the majority number is the number that occurs more than 1/k of the size of the array.
Find it.

Notice
There is only one majority number in the array.

Example
Given [3,1,2,3,2,3,3,4,4,4] and k=3, return 3.

Challenge 
O(n) time and O(k) extra space

Tags 
LintCode Copyright Hash Table Linked List
*/

public class Solution {
    /**
     * @param nums: A list of integers
     * @param k: As described
     * @return: The majority number
     */
    public int majorityNumber(ArrayList<Integer> nums, int k) {
        // count at most k keys.
        HashMap<Integer, Integer> counters = new HashMap<Integer, Integer>();
        for (Integer i : nums) {
            if (!counters.containsKey(i)) {
                counters.put(i, 1);
            } else {
                counters.put(i, counters.get(i) + 1);
            }
            
            if (counters.size() >= k) {
                removeKey(counters);
            }
        }
        
        // corner case
        if (counters.size() == 0) {
            return Integer.MIN_VALUE;
        }
        
        // recalculate counters
        for (Integer i : counters.keySet()) {
            counters.put(i, 0);
        }
        for (Integer i : nums) {
            if (counters.containsKey(i)) {
                counters.put(i, counters.get(i) + 1);
            }
        }
        
        // find the max key
        int maxCounter = 0, maxKey = 0;
        for (Integer i : counters.keySet()) {
            if (counters.get(i) > maxCounter) {
                maxCounter = counters.get(i);
                maxKey = i;
            }
        }
        
        return maxKey;
    }
    
    private void removeKey(HashMap<Integer, Integer> counters) {
        Set<Integer> keySet = counters.keySet();
        List<Integer> removeList = new ArrayList<>();
        for (Integer key : keySet) {
            counters.put(key, counters.get(key) - 1);
            if (counters.get(key) == 0) {
                removeList.add(key);
            }
        }
        for (Integer key : removeList) {
            counters.remove(key);
        }
    }
}