package com.smz.solution.linked_list.n148_sort_list;

import com.smz.utils.ListNode;

class Solution {
    // [法一]：归并排序+递归+合并链表
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode fast = head.next.next;
        ListNode slow = head;
        while (fast != null) {
            fast = fast.next;
            if (fast == null) break;
            fast = fast.next;
            slow = slow.next;
        }
        ListNode right = slow.next;
        slow.next = null;

        ListNode sortLeft = sortList(head);
        ListNode sortRight = sortList(right);
        
        ListNode newHead = merge(sortLeft, sortRight);
        
        return newHead;
    }

    public ListNode merge(ListNode left, ListNode right) {
        ListNode dummy = new ListNode();
        ListNode pre = dummy;

        while (left != null && right != null) {
            if (left.val < right.val) {
                pre.next = left;
                pre = left;
                left = left.next;
            } else {
                pre.next = right;
                pre = right;
                right = right.next;
            }
        }
        if (left == null) pre.next = right;
        else pre.next = left;

        return dummy.next;
    }

    public static void main(String[] args) {
        int[][] testHeads = {
            {4,2,1,3},
            {-1,5,3,4,0},
        };

        Solution s = new Solution();
        for (int i = 0; i < testHeads.length; i++) {
            System.out.println("No."+i+" test case: "+s.sortList(ListNode.init(testHeads[i])));
        }
    }
}