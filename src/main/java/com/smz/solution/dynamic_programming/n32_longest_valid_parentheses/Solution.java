class Solution {
    public int longestValidParentheses(String s) {
        // [法一]：DP
        int n = s.length();
        int[] dp = new int[n];

        int res = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '(') continue;
            if (i > 0 && s.charAt(i-1) =='(') {
                dp[i] = 2;
                if (i-2 >= 0) dp[i] += dp[i-2];
            } 

            if (i > 0 && s.charAt(i-1) == ')') {
                int left = i-1-dp[i-1];
                if (left >= 0 && s.charAt(left) == '(') {
                    dp[i] = dp[i-1] + 2;
                    if (left-1 >= 0) dp[i] += dp[left-1];
                }
            }

            res = Math.max(res, dp[i]);
        }

        return res;
    }

    public static void main(String[] args) {
        String[] testSs = {
            "(()",
            ")()())",
            ""
        };

        Solution s = new Solution();
        for (int i = 0; i < testSs.length; i++) {
            System.out.println("No."+i+" test case: "+s.longestValidParentheses(testSs[i]));
        }
    }
}