/*
Description
Given two non-negative integers num1 and num2 represented as strings, return the product of num1 and num2

Example
The length of both num1 and num2 is < 110.
Both num1 and num2 contains only digits 0-9.
Both num1 and num2 does not contain any leading zero.
You must not use any built-in BigInteger library or convert the inputs to integer directly.

Tags 
String Mathematics Twitter Facebook
*/

/**
 * This Question is the same as Multiply String in LeetCode
 * You can get more explanations here:
 * https://github.com/cherryljr/LeetCode/blob/master/Multiply%20Strings.java
 */
public class Solution {
    /*
     * @param num1: a non-negative integers
     * @param num2: a non-negative integers
     * @return: return product of num1 and num2
     */
    public String multiply(String num1, String num2) {
        int[] pos = new int[num1.length() + num2.length()];

        // Calculate the result of each index in the pos array.
        for (int i = num1.length() - 1; i >= 0; i--) {
            for (int j = num2.length() - 1; j >= 0; j--) {
                int p1 = i + j, p2 = i + j + 1;
                int mul = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                int sum = mul + pos[p2];
                
                pos[p1] += sum / 10;
                pos[p2] = sum % 10;
            }
        }

        // Turn the pos array into String result
        StringBuilder sb = new StringBuilder();
        for (int p : pos) {
            if (!(sb.length() == 0 && p == 0)) {
                sb.append(p);
            }
        }
        return sb.length() == 0 ? "0" : sb.toString();
    }
}