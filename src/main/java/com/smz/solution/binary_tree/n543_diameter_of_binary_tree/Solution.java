package com.smz.solution.binary_tree.n543_diameter_of_binary_tree;

import com.smz.utils.IntegerValueBinaryTree.TreeNode;

class Solution {
    // [法一]：DFS 通过深度计算直径
    private int diameter = -1;
    public int diameterOfBinaryTree(TreeNode root) {
        diameter = 0;
        depthOf(root);

        return diameter;
    }

    private int depthOf(TreeNode root) {
        if (root == null) return 0;
        int ld = depthOf((TreeNode) root.left);
        int rd = depthOf((TreeNode) root.right);
        diameter = Math.max(ld + rd, diameter);

        return Math.max(ld, rd) + 1;
    }
}