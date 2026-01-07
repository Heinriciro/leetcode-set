package com.smz.solution.dfs.n1339_maximum_product_of_splitted_binary_tree;

import com.smz.utils.SimpleBinaryTree;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Solution {
    private static int MOD = 1000000007;
    private long maximumProduct;
    // [法一]：DFS
    public int maxProduct(TreeNode root) {
        maximumProduct = 0l;
        // 首先计算‘前缀和’即全部元素之和，由于断开任意一边均可形成两个子树，故另一个子树的和可有总和减当前子树和得出
        long totalSum = dfsForSum(root);
        dfsForSplit(root, totalSum);

        return (int)(maximumProduct % MOD);

    }

    public long dfsForSplit(TreeNode root, long total) {
        if (root == null) return 0;
        long val = root.val;
        val += dfsForSplit((TreeNode) root.left, total);
        val += dfsForSplit((TreeNode) root.right, total);

        long other = total - val;
        maximumProduct = Math.max(maximumProduct, val * other);

        return val;
    }

    public int dfsForSum(TreeNode root) {
        if (root == null) return 0;
        int val = root.val;
        val += dfsForSum((TreeNode) root.left);
        val += dfsForSum((TreeNode) root.right);
        return val;
    }

    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ArrayList<Tree> testTrees = new ArrayList<>();
        testTrees.add(new Tree(new Integer[]{1,2,3,4,5,6}));
        testTrees.add(new Tree(new Integer[]{1,null,2,null,null,3,4,null,null,null,null,null,null,5,6}));

        Solution s = new Solution();
        for (int i = 0; i < testTrees.size(); i++) {
            System.out.println("No,"+i+" test case: "+s.maxProduct((TreeNode) testTrees.get(i).root));
        }
    }


    public static class Tree extends SimpleBinaryTree<Integer> {
        public Tree(Integer[] values)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            super(values, TreeNode.class, Integer.class);
        }
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