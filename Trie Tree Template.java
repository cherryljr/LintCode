/**
 * 这边只是提供了一个 Trie Tree 的模板，实际使用时，
 * 可以在这个基础上根据题目要求进行 修改。
 *
 * 本模板拥有的功能为：
 *  1. 建立 Trie Tree, 根据 insert 方法（即可以向树中 插入 一个单词）
 *  2. 从 Trie Tree 中删除某一个单词
 *  3. 在 Trie Tree 中搜索某一个单词，看是否存在
 *  4. 在 Trie Tree 中搜索存在几个以 preWord 作为前缀的单词
 */
class TrieTree {

    public static class TrieNode {
        // 插入/删除一个单词时，每当经过节点 node(字符), 其 path +1/-1
        // 这在我们进行 前缀个数 搜索时，非常方便
        public int path;
        // 以当前节点作为结尾的单词有几个（这边考虑了重复的单词）
        // 这个参数可以根据需求进行修改，比如在 Word Search II 我们为了方便直接使用了 String word
        public int end;
        public TrieNode[] child;

        public TrieNode() {
            path = 0;
            end = 0;
            child = new TrieNode[26];
        }
    }

    public static class Trie {
        private TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        // 插入单词 word
        public void insert(String word) {
            if (word == null) {
                return;
            }
            char[] chs = word.toCharArray();
            TrieNode node = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = chs[i] - 'a';
                if (node.child[index] == null) {
                    node.child[index] = new TrieNode();
                }
                node = node.child[index];
                node.path++;
            }
            node.end++;
        }

        // 删除单词 word
        public void delete(String word) {
            if (search(word) != 0) {
                char[] chs = word.toCharArray();
                TrieNode node = root;
                int index = 0;
                for (int i = 0; i < chs.length; i++) {
                    index = chs[i] - 'a';
                    if (--node.child[index].path == 0) {
                        // 如果 node 的 path 为 0，说明可以直接移除了，
                        // 同样后面的节点也可以直接移除了，不用就遍历了
                        node.child[index] = null;
                        return;
                    }
                    node = node.child[index];
                }
                node.end--;
            }
        }

        // 查找单词 word
        public int search(String word) {
            if (word == null) {
                return 0;
            }
            char[] chs = word.toCharArray();
            TrieNode node = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = chs[i] - 'a';
                if (node.child[index] == null) {
                    return 0;
                }
                node = node.child[index];
            }
            return node.end;
        }

        // 查找单词前缀 preWord
        public int prefixNumber(String preWord) {
            if (preWord == null) {
                return 0;
            }
            char[] chs = preWord.toCharArray();
            TrieNode node = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = chs[i] - 'a';
                if (node.child[index] == null) {
                    return 0;
                }
                node = node.child[index];
            }
            return node.path;
        }
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        System.out.println(trie.search("cherry"));
        trie.insert("cherry");
        System.out.println(trie.search("cherry"));
        trie.delete("cherry");
        System.out.println(trie.search("cherry"));
        trie.insert("cherry");
        trie.insert("cherry");
        trie.delete("cherry");
        System.out.println(trie.search("cherry"));
        trie.delete("cherry");
        System.out.println(trie.search("cherry"));
        trie.insert("cherryA");
        trie.insert("cherryC");
        trie.insert("cherryB");
        trie.insert("cherryD");
        trie.delete("cherryA");
        System.out.println(trie.search("cherryA"));
        System.out.println(trie.prefixNumber("cherry"));
    }

}
