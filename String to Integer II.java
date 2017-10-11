There is no doubt that there is so many input cases...(test case is very, very...)
This question is often meeted in interview, but in OJ, we can't talk with the interviewer... sad.. Just try it!

Conclusion:
We only need to handle four cases:
	1. discards all leading whitespaces
	2. sign of the number
	3. overflow
	4. invalid input
You can watch the code and learn how to do it with the notes.

/*
Description
Implement function atoi to convert a string to an integer.
If no valid conversion could be performed, a zero value is returned.
If the correct value is out of the range of representable values, INT_MAX (2147483647) or INT_MIN (-2147483648) is returned.

Example
"10" => 10
"-1" => -1
"123123123123123" => 2147483647
"1.0" => 1

Tags 
Basic Implementation String Uber
*/

class Solution {	
    /*
     * @param str: A string
     * @return: An integer
     */
    public int atoi(String str) {
    	// Deal with the empty string
    	if (str == null || str.length() == 0) {
    		return 0;
    	}
    	
    	// Remove the spaces
    	str = str.trim();
    	
    	char firstChar = str.charAt(0);
    	int sign = 1, start = 0, len = str.length();
    	long sum = 0;
    	// Handle the signs
    	if (firstChar == '+') {
    		sign = 1;
    		start++;	// remember to move the index to the next position
    	} else if (firstChar == '-') {
    		sign = -1;
    		start++;	// remember to move the index to the next position
    	}
    	
    	// Convert to number and avoid overflow
    	for (int i = start; i < len; i++) {
    		if (!Character.isDigit(str.charAt(i))) {
    			return (int) sum * sign;	
    		}
    		sum = sum * 10 + str.charAt(i) - '0';
    		if (sign == 1 && sum > Integer.MAX_VALUE) {
    			return Integer.MAX_VALUE;	
    		}
    		if (sign == -1 && (-1) * sum < Integer.MIN_VALUE) {
    			return Integer.MIN_VALUE;	
    		}
    	}

    	return (int) sum * sign;
    }
}