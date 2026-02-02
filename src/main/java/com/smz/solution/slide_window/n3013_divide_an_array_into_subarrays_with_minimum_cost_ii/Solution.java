package com.smz.solution.slide_window.n3013_divide_an_array_into_subarrays_with_minimum_cost_ii;
import java.util.PriorityQueue;
import java.util.TreeSet;

class Solution {
    // No.3010 升级版
    // 分析：首先，如果抛开dist的约束，将该数组分为k组，每组第一个数为cost，总最小cost就是第一个数，加上另外k-1个最小数之和
    //      其次，加入dist的约束后，需要考虑第二组第一个数与最后一组第一个数之间的距离要不大于dist
    // 思路：首先，第一个数是必定要取的，而如果第二组第一个数的位置能够确定，就能确定最后一组第一个数取值的范围，在这个范围内找到除第一组第一个数，第二组第一个数之外的k-2个最小数即可
    //       那么为了更快地找到这k-2个最小数：
    //       可以采用一个大顶堆和一个小顶堆来维护，大顶堆记录k-2个最小数，小顶堆记录剩余的数，这样方便从小顶堆中将最小的数转移至大顶堆中
    //       每次第二个数位置后移时，将之前的第二个数出堆，后移之后后的最后一个数，若比大顶堆堆顶小，则将大顶堆堆顶出堆，加入该数。
    //       根据出入堆情况既可以直接更新k-2个数之和，从而计算总cost
    
    
    // [法二]：枚举第二组第一个数 + 双有序集合维护k-2个最小数
    //     将双堆替换为双有序集合，删除时速度更快
    private record Pair(int value, int index) {}
    public long minimumCost(int[] nums, int k, int dist) {
        // 大顶堆，值相同的时候，下标小的在前面
        TreeSet<Pair> maxHeap = new TreeSet<>((o1, o2) -> o2.value == o1.value ? o1.index - o2.index : o2.value - o1.value);
        // 小顶堆，值相同的时候，下标小的在前面
        TreeSet<Pair> minHeap = new TreeSet<>((o1, o2) -> o1.value == o2.value ? o1.index - o2.index : o1.value - o2.value);
        long curSum = nums[0] + nums[1];
        // 以选择nums[1]作为第二组第一个数，初始化小顶堆, 后续需要从dist个数中，选择k-2个最小数，目前先将dist个数全部放入小顶堆
        for (int i = 2; i <= 2 + dist - 1 && i <= nums.length; i++) { // 由于dist <= n -2， 所以后面的i<=nums.length可以省略
            minHeap.add(new Pair(nums[i], i));
        }
        // 从小顶堆中取出k-2个最小数，放入大顶堆
        for (int i = 0; i < k - 2; i++) {
            Pair p = minHeap.pollFirst();
            maxHeap.add(p);
            curSum += p.value;
        }

        long ans = curSum;
        for (int i = 2; i < nums.length - (k - 2); i++) { // 分为k组，第二组第一个数最多只能到nums.length-(k-2)-1
            // 第二组第一个数后移一位
            int prevSecond = nums[i-1];
            curSum -= prevSecond;

            // 当前数选为第二组第一个数，因而需要判断出堆
            int curSecond = nums[i];
            curSum += curSecond;
            // 堆里的元素下标必然大于等于i, 且堆中下标小的元素靠前，
            // 因此当值相等时，堆中所有相等的之中，堆顶的下标最小，且大于等于i，所以只有下标相等才出堆，若堆顶的下标更大，说明当前curSecond不在堆中
            if (curSecond < maxHeap.first().value || curSecond == maxHeap.first().value && i == maxHeap.first().index) {
                // curSecond在大顶堆中，出堆
                curSum -= curSecond;
                // Record实现了特定的equals, 因而当成员相等时两对象相等，故可以直接remove
                // https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/Record.html#equals(java.lang.Object)
                maxHeap.remove(new Pair(curSecond, i));
            } else {
                // curSecond在小顶堆中，出堆
                minHeap.remove(new Pair(curSecond, i));
            }

            // 将新加入的最后一个数加入小顶堆
            if (i + dist < nums.length) {
                int last = nums[i+dist];
                minHeap.add(new Pair(last, i + dist));
            }

            // 更新小顶堆和大顶堆: 此时要么小顶堆除去了一个元素，要么大顶堆除去了一个元素, 随后小顶堆一定添加了一个新的元素，这个元素有可能破坏大顶堆和小顶堆之间的大小关系
            // 此时只有当新加入小顶堆的元素成为了小顶堆新的堆顶，才可能破坏大小关系。并且由于新加入的元素，其位置必然是最大的，所以如果此时新元素和大顶堆的堆顶值一样，并不需要交换, 只有大顶堆元素数量不够时，后面会直接将其加入大顶堆。
            while (!maxHeap.isEmpty() && !minHeap.isEmpty() && minHeap.first().value < maxHeap.first().value) {
                Pair fromMin = minHeap.pollFirst();
                Pair fromMax = maxHeap.pollFirst();
                curSum = curSum - fromMax.value + fromMin.value;
                maxHeap.add(fromMin);
                minHeap.add(fromMax);
            }

            // 若大顶堆元素数量不够k-2个，则从小顶堆中加入
            while (maxHeap.size() < k - 2) {
                Pair fromMin = minHeap.pollFirst();
                maxHeap.add(fromMin);
                curSum += fromMin.value;
            }

            ans = Math.min(ans, curSum);
        }

        return ans;
    }

    // [法一]：枚举第二组第一个数 + 双堆(大顶堆&小顶堆)维护k-2个最小数
    public long minimumCost1(int[] nums, int k, int dist) {
        // 大顶堆，值相同的时候，下标小的在前面
        PriorityQueue<Pair> maxHeap = new PriorityQueue<>((o1, o2) -> o2.value == o1.value ? o1.index - o2.index : o2.value - o1.value);
        // 小顶堆，值相同的时候，下标下的在前面
        PriorityQueue<Pair> minHeap = new PriorityQueue<>((o1, o2) -> o1.value == o2.value ? o1.index - o2.index : o1.value - o2.value);
        long curSum = nums[0] + nums[1];
        // 以选择nums[1]作为第二组第一个数，初始化小顶堆, 后续需要从dist个数中，选择k-2个最小数，目前先将dist个数全部放入小顶堆
        for (int i = 2; i <= 2 + dist - 1 && i <= nums.length; i++) { // 由于dist <= n -2， 所以后面的i<=nums.length可以省略
            minHeap.offer(new Pair(nums[i], i));
        }
        // 从小顶堆中取出k-2个最小数，放入大顶堆
        for (int i = 0; i < k - 2; i++) {
            Pair p = minHeap.poll();
            maxHeap.offer(p);
            curSum += p.value;
        }

        long ans = curSum;
        for (int i = 2; i < nums.length - (k - 2); i++) { // 分为k组，第二组第一个数最多只能到nums.length-(k-2)-1
            // 第二组第一个数后移一位
            int prevSecond = nums[i-1];
            curSum -= prevSecond;

            // 当前数选为第二组第一个数，因而需要判断出堆
            int curSecond = nums[i];
            curSum += curSecond;
            // 堆里的元素下标必然大于等于i, 且堆中下标小的元素靠前，
            // 因此当值相等时，堆中所有相等的之中，堆顶的下标最小，且大于等于i，所以只有下标相等才出堆，若堆顶的下标更大，说明当前curSecond不在堆中
            if (curSecond < maxHeap.peek().value || curSecond == maxHeap.peek().value && i == maxHeap.peek().index) {
                // curSecond在大顶堆中，出堆
                curSum -= curSecond;
                // Record实现了特定的equals, 因而当成员相等时两对象相等，故可以直接remove
                // https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/Record.html#equals(java.lang.Object)
                maxHeap.remove(new Pair(curSecond, i));
            } else {
                // curSecond在小顶堆中，出堆
                minHeap.remove(new Pair(curSecond, i));
            }

            // 将新加入的最后一个数加入小顶堆
            if (i + dist < nums.length) {
                int last = nums[i+dist];
                minHeap.offer(new Pair(last, i + dist));
            }

            // 更新小顶堆和大顶堆: 此时要么小顶堆除去了一个元素，要么大顶堆除去了一个元素, 随后小顶堆一定添加了一个新的元素，这个元素有可能破坏大顶堆和小顶堆之间的大小关系
            // 此时只有当新加入小顶堆的元素成为了小顶堆新的堆顶，才可能破坏大小关系。并且由于新加入的元素，其位置必然是最大的，所以如果此时新元素和大顶堆的堆顶值一样，并不需要交换, 只有大顶堆元素数量不够时，后面会直接将其加入大顶堆。
            while (maxHeap.peek() != null && minHeap.peek() != null && minHeap.peek().value < maxHeap.peek().value) {
                Pair fromMin = minHeap.poll();
                Pair fromMax = maxHeap.poll();
                curSum = curSum - fromMax.value + fromMin.value;
                maxHeap.offer(fromMin);
                minHeap.offer(fromMax);
            }

            // 若大顶堆元素数量不够k-2个，则从小顶堆中加入
            while (maxHeap.size() < k - 2) {
                Pair fromMin = minHeap.poll();
                maxHeap.offer(fromMin);
                curSum += fromMin.value;
            }

            ans = Math.min(ans, curSum);
        }

        return ans;
    }


    public static void main(String[] args) {
        int[][] testNums = {
            {1,3,2,6,4,2},
            {10,1,2,2,2,1},
            {10,8,18,9},
        };
        int[] testK = {3, 4, 3};
        int[] testDist = {3, 3, 1};

        Solution solution = new Solution();
        for (int i = 0; i < testNums.length; i++) {
            System.out.println("No."+i+" test case: " + solution.minimumCost(testNums[i], testK[i], testDist[i]));
        }
    }

}