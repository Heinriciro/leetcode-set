package com.smz.solution.simulation.n799_champaign_tower;
class Solution {
    // [法一]：模拟
    public double champagneTower(int poured, int query_row, int query_glass) {
        double[] cur = new double[]{poured};

        for (int i = 1; i <= query_row; i++) {
            double[] next = new double[i+1];
            for (int j = 0; j < i; j++) {
                if (cur[j] > 1) {
                    double overflow = cur[j] - 1;
                    next[j] += overflow / 2.0;
                    next[j+1] += overflow / 2.0;
                }
            }
            cur = next;
        }

        return Math.min(cur[query_glass], 1.0);
    }

    public static void main(String[] args) {
        int[] testPoureds =  new int[]{
            1,
            2,
            1000000009,
        };
        int[] testRows = new int[]{
            1,
            1,
            33,
        };
        int[] testGlasses = new int[]{
            1,
            1,
            17,
        };

        Solution s = new Solution();
        for (int i = 0; i < testPoureds.length; i++) {
            System.out.println("No."+i+" test case: "+s.champagneTower(testPoureds[i], testRows[i], testGlasses[i]));
        }
    }
}