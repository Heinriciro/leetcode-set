package com.smz.solution.linked_list.n141_linked_list_cycle;

import com.smz.utils.ListNode;

public class Solution {
    public boolean hasCycle(ListNode head) {
        ListNode slow = head;
        if (head == null || head.next == null || head.next.next == null) return false;
        ListNode fast = head.next.next;

        while (fast != null) {
            if (fast == slow) return true;

            if (fast.next == null || fast.next.next == null) return false;
            fast = fast.next.next;
            slow = slow.next;
        }

        return false;
    }
}