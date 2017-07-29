Recursive Search / DFS
排列组合问题，采用递归查找的方法。
该题需要创建一张map来存储电话上每个按键所对应的英文字母。
每次遍历的结果存储在一个StringBuilder()里面.当其长度与digit相等时则加入到result中。
然后用for循环遍历map中对应的字符即可。
递归, 回溯

同时参考：
http://www.jiuzhang.com/solution/letter-combinations-of-a-phone-number


还有其他两种解法，可以采用计状态的方式来解决问题，
方法也同样采用的是递归遍历的方法。不过更加简洁

/*
Given a digit string, return all possible letter combinations that the number could represent.

A mapping of digit to letters (just like on the telephone buttons) is given below.

Cellphone. Picture:
http://www.lintcode.com/en/problem/letter-combinations-of-a-phone-number/

Example
Given "23"

Return ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"]

Note
Although the above answer is in lexicographical order, your answer could be in any order you want.

Tags Expand 
String Backtracking Recursion Facebook Uber
*/

//	Vsersion 1: DFS

public class Solution {
    public ArrayList<String> letterCombinations(String digits) {
        ArrayList<String> result = new ArrayList<String>();

        if (digits == null || digits.equals("")) {
            return result;
        }

        Map<Character, char[]> map = new HashMap<Character, char[]>();
        map.put('0', new char[] {});
        map.put('1', new char[] {});
        map.put('2', new char[] { 'a', 'b', 'c' });
        map.put('3', new char[] { 'd', 'e', 'f' });
        map.put('4', new char[] { 'g', 'h', 'i' });
        map.put('5', new char[] { 'j', 'k', 'l' });
        map.put('6', new char[] { 'm', 'n', 'o' });
        map.put('7', new char[] { 'p', 'q', 'r', 's' });
        map.put('8', new char[] { 't', 'u', 'v'});
        map.put('9', new char[] { 'w', 'x', 'y', 'z' });

        helper(map, digits, new StringBuilder(), result);

        return result;
    }

    private void helper(Map<Character, char[]> map, String digits, 
        StringBuilder sb, ArrayList<String> result) {
        if (sb.length() == digits.length()) {
            result.add(sb.toString());
            return;
        }

        for (char c : map.get(digits.charAt(sb.length()))) {
            sb.append(c);
            helper(map, digits, sb, result);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}

//  Version 2: 计状态 

public class Solution {
    /**
     * @param digits A digital string
     * @return all posible letter combinations
     */
    ArrayList<String> ans = new ArrayList<>();

    void dfs(int x, int l, String str, String digits, String phone[]) {
        if (x == l) {
            ans.add(str);
            return;
        }
        int d = digits.charAt(x) - '0';
        for (char c : phone[d].toCharArray()) {
            dfs(x + 1, l, str + c, digits, phone);
        }
    }

    public ArrayList<String> letterCombinations(String digits) {
        // Write your code here
        String phone[] = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

        if (digits.length() == 0) {
            return ans;
        }
        dfs(0, digits.length(), "", digits, phone);
        return ans;
    }
}


//	Version 3: 计状态

public class Solution {
    /**
     * @param digits A digital string
     * @return all posible letter combinations
     */
    ArrayList<String> ans = new ArrayList<>();

    int l;
    String digits;
    static final String phone[] = {"", "", "abc", "def", "ghi", "jkl",
            "mno", "pqrs", "tuv", "wxyz"};

    void dfs(int x, String str) {
        if (x == l) {
            ans.add(str);
            return;
        }
        int d = digits.charAt(x) - '0';
        for (char c : phone[d].toCharArray()) {
            dfs(x + 1, str + c);
        }
    }

    public ArrayList<String> letterCombinations(String digits) {
        // Write your code here
        this.l = digits.length();
        this.digits = digits;

        if (digits.length() == 0) {
            return ans;
        }
        dfs(0, "");
        return ans;
    }
}