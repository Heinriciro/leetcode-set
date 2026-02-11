package com.smz.solution.binary_search_tree.n1382_balance_a_binary_search_tree;
import com.smz.utils.IntegerValueBinaryTree;
import com.smz.utils.IntegerValueBinaryTree.TreeNode;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

class Solution {
    // [法一]：中序遍历 + 贪心二分构造平衡二叉搜索树
    public TreeNode balanceBST(TreeNode root) {
        List<Integer> inOrderBST = inorderTraversal(root);

        return buildBalancedBST(inOrderBST, 0 , inOrderBST.size() - 1);        
    }

    private TreeNode buildBalancedBST(List<Integer> inOrderBST, int start, int end) {
        if (start > end) return null;
        if (start == end) return new TreeNode(inOrderBST.get(start));
        int mid = (start + end) >> 1;
        TreeNode root = new TreeNode(inOrderBST.get(mid));
        root.left = buildBalancedBST(inOrderBST, start, mid - 1);
        root.right = buildBalancedBST(inOrderBST, mid + 1, end);
        return root;
    }

    private List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        dfs(root, res);
        return res;
    }

    private void dfs(TreeNode root, List<Integer> res) {
        if (root == null) return;
        dfs((TreeNode) root.left, res);
        res.add(root.val);
        dfs((TreeNode) root.right, res);
    }

    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        IntegerValueBinaryTree[] testTrees = {
            new IntegerValueBinaryTree(new Integer[]{1, null, 2, null, null, null, 3, null, null, null, null, null, null, null, 4}),
            new IntegerValueBinaryTree(new Integer[]{2,1,3}),
        };

        Solution s = new Solution();
        for (int i = 0; i < testTrees.length; i++) {
            System.out.println("No."+i+" test case: ");
            s.balanceBST((TreeNode) testTrees[i].root).print();
        }
    }
}