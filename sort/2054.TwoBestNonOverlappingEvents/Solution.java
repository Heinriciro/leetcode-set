import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Solution {

    // [法二]：时间戳排序，官方题解。非常妙
    public int maxTwoEvents(int[][] events) {
        List<Event> evs = new ArrayList<>();
        for (int[] event : events) {
            evs.add(new Event(event[0], 0, event[2]));
            evs.add(new Event(event[1], 1, event[2]));
        }
        Collections.sort(evs);
        int ans = 0, bestFirst = 0;
        for (Event event : evs) {
            if (event.op == 0) {
                ans = Math.max(ans, event.val + bestFirst);
            } else {
                bestFirst = Math.max(bestFirst, event.val);
            }
        }
        return ans;
    }
    
    class Event implements Comparable<Event> {
        int ts;
        int op;
        int val;
        
        Event(int ts, int op, int val) {
            this.ts = ts;
            this.op = op;
            this.val = val;
        }
        
        @Override
        public int compareTo(Event other) {
            if (this.ts != other.ts) {
                return Integer.compare(this.ts, other.ts);
            }
            return Integer.compare(this.op, other.op);
        }
    }


    // [法一]：排序 + 二叉搜索 + DP思路
    // 将事件按结束时间从小到大排序，结束时间相同时，仅保留值最大的
    // 利用DP思想，枚举每一个排序后的事件，记录这个事件以及比这个事件结束更早的事件中最大的值
    // 再次枚举每一个事件，根据其起始时间，在前面排序后的事件中使用二叉搜索，搜索对应的事件，从dp中获取这一事件及之前所有事件的最大值，将当前枚举事件的值和之前事件最大值相加，即为该合法组合的值
    // 从中寻找最大值即可
    public int maxTwoEvents1(int[][] events) {
        int res = 0;
        HashMap<Integer, Integer> m = new HashMap<>();

        for (int i = 0; i < events.length; i++) {
            int[] curE = events[i];
            int endTime = curE[1];
            int value = curE[2];
            res = Math.max(res, value);

            if (!m.containsKey(endTime) || value > events[m.get(endTime)][2]) {
                m.put(endTime, i);
            }
        }

        Integer[] sortedEIdx = m.values().toArray(new Integer[m.size()]);
        Arrays.sort(sortedEIdx, (a, b) -> events[a][1] - events[b][1]);
        HashMap<Integer, Integer> biggestValue = new HashMap<>();
        Integer ans = 0;
        for (Integer idx : sortedEIdx) {
            ans = Math.max(ans, events[idx][2]);
            biggestValue.put(idx, ans);
        }

        for (int i = 0; i < events.length; i++) {
            int prevIdx = Arrays.binarySearch(sortedEIdx, i, (Integer o1, Integer o2) -> events[o1][1] - (events[o2][0] - 1));

            if (prevIdx < 0) prevIdx = -prevIdx - 1 - 1;
            if (prevIdx < 0 || sortedEIdx[prevIdx] == i) continue;

            res = Math.max(res, events[i][2] + biggestValue.get(sortedEIdx[prevIdx]));
        }

        return res;
    }

    public static void main(String[] args) {
        int[][][] testEvents = {
            {{10,83,53},{63,87,45},{97,100,32},{51,61,16}},
            {{1,3,2},{4,5,2},{2,4,3}},
            {{1,3,2},{4,5,2},{1,5,5}},
            {{1,5,3},{1,5,1},{6,6,5}},
        };

        Solution s = new Solution();
        for (int i=0; i < testEvents.length; i++) {
            System.out.println("No."+i+" test case: "+s.maxTwoEvents(testEvents[i]));
        }
    }
}