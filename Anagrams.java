/*
Given an array of strings, return all groups of strings that are anagrams.

Example
Given ["lint", "intl", "inlt", "code"], return ["lint", "inlt", "intl"].

Given ["ab", "ba", "cd", "dc", "e"], return ["ab", "ba", "cd", "dc"].

Note
All inputs will be in lower-case

Tags Expand
String Hash Table
*/


/**
 * Approach 1: HashMap and Sorting the String
 * Algorithm
 * Two strings are anagrams if and only if their sorted strings are equal.
 * So we can maintain a map ans : {String -> List<String>} where each key K is a sorted string,
 * and each value is the list of strings from the initial input that when sorted, are equal to K.
 *
 * Complexity Analysis
 * Time Complexity: O(NKlog(K))
 * where N is the length of strs, and K is the maximum length of a string in strs.
 * The outer loop has complexity O(N) as we iterate through each string.
 * Then, we sort each string in O(KlogK) time.
 * Space Complexity: O(N∗K), the total information content stored in ans.
 */
public class Solution {
    /*
     * @param strs: A list of strings
     * @return: A list of strings
     */
    public List<String> anagrams(String[] strs) {
        List<String> rst = new ArrayList<String>();
        if (strs == null || strs.length == 0) {
            return rst;
        }

        Map<String, ArrayList<String>> map = new HashMap<>();
        for (String s : strs) {
            char[] arr = s.toCharArray();
            Arrays.sort(arr);
            String key = String.valueOf(arr);
            //	不能使用arr.toString()， 但是可以用Arrays.toString(arr);
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<String>());
            }
            map.get(key).add(s);
        }

        // check instance occurs >= 2
        for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
            if (entry.getValue().size() >= 2) {
                rst.addAll(entry.getValue());
            }
        }
        return rst;
    }
}

/**
 * Approach 2: HashMap and Count The Frequencies of Each Words
 * Algorithm
 * Two strings are anagrams if and only if their character counts (respective number of occurrences of each character) are the same.
 * We can transform each string s into a character count, consisting of 26 non-negative integers
 * representing the number of a's, b's, c's, etc.
 * We use these counts as the basis for our hash map.
 * The String of count array is the key, and the list<String> store the anagrams.
 * For example, abbccc will be (1, 2, 3, 0, 0, ..., 0), where there are 26 entries total.
 *
 * Complexity Analysis
 * Time Complexity: O(N∗K), where N is the length of strs, and K is the maximum length of a string in strs.
 * Counting each string is linear in the size of the string, and we count every string.
 * Space Complexity: O(N∗K), the total information content stored in ans.
 */
public class Solution {
    /*
     * @param strs: A list of strings
     * @return: A list of strings
     */
    public List<String> anagrams(String[] strs) {
        List<String> rst = new ArrayList<String>();
        if (strs == null || strs.length == 0) {
            return rst;
        }

        HashMap<String, ArrayList<String>> map = new HashMap<>();
        for (String s : strs) {
            int[] arr = new int[26];
            for (int j = 0; j < s.length(); j++) {
                arr[s.charAt(j) - 'a'] += 1;
            }

            String key =  Arrays.toString(arr);
            //	不能使用String.valueOf(arr) / arr.toString();
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(s);
        }

        // check instance occurs >= 2
        for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
            if (entry.getValue().size() >= 2)
                rst.addAll(entry.getValue());
        }

        return rst;
    }
}