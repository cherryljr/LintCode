该题是典型的 二分法 的题目。
与 Search Insert Position 几乎相同。根据该思路编写程序即可。

/*
Description
We are playing the Guess Game. The game is as follows:
I pick a number from 1 to n. You have to guess which number I picked.
Every time you guess wrong, I'll tell you whether the number is higher or lower.
You call a pre-defined API guess(int num) which returns 3 possible results (-1, 1, or 0):

Example
n = 10, I pick 4 (but you don't know)

Return 4. Correct !

Tags 
Binary Search Google
*/

/* The guess API is defined in the parent class GuessGame.
   @param num, your guess
   @return -1 if my number is lower, 1 if my number is higher, otherwise return 0
      int guess(int num); */

public class Solution extends GuessGame {
    /**
     * @param n an integer
     * @return the number you guess
     */
    public int guessNumber(int n) {
        int left = 1;
        int right = n;
        
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            int res = guess(mid);
            
            if (res == 0) {
                return mid;
            }
            else if (res == -1) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        
        if (guess(left) == 0) {
            return left;
        } 
        return right;
    }
}