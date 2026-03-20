class Solution {
    // [法一]：DP 同时记录以第i个元素为末尾的子数组的最大积和最小积
    public int maxProduct(int[] nums) {
        int n = nums.length;
        int[] maxDp = new int[n];
        int[] minDp = new int[n];
        maxDp[0] = nums[0];
        minDp[0] = nums[0];

        int res = maxDp[0];
        for (int i = 1; i < n; i++) {
            int cur = nums[i];
            if (cur == 0) {
                maxDp[i] = 0;
                minDp[i] = 0;
            } else {
                maxDp[i] = Math.max(nums[i], nums[i] * maxDp[i-1]);
                maxDp[i] = Math.max(maxDp[i], nums[i] * minDp[i-1]);
                minDp[i] = Math.min(nums[i], nums[i] * maxDp[i-1]);
                minDp[i] = Math.min(minDp[i], nums[i] * minDp[i-1]);
            }

            res = Math.max(res, maxDp[i]);
        }

        return res;
    }

    public static void main(String[] args) {
        int[][] testNums = {
            {2,3,-2,4},
            {-2,0,-1},
        };

        Solution s = new Solution();
        for (int i = 0; i < testNums.length; i++) {
            System.out.println("No."+i+" test case: "+s.maxProduct(testNums[i]));
        }
    }
}