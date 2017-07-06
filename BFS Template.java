public class Solution {
    public ArrayList<ArrayList<Integer>> levelOrder(TreeNode root) {
        ArrayList result = new ArrayList();
        
        if (root == null)
            return result;
            
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        
        //	是否还有数需要遍历
        while (!queue.isEmpty()) {
            ArrayList<Integer> level = new ArrayList<Integer>();
            //	取出size后再for一遍，这是BFS的关键点所在
            int size = queue.size();
            //	将下一层的数添加到队列中
            for (int i = 0; i < size; i++) {
                TreeNode head = queue.poll();
                level.add(head.val);
                if (head.left != null)
                    queue.offer(head.left);
                if (head.right != null)
                    queue.offer(head.right);
            }
            result.add(level);
        }
        
        return result;
    }
}
