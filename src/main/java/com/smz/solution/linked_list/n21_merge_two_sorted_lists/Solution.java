package com.smz.solution.linked_list.n21_merge_two_sorted_lists;

import com.smz.utils.ListNode;

class Solution {
    // [法一]：迭代
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) return list2;
        if (list2 == null) return list1;

        ListNode fake = new ListNode();
        ListNode cur = fake;

        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                cur.next = list1;
                list1 = list1.next;
            } else {
                cur.next = list2;
                list2 = list2.next;
            }

            cur = cur.next;
        }
        if (list1 == null) cur.next = list2;
        if (list2 == null) cur.next = list1;

        return fake.next;
    }
}