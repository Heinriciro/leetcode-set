class Solution {
    // [法一]: DP
    public int minDistance(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i < len1 + 1; i++) {
            for (int j = 0; j < len2 + 1; j++) {
                dp[i][j] = Integer.MAX_VALUE;
                if (i==0 && j==0) dp[i][j] = 0;
                else if (i == 0) dp[i][j] = j;
                else if (j == 0) dp[i][j] = i;

                if (i > 0 && j > 0 && word1.charAt(i - 1) == word2.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1];
                if (i>0 && j > 0) dp[i][j] = Math.min(dp[i-1][j-1]+1, Math.min(dp[i][j], Math.min(dp[i - 1][j], dp[i][j - 1]) + 1));
            }
        }
        return dp[len1][len2];
    }

    public static void main(String[] args) {
        String[] testWord1s = {
            "horse",
            "intention",
        };
        String[] testWord2s = {
            "ros",
            "execution",
        };

        Solution s = new Solution();
        for (int i = 0; i < testWord1s.length; i++) {
            System.out.println("No."+i+" test case: " + s.minDistance(testWord1s[i], testWord2s[i]));
        }
    }
}