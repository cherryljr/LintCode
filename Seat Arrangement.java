/*
Description
There are a row of n seats, numbered from 0 to n-1. Every time someone may be seated, someone may leave from the seat.
It should be guaranteed that the distance between each people is the greatest all the time.
If there is more than one position that meet the requirement, choose the leftmost one.
Given an array arr, if the item is positive, it means that the person of this number is seated.
At this time, an arrangement seat should be added to the answer array ans, if there is no seat left, add 1.
If the item is negative, it means that the person with the absolute number has left.
At this time, if the numbered person is in the seat, add 1 to ans, otherwise add -1.

The number of seats and the number of people do not exceed 500.
The number of all persons is a positive integer, and no one has the same number.

Example
Given n=10, arr=[1,2,3,4,-3,5], return [0,9,4,2,1,5].
Explanation:
There are 10 seats, 0 means the seat is free, otherwise it means the people of this number is sat.
At the beginning
0 0 0 0 0 0 0 0 0 0
After Add(1) :
1 0 0 0 0 0 0 0 0 0
After Add(2) :
1 0 0 0 0 0 0 0 0 2
After Add(3) :
1 0 0 0 3 0 0 0 0 2
(4 and 5 can be selected, but 4 is the leftmost)
After Add(4) :
1 0 4 0 3 0 0 0 0 2
(2 and 6 can be selected, but 4 is the leftmost)
After Remove(3):
1 0 4 0 0 0 0 0 0 2
After Add(5) :
1 0 4 0 0 5 0 0 0 2

Give n=2, arr=[1,2,3,-4], return [0,1,-1,-1].
Explanation:
When the third person came, there was no seat.
There is no person with the number 4 in the seats.
 */

/**
 * Approach: HashMap + TreeSet (Simulation)
 */
public class Solution {
    /**
     * @param n: The number of seats
     * @param arr: The number of peaple come or leave
     * @return: The answer array
     */
    // 客人编号以及对应的座位位置编号
    Map<Integer, Integer> peoplePos = new HashMap<>();
    // 已经被使用了的座位编号
    TreeSet<Integer> seatedSet = new TreeSet<>();

    public List<Integer> arrangeSeat(int n, List<Integer> arr) {
        List<Integer> rst = new ArrayList<>();
        for (int peopleId : arr) {
            // People come in
            if (peopleId > 0) {
                // The seat is full
                if (seatedSet.size() == n) {
                    rst.add(-1);
                } else {
                    // 选定的座位
                    int seatIndex = seat(peopleId, n);
                    rst.add(seatIndex);
                }
            } else {
                // People leave the seat
                peopleId = -peopleId;
                if (peoplePos.containsKey(peopleId)) {
                    leave(peopleId);
                    rst.add(1);
                } else {
                    rst.add(-1);
                }
            }
        }
        return rst;
    }

    /**
     * @param peopleId 客人的编号，正数表示需要一个座位，负数表示离开
     * @param n 总共的座位数
     * @return 最后选定的座位位置
     */
    private int seat(int peopleId, int n) {
        int seatPos = 0;
        int maxDis = 0;
        if (seatedSet.isEmpty()) {
            seatedSet.add(seatPos);
        } else {
            if (seatedSet.first() - 0 > maxDis) {
                maxDis = seatedSet.first() - 0;
                seatPos = 0;
            }

            Iterator<Integer> it = seatedSet.iterator();
            Integer pre = null;
            while (it.hasNext()) {
                int index = it.next();
                if (pre == null) {
                    pre = index;
                } else {
                    int dis = (index - pre) >> 1;
                    if (dis > maxDis) {
                        maxDis = dis;
                        seatPos = pre + dis;
                    }
                    pre = index;
                }
            }

            if (n - 1 - seatedSet.last() > maxDis) {
                seatPos = n - 1;
            }
        }

        seatedSet.add(seatPos);
        peoplePos.put(peopleId, seatPos);
        return seatPos;
    }

    private void leave(int peopleId) {
        seatedSet.remove(peoplePos.get(peopleId));
        peoplePos.remove(peopleId);
    }

}