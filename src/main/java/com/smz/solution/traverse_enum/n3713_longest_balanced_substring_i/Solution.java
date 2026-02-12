import java.util.HashMap;
import java.util.Map;

class Solution {
    // [法一]：暴力解法
    public int longestBalanced(String s) {
        int ans = 0;

        for (int i = 0; i < s.length(); i++) {
            Map<Character, Integer> cnt = new HashMap<>();
            for (int j = i; j >=0; j--) {
                cnt.put(s.charAt(j), cnt.getOrDefault(s.charAt(j), 0) + 1);
                int target = 0;
                int sameCnt = 0;
                for (Character c : cnt.keySet()) {
                    if (target == 0) target = cnt.get(c);
                    else {
                        if (target != cnt.get(c)) break;
                        sameCnt++;
                    }
                }
                if (sameCnt == cnt.size() - 1) ans = Math.max(ans, i - j + 1);
            }
        }

        return ans; 
    }

    public static void main(String[] args) {
       String[] testStrs = {
           "abbac",
           "zzabccy",
           "aba",
       };

       Solution s = new Solution();
       for (int i = 0; i < testStrs.length; i++) {
           System.out.println("No." + i + " test case: " + s.longestBalanced(testStrs[i]));
       }
    }
}