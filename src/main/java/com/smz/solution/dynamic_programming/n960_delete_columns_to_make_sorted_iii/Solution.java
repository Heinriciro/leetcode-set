import java.util.Arrays;


public class Solution {
// [法一]：动态规划
//     与959等要求通过删除竖向列，要求满足竖向字典序的题目完全不同，那些题只要有一个不满足字典序直接删除即可，因为整列都会被删除
//     该题是删除竖向列，要求满足横向字典序，即会出现多个不满足字典序的元素如何选择删除哪个的问题如 [1， 3， 2]，删除其中的3或者2均可以满足字典序
//     故使用动态规划进行求解，可以看出其数据量较小，可以用O(n^2)的时间复杂度解决
    public int minDeletionSize(String[] strs) {
        int n = strs.length;
        int len = strs[0].length();
        int[] dp = new int[len]; // dp[i] 表示所有字符串均以第 i 个字符结尾时的最大子序列长度
        Arrays.fill(dp, 1);
        int ans = 1;

        for (int i = 1; i < len; i++) {
           for (int j = i - 1; j >= 0; j--) {
                int m = 0;
                for (; m < n; m++) {
                    if (strs[m].charAt(i) < strs[m].charAt(j)) {
                        break;
                    }
                }
                if (m == n) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
           }

           ans = Math.max(ans, dp[i]);
        }
        return len - ans;
    }

    public static void main(String[] args) {
        String[][] testStrs = {
            {"a"},
            {"babca", "bbazb"},
            {"edcba"},
            {"ghi", "def", "abc"}
        };
    
        Solution s = new Solution();
        for (int i = 0; i < testStrs.length; i++) {
            System.out.println("No." + i + " test case: " + s.minDeletionSize(testStrs[i]));
        }
    }
}