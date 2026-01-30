package com.smz.utils;

public class Trie {
    public int count = 0;
    public Node root = new Node();

    public int put(String s) {
        Node cur = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int idx = c - 'a';
            if (cur.children[idx] == null) {
                cur.children[idx] = new Node();
            }
            cur = cur.children[idx];
        }
        if (cur.sid == -1) {
            cur.sid = count++;
        }
        return cur.sid;
    }

    public static class Node {
        public int sid = -1; // -1 表示非终止节点
        public Node[] children = new Node[26];
    }
}