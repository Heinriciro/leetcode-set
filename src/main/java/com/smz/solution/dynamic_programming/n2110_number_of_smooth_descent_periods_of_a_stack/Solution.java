public class Solution {
    public long getDescentPeriods(int[] prices) {
        int n = prices.length;
        int longestPeriodEndWithPrev = 1;

        long res = 0;
        for (int i = 0; i < n; i++) {
            res++; // 一定包含自己
            if (i > 0 && prices[i] == prices[i-1] - 1) {
                res += longestPeriodEndWithPrev;
                longestPeriodEndWithPrev++;
            } else {
                longestPeriodEndWithPrev = 1;
            }
        }

        return res;
    }


    public static void main(String[] args) {
        int specTest1Len = 100000;
        int[] specTest1 = new int[specTest1Len];
        for (int i = 0; i < specTest1Len; i++) {
            specTest1[i] = specTest1Len - i;
        }
        int[][] testPrices = {
            {3,2,1,4},
            {8,6,7,7},
            {1},
            specTest1
        };
        Solution s = new Solution();

        for (int i = 0; i<testPrices.length; i++) {
            System.out.println("No."+i+" test case: " + s.getDescentPeriods(testPrices[i]));
        }
    }

}