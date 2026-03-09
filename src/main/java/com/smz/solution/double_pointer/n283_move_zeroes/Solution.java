package com.smz.solution.double_pointer.n283_move_zeroes;

import java.util.Arrays;

public class Solution {
    public void moveZeroes(int[] nums) {
        int n = nums.length;
        int left = 0;
        int right = 0;

        while (right < n) {
            if (nums[right] != 0) {
                if (left < right) {
                    int temp = nums[right];
                    nums[right] = nums[left];
                    nums[left] = temp;
                }
                left++;
            }

            right++;
        }
    }

    // [法一]：自行实现的双指针法
    //  左指针指向第一个零位置，右指针指向第一个非零位置，进行交换
    //  存在零位置的多次互换，并且逻辑判断书写上有些复杂
    public void moveZeroes1(int[] nums) {
        int zeroIdx = 0;
        int nZeroIdx = 0;

        while (zeroIdx < nums.length && nZeroIdx < nums.length) {
            while (zeroIdx < nums.length && nums[zeroIdx] != 0) zeroIdx++;
            while (nZeroIdx < zeroIdx || nZeroIdx < nums.length && nums[nZeroIdx] == 0) nZeroIdx++;

            if (zeroIdx < nums.length && nZeroIdx < nums.length) {
                int temp = nums[nZeroIdx];
                nums[nZeroIdx] = nums[zeroIdx];
                nums[zeroIdx] = temp;
            }
        }
    }


    public static void main(String[] args) {
        int[][] testNums = {
            {0,1},
            {1,0},
            {0,0,1,1,3,4,0,12,2},
            {0,1,0,3,12},
            {0},
        };

        Solution s = new Solution();
        for (int i = 0; i < testNums.length; i++) {
            s.moveZeroes(testNums[i]);
            System.out.println("No."+i+" test case:"+ Arrays.toString(testNums[i]));
        }
    }

}