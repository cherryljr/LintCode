/*
Description
There are n monsters and an Altman. Both Altman and Monster have five attribute values.
When Altman's five attributes are greater than or equal to the five attributes of a monster, Altman can kill the monster.
When Altman kills a monster, the five properties of the monster are added to Altman. How many monsters can Altman kill at most?
v[0][0]-v[0][4] means the 5 values of ALtman. v[1]-v[n] means n monsters' 5 value5。

Example
Example 1:
Input: n = 2, v = [[1,1,1,1,1],[1,1,1,1,1],[2,2,2,2,2]]
Output: 2
Explanation: Altman kills monster v[1] at first, and his attribute changes to [2,2,2,2,2]. Then Altman can kill monster v[2]

Example 2:
Input: n = 5, v = [[3,9,2,1,5],[0,9,6,5,9],[6,1,8,6,3],[3,7,0,4,4],[9,9,0,6,5],[5,6,5,6,7]]
Output: 0
Explanation: Altman can not kill any monster.
 */

/**
 * Approach: BFS
 * 一开始遍历所有的怪兽，看有没有一开始就能够击败的，如果存在加入到队列中。
 * 然后每次从队列中拿出可以击败的怪兽，并更新奥特曼的能力值，然后继续遍历所有怪兽即可。
 * 基本就是一个 Brute Force 的做法。
 *
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(n)
 */
public class Solution {
    /**
     * @param n:
     * @param v:
     * @return: nothing
     */
    public int killMonster(int n, int[][] v) {
        if (n <= 0 || v == null || v.length == 0) {
            return 0;
        }

        boolean[] visited = new boolean[n + 1];
        Queue<int[]> queue = new LinkedList<>();
        // Initialize the queue
        for (int i = 1; i <= n; i++) {
            if (canKill(v[0], v[i])) {
                visited[i] = true;
                queue.offer(v[i]);
            }
        }

        int count = 0;
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            update(v[0], curr);
            count++;
            for (int i = 1; i <= n; i++) {
                if (!visited[i] && canKill(v[0], v[i])) {
                    queue.offer(v[i]);
                    visited[i] = true;
                }
            }
        }
        return count;
    }

    private boolean canKill(int[] a, int[] b) {
        return a[0] >= b[0] && a[1] >= b[1] && a[2] >= b[2] && a[3] >= b[3] && a[4] >= b[4];
    }

    private void update(int[] a, int[] b) {
        a[0] += b[0]; a[1] += b[1]; a[2] += b[2]; a[3] += b[3]; a[4] += b[4];
    }

}