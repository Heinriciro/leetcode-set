public class Solution {
    public long maxProfit(int[] prices, int[] strategy, int k) {
        int left = 0;
        int right = prices.length - 1;
        int mid = left;
        long rr = 0;
        long ll = 0;
        long inner = 0;
        long res = 0;

        while (right - left + 1 > k) {
            rr += prices[right] * strategy[right];
            res += prices[right] * strategy[right];
            right--;
        }
        while (mid <= k / 2 - 1) {
            res += prices[mid] * strategy[mid];
            mid++;
        } 
        int tmp = right;
        while (tmp >= mid) {
            inner += prices[tmp];
            res += prices[tmp] * strategy[tmp];
            tmp--;
        }
        res = Math.max(res, ll + inner + rr);

        while (right < prices.length - 1) {
            ll += prices[left] * strategy[left];
            inner -= prices[mid];
            left++;
            mid++;
            right++;
            rr -= prices[right] * strategy[right];
            inner += prices[right];

            res = Math.max(res, ll + inner + rr);
        }

        return res;
    }

    public static void main(String[] args) {
        int[][] testPrices = {
            // {4,2,8},
            // {5,4,3},
            {4,7,13},
        };

        int[][] testStrategies = {
            // {-1,0,1},
            // {1,1,0},
            {-1,-1,0},
        };

        int[] testKs = {
            // 2,
            // 2,
            2,
        }; 

        Solution s = new Solution();
        for (int i = 0; i <testKs.length; i++){
            System.out.println("No." + i + " test case: " + s.maxProfit(testPrices[i], testStrategies[i], testKs[i]));
        }
    }
}