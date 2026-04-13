package com.smz.solution.linked_list.n138_copy_list_with_random_pointer;

import com.smz.utils.Node;
import com.smz.utils.RandomListNode;
import java.util.HashMap;
import java.util.Map;

class Solution {
    // [法一]：递归创建random节点，迭代链接next + HashMap
    Map<Node, Node> old2new;

    public Node copyRandomList(Node head) {
        old2new = new HashMap<>();
        Node cur = head;

        Node pre = null;
        while (cur != null) {
            Node copy = old2new.getOrDefault(cur, formRandomNodeCopy(cur));
            if (pre != null) ((RandomListNode) pre).next = (RandomListNode) copy;
            pre = copy;
            cur = ((RandomListNode) cur).next;
        }

        return old2new.get(head);
    }

    public Node formRandomNodeCopy(Node orig) {
        if (old2new.containsKey(orig)) return old2new.get(orig);

        Node copy = new RandomListNode(orig.val);
        Node randomCopy = null;
        old2new.put(orig, copy);
        if (((RandomListNode) orig).random != null) randomCopy = formRandomNodeCopy(((RandomListNode) orig).random);

        ((RandomListNode) copy).random = (RandomListNode) randomCopy;

        return copy;
    }

    public static void main(String[] args) {
        Integer[][][] testHeads = {
            {{7, null},{13,0},{11,4},{10,2},{1,0}},
            {{1,1},{2,1}},
            {{3, null}, {3,0}, {3,null}},
        };

        Solution s = new Solution();
        for (int i = 0; i < testHeads.length; i++) {
            System.out.println("No."+i+" test case: " + s.copyRandomList(RandomListNode.init(testHeads[i])));
        }
    }
}