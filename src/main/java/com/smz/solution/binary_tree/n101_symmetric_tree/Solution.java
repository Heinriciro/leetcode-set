package com.smz.solution.binary_tree.n101_symmetric_tree;

import com.smz.utils.IntegerValueBinaryTree.TreeNode;
import java.util.Objects;

class Solution {
    // [法一]：递归
    public boolean isSymmetric(TreeNode root) {
        return check((TreeNode) root.left, (TreeNode) root.right);
    }

    private boolean check(TreeNode left, TreeNode right) {
        if (left == null && right == null) return true;
        if (left != null && right != null) {
            if (Objects.equals(left.val, right.val)) return false;
            return check((TreeNode) left.left, (TreeNode) right.right) && check((TreeNode) left.right, (TreeNode) right.left);
        }

        return false;
    }
}