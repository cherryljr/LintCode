涉及到对数字的自由组合，于是想到了使用字符串来解决。
因为字符串不仅灵活 而且 支持大数操作。
想要得到组合后的值最小，故涉及到了 字符串 的比较。
我们需要比较的是两个字符串 concat 之后，哪个个更小。即 a+b 与 b+a 中的较小值。
故我们写一个比较器，并调用 Arrays.sort() 方法对其进行排序。时间复杂度为 O(nlogn)

注意：排序完成后的 ans 字符串开头很可能有 0, 我们必须要略过该部分才行。

/*
Description
Construct minimum number by reordering a given non-negative integer array. Arrange them such that they form the minimum number.

Notice
The result may be very large, so you need to return a string instead of an integer.

Example
Given [3, 32, 321], there are 6 possible numbers can be constructed by reordering the array:
3+32+321=332321
3+321+32=332132
32+3+321=323321
32+321+3=323213
321+3+32=321332
321+32+3=321323
So after reordering, the minimum number is 321323, and return it.

Challenge 
Do it in O(nlogn) time complexity.

Tags 
Array
*/

public class Solution {
    /**
     * @param nums n non-negative integer array
     * @return a string
     */
    public String minNumber(int[] nums) {
        if (nums == null || nums.length == 0) {
            return "";
        }
        
        String[] str = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            str[i] = String.valueOf(nums[i]);
        }
        
        // 自定义比较器：从小到大
        Arrays.sort(str, new Comparator<String>() {
           public int compare(String a, String b) {
               String ab = a.concat(b);
               String ba = b.concat(a);
               return ab.compareTo(ba);
           } 
        });
        
        String ans = "";
        for (int i = 0; i < str.length; i++) {
            ans = ans.concat(str[i]);
        }
        // 记得处理字符串以 0 开头的部分！！！
        int i = 0;
        while (i < ans.length() && ans.charAt(i) == '0') {
            i++;
        }
        if (i == ans.length()) {
            return "0";
        }
        return ans.substring(i);
    }
}