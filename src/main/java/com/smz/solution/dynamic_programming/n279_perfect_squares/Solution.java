import java.util.Arrays;

class Solution {
    // [法二]：DP + 大大减小内层循环
    public int numSquares(int n) {
        int[] dp = new int[n+1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 1;
        for (int i = 1; i < n+1; i++) {
            double root = Math.sqrt(i);
            if (root % 1 == 0d) {
                dp[i] = 1;
                continue;
            }
            for (int j = 1; j * j <= i; j++) {
                dp[i] = Math.min(dp[i], dp[i - j * j]);
            }
            dp[i] += 1;
        }

        return dp[n];
    }
    // [法一]: DP + 暴力
    public int numSquares1(int n) {
        int[] dp = new int[n+1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 1;
        for (int i = 1; i < n + 1; i++) {
            double root = Math.sqrt(i);
            if (root % 1 == 0d) {
                dp[i] = 1;
                continue;
            }

            for (int j = 1; j <= i / 2; j++) {
                dp[i] = Math.min(dp[i], dp[j] + dp[i-j]);
            }
        }

        return dp[n];
    }

    public static void main(String[] args) {
        int[] testNs = {
            12,
            13,
        };
        
        Solution s = new Solution();
        for (int i = 0; i < testNs.length; i++) {
            System.out.println("No."+i+" test case: " + s.numSquares(testNs[i]));
        }
    }
}