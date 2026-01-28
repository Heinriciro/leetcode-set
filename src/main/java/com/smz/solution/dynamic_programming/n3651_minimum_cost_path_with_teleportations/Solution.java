package com.smz.solution.dynamic_programming.n3651_minimum_cost_path_with_teleportations;
import java.util.Arrays;

public class Solution {
    // [法一]：DP + 后缀最小值
    public int minCost(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        int mx = 0; // 找到网格最大值，便于后续记录可传送节点
        for (int[] row : grid) {
            for (int e : row) {
                mx = Math.max(mx, e);
            }
        }

        int[][][] dp = new int[k + 1][m + 1][n + 1];
        // 初始值设置为最大值，因为只能从左上角起点开始逐步跳转至其他点
        for (int[][] tdp : dp) {
            for (int[] row : tdp) {
                Arrays.fill(row, Integer.MAX_VALUE / 2); 
            }
        }
        int[] minDpofValue = new int[mx + 1];
        int[] sufMinDpofValue = new int[mx + 2]; // 在计算恰好t次传送时，寻找最小值仅在t-1中寻找，所以仅使用1维
        Arrays.fill(sufMinDpofValue, Integer.MAX_VALUE);

        for (int t = 0; t <= k; t++) {
            //每次遍历新的t值时，minDpofValue可以初始化来记录当前t下对应花费x的最小dp值，此时sufMinDpofValue仍是t-1时的后缀最小值，最后再使用minDpofValue 更新sufMinDpofValue
            Arrays.fill(minDpofValue, Integer.MAX_VALUE);
            dp[t][1][1] = -grid[0][0]; // 便于在后续+grid[i][j]时将花费变为0

            for (int i = 1; i < m + 1; i++) {
                for (int j = 1; j < n + 1; j++) {
                    int cost = grid[i-1][j-1];
                    dp[t][i][j] = Math.min(dp[t][i][j], dp[t][i - 1][j]);
                    dp[t][i][j] = Math.min(dp[t][i][j], dp[t][i][j-1]);

                    dp[t][i][j] = Math.min(dp[t][i][j] + cost, sufMinDpofValue[cost]);

                    minDpofValue[cost] = Math.min(minDpofValue[cost], dp[t][i][j]);
                }
            }

            for (int i = mx; i>=0; i--) {
                sufMinDpofValue[i] = Math.min(sufMinDpofValue[i+1], minDpofValue[i]);
            }
        }

        return dp[k][m][n];
    }

    public static void main(String[] args) {
        int[][][] testGrids = {
            {{1,2},{2,3},{3,4}},
            {{1,3,3},{2,5,4},{4,3,5}},
        };
        int[] testKs = {1,2};

        Solution s = new Solution();
        for (int i = 0; i < testKs.length; i++) {
            System.out.println("No."+i+" test case: "+s.minCost(testGrids[i], testKs[i]));
        }
    }
}