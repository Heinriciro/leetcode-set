public class Solution {
    // [法一]：遍历 + 剪枝
    //    未利用矩阵在列上仍是非递增的这一性质，可进一步剪枝如使用倒序遍历
    //    + 另外还可以使用二分加快第一个负数的寻找速度
    public int countNegatives(int[][] grid) {
        int res = 0;
        int n = grid[0].length;
        for (int[] row : grid) {
            for (int i = 0; i < n; i++) {
                if (row[i] < 0) {
                    res += n - i;
                    break;
                }
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int[][][] testGrids = {
            {{4,3,2,-1},{3,2,1,-1},{1,1,-1,-2},{-1,-1,-2,-3}},
            {{3,2},{1,0}},
        };

        Solution s = new Solution();
        for (int i = 0; i < testGrids.length; i++) {
            System.out.println("No."+i+" test case: " + s.countNegatives(testGrids[i]));
        }
    }
}