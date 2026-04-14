package com.smz.solution.binary_tree.n94_binary_tree_inorder_traversal;

import com.smz.utils.IntegerValueBinaryTree.TreeNode;
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();

        inorderRecur(root, res);

        return res;
    }
    
    private void inorderRecur(TreeNode cur, List<Integer> res) {
        if (cur == null) return;
        inorderRecur((TreeNode) cur.left, res);
        res.add(cur.val);
        inorderRecur((TreeNode) cur.right, res);
    }
}