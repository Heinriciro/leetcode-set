package com.smz.solution.prefix_sum.n560_subarray_sum_equals_k;
import java.util.HashMap;
import java.util.Map;

class Solution {
    // [法二]：前缀和 + 哈希
    public int subarraySum(int[] nums, int k) {
        int n = nums.length;
        int[] prefix = new int[n + 1];
        Map<Integer, Integer> prefix2cnt = new HashMap<>();
        prefix2cnt.put(0, 1);

        int res = 0;
        for (int i = 0; i < n; i++) {
            prefix[i+1] = prefix[i] + nums[i];
            int target = prefix[i+1] - k;
            if (prefix2cnt.containsKey(target)) res += prefix2cnt.get(target);

            prefix2cnt.put(prefix[i+1], prefix2cnt.getOrDefault(prefix[i+1], 0) + 1);
        } 

        return res;
    }

    // [法一]：前缀和 + 暴力
    public int subarraySum1(int[] nums, int k) {
        int n = nums.length;
        int[] prefix = new int[n + 1];

        int res = 0;
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];

            for (int j = i - 1; j >= -1; j--) {
                int sum = prefix[i+1] - prefix[j+1];
                if (sum == k) res++;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int[][] testNums = {
            {1,1,1},
            {1,2,3},
        };
        int[] testKs = {
            2,
            3,
        };
        
        Solution s = new Solution();
        for (int i = 0; i < testNums.length; i++) {
            System.out.println("No."+ i + " test case: " + s.subarraySum(testNums[i], testKs[i]));
        }
    }
}