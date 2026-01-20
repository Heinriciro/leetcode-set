package com.smz.solution.bitwise.n3314_construct_the_minimum_bitwise_array_i;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {
    // 分析：一个质数仅有自己和1两个因数，2也是质数
    //       这样一个数表示为2进制，可知除了2这个数以外，其他数最低位一定是1(2^0)，不然它一定会有2的因数
    //       题目要求`ans[i] OR (ans[i]+1) = nums[i]`, 或运算的结果肯定大于或运算的两个输入值
    //           首先根据题目约束nums[i]<=1000, nums.length<=100来看，可以暴力解答，同时可以缓存历史枚举结果来加快计算速度,得到[法一].

    // [法一]：暴力+缓存
    private static final Map<Integer, Integer> map = new HashMap<>();
    static {
        map.put(2, -1); // 2 没有结果可以预先初始化
    }
    public int[] minBitwiseArray(List<Integer> nums) {
        int n = nums.size();
        int[] ans = new int[n];

        for (int i = 0; i < n; i++) {
            int num = nums.get(i);
            if (map.containsKey(num)) {
                ans[i] = map.get(num);
                continue;
            }

            // 0 或运算结果是1，非质数，所以直接跳过
            int res = -1;
            for (int f = 1; f < num; f++) {
                if ((f | (f+1)) == num) {
                    res = f;
                    break;
                }
            }

            ans[i] = res;
            map.put(num, res);
        }

        return ans;
    }

    public static void main(String[] args) {
        List<List<Integer>> testNums = Arrays.asList(
            Arrays.asList(2,3,4,5),
            Arrays.asList(2,3,5,7),
            Arrays.asList(11,13,31)
        );

        Solution s = new Solution();
        for (int i = 0; i < testNums.size(); i++) {
            System.out.println("No."+i+" test case: "+Arrays.toString(s.minBitwiseArray(testNums.get(i))));
        }
    }
}