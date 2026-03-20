package com.smz.solution.dynamic_programming.n62_unique_paths;
class Solution {
    // [法一]：DP
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i==0 && j==0) {
                    dp[i][j] = 1;
                    continue;
                }
                if (i-1 >= 0) dp[i][j] += dp[i-1][j];
                if (j-1 >= 0) dp[i][j] += dp[i][j-1];
            }
        }

        return dp[m-1][n-1];
    }

    public static void main(String[] args) {
        int[] testMs = {
            3,
            3,
        };
        int[] testNs = {
            7,
            2,
        };

        Solution s = new Solution();
        for (int i = 0; i < testMs.length; i++) {
            System.out.println("No."+i+" test case: "+s.uniquePaths(testMs[i], testNs[i]));
        }
    }
}