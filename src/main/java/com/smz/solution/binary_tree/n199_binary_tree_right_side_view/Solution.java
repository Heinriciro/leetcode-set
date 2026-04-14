package com.smz.solution.binary_tree.n199_binary_tree_right_side_view;

import com.smz.utils.IntegerValueBinaryTree.TreeNode;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

class Solution {
    // [法一]：层序遍历，添加每层最后一个入结果
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.offer(root);


        while (!stack.isEmpty()) {
            int size = stack.size();
            for (int i = 0; i < size; i++) {
                TreeNode cur = stack.removeFirst();
                if (cur.left != null) stack.offer((TreeNode) cur.left);
                if (cur.right != null) stack.offer((TreeNode) cur.right);
                if (i == size - 1) res.add(cur.val);
            }
        }

        return res;
    }
}