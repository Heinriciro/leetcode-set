class Solution {
    // [法一]：DP
    public int rob(int[] nums) {
        int n = nums.length;
        int[] dp =  new int[n + 2];

        int res = 0;
        
        for (int i = 1; i < n + 2; i++) {
            if (i - 1 >= 0) dp[i] = dp[i - 1];
            if (i - 2 >= 0) dp[i] = Math.max(dp[i-2] + nums[i-2], dp[i]);
            res = Math.max(res, dp[i]);
        }

        return res;
    }

    public static void main(String[] args) {
        int[][] testNum = {
            {1,2,3,1},
            {2,7,9,3,1},
        };

        Solution s = new Solution();
        for (int i = 0; i < testNum.length; i++) {
            System.out.println("No."+i+" test case: "+s.rob(testNum[i]));
        }
    }
}