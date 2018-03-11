/*
Description
Design an algorithm and write code to serialize and deserialize a binary tree. 
Writing the tree to a file is called 'serialization' and reading back from the file to reconstruct the exact same binary tree is 'deserialization'.

Notice
There is no limit of how you deserialize or serialize a binary tree, 
LintCode will take your output of serialize as the input of deserialize, it won't check the result of serialize.

Example
An example of testdata: Binary tree {3,9,20,#,#,15,7}, denote the following structure:
  3
 / \
9  20
  /  \
 15   7
Our data serialization use bfs traversal. This is just for when you got wrong answer and want to debug the input.
You can use other method to do serializaiton and deserialization.

Tags 
Binary Tree Uber Yahoo Microsoft
*/

/**
 * Serialize means we need to convert the tree into a String to restore it.
 * But we can design our own algorithm to serialize a binary tree to a string and
 * guarantee that it could be deserialize.
 * So we support two algorithms here.
 *  1. Level Order Traversal / BFS
 *  2. PreOrder Traversal
 */

/**
 * Approach 1: Level Order Traversal (Using Queue)
 * For serializaiton, it just like binary tree level order traversal.
 * We can write it just like it.(or BFS Template).
 * https://github.com/cherryljr/LintCode/blob/master/BFS%20Template.java
 * We use "#" to denote null node and split node with " ", and use a StringBuilder to store the traversal result.
 * For deserialization, we use a Queue to store the level-order traversal and since we have "#" as null node,
 * so we know exactly how to where to end building subtress.
 */
 
 /**
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int val;
 *     public TreeNode left, right;
 *     public TreeNode(int val) {
 *         this.val = val;
 *         this.left = this.right = null;
 *     }
 * }
 */
public class Solution {
    /**
     * This method will be invoked first, you should design your own algorithm
     * to serialize a binary tree which denote by a root node to a string which
     * can be easily deserialized by your own "deserialize" method later.
     */
    public String serialize(TreeNode root) {
        if (root == null) {
            return null;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                sb.append("# ");    // "#" denote the null nodes.
                continue;
            }
            sb.append(node.val + " ");  // Using space to split nodes.
            queue.offer(node.left);
            queue.offer(node.right);
        }

        return sb.toString();
    }

    /**
     * This method will be invoked second, the argument data is what exactly
     * you serialized at method "serialize", that means the data is not given by system,
     * it's given by your own serialize method. So the format of data is
     * designed by yourself, and deserialize it here as you serialize it in
     * "serialize" method.
     */
    public TreeNode deserialize(String data) {
        if (data == null || data.length() == 0) {
            return null;
        }

        String[] values = data.split(" ");
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.parseInt(values[0]));
        queue.offer(root);

        for (int i = 1; i < values.length; i++) {
            TreeNode parent = queue.poll();
            if (!values[i].equals("#")) {
                TreeNode left = new TreeNode(Integer.parseInt(values[i]));
                parent.left = left;
                queue.offer(left);
            }
            if (!values[++i].equals("#")) {
                TreeNode right = new TreeNode(Integer.parseInt(values[i]));
                parent.right = right;
                queue.offer(right);
            }
        }

        return root;
    }
}

/**
 * Approach 2: PreOrder Traversal (Using Stack)
 * For serializaiton, it just like binary tree preorder traversal.
 * We can write it just like it.
 * https://github.com/cherryljr/LintCode/blob/master/Binary%20Tree%20Preorder%20Traversal.java
 * We use "#" to denote null node and split node with " ", and use a StringBuilder to store the traversal result.
 * For deserialization, we use a Queue to store the pre-order traversal and since we have "#" as null node,
 * so we know exactly how to where to end building subtress.
 */
public class Solution {
    /**
     * This method will be invoked first, you should design your own algorithm
     * to serialize a binary tree which denote by a root node to a string which
     * can be easily deserialized by your own "deserialize" method later.
     */
    public String serialize(TreeNode root) {
        if (root == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
//        buildString(root, sb);
//        return sb.toString();
        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            if (curr == null) {
                sb.append("# ");
                continue;
            }
            sb.append(curr.val + " ");
            stack.push(curr.right);
            stack.push(curr.left);
        }

        return sb.toString();
    }

    // Traverse it (Using recursion)
    private void buildString(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append("# ");
        } else {
            sb.append(node.val + " ");
            buildString(node.left, sb);
            buildString(node.right,sb);
        }
    }

    /**
     * This method will be invoked second, the argument data is what exactly
     * you serialized at method "serialize", that means the data is not given by
     * system, it's given by your own serialize method. So the format of data is
     * designed by yourself, and deserialize it here as you serialize it in
     * "serialize" method.
     */
    public TreeNode deserialize(String data) {
        if (data == null || data.length() == 0) {
            return null;
        }

        Deque<String> nodes = new LinkedList<>();
        nodes.addAll(Arrays.asList(data.split(" ")));
        return buildTree(nodes);
    }

    private TreeNode buildTree(Deque<String> nodes) {
        String val = nodes.remove();
        if (val.equals("#")) {
            return null;
        }
        else {
            TreeNode node = new TreeNode(Integer.parseInt(val));
            node.left = buildTree(nodes);
            node.right = buildTree(nodes);
            return node;
        }
    }
}