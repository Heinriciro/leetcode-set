package com.smz.solution.dijkstra.n3650_minimum_cost_path_with_edge_reversals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Solution {
    // [法二]：Djistra + 小顶堆(优先队列)
    public int minCost(int n, int[][] edges) {
        @SuppressWarnings("unchecked")
        List<int[]>[] adjacent  = new ArrayList[n];
        Arrays.setAll(adjacent, _->new ArrayList<>());

        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], c = edge[2];
            adjacent[u].add(new int[]{v, c});
            adjacent[v].add(new int[]{u, 2*c});
        }

        int[] minDis = new int[n];
        Arrays.fill(minDis, Integer.MAX_VALUE);
        PriorityQueue<int[]> p = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        p.offer(new int[]{0, 0});

        while (!p.isEmpty()) {
            int[] min = p.poll();
            int dis = min[0];
            int node = min[1];
            if (dis > minDis[node]) continue;
            if (node == n-1) return dis;

            for (int[] edge : adjacent[node]) {
                int newDis = dis + edge[1];
                int target = edge[0];
                if (newDis < minDis[target]) {
                    minDis[target] = newDis;
                    p.offer(new int[]{newDis, target});
                }
            }
        }

        return -1;
    }

    // [法一]：DFS + 剪枝
    //   超时
    private int min;
    public int minCost1(int n, int[][] edges) {
        min = Integer.MAX_VALUE;
        Map<Integer, List<List<Integer>>> out = new HashMap<>();
        Map<Integer, List<List<Integer>>> in = new HashMap<>();
        boolean[] visited = new boolean[n];
        boolean[] switched = new boolean[n];

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int c = edge[2];

            List<List<Integer>> outList = out.computeIfAbsent(u, (_) -> {
                    List<List<Integer>> list = new ArrayList<>();
                    list.add(new ArrayList<>());
                    list.add(new ArrayList<>());
                    return list;
                }
            );
            outList.get(0).add(v);
            outList.get(1).add(c);

            List<List<Integer>> inList = in.computeIfAbsent(v, (_) -> {
                    List<List<Integer>> list = new ArrayList<>();
                    list.add(new ArrayList<>());
                    list.add(new ArrayList<>());
                    return list;
                }
            );
            inList.get(0).add(u);
            inList.get(1).add(c);
        }

        dfs(0, n, out, in, visited, switched, 0);

        return min == Integer.MAX_VALUE ? -1 : min;
    }

    private void dfs(int cur, int n, Map<Integer, List<List<Integer>>> out, Map<Integer, List<List<Integer>>> in, boolean[] visited, boolean[] switched, long tCost) {
        if (cur == n - 1) min = Math.min(min, (int) tCost);
        
        visited[cur] = true;

        if (out.containsKey(cur)) {
            List<List<Integer>> outList = out.get(cur);
            for (int i = 0; i < outList.get(0).size(); i++) {
                int next = outList.get(0).get(i);
                if (!visited[next] && tCost + outList.get(1).get(i) < min) {
                    dfs(next, n, out, in, visited, switched, tCost + outList.get(1).get(i)); 
                }
            }
        }

        if (in.containsKey(cur)) {
            List<List<Integer>> inList = in.get(cur);
            for (int i = 0; i < inList.get(0).size(); i++) {
                int next = inList.get(0).get(i);
                if (!visited[next] && !switched[cur] && tCost + inList.get(1).get(i)*2 < min) {
                    switched[cur] = true;
                    dfs(next, n, out, in, visited, switched, tCost + inList.get(1).get(i)*2);
                    switched[cur] = false;
                }
            }
        }
        visited[cur] = false;
    }

    public static void main(String[] args) {
        int[][][] testEdges = {
            {{7,12,7},{2,12,10},{3,12,15},{9,7,23},{4,1,15},{12,8,17},{8,3,6},{3,0,19},{1,0,14},{2,0,20},{2,1,16},{6,8,25},{6,9,5},{2,8,13},{0,8,15},{0,10,16},{12,1,12},{5,6,4},{7,8,11},{9,6,9},{9,12,12},{5,4,1},{1,9,24},{0,6,11},{2,11,19},{3,8,22},{5,12,25},{12,3,5},{10,3,21},{12,6,3},{9,2,10},{3,4,5},{2,6,7},{7,11,15},{6,4,18},{11,4,15},{7,3,21},{3,9,24},{4,5,19},{1,7,10},{5,9,2},{8,6,18},{9,1,5},{4,12,21},{1,12,2},{7,1,17},{8,7,21},{11,2,12},{2,7,12},{3,11,7},{7,5,2},{2,10,11},{3,1,1},{5,1,22},{9,8,16},{12,10,15},{7,6,25},{11,0,12},{2,3,17},{1,11,14},{1,4,13},{4,3,24},{11,10,6},{0,11,25}},
            {{1,0,3},{0,1,11},{1,2,5},{2,0,7}},
            {{0,1,3},{3,1,1},{2,3,4},{0,2,2}},
            {{0,2,1},{2,1,1},{1,3,1},{2,3,3}},
        };
        int[] testNs = {13,3,4,4};

        Solution s = new Solution();
        for (int i = 0; i < testNs.length; i++) {
            System.out.println("No."+i+" test case: "+s.minCost(testNs[i], testEdges[i]));
        }
    }
}