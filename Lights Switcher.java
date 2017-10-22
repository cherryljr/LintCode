该题考察的是数学分析的能力。
突破口在于：
    1. 按钮的冗余性，按按钮的顺序是可以交换而不改变结果的
    （如果把灯泡状态看成是一个二进制数的话，按按钮相当于按位异或某个数，而异或是满足交换律的）
    2. 同一个按钮按多次其实只有奇偶之分，也就是只有按与不按之分（连续按两次相当于不按，连续按三次又相当于按1次）
由以上两点我们可以发现：
    按钮1、按钮2、按钮3中的其中两个是可以替代另一个的（或称线性相关），
    也就是说按这三个按钮中的任意两个按钮一下，相当于按另一个按钮一下。
    因此，4个按钮中有效的只有3个，3个按钮各自按与不按有两种情况，最后可能的状态数不会超过2^3=8种。
    即：不按, 按1， 按2， 按3， 按4， 按1+4, 按2+4, 按3+4
    而且这八种按按钮的方案最后得到的灯的状态（在灯足够多的情况下）是各不相同的。
    即当 m>=3 时，这8种可能都是可以实现的。

这样一来就可以得到相对合理的算法：
    如果 m>=3, 就枚举按钮2,3,4（前3个按钮可任取两个）按与不按得到八种情况，看这八种情况中不重复的有几种；
    如果 m<3, 可以直接枚举按每一次按的是4个中的哪一个，然后判重得到答案。
1. m>=3 时,总共有8种可能，即000000、001110、010101、011011、100100、101010、110001、111111。
    如果 n=1, 可以只看第1位，有0和1两种可能；
    如果 n=2, 看前两位，有00、01、10、11四种可能；
    如果 n>=3, 所有8种可能都互不相同。

2. m=1 时,按一次开关有4种可能，且这4种可能各不相同：111111、010101、101010、100100。
    如果n=1, 看第1位，有0和1两种可能；
    如果n=2, 看前两位，最后两种是一样的，有11、01、10三种可能；
    如果n>=3, 那么就有所有4种可能。

3. m=2 时,按两次开关有16种可能，但不同的只有7种：000000、001110、010101、011011、101010、110001、111111。
    如果n=1, 看第一位，有0和1两种可能；
    如果n=2, 看前两位，有00、01、10、11四种可能；
    如果n>=3, 所有7种可能都互不相同。
    
/*
Description
There is a room with n lights which are turned on initially and 4 buttons on the wall. 
After performing exactly m unknown operations towards buttons, 
you need to return how many different kinds of status of the n lights could be.

Suppose n lights are labeled as number [1, 2, 3 ..., n], function of these 4 buttons are given below:
You can flip all the lights.
You can flip lights with even numbers.
You can flip lights with odd numbers.
You can flip lights with (3k + 1) numbers, k = 0, 1, 2, ...

Example
Given n = 1, m = 1.
return 2 // Status can be: [on], [off]

Given n = 2, m = 1.
return 3 // Status can be: [on, off], [off, on], [off, off]

Tags 
Mathematics
*/

public class Solution {
    /*
     * @param : number of lights
     * @param : number of operations
     * @return: the number of status
     */
    public int flipLights(int n, int m) {
        if (m == 1) {
            if (n == 2) {
                return 3;
            }
            return Math.min(4, 1 << n);
        }
        
        if (m == 2) {
            return Math.min(7, 1 << n);
        }
        
        // m >= 3
        // Math.min(m, n)是担心左移使得int越界，变成负数。
        return Math.min(8, 1 << Math.min(m, n));
    }
};