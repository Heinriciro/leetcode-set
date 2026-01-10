package com.smz.solution.dynamic_programming.n712_minimum_ascii_delete_sum_for_two_strings;
public class Solution {

    // [法二]：DP + 正向思维
    public int minimumDeleteSum(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m+1][n+1];

        // 注意此时的i, j不同于法一，代表的是遍历dp[][], 而不是字符串s1,s2
        for (int i = 0; i < m + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                if (i == 0 && j == 0) continue;
                if (i == 0) {
                    dp[i][j] = dp[i][j-1] + s2.charAt(j-1);
                }
                if (j == 0) {
                    dp[i][j] = dp[i-1][j] + s1.charAt(i-1);
                }

                if (i != 0 && j != 0) {
                    // 字符相同，说明不需要再去除字符
                    if (s1.charAt(i-1) == s2.charAt(j-1)) dp[i][j] = dp[i-1][j-1];
                    else {
                        dp[i][j] = dp[i-1][j-1] + s1.charAt(i-1) + s2.charAt(j-1);
                        dp[i][j] = Math.min(dp[i][j], dp[i][j-1] + s2.charAt(j-1));
                        dp[i][j] = Math.min(dp[i][j], dp[i-1][j] + s1.charAt(i-1));
                    }
                }
            }
        }

        return dp[m][n];
    }

    // [法一]：DP + 反向思维
    //   记录拥有最大ascii和的两字符串中的相同子序列
    public int minimumDeleteSum1(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m+1][n+1];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 当前字符相同即保留
                if (s1.charAt(i) == s2.charAt(j)) {
                    dp[i+1][j+1] = dp[i][j] + s1.charAt(i) * 2;
                } else {
                    dp[i+1][j+1] = Math.max(dp[i][j+1], dp[i+1][j]);
                }
            }
        }

        int totalSum = 0;
        for (char s : s1.toCharArray()) {
            totalSum += s;
        }
        for (char s : s2.toCharArray()) {
            totalSum += s;
        }

        return totalSum - dp[m][n];
    }

    public static void main(String[] args) {
        String[] testS1s = {
            "sea",
            "delete",
        };
        String[] testS2s = {
            "eat",
            "leet",
        };

        Solution s = new Solution();
        for (int i = 0; i < testS1s.length; i++) {
            System.out.println("No."+i+" test case "+s.minimumDeleteSum(testS1s[i], testS2s[i]));
        }
    }
}