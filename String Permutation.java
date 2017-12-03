本题有 3 种解法。

Solution 1: O(nlogn) Sort 
	该方法直观，暴力。直接将 Sting A, B 转换为 char[], 然后调用 Arrays.sort() 进行排序即可
	然后利用 String.valueOf() 将字符数组转换回 String
	最后利用 String 的 equals 方法比较两个经过排序的字符串即可。
	但是该方法时间复杂度过高，不推荐哦~ 
	所以这边就提出这个解法，具体代码就不实现啦~ 反正八成会Time Limit Exceeded~~

Solution 2: O(n) Calculate the Sum of String
	因为在计算机中每一个字符其实都是由一个 int 数值来表示的 (ASCII 码)
	因此将 String 中每次字符相加起来若得到的 sum 相等则代表这两个字符串可以置换
	做法很简单：将 String 转换为 char[] 之后，取出字符数组中每个字符值相加即可
	缺点：
	1. String中不能包含重复数据，不然会出现误判的情况。比如 "aa" 与 "bc" 的sum值是相同的，但是并不互为permutations.
	2. 一旦涉及到运算，一定要考虑边界问题。毫无疑问在面对大量数据时，sum 将会超出 MAX_VALUE。
	但是呢~ 本题的 testcase 毫无疑问没有这么大，而且这个代码书写简单~ 嘿嘿嘿~~~
	Note：实际应用场合中还是要严谨哦， 请看 Solution 3

Solution 3: O(n) HashMap
	想到乱序情况下的判断，毫无以为是我大 HashMap 了。
	利用 HashMap 中的 key 来储存字符数值, value 来储存字符出现的相应次数。
	然后对两个 map 进行比较便能够得出最后的结果。
    
Solution 4: O(n) 主要思想与 HashMap 相同
    因为该题的 testcase 并不全面，故可以使用一个 256 大小的数组 record 来替代 HashMap.
    对于 A 字符串中的字符，每遇到一次 record[A.charAt(i)]++ ;
    对于 B 字符串中的字符，每遇到一次 record[B.charAt(i)]--;
    最后我们只需要遍历 record 数组中看是否存在 非0 的元素即可，若存在则代表存在不同的字符，return false即可.
	
/*
Description
Given two strings, write a method to decide if one is a permutation of the other.

Example
abcd is a permutation of bcad, but abbe is not a permutation of abe

Tags 
String Permutation
*/

// Solution 2: Calculate the Sum of String O(N)
public class Solution {
    /*
     * @param A: a string
     * @param B: a string
     * @return: a boolean
     */
    public boolean Permutation(String A, String B) {
        if (A == null || B == null) {
            return A == B;
        }
        if (A.length() != B.length()) {
            return false;
        }
        if (A.equlas(B)) {
            return true;
        }
        
        char[] chA = A.toCharArray();
        char[] chB = B.toCharArray();

        long sumA = 0, sumB = 0;
        for (int i = 0; i < chA.length; i++) {
            sumA += chA[i];
            sumB += chB[i];
        }
        
        return sumA == sumB;
    }
}

// Solution 3: HashMap O(N)
public class Solution {
    /*
     * @param A: a string
     * @param B: a string
     * @return: a boolean
     */
    public boolean Permutation(String A, String B) {
        if (A == null || B == null) {
            return A == B;
        }
        if (A.equals(B)) {
            return true;
        }
        if (A.length() == B.length()) {
            return mapCompare(charCount(A), charCount(B));
        }
        return false;
    }
    
    //  得到字符串各个字符的出现次数
    public Map<Character, Integer> charCount(String str) {
        Map<Character, Integer> map = new HashMap<>();
        char[] arr = str.toCharArray();
        
        for (char item : arr) {
            if (map.containsKey(item)) {
                Integer num = map.get(item);
                num += 1;
                map.put(item, num);
            } else {
                map.put(item, 1);
            }
        }
        
        return map;
    }
    
    //  比较两个map
    public boolean mapCompare(Map<Character, Integer> map1, Map<Character, Integer> map2) {
    	// 两个 map 的size 不相等则肯定也不能置换
        if (map1.size() != map2.size()) {
            return false;
        }
        for (Character c : map1.keySet()) {
            try {
                if (map1.get(c) != map2.get(c)) {
                    return false;
                }
            } catch (NullPointerException e) {
                //  说明map2中没有map1中的某个字符
                return false;
            }
        }
        return true;
    }
}

// Solution 4: Using Array
public class Solution {
    /*
     * @param A: a string
     * @param B: a string
     * @return: a boolean
     */
    public boolean Permutation(String A, String B) {
        int[] record = new int[256];
        
        for (int i = 0; i < A.length(); i++) {
            record[A.charAt(i)]++;
        }
        for (int i = 0; i < B.length(); i++) {
            record[B.charAt(i)]--;
        }
        for (int i : record) {
            if (i != 0) {
                return false;
            }
        }
        
        return true;
    }
}