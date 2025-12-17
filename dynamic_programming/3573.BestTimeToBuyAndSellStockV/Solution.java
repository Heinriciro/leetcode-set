public class Solution {
    // 本题不需要考虑股票在手的数量，可以任意的买进和卖出, 但是买进后必须卖出或卖出后必须买进
    // 注意：由于必须完成一笔交易再进行下一个，考虑到题目中引入天数这一时间轴，所以无法重复进行同一个交易

    // 法二：DP - 三维，引入三种状态
    public long maximumProfit(int[] prices, int k) {
        int n = prices.length;
        long[][][] dp = new long[n][k + 1][3];
        
        // 初始化第 0 天的状态
        for (int j = 1; j <= k; j++) {
            dp[0][j][1] = -prices[0];
            dp[0][j][2] = prices[0];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= k; j++) {
                dp[i][j][0] = Math.max(dp[i - 1][j][0], Math.max(dp[i - 1][j][1] + prices[i], dp[i - 1][j][2] - prices[i]));
                dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i - 1][j - 1][0] - prices[i]);
                dp[i][j][2] = Math.max(dp[i - 1][j][2], dp[i - 1][j - 1][0] + prices[i]);
            }
        }
        
        return dp[n - 1][k][0];
    }

    // 思考：题目可以简化为，按顺序挑选最多k组数字，每组数字包含两个，每组数字最优收益必定是其差的绝对值，求可能挑选组合的最大收益
    // 看出可以使用动态规划的思路：
    // 令dp[i][j]为前i个元素组成的子数组，在最大j次操作下，获得的最大收益。 显然有dp[i][0] = dp[0][j] = 0
    // 有状态转移公式： dp[i][j] = (p=0...i)Max(dp[i-1][j], dp[i-1-p][j-1] + curProfit(i-p, i))
    // 其中最后一项代表，从(i-j+1)~(i+1)这一子数组内的最大收益，实际上就是找到这一区间内的最大值和最小值

    // 法一：DP - 递推写法
    // 注意找转移公式最后一项时，由于其就是寻找最大值最小值，且区间(i-j+1)~(i+1)在同一个i下就是一个不断增大的子数组，已进入这个范围内的元素不会再出去，故只需要维护最大最小值即可
    public long maximumProfit1(int[] prices, int k) {
        long[][] dp = new long[prices.length+1][k+1];

        for (int i = 1; i <= prices.length; i++) {
            for (int j = 1; j <= k; j++) {

                dp[i][j] = dp[i-1][j];
                if (i != 1) {
                    int max = Integer.MIN_VALUE;
                    int min = Integer.MAX_VALUE;
                    for (int p = 0; p < i; p++) {
                        if (prices[i-1-p] > max) max = prices[i-1-p];
                        if (prices[i-1-p] < min) min = prices[i-1-p];
                        dp[i][j] = Math.max(dp[i][j], dp[i-1-p][j-1] + max - min);
                    }
                }
            }
        }

        return dp[prices.length][k];
    }


    public static void main(String[] args) {
        int[][] testPrices = {
            {1,7,9,8,2},
            {12,16,19,19,8,1,19,13,9}
        };
        int[] testKs = {2,3};

        Solution s = new Solution();
        for (int i = 0; i < testKs.length; i++){
            System.out.println("No." + i + " test case: " + s.maximumProfit(testPrices[i], testKs[i]));
        }
    }
}