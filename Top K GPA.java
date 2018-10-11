/*
Description
Given a List, each element in the list represents a student's StudentId and GPA.
Return the StudentId and GPA of the top K GPA,in the original order.
    1.if k > the number of students, Return all student information.
    2.Both StudentId and GPA are String.

Example
Given：
List = [["001","4.53"],["002","4.87"],["003","4.99"]]
k = 2
Return:
[["002","4.87"],["003","4.99"]]
 */

/**
 * Approach: QuickSelect
 * 寻找前k大的元素，第一反应就是使用 QuickSelect 来做
 * 但是本题要求输出的时候不能改变数据的原有顺序。
 * 而 QuickSelect 过程中的 partition 是不稳定的，
 * 因此我们只能通过 QuickSelect 来获得第 kth 的元素，
 * 然后再按照原本的数据顺序进行比较即可。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 参考资料：
 * https://github.com/cherryljr/LintCode/blob/master/Kth%20Largest%20Element.java
 *  https://github.com/cherryljr/LintCode/blob/master/Sort%20Colors.java
 */
public class Solution {
    /**
     * @param list: the information of studnet
     * @param k:
     * @return: return a list
     */
    public List<List<String>> topKgpa(List<List<String>> list, int k) {
        if (list == null || list.size() == 0) {
            return new ArrayList<>();
        }

        double[] GPAs = new double[list.size()];
        for (int i = 0; i < GPAs.length; i++) {
            GPAs[i] = Double.parseDouble(list.get(i).get(1));
        }
        double kthGPA = findKth(GPAs, 0, GPAs.length - 1, GPAs.length - k);

        List<List<String>> rst = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            double GPA = Double.parseDouble(list.get(i).get(1));
            if (GPA >= kthGPA && rst.size() < k) {
                rst.add(list.get(i));
            }
        }
        return rst;
    }

    private double findKth(double[] GPAs, int left, int right, int k) {
        if (left >= right) {
            return GPAs[left];
        }

        int position = partition(GPAs, left, right);
        if (position == k) {
            return GPAs[position];
        } else if (position < k) {
            return findKth(GPAs, position + 1, right, k);
        } else {
            return findKth(GPAs, left, position - 1, k);
        }
    }

    private int partition(double[] GPAs, int left, int right) {
        int less = left - 1, more = right;
        while (left < more) {
            if (GPAs[left] < GPAs[right]) {
                swap(GPAs, ++less, left++);
            } else if (GPAs[left] == GPAs[right]) {
                left++;
            } else {
                swap(GPAs, left, --more);
            }
        }
        swap(GPAs, more, right);

        return less + 1;
    }

    private void swap(double[] GPAs, int x, int y) {
        double temp = GPAs[x];
        GPAs[x] = GPAs[y];
        GPAs[y] = temp;
    }
}