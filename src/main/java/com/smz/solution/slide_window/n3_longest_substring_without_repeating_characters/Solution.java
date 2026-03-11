package com.smz.solution.slide_window.n3_longest_substring_without_repeating_characters;
import java.util.HashSet;
import java.util.Set;

public class Solution {
    // [法一]：滑动窗口
    public int lengthOfLongestSubstring(String s) {
        int left = 0;
        int right = 0;
        int n = s.length();
        int res = 0;

        Set<Character> set = new HashSet<>();

        while (right < n) {
            char cur = s.charAt(right);

            while (set.contains(cur)) {
                set.remove(s.charAt(left));
                left++;
            }
            set.add(cur);
            if (set.size() > res) res = set.size();
            right++;
        }
        return res;
    }

    public static void main(String[] args) {
        String[] testSs = {
            "abcabcbb",
            "bbbbb",
            "pwwkew",
        };
        Solution s = new Solution();
        for (int i = 0; i < testSs.length; i++) {
            System.out.println("No."+i+" test case: "+ s.lengthOfLongestSubstring(testSs[i]));
        }
    }
}