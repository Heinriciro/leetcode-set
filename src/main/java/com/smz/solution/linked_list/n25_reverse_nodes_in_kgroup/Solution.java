package com.smz.solution.linked_list.n25_reverse_nodes_in_kgroup;

import com.smz.utils.ListNode;

class Solution {
    // [法一]：类似于部分翻转列表中多次翻转的方法，首先确定前后节点，符合长度要求后，随后翻转中间，最后链接
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode();
        dummy.next = head;

        ListNode preLeft = dummy;
        ListNode sucRight;
        ListNode left, right, pre=null, nxt;
        ListNode cur = head;
        while (cur != null) {
            cur = preLeft;
            left = cur.next;
            for (int i = 0; i < k; i++) {
                cur = cur.next;
                if (cur == null) return dummy.next;
            }
            right = cur;
            sucRight = right.next;

            cur = left;
            for (int i = 0; i < k; i++) {
                nxt = cur.next;
                cur.next = pre;
                pre = cur;
                cur = nxt;
            }
            preLeft.next = right;
            left.next = sucRight;

            preLeft = left;
        }

        return dummy.next;
    }
}