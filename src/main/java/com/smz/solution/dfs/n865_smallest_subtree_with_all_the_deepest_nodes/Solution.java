package com.smz.solution.dfs.n865_smallest_subtree_with_all_the_deepest_nodes;

import com.smz.utils.IntegerValueBinaryTree;
import com.smz.utils.IntegerValueBinaryTree.TreeNode;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Solution {
    // [法一]：DFS
    // 当左子树深度和右子树深度一样且大于或等于记录的最大深度时，说明此时需要更新结果
    private TreeNode res;
    private int resDepth;
    public TreeNode subtreeWithAllDeepest(TreeNode root) {
        res = null;
        resDepth = 0;

        dfs(root, 0);

        return res;
    }

    public int dfs(TreeNode root, int prevLevel) {
        if (root == null) return prevLevel;
        prevLevel++;

        int leftDepth = dfs((TreeNode) root.left, prevLevel);
        int rightDepth = dfs((TreeNode) root.right, prevLevel);

        if (leftDepth == rightDepth) {
            if (resDepth <= leftDepth) {
                resDepth = leftDepth;
                res = root;
            }
                return leftDepth;
        } else {
            return Math.max(leftDepth, rightDepth);   
        }
    }

    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ArrayList<IntegerValueBinaryTree> testTrees = new ArrayList<>();
        testTrees.add(new IntegerValueBinaryTree(new Integer[]{3,5,1,6,2,0,8,null,null,7,4}));
        testTrees.add(new IntegerValueBinaryTree(new Integer[]{1}));
        testTrees.add(new IntegerValueBinaryTree(new Integer[]{0,1,3,null,2}));

        Solution s = new Solution();
        for (int i = 0; i < testTrees.size(); i++) {
            System.out.println("No."+i+" test case: "+s.subtreeWithAllDeepest((TreeNode) testTrees.get(i).root).val);
        }
    }
}