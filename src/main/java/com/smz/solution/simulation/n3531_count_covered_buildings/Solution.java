import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Solution {

    // [法二]：模拟，查找每行每列的最大最小值，最终遍历坐标时若x，y坐标均处于最大最小值中间即符合要求
    public int countCoveredBuildings(int n, int[][] buildings) {
        int[] maxRow = new int[n + 1];
        int[] minRow = new int[n + 1];
        int[] maxCol = new int[n + 1];
        int[] minCol = new int[n + 1];
        
        Arrays.fill(minRow, n + 1);
        Arrays.fill(minCol, n + 1);

        for (int[] p : buildings) {
            int x = p[0], y = p[1];
            maxRow[y] = Math.max(maxRow[y], x);
            minRow[y] = Math.min(minRow[y], x);
            maxCol[x] = Math.max(maxCol[x], y);
            minCol[x] = Math.min(minCol[x], y);
        }
        
        int res = 0;
        for (int[] p : buildings) {
            int x = p[0], y = p[1];
            if (x > minRow[y] && x < maxRow[y] && 
                y > minCol[x] && y < maxCol[x]) {
                res++;
            }
        }
        
        return res;
    }

    // [法一]：哈希表 + Set方便查找
    public int countCoveredBuildings1(int n, int[][] buildings) {
        Map<Integer, List<Integer>> sameX2Y = new HashMap<>();
        Map<Integer, List<Integer>> sameY2X = new HashMap<>();

        Map<Integer, Set<Integer>> sameX2YCovered = new HashMap<>();
        Map<Integer, Set<Integer>> sameY2XCovered = new HashMap<>();

        for (int[] coordinates: buildings) {
            int x = coordinates[0];
            int y = coordinates[1];
            sameX2Y.computeIfAbsent(x, _->new ArrayList<>()).add(y);
            sameY2X.computeIfAbsent(y, _->new ArrayList<>()).add(x);
        }

        for (Integer x : sameX2Y.keySet()) {
            List<Integer> yList = sameX2Y.get(x);
            if (yList.size() <= 2) {
                yList.clear();
                continue;
            }
            yList.sort(Integer::compareTo);
            // 去除头尾，中间的点即为所有在这一方向被覆盖的点的y坐标
            yList.removeFirst();
            yList.removeLast();

            sameX2YCovered.put(x, new HashSet<>(yList));
        }

        for (Integer y : sameY2X.keySet()) {
            List<Integer> xList = sameY2X.get(y);
            if (xList.size() <= 2) {
                xList.clear();
                continue;
            }
            xList.sort(Integer::compareTo);
            // 去除头尾，中间的点即为所有在这一方向被覆盖的点的x坐标
            xList.removeFirst();
            xList.removeLast();

            sameY2XCovered.put(y, new HashSet<>(xList));
        }

        int res = 0;
        for (Integer x : sameX2YCovered.keySet()) {
            Set<Integer> coveredYSet = sameX2YCovered.get(x);

            for (Integer coveredY : coveredYSet) {
                Set<Integer> coveredXSet = sameY2XCovered.get(coveredY);
                if (coveredXSet != null && coveredXSet.contains(x)) res++;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int[][][] testBuildings = {
            {{1,2}, {2,2}, {3,2}, {2,1}, {2,3}},
            {{1,1}, {1,2}, {2,1}, {2,2}},
            {{1,3}, {3,2}, {3,3}, {3,5}, {5,3}},
        };
        int[] testNs = {3,3,5};

        Solution s = new Solution();
        for (int i = 0; i < testNs.length; i++) {
            System.out.println("No." + i + " test case: " + s.countCoveredBuildings(testNs[i], testBuildings[i]));
        }
    }

}