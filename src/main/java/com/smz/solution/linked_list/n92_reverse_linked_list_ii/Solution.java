package com.smz.solution.linked_list.n92_reverse_linked_list_ii;

import com.smz.utils.ListNode;

class Solution {
    // [法二]：重复遍历，分解任务
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode preLeft = dummy;
        for (int i = 0; i < left - 1; i++) {
            preLeft = preLeft.next;
        }
        ListNode sucRight = preLeft.next;
        for (int i = 0; i < right - left; i++) {
            sucRight = sucRight.next;
        }

        ListNode leftNode = preLeft.next;
        ListNode rightNode = sucRight;
        sucRight = sucRight.next;
        ListNode cur = leftNode;
        ListNode pre = null;
        ListNode nxt;
        for (int i = 0; i < right - left + 1; i++) {
            nxt = cur.next;
            cur.next = pre;
            pre = cur;
            cur = nxt;
        }
        preLeft.next = rightNode;
        leftNode.next = sucRight;

        return dummy.next;
    }

    // [法一]：一次遍历，再次练习
    public ListNode reverseBetween2(ListNode head, int left, int right) {
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode pre = dummy;
        for (int i = 0; i < left - 1; i++) {
            pre = pre.next;
        }

        ListNode cur = pre.next;
        ListNode nxt;
        for (int i = 0; i < right - left; i++) {
            nxt = cur.next;
            cur.next = nxt.next;
            nxt.next = pre.next;
            pre.next = nxt;
        }

        return dummy.next;
    }


    // [法一]：一次遍历
    public ListNode reverseBetween1(ListNode head, int left, int right) {
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode prev = dummy;

        for (int i = 0; i < left - 1; i++) {
            prev = prev.next;
        }
        ListNode cur = prev.next;
        ListNode nxt;

        for (int i = 0; i < right - left; i++) {
            nxt = cur.next;
            cur.next = nxt.next;
            nxt.next = prev.next;
            prev.next = nxt;
        }

        return dummy.next;
    }
}