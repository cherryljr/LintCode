/*
Description
Tower of Hanoi problem, is a well-known problem.
On the A, B, C three pillars, there are n disks of different sizes (radii 1-n), they are stacked in a start on A,
your goal is to a minimum number of legal steps to move all the plates move from A to C tower tower.
Each step in the rules of the game are as follows:
    1. Each step is only allowed to move a plate (from the top of one pillars to the top of another pillars)
    2. The process of moving, you must ensure that a large dish is not at the top of the small plates
    (small can be placed on top of a large, below the maximum plate size can not have any other dish)

Diagram:
http://ww4.sinaimg.cn/large/0060lm7Tly1fphwld4at7j30dm05q74d.jpg

Example
Given n = 3
return ["from A to C","from A to B","from C to B","from A to C","from B to A","from B to C","from A to C"]

Tags
Backtracking
 */

/**
 * Approach: Recursive
 * 汉诺塔问题，递归的一道经典问题。
 * 如果想要把 n 层的汉诺塔从 A 移动到 C，其过程可以分为 3 步：
 *  1. 把 n-1 层的汉诺塔从 A 移动到 B （作为一个缓冲）
 *  2. 把第 n 层从 A 移动到 C
 *  3. 最后把 n-1 层的汉诺塔从缓冲区 B 移动到 C 上（期间用到了A作为缓冲）
 * 把过程说的生动点就是：如何把大象放进冰箱呢？
 *  1. 把冰箱门打开（对应以上第一步，将 n-1 层移动到缓冲区）
 *  2. 把大象放进冰箱（对应以上第二步）
 *  3. 把冰箱门关上（对应以上第三步）
 *
 * 因此时间复杂度的递推式为：T(N) = 2T(N) + 1
 * 总共需要 2^n - 1 步
 * 
 * 参考资料：
 * https://www.zhihu.com/question/24385418
 */
public class Solution {
    /**
     * @param n: the number of disks
     * @return: the order of moves
     */
    public List<String> towerOfHanoi(int n) {
        if (n <= 0) {
            return new LinkedList<>();
        }

        List<String> rst = new LinkedList<>();
        process(rst, n, "A", "C", "B");
        return rst;
    }

    private void process(List<String> rst, int n, String from, String to, String buffer) {
        // 如果只剩下一层了，直接从 from 移动到 to 即可
        if (n == 1) {
            rst.add("from " + from + " to " + to);
            return;
        }

        // 将 n-1 层移动到 缓冲区
        process(rst, n - 1, from, buffer, to);
        // 将第 n 层移动到目标位置 to
        rst.add("from " + from + " to " + to);
        // 将 n-1 层从 缓冲区 移动回目标位置 to
        process(rst, n - 1, buffer, to, from);
    }
}