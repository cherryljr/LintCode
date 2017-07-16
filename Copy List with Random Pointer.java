此题有两种解法：
	1. 使用HashMap，需要使用到O(N)的额外空间
	2. 不使用HashMap，仅仅需要使用到O(1)的额外空间

// HashMap Version
所有深度拷贝的问题都可以通过使用哈希表来实现（使用了O(N)的额外空间）。
使用HashMap的key来存储原来的节点，value来储存复制后new出来的节点。
这样我们便有了复制之后的节点与原版节点的映射关系。
 	1. 检查Map中是否包含原本List中的node，若没有new一个插入。
 	2. 检查Map中是否包含了node.random，若没有new一个插入
 	
// No HashMap Version
此解法较为巧妙，记住即可

/*
A linked list is given such that each node contains an additional random pointer 
which could point to any node in the list or null.

Return a deep copy of the list.

Hide Company Tags Microsoft Uber
Hide Tags Hash Table Linked List
Hide Similar Problems (M) Clone Graph

LeetCode: Hard
*/  


//	HashMap version
/*
Thinking process:
1. Loop through the original list
2. Use a HashMap<old node, new node>. User the old node as a key and new node as value.
3. Doesn't matter of the order of node that being added into the hashMap.
    For example, node1 is added.
    node1.random, which is node 99, will be added into hashMap right after node1.
4. During the loop:
    If head exist in hashmap, get it; if not existed, create new node using head, add into hashMap
    If head.random exist, get it; if not, add a new node using head.random.
border case: if head == null, return null

*/

public class Solution {
    public RandomListNode copyRandomList(RandomListNode head) {
        if (head == null) {
            return null;
        }

        HashMap<RandomListNode, RandomListNode> map = new HashMap<RandomListNode, RandomListNode>();
        RandomListNode dummy = new RandomListNode(0);
        RandomListNode pre = dummy, newNode;
        
        while (head != null) {
            if (map.containsKey(head)) {
                newNode = map.get(head);
            } else {
                newNode = new RandomListNode(head.label);
                map.put(head, newNode);
            }
            pre.next = newNode;

            if (head.random != null) {
                if (map.containsKey(head.random)) {
                    newNode.random = map.get(head.random);
                } else {
                    newNode.random = new RandomListNode(head.random.label);
                    map.put(head.random, newNode.random);
                }
            }

            pre = newNode;
            head = head.next;
        }

        return dummy.next;
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