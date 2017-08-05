栈这种数据结构是 先进后出 的，而队列则是 先进先出 的.
对于Stack来说，我们将数据存入一个栈之后，再取出来它的顺序是倒过来的。
那么我们便可以想到，是否可以通过两个栈来实现 Queue 这个数据结构：
 将从第一个栈的数据全部取出来然后再次push到第二个栈里，
 实现再一次翻转顺序，这样我们取出来的数据便是顺序的了。也就是队列的 FIFO.

/*
Description
As the title described, you should only use two stacks to implement a queue's actions.
The queue should support push(element), pop() and top() where pop is pop the first(a.k.a front) element in the queue.
Both pop and top methods should return the value of first element.

Example
For push(1), pop(), push(2), push(3), top(), pop(), you should return 1, 2 and 2

Challenge
implement it by two stacks, do not use any other data structure and push, pop and top should be O(1) by AVERAGE.

Tags Expand 
LintCode Copyright Stack Queue
*/

public class MyQueue {
    private Stack<Integer> stack1;
    private Stack<Integer> stack2;

    public MyQueue() {
       // do initialization if necessary
       stack1 = new Stack<Integer>();
       stack2 = new Stack<Integer>();
    }
    
    public void push(int element) {
        stack1.push(element);
    }

    public int pop() {
    	// 记得需要判断stack2是否为空
    	// 将stack中的所有数据pop出来，然后push入stack2中，实现顺序的翻转
        if (stack2.empty()) {
            while (!stack1.empty()) {
                stack2.push(stack1.pop());
            }
        }
        
        return stack2.pop();
    }

    public int top() {
    	// 记得需要判断stack2是否为空
    	// 将stack中的所有数据pop出来，然后push入stack2中，实现顺序的翻转
        if (stack2.empty()) {
            while (!stack1.empty()) {
                stack2.push(stack1.pop());
            }
        }
        
        return stack2.peek();
    }
}