package com.smz.solution.linked_list.n19_remove_nth_node_from_end_of_list;

import com.smz.utils.ListNode;
import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    // [法三]：栈
    public ListNode removeNthFromEnd(ListNode head, int n) {
        Deque<ListNode> stack = new ArrayDeque<>();
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode cur = dummy;
        while(cur != null) {
            stack.push(cur);
            cur = cur.next;
        }

        for (int i = 0; i < n; i++) {
            cur = stack.poll();
        }

        stack.peek().next = cur.next;
        return dummy.next;
    }

    // [法二]：双指针
    public ListNode removeNthFromEnd2(ListNode head, int n) {
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode fast = head;
        ListNode slow = head;

        int i = 0;
        ListNode pre = dummy;
        while (fast.next != null) {
            i++;
            fast = fast.next;
            if (i >= n) {
                pre = slow;
                slow = slow.next;
            }
        }

        pre.next = slow.next;
        return dummy.next;
    }

    // [法一]：递归
    int idx;
    public ListNode removeNthFromEnd1(ListNode head, int n) {
        idx = 0;
        return recursiveRmNthFromEnd(head, n);
    }

    public ListNode recursiveRmNthFromEnd(ListNode head, int n) {
        if (head == null) return null;
        ListNode newNext = recursiveRmNthFromEnd(head.next, n);
        idx++;

        if (idx == n) {
            head.next = null;
            return newNext;
        } else {
            head.next = newNext;
            return head;
        }
    }

    public static void main(String[] args) {
        int[][] testHeads = {
            {1,2,3,4,5},
            {1},
            {1,2},
        };
        int[] testNs = {
            2,
            1,
            1,
        };

        Solution s = new Solution();
        for (int i = 0; i < testHeads.length; i++) {
            System.out.println("No."+i+" test case: "+s.removeNthFromEnd(ListNode.init(testHeads[i]), testNs[i]));
        }
    }
}