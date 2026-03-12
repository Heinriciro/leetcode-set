package com.smz.solution.dynamic_programming.n53_maximum_subarray;
class Solution {
    // [法二]：分治
    public int maxSubArray(int[] nums) {
    }
    // [法一]：DP
    public int maxSubArray1(int[] nums) {
        int n = nums.length;
        int res = Integer.MIN_VALUE;
        int[] dp = new int[n+1];

        for (int i = 0; i < n; i++) {
            dp[i + 1] = Math.max(dp[i] + nums[i], nums[i]);
            res = Math.max(res, dp[i + 1]);
        }

        return res;
    }

    public static void main(String[] args) {
        int[][] testNums = {
            {-2,1,-3,4,-1,2,1,-5,4},
            {1},
            {5,4,-1,7,8},
        };

        Solution s = new Solution();
        for (int i = 0 ; i < testNums.length; i++) {
            System.out.println("No." + i + " test case:" + s.maxSubArray(testNums[i]));
        }
    }
}