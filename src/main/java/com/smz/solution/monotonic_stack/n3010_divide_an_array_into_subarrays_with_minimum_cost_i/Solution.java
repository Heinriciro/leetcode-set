package com.smz.solution.monotonic_stack.n3010_divide_an_array_into_subarrays_with_minimum_cost_i;

public class Solution {
    // 分析：简单题数据量较小，必然可以用暴力解决，但是暂不考虑
    //      首先，第一个数必定保留以构成第一个子数组
    //      随后只需要找到此外最小的两个数，以此分组即可
    //      由于求得就是最小和，那么也不用记录位置坐标，仅记录数值即可
    public int minimumCost(int[] nums) {
        // 使用数组模拟单调栈，记录最小的两个数，数组单调非递减
        int[] minTwo = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        int first = nums[0];

        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];
            if (num < minTwo[0]) {
                minTwo[1] = minTwo[0];
                minTwo[0] = num;
            } else if (num < minTwo[1]) {
                minTwo[1] = num;
            }
        }

        return first + minTwo[0] + minTwo[1];
    }

    public static void main(String[] args) {
        int[][] testNums = {
            {1,2,3,12},
            {5,4,3},
            {10,3,1,1},
        };
        
        Solution solution = new Solution();
        for (int i = 0; i < testNums.length; i++) {
            System.out.println("No."+i+" test case: " + solution.minimumCost(testNums[i]));
        }
    }
}
