package com.smz.solution.dfs.n110_balanced_binary_tree;
import com.smz.utils.IntegerValueBinaryTree;
import com.smz.utils.IntegerValueBinaryTree.TreeNode;
import java.lang.reflect.InvocationTargetException;

class Solution {
    // [法一]：DFS
    // 获取子树高度，并且令高度为-1时为不平衡状态
    public boolean isBalanced(TreeNode root) {
        return dfs((TreeNode) root) >= 0;
    }

    private int dfs(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 1;
        int leftHeight = 0;
        int rightHeight = 0;
        if (root.left != null) leftHeight = dfs((TreeNode) root.left);
        if (root.right !=null) rightHeight = dfs((TreeNode) root.right);
        if (leftHeight == -1 || rightHeight == -1) return -1;
        if (leftHeight - rightHeight > 1 || leftHeight - rightHeight < -1) return -1;

        return Math.max(leftHeight, rightHeight) + 1;
    }

    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        IntegerValueBinaryTree[] testTrees = {
            new IntegerValueBinaryTree(new Integer[]{1,2,2,3,null,null,3,4,null,null,null,null,null,null,4}),
            new IntegerValueBinaryTree(new Integer[]{1,2,2,3,3,null,null,4,4,null,null,null,null}),
            new IntegerValueBinaryTree(new Integer[]{3,9,20,null,null,15,7}),
            new IntegerValueBinaryTree(new Integer[]{}),
        };

        Solution s = new Solution();
        for (int i = 0; i < testTrees.length; i++) {
            System.out.println("No."+i+" test case: "+s.isBalanced((TreeNode) testTrees[i].root));
        }
        
    }
}
