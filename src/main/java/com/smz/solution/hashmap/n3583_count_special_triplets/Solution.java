import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Solution {
    public static final int MOD = 1_000_000_007;


    // 法二：官方题解，原理一致，大幅简化步骤，代码优美，但实际时间慢于法一，空间占用也好的有限。将法一中的指针移动部分直接整合进入一次遍历中，不需要记录所以，仅记录计数
    public int specialTriplets(int[] nums) {
        Map<Integer, Integer> numCnt = new HashMap<>();
        Map<Integer, Integer> numPartialCnt = new HashMap<>();
        
        for (int v : nums) {
            numCnt.put(v, numCnt.getOrDefault(v, 0) + 1);
        }
        
        long ans = 0;
        for (int v : nums) {
            int target = v * 2;
            int lCnt = numPartialCnt.getOrDefault(target, 0);
            numPartialCnt.put(v, numPartialCnt.getOrDefault(v, 0) + 1);
            int rCnt = numCnt.getOrDefault(target, 0) - numPartialCnt.getOrDefault(target, 0);
            ans = (ans + (long) lCnt * rCnt) % MOD;
        }
        
        return (int) ans;
    }

    // 法一：双指针 + 哈希表 + 数学公式
    public int specialTriplets1(int[] nums) {
        Map<Integer, List<Integer>> value2index = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            value2index.computeIfAbsent(nums[i], _->new ArrayList<>()).add(i);
        }

        long res = 0;
        for (int value : value2index.keySet()) {
            if (value % 2 != 0) continue;
            // 若存在0值，则可以直接计算此时的组三元组数
            if (value == 0) {
                List<Integer> zeroIdxList = value2index.get(value);
                int n = zeroIdxList.size();
                if (n < 3) continue;
                if (n == 3) {
                    res += 1;
                    continue;
                }
                // 此时组数为Cn3, 即 n!/(3!(n-3)!) = n(n-1)(n-2)/6
                long count = (n * (long) (n-1) * (long) (n-2) / 6) % MOD;
                res = (res + count % MOD) % MOD;
                continue;
            }

            // 若不是0值则另外计算三元组数
            int mid = value / 2;
            if (!value2index.containsKey(mid)) continue;

            List<Integer> midIdxList = value2index.get(mid);
            List<Integer> valueIdxList = value2index.get(value);

            res = (res + count(valueIdxList, midIdxList)) % MOD;
        }

        return (int) res;
    }

    private int count(List<Integer> endIdxes, List<Integer> midIdxes) {
        long ans = 0;
        // 若两端索引的可能取值个数不足两个，返回0
        if (endIdxes.size() < 2) return (int)ans;

        // 将三元组的左，中，右端点索引表示为 E(i), M(j), E(k)
        int i = 0, j = 0, k = 0;
        Deque<Integer> midIdxQue = new ArrayDeque<>();
        while (j < midIdxes.size() && k < endIdxes.size()) {
            // 若无重叠，终止
            if (midIdxes.get(j) > endIdxes.getLast() || midIdxes.getLast() < endIdxes.get(i)) break;
            // 找到目前E(i)右边的第一个M(j)
            while (midIdxes.get(j) < endIdxes.get(i)) j++;

            // 若无重叠，终止
            if (midIdxes.get(j) > endIdxes.getLast()) break;
            // 找到M(j)右侧第一个E(k)
            while (midIdxes.get(j) > endIdxes.get(k)) k++;

            // 找到E(i)，E(k)中间所有的M(j)，并入队。注意入队完成后，此时的M(j)已经不在E(i) E(k)中间
            while (j < midIdxes.size() && midIdxes.get(j) < endIdxes.get(k)) midIdxQue.addLast(midIdxes.get(j++));
            // 向右移i,找到距离此时的中间值最近的E(i)。注意找到后，此时的E(i)已经不在中间值左侧，其应该等于E(k)
            while (endIdxes.get(i) < midIdxQue.getFirst()) i++;
            // 此时该左端点以左的所有端点值，与 目前所有的中点值， 和 距离中点值最近的右端点以右的所有端点值，能够构成三元组
            ans = ans + ((long) i * ((long) midIdxQue.size() * (long) (endIdxes.size() - k))) % MOD;
            //此时E(i)必然已经跳过了所有中间值，所以直接将队列清零，这样看可以优化掉该队列
            midIdxQue.clear();
        }

        return (int) (ans % MOD);
    }
    
    public static void main(String[] args) {
        int[] largeZeros = new int[100000];

        int[] large5 = new int[33333];
        int[] large10 = new int[33333];
        Arrays.fill(large5, 5);
        Arrays.fill(large10, 10);
        IntStream a = IntStream.concat(Arrays.stream(large10), Arrays.stream(large5));
        int[] largeTest = IntStream.concat(a, Arrays.stream(large10)).toArray();
        int[][] testNums = {
            {6, 3, 6},
            {0, 1, 0, 0},
            {8, 4, 2, 8, 4},
            largeZeros,
            largeTest
        };

        Solution s = new Solution();

        for (int i = 0; i < testNums.length; i++) {
            System.out.println("No." + i + " test result: " + s.specialTriplets(testNums[i]));
        }
    }
}