import java.util.HashMap;
import java.util.Map;

class Solution {
    // [法一]：滑动窗口 + 哈希表 + 维护数量不足的字母个数
    public String minWindow(String s, String t) {
        int sLen = s.length();
        int tLen = t.length();
        if (sLen < tLen) return "";

        Map<Character, Integer> cnt = new HashMap<>();

        int diff = 0;
        for (int i = 0; i < tLen; i++) {
            char cur = t.charAt(i);
            cnt.put(cur, cnt.getOrDefault(cur, 0) - 1);
            if (cnt.get(cur) == -1) diff++;
        }

        int left = 0;
        int right = 0;

        String res = s + "initial";
        while (right < sLen) {
            char curRight = s.charAt(right);
            if (cnt.containsKey(curRight)) {
                cnt.put(curRight, cnt.get(curRight) + 1);
                if (cnt.get(curRight) == 0) diff--;
            }
            right++;

            while (left < right && diff == 0) {
                res = res.length() < (right - left) ? res : s.substring(left, right);
                char curLeft = s.charAt(left);
                if (cnt.containsKey(curLeft)) {
                    cnt.put(curLeft, cnt.get(curLeft) - 1);
                    if (cnt.get(curLeft) == -1) diff++;
                }
                left++;
            }
        }
        return res.equals(s + "initial") ? "" : res;
    }

    public static void main(String[] args) {
        String[] testSs = {
            "a",
            "ADOBECODEBANC",
            "a",
            "a",
        };
        String[] testTs = {
            "b",
            "ABC",
            "a",
            "aa",
        };

        Solution s = new Solution();
        for (int i = 0; i < testSs.length; i++) {
            System.out.println("No."+ i + " test case: "+s.minWindow(testSs[i], testTs[i]));
        }
    }
}