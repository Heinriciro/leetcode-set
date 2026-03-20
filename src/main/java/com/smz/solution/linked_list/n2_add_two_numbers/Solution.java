package com.smz.solution.linked_list.n2_add_two_numbers;
import com.smz.utils.ListNode;

class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int init = (l1.val + l2.val) % 10;
        int addition = (l1.val + l2.val) / 10;
        ListNode res = new ListNode(init);
        l1 = l1.next;
        l2 = l2.next;

        ListNode cur = res;
        while (l1 != null || l2 != null) {
            init = 0;
            if (l1 != null) {
                init += l1.val;
                l1 = l1.next;
            }
            if (l2 !=null) {
                init += l2.val;
                l2 = l2.next;
            }
            init += addition;

            addition = init / 10;
            init %= 10;
            cur.next = new ListNode(init);
            cur = cur.next;
        }

        if (addition != 0) {
            cur.next = new ListNode(addition);
        }

        return res;
    }
}