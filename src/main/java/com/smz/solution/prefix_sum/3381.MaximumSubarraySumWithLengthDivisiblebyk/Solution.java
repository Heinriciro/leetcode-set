
import java.util.ArrayList;
import java.util.List;


public class Solution {
    public long maxSubarraySum1(int[] nums, int k) {
        int n = nums.length;
        long[] kMinSum = new long[k];
        for (int i = 0; i < k; i++) {
            kMinSum[i] = Long.MAX_VALUE / 2; // 防止溢出
        }
        kMinSum[k-1] = 0; // 因为余数为K-1时，最小的前缀和可能的选择中肯定包含还没开始累加时的0， 而且这个0在从下标为0开始计算时无法算到，所以直接设置在这里体现。

        long prefixSum = 0;
        long maxSum = Long.MIN_VALUE;
        for (int i = 0; i < n; i++) { // 由于计算到i时，所有比i小的下标的前缀和已经计算（即末尾下标i， 开头下标j-1， j-1<i），所以只需一次遍历
            prefixSum += nums[i];
            maxSum = Math.max(maxSum, prefixSum - kMinSum[i % k]);
            kMinSum[i % k] = Math.min(kMinSum[i % k], prefixSum);
        }

        return maxSum;
    }

    public long maxSubarraySum(int[] nums, int k) {
        int totalLen = nums.length; 
        List<Integer> lens = new ArrayList<>();

        for (int i = 1; k * i <= totalLen; i++) {
            lens.add(k * i);
        }

        long maxSum = Long.MIN_VALUE;
        for (int len : lens) {
            long curSum = 0;
            for (int i = 0; i < len; i++) {
                curSum += nums[i];
            }
            maxSum = maxSum >= curSum ? maxSum : curSum;

            for (int i = 1; i + len - 1 < totalLen; i++) {
                curSum = curSum - nums[i - 1] + nums[i + len - 1];
                maxSum = maxSum >= curSum ? maxSum : curSum;
            }
        }

        return maxSum;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        int[][] nums = {
            {1,2},
            {-1,-2,-3,-4,-5},
            {-5,1,2,-3,4},
        };
        int[] k = {1,4,2};
        for (int i = 0; i < nums.length; i++) {
            long result = sol.maxSubarraySum1(nums[i], k[i]);
            System.out.println("Maximum Subarray Sum in No." + i  + " testcase with Length Divisible by " + k[i] + " is: " + result);
        }
    }
    
}
