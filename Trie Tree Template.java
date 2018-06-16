/**
 * 这边只是提供了一个 Trie Tree 的模板，实际使用时，
 * 可以在这个基础上根据题目要求进行 修改。
 *
 * 本模板拥有的功能为：
 *  1. 建立 Trie Tree, 根据 insert 方法（即可以向树中 插入 一个单词）
 *  2. 从 Trie Tree 中删除某一个单词
 *  3. 在 Trie Tree 中搜索某一个单词，看是否存在
 *  4. 在 Trie Tree 中搜索存在几个以 preWord 作为前缀的单词
 *
 * 相关应用例题有：
 * Longest Word in Dictionary:
 *  https://github.com/cherryljr/LeetCode/blob/master/Longest%20Word%20in%20Dictionary.java
 * Prefix and Suffix Search:
 *  https://github.com/cherryljr/LeetCode/blob/master/Prefix%20and%20Suffix%20Search.java
 * Short Encoding of Words:
 *  https://github.com/cherryljr/LeetCode/blob/master/Short%20Encoding%20of%20Words.java
 * Add and Search Word:
 *  https://github.com/cherryljr/LintCode/blob/master/Add%20and%20Search%20Word.java
 * Maximum XOR of Two Numbers in an Array:
 *  https://github.com/cherryljr/LeetCode/blob/master/Maximum%20XOR%20of%20Two%20Numbers%20in%20an%20Array.java
 * Word Search II:
 *  https://github.com/cherryljr/LintCode/blob/master/Word%20Search%20II.java
 */
class TrieTree {

    public static class TrieNode {
        public TrieNode[] child;
        // 插入/删除一个单词时，每当经过节点 node(字符), 其 path +1/-1
        // 这在我们进行 前缀个数 搜索时，非常方便
        public int path;
        // 以当前节点作为结尾的单词有几个（这边考虑了重复的单词）
        // 这个参数可以根据需求进行修改，比如在 Word Search II 我们为了方便直接使用了 String word
        public int end;

        public TrieNode() {
            this.child = new TrieNode[26];
            this.path = 0;
            this.end = 0;
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
            char[] chars = word.toCharArray();
            TrieNode currNode = root;
            int index = 0;
            for (int i = 0; i < chars.length; i++) {
                index = chars[i] - 'a';
                if (currNode.child[index] == null) {
                    currNode.child[index] = new TrieNode();
                }
                currNode = currNode.child[index];
                currNode.path++;
            }
            currNode.end++;
        }

        // 删除单词 word
        public void delete(String word) {
            if (search(word) != 0) {
                char[] chars = word.toCharArray();
                TrieNode currNode = root;
                int index = 0;
                for (int i = 0; i < chars.length; i++) {
                    index = chars[i] - 'a';
                    if (--currNode.child[index].path == 0) {
                        // 如果 node 的 path 为 0，说明可以直接移除了，
                        // 同样后面的节点也可以直接移除了，不用就遍历了
                        currNode.child[index] = null;
                        return;
                    }
                    currNode = currNode.child[index];
                }
                currNode.end--;
            }
        }

        // 查找单词 word
        public int search(String word) {
            if (word == null) {
                return 0;
            }
            char[] chars = word.toCharArray();
            TrieNode currNode = root;
            int index = 0;
            for (int i = 0; i < chars.length; i++) {
                index = chars[i] - 'a';
                if (currNode.child[index] == null) {
                    return 0;
                }
                currNode = currNode.child[index];
            }
            return currNode.end;
        }

        // 查找单词前缀 prefix
        public int prefixNumber(String prefix) {
            if (prefix == null) {
                return 0;
            }
            char[] chars = prefix.toCharArray();
            TrieNode currNode = root;
            int index = 0;
            for (int i = 0; i < chars.length; i++) {
                index = chars[i] - 'a';
                if (currNode.child[index] == null) {
                    return 0;
                }
                currNode = currNode.child[index];
            }
            return currNode.path;
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
        trie.insert("cherrya");
        trie.insert("cherryb");
        trie.insert("cherryc");
        trie.insert("cherryd");
        trie.delete("cherrya");
        System.out.println(trie.search("cherrya"));
        System.out.println(trie.prefixNumber("cherry"));
    }

}
