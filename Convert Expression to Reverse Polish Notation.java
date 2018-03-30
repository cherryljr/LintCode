/*
Description
Given an expression string array, return the Reverse Polish notation of this expression. (remove the parentheses)

Example
For the expression [3 - 4 + 5] (which denote by ["3", "-", "4", "+", "5"]),
 return [3 4 - 5 +] (which denote by ["3", "4", "-", "5", "+"])

Tags
LintCode Copyright Stack
 */

/**
 * Approach: Using Stack
 * Infix to Postfix Algorithm
 *  1. Scan the infix expression from left to right.
 *  2. If the scanned character is an operand, output it.
 *  3. Else,
 *      …..3.1 If the precedence of the scanned operator is greater than the precedence of the operator in the stack
 *      (or the stack is empty), push it.
 *      …..3.2 Else, Pop the operator from the stack until the precedence of the scanned operator is
 *      less-equal to the precedence of the operator residing on the top of the stack.
 *      Push the scanned operator to the stack.
 *  4. If the scanned character is an ‘(‘, push it to the stack.
 *  5. If the scanned character is an ‘)’, pop and output from the stack until an ‘(‘ is encountered.
 *  6. Repeat steps 2-6 until infix expression is scanned.
 *  7. Pop and output from the stack until it is not empty.
 *
 * Note：
 *  此类题目，对于输入数据的处理也是问题之一。通常我们会遇到两种输入：
 *  1. 输入的是一个 表达式 String,每个字符使用 空格 分割。（有明确的输入格式）
 *  对于这种情况，我们通常使用 str.trim().split(" ") 来获得 String[] 的数据，然后依据本题的方式进行处理。
 *  例子为 LintCode 上的：
 *
 *  2. 输入的表达式每个字符是相邻的，其之间并没有分隔符，或者是可能有又或者没有。（输入格式不明确）
 *  对于这种情况，处理起来就会棘手一些了，通常我们会利用 str.toCharArray() 将其转换成 char[] 格式的数据，
 *  然后 逐个字符处理 并自己处理 字符串=>整数 的转换。
 *  （至于如何将 输入不规范的表达式 转换成 String[] 可以参见 convertToArray(String exp) 函数）
 *  这个函数只是告诉大家应该如何自己处理 整数字符串，并不建议使用该函数，因为就算输入字符串没有明确规范，
 *  我们也能直接将其转换成需要的 Postfix. 可以参考：
 *  简单计算器 Approach 1：
 *
 *  例子为 LeetCode 上的：
 *
 *  3. 输入的不是 数字 表达式，而是 字母 表达式。
 *  这种情况是最简单的，我不管是 String[] 还是 char[] 都能很方便地处理它。因为不涉及 字符串 到 整数 的转换。
 *  也就不需要考虑许多问题了。这中情况下，我们通常会转换成 char[] 然后 逐字符处理，
 *  因为字符数组在 空间 和 访问速度 上都快上不少，而且代码写起来也很简单。
 *  例子为 GeeksforGeeks 上的：
 *  https://www.geeksforgeeks.org/stack-set-2-infix-to-postfix/
 *
 * References:
 * https://www.youtube.com/watch?v=vq-nUF0G4fI
 */
public class Solution {
    /**
     * @param expression: A string array
     * @return: The Reverse Polish notation of this expression
     */
    public List<String> convertToRPN(String[] expression) {
        if (expression == null) {
            return new ArrayList<>();
        }

        List<String> rst = new ArrayList<>();
        infixToPostfix(expression, rst);

        return rst;
    }

    /**
     * A utility function to return precedence of a given operator
     * Higher returned value means higher precedence
     */
    private int getPrecedence(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            case "^":
                return 3;
            default:
                return-1;
        }
    }

    /**
     * The main method that converts given infix expression to postfix expression.
     */
    private void infixToPostfix(String[] expression, List<String> rst) {
        // Initializing empty stack
        Stack<String> stack = new Stack<>();

        for (String s : expression) {
            if (Character.isLetterOrDigit(s.charAt(0))) {
                // If the scanned character is an operand, add it to output.
                rst.add(s);
            } else if (s.equals("(")) {
                // If the scanned character is an '(', push it to the stack.
                stack.push(s);
            } else if (s.equals(")")) {
                //  If the scanned character is an ')', pop and output from the stack until an '(' is encountered.
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    rst.add(stack.pop());
                }
                // invalid expression
                if (stack.isEmpty() || !stack.peek().equals("(")) {
                    // return "Invalid Expression";
                    return;
                } else {
                    stack.pop();
                }
            } else {
                // an operator is encountered
                int precedence = getPrecedence(s);
                while (!stack.isEmpty() && precedence <= getPrecedence(stack.peek())) {
                    rst.add(stack.pop());
                }
                stack.push(s);
            }
        }
        // pop all the operators from the stack
        while (!stack.isEmpty()) {
            rst.add(stack.pop());
        }
    }

    /**
     * Convert the input String expression into String[]
     */
    private String[] convertToArray(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }

        char[] chars = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isDigit(chars[i])) {
                // 将 整数字符串 分割出来
                while (i < chars.length && Character.isDigit(chars[i])) {
                    sb.append(chars[i++]);
                }
                // 记得将 i 回滚一个位置
                i--;
                sb.append(" ");
            } else if (!Character.isDigit(chars[i]) && chars[i] != ' ') {
                sb.append(chars[i] + " ");
            }
        }

        return sb.toString().trim().split(" ");
    }
}