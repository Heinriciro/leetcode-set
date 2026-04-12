package com.smz.solution.double_pointer.n11_container_with_most_water;

public class Solution {
    // [法一]：双指针, 再次练习
    public int maxArea1(int[] height) {
        int n = height.length;
        int left = 0;
        int right = n - 1;

        int res = 0;
        while (left < right) {
           res = Math.max(res, Math.min(height[right],height[left]) * (right - left)); 

           if (left < right) left++;
           else right--;
        }

        return res;
    }

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
            {8,7,2,1},
            {1,8,6,2,5,4,8,3,7},
            {1,1},
        };

        Solution s = new Solution();
        for (int i = 0; i < testHeights.length; i++) {
            System.out.println("No."+i+" test case:"+s.maxArea(testHeights[i]));
        }
    }
}