package com.smz.solution.bfs.n1161_maximum_level_sum_of_a_binary_tree;

import com.smz.utils.SimpleBinaryTree;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.ArrayList;
class Solution {
    
    // [法一]：BFS 
    public int maxLevelSum(TreeNode root) {
        ArrayDeque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int maxSum = Integer.MIN_VALUE;
        int resultLevel = 0;
        int curLevel = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            int currentLevelSum = 0;
            curLevel++;
            for (int i = 0; i < levelSize; i++) {
                TreeNode currentNode = queue.poll();
                currentLevelSum += currentNode.val;
                if (currentNode.left != null) {
                    queue.offer((TreeNode) currentNode.left);
                }
                if (currentNode.right != null) {
                    queue.offer((TreeNode) currentNode.right);
                }
            }
            if (currentLevelSum > maxSum) {
                maxSum = currentLevelSum;
                resultLevel = curLevel;
            }
        }

        return resultLevel;
    }

    public static void main(String[] args)
        throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ArrayList<Tree> testBinaryTree = new ArrayList<>();
        testBinaryTree.add(new Tree(new Integer[]{1,7,0,7,-8,null,null}));
        testBinaryTree.add(new Tree(new Integer[]{989,null,10250,98693,-89388,null,null,null,-32127}));

        Solution s = new Solution();
        for (int i =0; i < testBinaryTree.size(); i++) {
            int result = s.maxLevelSum((TreeNode) testBinaryTree.get(i).root);
            System.out.println("No." + (i + 1) + " test case: " + result);
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