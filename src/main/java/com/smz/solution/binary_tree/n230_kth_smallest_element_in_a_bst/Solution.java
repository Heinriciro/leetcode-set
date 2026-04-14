package com.smz.solution.binary_tree.n230_kth_smallest_element_in_a_bst;

import com.smz.utils.IntegerValueBinaryTree;
import com.smz.utils.IntegerValueBinaryTree.TreeNode;
import java.lang.reflect.InvocationTargetException;

class Solution {
    // [法一]：按中序遍历递归，计数遍历节点的数量，返回第k个
    int cnt = 0;
    public int kthSmallest(TreeNode root, int k) {
        if (root == null) return -1;

        return findKthSmallest(root, k);
    }

    public int findKthSmallest(TreeNode root, int k) {
        if (root == null) return -1;

        int res = findKthSmallest((TreeNode) root.left, k);
        if (res != -1) return res;
        cnt++;
        if (cnt == k) return root.val;
        
        return findKthSmallest((TreeNode) root.right, k);
    }

    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Integer[][] testRoots = {
            {5,3,6,2,4,null,null,1},
            {3,1,4,null,2},
        };
        int[] testKs = {3,1};

        Solution s = new Solution();
        for (int i = 0; i < testRoots.length; i++) {
            System.out.println("No."+i+" test case: " + s.kthSmallest((TreeNode) (new IntegerValueBinaryTree(testRoots[i]).root), testKs[i]));
        }
    }
}