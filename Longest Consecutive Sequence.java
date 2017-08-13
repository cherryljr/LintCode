第一反应为先排序，然后查询即可。但是仅仅是排序就需要花费 O(NlogN) 的时间复杂度。
故考虑其他方法。题目要求为 O(N) 的时间复杂度，也就是说对于每一个元素的操作都是 
O(1) 的，并且给的元素是乱序的，根据这两个特点我们想到了最符合条件的 HashMap.
因为其特点就是所有操作均为 O(1), 并且没有顺序。

想到使用 HashMap 之后，我们需要考虑它的key, value应该存储什么。key毫无疑问应该就是
各个元素了，那么value呢？因为要求 O(N) 的时间复杂度，所以我们每个元素只能查询一次，
这就意味着我们需要去除那些重复的查询操作。故 value 值为 boolean, 表示当前元素是否
已经被查询过了。
  
接下来我们只需要查询 num-- 和 num++ 是否存在在 HashMap 中即可，若存在则表示连续，
计数器+1, 同时把num-- 和 num++ 的 value 置为true. 这样第二次查询时，若发现其value
为true则直接跳过该元素。最后我们返回整个过程中最大的长度即可。

/*
Given an unsorted array of integers, find the length of the longest consecutive elements sequence.

For example,
Given [100, 4, 200, 1, 3, 2],
The longest consecutive elements sequence is [1, 2, 3, 4]. Return its length: 4.

Your algorithm should run in O(n) complexity.

Hide Tags Array

Thinking process:
0. This problem can be done using sorting, but time complexity of sorting is O(nlogn). This problem requires O(n).
1. Want to check if a number's left and right is consecutive to itself, but cannot do it due to the given unsorted array: think about a Hashmap.
2. HashMap(Key, Value) = (the number itself, boolean: have been counted or not). If you count a number as a consecutive, you only need to count it once.
3. How HashMap works: 
	when checking a number's consecutive, look at number--, number++, see if they are in the HashMap. If exist, means consecutive.
	If a number exist in the hashmap and its value is 'true', then we need to skip this number beacuse it has been checked.
4. Track the total number consecutives of 1 perticular number, compare it with the maxL. Save the Math.max to maxL.
5. Depending on the problem, we can store a consecutive sequence or simply just its length: maxL. This problem wants the maxL.
*/

public class Solution {
    public int longestConsecutive(int[] num) {
        if (num == null || num.length == 0) {
            return 0;
        }
        
        int maxL = 1;
        HashMap<Integer, Boolean> history = new HashMap<Integer, Boolean>();
        
        for (int i : num) {
            history.put(i, false);
        }
        
        for (int i : num) {
        	// if the element has been checked skip it
            if (history.get(i)) {
                continue;
            }
            //check ++num
            int temp = i;
            int total = 1;
            while (history.containsKey(++temp)) {
                total++;
                history.put(temp, true);
            }
            //check --num
            temp = i;
            while (history.containsKey(--temp)) {
                total++;
                history.put(temp, true);
            }
            maxL = Math.max(maxL, total);
        }
        
        return maxL;
    }
}