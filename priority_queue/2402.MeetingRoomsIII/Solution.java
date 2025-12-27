import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Solution {
    // [法一]：优先队列+模拟
    public int mostBooked(int n, int[][] meetings) {
        // 按照会议开始时间排序, 方便遍历
        Arrays.sort(meetings, Comparator.comparingInt(o -> o[0]));
        // 存储可用的房间， 按序号从小到大取出
        PriorityQueue<Integer> availableRoom = new PriorityQueue<>();
        for (int i = 0; i < n; i++) availableRoom.offer(i);
        // 存储被已被占用的房间以及其中会议的结束时间，按照时间从小到大取出
        PriorityQueue<long[]> usedRoom = new PriorityQueue<>(Comparator.comparingLong(o -> o[0]));

        int[] roomCount = new int[n];
        long curTime = 0;

        for (int[] meeting : meetings) {
            // 时间进行至下一个会议开始的时间
            if (curTime < meeting[0]) curTime = meeting[0];
            // 释放期间结束的会议室
            while (!usedRoom.isEmpty() && usedRoom.peek()[0] <= curTime) {
                long[] out = usedRoom.poll();
                availableRoom.offer((int) out[1]);
            }
            // 若仍没有可用会议室，则时间推进至最近一个会议完成的时间
            if (availableRoom.isEmpty()) {
                curTime = usedRoom.peek()[0];
                 while (!usedRoom.isEmpty() && usedRoom.peek()[0] <= curTime) {
                    availableRoom.offer((int)usedRoom.poll()[1]);
                }
            }

            int targetRoom = availableRoom.poll();

            // 将当前会议安排进入最小可用会议室
            usedRoom.offer(new long[]{curTime + ((long) meeting[1] - (long) meeting[0]), targetRoom});
            roomCount[targetRoom]++;
        }

        int maxCount = 0;
        int res = 0;
        for (int i = 0; i < n; i++) {
            int count = roomCount[i];
            if (count > maxCount) {
                maxCount = count;
                res = i;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int[][][] testMeetings = {
            {{0,10},{1,5},{2,7},{3,4}},
            {{1,20},{2,10},{3,5},{4,9},{6,8}},
        };
        int[] testNs = {2,3};

        Solution s = new Solution();
        for (int i = 0; i < testMeetings.length; i++) {
            System.out.println("No."+i+" test case: "+s.mostBooked(testNs[i], testMeetings[i]));
        }
    }
}