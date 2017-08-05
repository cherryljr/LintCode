双Stack：一个正常stack，另一个minStack存当前level下栈的最小值. 这样当原来的stack发生变化后(push or pop),
我们都能够立即知道当前level下，栈的最小值是多少。
注意维护minStack的变化

另外. 如果要maxStack，也是类似做法

/*
Implement a stack with min() function, which will return the smallest number in the stack.

It should support push, pop and min operation all in O(1) cost.

Example
push(1)
pop()   // return 1
push(2)
push(3)
min()   // return 2
push(1)
min()   // return 1
Note
min operation will never be called if there is no number in the stack.

Tags Expand 
Stack

Thoughts:
using 2 stacks: one regular, the other one trackes min element
MinStack (0 ~ i): for i elements in regular stack, at each ith, the min element is stored at MinStack(i). This means, there can be duplicated mins for different ith.

Note: remember to check if minStack isEmpty(), empty stack does not have peek()
*/

public class MinStack {
    private Stack<Integer> stack;
    private Stack<Integer> minStack;
    
    public MinStack() {
        // do initialize if necessary
        stack = new Stack<Integer>();
        minStack = new Stack<Integer>();
    }

    public void push(int number) {
        stack.push(number);
        if (minStack.isEmpty()) {
            minStack.push(number);
        } else {
            minStack.push(Math.min(minStack.peek(), number));
        }
    }

    public int pop() {
        minStack.pop();
        return stack.pop();
    }

    public int min() {
        return minStack.peek();
    }
}
