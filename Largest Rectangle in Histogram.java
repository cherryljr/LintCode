	对于在一个数组中，找一个数 左边 / 右边 第一个比它 小 / 大 的元素，可以使用栈这个数据结构来解决。
使栈递增 / 递减. 每一次比较都是一个元素进栈或者出栈，故总共为O(N)次进栈和O(N)次出栈。
因此时间复杂度仅为O(N).是一个非常巧妙地算法，值得学习。
	利用for loop遍历数组，每当 nums[i] 小于 / 大于 栈顶元素时，则将栈顶元素 pop 出来，直到 num[i] 小于 / 大于
栈顶元素。

/*
Description
Given n non-negative integers representing the histogram's bar height where the width of each bar is 1, find the area of largest rectangle in the histogram.

histogram ：http://www.lintcode.com/en/problem/largest-rectangle-in-histogram/

Above is a histogram where width of each bar is 1, given height = [2,1,5,6,2,3].

histogram ：http://www.lintcode.com/en/problem/largest-rectangle-in-histogram/

The largest rectangle is shown in the shaded area, which has area = 10 unit.

Have you met this question in a real interview? Yes

Example
Given height = [2,1,5,6,2,3],
return 10.

Tags 
Array Stack
*/

public class Solution {
    /**
     * @param height: A list of integer
     * @return: The area of largest rectangle in the histogram
     */
    public int largestRectangleArea(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        
        Stack<Integer> stack = new Stack<Integer>();
        int maxArea = 0;
        
        for (int i = 0; i <= height.length; i++) {
            int cur = (i == height.length) ? -1 : height[i];
            // 必须先判断stack非空，然后才能进行peek()操作
            while (!stack.isEmpty() && cur < height[stack.peek()]) {
            	/* 
          		  注意 h 和 w 的取值顺序不能调换，因为pop操作会导致peek()发生变换 
          	    但是有人不禁会想，虽然会使得顺序发生改变，那我在 w 取值时少减1不就好了吗？
          	    答案仍然是错误的。因为有一步非常关键的步骤在于判断 stack 是否为空，
          	    若为空，则 i 即为长方形的宽。举个例子:[5, 4, 2, 1].
          	    当for loop循环到 2 时，如果我们先去 w，会因为 4 没有被 pop 出去使得 stack 仍然不为空
          	    所以得到的 w = 2 - 1 = 1. 这样就会使得 w 的长度少算了一个 1.
          	    故我们需要彻底理解算法，先pop得到高度，然后再来判断stack来取宽度
            	*/
                int h = height[stack.pop()];
                int w = stack.isEmpty() ? i : (i - stack.peek() - 1);
                maxArea = Math.max(maxArea, w * h);
            }
            stack.push(i);
        }
        
        return maxArea;
    }
}
