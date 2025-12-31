
import java.util.ArrayDeque;
import java.util.Deque;

public class Solution {
    public static final int MOD = 1_000_000_007;

    // [法三]
    // 法二中重要优化点观察：在法二中，每枚举一个新的下标i时，都重新遍历了j = i-1 ~ L的所有情况，这个L代表最远能满足max-min<=k的下标
    // 分析
    // 1.之所以会存在这个L，是由于对于同一个i来说，以j结尾的这一个子数组长度只会越来越长，而他其中包含的元素并不会减少，也就是说最大值只有遇到更大的才会更新，最小值遇到更小的才会更新
    // 所以最大最小值的差只可能增大，不可能减少
    // 2.而当我们不断枚举i时，由于i往后移动，遍历j时的起始点i-1首先会往后移动，假定上次的L保持上次不变，那么这一回L~i-1的子数组的最大最小差不会变小，L也不可能往前移，因为上一次遍历时已经不满足条件了
    // 那么对于此次遍历，只需要考虑向后移动了的i-1 与 L 组成的子数组是否符合要求，若不符合再将L向后移动，直到符合要求为止即可

    public int countPartitions(int[] nums, int k) {
        int[] dp = new int[nums.length + 1];
        dp[0] = 1;
        long sum = 0; // dp[L] ~ dp[i-1]的和
        int L = 0; // 代表当前满足max-min<=k的最远下标
        Deque<Integer> maxIdxs = new ArrayDeque<>(); // 对应最大值单调递减栈，存储当前窗口的最大值下标
        Deque<Integer> minIdxs = new ArrayDeque<>(); // 对应最小值单调递增栈，存储当前窗口的最小值下标

        for (int i = 1; i <= nums.length; i++) {
            int cur = nums[i-1];

            // 判断最新进入窗口的 i-1 下标对应的值
            while(!maxIdxs.isEmpty() && nums[maxIdxs.peekLast()] <= cur) {
                maxIdxs.pollLast();
            }
            maxIdxs.addLast(i-1);

            while(!minIdxs.isEmpty() && nums[minIdxs.peekLast()] >= cur) {
                minIdxs.pollLast();
            }
            minIdxs.addLast(i-1);

            // 判断此时子数组是否符合最大最小值差要求，不符合则往后移L，同时更新sum和单调栈
            sum = (sum  + dp[i-1] % MOD) % MOD;
            while (nums[maxIdxs.peekFirst()] - nums[minIdxs.peekFirst()] > k) {
                // 需要将L向后移动
                sum = (sum - dp[L] % MOD + MOD) % MOD;
                L++;
                if (maxIdxs.peekFirst() < L) {
                    maxIdxs.pollFirst();
                }
                if (minIdxs.peekFirst() < L) {
                    minIdxs.pollFirst();
                }
            }

            dp[i] = (int) sum;
        }

        return dp[nums.length];
    }

    // [法二]: 动态规划 - 相比[法一]优化了重复计算, 但依然超时
    public int countPartitions2(int[] nums, int k) {
        int[] dp = new int[nums.length + 1]; // dp[i] 表示以第i-1个元素为结尾的，前缀子数组的划分方案数
        dp[0] = 1; // 因为长度为1的子数组必然可以划分，划分方案数为1，故为了方便，将dp[0]所代表的空数组方案数设置为1

        for (int i = 1; i <= nums.length; i++) {
            int max = Integer.MIN_VALUE;
            int min = Integer.MAX_VALUE;
            int res = 0;
            for (int j = i - 1; j >= 0; j--) {
                int cur = nums[j];
                max = Math.max(max, cur);
                min = Math.min(min, cur);
                if (max - min <= k) {
                    res =  (res + dp[j]) % MOD;
                } else {
                    break;
                }
            }
            dp[i] = res;
        }

        return dp[nums.length];
    }

    // 其实法一中的分解子问题已经接近动态规划的思路了
    // 只是没有进一步优化减少重复计算, 而且使用递归可能还增多了重复计算
    // [法一]：暴力+分解子问题+递归 - 超时
    private int[] nums;
    private int k;
    public int countPartitions1(int[] nums, int k) {
        this.nums = nums;
        this.k = k;

        return countPartitions(0);
    }

    public int countPartitions(int start) {
        if (start >= nums.length) {
            return 1;
        }
        int res = 0;
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int i = start; i < nums.length; i++) {
            int cur = nums[i];
            max = Math.max(max, cur);
            min = Math.min(min, cur);
            if (max - min <= k) {
                res = (res + countPartitions(i + 1)) % MOD;
            } else {
                break;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int[][] testNums = {
            {9, 4, 1, 3, 7},
            {3, 3, 4},
        };

        int[] testKs = {
            4,
            0,
        };

        Solution s = new Solution();
        for (int i = 0; i < testNums.length; i++) {
            System.out.println("No." + i + " result: " + s.countPartitions(testNums[i], testKs[i]));
        }
    }
}