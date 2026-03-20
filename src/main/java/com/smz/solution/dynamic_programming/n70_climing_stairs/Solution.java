class Solution {
    // [法一]：DP
    public int climbStairs(int n) {
        int[] dp = new int[n + 2];

        dp[1] = 1;
        for (int i = 0; i < n; i++) {
            dp[i + 2] += dp[i] + dp[i+1];
        }

        return dp[n+1];
    }

    public static void main(String[] args) {
        int[] testNs = {
            2,
            3,
        };

        Solution s = new Solution();
        for (int i = 0; i < testNs.length; i++) {
            System.out.println("No."+i+" test case: "+s.climbStairs(testNs[i]));
        }
    }
}