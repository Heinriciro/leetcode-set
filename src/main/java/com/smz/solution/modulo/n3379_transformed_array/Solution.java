package com.smz.solution.modulo.n3379_transformed_array;
import java.util.Arrays;

class Solution {
    // [法一]：遍历 + 模运算
    public int[] constructTransformedArray(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            int target = i + nums[i];
            while (target < 0) {
                target += n;
            }
            while (target >= n) {
                target -= n;
            }

            ans[i] = nums[target];
        }

        return ans;
    }

    public static void main(String[] args) {
        int[][] testNums = {
            {3,-2,1,1},
            {-1,4,-1},
            {2,2,2,2,2},
        };

        Solution s = new Solution();
        for (int i = 0; i < testNums.length; i++) {
            System.out.println("No." + i + " test case: " + Arrays.toString(s.constructTransformedArray(testNums[i])));
        }
    }
}