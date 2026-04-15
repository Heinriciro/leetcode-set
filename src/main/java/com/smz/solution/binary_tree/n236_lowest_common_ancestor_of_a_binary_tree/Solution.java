package com.smz.solution.binary_tree.n236_lowest_common_ancestor_of_a_binary_tree;

import com.smz.utils.IntegerValueBinaryTree;
import com.smz.utils.IntegerValueBinaryTree.TreeNode;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

class Solution {
    // [法一]：递归 + 哈希表存储每个子树是否包含对应节点
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        HashMap<TreeNode, Boolean> isExistIn = new HashMap<>();
        
        return dfs(root, p, q, isExistIn);
    }

    private TreeNode dfs(TreeNode cur, TreeNode p, TreeNode q, Map<TreeNode, Boolean> isExistIn) {
        if (cur == null) return null;
        
        TreeNode res = dfs((TreeNode) cur.left, p, q, isExistIn);
        if (res != null) return res;
        res = dfs((TreeNode) cur.right, p, q, isExistIn);
        if (res != null) return res;

        boolean existLeft = cur.left == null ? false : isExistIn.get((TreeNode) cur.left);
        boolean existRight = cur.right == null ? false : isExistIn.get((TreeNode) cur.right);
        if (existLeft && existRight || (cur == p || cur == q) && (existLeft || existRight)) return cur;

        isExistIn.put(cur, cur == p || cur == q || existLeft || existRight);
        return null;
    }

    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Integer[][] testRoots = {
            {3,5,1,6,2,0,8,null,null,7,4},
            {3,5,1,6,2,0,8,null,null,7,4},
            {1,2},
        };
        int[] testPs = {
            5,
            5,
            1,
        };
        int[] testQs = {
            1,
            4,
            2,
        };

        Solution s = new Solution();
        for (int i = 0; i < testRoots.length; i++) {
            IntegerValueBinaryTree tree = new IntegerValueBinaryTree(testRoots[i]);
            TreeNode res = s.lowestCommonAncestor((TreeNode) tree.root, tree.getByValue(testPs[i]), tree.getByValue(testQs[i]));
            System.out.println("No."+i+" test case: " + (res == null ? "null" : res.val));
        }
    }
}