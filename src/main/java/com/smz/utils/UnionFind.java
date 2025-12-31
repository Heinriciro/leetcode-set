package com.smz.utils;
public class UnionFind {
    int[] parent;
    public UnionFind(int n) {
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }

        return parent[x];
    }

    public void merge(int from, int to) {
        int x = find(from);
        int y = find(to);
        if (x != y) {
            parent[x] = y;
        }
    }

    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }
}