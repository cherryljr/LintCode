/**
 * Morris Traversal Template
 * 这是一个非常棒的算法，它可以在 O(n) 的时间复杂度内，并以 O(1) 的空间复杂度完成对树的遍历。
 * 并且学习它可以使得我们更进一步掌握树的递归遍历。
 * 相信我，看完这部分内容，你会对 树的遍历 有更深一层次的认识。
 * 那么从现在开始，请忘记 先/中/后序遍历，读罢以下内容你自然会明白它们本质是什么。
 *
 * 首先，我们介绍一下 Morris 算法是怎么实现的。
 *  我们利用 curr 代表当前节点。
 *  当 curr 的左子树为空时 (curr.left == null)，curr 直接向右子节点移动。
 *  当 curr 的左子树不为空时，我们求其 左子树 的最右子节点rightMost
 *      ① 当 rightMost 的右子节点为空时，我们令 rightMost.right = curr，
 *      即利用本来被浪费的空间 rightMost.right 将其余 curr 节点连接起来了，使得我们可以通过其返回 curr.
 *      然后 curr 节点向 左子节点 移动。
 *      ② 当 rightMost 的右子节点不为空时，此时其指向我们之前连接好的 curr 节点。
 *      我们令 rightMost.right = null，即断开连接，然后 curr 先 右子节点 移动
 *      ③ 循环以上步骤，直到 curr == null. 说明遍历完整棵树了。
 * 代码注意点：
 *  在寻找 rightMost 的时候一定要记得判断 rightMost ！= curr 才行，因为我们可能之前已经将 rightMost.right
 *  连接到了 curr 上面，如果不加上该条件就会出错，请务必注意！
 *
 * 这个时候先别急，我们来聊聊传统的 树的递归遍历操作.
 * 同样的，先别去管是什么序遍历，我们就单纯地从程序代码方面来进行分析。
 * 去除 关于对节点的 print/store 操作，我们可以发现其代码是一模一样的。
 * 举个例子：
 *      1
 *    / \
 *  2   3
 * 程序执行的时候，将会先后访问：1 2 2 2 1 3 3 3 1.
 * 这就是 递归 的特性，它必须回到之前被调用的位置才知道接下来要怎么执行。
 * 因此我们可以发现：
 * 经典的递归遍历就是前后来到一个节点总共 3 次。
 * 树的所谓 先序/中序/后序 遍历只是在来到这个节点的 不同时间 进行了相应的人为处理罢了。（如打印，或者加入list等）
 *     先序就是来到节点的 第一次 进行处理；
 *     中序就是来到节点的 第二次 进行处理；
 *     后序就是来到节点的 第三次 进行处理；
 * 这就是树的 递归遍历 的本质。现在你是不是觉得不管是什么序遍历都非常清楚了呢？
 * 因为它们本质就是同一个算法啊。只是我们 处理节点数据的时机不同 罢了。
 *
 * 接下来我们回到 Morris 遍历算法，同样按照以上的方式进行分析，我们可以发现：
 *  在 Morris 遍历中，只要该节点有左子树，Morris遍历就会遍历该节点 两次 ，
 *  并且第二次 visit 到它的时候，它一定把其 所有的左子树上的节点 全部遍历过一遍了。
 * 那么如何标记是 第几次 来到这个节点呢？
 *  很简单，看其 左子树 的 最右节点的右子节点指针 是否为空就行了，为空，就是第一次，不为空就是第二次。
 *  因为 第一次 我们会把rightMost的 right 连接到 curr 上来作为我们返回的路径，第二次 我们会断开这个连接，然后我们再也不会 visit 它了。
 * （不存在左子树的节点只会被 visit 一次）
 *
 * 有了以上的分析，如果我们想要利用 Morris 遍历的方式实现 先序/中序/后序 遍历，
 * 同样也只是在 遍历的不同时机，人为地处理节点罢了。而整个遍历的流程是完全相同的。
 * PS. 这边再次重点强调 Morris 遍历算法 与 树的递归遍历 方法一样，只是一种方法。
 * 不管是 先序，中序，还是后序 它都能够实现，千万别被这个限制住了。
 * 有非常多的 Blog 只写了 中序 遍历实现，导致有人错误地认为 Morris遍历 只是一种 O(1)的中序遍历。
 * （我承认理解起来按照中序遍历的方法会顺一些，所以我希望大家不要带着任何 X序的 思想去看待 Morris 遍历）
 * 事实上是，只需要我们修改 人为处理节点 的时机，就能够实现 先序/中序/后序 遍历。
 *
 * 对于 Morris遍历 实现 先序/中序 遍历还是很简单的，上文也提到了如何判断该节点是 第几次 被访问到。
 * 与传统 树的递归遍历 相同，第一次来到这个节点时处理就是先序，第二次就是中序。
 * 实现代码都提供了非常详细的注解，大家可以直接看代码即可。
 * 这边要重点说明的是：Morris后序遍历。
 * 通过上文分析我们知道，Morris遍历中是 不会第三次 访问一个节点的。
 * 那么我们要怎么解决呢？
 * 具体做法为：
 *  后序遍历时，我们只关心那些能够被访问到两次的节点，只能被访问的节点(无左子树)直接忽略。
 *  然后 第二次 到达一个节点时，在结果集中加入其 左子树右边界的 逆序 。
 *  然后在函数退出之前，再在结果集中加入 整棵树右边界的 逆序。
 *  那么如何实现在 O(1)的空间复杂度内 逆序打印 一棵树的右边界呢？
 *  明显我们是无法利用 栈 来实现的（空间复杂度O(n)），但是我们发现一颗树的右边界结构不就是一个链表吗，
 *  因此我们可以利用 Reverse Linked List 这个操作将树的右边界进行一个 逆序 操作。
 *  遍历完之后，再修改回来即可。从而实现在 O(1)的空间复杂度内 逆序打印 一棵树的右边界。
 *
 * 最后我们来分析一下 Morris遍历 的时间复杂度吧。
 * 许多人不明白为什么 Morris遍历的时间复杂度仍然是 O(n).
 * 认为在处理 rightMost 时,花费了 O(logn) 的时间，所以时间复杂度应该是 O(nlogn) 才对。
 * 但是并不是这样的，我们不妨可以将整个树划分成 一条条右边界 来看待。
 * 每条右边界最多被访问 2 次。而 右边界 总共有多少个点呢？总共有 n 个。
 * 因为每条右边界只会被 有限次 地访问，所以总体时间复杂度仍然为：O(n)
 * 空间复杂度：O(1)
 *
 * 以上便是关于 Morris遍历 的所有讲解，同时我们还结合了 树递归遍历 的本质来讲解，
 * 希望可以帮助大家进一步加深对 树递归遍历 的理解。
 */

import java.util.*;

class TreeNode {
    public int val;
    public TreeNode left, right;

    public TreeNode(int val) {
        this.val = val;
        this.left = this.right = null;
    }
}

public class MorrisTraversal {

    // 先序 Morris 遍历
    public static List<Integer> preOrderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        TreeNode curr = root;
        TreeNode rightMost = null;
        List<Integer> rst = new ArrayList<>();
        while (curr != null) {
            if (curr.left == null) {
                /*
                 * If there is no left subtree, then we can visit this node and
                 * continue traversing right.
                 */
                // Store the curr.val when we visit the node firstly
                rst.add(curr.val);
                // move to next right node
                curr = curr.right;
            } else {
                // if curr node has a left subtree
                // then get rightmost node of left subtree
                rightMost = getRightMostNode(curr);
                if (rightMost.right == null) {
                    /*
                     * If the rightMost node's right subtree is null, then we have never been here before.
                     * (the first time that we visit the curr node)
                     * the current node should be the right child of the rightMost node.
                     */
                    // Store the curr.val when we visit the node firstly
                    rst.add(curr.val);
                    rightMost.right = curr;
                    curr = curr.left;
                } else {
                    /*
                     * If there is a right subtree, it is a link that we created on a previous pass,
                     * (the second time that we visit the curr node)
                     * so we should unlink it and visit this node to avoid infinite loops
                     */
                    rightMost.right = null;
                    curr = curr.right;
                }
            }
        }

        return rst;
    }

    // 中序 Morris 遍历
    public static List<Integer> inorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        TreeNode curr = root;
        TreeNode rightMost = null;
        List<Integer> rst = new ArrayList<>();
        while (curr != null) {
            if (curr.left == null) {
                /*
                 * If there is no left subtree, then we can visit this node and
                 * continue traversing right.
                 */
                // if curr don't have left subtree, it will be visited only once
                // Or you can look it like we visit the node twice at the same time
                // so add curr.val to the list directly
                rst.add(curr.val);
                // move to next right node
                curr = curr.right;
            } else {
                // if curr node has a left subtree
                // then get rightmost node of left subtree
                rightMost = getRightMostNode(curr);
                if (rightMost.right == null) {
                    /*
                     * If the rightMost node's right subtree is null, then we have never been here before.
                     * (the first time that we visit the curr node)
                     * the current node should be the right child of the rightMost node.
                     */
                    rightMost.right = curr;
                    curr = curr.left;
                } else {
                    /*
                     * If there is a right subtree, it is a link that we created on a previous pass,
                     * (the second time that we visit the curr node)
                     * so we should unlink it and visit this node to avoid infinite loops
                     */
                    rightMost.right = null;
                    // Store the curr.val when we visited it at the second time
                    rst.add(curr.val);
                    curr = curr.right;
                }
            }
        }

        return rst;
    }

    // 后序 Morris 遍历
    public static List<Integer> postorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        TreeNode curr = root;
        TreeNode rightMost = null;
        List<Integer> rst = new ArrayList<>();
        while (curr != null) {
            if (curr.left == null) {
                /*
                 * If there is no left subtree, then we can visit this node and
                 * continue traversing right.
                 */
                // move to next right node
                curr = curr.right;
            } else {
                // if curr node has a left subtree
                // then get rightmost node of left subtree
                rightMost = getRightMostNode(curr);
                if (rightMost.right == null) {
                    /*
                     * If the rightMost node's right subtree is null, then we have never been here before.
                     * (the first time that we visit the curr node)
                     * the current node should be the right child of the rightMost node.
                     */
                    rightMost.right = curr;
                    curr = curr.left;
                } else {
                    /*
                     * If there is a right subtree, it is a link that we created on a previous pass,
                     * (the second time that we visit the curr node)
                     * so we should unlink it and visit this node to avoid infinite loops
                     */
                    // Remember to unlink the rightMost's right pointer to the curr node firstly
                    rightMost.right = null;
                    // Store the curr.val when we visited it at the second time
                    addReverseEdge(rst, curr.left);
                    curr = curr.right;
                }
            }
        }
        // When we quit the loop, add the reversal right edge of root to the result
        addReverseEdge(rst, root);

        return rst;
    }

    private static void addReverseEdge(List<Integer> rst, TreeNode root) {
        TreeNode tail = reverseEdge(root);
        TreeNode curr = tail;
        while (curr != null) {
            rst.add(curr.val);
            curr = curr.right;
        }
        reverseEdge(tail);
    }

    private static TreeNode reverseEdge(TreeNode root) {
        TreeNode pre = null;
        while (root != null) {
            TreeNode temp = root.right;
            root.right = pre;
            pre = root;
            root = temp;
        }
        return pre;
    }

    // Get the node with the right most position of left subtree of curr.
    // Attention: node.right != curr, cuz the node.right may be linked to curr node before.
    private static TreeNode getRightMostNode(TreeNode curr) {
        TreeNode node = curr.left;
        while (node.right != null && node.right != curr) {
            node = node.right;
        }
        return node;
    }

    public static void main(String[] args) {
        TreeNode head = new TreeNode(1);
        head.left = new TreeNode(2);
        head.right = new TreeNode(3);
        head.left.left = new TreeNode(4);
        head.left.right = new TreeNode(5);
        head.right.left = new TreeNode(6);
        head.right.right = new TreeNode(7);

        System.out.print("Preorder Traverse: ");
        printTraversalOrder(preOrderTraversal(head));
        System.out.print("Inorder Traverse: ");
        printTraversalOrder(inorderTraversal(head));
        System.out.print("Postorder Traverse: ");
        printTraversalOrder(postorderTraversal(head));
    }

    public static void printTraversalOrder(List<Integer> list) {
        for (int i : list) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

}
