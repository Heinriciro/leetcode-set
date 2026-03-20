import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Solution {
    // [法一]：DP + 集合
    public boolean wordBreak(String s, List<String> wordDict) {
        int n = s.length();
        Set<String> dictSet = new HashSet<>();
        for (String word : wordDict) {
            dictSet.add(word);
        }

        boolean[] dp = new boolean[n+1]; 
        for (int i = 1; i <= n; i++) {
            String cur = s.substring(0, i);
            if (dictSet.contains(cur)) {
                dp[i] = true;
                continue;
            }

            for (int j = 1; j < i; j++) {
                if (dp[j] && dictSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            } 
        }

        return dp[n];
    }

    public static void main(String[] args) {
        String[] testSs = {
            "leetcode",
            "applepenapple",
            "catsandog",
        };
        List[] testDicts = {
            Arrays.asList("leet", "code"),
            Arrays.asList("apple", "pen"),
            Arrays.asList("cats", "dog", "sand", "and", "cat"),
        };

        Solution s = new Solution();
        for (int i = 0; i < testSs.length; i++) {
            System.out.println("No."+i+" test case:" + s.wordBreak(testSs[i], testDicts[i]));
        }
    }
}