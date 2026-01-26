package com.smz.solution.greedy.n1200_minimum_absolute_difference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {
    // [法一]：贪心 + 哈希表
    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        Map<Integer, List<List<Integer>>> diffMap = new HashMap<>();
        Arrays.sort(arr);

        int min = Integer.MAX_VALUE;
        int prev = arr[0];
        for (int i = 1; i < arr.length; i++) {
            int cur = arr[i];
            int diff = Math.abs(cur - prev);
            min = Math.min(min, diff);
            diffMap.computeIfAbsent(diff, _ -> new ArrayList<>()).add(Arrays.asList(prev, cur));

            prev = cur;
        }

        return diffMap.get(min);
    }

    public static void main(String[] args) {
        int[][] testArrs = {
            {4,2,1,3},
            {1,3,6,10,15},
            {3,8,-10,23,19,-4,-14,27},
        };

        Solution s = new Solution();
        for (int i = 0; i < testArrs.length; i++) {
            System.out.println("No."+i+" test case: "+s.minimumAbsDifference(testArrs[i]));
        }
    }
}