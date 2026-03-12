package com.smz.solution.math_derivation.n189_rotate_array;
import java.util.Arrays;

class Solution {
    // [法一]：有额外存储空间
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k = k % n;
        int[] temp = new int[k];
        int j = 0;
        for (int i = n - k; i < n; i++) {
            temp[j++] = nums[i];
        }

        j = n - 1;
        for (int i = n - k - 1; i >= 0; i--) {
            nums[j--] = nums[i];
        }

        System.arraycopy(temp, 0, nums, 0, k);
    }

    public static void main(String[] args) {
        int[][] testNums = {
            {1,2,3,4,5,6,7},
            {-1,-100,3,99},
        };
        int[] testKs = {
            3,
            2,
        };

        Solution s = new Solution();
        for (int i = 0; i < testNums.length; i++){
            s.rotate(testNums[i], testKs[i]);
            System.out.println("No."+i+" test case: "+ Arrays.toString(testNums[i]));
        }
    }
}