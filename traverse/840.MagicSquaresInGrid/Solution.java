import java.util.HashSet;
import java.util.Set;

public class Solution {
    // [法一]：暴力+剪枝
    //     仍可进一步优化：推算可知9数和应为45，而同时每一行每一列的和必须为15，所以幻方中心数必须为5，进而在前面的条件下，只需判断前两行和前两列，对角线也无需判断
    public int numMagicSquaresInside(int[][] grid) {
        if (grid.length < 3 || grid[0].length < 3) return 0;
        int res = 0;
        for (int i = 0; i < grid.length - 2; i++) {
            for (int j = 0; j < grid[0].length - 2; j++) {



                if (isMagic(i, j, grid)) res++;
            }
        }

        return res;
    }

    private boolean isMagic(int i, int j, int[][] grid) {
        Set<Integer> distinctNum = new HashSet<>();

        int[] rowSum = new int[3];
        int[] colSum = new int[3];
        int diagSum = 0;
        int adiagSum = 0;

        for (int m = 0; m < 3; m++) {
            for (int n = 0; n < 3; n++) {
                int value = grid[i+m][j+n];
                if (distinctNum.contains(value)) return false;
                if (value > 9 || value < 1) return false;
                distinctNum.add(value);

                colSum[n] += value;
                rowSum[m] += value; 

                if (m == n) diagSum += value;
                if (m+n ==2) adiagSum += value;
            }
        }

        if (adiagSum != diagSum) return false;
        for (int k = 0; k < 3; k++) {
            if (rowSum[k] != diagSum) return false;
            if (colSum[k] != diagSum) return false;
        }

        return true;
    }

    public static void main(String[] args) {
        int[][][] testGrids = {
            {{5,5,5,5},{5,5,5,5},{5,5,5,5}},
            {{4,3,8,4},{9,5,1,9},{2,7,6,2}},
            {{8}},
        };

        Solution s = new Solution();
        for (int i = 0; i < testGrids.length; i++) {
            System.out.println("No."+i+" test case: "+s.numMagicSquaresInside(testGrids[i]));
        }
    }
}