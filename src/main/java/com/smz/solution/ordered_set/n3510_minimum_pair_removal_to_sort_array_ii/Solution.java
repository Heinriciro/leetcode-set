package com.smz.solution.ordered_set.n3510_minimum_pair_removal_to_sort_array_ii;
import java.util.Arrays;
import java.util.TreeSet;

public class Solution {
    // 分析：本题与NO.3507一模一样，区别在于数据约束变为nums.length<=10^5， nums[i]<=10^9，导致无法使用纯暴力方式O(N^2)解决
    //       可知3507中将数据放在链表中便于删除，然后每次操作在数据链表中重新寻找最小的数字对。本题我们即可从这里入手进行优化:
    //          1. 首先，无需每次重新遍历链表，而是维护记录每一个数对的和，可以使用类似于小顶堆,或者有序集合来维护大小
    //          2. 其次，替换数对后，是否非递减，以及前后坐标会有所变化，可以使用一个有序的集合来记录 现存的坐标
    
    // [法一]：模拟 + 有序集合
    private record Pair(long sum, int first) {}
    public int minimumPairRemoval(int[] nums) {
        // 注意这里不能直接使用(int)(o1.sum - o2.sum) 直接转换会因为溢出造成结果错误，要么使用Long.compare, 要么手动根据正负情况给出结果
        TreeSet<Pair> pairSums = new TreeSet<>((Pair o1, Pair o2) -> o1.sum != o2.sum ? Long.compare(o1.sum, o2.sum) : o1.first - o2.first);
        TreeSet<Integer> idxes = new TreeSet<>();
        int dec = 0;

        long[] curNums = Arrays.stream(nums).mapToLong((i) -> (long)i).toArray();

        for (int i = 0; i < curNums.length - 1; i++) {
            long cur = curNums[i];
            long nxt = curNums[i+1];
            idxes.add(i);
            pairSums.add(new Pair(nxt + cur, i));
            if (nxt < cur) dec++;
        }
        idxes.add(curNums.length-1);

        int ans = 0;
        while (dec > 0) {
            Pair select = pairSums.pollFirst();
            Integer prev = idxes.lower(select.first);
            int first = select.first;
            int second = idxes.higher(select.first);
            Integer next = idxes.higher(second);

            if (curNums[first] > curNums[second]) dec--;

            if(prev != null && curNums[prev] <= curNums[first] && curNums[prev] > select.sum) dec++;
            else if (prev != null && curNums[prev] > curNums[first] && curNums[prev] <= select.sum) dec--;

            if(next != null && curNums[second] <= curNums[next] && select.sum > curNums[next]) dec++;
            else if (next != null && curNums[second] > curNums[next] && select.sum <= curNums[next]) dec--;

            if(prev != null) {
                pairSums.remove(new Pair(curNums[prev] + curNums[first], prev));
                pairSums.add(new Pair(select.sum + curNums[prev], prev));
            }
            if(next != null){
                pairSums.remove(new Pair(curNums[second] + curNums[next], second));
                pairSums.add(new Pair(select.sum + curNums[next], first));
            } 
            curNums[first] = select.sum;
            idxes.remove(second);
            ans++;
        }

        return ans;
    }

    public static void main(String[] args) {
        int[][] testNums = {
            {2,2,-1,3,-2,2,1,1,1,0,-1},
            {5,2,3,1},
            {1,2,2},
        };

        Solution s = new Solution();
        for (int i = 0; i < testNums.length; i++) {
            System.out.println("No."+i+" test case: "+s.minimumPairRemoval(testNums[i]));
        }
    }
}