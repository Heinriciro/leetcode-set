package com.smz.solution.dynamic_programming.n1458_max_dot_product_of_two_subsequences;

public class Solution {

    // [法三]：官方题解 DP，思路类似，写法更简便
    public int maxDotProduct(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int[][] f = new int[m][n];

        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                int xij = nums1[i] * nums2[j];
                f[i][j] = xij;
                if (i > 0) {
                    f[i][j] = Math.max(f[i][j], f[i - 1][j]);
                }
                if (j > 0) {
                    f[i][j] = Math.max(f[i][j], f[i][j - 1]);
                }
                if (i > 0 && j > 0) {
                    f[i][j] = Math.max(f[i][j], f[i - 1][j - 1] + xij);
                }
            }
        }

        return f[m - 1][n - 1];
    }

    // [法二]：DP + 时间优化
    public int maxDotProduct2(int[] nums1, int[] nums2) {
        int res = Integer.MIN_VALUE;
        // 令dp[i][j]为第一个子序列以nums1[i]结尾，第二个子序列以nums2[j]结尾时的最大点积
        int[][] dp = new int[nums1.length][nums2.length];

        // 令max[i][j] 为 dp[m][n] 在所有 m<i,n<j 中的最大值
        int[][] max = new int[nums1.length+1][nums2.length+1];

        for (int i = 0; i < nums1.length; i++) {
            for (int j = 0; j < nums2.length; j++) {
                int curProduct = nums1[i] * nums2[j];
                dp[i][j] = curProduct;
                dp[i][j] = Math.max(dp[i][j], max[i][j] + curProduct);
                max[i+1][j+1] = Math.max(max[i+1][j+1], dp[i][j]);
                max[i+1][j+1] = Math.max(max[i+1][j+1], max[i][j]);
                max[i+1][j+1] = Math.max(max[i+1][j+1], max[i+1][j]);
                max[i+1][j+1] = Math.max(max[i+1][j+1], max[i][j+1]);

                res = Math.max(res, dp[i][j]);
            }
        }

        return res;
    }
    // [法一]：DP，超时
    public int maxDotProduct1(int[] nums1, int[] nums2) {
        int res = Integer.MIN_VALUE;
        // 令dp[i][j]为第一个子序列以nums1[i]结尾，第二个子序列以nums2[j]结尾时的最大点积
        int[][] dp = new int[nums1.length][nums2.length];

        for (int i = 0; i < nums1.length; i++) {
            for (int j = 0; j < nums2.length; j++) {
                int curProduct = nums1[i] * nums2[j];
                dp[i][j] = curProduct;

                for (int m = i-1; m >= 0; m--) {
                    for (int n = j-1; n >= 0; n--) {
                        dp[i][j] = Math.max(dp[i][j], dp[m][n] + curProduct);
                    }
                }
                res = Math.max(res, dp[i][j]);
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int[][] testNums1 = {
            {5,-4,-3},
            {2,1,-2,5},
            {3,-2},
            {-1,-1},
        };
        int[][] testNums2 = {
            {-4,-3,0,-4,2},
            {3,0,-6},
            {2,-6,7},
            {1,1},
        };

        Solution s = new Solution();
        for (int i = 0; i < testNums1.length; i++) {
            System.out.println("No."+i+" test case: " + s.maxDotProduct(testNums1[i], testNums2[i]));
        }
    }
}