package com.smz.solution.linked_list.n206_reverse_linked_list;

import com.smz.utils.ListNode;

class Solution {

    // [法二]：递归
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode nxt = head.next;
        ListNode newHead = reverseList(head.next);
        nxt.next = head;
        head.next = null;

        return newHead;
    }


    // [法一]：迭代
    public ListNode reverseList1(ListNode head) {
        ListNode prev = null;
        ListNode cur = head;

        ListNode next;
        while (cur != null) {
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }

        return prev;
    }
}