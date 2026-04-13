package com.smz.utils;

public class ListNode extends Node{
    public ListNode next;
    public ListNode() {}
    public ListNode(int x) {
        this.val = x;
        next = null;
    }
    public ListNode(int val, ListNode next) { this.val = val; this.next = next; }


    public static ListNode init(int[] elements) {
        ListNode dummy = new ListNode();
        ListNode pre = dummy;
        ListNode cur;
        for (int e : elements) {
            cur = new ListNode(e);
            pre.next = cur;
            pre = cur;
        }

        return dummy.next;
    }

    @Override
    public String toString() {
        ListNode cur = this;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        while (cur != null) {
            sb.append(cur.val);
            if (cur.next != null) sb.append(",");
            cur = cur.next;
        }
        sb.append("]");

        return sb.toString();
    }

}
