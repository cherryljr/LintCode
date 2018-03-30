/*
Description
Evaluate the value of an arithmetic expression in Reverse Polish Notation.
Valid operators are +, -, *, /. Each operand may be an integer or another expression.

Example
["2", "1", "+", "3", "*"] -> ((2 + 1) * 3) -> 9
["4", "13", "5", "/", "+"] -> (4 + (13 / 5)) -> 6

Tags
Stack LinkedIn
 */

/**
 * Approach: Using Stack
 * 直接按照 逆波兰表达式（后缀表达式） 的计算方法进行计算即可。
 * 遇到运算符就将栈顶的两个元素 pop 出来进行计算，
 * （栈顶元素在后，即第一次被 pop 出来的元素在 运算符 之后）
 * 然后将计算得到的值 push 回栈里即可。
 * 最后剩下的元素就是我们的计算结果。
 */
public class Solution {
    /**
     * @param tokens: The Reverse Polish Notation
     * @return: the value
     */
    public int evalRPN(String[] tokens) {
        if (tokens == null || tokens.length == 0) {
            return 0;
        }

        Stack<Integer> stack = new Stack<>();
        for (String exp : tokens) {
            switch (exp) {
                case "+":
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-":
                    stack.push(-stack.pop() + stack.pop());
                    break;
                case "*":
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "/":
                    int x = stack.pop(), y = stack.pop();
                    stack.push(y / x);
                    break;
                default:
                    stack.push(Integer.parseInt(exp));
            }
        }

        return stack.pop();
    }
}