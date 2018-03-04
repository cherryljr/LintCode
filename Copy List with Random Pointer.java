/*
Description
A linked list is given such that each node contains an additional random pointer
which could point to any node in the list or null.

Return a deep copy of the list.

Example
Challenge
Could you solve it with O(1) space?

Tags
Linked List Hash Table Uber
 */

/**
 * Approach 1: HashMap
 * 所有 深度拷贝 的问题都可以通过使用 哈希表 来实现（使用了O(N)的额外空间）
 * 具体做法为：
 *  使用HashMap的 key 来存储原来的节点，value 来储存复制后new出来的节点。
 *  这样我们便有了 复制之后的节点 与 原版节点 的映射关系。
 *  然后我们再次从头到尾遍历一次链表，并借助于 复制节点和原来节点的 映射关系，
 *  将复制后的节点的 next 和 random 指针 与 对应的节点 连接起来。
 *  eg. 假设对于节点 curr, 通过映射关系我们知道其复制节点为 map.get(curr)，
 *  并且我们可以通过原来节点的 next指针 和 random指针 找到其所对应的节点。
 *  而这些节点又能够根据建立好的 映射关系 得到其复制节点。
 *  因此 curr节点的拷贝节点 map.get(curr) 的 next 指针指向 map.get(curr.next),
 *      map.get(curr).next = map.get(curr.next);
 *  同理我们也可以得到 curr 拷贝节点的 random 指针应该指向：
 *      map.get(curr).random = map.get(curr.random);
 *      
 * 时间复杂度为：O(n);  空间复杂度为：O(n)
 */

/**
 * Definition for singly-linked list with a random pointer.
 * class RandomListNode {
 *     int label;
 *     RandomListNode next, random;
 *     RandomListNode(int x) { this.label = x; }
 * };
 */
public class Solution {
    /**
     * @param head: The head of linked list with a random pointer.
     * @return: A new head of a deep copy of the list.
     */
    public RandomListNode copyRandomList(RandomListNode head) {
        if (head == null) {
            return null;
        }

        Map<RandomListNode, RandomListNode> map = new HashMap<>();
        RandomListNode curr = head;
        while (curr != null) {
            map.put(curr, new RandomListNode(curr.label));
            curr = curr.next;
        }

        curr = head;
        while (curr != null) {
            map.get(curr).next = map.get(curr.next);
            map.get(curr).random = map.get(curr.random);
            curr = curr.next;
        }

        return map.get(head);
    }
}

//	No HashMap version
/*
第一遍扫的时候巧妙运用next指针， 开始数组是1->2->3->4。
然后扫描过程中先建立copy节点 1->1`->2->2`->3->3`->4->4`, 
然后第二遍copy的时候去建立边的copy， 
拆分节点, 一边扫描一边拆成两个链表，
这里用到两个dummy node。第一个链表变回  1->2->3 , 然后第二变成 1`->2`->3` 
 */

public class Solution {
    private void copyNext(RandomListNode head) {
        while (head != null) {
            RandomListNode newNode = new RandomListNode(head.label);
            newNode.random = head.random;
            newNode.next = head.next;
            head.next = newNode;
            head = head.next.next;
        }
    }

    private void copyRandom(RandomListNode head) {
        while (head != null) {
            if (head.next.random != null) {
                head.next.random = head.random.next;
            }
            head = head.next.next;
        }
    }

    private RandomListNode splitList(RandomListNode head) {
        RandomListNode newHead = head.next;
        while (head != null) {
            RandomListNode temp = head.next;
            head.next = temp.next;
            head = head.next;
            if (temp.next != null) {
                temp.next = temp.next.next;
            }
        }
        return newHead;
    }

    public RandomListNode copyRandomList(RandomListNode head) {
        if (head == null) {
            return null;
        }
        copyNext(head);
        copyRandom(head);
        return splitList(head);
    }
}

/**
 * Approach 2: Copy the List (Tricky)
 * 链表问题的最优解，通常是在 空间复杂度上 而非在 时间复杂度 上。
 * 因此特别是在 笔试/打比赛 时，能用额外空间 AC 掉的就使用额外空间，万事以A掉题目为准。
 *
 * 当然，这边也会给出最优解，但是这种解法通常比较有技巧性，代码也比较复杂一些。
 * 适合在 面试 的时候与面试官讨论的过程中给出该解法。
 * 那么废话少说，开始说明解法：
 * 本题所使用到的一个技巧就是，在扫描数组的过程中，拷贝各个节点，
 * 并将各个节点的 next 指针指向其后面的 拷贝节点。
 * 这样做的好处就是，我们不需要 HashMap 就能够确定各个节点与其 拷贝节点 的映射关系。(拷贝节点就是原节点的后一个节点)
 * eg. 1->2->3->4  =>  1->1`->2->2`->3->3`->4->4`
 * 然后再次遍历链表，根据映射关系，将 各个拷贝节点的random指针 与其对应的节点连接起来。经过该操作，我们得到一个大链表，
 * 其所有的 random 指针已经连接完毕，最后我们只需要将这个大链表中包含的 原节点 与 复制节点 分离开来即可。
 *
 * 时间复杂度为：O(n);  空间复杂度为：O(1)
 */
public class Solution {
    /**
     * @param head: The head of linked list with a random pointer.
     * @return: A new head of a deep copy of the list.
     */
    public RandomListNode copyRandomList(RandomListNode head) {
        if (head == null) {
            return null;
        }

        RandomListNode curr = head;
        RandomListNode next = null;

        // copy node and link to every node
        while (curr != null) {
            next = curr.next;
            curr.next = new RandomListNode(curr.label);
            curr.next.next = next;
            curr = next;
        }

        // set copy nodes' random pointer
        curr = head;
        RandomListNode currCopyNode = null;
        while (curr != null) {
            next = curr.next.next;
            currCopyNode = curr.next;
            currCopyNode.random = curr.random != null ? curr.random : null;
            curr = next;
        }

        // split the copy nodes from the big linkedlist
        RandomListNode rst = head.next;
        curr = head;
        while (curr != null) {
            next = curr.next.next;
            currCopyNode = curr.next;
            curr.next = next;
            currCopyNode.next = next != null ? next.next : null;
            curr = next;
        }

        return rst;
    }
}