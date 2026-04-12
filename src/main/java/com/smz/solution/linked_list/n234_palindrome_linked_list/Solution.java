package com.smz.solution.linked_list.n234_palindrome_linked_list;

import com.smz.utils.ListNode;
import java.util.ArrayList;
import java.util.HashMap;

class Solution {
    // [法二]：遍历 + ArrayList
    public boolean isPalindrome1(ListNode head) {
        if (head == null) return false;
        int n = 0;
        ListNode cur = head;
        ArrayList<Integer> nodes = new ArrayList<>();
        while (cur != null) {
            nodes.add(cur.val);
            n++;
            cur = cur.next;
        }

        int left = 0;
        int right = n-1;
        while (left < right) {
            if (!nodes.get(left).equals(nodes.get(right))) return false;
            left++;
            right--;
        }

        return true;
    }
    // [法一]：遍历 + 哈希表
    public boolean isPalindrome(ListNode head) {
        if (head == null) return false;
        int n = 0;
        ListNode cur = head;
        HashMap<Integer, Integer> nodes = new HashMap<>();
        while (cur != null) {
            nodes.put(n, cur.val);
            n++;
            cur = cur.next;
        }

        int left = 0;
        int right = n-1;
        while (left < right) {
            if (!nodes.get(left).equals(nodes.get(right))) return false;
            left++;
            right--;
        }

        return true;
    }

    public static void main(String[] args) {
        int[][] testHeads = {
            {1,2,2,1},
            {1,2},
            {},
        };

        Solution s = new Solution();
        for (int i = 0; i < testHeads.length; i++) {
            System.out.println("No."+i+" test case: " + s.isPalindrome1(ListNode.init(testHeads[i])));
        }
    }
}