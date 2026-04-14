package com.smz.solution.binary_tree.n104_maximum_depth_of_binary_tree;

import com.smz.utils.IntegerValueBinaryTree.TreeNode;

class Solution {
    // [法一]：DFS
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;

        int leftDepth = maxDepth((TreeNode) root.left);
        int rightDepth = maxDepth((TreeNode) root.right);

        return Math.max(leftDepth, rightDepth) + 1;
    }
}