public class Solution {
    // [法一]：动态规划记录每个位置的水平和垂直方向连续1的数量，然后以每个1为右下角向上扩展计算最大矩形面积
    public int maximalRectangle(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        // 令dp[i][j][k]为以第(i,j)为结尾的k方向的连续1的数量，k=0表示水平方向（向右），k=1表示垂直方向（向下）
        int[][][] dp = new int[m][n][2];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    dp[i][j][0] = (j-1 >= 0 ? dp[i][j-1][0] : 0) + 1;
                    dp[i][j][1] = (i-1 >= 0 ? dp[i-1][j][1] : 0) + 1;
                }
            }
        }

        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 如果当前位置为1，则尝试以该位置为右下角，向上扩展计算最大矩形面积
                if (matrix[i][j] == '1') {
                    if (res == 0) res = 1; // 存在1，则面积至少为1
                    // 先取水平方向宽度，然后向上扩展高度
                    int width = dp[i][j][0];
                    int height = 1; // 高度至少为1
                    res = Math.max(res, width * height);
                    int x = i - 1;
                    while (x >= 0 && x > i - dp[i][j][1] && dp[x][j][0] > 0) {
                        width = Math.min(width, dp[x][j][0]);
                        height = i - x + 1;
                        res = Math.max(res, width * height);
                        x--;
                    }
                }
            }
        }

        return res;
    }

    public static void main(String[] args) {
        char[][][] testMatrixes = {
            {
                {'0','0','1','0'},
                {'0','0','1','0'},
                {'0','0','1','0'},
                {'0','0','1','1'},
                {'0','1','1','1'},
                {'0','1','1','1'},
                {'1','1','1','1'},
            },
            {
                {'1','1'},
            },
            {
                {'1','0','1','0','0'},
                {'1','0','1','1','1'},
                {'1','1','1','1','1'},
                {'1','0','0','1','0'}
            },
            {
                {'0'}
            },
            {
                {'1'}
            },
            {
                {'0','0'}
            }
        };

        Solution s = new Solution();
        for (int i = 0; i < testMatrixes.length; i++) {
            System.out.println("No,"+i+" test case: "+s.maximalRectangle(testMatrixes[i]));
        }
    }
}