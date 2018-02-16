/*
Description
Design a data structure that supports the following two operations: addWord(word) and search(word)
search(word) can search a literal word or a regular expression string containing only letters a-z or ..
A . means it can represent any one letter.

Notice
You may assume that all words are consist of lowercase letters a-z.

Example
addWord("bad")
addWord("dad")
addWord("mad")
search("pad")  // return false
search("bad")  // return true
search(".ad")  // return true
search("b..")  // return true

Tags
Trie
 */

/**
 * Approach: Trie + DFS
 * 关于 字典树 的一个经典应用。
 * 本题之所以使用 Trie 这个数据结构的原因主要在于 '.' 这个适配符的应用。
 * 如果使用 HashMap 的话，我们很难对此进行处理。（找出相同 前缀/后缀 的单词等）
 * 因此我们使用了 Trie树 这个数据结构。
 * 得益于它的树型结构，它查找单词时是 按照前缀 一个个字符 进行查询的。
 * 因此当遇到 '.' 这个适配符时，我们能够很容易地知道这个节点下面有哪几个单词。
 * 然后我们只需要对这个节点下面的 有效分支 进行一次 DFS 即可得到 字典中含有的单词。
 * 并且使用 Trie 还为我们节约了不少额外空间。
 */
public class WordDictionary {
    TrieNode root = new TrieNode();

    /*
     * @param word: Adds a word into the data structure.
     * @return: nothing
     */
    public void addWord(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int i = c - 'a';
            if (node.child[i] == null) {
                node.child[i] = new TrieNode();
            }
            node = node.child[i];
        }
        node.isWord = true;
    }

    /*
     * @param word: A word could contain the dot character '.' to represent any one letter.
     * @return: if the word is in the data structure.
     */
    public boolean search(String word) {
        return helper(word, 0, root);
    }

    private boolean helper(String word, int index, TrieNode node) {
        if (index == word.length()) {
            return node.isWord;
        }

        char c = word.charAt(index);
        if (c == '.') {
            for (int i = 0; i < node.child.length; i++) {
                if (node.child[i] != null && helper(word, index + 1, node.child[i])) {
                    return true;
                }
            }
        } else {
            if (node.child[c - 'a'] == null) {
                return false;
            } else {
                return helper(word, index + 1, node.child[c - 'a']);
            }
        }

        return false;
    }

    class TrieNode {
        TrieNode[] child;
        boolean isWord;

        TrieNode() {
            child = new TrieNode[26];
            isWord = false;
        }
    }
}
