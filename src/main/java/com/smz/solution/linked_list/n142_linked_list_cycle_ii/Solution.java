package com.smz.solution.n142_linked_list_cycle_ii;
import com.smz.utils.ListNode;
import java.util.HashSet;
import java.util.Set;

public class Solution {

    // [法二]：快慢指针 + 第三指针
    public ListNode detectCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;

        while (fast != null) {
            fast = fast.next;
            if (fast != null) fast = fast.next;
            else return null;

            slow = slow.next;

            if (fast == slow) {
                ListNode res = head;
                while (res != slow) {
                    res = res.next;
                    slow = slow.next;
                }
                return res;
            }
        }

        return null;
    }

    // [法一]：哈希表
    public ListNode detectCycle1(ListNode head) {
        Set<ListNode> set = new HashSet<>();

        while (head != null) {
            if (set.contains(head)) return head;
            set.add(head);
            head = head.next;
        }

        return null;
    }
}