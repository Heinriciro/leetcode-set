package com.smz.solution.linked_list.n24_swap_node_in_pairs;

import com.smz.utils.ListNode;

class Solution {
    // [法二]：迭代
    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode pre = dummy, nxt, cur = head;
        while (cur != null && cur.next != null) {
            nxt = cur.next;
            cur.next = nxt.next;
            nxt.next = cur;
            pre.next = nxt;
            pre = cur;
            cur = cur.next;
        }

        return dummy.next;
    }

    // [法一]：递归
    public ListNode swapPairs1(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return head;

        ListNode newNext = swapPairs(head.next.next);
        
        ListNode nxt = head.next;
        head.next = newNext;
        nxt.next = head;

        return nxt;
    }

    public static void main(String[] args) {
        int[][] testHeads = {
            {1,2,3,4},
            {},
            {1},
            {1,2},
        };

        Solution s = new Solution();
        for (int i = 0; i < testHeads.length; i++) {
            System.out.println("No."+i+" test case: "+s.swapPairs(ListNode.init(testHeads[i])));
        }
    }

}