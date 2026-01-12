package com.smz.solution.math_derivation.n1266_minimum_time_visiting_all_points;
public class Solution {
    // [法一]：数学/几何推导以优化路径计算方法 (这一方法又称 切比雪夫距离)
    public int minTimeToVisitAllPoints(int[][] points) {
        int res = 0;

        for (int i = 1; i < points.length; i++) {
            int[] curPoint = points[i];
            int[] prePoint = points[i-1];
            int dx = Math.abs(curPoint[0] - prePoint[0]);
            int dy = Math.abs(curPoint[1] - prePoint[1]);

            int diagonalPath = Math.min(dx,dy);
            int nonDiagonalPath = Math.abs(dx - dy);
            res += diagonalPath + nonDiagonalPath;
        }

        return res;
    }

    public static void main(String[] args) {
        int[][][] testPoints = {
            {{1,1},{3,4},{-1,0}},
            {{3,2},{-2,2}},
        };

        Solution s = new Solution();
        for (int i = 0; i < testPoints.length; i++) {
            System.out.println("No."+i+" test case: "+ s.minTimeToVisitAllPoints(testPoints[i]));
        }
    }
}