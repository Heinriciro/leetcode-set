package com.smz.solution.binary_tree.n105_construct_binary_tree_from_preorder_and_inorder_traversal;

import com.smz.utils.IntegerValueBinaryTree.TreeNode;
import java.util.HashMap;
import java.util.Map;

class Solution {
    // [法一]：通过寻找根节点位置，和左右子树对应起始坐标，递归创建
    Map<Integer,Integer> val2Idx = new HashMap<>();
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        for (int i = 0; i < inorder.length; i++) {
            val2Idx.put(inorder[i], i);
        }

        return partitionBuild(preorder, 0, preorder.length-1, inorder, 0, inorder.length-1);
        
    }

    private TreeNode partitionBuild(int[] preorder, int preL, int preR, int[] inorder, int inL, int inR) {
        if (inL > inR && preL > preR) return null;
        
        int rootVal = preorder[preL];
        int rootIdx = val2Idx.get(rootVal);
        int leftSize = rootIdx - inL;
        int rightSize = inR - rootIdx;

        if (preR - preL - leftSize != rightSize) throw new IllegalArgumentException("Wrong size.");

        TreeNode cur = new TreeNode(rootVal);
        TreeNode left = partitionBuild(preorder, preL + 1, preL + leftSize, inorder, inL, rootIdx - 1);
        TreeNode right = partitionBuild(preorder, preL + leftSize + 1, preR, inorder, rootIdx + 1, inR);

        cur.left = left;
        cur.right = right;

        return cur;
    }

    public static void main(String[] args) {
        int[][] testPreorders = {
            {3,9,20,15,7},
            {-1},
        };
        int[][] testInorders = {
            {9,3,15,20,7},
            {-1},
        };

        Solution s = new Solution();
        for (int i = 0; i < testPreorders.length; i++) {
            System.out.print("No."+i+" test case: "+TreeNode.levelOrder(s.buildTree(testPreorders[i], testInorders[i])));
            
        }

    }
}