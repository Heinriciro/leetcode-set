package com.smz.solution.prefix_sum.n1292_maximum_side_length_of_a_square_with_sum_less_than_or_equal_to_threshold;
public class Solution {
    // [法一]: 前缀和 + 枚举 + 剪枝
    public int maxSideLength(int[][] mat, int threshold) {
        int m = mat.length;
        int n = mat[0].length;
        int[][] prefix = new int[m+1][n+1];

        int maxSide = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                prefix[i+1][j+1] = mat[i][j] + prefix[i][j+1] + prefix[i+1][j] - prefix[i][j];

                for (int k = maxSide; k <= i && k <= j; k++) {
                    int sum = prefix[i+1][j+1] - prefix[i+1 - k - 1][j+1] - prefix[i+1][j+1 - k - 1] + prefix[i+1 - k - 1][j+1 - k - 1];
                    if (sum <= threshold) {
                        maxSide = k + 1;
                    }
                }
            }
        }

        return maxSide;
    }

    public static void main(String[] args) {
        int[][][] testMats = {
            {{1,1,3,2,4,3,2},{1,1,3,2,4,3,2},{1,1,3,2,4,3,2}},
            {{2,2,2,2,2},{2,2,2,2,2},{2,2,2,2,2},{2,2,2,2,2},{2,2,2,2,2}},
        };
        int[] testThresholds = {4,1};

        Solution s = new Solution();
        for (int i = 0; i < testMats.length; i++) {
            System.out.println("No."+i+" test case: "+s.maxSideLength(testMats[i], testThresholds[i]));
        }
    }
}