package com.smz.solution.sort.n56_merge_intervals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

class Solution {
    public int[][] merge(int[][] intervals) {
        ArrayList<int[]> res = new ArrayList<>();

        Arrays.sort(intervals, Comparator.comparingInt((u -> u[0])));

        for (int[] i : intervals) {
            if (res.isEmpty()) {
                res.add(i);
                continue;
            }

            int[] prev = res.getLast();
            if (i[0] <= prev[1] && i[1] > prev[1]) prev[1] = i[1];
            else if (i[0] > prev[1]) res.add(i);
        }
        
        int[][] resArr = new int[res.size()][2];
        int i = 0;
        for (int[] e : res) {
            resArr[i++] = e;
        }

        return resArr;
    }

    public static void main(String[] args) {
        int[][][] testIntervals = {
            {{1,3},{2,6},{8,10},{15,18}},
            {{1,4},{4,5}},
            {{4,7},{1,4}},
        };

        Solution s = new Solution();
        for (int i = 0; i < testIntervals.length; i++){
            System.out.print("No."+i+" test case: ");
            int[][] res = s.merge(testIntervals[i]);
            for (int[] e : res) System.out.print(Arrays.toString(e) + ' ');
            System.out.println();
        }
    }
}