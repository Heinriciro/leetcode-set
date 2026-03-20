
class Solution {
    // [法一]：DP (变种0-1背包，使用二维动态规划)
    public boolean canPartition(int[] nums) {
        int n = nums.length;
        if (n == 1) return false;
        int sum = 0;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
            max = Math.max(max, nums[i]);
        }

        if (sum % 2 != 0) return false;
        if (max > sum / 2) return false;

        int target = sum / 2;
        boolean[][] dp = new boolean[n][target + 1];
        for (int i = 0; i < n; i++) {
            dp[i][0] = true;
        }

        for (int i = 0; i < n; i++) {
            int cur = nums[i];
            for (int j = 1; j < target + 1; j++) {
                if (cur > j && i > 0) dp[i][j] = dp[i-1][j];
                else if (i == 0) dp[i][j] = cur == j;
                else {
                    dp[i][j] = dp[i-1][j] || dp[i-1][j - cur];
                }
            }
        }
        
        return dp[n-1][target];
    }

    public static void main(String[] args) {
        int[][] testNums = {
            {2,2,3,5},
            {3,3,6,8,16,16,16,18,20},
            {2,2,1,1},
            {1,5,11,5},
            {1,2,3,5},
        };

        Solution s = new Solution();
        for (int i = 0; i < testNums.length; i++) {
            System.out.println("No."+i+" test case: " + s.canPartition(testNums[i]));
        }
    }
}