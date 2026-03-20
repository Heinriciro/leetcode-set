class Solution {
    // [法一]: DP
    public int longestCommonSubsequence(String text1, String text2) {
        int len1 = text1.length(), len2 = text2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i < len1; i++) {
            for (int j = 0; j < len2; j++) {
                if (text1.charAt(i) == text2.charAt(j)) {
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                } else {
                    dp[i + 1][j + 1] = Math.max(dp[i][j + 1], dp[i + 1][j]);
                }
            }
        }

        return dp[len1][len2];
    }

    public static void main(String[] args) {
        String[] testStr1s = {
            "abcde",
            "abc",
            "abc",
        };
        String[] testStr2s = {
            "ace",
            "abc",
            "def",
        };

        Solution s = new Solution();
        for (int i = 0; i < testStr1s.length; i++) {
            System.out.println("No."+i+" test case: " + s.longestCommonSubsequence(testStr1s[i], testStr2s[i]));
        }
    }
}