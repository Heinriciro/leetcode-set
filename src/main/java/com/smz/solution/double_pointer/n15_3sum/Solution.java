package com.smz.solution.double_pointer.n15_3sum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    // [法一·2]: 双指针，两个while写法
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        List<List<Integer>> res = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (i > 0 && nums[i] == nums[i-1]) continue;
            int target = -nums[i];

            int j = i+1;
            int k = n-1;

            while(j < k) {
                int curSum = nums[j] + nums[k];
                if (curSum == target) {
                    List<Integer> s = new ArrayList<>();
                    s.add(nums[i]);
                    s.add(nums[j]);
                    s.add(nums[k]);
                    res.add(s);
                    while (k > j && nums[--k] == nums[k+1]) {}
                    while (k > j && nums[++j] == nums[j-1]) {} 
                } else if (curSum > target) {
                    k--;
                } else {
                    j++;
                }
            }
        }
        return res;
    }

    // [法一]: 双指针，两个for写法
    public List<List<Integer>> threeSum1(int[] nums) {
        Arrays.sort(nums);

        List<List<Integer>> res = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i-1]) continue;

            int k = nums.length - 1;

            for (int j = i + 1; j < k; j++) {
                if (j > i+1 && nums[j] == nums[j-1]) continue;

                int target = -nums[i];

                while (k > j && nums[j] + nums[k] > target) k--;
                if (k == j) break;
                if (nums[j] + nums[k] == target) {
                    List<Integer> s = new ArrayList<>();
                    s.add(nums[i]);
                    s.add(nums[j]);
                    s.add(nums[k]);
                    res.add(s);

                    while (k > j && nums[k - 1] == nums[k]) k--;
                }
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int[][] testNums = {
            // {-100, -70, -60, 110, 120, 130, 160},
            {-1,0,1,2,-1,-4},
            {0,1,1},
            {0,0,0}
        };

        Solution s = new Solution();
        for (int i = 0; i < testNums.length; i++) {
            System.out.println("No." + i + " test case:" + s.threeSum(testNums[i]));
        }
    }
}