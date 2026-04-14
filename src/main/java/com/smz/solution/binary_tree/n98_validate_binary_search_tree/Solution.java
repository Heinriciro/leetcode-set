package com.smz.solution.binary_tree.n98_validate_binary_search_tree;

import com.smz.utils.IntegerValueBinaryTree;
import com.smz.utils.IntegerValueBinaryTree.TreeNode;
import java.lang.reflect.InvocationTargetException;

class Solution {
    // [法一]：递归校验
    public boolean isValidBST(TreeNode root) {
        if (root == null) return false;

        return check(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean check(TreeNode cur, long lower, long upper) {
        if (cur == null) return true;
        if (cur.val <= lower || cur.val >= upper) return false;

        if (cur.left != null && cur.left.val >= cur.val) return false;
        if (cur.right != null && cur.right.val <= cur.val) return false; 

        return check((TreeNode)cur.left, lower, cur.val) && check((TreeNode)cur.right, cur.val, upper);
    }

    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Integer[][] testRoots = {
            {2,1,3},
        };

        Solution s = new Solution();
        for (int i = 0; i< testRoots.length; i++) {
            System.out.println("No."+i+" test case: "+ s.isValidBST((TreeNode) (new IntegerValueBinaryTree(testRoots[i]).root)));
        }
    }
}