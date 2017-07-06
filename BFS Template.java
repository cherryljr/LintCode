public class Solution {
    public ArrayList<ArrayList<Integer>> levelOrder(TreeNode root) {
        ArrayList result = new ArrayList();
        
        if (root == null)
            return result;
            
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        
        //	是否还有数需要遍历
        //  每一次循环都是在遍历一层
        while (!queue.isEmpty()) {
        	  //  level作为一个buffer储存遍历时每一层的元素
            ArrayList<Integer> level = new ArrayList<Integer>();
            //	取出size后再for一遍，这是BFS的关键点所在。
            //	循环中queue.size()是动态的，故必须在for循环之前就取好.
            int size = queue.size();
            //	利用for将该层每个点的孩子都添加到Queue中
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
