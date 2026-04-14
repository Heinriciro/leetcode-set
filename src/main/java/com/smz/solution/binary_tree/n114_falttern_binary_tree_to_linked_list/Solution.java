package com.smz.solution.binary_tree.n114_falttern_binary_tree_to_linked_list;

import com.smz.utils.IntegerValueBinaryTree.TreeNode;
import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    // [法三]: 找前序节点/莫里斯遍历
    public void flatten(TreeNode root) {
        if (root == null) return;
        TreeNode cur = root;

        while (cur != null) {
            if (cur.left != null) {
                TreeNode pre = (TreeNode) cur.left;
                while (pre.right != null) {
                    pre = (TreeNode) pre.right;
                }

                pre.right = cur.right;
                cur.right = cur.left;
                cur.left = null;
            }
            cur = (TreeNode) cur.right;
        }
    }

    // [法二]：前序遍历 + 栈， 在遍历过程中直接连接
    public void flatten1(TreeNode root) {
        if (root == null) return;

        Deque<TreeNode> que = new ArrayDeque<>();
        que.push(root);

        TreeNode pre = null;
        while (!que.isEmpty()) {
            TreeNode cur = que.poll();
            if (pre != null) {
                pre.left = null;
                pre.right = cur;
            }
            pre = cur;

            if (cur.right != null) que.push((TreeNode) cur.right);
            if (cur.left != null) que.push((TreeNode) cur.left);
        }
    }

    // [法一]：前序遍历 + 队列/列表
    public void flatten1(TreeNode root) {
        if (root == null) return;
        Deque<TreeNode> que = new ArrayDeque<>();
        preOrder(root, que);

        TreeNode pre = new TreeNode();
        while (!que.isEmpty()) {
            TreeNode cur = que.removeFirst();
            pre.left = null;
            pre.right = cur;
            pre = cur;
        }
    }

    private void preOrder(TreeNode cur, Deque<TreeNode> que) {
        if (cur == null) return;
        que.offer(cur);
        preOrder((TreeNode) cur.left, que);
        preOrder((TreeNode) cur.right, que);
    }
}