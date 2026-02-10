package main.java.com.smz.solution.segment_tree.n3719_longest_balanced_subarray_i;

import java.util.Arrays;
import java.util.HashMap;

class Solution {
    // [法一]：相反数表示奇偶 + 前缀和 + 线段树 
    public int longestBalanced(int[] nums) {
        int n = nums.length;
        LazySegmentTree tree = new LazySegmentTree(n);
        HashMap<Integer, Integer> lastNumPos = new HashMap<>();
        int curSum = 0;
        int ans = 0;

        for (int i = 0; i < n; i++) {
            int num = nums[i];
            int value = num % 2 == 0 ? 1 : -1;
            Integer lastPos = lastNumPos.get(num);
            if (lastPos != null) {
                tree.update(lastPos, i-1, -value);
            } else {
                curSum += value;
                tree.update(i, n-1, value);
            }
            lastNumPos.put(num, i);
            if (curSum == 0) {
                ans = Math.max(ans, i + 1);
            } else {
                int first = tree.findeFirst(0, i-ans-1, curSum);
                if (first >= 0) ans = Math.max(ans, i - first);
            }
        }
        
        return ans;
    }

    public static void main(String[] args) {
        int[][] testNums = {
            {1,2,3,2},
            {2,5,4,3},
            {3,2,2,5,4},
        };

        Solution s = new Solution();
        for (int i = 0; i < testNums.length; i++) {
            System.out.println("No."+i+" test case: "+s.longestBalanced(testNums[i]));
        }
    }
}

class LazySegmentTree {
    int n;
    Node[] tree;
    private class Node {
        int min;
        int max;
        int todo;
    }

    public LazySegmentTree(int n) { // n个线段
        tree = new Node[2 << (32 - Integer.numberOfLeadingZeros(n - 1))]; // n-1 避免n是二次幂的情况
        this.n = n;
        Arrays.setAll(tree, _->new Node());
    }

    public int findeFirst(int startIdx, int endIdx, int target) {
        return findeFirst(0, 0, n-1, startIdx, endIdx, target);
    }

    private int findeFirst(int nodeIdx, int leftIdx, int rightIdx, int startIdx, int endIdx, int target){
        if (endIdx < leftIdx || startIdx > rightIdx || tree[nodeIdx].max < target || tree[nodeIdx].min > target) return -1;
        if (leftIdx == rightIdx) return leftIdx;

        spread(nodeIdx);
        int mid = (leftIdx + rightIdx) >> 1;
        int res = findeFirst(2*nodeIdx + 1, leftIdx, mid, startIdx, endIdx, target);
        if (res == -1) {
            res = findeFirst(2*nodeIdx + 2, mid + 1, rightIdx, startIdx, endIdx, target);
        }

        return res;
    }

    public void update(int targetLeftIdx, int targetRightIdx, int value) {
        update(0, 0, n-1, targetLeftIdx, targetRightIdx, value);
    }

    private void update(int nodeIdx, int leftIdx, int rightIdx, int targetLeftIdx, int targetRightIdx, int value) {
        if (rightIdx < targetLeftIdx || leftIdx > targetRightIdx) return;
        if (targetLeftIdx <= leftIdx && targetRightIdx >= rightIdx) {
            apply(nodeIdx, value);
            return;
        }
        spread(nodeIdx);
        int mid = (leftIdx + rightIdx) >> 1;
        if (targetLeftIdx <= mid) {
            update(2*nodeIdx + 1, leftIdx, mid, targetLeftIdx, targetRightIdx, value);
        }
        if (targetRightIdx > mid) {
            update(2*nodeIdx + 2, mid + 1, rightIdx, targetLeftIdx, targetRightIdx, value);
        }
        maintain(nodeIdx);
    }
    private void apply(int nodeIdx, int value) {
        Node cur = tree[nodeIdx];
        cur.min += value;
        cur.max += value;
        cur.todo += value;
    }
    private void spread(int nodeIdx) {
        Node cur = tree[nodeIdx];
        if (cur.todo != 0) {
            apply(2*nodeIdx + 1, cur.todo);
            apply(2*nodeIdx + 2, cur.todo);
            cur.todo = 0;
        }
    }
    private void maintain(int nodeIdx) {
        Node left = tree[2*nodeIdx + 1];
        Node right = tree[2*nodeIdx + 2];
        tree[nodeIdx].min = Math.min(left.min, right.min);
        tree[nodeIdx].max = Math.max(left.max, right.max);
    }
}