/*
Description
Give n personal friends list, tell you user, find the person that user is most likely to know.
(He and the user have the most common friends and he is not a friend of user)
n <= 500.
The relationship between friends is mutual. (if B appears on a's buddy list, a will appear on B's friends list).
Each person's friend relationship does not exceed m, m <= 3000.
If there are two people who share the same number of friends as user, the smaller number is considered the most likely person to know.
If user and all strangers have no common friends, return -1.

Example
Given list = [[1,2,3],[0,4],[0,4],[0,4],[1,2,3]], user = 0, return 4.
Explanation:
0 and 4 are not friends, and they have 3 common friends. So 4 is the 0 most likely to know.

Given list = [[1,2,3,5],[0,4,5],[0,4,5],[0,5],[1,2],[0,1,2,3]], user = 0, return 4.
Explanation:
Although 5 and 0 have 3 common friends, 4 and 0 only have 2 common friends, but 5 is a 0's fri
 */

/**
 * Approach: HashSet
 * 一开始以为会使用到很好玩的解法...结果看了下测试数据...
 * 直接 HashSet 存下来比较就可以了，完全不会超时...
 */
public class Solution {
    /**
     * @param friends: people's friends
     * @param user: the user's id
     * @return: the person who most likely to know
     */
    public int recommendFriends(int[][] friends, int user) {
        Set<Integer> friendSet = new HashSet<>();
        // 用Set来记录 user 的朋友，以便于进行对比
        for (int i = 0; i < friends[user].length; i++) {
            friendSet.add(friends[user][i]);
        }

        int rst = -1, maxCount = 0;
        for (int i = 0; i < friends.length; i++) {
            if (i == user || friendSet.contains(i)) {
                continue;
            }

            int count = 0;
            // 每当有一个共同朋友，count++
            for (int j = 0; j < friends[i].length; j++) {
                if (friendSet.contains(friends[i][j])) {
                    count++;
                }
            }
            // 比较 count 与 maxCount，必要的话，对结果进行更新
            // 如果有两个人和user的共同好友数目一样，则取编号较小的
            if (count > maxCount) {
                rst = i;
                maxCount = count;
            } else if (count == maxCount && rst > i) {
                rst = i;
            }
        }

        return rst;
    }
}