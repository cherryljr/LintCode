/*
Description
Print numbers from 1 to the largest number with N digits by recursion.

Notice
It's pretty easy to do recursion like:
recursion(i) {
    if i > largest number:
        return
    results.add(i)
    recursion(i + 1)
}
however this cost a lot of recursion memory as the recursion depth maybe very large. Can you do it in another way to recursive with at most N depth?

Example
Given N = 1, return [1,2,3,4,5,6,7,8,9].

Given N = 2, return [1,2,3,4,5,6,7,8,9,10,11,12,...,99].

Challenge 
Do it in recursion, not for-loop.

Tags 
Recursion
*/

public class Solution {
    /**
     * @param n: An integer.
     * return : An array storing 1 to the largest number with n digits.
     */
    public List<Integer> numbersByRecursion(int n) {
        ArrayList<Integer> res = new ArrayList<>();
        num(n, 0, res);
        return res;
    }
    
    private void num(int n, int ans,ArrayList<Integer> res) {
        if (n == 0) {
            if (ans > 0) {
                res.add(ans);
            }
            return;
        }
        
        for (int i = 0; i <= 9; i++) {
            num(n - 1, ans * 10 + i, res);
        }
    }
}