package com.smz.solution.bitwise.n3315_construct_the_minimum_bitwise_array_ii;
import java.util.Arrays;
import java.util.List;

public class Solution {
    // 分析：本题与3314一模一样，只是数据范围约束变化，nums的最大值从1000变为10^9，这使得暴力搜索变得不现实，必须使用位运算相关的特性
    //       观察： 011 = 010 OR 001 , 0101 = 0100 OR 0101, 0111 = 0011 OR 0100
    //       观察规律可知，将一个数转换为(ans)OR(ans+1)的形式，其实是将原数中最靠右的零之后最靠左的1转换为0后得出一数，该数+1后的二进制表示正好能够填不上这个被转换的1，从而相或保持不变
    //         对于2来说，其二进制为10，最靠右的0后不存在1，所以不存在解
    //       同时，与3314一样，除2以外必须为奇数才符合要求，事实上质数应当除了2其他均为奇数，因为是偶数则必然有2的因子
    // [法一]：lowbit 位运算
    public int[] minBitwiseArray(List<Integer> nums) {
        int n = nums.size();
        int[] ans = new int[n];

        for (int i =0; i < n; i++) {
            int num = nums.get(i);
            if (num == 2) {
                ans[i] = -1;
            } else {
                int lowbit = ((num + 1) & ~num);
                ans[i] = num ^ (lowbit >> 1);
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        List<List<Integer>> testNums = Arrays.asList(
            Arrays.asList(2,3,5,7),
            Arrays.asList(11,13,31)
        );

        Solution s = new Solution();
        for (int i = 0; i < testNums.size(); i++) {
            System.out.println("No."+i+" test case: "+ Arrays.toString(s.minBitwiseArray(testNums.get(i))));
        }
    }
}