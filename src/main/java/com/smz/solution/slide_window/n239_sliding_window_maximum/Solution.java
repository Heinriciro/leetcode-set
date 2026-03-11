package com.smz.solution.slide_window.n239_sliding_window_maximum;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

class Solution {
    // [法一]：滑动窗口 + 单调队列
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] res = new int[n - k + 1];
        Deque<Integer> monoQue = new ArrayDeque<>();

        for (int i = 0; i < k; i++) {
            int cur = nums[i];
            while (!monoQue.isEmpty() && nums[monoQue.peekLast()] < cur) {
                monoQue.pollLast();
            }
            monoQue.addLast(i);
        }
        res[0] = nums[monoQue.peekFirst()];

        for (int i = 0; i < n - k; i++) {
            int right = nums[i + k];
            
            if (i == monoQue.peekFirst()) {
                monoQue.pollFirst();
            }

            while (!monoQue.isEmpty() && nums[monoQue.peekLast()] < right) {
                monoQue.pollLast();
            }
            monoQue.addLast(i + k);

            res[i+1] = nums[monoQue.peekFirst()];
        }
        return res;
    }

    public static void main(String[] args) {
        int[][] testNums = {
            {1,3,1,2,0,5},
            {1,3,-1,-3,5,3,6,7},
            {1},
        };
        int[] testKs = {
            3,
            3,
            1,
        };

        Solution s = new Solution();
        for (int i = 0; i < testNums.length; i++) {
            System.out.println("No." + i + " test case: " + Arrays.toString(s.maxSlidingWindow(testNums[i], testKs[i])));
        }
    }
}