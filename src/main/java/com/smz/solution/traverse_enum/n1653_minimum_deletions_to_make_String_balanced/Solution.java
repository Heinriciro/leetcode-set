package com.smz.solution.traverse_enum.n1653_minimum_deletions_to_make_String_balanced;
class Solution {
    // [法二]：DP
    public int minimumDeletions(String s) {
        int prefB = 0;
        int dp = 0;

        for (char c : s.toCharArray()) {
            if (c == 'a') {
                dp = Math.min(dp + 1, prefB);
            } else {
                prefB++;
            }
        }

        return dp;
    }
    // [法一]：枚举分割线，统计前缀B和后缀A的数量，取最小值
    public int minimumDeletions1(String s) {
        int cnt = 0;
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == 'a') cnt++;
        }

        ans = Math.min(ans, cnt);

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == 'a') cnt--;
            else cnt++;

            ans = Math.min(ans, cnt);
        }

        return ans;
    }

    public static void main(String[] args) {
        String[] testStrs = {
            "ababaaaabbbbbaaababbbbbbaaabbaababbabbbbaabbbbaabbabbabaabbbababaa",
            "aababbab",
            "bbaaaaabb",
            "aabbabba",
        };

        Solution s = new Solution();
        for (int i = 0; i < testStrs.length; i++) {
            System.out.println("No."+i+" test case: "+ s.minimumDeletions(testStrs[i]));
        }
    }
}