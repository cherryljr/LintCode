/*
Description
Given a set of n nuts of different sizes and n bolts of different sizes.
There is a one-one mapping between nuts and bolts.
Comparison of a nut to another nut or a bolt to another bolt is not allowed.
It means nut can only be compared with bolt and bolt can only be compared with nut to see which one is bigger/smaller.
We will give you a compare function to compare nut with bolt.

Example
Given nuts = ['ab','bc','dd','gg'], bolts = ['AB','GG', 'DD', 'BC'].
Your code should find the matching bolts and nuts.
one of the possible return:
nuts = ['ab','bc','dd','gg'], bolts = ['AB','BC','DD','GG'].
we will tell you the match compare function. If we give you another compare function.
the possible return is the following:
nuts = ['ab','bc','dd','gg'], bolts = ['BC','AA','DD','GG'].
So you must use the compare function that we give to do the sorting.
The order of the nuts or bolts does not matter. You just need to find the matching bolt for each nut.

Tags
Sort Quick Sort
 */

/**
 * Approach: Two Pointers (Partition)
 * 本题实际考察的就是 QuickSort.
 * 因为排序的依据是 外部提供的比较方法 并且 同一个数组内部的元素无法互相比较。
 * 那么我们就只能选择另一个数据的元素作为 排序过程中的pivot 来进行排序。
 * 考察代码实现的题目...
 */

/**
 * public class NBCompare {
 *     public int cmp(String a, String b);
 * }
 * You can use compare.cmp(a, b) to compare nuts "a" and bolts "b",
 * if "a" is bigger than "b", it will return 1, else if they are equal,
 * it will return 0, else if "a" is smaller than "b", it will return -1.
 * When "a" is not a nut or "b" is not a bolt, it will return 2, which is not valid.
 */
public class Solution {
    /**
     * @param nuts: an array of integers
     * @param bolts: an array of integers
     * @param compare: a instance of Comparator
     * @return: nothing
     */
    public void sortNutsAndBolts(String[] nuts, String[] bolts, NBComparator compare) {
        if (nuts == null || bolts == null || nuts.length != bolts.length) {
            return;
        }

        qsort(nuts, bolts, compare, 0, nuts.length - 1);
    }

    private void qsort(String[] nuts, String[] bolts, NBComparator compare, int l, int u) {
        if (l >= u) {
            return;
        }
        // find the partition index for nuts with bolts[l]
        int part_inx = partition(nuts, bolts[l], compare, l, u);
        // partition bolts with nuts[part_inx]
        partition(bolts, nuts[part_inx], compare, l, u);
        // qsort recursively
        qsort(nuts, bolts, compare, l, part_inx - 1);
        qsort(nuts, bolts, compare, part_inx + 1, u);
    }

    int partition(String[] data, String pivot, NBComparator compare, int start, int end){
        int lo = start, hi = end;
        for (int i = start; i <= end; i++){
            if (comp(compare, data[i], pivot) == 0) {
                swap(data, lo, i);
                break;
            }
        }

        String now = data[lo];
        while (lo < hi) {
            while (lo < hi && comp(compare, data[hi], pivot) >= 0) {
                hi--;
            }
            data[lo] = data[hi];
            while (lo < hi && comp(compare, data[lo], pivot) <= 0) {
                lo++;
            }
            data[hi] = data[lo];
        }
        data[lo] = now;
        return lo;
    }

    int comp(NBComparator compare, String a, String b){
        int ans = compare.cmp(a, b);
        return ans == 2 ? -compare.cmp(b, a) : ans;
    }

    void swap(String[] data, int lo, int hi){
        String tmp = data[lo];
        data[lo] = data[hi];
        data[hi] = tmp;
    }
}