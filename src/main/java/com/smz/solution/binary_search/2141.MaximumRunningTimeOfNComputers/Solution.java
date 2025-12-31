class Solution {

    // 1. 此法计算出的结果，是理论上的最大结果，考虑到耗光电池电量后如果电池数量小于电脑数量，就无法平均分配时间
    // public long maxRunTime(int n, int[] batteries) {
    //     long totalBattery = 0;
    //     for (int battery : batteries) {
    //         totalBattery += battery;
    //     }

    //     return totalBattery / (long) n;
    // }

    // 2. 在法1的基础上，从0到理论最大时间中间，通过二分法寻找到一个符合条件的值
    public long maxRunTime(int n, int[] batteries) {
        long totalBattery = 0;
        for (int battery : batteries) {
            totalBattery += battery;
        }

        long left = 0l;
        long right = totalBattery / (long) n; //此为理论最大时间
        while (left < right) {
            long mid = (left + right + 1) / 2; // mid为此次判断是否符合要求的时间
            // 满足要求须符合的条件：
            // 1. 一个电池同时间只能给最多1个电脑使用 ==> mid时间内，一个电池最多提供mid的电量
            // 2. 一个电池最多只能提供自己本身的电量 ==> mid时间内，一个电池最多提供battery[i]的电量
            // ==> 因此，一个电池在mid时间内，最多能为电脑提供的电量为min(battery[i], mid)
            // ==> 所有电池在mid时间内最多提供的总电量为 sum(min(battery[i], mid))
            // 如果mid时间符合要求，那么总电量需要 n * mid ==> 故 sum(min(battery[i], mid)) >= n * mid， 说明电池才能够满足提供mid时间电量的要求
            long capacity = 0;
            for (int i = 0; i < batteries.length; i++) {
                capacity += Math.min(batteries[i], mid);
            }

            if (capacity >= n * mid) {
                // 此时说明该方案可行，那么比mid小的时间必然符合要求，故尝试更大的时间
                left = mid;
            } else {
                // 此时说明该方案不可行，那么比mid大的时间必然不符合要求，故尝试更小的时间
                right = mid - 1;
            }
        }

        return left;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] n = {2, 2, 3};
        int[][] batteries = {
            {3, 3, 3},
            {1, 1, 1},
            {10, 10, 3, 5},
        };

        for (int i = 0; i < n.length; i++) {
            long result = solution.maxRunTime(n[i], batteries[i]);
            System.out.println("Max run time for No." + i + " computers: " + result);
        }
    }
}