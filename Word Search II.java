/*
Description
Given a matrix of lower alphabets and a dictionary.
Find all words in the dictionary that can be found in the matrix.
A word can start from any position in the matrix and go left/right/up/down to the adjacent position.

Example
Given matrix:
doaf
agai
dcan
and dictionary:
{"dog", "dad", "dgdg", "can", "again"}

return {"dog", "dad", "can", "again"}

dog:
doaf
agai
dcan

dad:
doaf
agai
dcan

can:
doaf
agai
dcan

again:
doaf
agai
dcan

Challenge
Using trie to implement your algorithm.

Tags
LintCode Copyright Trie Airbnb
 */

/**
 * Approach 1: Trie + Backtracking
 * 在 Word Search 中我们只需要使用 DFS/Backtracking 就能够解决问题.
 * 但是在 Follow Up 中，原本 对于一个单词的查找 变成了 对于一个词典的查询。
 * 因此我们必须想出一个 合适的数据结构 来储存词典中的单词，
 * 以便我们进行 DFS 的时候来查询当前 DFS 得到的单词是否在词典中（是否有效）。
 * 这里我们使用了 Trie树.
 * 原因是：DFS是按照 一个个字符 进行遍历查找的，因此对于一个字符串我们是 按照前缀 一个个字符 进行遍历的，
 * 这时候 Trie树 会比 HashMap 有着更好的效果。
 * 因为当遍历字符串中的 下一个字符 时，Trie 只需要检查一个字符即可 O(1)，时间复杂度更低，
 * 而 HashMap 需要遍历整个字符串 O(n)，计算出 HashCode 用于查找。
 * 并且 Tire 更加节省空间。因为 Trie树 可以对 公共前缀 的单词进行合并。
 *
 * 具体操作：
 * 利用 词典中的单词 构建 Trie树。
 * 利用 DFS 搜寻 board, 将 DFS 得到的单词在 Trie 进行查询，看是否是词典中的单词（有效）
 *
 * Note：
 * 为了大家能够更加直观地学习 Trie树，该部分的代码书写会尽量 简明直观。
 * 在充分理解了 Trie树 的数据结构和使用方法后，大家可以看 Approach 2 的优化版写法。
 */
public class Solution {
    Set<String> rst = new HashSet<>();
    
    /*
     * @param board: A list of lists of character
     * @param words: A list of string
     * @return: A list of string
     */
    public List<String> wordSearchII(char[][] board, List<String> words) {
        if (words == null || words.size() == 0) {
            return new ArrayList<>();
        }

        // 根据词典建立 Trie树
        Trie trie = new Trie();
        for (String word : words) {
            trie.insert(word);
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs(board, i, j, "", trie);
            }
        }

        return new ArrayList<>(rst);
    }
    
    private void dfs(char[][] board, int i, int j, String str, Trie trie) {
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] == '#') {
            return;
        }

        str += board[i][j];
        // if we can't find the prefix equals to str in the trie,
        // it means that we can't find str in dictionary
        if (!trie.startsWith(str)) {
            return;
        }
        // if the str could be find in the trie, add it to the rst
        if (trie.search(str)) {
            rst.add(str);
        }

        char temp = board[i][j];
        board[i][j] = '#';
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : dirs) {
            dfs(board, i + dir[0], j + dir[1], str, trie);
        }
        board[i][j] = temp;
    }

    class TrieNode {
        TrieNode[] child;
        boolean isWord; // 标志该字符可以作为单词的结尾
        TrieNode() {
            child = new TrieNode[26];
            isWord = false;
        }
    }

    class Trie {
        TrieNode root;
        Trie() {
            root = new TrieNode();
        }

        // Inserts a word into the trie.
        public void insert(String word) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                if (node.child[c - 'a'] == null) {
                    node.child[c - 'a'] = new TrieNode();
                }
                node = node.child[c - 'a'];
            }
            node.isWord = true;
        }

        // Returns if the word is in the trie.
        public boolean search(String word) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                if (node.child[c - 'a'] == null) {
                    return false;
                }
                node = node.child[c - 'a'];
            }
            return node.isWord;
        }

        // Returns if there is any word in the trie
        // that starts with the given prefix.
        public boolean startsWith(String prefix) {
            TrieNode node = root;
            for (char c : prefix.toCharArray()) {
                if (node.child[c - 'a'] == null) {
                    return false;
                }
                node = node.child[c - 'a'];
            }
            return true;
        }
    }
}

/**
 * Approach 2: Trie + Backtracking
 * 解题方法与用到的数据结构是完全相同的，但是对 Approach 1 中的代码进行了一定的优化。
 * 优化点：
 *  1. 移除 Trie 这个类，我们不需要每次 DFS 都从 root 节点开始。
 *  2. 每次进行 DFS 之前，判断条件的合理性，降低函数调用消耗的时间。如 if (i > 0) dfs(...)
 *  3. 修改 TrieNode 中的 isWord 参数，将其更换为 String 类型的 word,
 *  用于直接记录 以当前字符作为结尾的单词 word.从而完全替代我们每次 DFS 时需要传递的字符串 str.
 *  4. 不使用 Set 来进行去重，因为 Trie树 本身就具备去重的功能。
 *  具体实现为：在将一个单词 add 到结果中后，将当前结点的 word 参数置为 null.
 *  这样使得下一次再次查找到该单词时会因为 该节点的word为null 而认为该单词并不在词典中，从而达到去重的目的。
 *  
 * Note:
 * 第二点虽然改了之后会省不少时间，但是与自己的习惯写法不同。
 * 考虑了下就不把这个代码放上来了，想看的可以参见：
 *  https://leetcode.com/problems/word-search-ii/discuss/59780/Java-15ms-Easiest-Solution-(100.00)
 */
public class Solution {
    List<String> rst = new ArrayList<>();

    /*
     * @param board: A list of lists of character
     * @param words: A list of string
     * @return: A list of string
     */
    public List<String> wordSearchII(char[][] board, List<String> words) {
        if (words == null || words.size() == 0) {
            return rst;
        }

        TrieNode root = buildTrie(words);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs (board, i, j, root);
            }
        }

        return rst;
    }

    public void dfs(char[][] board, int i, int j, TrieNode node) {
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] == '#') {
            return;
        }

        // Search the next character of word in Trie
        // form node directly (not from root, which will save time)
        char c = board[i][j];
        if (node.child[c - 'a'] == null) {
            return;
        }
        node = node.child[c - 'a'];
        // found a word in the trie
        if (node.word != null) {
            rst.add(node.word);
            // remove duplicate word
            node.word = null;
        }

        board[i][j] = '#';
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : dirs) {
            dfs(board, i + dir[0], j + dir[1], node);
        }
        // Backtracking
        board[i][j] = c;
    }

    public TrieNode buildTrie(List<String> words) {
        TrieNode root = new TrieNode();
        for (String word : words) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int i = c - 'a';
                if (node.child[i] == null) {
                    node.child[i] = new TrieNode();
                }
                node = node.child[i];
            }
            node.word = word;
        }
        return root;
    }

    class TrieNode {
        TrieNode[] child;
        String word;

        TrieNode() {
            child = new TrieNode[26];
            word = null;
        }
    }
}