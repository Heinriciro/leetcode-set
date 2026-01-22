package com.smz.solution.simulation.n3507_minimum_pair_removal_sort_array_i;
import java.util.LinkedList;
import java.util.List;

public class Solution {
    // 分析：注意题目的约束，数组长度1~50，数组元素值-1000~1000，存在负数情况
    //       由于数据量小，可以采用暴力模拟

    // [法一]：模拟 + 暴力
    public int minimumPairRemoval(int[] nums) {
        List<Integer> numList = new LinkedList<>();
        int prev = Integer.MIN_VALUE;
        int dec = 0;
        for (int num : nums) {
            numList.add(num);
            if (num < prev) dec++;
            prev = num;
        }

        int ans = 0;
        while (dec > 0) {
            dec = process(numList, dec);
            ans++;
        }

        return ans;
    }

    private int process(List<Integer> nums, int dec) {
        int minIdx = 0;
        int minSum = Integer.MAX_VALUE;

        for (int i = 0; i < nums.size() - 1; i++) {
            int curSum = nums.get(i) + nums.get(i+1);
            if (curSum < minSum) {
                minIdx = i;
                minSum = curSum;
            }
        }

        int prevNum = minIdx == 0 ? Integer.MIN_VALUE : nums.get(minIdx - 1);
        int curNum1 = nums.get(minIdx);
        int curNum2 = nums.get(minIdx + 1);
        int nextNum = minIdx == nums.size() - 2 ? Integer.MAX_VALUE : nums.get(minIdx + 2);

        nums.set(minIdx, minSum);
        nums.remove(minIdx + 1);

        if (curNum1 > curNum2) dec--;

        if (prevNum <= curNum1 && prevNum > minSum) dec++;
        else if (prevNum > curNum1 && prevNum <= minSum) dec--;

        if (nextNum >= curNum2 && nextNum < minSum) dec++;
        else if (nextNum < curNum2 && nextNum >= minSum) dec--;
        
        return dec;
    } 

    public static void main(String[] args) {
        int[][] testNums = {
            {2,2,-1,3,-2,2,1,1,1,0,-1},
            {5,2,3,1},
            {1,2,2},
        };

        Solution s = new Solution();
        for (int i = 0; i < testNums.length; i++) {
            System.out.println("No."+i+" test case: "+s.minimumPairRemoval(testNums[i]));
        }
    }
}