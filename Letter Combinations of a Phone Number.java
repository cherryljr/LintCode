Recursive Search / DFS
排列组合问题，采用递归查找的方法。
该题需要创建一张map来存储电话上每个按键所对应的英文字母。
每次遍历的结果存储在一个StringBuilder()里面.当其长度与digit相等时则加入到result中。
然后用for循环遍历map中对应的字符即可。
递归, 回溯

同时参考：
http://www.jiuzhang.com/solution/letter-combinations-of-a-phone-number

还存在着另外一种方法，可以使用Arrays来替代map来存储字符与数字按键的对应关系。
这种解法需要每次传递变量index，用来记录当前遍历到第几个数字。再利用digits[index] - '0'
我们便可以得到具体的按键数字是多少，然后以此为数组下标遍历String数组中对应得值即可。

方法三只是方法二在代码上面的进一步优化，掌握方法二即可。

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

//	Version: 计状态，使用Arrays替代Map

public class Solution {
    /**
     * @param digits A digital string
     * @return all posible letter combinations
     */
    public ArrayList<String> letterCombinations(String digits) {
        // Write your code here
        ArrayList<String> result = new ArrayList<String>();
        if (digits == null || digits.length() == 0) {
            return result;
        }
        
        String[] phone = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        
        helper(result, new StringBuilder(), 0, digits, phone);
        return result;
    }
    
    private void helper(ArrayList<String> result, 
                        StringBuilder sb,
                        int x,
                        String digits,
                        String[] phone) {
        if (sb.length() == digits.length()) {
            result.add(sb.toString());
            return;
        }          
        
        int d = digits.charAt(x) - '0';
        for (char c : phone[d].toCharArray()) {
            sb.append(c);
            helper(result, sb, x + 1, digits, phone);
            //	Backtracking, 删去最后一个字符
            sb.deleteCharAt(sb.length() - 1);		
        }
    }
}

//	Version 3: 在第二个版本的基础上，对代码进一步优化

public class Solution {
    /**
     * @param digits A digital string
     * @return all posible letter combinations
     */
    
    //	将这些变量作为类的变量来声明，具有更广的作用域，使得DFS时不再需要传递这些参数。
    ArrayList<String> ans = new ArrayList<>();

    int l;
    String digits;
    //	这是一个对应的关系表，定义后作为常量使用，无需改动，故定义为static final
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