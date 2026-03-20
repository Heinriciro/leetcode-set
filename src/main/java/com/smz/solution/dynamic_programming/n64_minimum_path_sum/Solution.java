class Solution {
    // [法一]：DP
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[][] dp = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i==0 && j==0) {
                    dp[i][j] = grid[i][j];
                    continue;
                }

                dp[i][j] = Integer.MAX_VALUE;
                if (i-1 >= 0) dp[i][j] = Math.min(dp[i][j], dp[i-1][j] + grid[i][j]);
                if (j-1 >= 0) dp[i][j] = Math.min(dp[i][j], dp[i][j-1] + grid[i][j]);
            }
        }

        return dp[m-1][n-1];
    }

    public static void main(String[] args) {
        int[][][] testGrids = {
            {{1,3,1},{1,5,1},{4,2,1}},
            {{1,2,3},{4,5,6}},
        };

        Solution s = new Solution();
        for (int i = 0; i < testGrids.length; i++) {
            System.out.println("No."+i+" test case: "+s.minPathSum(testGrids[i]));
        }
    }
}