package com.smz.solution.binary_tree.n437_path_sum_iii;

import com.smz.utils.IntegerValueBinaryTree;
import com.smz.utils.IntegerValueBinaryTree.TreeNode;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

class Solution {
    // [法一]：前缀和 + DFS
    public int pathSum(TreeNode root, int targetSum) {
        if (root == null) return 0;
        HashMap<Long, Integer> prefixCnt = new HashMap<>();
        prefixCnt.put(0l, 1);

        return dfs(root, targetSum, 0l, prefixCnt);
    }

    public int dfs(TreeNode cur, int targetSum, long lastSum, Map<Long, Integer> prefix) {
        if (cur == null) return 0;
        long curSum = lastSum + cur.val;
        int res = prefix.getOrDefault(curSum - targetSum, 0);
        prefix.put(curSum, prefix.getOrDefault(curSum, 0) + 1);
        res += dfs((TreeNode) cur.left, targetSum, curSum, prefix);
        res += dfs((TreeNode) cur.right, targetSum, curSum, prefix);

        prefix.put(curSum, prefix.get(curSum) - 1);

        return res;
    }

    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Integer[][] testRoots = {
            {10,5,-3,3,2,null,11,3,-2,null,1},
            {5,4,8,11,null,13,4,7,2,null,null,null,null,5,1},
        };
        int[] testTargetSums = {
            8,
            22,
        };

        Solution s = new Solution();
        for (int i = 0; i < testRoots.length; i++) {
            System.out.println("No."+i+" test case: "+ s.pathSum((TreeNode) (new IntegerValueBinaryTree(testRoots[i]).root), testTargetSums[i]));
        }
    }
}