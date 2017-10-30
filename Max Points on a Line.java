This Question is about Mathematical Geometry 
and there are some similar questions such as Convex Polygon in LeetCode (about Cross Product).

Firstly, let's talk about mathematics.
How to determine if three points are on the same line?
The answer is to see if slopes of arbitrary two pairs are the same.

Secondly, let's see what the minimum time complexity can be.
Definitely, O(n^2). It's because you have to calculate all slopes between any two points.
Then let's go back to the solution of this problem.
In order to make this discussion simpler, let's pick a random point A as an example.
Given point A, we need to calculate all slopes between A and other points. There will be three cases:
    Some other point is the same as point A.
    Some other point has the same x coordinate as point A, which will result to a positive infinite slope.
    General case. We can calculate slope.
We can store all slopes in a hashmap. And we find which slope shows up mostly. 
Then add the number of same points to it. 
Then we know the maximum number of points on the same line for point A.
We can do the same thing to point B, point C...

Finally, just return the maximum result among point A, point B, point C...

/*
Description
Given n points on a 2D plane, find the maximum number of points that lie on the same straight line.

Example
Given 4 points: (1,2), (3,6), (0,0), (1,3).
The maximum number is 3.

Tags 
Mathematics Hash Table
*/

Approach 1: Using GCD
/**
 * Definition for a point.
 * class Point {
 *     int x;
 *     int y;
 *     Point() { x = 0; y = 0; }
 *     Point(int a, int b) { x = a; y = b; }
 * }
 */
class Solution {
    public int maxPoints(Point[] points) {
        if (points == null || points.length == 0) {
            return 0;
        }

        Map<Integer,Map<Integer,Integer>> map = new HashMap<>();  // map(x, map(y, occurrences))
        int result = 0;
        for (int i = 0; i < points.length; i++) { 
            // shared point changed, map should be cleared and server the new point
            map.clear();
            int dul = 0, max = 0;
            for (int j = i + 1; j < points.length; j++) {
                int x = points[j].x - points[i].x;
                int y = points[j].y - points[i].y;
                if (x == 0 && y == 0) {
                    dul++;
                    continue;
                }
                int gcd = generateGCD(x, y);
                if (gcd != 0) {
                    x /= gcd;
                    y /= gcd;
                }

                if (map.containsKey(x)) {
                    if (map.get(x).containsKey(y)) {
                        map.get(x).put(y, map.get(x).get(y)+1);
                    } else {
                        map.get(x).put(y, 1);
                    }   					
                } else {
                    Map<Integer,Integer> m = new HashMap<>();
                    m.put(y, 1);
                    map.put(x, m);
                }
                max = Math.max(max, map.get(x).get(y));
            }
            result = Math.max(result, max + dul + 1);
        }
        
        return result;
    }
    
    private int generateGCD(int a,int b){
        if (b == 0) {
            return a;
        }
        else {
            return generateGCD(b, a % b);
        }
    }
}
    
Approach 2: Calculating the slope
// May get the wrong answer because of the accuracy of slope.
// e.g.  [[0,0],[94911151,94911150],[94911152,94911151]]
// right answer: 2  wrong answer: 3
/**
 * Definition for a point.
 * class Point {
 *     int x;
 *     int y;
 *     Point() { x = 0; y = 0; }
 *     Point(int a, int b) { x = a; y = b; }
 * }
 */
class Solution {
    public  int maxPoints(Point[] points) {
        if (points == null || points.length == 0) {
            return 0;
        }  

        HashMap<Double, Integer> map = new HashMap<Double, Integer>();
        int max = 1;

        for(int i = 0 ; i < points.length; i++) {
            // shared point changed, map should be cleared and server the new point
            map.clear();

            // Initialize the map. Maybe all points contained in the list are same points,
            // and same points' k(slope) is represented by Integer.MIN_VALUE
            map.put((double)Integer.MIN_VALUE, 1);
            
            // calculate the number of duplicated pointers
            int dup = 0;
            for(int j = i + 1; j < points.length; j++) {
                if (points[j].x == points[i].x && points[j].y == points[i].y) {
                    dup++;
                    continue;
                }

                // look 0.0 + (double)(points[j].y-points[i].y)/(double)(points[j].x-points[i].x)
                // because map.containsKey(0.0) is different with map.containsKey(-0.0)
                // i.e. if the map contains key 0.0, but the expression map.containsKey(-0.0) will return false
                // so we should use 0.0 + -0.0 = 0.0 to solve 0.0 != -0.0 problem

                // if the line through two points are parallel to y coordinator, then K(slop) is 
                // Integer.MAX_VALUE
                double slope = points[j].x - points[i].x == 0 ? 
                    Integer.MAX_VALUE :
                    0.0 + (double)(points[j].y - points[i].y) / (double)(points[j].x - points[i].x);

                if (map.containsKey(slope)) {
                    map.put(slope, map.get(slope) + 1);
                } else {
                    map.put(slope, 2);
                }
            }

            for (int temp : map.values()) {
                // duplicate may exist
                max = Math.max(max, temp + dup);
            }
        }
        
        return max;
    }
}
