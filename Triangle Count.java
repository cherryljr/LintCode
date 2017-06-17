	/**
	Given an array of integers, how many three numbers can be found in the array, 
	so that we can build an triangle whose three edges length is the three numbers 
	that we find?

	Example
	Given array S = [3,4,6,7], return 3. They are:

	[3,4,6]
	[3,6,7]
	[4,6,7]
	*/
	
	/*
	Thoughts:
	Pick 3 integers that fits the condition: 
	A + B > C
	B + C > A
	A + C > B
	If we sort the input, then we know A <= B <= C, so we can remove 2 conditoins above and only have:
	A + B > C
	That is, Pick one C, and pick two integers A,B in front. Similar to Two Sum - Input array is sorted.
	Have a fixed C as target, and find A + B > target in the remaining array on left of C. 
	How about just use 2 pointers left, right, and compare with a C (s[i] in for loop)
	Time: O(n^2)

	Note: don't forget to sort
	*/
public class Solution {
    /**
     * @param S: A list of integers
     * @return: An integer
     */
    public int triangleCount(int S[]) {
        // write your code here
        if (S == null || S.length <3)
        	return 0;
        int count = 0;
        int n = S.length;

        quicksort(S, 0, S.length-1);
        // 这里可直接调用Arrays.sort(S);直接进行快速排序，
        //	这边仅仅对快排进行重写稍作演示，之后的代码将直接调用Java已经实现的方法
				for (int i = 2; i < S.length; i++) {
					int left = 0;
					int right = i -1;
					while (left < right) {
						if (S[left] + S[right] > S[i]) {
							count += (right - left);		
							//  因为已经按从小到大的顺序排序完，故一旦S[left]+S[right]>S[i]，
							//	那么说明S中下标介于left~right的值与S[right]相加均大于S[i](C),故代表可right-left个三角形.
							//	注意：这里是一定包含S[right]这条边的意思。
							right--;
						}
						else{
							left++;
						}
					}
				}  
        
        return count;
    }
    
        void quicksort(int a[], int low, int high)  // 利用递归对分割好的数组进行排序
    {
    	int middle;
    
    	if (low >= high)
    		return ;
    	middle = split(a, low, high);
    	quicksort(a, low, middle - 1);
    	quicksort(a, middle + 1, high);
    }
    
    int split (int a[], int low, int high)  
    {
    	int part_element = a[low];
    	
    	while (low < high)                         
    	{
    		while (low < high && a[high] > part_element)  
    			high--;
    		if (low < high)
    			a[low++] = a[high];         
    			
    		while (low < high && a[low] < part_element)
    			low++;
    		if (low < high)
    			a[high--] = a[low]; 
    	}
    
    	a[low] = part_element;
    	return low;
    }
}
