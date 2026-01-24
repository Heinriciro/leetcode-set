package com.smz.solution.greedy.n1877_minimize_maximum_pair_sum_in_array;
import java.util.Arrays;

public class Solution {
    // 法一： 排序 + 贪心
    //      将数组从小到大排序，使最大值和最小值成对，能够使得总体的最大值最小
    public int minPairSum(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;

        int ans = 0;
        for (int i = 0; i < n / 2; i++) {
            ans = Math.max(ans, nums[i]+nums[n - 1 - i]);
        }
        
        return ans;
    }

    public static void main(String[] args) {
        int[][] testNums = {
            {4,1,5,1,2,5,1,5,5,4},
            {3,5,2,3},
            {3,5,4,2,4,6},
        };

        Solution s = new Solution();
        for(int i = 0; i < testNums.length; i++){
            System.out.println("No."+i+" test case: "+s.minPairSum(testNums[i]));
        }
    }
}