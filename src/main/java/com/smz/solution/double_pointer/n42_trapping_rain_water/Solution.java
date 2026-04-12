package com.smz.solution.double_pointer.n42_trapping_rain_water;

import java.util.ArrayDeque;
import java.util.Deque;

public class Solution {
    
    // [法一]：双指针，再次练习
    public int trap3(int[] height) {
        int n = height.length;
        int left = 0, right = n - 1;
        int leftMax = 0, rightMax = 0;

        int ans = 0;
        while(left < right) {
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);

            if (height[left] < height[right]) {
                ans += leftMax - height[left];
                left++;
            } else {
                ans += rightMax - height[right];
                right--;
            }
        }

        return ans;
    }

    // [法二]：单调栈，再次练习
    public int trap4(int[] height) {
        int n = height.length;
        Deque<Integer> monoStack = new ArrayDeque<>();

        int ans = 0;
        for (int i = 0; i < n; i++) {
            int cur = height[i];
            while(!monoStack.isEmpty() && height[monoStack.peek()] < cur) {
                int topIdx = monoStack.poll();
                if (!monoStack.isEmpty()) {
                    ans += (Math.min(height[monoStack.peek()], cur) - height[topIdx]) * (i - monoStack.peek() - 1);
                }
            }
            monoStack.push(i);
        }

        return ans;
    }

    // [法三]：DP，再次练习
    public int trap5(int[] height) {
        int n = height.length;
        int[] leftMax = new int[n], rightMax = new int[n];

        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i-1], height[i-1]);
        }

        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i+1], height[i+1]);
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans += Math.max(0, Math.min(leftMax[i], rightMax[i]) - height[i]);
        }

        return ans;
    }


    // [法三]：DP
    public int trap(int[] height) {
        int n = height.length;
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];

        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i-1], height[i-1]);
        }
        for (int i = n - 2; i >= 0 ; i--) {
            rightMax[i] = Math.max(rightMax[i+1], height[i+1]);
        }

        int res = 0;
        for (int i = 0; i < n; i++) {
            int diff = Math.min(leftMax[i], rightMax[i]) - height[i];
            if (diff > 0) res += diff;
        }
        return res;
    }

    // [法二]：双指针
    public int trap2(int[] height) {
        int n = height.length;

        int leftMax = 0;
        int rightMax = 0;
        int left = 0;
        int right = n-1;

        int res = 0;
        while(left < right) {
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);
            if (height[left] < height[right]) {
               res += leftMax - height[left]; 
               left++;
            } else {
                res += rightMax - height[right];
                right--;
            }
        }

        return res;
    }

    // [法一]：单调栈
    public int trap1(int[] height) {
        Deque<Integer> stack = new ArrayDeque<>();
        int n = height.length;
        int res = 0;
        
        for (int i = 0; i < n; i++) {
            while(!stack.isEmpty() && height[stack.peek()] < height[i]) {
                int top = stack.poll();
                if (!stack.isEmpty()) {
                    res += (i - stack.peek() - 1) * (Math.min(height[i], height[stack.peek()]) - height[top]);
                }
            }

            stack.push(i);
        }
        
        return res;
    }

    public static void main(String[] args) {
        int[][] testHeights = {
            {0,1,0,2,1,0,1,3,2,1,2,1},
            {4,2,0,3,2,5},
        };

        Solution s = new Solution();
        for (int i = 0; i < testHeights.length; i++) {
            System.out.println("No."+i+" test case:"+s.trap4(testHeights[i]));
        }
    }
}