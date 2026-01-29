package com.smz.solution.floyd.n2976_minimum_cost_to_convert_string_i;
// 分析： 由于存在相同原字母，和相同目标字母，显然需要记录每一种转换的最低花费
//           而且存在多步转换，即A->B->C的转换，需要将这些分步转换直接记录在A->C中便于最终的替换查找
//           那么得到法一：可以维护两个哈希表，一个记录每个字母能够转换成为的所有字母对应的最小花费，一个记录每个字母能够从所有字母转换而来的对应最小花费，最终能够得到记录了所有转换可能得哈希表

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class Solution {
    // [法二]：Floyd算法: 解决多个节点之间最短路径问题
    public long minimumCost(String source, String target, char[] original, char[] changed, int[] cost) {
        final int INF = Integer.MAX_VALUE / 2;
        int[][] dis = new int[26][26];
        for (int i = 0; i < 26; i++) {
            Arrays.fill(dis[i], INF);
            dis[i][i] = 0;
        }
        for (int i = 0; i < cost.length; i++) {
            int x = original[i] - 'a';
            int y = changed[i] - 'a';
            dis[x][y] = Math.min(dis[x][y], cost[i]);
        }
        for (int k = 0; k < 26; k++) {
            for (int i = 0; i < 26; i++) {
                if (dis[i][k] == INF) {
                    continue; // 巨大优化！
                }
                for (int j = 0; j < 26; j++) {
                    dis[i][j] = Math.min(dis[i][j], dis[i][k] + dis[k][j]);
                }
            }
        }

        long ans = 0;
        for (int i = 0; i < source.length(); i++) {
            int d = dis[source.charAt(i) - 'a'][target.charAt(i) - 'a'];
            if (d == INF) {
                return -1;
            }
            ans += d;
        }
        return ans;
    }

    // [法一]：ConcurrentHashMap + 预处理字母转移路径
    //         虽未超时，但执行时间较长(有使用ConcurrentHashMap的原因)
    public long minimumCost1(String source, String target, char[] original, char[] changed, int[] cost) {
        Map<Character, Map<Character, Long>> to = new ConcurrentHashMap<>();
        Map<Character, Map<Character, Long>> from = new ConcurrentHashMap<>();

        for (int i = 0; i < original.length; i++) {
            char s = original[i];
            char t = changed[i];
            long c = cost[i];
            Map<Character, Long> sto = to.computeIfAbsent(s, _-> new ConcurrentHashMap<>());
            Map<Character, Long> tfrom = from.computeIfAbsent(t, _-> new ConcurrentHashMap<>());
            if (!sto.containsKey(t) || sto.containsKey(t) && c < sto.get(t)) {
                sto.put(t, c);
                tfrom.put(s, c);
            }
        }
        int affected = 1;

        while (affected !=0) {
            affected = 0;
            for (Character s : to.keySet()) {
                Map<Character, Long> sto = to.get(s); 
                for (Character t : sto.keySet()) {
                    Map<Character, Long> tfrom = from.get(t);
                    Long c = sto.get(t);
                    // 更新多步转换
                    // prev -> s -> t
                    Map<Character, Long> sfrom = from.computeIfAbsent(s, _->new ConcurrentHashMap<>());
                    for (Character prev : sfrom.keySet()) {
                        if (Objects.equals(prev, t)) continue;
                        Long cp = sfrom.get(prev);
                        Map<Character, Long> prevto = to.computeIfAbsent(prev, _->new ConcurrentHashMap<>());
                        if (!prevto.containsKey(t) || prevto.containsKey(t) && prevto.get(t) > c + cp) {
                            prevto.put(t, c + cp);
                            tfrom.put(prev, c + cp);
                            affected++;
                        }
                    }
                    // s -> t-> next
                    Map<Character, Long> tto = to.computeIfAbsent(t, _ -> new ConcurrentHashMap<>());
                    for (Character next : tto.keySet()) {
                        if (Objects.equals(s, next)) continue;
                        Long cn = tto.get(next);
                        Map<Character, Long> nextFrom = from.computeIfAbsent(next, _ -> new ConcurrentHashMap<>());
                        if (!sto.containsKey(next) || sto.containsKey(next) && sto.get(next) > c + cn) {
                            sto.put(next, c + cn);
                            nextFrom.put(s, c + cn);
                            affected++;
                        }
                    }
                }
            }
        }


        long ans = 0;
        for (int i = 0; i < source.length(); i++) {
            char s = source.charAt(i);
            char t = target.charAt(i);
            if (s != t) {
                if (!to.containsKey(s)) return -1;
                Map<Character, Long> sto = to.get(s);
                if (!sto.containsKey(t)) return -1;
                ans += sto.get(t);
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        String[] testSources = {
            "aaadbdcdac",
            "abcd",
            "aaaa",
            "abcd",
        };
        String[] testTargets = {
            "cdbabaddba",
            "acbe",
            "bbbb",
            "abce",
        };
        char[][] testOriginals = {
            {'a','c','b','d','b','a','c'},
            {'a','b','c','c','e','d'},
            {'a','c'},
            {'a'},
        };
        char[][] testChangeds = {
            {'c','a','d','b','c','b','d'},
            {'b','c','b','e','b','e'},
            {'c','b'},
            {'e'}
        };
        int[][] testCosts = {
            {7,2,1,3,6,1,7},
            {2,5,5,1,2,20},
            {1,2},
            {10000},
        };

        Solution s = new Solution();
        for (int i = 0; i < testSources.length; i++) {
            System.out.println("No."+i+" test case: "+s.minimumCost(testSources[i], testTargets[i], testOriginals[i], testChangeds[i], testCosts[i]));
        }
    }
}