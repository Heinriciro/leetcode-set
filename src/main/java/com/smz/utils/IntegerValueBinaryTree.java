package com.smz.utils;

import java.lang.reflect.InvocationTargetException;

    /// A SimpleBinaryTree with a Integer value.
    public class IntegerValueBinaryTree extends SimpleBinaryTree<Integer> {
        public IntegerValueBinaryTree(Integer[] values)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            super(values, TreeNode.class, Integer.class);
        }

        public static class TreeNode extends SimpleBinaryTree.SimpleBinaryTreeNode<Integer> {
            public TreeNode(Integer x) {
                super(x);
            }

            public TreeNode(Integer x, TreeNode left, TreeNode right) {
                super(x, left, right);
            }
        }
    }


