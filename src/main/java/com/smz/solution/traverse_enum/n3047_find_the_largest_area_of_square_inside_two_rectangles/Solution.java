package com.smz.solution.traverse_enum.n3047_find_the_largest_area_of_square_inside_two_rectangles;
public class Solution {
    // 分析：观察测例可知，题目所求的正方形必须完完全全位于某两个正方形的交界区域内，不能只是部分位于
    //       两个正方形的相交区域，只有可能是正方形或者矩形
    //       那么解题思路就是得到每两个正方形相交区域的矩形面积或者说边长，使用边长较小的那一边组成正方形，在所有这些正方形中找到最大值
    //       从题目约束可知，正方形数量在2-10^3之间，枚举每两个正方形的复杂度O(N^2)最大大约在10^6，初步判断并不超时

    // [法二]：暴力枚举 + 剪枝
    public long largestSquareArea(int[][] bottomLeft, int[][] topRight) {
        int n = bottomLeft.length;


        int maxSide = 0;
        // 如此遍历以去重，降低复杂度
        for (int i = 1; i < n; i++) {
            int leftX1 = bottomLeft[i][0];
            int bottomY1 = bottomLeft[i][1];
            int rightX1 = topRight[i][0];
            int topY1 = topRight[i][1];

            // 只要有一个正方形小于maxSide，最终相交一定不会大于maxside，所以可以直接跳过
            if (rightX1 - leftX1 <= maxSide || topY1 - bottomY1 <= maxSide) continue;
            for (int j = 0; j < i; j++) {
                int leftX2 = bottomLeft[j][0];
                int bottomY2 = bottomLeft[j][1];
                int rightX2 = topRight[j][0];
                int topY2 = topRight[j][1];

                // 不相交则跳过
                if (rightX1 <= leftX2 || rightX2 <= leftX1 || topY1 <= bottomY2 || topY2 <= bottomY1) continue;
                // 只要有一个正方形小于maxSide，最终相交一定不会大于maxside，所以可以直接跳过
                if (rightX2 - leftX2 <= maxSide || topY2 - bottomY2 <= maxSide) continue;
                int width, height, side;
                // 分完全包含和相交两种情况计算重叠部分宽度
                if ((leftX1 >= leftX2 && rightX1 <= rightX2) || (leftX2 >= leftX1 && rightX2 <= rightX1)) {
                    width = Math.min(rightX1, rightX2) - Math.max(leftX1, leftX2);
                } else {
                    width = Math.min(Math.abs(rightX1 - leftX2), Math.abs(rightX2 - leftX1));
                }
                // 分完全包含和相交两种情况计算重叠部分高度
                if ((topY1 <= topY2 && bottomY1 >= bottomY2) || (topY2 <= topY1 && bottomY2 >= bottomY1)) {
                    height = Math.min(topY1, topY2) - Math.max(bottomY1, bottomY2);
                } else {
                    height = Math.min(Math.abs(topY1 - bottomY2), Math.abs(topY2 - bottomY1));
                }
                side = Math.min(width, height);

                maxSide = Math.max(maxSide, side);
            }
        }

        return (long)maxSide * (long)maxSide;
    }

    // [法一]：暴力枚举
    public long largestSquareArea1(int[][] bottomLeft, int[][] topRight) {
        int n = bottomLeft.length;

        long res = 0;

        // 如此遍历以去重，降低复杂度
        for (int i = 1; i < n; i++) {
            int leftX1 = bottomLeft[i][0];
            int bottomY1 = bottomLeft[i][1];
            int rightX1 = topRight[i][0];
            int topY1 = topRight[i][1];
            for (int j = 0; j < i; j++) {
                int leftX2 = bottomLeft[j][0];
                int bottomY2 = bottomLeft[j][1];
                int rightX2 = topRight[j][0];
                int topY2 = topRight[j][1];

                // 不相交则跳过
                if (rightX1 <= leftX2 || rightX2 <= leftX1 || topY1 <= bottomY2 || topY2 <= bottomY1) continue;
                int width, height, side;
                // 分完全包含和相交两种情况计算重叠部分宽度
                if ((leftX1 >= leftX2 && rightX1 <= rightX2) || (leftX2 >= leftX1 && rightX2 <= rightX1)) {
                    width = Math.min(rightX1, rightX2) - Math.max(leftX1, leftX2);
                } else {
                    width = Math.min(Math.abs(rightX1 - leftX2), Math.abs(rightX2 - leftX1));
                }
                // 分完全包含和相交两种情况计算重叠部分高度
                if ((topY1 <= topY2 && bottomY1 >= bottomY2) || (topY2 <= topY1 && bottomY2 >= bottomY1)) {
                    height = Math.min(topY1, topY2) - Math.max(bottomY1, bottomY2);
                } else {
                    height = Math.min(Math.abs(topY1 - bottomY2), Math.abs(topY2 - bottomY1));
                }
                side = Math.min(width, height);

                res = Math.max(res, (long)side * (long)side);
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int[][][] testBLs = {
            {{2,2},{4,2},{5,3}},
            {{3,2},{1,1}},
            {{1,1},{2,2},{3,1}},
            {{1,1},{1,3},{1,5}},
            {{1,1},{2,2},{1,2}},
            {{1,1},{3,3},{3,1}},
        };
        int[][][] testTRs = {
            {{4,4},{7,4},{8,5}},
            {{4,5},{5,4}},
            {{3,3},{4,4},{6,6}},
            {{5,5},{5,7},{5,9}},
            {{3,3},{4,4},{3,4}},
            {{2,2},{4,4},{4,2}},
        };

        Solution s = new Solution();
        for(int i = 0; i < testBLs.length; i++) {
            System.out.println("No."+i+" test case: "+s.largestSquareArea(testBLs[i], testTRs[i]));
        }
    }
}