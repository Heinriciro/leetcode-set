package com.smz.solution.slide_window.n438_find_all_anagrams_in_a_string;
import java.util.ArrayList;
import java.util.List;

class Solution {
    // [法一]：滑动窗口
    public List<Integer> findAnagrams(String s, String p) {
        int sLen = s.length();
        int pLen = p.length();

        if (sLen < pLen) return new ArrayList<>();

        int[] count = new int[26];
        int diff = 0;

        for (int i =0; i < pLen; i++) {
            count[p.charAt(i) - 'a']--;
            count[s.charAt(i) - 'a']++;
        }

        for (int i = 0; i < 26; i++) {
            if (count[i] != 0) diff++;
        }
        List<Integer> res = new ArrayList<>();

        if (diff == 0) res.add(0);
        

        for (int i = 0; i < sLen - pLen; i++) {
            char left = s.charAt(i);
            char right = s.charAt(i + pLen);
            
            count[left - 'a']--;
            if (count[left - 'a'] == 0) diff--;
            if (count[left - 'a'] == -1) diff++;

            count[right - 'a']++;
            if (count[right - 'a'] == 0) diff--;
            if (count[right - 'a'] == 1) diff++;

            if (diff == 0) res.add(i+1);
        }

        return res;
    }
    // public List<Integer> findAnagrams(String s, String p) throws RuntimeException {
    //     int n = s.length();
    //     Map<Character, Integer> map = new HashMap<>();
    //     for (int i = 0; i < p.length(); i++) {
    //         map.put(p.charAt(i), map.getOrDefault(p.charAt(i), 0) + 1);
    //     }

    //     List<Integer> res = new ArrayList<>();
    //     int left = 0;
    //     int right = 0;
    //     int remainCnt = p.length();

    //     while (left <= right && right < n) {
    //         char curRight = s.charAt(right);
    //         if (map.containsKey(curRight)) {
    //             int curCnt = map.get(curRight);
    //             if (curCnt > 0) {
    //                 map.put(curRight, curCnt - 1);
    //                 remainCnt--;
    //                 right++;
    //             } else {
    //                 char curLeft = s.charAt(left);
    //                 if (map.containsKey(curLeft)) {
    //                     map.put(curLeft, map.get(curLeft) + 1);
    //                     remainCnt++;
    //                 }
    //                 left++;
    //             }
    //         } else {
    //             while (left < right) {
    //                 char curLeft = s.charAt(left);
    //                 if (map.containsKey(curLeft)) {
    //                     map.put(curLeft, map.get(curLeft) + 1);
    //                     remainCnt++;
    //                     left++;
    //                 }
    //             }
    //             right++;
    //         }

    //         while (left <= right && right - left > p.length()) {
    //             char curLeft = s.charAt(left);
    //             if (map.containsKey(curLeft)) {
    //                 map.put(curLeft, map.get(curLeft) + 1);
    //                 left++;
    //                 remainCnt++;
    //             }
    //         }

    //         if (right - left == p.length() && remainCnt == 0) {
    //             res.add(left);
    //         }
    //     }

    //     return res;
    // }

    public static void main(String[] args) {
        String[] testSs = {
            "aa",
            "cbaebabacd",
            "abab",
        };
        String[] testPs = {
            "bb",
            "abc",
            "ab",
        };
        
        Solution s = new Solution();
        for (int i = 0; i < testSs.length; i++) {
            System.out.println("No."+ i + " test case: "+s.findAnagrams(testSs[i], testPs[i]));
        }
    }
}