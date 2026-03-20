import java.util.HashMap;
import java.util.Map;

public class Solution {
    public int countTrapezoids(int[][] points) {
        Map<Integer, Integer> yCount = new HashMap<>();
        for (int[] point : points) {
            yCount.put(point[1], yCount.getOrDefault(point[1], 0) + 1);
        }

        // 设统一平行线上的点组成的线段数量为 l(i), 共有n组平行线
        // 某一平行线i和j上的点能够组成的梯形数量n(i)为 l(i) * Sigma(l(j)) (j != i)\
        // 再注意去除重复的组合
        // n(1) = l(1) * (l(2) + l(3) + ... + l(m))
        // n(2) = l(2) * (l(3) + ... + l(m)) 
        // n(3) = l(3) * (l(4) + ... + l(m))
        // ...
        // n(m-1) = l(m-1) * l(m)
        // 总的梯形数量为 n(1) + n(2) + ... + n(m-1)
        // 计算方法： 故遍历l(i)，同时计算累加和，用此时遍历的l(i)乘之前的累加和，再最终相加
        long res = 0;
        long sum = 0;
        for (long count : yCount.values()) {
            if (count == 1) continue;
            long lineCount = count * (count - 1) / 2 % 1000000007;
            long curRes = lineCount * sum % 1000000007;
            res = (res + curRes) % 1000000007;
            sum = (sum + lineCount) % 1000000007;
        }

        return (int) res;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[][][] testPoints = {
            {{1,0}, {2,0}, {3,0}, {2,2}, {3,2}}, // 3 expected
            {{0,0}, {1,0}, {0,1}, {2,1}}, // 1 expected
        };

        int count = 1;
        for (int[][] points : testPoints) {
            int result = solution.countTrapezoids(points);
            System.out.println("No." + count++ + " test result:" + result);
        }
    }
    
}
