/*
Description
LFU (Least Frequently Used) is a famous cache eviction algorithm.
For a cache with capacity k, if the cache is full and need to evict a key in it,
the key with the lease frequently used will be kicked out.
Implement set and get method for LFU cache.

Example
Given capacity=3

set(2,2)
set(1,1)
get(2)
>> 2
get(1)
>> 1
get(2)
>> 2
set(3,3)
set(4,4)
get(3)
>> -1
get(2)
>> 2
get(1)
>> 1
get(4)
>> 4

Tags
Amazon Google
 */

/**
 * Approach 1: Two HashMap + DoubleLinkedList
 * 相较于 LRU,我们可以说在实现上 LFU 是它的一个升级版。
 * 首先我们可以来看一下 LFU 的数据结构：
 *      head <---> FreqNode1 <---> FreqNode2 <---> ... <---> FreqNodeN
 *                    |               |                       |
 *                  first           first                   first
 *                    |               |                       |
 *                KeyNodeA        KeyNodeE                KeyNodeG
 *                    |               |                       |
 *                KeyNodeB        KeyNodeF                KeyNodeH
 *                    |               |                       |
 *                KeyNodeC          last                   KeyNodeI
 *                    |                                       |
 *                KeyNodeD                                   last
 *                    |
 *                  last
 *
 * 它是由一条 双向链表 所组成的。而双向链表中各个节点 FreqNode 又是一条 双向链表（实现上是LRU）。
 * 被操作次数相同的节点 KeyNode 挂在同一个 FreqNode 下面。而 KeyNode 双向链表的实现是一个 LRU.
 * 因此由上可得，横向大链表 list 中的各个节点 FreqNode 里面包含的信息有：
 *  1. 头节点 head （与 LRU 实现类似，用于移除 最不经常被使用 的节点）
 *  2. 被操作的次数 freq
 *  3. 所有被操作次数相同的节点所形成的 双向链表 （这里为了代码的简洁，使用了 LinkedHashSet 来替代 LRU Cache 结构）
 *  当需要发生移除最不经常被使用的节点时，如果操作次数相同的 FreqNode 下面挂着几个节点，
 *  那么移除 最早被操作 的节点（即按照 LRU Cache 的判断方法进行移除）
 *
 * 通过以上分析我们知道了 双向链表中（横向） 所需要包含的信息。接下来我们再来看看 LFU 中需要包含那些信息。
 * LFU 的实现其主要方法与 LRU 相同，仍然是通过 双向链表的增/删/改操作 并配合 HashMap的查找 来降低时间复杂度。
 * 因此我们的 LFU 需要具备一下信息：
 *  1. 容量大小 CAPACITY, 当超过时，会触发 removeOld() 操作
 *  2. keyValueMap. 用于存储各个插入键值对的信息。
 *  3. keyNodeMap. 用于存储插入节点 key 与其对应位于双端链表中哪个节点（FreqNode）位置的信息。
 *  4. list. 双端链表（横向）即上述分析的链表，用于将 FreqNode 连起来，记录操作次数用。
 *  由 head 开始，从左向右，操作次数(freq)递增
 *
 * 接下来的请参考代码以及注释，以方便理解。
 *
 * 时间复杂度：每个操作均为 O(1)
 *
 * 如果不了解 LRU Cache 或者是不清楚 LinkedHashSet 用法的可以参考：
 * https://github.com/cherryljr/LintCode/blob/master/LRU%20Cache.java
 */
public class LFUCache {
    private final int CAPACITY;
    private Map<Integer, Integer> keyValueMap;
    private Map<Integer, FreqNode> keyNodeMap;
    private DoubleLinkedList list;

    /*
     * @param capacity: An integer
     */
    public LFUCache(int capacity) {
        // do initialization if necessary
        this.CAPACITY = capacity;
        keyValueMap = new HashMap<>();
        keyNodeMap = new HashMap<>();
        list = new DoubleLinkedList();
    }

    /*
     * @param key: An integer
     * @param value: An integer
     * @return: nothing
     */
    public void set(int key, int value) {
        if (CAPACITY == 0) {
            return;
        }

        if (!keyValueMap.containsKey(key)) {
            // 只有插入新的 keyNode 才会发生以下操作
            if (keyValueMap.size() >= CAPACITY) {
                // 插入新的节点后，将超过容量大小，故需要移除节点
                list.removeOld();
            }
            // 将新的 keyNode 节点插入到 head 下面
            list.addToHead(key);
        }
        // key 的操作次数+1 (对于 keyNodeMap 的更新也在该函数中进行）
        list.increaseCount(key);
        // 更新 key 所对应的 value
        keyValueMap.put(key, value);
    }

    /*
     * @param key: An integer
     * @return: An integer
     */
    public int get(int key) {
        if (keyValueMap.containsKey(key)) {
            // key 的操作次数+1
            list.increaseCount(key);
            return keyValueMap.get(key);
        }
        return -1;
    }

    class FreqNode {
        int freq;   // 被操作次数
        Set<Integer> set;   // LRU Cache (挂在 FreqNode 下的双向链表)
        FreqNode pre, next;

        FreqNode(int freq) {
            this.freq = freq;
            set = new LinkedHashSet<>();
            pre = next = null;
        }
    }

    class DoubleLinkedList {
        FreqNode head;

        DoubleLinkedList() {
            this.head = null;
        }

        public void increaseCount(int key) {
            FreqNode currNode = keyNodeMap.get(key);
            // 首先将 key 从所对应的 FreqNode 下面移除
            currNode.set.remove(key);

            // currNode是list的最后一个节点(tail)
            if (currNode.next == null) {
                currNode.next = new FreqNode(currNode.freq + 1);
                currNode.next.set.add(key);
                currNode.next.pre = currNode;
            }
            // currNode.freq 操作数 +1 的节点存在
            else if (currNode.next.freq == currNode.freq + 1) {
                currNode.next.set.add(key);
            }
            // currNode.freq 操作数 +1 的节点不存在
            else {
                FreqNode node = new FreqNode(currNode.freq + 1);
                node.set.add(key);
                node.next = currNode.next;
                currNode.next.pre = node;
                node.pre = currNode;
                currNode.next = node;
            }

            // 更新 keyNodeMap 中，key 与 FreqNode 的对应关系
            keyNodeMap.put(key, currNode.next);
            // 将 currNode.set 中的 key 移除之后，检查看 currNode.set 的大小是否为空
            // 即 currNode 下面是否还有悬挂节点，如果没有的话则将 currNode 从 list 中移除
            if (currNode.set.size() == 0) {
                list.removeNode(currNode);
            }
        }

        // 新建一个节点时，其freq为0，这是因为我们后面还将调用一次 increaseCount() 操作
        public void addToHead(int key) {
            if (head ==  null) {
                head = new FreqNode(0);
                head.set.add(key);
            } else if (head.freq > 0) {
                FreqNode node = new FreqNode(0);
                node.set.add(key);
                node.next = head;
                head.pre = node;
                head = node;
            } else {
                head.set.add(key);
            }
            keyNodeMap.put(key, head);
        }

        public void removeOld() {
            if (head == null) {
                return;
            }

            // 利用了 LinkedHashSet 我们可以轻易得到最早被操作元素的 key
            int old = head.set.iterator().next();
            // 首先从 head 下的 set 中将其移除
            head.set.remove(old);
            // 检查移除 old 之后是否会导致 head.set 变为空
            // 如果变为空，我们需要从 list 中移除 head 节点
            if (head.set.size() == 0) {
                removeNode(head);
            }
            // 再从两个储存 key 以及对应信息的 map 中将其移除
            keyValueMap.remove(old);
            keyNodeMap.remove(old);
        }

        public void removeNode(FreqNode removedNode) {
            // 讨论 removedNode 前指针是否为空的情况（是否为 head）
            if (removedNode.pre == null) {
                head = removedNode.next;
            } else {
                removedNode.pre.next = removedNode.next;
            }
            // 讨论 removedNode 后指针是否为空的情况（是否为 tail）
            if (removedNode.next != null) {
                removedNode.next.pre = removedNode.pre;
            }
            // 切断 removedNode 与其他节点的联系
            removedNode.pre = null;
            removedNode.next = null;
        }
    }
}

/**
 * Approach 2: Three HashMap
 * 不使用 双向链表，取而代之的是使用了 HashMap 来实现，
 * 因为没有了 链表操作，实现上会简单不少。（仍然借助了 LinkedHashSet 来实现 LRU Cache)
 * https://leetcode.com/problems/lfu-cache/discuss/94521/JAVA-O(1)-very-easy-solution-using-3-HashMaps-and-LinkedHashSet
 */
public class LFUCache {
    private int min;
    private final int CAPACITY;
    private final HashMap<Integer, Integer> keyToVal;
    private final HashMap<Integer, Integer> keyToCount;
    private final HashMap<Integer, LinkedHashSet<Integer>> countToLRU;

    public LFUCache(int capacity) {
        this.min = -1;
        this.CAPACITY = capacity;
        this.keyToVal = new HashMap<>();
        this.keyToCount = new HashMap<>();
        this.countToLRU = new HashMap<>();
    }

    public int get(int key) {
        if (!keyToVal.containsKey(key)) {
            return -1;
        }

        int count = keyToCount.get(key);
        // remove key from current count (since we will increase the count)
        countToLRU.get(count).remove(key);
        if (count == min && countToLRU.get(count).size() == 0) {
            min++; // nothing in the current min bucket
        }

        putCount(key, count + 1);
        return keyToVal.get(key);
    }

    public void set(int key, int value) {
        if (CAPACITY <= 0) {
            return;
        }

        if (keyToVal.containsKey(key)) {
            keyToVal.put(key, value); // update key's value
            get(key); // update key's count
            return;
        }

        if (keyToVal.size() >= CAPACITY) {
            evict(countToLRU.get(min).iterator().next()); // evict LRU from this min count bucket   
        }

        min = 1;
        putCount(key, min); // adding new key and count
        keyToVal.put(key, value); // adding new key and value
    }

    private void evict(int key) {
        countToLRU.get(min).remove(key);
        keyToVal.remove(key);
    }

    private void putCount(int key, int count) {
        keyToCount.put(key, count);
        countToLRU.computeIfAbsent(count, ignore -> new LinkedHashSet<>());
        countToLRU.get(count).add(key);
    }
}