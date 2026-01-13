package com.smz.solution.binary_search.n3453_separate_squares_i;
public class Solution {
    // [法一]：分精度的两次二分查找
    public double separateSquares(int[][] squares) {
        int maxHeight = 0;
        for (int[] square : squares) {
            int side = square[2];
            maxHeight = Math.max(maxHeight, square[1] + side);
        }

        int lo = 0;
        int hi = maxHeight;
        int mid;

        // 先粗略二分查找，整数分界线
        while (lo <= hi) {
            mid = (lo + hi) / 2;
            long loArea = 0;
            long hiArea = 0;

            for (int[] square : squares) {
                int side = square[2];
                int upperY = square[1] + side;
                int lowerY = square[1];

                if (lowerY >= mid) {
                    hiArea += (long)side * (long)side;
                } else if (upperY <= mid) {
                    loArea += (long)side * (long)side;
                } else {
                    hiArea += (long)side * (long)(upperY - mid);
                    loArea += (long)side * (long)(mid - lowerY);
                }
            }

            if (hiArea > loArea) {
                lo = mid + 1;
            } else if (loArea > hiArea) {
                hi = mid - 1;
            } else {
                // 此时找到精确分界线，但还需尝试找到最小分界线
                int res = mid;
                int cur = mid - 1;
                hiArea = 0;
                loArea = 0;
                while (cur >= 0) {
                    for (int[] square : squares) {
                        int side = square[2];
                        int upperY = square[1] + side;
                        int lowerY = square[1];

                        if (lowerY >= cur) {
                            hiArea += (long)side * (long)side;
                        } else if (upperY <= cur) {
                            loArea += (long)side * (long)side;
                        } else {
                            hiArea += (long)side * (long)(upperY - cur);
                            loArea += (long)side * (long)(cur - lowerY);
                        }
                    }
                    if (loArea == hiArea) {
                        res = cur;
                        cur--;
                    } else {
                        break;
                    }
                }
                return res;
            }
        }

        // 当没有直接返回时，说明搜索到的整数分界线附近存在解，进行精确二分查找
        double epsilon = 1e-5;
        double hiHr = lo; // 上一次搜索时，lo代表大于等于分界线的最小整数，故以其为上界
        double loHr = lo - 1; // 以其前一个整数为下界
        double midHr;

        while (Math.abs(hiHr - loHr) > epsilon) {
            midHr = (loHr + hiHr) / 2.0;
            double loArea = 0.0;
            double hiArea = 0.0;

            for (int[] square : squares) {
                int side = square[2];
                int upperY = square[1] + side;
                int lowerY = square[1];

                if (lowerY >= midHr) {
                    hiArea += (double)side * (double)side;
                } else if (upperY <= midHr) {
                    loArea += (double)side * (double)side;
                } else {
                    hiArea += (double)side * ((double)upperY - midHr);
                    loArea += (double)side * (midHr - (double)lowerY);
                }
            }

            if (hiArea > loArea && hiArea - loArea > epsilon) {
                loHr = midHr;
            } else if (loArea > hiArea && loArea - hiArea > epsilon) {
                hiHr = midHr;
            } else {
                return midHr;
            }
        }

        return loHr;
    }

    public static void main(String[] args) {
        int[][][] testSquares = {
            {{522261215,954313664,225462},{628661372,718610752,10667},{619734768,941310679,44788},{352367502,656774918,289036},{860247066,905800565,100123},{817623994,962847576,71460},{691552058,782740602,36271},{911356,152015365,513881},{462847044,859151855,233567},{672324240,954509294,685569}},
            {{0,8,5}, {26,27,5}},
            {{0,0,1},{2,2,1}},
            {{0,0,2},{1,1,1}},
            {{0,0,3},{0,3,3},{3,0,3}},
        };

        Solution s = new Solution();
        for (int i = 0; i < testSquares.length; i++) {
            System.out.println("No." + i + " test case: " + s.separateSquares(testSquares[i]));
        }
    }
}