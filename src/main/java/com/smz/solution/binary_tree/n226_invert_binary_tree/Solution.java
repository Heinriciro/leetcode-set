package com.smz.solution.binary_tree.n226_invert_binary_tree;

import com.smz.utils.IntegerValueBinaryTree.TreeNode;

class Solution {
    // [法一]：递归
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;

        TreeNode left = (TreeNode) root.left;
        TreeNode right = (TreeNode) root.right;
        left = invertTree(left);
        right = invertTree(right);

        root.left = right;
        root.right = left;

        return root;
    }
}