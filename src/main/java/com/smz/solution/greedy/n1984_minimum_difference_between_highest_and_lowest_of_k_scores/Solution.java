package com.smz.solution.greedy.n1984_minimum_difference_between_highest_and_lowest_of_k_scores;
import java.util.Arrays;

public class Solution {
    // [法一]：排序 + 贪心
    public int minimumDifference(int[] nums, int k) {
        Arrays.sort(nums);
        int left = 0;
        int right = k-1;

        int ans = Integer.MAX_VALUE;
        while (right < nums.length) {
            ans = Math.min(ans, nums[right] - nums[left]);
            left++;
            right++;
        }

        return ans;
    }

    public static void main(String[] args) {
        int[][] testNums = {
            {90},
            {9,4,1,7},
        };
        int[] testKs = {1,2};
        
        Solution s = new Solution();
        for (int i = 0; i < testKs.length; i++) {
            System.out.println("No."+i+" test case: "+s.minimumDifference(testNums[i], testKs[i]));
        }
    }
}