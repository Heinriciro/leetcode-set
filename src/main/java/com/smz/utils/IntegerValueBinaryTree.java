package com.smz.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

    /// A SimpleBinaryTree with a Integer value.
    public class IntegerValueBinaryTree extends SimpleBinaryTree<Integer> {
        Map<Integer, TreeNode> nodeMap;
        public IntegerValueBinaryTree(Integer[] values)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            super(values, TreeNode.class, Integer.class);
            nodeMap = new HashMap<>();
            storeNode();
        }

        public TreeNode getByValue(int val) {
            return nodeMap.get(val);
        }

        private void storeNode(){
            recurStore((TreeNode) root);
        }

        private void recurStore(TreeNode cur) {
            if (cur == null) return;
            nodeMap.put(cur.val, cur);
            recurStore((TreeNode) cur.left);
            recurStore((TreeNode) cur.right);
        }

        public static class TreeNode extends SimpleBinaryTree.SimpleBinaryTreeNode<Integer> {
            public TreeNode(Integer x) {
                super(x);
            }

            public TreeNode() {}

            public TreeNode(Integer x, TreeNode left, TreeNode right) {
                super(x, left, right);
            }

            public static List<List<Integer>> levelOrder(TreeNode root) {
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
    }


