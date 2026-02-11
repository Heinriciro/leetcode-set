package com.smz.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class SimpleBinaryTree<T1> {
    public SimpleBinaryTreeNode<T1> root;

    public SimpleBinaryTree(T1[] values, Class<? extends SimpleBinaryTreeNode<T1>> clazz, Class<T1> typeVariableClazz)
        throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (values == null || values.length == 0) {
            root = null;
            return;
        }

        Constructor<? extends SimpleBinaryTreeNode<T1>> nodeConstructor = clazz.getConstructor(typeVariableClazz);
        
        root = buildTree(values, 0, nodeConstructor);
    }

    private SimpleBinaryTreeNode<T1> buildTree(T1[] values, int index, Constructor<? extends SimpleBinaryTreeNode<T1>> nodeConstructor)
        throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (index >= values.length || values[index] == null) {
            return null;
        }

        SimpleBinaryTreeNode<T1> node = nodeConstructor.newInstance(values[index]);
        node.left = buildTree(values, 2 * index + 1, nodeConstructor);
        node.right = buildTree(values, 2 * index + 2, nodeConstructor);
        return node;
    }

    public static class SimpleBinaryTreeNode<T2> {
        public T2 val;
        public SimpleBinaryTreeNode<T2> left;
        public SimpleBinaryTreeNode<T2> right;

        public SimpleBinaryTreeNode(T2 x) {
            val = x;
        }
        public SimpleBinaryTreeNode(T2 x, SimpleBinaryTreeNode<T2> left, SimpleBinaryTreeNode<T2> right) {
            val = x;
            this.left = left;
            this.right = right;
        }

        public void print() {
            ArrayList<SimpleBinaryTreeNode<T2>> stack = new ArrayList<>();
            SimpleBinaryTreeNode<T2> cur = this;
            stack.add(cur);

            while(!stack.isEmpty()) {
                for (int i = 0; i < stack.size(); i++) {
                    cur = stack.removeFirst();
                    if (cur != null) {
                        System.out.print(cur.val.toString() + " ");
                        stack.addLast(cur.left);
                        stack.addLast(cur.right);

                    } else {
                        System.out.print("null ");
                    }
                }
                System.out.println();
            }
        }
    }

}

