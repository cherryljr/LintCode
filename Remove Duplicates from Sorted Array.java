Remove Duplicate from Array 不同于remove from linked list.

LinkedList里面我们是最好不要动node.val的，直接把node去掉。
而array我们很难直接把node去掉，又不能用新array，那么就要：

把不重复的element一个个放到最前面。

* 有个反向思维：remove duplicate,实际上也是找unique elements, and insert into original array

```
/*31% Accepted
Given a sorted array, remove the duplicates in place such that each element appear only once and return the new length.

Do not allocate extra space for another array, you must do this in place with constant memory.

For example,
Given input array A = [1,1,2],

Your function should return length = 2, and A is now [1,2].

Example
Tags Expand 
Array Two Pointers

Thinking Process:
Two pointers, i, j
i: the regular for loop
j - jumper:
If nums[i] == nums[j], do not update nums[j]. It stays the same.
after i++, compare nums[j] with the new nums[i]. If not the same, means the position after j can have a new number that’s not duplicate of nums[j]. In this case, we update nums[j] = nums[i].
Do this until regular i runs out.
At the end, j is actually the last index of new Array. j + 1 is the size.
*/

public class Solution {
    public int removeDuplicates(int[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }
        
        int size = 0;
        for (int i = 0; i < A.length; i++) {
            if (A[i] != A[size]) {
                A[++size] = A[i];
            }
        }
        return size + 1;
    }
}