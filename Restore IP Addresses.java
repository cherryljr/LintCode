Recursive Search / DFS
排列组合问题，采用递归查找的方法。
程序基本框架与Recursive Search Template相同。
分析如下：
	1. 什么时候将遍历结果加入result中 / 什么样的遍历结果才是符合条件的？
	当startIndex == s.length()时说明已经遍历到最后一个字符，并且当list.size() == 4，即符合IP的规格时。
	说明我们得到了一个符合条件的IP。
	2. for循环的条件是什么？从哪开始。
	要求是前面取过的数字不能够再取，所以我们需要一个startIndex作为开始的标记位。并且每次递归时传入的值加1 (i + 1).
	当i的位置到了s的末尾时退出循环。同时我们要注意IP地址的特点：每段长度最多只有3，故i与startIndex的距离不得超过3. (i <= startIndex + 3>)
	3. IP地址中的每个字段符合0 ~ 255时才能加入list中。	

/*

Given a string containing only digits, restore it by returning all possible valid IP address combinations.

For example:
Given "25525511135",

return ["255.255.11.135", "255.255.111.35"]. (Order does not matter)

Hide Tags Backtracking String

*/

public class Solution {
    /**
     * @param s the IP string
     * @return All possible valid IP addresses
     */
    public ArrayList<String> restoreIpAddresses(String s) {
        // Write your code here
        ArrayList<String> result = new ArrayList<String>();
        //	当s长度不符合标准时直接返回
        if (s.length() < 4 || s.length() > 12) {
            return result;
        }
        
        helper(result, new ArrayList<String>(), s, 0);
        return result;
    }
    
    private void helper(ArrayList<String> result,
                        ArrayList<String> list, 
                        String s, 
                        int startIndex) {
        //	一个IP地址有四段，故一个符合IP地址规范的list长度为4
        if (list.size() == 4 && startIndex == s.length()) {
            StringBuffer sb = new StringBuffer();
            for (String temp : list) {
                sb.append(temp);
                sb.append('.');
            }
            sb.deleteCharAt(sb.length() - 1);
            result.add(sb.toString());
            return;
        }
        
        //	IP地址每段长度最多只有3	
        for (int i = startIndex; i < s.length() && i <= startIndex + 3; i++) {
            String temp = s.substring(startIndex, i + 1);
            if (!isValid(temp)) {
                continue;
            }
            list.add(temp);
            helper(result, list, s, i + 1);
            list.remove(list.size() - 1);
        }
    }
    
    private boolean isValid(String s) {
    		//	防止出现"00"或者"01"等类似的情况
        if (s.charAt(0) == '0') {
            return s.equals("0");
        }
        
        int digit = Integer.valueOf(s);
        return digit >= 0 && digit <= 255;
    }
}








