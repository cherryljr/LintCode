方法：用两个pointer, head和i.    
   		HashMap<Char, Integer>
   		head从index 0 开始。若没有重复char, 每次只有for loop的i++。每次取substring[head,i]作为最新的string.
   		一旦有重复，那么意味着，从重复的老的那个index要往后加一格开始。所以head = map.get(i) +１.


注意：head很可能被退回到很早的地方，比如abbbbbba,当遇到第二个a，head竟然变成了 head = 0+1 = 1.      
当然这是不对的，所以head要确保一直增长，不回溯。

```
/*
Given a string, find the length of the longest substring without repeating characters.

Example
For example, the longest substring without repeating letters for "abcabcbb" is "abc", which the length is 3.

For "bbbbb" the longest substring is "b", with the length of 1.

Challenge
O(n) time

Tags Expand 
String Two Pointers Hash Table

/*
    Method-2, Thoughts:
    HashMap<Char,Integer> map
    When char re-appear in map, 1. move head to repeating char's index + 1, 2. renew map with current index

    Note: head could repeat in earlier index, so make sure head does not travel back
*/

public class Solution {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        int head = 0;
        int max = 0;
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {
                if (map.get(c) + 1 > head) {
                	//	make sure head does not travel back
                    head = map.get(c) + 1;
                }
            }
            //	对比Two Sum - Input array not sorted,该语句不能被写入else中。
            //	因为Two Sum - Input array not sorted中一旦找到匹配的元素后便可结束循环不再关心后续的其他数据.
            //	但是在该程序中，我们需要遍历整个字符串才能确定结果，故每次数据都必须被插入.
            map.put(c, i);		
            String str = s.substring(head, i + 1);
            max = Math.max(max, str.length());
        }
        
        return max;
    }
}

/********************************The other way********************************************************/

public class Solution {
    /**
     * @param s: a string
     * @return: an integer 
     */
    public int lengthOfLongestSubstring(String s) {
        int[] map = new int[256]; // map from character's ASCII to its last occured index
        
        int j = 0;
        int i = 0;
        int ans = 0;
        for (i = 0; i < s.length(); i++) {
            while (j < s.length() && map[s.charAt(j)]==0) {
                map[s.charAt(j)] = 1;
                ans = Math.max(ans, j-i + 1);
                j ++;
            }
            map[s.charAt(i)] = 0;
        }
        
        return ans;
    }
}
