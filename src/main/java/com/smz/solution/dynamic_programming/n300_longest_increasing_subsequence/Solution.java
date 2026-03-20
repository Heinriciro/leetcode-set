import java.util.Arrays;

class Solution {
    // [法二]：贪心
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] tailofSubarrayWithLength = new int[n+1];
        int len = 1;
        tailofSubarrayWithLength[1] = nums[0];

        for (int i = 1; i < n; i++) {
            int cur = nums[i];
            if (cur > tailofSubarrayWithLength[len]) {
                tailofSubarrayWithLength[++len] = cur;
            } else {
                int target = Arrays.binarySearch(tailofSubarrayWithLength, 1, len + 1, cur);
                if (target < 0) target = -target - 1;
                tailofSubarrayWithLength[target] = cur;
            }
        }

        return len;
    }

    // [法一]： DP + 暴力
    public int lengthOfLIS1(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];

        Arrays.fill(dp, 1);
        
        int res = 1;
        for (int i = 1; i < n; i++) {

            for (int j = i - 1; j >= 0; j--) {
                if (nums[i] > nums[j]) dp[i] = Math.max(dp[i], dp[j] + 1);
            }

            res = Math.max(res, dp[i]);
        }

        return res;
    }

    public static void main(String[] args) {
        int[][] testNums = {
            {1,2,-10,-8,-7},
            {3,5,6,2,5,4,19,5,6,7,12},
            {0,1,0,3,2,3},
            {10,9,2,5,3,7,101,18},
            {7,7,7,7,7,7,7},
        };

        Solution s = new Solution();
        for (int i = 0; i < testNums.length; i++) {
            System.out.println("No."+i+" test case: " + s.lengthOfLIS(testNums[i]));
        }
    }
}