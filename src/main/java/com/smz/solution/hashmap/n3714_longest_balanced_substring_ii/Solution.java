package com.smz.solution.hashmap.n3714_longest_balanced_substring_ii;
import java.util.HashMap;

class Solution {
    // [法一]: 分情况讨论 + 前缀和 + 哈希表
    public int longestBalanced(String s) {
        int ans = 0;
        int n = s.length();
        for (int i = 0; i < s.length();){
            int start = i;
            for (i++; i < s.length() && s.charAt(i) == s.charAt(i-1); i++) {}
            ans = Math.max(ans, i - start);
        }
            
        ans = Math.max(ans, getLongestTwo(s.toCharArray(), 'a', 'b'));
        ans = Math.max(ans, getLongestTwo(s.toCharArray(), 'a', 'c'));
        ans = Math.max(ans, getLongestTwo(s.toCharArray(), 'b', 'c'));

        int[] cnt = new int[3]; 
        HashMap<Long, Integer> m = new HashMap<>();
        m.put((long)n << 20 | n, -1);
        for (int i = 0; i < s.length(); i++) {
            cnt[s.charAt(i) - 'a']++;
            long key = (long)(cnt[0] - cnt[1] + n) << 20 | (cnt[1] - cnt[2] + n); // n的上限为10^5,二进制为(2^3 + 2) ^ 5, 可以设置一个上限为20位
            if (m.containsKey(key)) {
                ans = Math.max(ans, i - m.get(key));
            } else {
                m.put(key, i);
            }
        }
        return ans;
    }

    private int getLongestTwo(char[] s, char c1, char c2) {
        int ans = 0;
        
        for (int i = 0; i < s.length; i++) {
            HashMap<Integer, Integer> m = new HashMap<>();
            int cnt = 0;
            m.put(0, i - 1);
            
            for (; i< s.length && (s[i] == c1 || s[i] == c2); i++) {
                cnt += s[i] == c1 ? 1 : -1;
                if (m.containsKey(cnt)) {
                    ans = Math.max(ans, i - m.get(cnt));
                } else {
                    m.put(cnt, i);
                }
            }

        }
        return ans;
    }

    public static void main(String[] args) {
        String[] testStrs = {
            "abbac",
            "aabcc",
            "aba",
        };

        Solution s = new Solution();
        for (int i = 0; i < testStrs.length; i++) {
            System.out.println("No." + i + " test case: " + s.longestBalanced(testStrs[i]));
        }
    }
}