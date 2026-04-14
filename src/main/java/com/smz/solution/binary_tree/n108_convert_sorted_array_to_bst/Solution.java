package com.smz.solution.binary_tree.n108_convert_sorted_array_to_bst;

import com.smz.utils.IntegerValueBinaryTree.TreeNode;

public class Solution {
    // [法一]：据中序遍历递归创建
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums.length == 0) return null;
        return recurConvert(nums, 0, nums.length-1);
    }

    private TreeNode recurConvert(int[] nums, int start, int end) {
        if (start > end) return null;
        int mid = (start + end) >> 1;

        TreeNode cur = new TreeNode(nums[mid]);
        TreeNode left = recurConvert(nums, start, mid - 1);
        TreeNode right = recurConvert(nums, mid + 1, end);

        cur.left = left;
        cur.right = right;

        return cur;
    }
}
