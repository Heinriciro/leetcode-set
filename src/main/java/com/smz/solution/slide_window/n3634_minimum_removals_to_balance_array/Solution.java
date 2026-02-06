package com.smz.solution.slide_window.n3634_minimum_removals_to_balance_array;
import java.util.Arrays;

class Solution {
    // 分析： 显然，从最大值或最小值删起是一定会有可能满足条件的

    // [法三]：滑动窗口 + 双指针
    public int minRemoval(int[] nums, int k) {
        int n = nums.length;
        Arrays.sort(nums);
        int max = 0;
        int left = 0;

        for (int i = 0; i < n; i++) {
            while ((long) nums[left] * k < nums[i]) {
                left++;
            }
            max = Math.max(max, i - left + 1);
        }

        return n - max;
    }

    // [法二]：DP+递推写法 
    //      超出内存限制
    public int minRemoval2(int[] nums, int k) {
        int n = nums.length;
        Arrays.sort(nums);
        int[][] dp = new int[n][n];

        for (int i = n - 1; i >=0; i--) {
            for (int j = i; j < n; j++) {
                if (i == j || (long)nums[i] * k >= nums[j]) {
                    dp[i][j] = 0;
                    continue;
                }

                dp[i][j] = Math.min(dp[i+1][j], dp[i][j-1]) + 1;
            }
        }

        return dp[0][n-1];
    }


    // [法一]：DFS+记忆化搜索
    //        超出内存限制
    public int minRemoval1(int[] nums, int k) {
        int n = nums.length;
        Arrays.sort(nums);
        int[][] mem = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(mem[i], Integer.MAX_VALUE);
        }

        return dfs(nums, mem, 0, n-1, k, 0);
    }

    private int dfs(int[] nums, int[][] mem, int start, int end, int k, int ans) {
        if(start == end) return ans;
        int min = nums[start];
        int max = nums[end];
        if ((long)min * k >= (long)max) return ans;

        int res;
        if (mem[start][end] != Integer.MAX_VALUE) return mem[start][end];
        else {
            int a = dfs(nums, mem, start + 1, end, k, ans + 1);
            int b = dfs(nums, mem, start, end - 1, k, ans + 1);
            res = Math.min(a, b);
            mem[start][end] = res;
        }

        return res;
    }

    public static void main(String[] args) {
        int[][] testNums = {
            {2,1,5},
            {1,6,2,9},
            {4,6},
        };
        int[] testKs = {2,3,2};
        Solution s = new Solution();
        for (int i = 0; i < testNums.length; i++) {
            System.out.println("No."+i+" test case: "+s.minRemoval(testNums[i], testKs[i]));
        }
    }
}