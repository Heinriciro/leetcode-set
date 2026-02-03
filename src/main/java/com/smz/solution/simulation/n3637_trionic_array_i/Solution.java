package com.smz.solution.simulation.n3637_trionic_array_i;
class Solution {
    public boolean isTrionic(int[] nums) {
        boolean isFirstDone = false;
        boolean isSecondDone = false;

        int prev = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int cur = nums[i];
            if (!isFirstDone && i != 1) {
                if (cur < prev) {
                    isFirstDone = true;
                }
                if (cur == prev) return false;
                prev = cur;
                continue;
            }

            if (isFirstDone && !isSecondDone) {
                if (cur > prev) {
                    isSecondDone = true;
                }
                if (cur == prev) return false;
                prev = cur;
                continue;
            }

            if (cur <= prev) return false;
            prev = cur;
        }

        return isFirstDone && isSecondDone;
    }

    public static void main(String[] args) {
        int[][] testNums = {
            {1,2,3},
            {2,1,3},
            {1,3,5,4,2,6},
        };

        Solution s = new Solution();
        for (int i = 0; i < testNums.length; i++) {
            System.out.println("No."+i+" test case: "+s.isTrionic(testNums[i]));
        }
    }
}