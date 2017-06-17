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
public class Solution {
    /**
     * @param S: A list of integers
     * @return: An integer
     */
    public int triangleCount(int S[]) {
        // write your code here
        int count = 0;
        int n = S.length;

        quicksort(S, 0, S.length-1);
        for (int i = 0;i<n;i++)  
        {  
              
            for (int j=i+1; j<n; j++)  
            {  
                int l = i + 1;  
                int r = j;  
                int target = S[j] - S[i];  
                while(l < r)  
                {  
                    int mid = (l + r) / 2;  
                    if(S[mid] > target)  
                        r = mid;  
                    else  
                        l = mid + 1;  
                }  
                count += (j - l);  
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
