package com.smz.solution.traverse_enum.n2975_maximum_square_area_by_removing_fences_from_field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Solution {
    // 分析与N2943的异同：
    //     本题与N2943非常相似，只是后者中每一个坐标都有一个栅栏，使得最大正方形面积至少为1.
    //     本题则仅有参数中给出的栅栏外加作为边界的四个栅栏，但同样都是从给出的栅栏中选择除去
    //     由于本题初始的栅栏并不固定，所以与N2943中找最大连续值不太一样：
    //         N2943中，由于每个点都有一个栅栏，找到最大连续值也就是最大能构成的空洞边长后，比它小的边长一定也存在
    //          （因为每个点的栅栏都是可以除去的，最大连续值对应的栅栏少除去头或尾的一个就能找到比其小的栅栏）
    //         对比来说，本题的栅栏是“离散的”，找到的最大连续值，由于前后不一定有栅栏，所以构成的空洞边长不等于该最大连续值+1，所以需要重新思考
    // 重新分析思路：
    //     本质上就是要找每个边能够形成的空洞的边长值，如果两个边都存在该边长值，则能够构成正方形 （N2943也是在此思路上简化得来,由于其初始全栅栏，所以找到可能边长比较容易）
    //     如何找到所有边长？：
    //         可能真的需要枚举所有可能，因为栅栏存在与否并不实现事先知道，只有参数中的栅栏和四个边框是存在的
    //         随后使用N2943.法一，其使用二分查找查找相同的边长

    private static final int MOD = 1000000007;
    // [法二]：枚举所有可能边长，但无需二分查找，交替遍历即可
    //         最后一个测例超时
    public int maximizeSquareArea(int m, int n, int[] hFences, int[] vFences) {
        Set<Integer> hGaps = getGaps(n, vFences);
        Set<Integer> vGaps = getGaps(m, hFences);

        int[] sortedHGaps = hGaps.stream().sorted().mapToInt(i -> i).toArray();
        int[] sortedVGaps = vGaps.stream().sorted().mapToInt(i -> i).toArray();

        // 逆序查找，从可能的最大值开始，减少查找次数
        int h = sortedHGaps.length-1;
        int v = sortedVGaps.length-1;
        int curH = sortedHGaps[h];
        int curV = sortedVGaps[v];

        while (h >= 0 && v >= 0) {
            while (h >= 0 && curH > curV) {
                if (--h < 0) break;
                curH = sortedHGaps[h];
            }
            while (v >= 0 && curV > curH) {
                if (--v < 0) break;
                curV = sortedVGaps[v];
            } 

            if (curH == curV) {
                return (int) (((long)curH * (long)curV) % MOD);
            }
        }

        return  -1;
    }
    
    // [法一]：枚举所有可能边长，随后二分查找最大的相同边长以构成正方形
    //         最后一个测例超时
    public int maximizeSquareArea1(int m, int n, int[] hFences, int[] vFences) {
        Set<Integer> hGaps = getGaps(n, vFences);
        Set<Integer> vGaps = getGaps(m, hFences);

        int[] sortedHGaps = hGaps.stream().sorted().mapToInt(i -> i).toArray();
        int[] sortedVGaps = vGaps.stream().sorted().mapToInt(i -> i).toArray();

        // 逆序查找，从可能的最大值开始，减少查找次数
        for (int i = sortedHGaps.length - 1; i >= 0; i--) {
            int target = Arrays.binarySearch(sortedVGaps, sortedHGaps[i]);
            if (target >= 0) {
                long res = (long)sortedHGaps[i] * (long)sortedHGaps[i];
                return (int) (res % MOD);
            }
            
        }

        return  -1;
    }

    private Set<Integer> getGaps(int m, int[] fences) {
        Set<Integer> res = new HashSet<>();
        // 计算每两个fence之间的距离来找到可能的边长，不需要排序，两个fence中间若还有其他fence，将其视为去除即可，不影响结果
        // 实际就是枚举两个保留的fence，其他fence都除去情况下的边长
        for (int i = 0; i < fences.length; i++) {
            for (int j = 0; j < fences.length; j++) {
                if (i==j) continue;
                // 只正向计算，负向的交给之后的遍历
                if (fences[i] > fences[j]) res.add(fences[i] - fences[j]);
            }
            // 还有当前fence与前后两个边框组成的距离
            res.add(m - fences[i]);
            res.add(fences[i] - 1);
        }

        // 所有fence都除去的边长
        res.add(m - 1);

        return res;
    }

    public static void main(String[] args) {
        int[] testMs = {5,3,6,4};
        int[] testNs = {3,7,7,3};
        int[][] testHFences = {
            {4},            
            {2},
            {2},
            {2,3},
        };
        int[][] testVFences = {
            {2},
            {4},
            {4},
            {2},
        };

        Solution s = new Solution();
        for(int i = 0; i < testMs.length; i++) {
            System.out.println("No."+i+" test case: "+s.maximizeSquareArea(testMs[i], testNs[i], testHFences[i], testVFences[i]));
        }
        
    }
}