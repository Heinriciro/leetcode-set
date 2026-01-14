package com.smz.solution.segment_tree.n3454_separate_squares_ii;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Solution {
    private record Square(int y, int lx, int rx, int weight) {}

    public double separateSquares(int[][] squares) {
        Set<Integer> vEdgeLocationXs = new HashSet<>();
        Square[] squareEvents = new Square[squares.length * 2]; // 记录正方形上下边，1表示下边，-1表示上边

        int idx = 0;
        for (int[] sq : squares) {
            int lx = sq[0], by = sq[1], len = sq[2];
            int rx = lx + len, ty = by + len;
            vEdgeLocationXs.add(lx);
            vEdgeLocationXs.add(rx);
            squareEvents[idx++] = new Square(by, lx, rx, 1); // 下边
            squareEvents[idx++] = new Square(ty, lx, rx, -1); //
        }

        // 将去重了的竖直边，按横坐标排序后转换为数组
        int[] vEdgesXs = vEdgeLocationXs.stream().sorted().mapToInt(i -> i).toArray();

        // 以上述竖直边构造线段树，记录两边之间区间的 正方形覆盖情况
        SegmentTree tree = new SegmentTree(vEdgesXs);


        // 模拟扫描以计算正方形覆盖后的面积
        Arrays.sort(squareEvents, Comparator.comparingInt(sq -> sq.y)); // 将上下边事件按照水平边纵坐标从小到大排序，便于从小到大扫描
        // 记录每个水平边下方的覆盖长度，以及该水平边对应的覆盖宽度
        ArrayList<Integer> xs = new ArrayList<>();
        ArrayList<Long> areas = new ArrayList<>();

        long totalArea = 0;
        for (int i = 0; i < squareEvents.length - 1; i++) { // 注意最后一个水平边不需要处理
            Square e = squareEvents[i];
            int y = e.y, lx = e.lx, rx = e.rx, w = e.weight;
            // 枚举到的正方形，首先在竖直边中查找
            int coveredLeftIdx = Arrays.binarySearch(vEdgesXs, lx);
            int coveredRightIdx = Arrays.binarySearch(vEdgesXs, rx) - 1; // 右端点对应的区间编号需要-1
            // 更新此时线段树中该区间的覆盖情况
            tree.update(coveredLeftIdx, coveredRightIdx, w);
            // 更新后，查看此时线段树被覆盖的宽度
            int curWidth = vEdgesXs[vEdgesXs.length - 1] - vEdgesXs[0] - tree.getUncoveredLen();
            long area = (long)curWidth * (long)(squareEvents[i+1].y - y);
            areas.add(totalArea);
            xs.add(curWidth);
            totalArea += area;
        }


        // 已经计算出所有正方形覆盖后的面积 totalArea，以及每一个水平线下的覆盖宽度 xs 和对应的面积 areas，找到其中最接近 totalArea/2 的面积，再根据其对应的宽度计算出高度

        // 把水平边也去重，可能可以继续使用二分查找，此处使用线性查找
        int i = 0;
        while ( i < areas.size() && areas.get(i) * 2 < totalArea) {
            i++;
        }
        i--;

        return squareEvents[i].y + (totalArea - areas.get(i) * 2.0) / (xs.get(i) * 2.0);
    }

    public static void main(String[] args) {
        int[][][] testSquares = {
            {{9,14,1},{16,30,1},{26,27,3},{17,24,5}},
            {{0,0,1},{2,2,1}},
            {{0,0,2},{1,1,1}},
        };

        Solution s = new Solution();
        for (int i = 0; i < testSquares.length; i++) {
            System.out.println("No." + i + " test case: " + s.separateSquares(testSquares[i]));
        }
    }

}

class SegmentTree {
    private int n; // 记录区间数量
    private int[] edges; // 记录端点的横坐标
    private int[] minCoverCount; // 记录区间的最小正方形覆盖数
    private int[] minCoverLen; // 记录区间的最小覆盖长度
    private int[] todo; // 记录懒更新信息

    public SegmentTree(int[] edges) {
        this.edges = edges;
        int k = edges.length - 1; // 端点对应的区间个数须-1
        this.n = k;

        int nodeCount = k * 4;
        // 线段树是一个二叉树，其节点数量应为区间数k向上取的最小2次幂和 (即2^0 + 2^1 + ... + 2^k) 之和
        // while (k > 1) {
        //     nodeCount += k;
        //     k = ((k - 1) >> 1) + 1;
        // }
        // nodeCount++;
        minCoverCount = new int[nodeCount];
        minCoverLen = new int[nodeCount];
        todo = new int[nodeCount];

        build(0, 0, n-1);
    }

    public int getUncoveredLen() {
        return minCoverCount[0] == 0 ? minCoverLen[0] : 0; 
        // 根节点记录了整个区间的覆盖情况。 若最小覆盖数为0，说明存在未覆盖的区间，此时minCoverLen表示的是未被覆盖的底边长之和
        // 若最小覆盖数非零，说明所有区域都被覆盖，未被覆盖的长度为0
    }

    public void update(int leftIntervalIdx, int rightIntervalIdx, int weight) {
        update(0, 0, n - 1, leftIntervalIdx, rightIntervalIdx, weight);
    }

    public void update(int nodeIdx, int leftIntervalIdx, int rightIntervalIdx, int updateLeftIdx, int updateRightIdx, int weight) {
        if (updateLeftIdx > rightIntervalIdx || updateRightIdx < leftIntervalIdx) {
            // 不在更新区间内
            return;
        }

        // 当前节点区间完全包含在更新区间内
        if (updateLeftIdx <= leftIntervalIdx && updateRightIdx >= rightIntervalIdx) {
            _do(nodeIdx, weight); // 直接更新当前节点，并储存进入懒加载等待后续更新子节点
            return;
        }

        // 部分包含
        spread(nodeIdx); // 需要将之前积累的懒更新，实际更新到子节点
        int mid = (leftIntervalIdx + rightIntervalIdx) >> 1;
        update(2*nodeIdx + 1, leftIntervalIdx, mid, updateLeftIdx, updateRightIdx, weight);
        update(2*nodeIdx + 2, mid + 1, rightIntervalIdx, updateLeftIdx, updateRightIdx, weight);

        // 根据子节点的覆盖情况更新当前节点的覆盖情况
        maintain(nodeIdx);
    }

    public void spread(int nodeIdx) {
        if (todo[nodeIdx] != 0) {
            _do(2*nodeIdx + 1, todo[nodeIdx]);
            _do(2*nodeIdx + 2, todo[nodeIdx]);
            todo[nodeIdx] = 0;
        }
    }
    
    public void _do(int nodeIdx, int count) {
        minCoverCount[nodeIdx] += count;
        todo[nodeIdx] += count;
    }
    
    private void build(int nodeIdx, int leftIntervalIdx, int rightIntervalIdx) {
        if (leftIntervalIdx == rightIntervalIdx) {
            // 叶子节点
            minCoverLen[nodeIdx] = edges[leftIntervalIdx + 1] - edges[leftIntervalIdx]; // 注意intervalIdx是区间编号，edges存储的是端点坐标, intervalIdx对应的区间为[intervalIdx, intervalIdx+1]
            return;
        }

        // 非叶子节点，首先构建左右子节点
        int mid = (leftIntervalIdx + rightIntervalIdx) >> 1;
        build(2*nodeIdx + 1, leftIntervalIdx, mid);
        build(2*nodeIdx + 2, mid + 1, rightIntervalIdx);

        // 根据子节点的覆盖情况更新当前节点的覆盖情况
        maintain(nodeIdx);
    }

    private void maintain(int nodeIdx) {
        int leftIdx = 2 * nodeIdx + 1;
        int rightIdx = 2 * nodeIdx + 2;
        int minCount = Math.min(minCoverCount[leftIdx], minCoverCount[rightIdx]);

        minCoverCount[nodeIdx] = minCount;
        minCoverLen[nodeIdx] = (minCoverCount[leftIdx] == minCount ? minCoverLen[leftIdx] : 0) + (minCoverCount[rightIdx] == minCount ? minCoverLen[rightIdx] : 0);
    }
}
