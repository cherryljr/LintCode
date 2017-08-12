思路过程：
首先对题目进行需求分析，该程序需要具备以下功能：
1.	删除头元素
2.	尾巴插入一个元素
3.	删除中间元素
4.	元素按照时间排序
5.	快速查找一个元素
于是我们想到了使用 链表 / 数组 来实现。因为需要经常性地增加或删除元素
故我们排除了数组，但是链表又无法做到快速查找一个元素。所以我们想到了能否借助
HashMap来实现快速查找。
每当一个新的元素到来时，我们先查询HashMap,如果表里已经有这个key了,则更新该链表，
将该元素移动到链表末尾，表示该元素刚刚被访问过。即链表的顺序就是元素被访问的先后顺序。
如果不包含该元素这将其插入链表的末尾，如果空间已经满了，则删除链表的一个节点。然后再
插入该元素。

注：1. 为了方便操作，链表拥有头节点和尾节点, 作用类似 Dummy Node
		2. 虽然HashMap能帮我们快速定位到要查找的节点，但是链表要删除一个节点需要知道
		其前面以及后面一个的节点是什么。所以我们使用了双向链表。
	
/*
Description
Design and implement a data structure for Least Recently Used (LRU) cache. It should support the following operations: get and set.

get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
set(key, value) - Set or insert the value if the key is not already present. When the cache reached its capacity, 
it should invalidate the least recently used item before inserting a new item.

Tags 
Linked List Google Uber Zenefits
*/

// Use double linked list to store value.
// Store key in hashmap<key, node> to find node quickly

public class LRUCache {
    private class Node{
        Node pre;
        Node next;
        int key;
        int value;
    
        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.pre = null;
            this.next = null;
        }
    }
    
    private int capacity;
    private HashMap<Integer, Node> hs = new HashMap<Integer, Node>();
    private Node head = new Node(-1, -1);
    private Node tail = new Node(-1, -1);

    // @param capacity, an integer
    public LRUCache(int capacity) {
        this.capacity = capacity;
        tail.pre = head;
        head.next = tail;
    }

    // @return an integer
    public int get(int key) {
        if( !hs.containsKey(key)) {
            return -1;
        }

        // remove current
        Node current = hs.get(key);
        current.pre.next = current.next;
        current.next.pre = current.pre;

        // move current to tail
        move_to_tail(current);

        return hs.get(key).value;
    }

    // @param key, an integer
    // @param value, an integer
    // @return nothing
    public void set(int key, int value) {
        // this internal `get` method will update the key's position in the linked list.
        if (get(key) != -1) {
            hs.get(key).value = value;
            return;
        }

        if (hs.size() == capacity) {
            hs.remove(head.next.key);
            head.next = head.next.next;
            head.next.pre = head;
        }

        Node insert = new Node(key, value);
        hs.put(key, insert);
        move_to_tail(insert);
    }
    
     private void move_to_tail(Node current) {
        current.pre = tail.pre;
        tail.pre = current;
        current.pre.next = current;
        current.next = tail;
    }
}