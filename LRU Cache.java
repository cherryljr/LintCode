/*
Description
Design and implement a data structure for Least Recently Used (LRU) cache.
It should support the following operations: get and set.

get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
set(key, value) - Set or insert the value if the key is not already present.
When the cache reached its CAPACITY, it should invalidate the least recently used item before inserting a new item.

Example
Tags
Linked List Uber Zenefits Google
 */

/**
 * Approach 1: HashMap + DoubleLinkedList
 * 这类题目纯粹考察的是我们的 Coding 能力。
 * 只需要分析好这个数据结构需要实现的 逻辑功能，并处理好各种情况下的 Case,就能够解决题目。
 *
 * 为了实现 LRU，首先我们需要准备一个 HashMap 来存储 key 与对应的信息 value。
 * 其次，各个节点具有优先级（最新被调用的优先级最高），并且我们需要 频繁地 对其进行修改。
 * 那么最合适的毫无疑问就是 链表了。并且我们还需要在队列的两端都能够进行操作，
 * 因此我们需要设计一个 双向链表。具备 head, tail 指针，数据从 尾部 插入（tail节点优先级最高）
 *
 * 接下来我们来考虑，HashMap 和 链表的节点 中应该存放哪些信息。
 * 对与 HashMap 而言，key毫无疑问就是我们插入的 key 了，但是 value 就直接是对应的 value 吗？
 * 并不是，因为对于 链表 而言，查询一个 key 需要花费 O(n) 的时间代价，
 * 但是我们使用了 HashMap 因此可以将查询的时间复杂度降低到 O(1),那么为什么不在 value 中放入链表的 Node 呢？
 * 这样就能够使我们可以通过 map 查询到 key 对应的 node 之后，直接对 双向链表 进行操作，从而省去遍历链表的时间。
 * 对于 Node 而言，我们需要放入的信息有：value, prePointer, nextPointer 以及 key.
 * 放入 key 的原因是考虑到了 removeHead() 这个函数，当 LRU 的大小到达 CAPACITY 时，
 * 如果继续插入元素，我们将移除 优先级最低 的节点，也就是我们的 head 节点。
 * 这个时候如果 Node 中含有 key 这个信息的话，我们可以直接通过 Node 获得 key，从而对 map 也进行相应的 remove 操作。
 *
 * 具体实现请参考代码以及注释。
 */
public class LRUCache {
    private Map<Integer, Node> map;
    private DoubleLinkedList list;
    public final int CAPACITY;

    /*
     * @param capacity: An integer
     */public LRUCache(int capacity) {
        // do intialization if necessary
        this.map = new HashMap<>();
        this.list = new DoubleLinkedList();
        this.CAPACITY = capacity;
    }

    /*
     * @param key: An integer
     * @return: An integer
     */
    public int get(int key) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            // 因为发生 get 操作，将对应的节点移动到链表末尾（优先级置为最高）
            list.moveNodeToTail(node);
            return node.value;
        }
        return -1;
    }

    /*
     * @param key: An integer
     * @param value: An integer
     * @return: nothing
     */
    public void set(int key, int value) {
        if (!map.containsKey(key)) {
            // 若不包含该元素，则在 map 和 list 中插入数据
            Node newNode = new Node(key, value);
            list.add(newNode);
            map.put(key, newNode);
            if (map.size() > CAPACITY) {
                // 如果本次插入导致了map的大小 大于 LRU.CAPACITY，则移除优先级最低的节点
                removeMostUnusedNode();
            }
        } else {
            // 更新节点值，同时将对应的节点优先级置为最高
            Node node = map.get(key);
            node.value = value;
            list.moveNodeToTail(node);
        }
    }

    private void removeMostUnusedNode() {
        // 分别从 list 和 map 中移除对应的元素
        Node removedNode = this.list.removeHead();
        map.remove(removedNode.key);
    }

    private class Node {
        int key;
        int value;
        Node pre, next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.pre = null;
            this.next = null;
        }
    }

    private class DoubleLinkedList {
        private Node head;  // 头结点（优先级最低）
        private Node tail;  // 尾节点（优先级最高）

        public DoubleLinkedList() {
            this.head = null;
            this.tail = null;
        }

        public void add(Node node) {
            if (node == null) {
                return;
            }

            if (head == null) {
                head = node;
                tail = node;
            } else {
                tail.next = node;
                node.pre = tail;
                tail = node;
            }
        }

        public void moveNodeToTail(Node node) {
            if (node == tail) {
                // 如果本身就是尾节点，则无需移动
                return;
            }

            if (node == head) {
                // 如果要移动到末尾的是头节点，则需要将头结点向后移动一位（移除原来的头结点）
                head = head.next;
                head.pre = null;
            } else {
                // 如果要移除的节点位于 链表中间，则连接该节点两端的节点
                node.pre.next = node.next;
                node.next.pre = node.pre;
            }
            // 将该节点连接在 原来的tail 之后，然后更新 tail 节点
            tail.next = node;
            node.pre = tail;
            node.next = null;
            tail = node;
        }

        public Node removeHead() {
            if (head == null) {
                return null;
            }

            Node removedNode = head;
            if (head == tail) {
                // 只有一个节点，直接删除即可
                head = null;
                tail = null;
            } else {
                // 删除头节点
                head = removedNode.next;
                head.pre = null;
                removedNode.next = null;
            }
            return removedNode;
        }
    }
}

/**
 * Approach 2: Using LinkedHashMap
 * This is the laziest implementation using Java’s LinkedHashMap.
 *
 * Several points to mention:
 * In the constructor, the third boolean parameter specifies the ordering mode.
 * If we set it to true, it will be in access order.
 * (https://docs.oracle.com/javase/8/docs/api/java/util/LinkedHashMap.html#LinkedHashMap-int-float-boolean-)
 * By overriding removeEldestEntry in this way, we do not need to take care of it ourselves.
 * It will automatically remove the least recent one when the size of map exceeds the specified capacity.
 * (https://docs.oracle.com/javase/8/docs/api/java/util/LinkedHashMap.html#removeEldestEntry-java.util.Map.Entry-)
 */
public class LRUCache {
    private LinkedHashMap<Integer, Integer> map;
    private final int CAPACITY;

    /*
     * @param capacity: An integer
     */
    public LRUCache(int capacity) {
        // do intialization if necessary
        this.CAPACITY = capacity;
        // The map will be in access order
        map = new LinkedHashMap<Integer, Integer>(capacity, 0.75f, true){
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > CAPACITY;
            }
        };
    }

    /*
     * @param key: An integer
     * @return: An integer
     */
    public int get(int key) {
        return map.getOrDefault(key, -1);
    }

    /*
     * @param key: An integer
     * @param value: An integer
     * @return: nothing
     */
    public void set(int key, int value) {
        map.put(key, value);
    }
}


/**
 * LRU Cache Template (Using Generics)
 */
public class LRU {

    public static class Node<K, V> {
        public K key;
        public V value;
        public Node<K, V> pre;
        public Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.pre = null;
            this.next = null;
        }
    }

    public static class DoubleLinkedList<K, V> {
        private Node<K, V> head;
        private Node<K, V> tail;

        public DoubleLinkedList() {
            this.head = null;
            this.tail = null;
        }

        public void addNode(Node<K, V> newNode) {
            if (newNode == null) {
                return;
            }
            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                newNode.pre = tail;
                tail = newNode;
            }
        }

        public void moveNodeToTail(Node<K, V> node) {
            if (tail == node) {
                return;
            }

            if (head == node) {
                head = node.next;
                head.pre = null;
            } else {
                node.pre.next = node.next;
                node.next.pre = node.pre;
            }
            tail.next = node;
            node.pre = tail;
            node.next = null;
            tail = node;
        }

        public Node<K, V> removeHead() {
            if (head == null) {
                return null;
            }

            Node<K, V> removedNode = head;
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                head = removedNode.next;
                head.pre = null;
                removedNode.next = null;
            }
            return removedNode;
        }

    }

    public static class LRUCache<K, V> {
        private HashMap<K, Node<K, V>> map;
        private DoubleLinkedList<K, V> list;
        private final int CAPACITY;

        public LRUCache(int capacity) {
            // do intialization
            if (capacity < 1) {
                throw new RuntimeException("should be more than 0.");
            }
            map = new HashMap<>();
            list = new DoubleLinkedList<>();
            this.CAPACITY = capacity;
        }

        public V get(K key) {
            if (map.containsKey(key)) {
                Node<K, V> node = map.get(key);
                list.moveNodeToTail(node);
                return node.value;
            }
            return null;
        }

        public void set(K key, V value) {
            if (map.containsKey(key)) {
                Node<K, V> node = map.get(key);
                node.value = value;
                list.moveNodeToTail(node);
            } else {
                Node<K, V> newNode = new Node<>(key, value);
                map.put(key, newNode);
                list.addNode(newNode);
                if (map.size() > CAPACITY) {
                    removeMostUnusedCache();
                }
            }
        }

        private void removeMostUnusedCache() {
            Node<K, V> removedNode = list.removeHead();
            map.remove(removedNode.key);
        }

    }

    public static void main(String[] args) {
        LRUCache<String, Integer> testCache = new LRUCache<>(3);
        testCache.set("A", 1);
        testCache.set("B", 2);
        testCache.set("C", 3);
        System.out.println(testCache.get("B"));
        System.out.println(testCache.get("A"));
        testCache.set("D", 4);
        System.out.println(testCache.get("D"));
        System.out.println(testCache.get("C"));
    }

}