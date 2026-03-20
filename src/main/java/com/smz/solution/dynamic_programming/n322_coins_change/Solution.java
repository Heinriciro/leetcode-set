import java.util.HashSet;
import java.util.Set;

class Solution {
    // [法一]：DP
    public int coinChange(int[] coins, int amount) {
        Set<Integer> denoms = new HashSet<>();
        for (int e : coins) {
            denoms.add(e);
        }

        int[] dp = new int[amount + 1];
        for (int i = 1; i < amount + 1; i++) {
            if (denoms.contains(i)) {
                dp[i] = 1;
                continue;
            }

            dp[i] = Integer.MAX_VALUE;
            for (int e : coins) {
                if (i - e > 0 && dp[i - e] != Integer.MAX_VALUE) dp[i] = Math.min(dp[i], dp[i - e] + 1);
            }
        }

        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }

    public static void main(String[] args) {
       int[][] testCoins = {
        {1,2,5},
        {2},
        {1},
       };
       int[] testAmount = {
        11,
        3,
        0
       };

       Solution s = new Solution();
       for (int i = 0; i < testCoins.length; i++) {
        System.out.println("No."+i+" test case: "+s.coinChange(testCoins[i], testAmount[i]));
       }
    }
}