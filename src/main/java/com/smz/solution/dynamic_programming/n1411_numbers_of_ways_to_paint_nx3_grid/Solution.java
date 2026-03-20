import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    // [法一]：动态规划
    // 从上往下涂色时，每一行的涂色选择只取决于上一行，故令dp[i][p1][p2][p3],p1 p2 p3表示上一行三格的颜色选择，可将其(p1 p2 p3)3 这一三进制数换算为十进制 p1 * 9 + p2 * 3 + p3
    // 即 dp[i][type]
    private static final int MOD = 1000000007;
    public int numOfWays(int n) {
        // 计算所有可能得type值
        List<Integer> types = new ArrayList<>();
        for (int p1 = 0; p1 < 3; p1++){
            for (int p2 = 0; p2 < 3; p2++){
                for (int p3 = 0; p3 < 3; p3++) {
                    if (p1 != p2 && p2 != p3) {
                        types.add(p1*9+p2*3+p3);
                    }
                }
            }
        }

        // 预处理得到每两对type是否可以相邻
        int[][] canAdjacent = new int[types.size()][types.size()];
        for (int i = 0; i < types.size(); i++) {
            int x1 = types.get(i) / 9;
            int x2 = types.get(i) / 3 % 3;
            int x3 = types.get(i) % 3;
            for (int j = 0; j < types.size(); j++) {
                int y1 = types.get(j) / 9;
                int y2 = types.get(j) / 3 % 3;
                int y3 = types.get(j) % 3;

                // 所有type已经满足相邻不同色，所以只需判断相邻两行对应位置是否同色
                if (x1 != y1 && x2 != y2 && x3 != y3) canAdjacent[i][j] = 1;
            }
        }

        int dp[][] = new int[n][types.size()];
        // 初始化第一行可能的所有配色
        Arrays.fill(dp[0], 1);

        for (int i = 1; i < n; i++) {
            for (int curTypeIdx = 0; curTypeIdx < types.size(); curTypeIdx++) {
                for (int prevTypeIdx = 0; prevTypeIdx < types.size(); prevTypeIdx++) {
                    if (prevTypeIdx != curTypeIdx && canAdjacent[curTypeIdx][prevTypeIdx] == 1) {
                        dp[i][curTypeIdx] = (dp[i][curTypeIdx] + dp[i-1][prevTypeIdx]) % MOD;
                    }
                }
            }
        }

        int res = 0;
        for (int i = 0; i < types.size(); i++) {
            res = (res + dp[n-1][i]) % MOD;
        }
        return res;
    }

    public static void main(String[] args) {
        int[] testNs = {5000, 1, 2, 3};
        Solution s = new Solution();

        for (int i = 0; i < testNs.length; i++){
            System.out.println("No."+i+" test case: "+s.numOfWays(testNs[i]));
        }
    }
}