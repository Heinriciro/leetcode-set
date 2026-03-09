package com.smz.solution.double_pointer.n11_container_with_most_water;

public class Solution {
    // [法一]：双指针
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;

        int ans = 0;
        while (left < right) {
            int h;
            if (height[left] < height[right]) {
                h = height[left];
                left++;
            } else {
                h = height[right];
                right--;
            }

            ans = Math.max(ans, h * (right - left + 1));
        }

        return ans;
    }

    public static void main(String[] args) {
        int[][] testHeights = {
            {1,8,6,2,5,4,8,3,7},
            {1,1},
        };

        Solution s = new Solution();
        for (int i = 0; i < testHeights.length; i++) {
            System.out.println("No."+i+" test case:"+s.maxArea(testHeights[i]));
        }
    }
}