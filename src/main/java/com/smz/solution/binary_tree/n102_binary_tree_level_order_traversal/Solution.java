package com.smz.solution.binary_tree.n102_binary_tree_level_order_traversal;

import com.smz.utils.IntegerValueBinaryTree.TreeNode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Solution {
    // [法一]：层序遍历
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) return ans;

        Queue<TreeNode> que = new LinkedList<>();
        que.offer(root);

        while (!que.isEmpty()) {
            List<Integer> layer = new ArrayList<>();
            int curSize = que.size();

            for (int i = 0; i < curSize; i++) {
                TreeNode cur = que.poll();
                if (cur != null) {
                    layer.add(cur.val);
                    if (cur.left != null) que.offer((TreeNode) cur.left);
                    if (cur.right != null) que.offer((TreeNode) cur.right);
                }
            }

            ans.add(layer);
        }

        return ans;
    }
}