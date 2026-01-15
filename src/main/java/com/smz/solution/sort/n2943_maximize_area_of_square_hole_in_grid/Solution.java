package com.smz.solution.sort.n2943_maximize_area_of_square_hole_in_grid;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Solution {
    // 分析可知：
    // 1） 若有最大面积大于1的解，必须水平和垂直各至少除去一条线才有可能形成正方形
    // 2） 除去一条线后，原本线之间的间距从都为1，变为有一个间距扩大的情况，而这个扩大了的间距，如果竖直方向和水平方向都存在，则能形成一个正方形
    // 进一步分析：
    // 1） 只除去一条线，最大线间距只可能为2
    // 2） 只除去两条线，只有这两条线相邻，才能使最大线间距变为3
    // 3） 同理，若想得到更大的线间距，必须出去更多的线，且这些线必须是相邻的
    // 综上所述，问题转化为：
    // 水平和垂直方向上，分别找出所有的连续线段数，然后若两方向都有相同的连续线段数，则能形成对应边长的正方形

    
  
   
    // [法二]：排序，找最大连续线段
    public int maximizeSquareHoleArea(int n, int m, int[] hBars, int[] vBars) {
        Arrays.sort(hBars);
        Arrays.sort(vBars);

        int maxhGaps = getMaxContinusBarsNum(hBars);
        int maxvGaps = getMaxContinusBarsNum(vBars);
        int maxSide = Math.min(maxhGaps, maxvGaps);

        return (maxSide + 1) * (maxSide + 1);
    }

    private int getMaxContinusBarsNum(int[] bars) {
        int maxNum = 1;
        int curNum = 1;
        int prev = bars[0];
        for (int i = 1; i < bars.length; i++) {
            if (bars[i] == prev + 1) {
                curNum++;
                maxNum = Math.max(maxNum, curNum);
            } else {
                curNum = 1;
            }
            prev = bars[i];
        }
        return maxNum;
    }

    // [法一]：排序+二分查找
    // 分别找到两个方向上可能的线段长度，从其中寻找相同的值，该值即可构成一个正方形空洞。在所有可能值中找到正方形面积最大值。
    // 其实也不需要查找，找到最大连续就可以，因为最大连续存在，小于其的值也都会存在
    public int maximizeSquareHoleArea1(int n, int m, int[] hBars, int[] vBars) {
        Arrays.sort(hBars);
        Arrays.sort(vBars);

        Set<Integer> hGaps = getGaps(hBars);
        Set<Integer> vGaps = getGaps(vBars);
        int[] hGapsArray = hGaps.stream().sorted().mapToInt(i -> i).toArray();
        int[] vGapsArray = vGaps.stream().sorted().mapToInt(i -> i).toArray();

        int res = 1;
        for (int hGap : hGapsArray) {
            int idx = Arrays.binarySearch(vGapsArray, hGap);
            if (idx >= 0) {
                res = Math.max(res, (hGap + 1) * (hGap + 1));
            }
        }

        return res;
    }

    public Set<Integer> getGaps(int[] bars) {
        int prev = bars[0];
        int curGap = 1;
        Set<Integer> gaps = new HashSet<>();
        gaps.add(curGap);
        for (int i = 1; i < bars.length; i++) {
            if (bars[i] == prev + 1) {
                curGap++;
            } else {
                curGap = 1;
            }
            prev = bars[i];
            if (curGap != 1) gaps.add(curGap);
        }
        return gaps;
    }

    public static void main(String[] args) {
        int[] testNs = {4, 2, 1, 2};
        int[] testMs = {4, 1, 1, 3};
        int[][] testHBars = {
                {2, 3, 4, 5},
                {2, 3},
                {2},
                {2, 3},
        };
        int[][] testVBars = {
                {5, 4, 3 ,2},
                {2},
                {2},
                {2, 4},
        };

        Solution s = new Solution();
        for (int i = 0; i < testNs.length; i++) {
            System.out.println("No."+i+" test case: " + s.maximizeSquareHoleArea(testNs[i], testMs[i], testHBars[i], testVBars[i]));
        }
    }
}