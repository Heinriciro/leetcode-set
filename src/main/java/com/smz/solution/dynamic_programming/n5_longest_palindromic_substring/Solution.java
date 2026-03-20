package com.smz.solution.dynamic_programming.n5_longest_palindromic_substring;

class Solution {
    // [法一]： DP
    public String longestPalindrome(String s) {
        int n = s.length();
        if (n <= 1) return s;

        boolean[][] dp = new boolean[n][n];
        String res = s.substring(0, 1);
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }

        for (int len = 2; len <= n; len++) {
            for (int i = 0; i < n - len + 1; i++) {
                int right = i + len - 1;
                if (s.charAt(i) == s.charAt(right)) {
                    if (i + 1 > right - 1) dp[i][right] = true;
                    else dp[i][right] = dp[i+1][right-1];
                }

                if (dp[i][right] && len > res.length()) res = s.substring(i, right+1);
            }
        }

        return res;
    }

    public static void main(String[] args) {
        String[] testSs = {
            "babad",
            "cbbd",
            "abcddcba",
            "a",
            "ac",
            "",
        };

        Solution s = new Solution();
        for (int i = 0; i < testSs.length; i++) {
            System.out.println("No."+i+" test case: "+s.longestPalindrome(testSs[i]));
        }
    }
}