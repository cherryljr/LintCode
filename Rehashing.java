开哈希的 rehash 方法。
遍历原来的整个 HashMap 的数组。
如果节点不为空，则遍历该链表，重新计算 HashCode.
插入时若节点不为空，则按照开哈希的方法解决冲突。

/*
Description
The size of the hash table is not determinate at the very beginning. If the total size of keys is too large 
(e.g. size >= capacity / 10), we should double the size of the hash table and rehash every keys. 
Say you have a hash table looks like below:
size=3, capacity=4
[null, 21, 14, null]
       ↓    ↓
       9   null
       ↓
      null
The hash function is:
int hashcode(int key, int capacity) {
    return key % capacity;
}
here we have three numbers, 9, 14 and 21, where 21 and 9 share the same position as they all have the same hashcode 1 (21 % 4 = 9 % 4 = 1). 
We store them in the hash table by linked list.
rehashing this hash table, double the capacity, you will get:
size=3, capacity=8
index:   0    1    2    3     4    5    6   7
hash : [null, 9, null, null, null, 21, 14, null]
Given the original hash table, return the new hash table after rehashing .

Notice
For negative integer in hash table, the position can be calculated as follow:
C++/Java: if you directly calculate -4 % 3 you will get -1. You can use function: a % b = (a % b + b) % b to make it is a non negative integer.
Python: you can directly use -1 % 3, you will get 2 automatically.

Example
Given [null, 21->9->null, 14->null, null],
return [null, 9->null, null, null, null, 21->null, 14->null, null]

Tags 
Hash Table LintCode Copyright
*/

/**
 * Definition for ListNode
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    /**
     * @param hashTable: A list of The first node of linked list
     * @return: A list of The first node of linked list which have twice size
     */    
    public ListNode[] rehashing(ListNode[] hashTable) {
        if (hashTable.length == 0) {
            return hashTable;
        }
        
        int newCapacity = hashTable.length * 2;
        ListNode[] newTable = new ListNode[newCapacity];
        
        // Traverse the Arrays
        for (int i = 0; i < hashTable.length; i++) {
        	// Traverse the LinkedList
            while (hashTable[i] != null) {
            	// get the newIndex
                int newIndex 
                = (hashTable[i].val % newCapacity + newCapacity) % newCapacity;
                // if the newTable[newIndex] is null then insert the node directly
                if (newTable[newIndex] == null) {
                    newTable[newIndex] = new ListNode(hashTable[i].val);
                } else {
                	// if it's not null, traverse the linkedlist until the next node is null then insert it
                    ListNode dummy = newTable[newIndex];
                    while (dummy.next != null) {
                        dummy = dummy.next;
                    }
                    dummy.next = new ListNode(hashTable[i].val);
                }
                hashTable[i] = hashTable[i].next;
            }
        }
        
        return newTable;
    }
};





