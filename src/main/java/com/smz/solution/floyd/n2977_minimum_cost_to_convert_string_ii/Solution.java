package com.smz.solution.floyd.n2977_minimum_cost_to_convert_string_ii;

import com.smz.utils.Trie;
import java.util.Arrays;

public class Solution {
    // [法一]：前缀树 + Floyd + DP
    public long minimumCost(String source, String target, String[] original, String[] changed, int[] cost) {
        Trie prefixTree = new Trie();
        int n = original.length + changed.length;
        int[][] minCost = new int[n][n]; // 数组尺寸最大可能即为original 和 change中所有字符串全部不相同的情况; 同时，由于可能转换对最多有100个，理论的cost最大值为10^6 * 100, 可以使用int类型
        for (int i = 0; i < n; i++) {
            Arrays.fill(minCost[i], Integer.MAX_VALUE / 2); // 单个cost最大为10^6，int最大值的一半为10*9,保证最大的情况下，防止两个最大值相加造成溢出
            minCost[i][i] = 0; // 自己到自己无需转换，等价于成本为0
        }
        for (int i = 0; i < original.length; i++) {
            int sid = prefixTree.put(original[i]);
            int tid = prefixTree.put(changed[i]);
            minCost[sid][tid] = Math.min(minCost[sid][tid], cost[i]); // 可能存在多种成本的转换，取最小值
        }

        // Floyd算法，预处理所有字符串之间的最小转换成本
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                if (minCost[i][k] == Integer.MAX_VALUE / 2) {
                    continue; // 此时不存在 i -> k 的转换路径，所以直接跳过
                }
                for (int j = 0; j < n; j++) {
                    minCost[i][j] = Math.min(minCost[i][j], minCost[i][k] + minCost[k][j]);
                }
            }
        }

        // 使用动态规划找出最小转换成本
        long[] dp = new long[source.length() + 1]; // dp[i]表示 source[i...n-1] 转换为 target[i...n-1] 的最小成本, dp[n] = 0作为边界条件
        for (int i = source.length() - 1; i >= 0; i--) {
            dp[i] = source.charAt(i) == target.charAt(i) ? dp[i + 1] : Long.MAX_VALUE / 2; // 初始化，如果两个字符相同，则其中一种方案是不需要转换，直接继承后续的最小成本，
                                                                                       // 若不同，初始化为最大值，继续枚举其他方案
            Trie.Node s = prefixTree.root, t = prefixTree.root; // 从根节点开始，查找存在的转换组合
            for (int j = i; j < source.length(); j++) {
                char sc = source.charAt(j);
                char tc = target.charAt(j);
                s = s.children[sc - 'a'];
                t = t.children[tc - 'a'];
                if (s == null || t == null) {
                    break; // 不存在对应的转换字符串，直接跳出
                }
                if (s.sid == -1 || t.sid == -1) {
                    continue; // 不是终止节点，说明到目前位置还未找到完整的转换字符串，继续往下找
                }
                // 找到了一对完整的转换字符串
                int min = minCost[s.sid][t.sid];
                if (min == Integer.MAX_VALUE / 2) {
                    continue; // 虽然找到了转换字符串，但两者之间并不存在转换路径
                }
                dp[i] = Math.min(dp[i], min + dp[j + 1]); // 此时的子串为source[i...j], 存在转换为 target[i...j]的路径，所以此时dp[i]的值即为
                                                                            // 将子串转换的成本minCost[s.sid][t.sid]， 加上后续部分的最小转换成本dp[j+1]
            }
        }
        return dp[0] == Long.MAX_VALUE / 2 ? -1 : dp[0];
    }

    public static void main(String[] args) {
        String[] testSources = {
            "babhbaaabbabehhhaaea",
            "abcd",
            "abcdefgh",
            "abcdefgh",
        };
        String[] testTargets = {
            "aabhhaebehbaehahbahb",
            "acbe",
            "acdeeghh",
            "addddddd",
        };
        String[][] testOriginals = {
            {"b","b","a","a","e"},
            {"a","b","c","c","e","d"},
            {"bcd","fgh","thh"},
            {"bcd","defgh"},
        };
        String[][] testChangeds = {
            {"a","h","e","b","h"},
            {"b","c","b","e","b","e"},
            {"cde","thh","ghh"},
            {"ddd","ddddd"},
        };
        int[][] testCosts = {
            {2,10,8,4,6},
            {2,5,5,1,2,20},
            {1,3,5},
            {100,1578},
        };

        Solution s = new Solution();
        for (int i = 0; i < testSources.length; i++) {
            System.out.println("No."+i+" test case: "+s.minimumCost(testSources[i], testTargets[i], testOriginals[i], testChangeds[i], testCosts[i]));
        }
    }
}