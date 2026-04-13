package com.smz.utils;

import java.util.HashMap;
import java.util.Map;

public class RandomListNode extends ListNode {
    public RandomListNode random;

    public RandomListNode() {}
    public RandomListNode(int val) {
        this.val = val;
    }
    public RandomListNode(int val, RandomListNode next, RandomListNode random) {
        this.val = val;
        this.next = next;
        this.random = random;
    }

    public static Map<Integer, RandomListNode> idx2Node;

    public static RandomListNode init(Integer[][] graph) {
        RandomListNode dummy = new RandomListNode();
        RandomListNode pre = dummy;

        idx2Node = new HashMap<>();
        for (int idx = 0; idx < graph.length; idx++) {
            RandomListNode cur = formRandomListNode(idx, graph);
            pre.next = cur;
            pre = cur;
        }

        return (RandomListNode) dummy.next;
    }

    public static RandomListNode formRandomListNode(int idx, Integer[][] graph) {
        if (idx2Node.containsKey(idx)) return idx2Node.get(idx);

        Integer[] cur = graph[idx];
        RandomListNode node = new RandomListNode(cur[0]);
        idx2Node.put(idx, node);

        RandomListNode random = null;
        if (cur[1] != null) random = formRandomListNode(cur[1], graph);
        node.random = random;

        return node;
    }
}