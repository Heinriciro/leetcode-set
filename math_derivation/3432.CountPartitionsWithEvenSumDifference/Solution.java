class Solution {
    // 对于被分成两份的数组 [0, i] 和 [i+1, n-1]
    // 前者的和为 prefix(i), 后者的和为 sum - prefix(i)
    // 两者的差即为 sum - 2 * prefix(i), 题目要求差为偶数
    // 分析可知 2 * prefix(i)必然是偶数 --> sum为偶数时，对于所有i(0 <= i < n-1)，都有差值为偶数, 故此时答案即为n-1
    // 当sum为奇数时，对于所有i，必然有差值是奇数，故此时答案为0
    public int countPartitions(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        return sum % 2 == 0 ? nums.length - 1 : 0;
    }

    public static void main(String[] args) {
        int[][] testNums = {
            {10, 10, 3, 7, 6},
            {1, 2, 2},
            {2, 4, 6, 8}
        };

        Solution s = new Solution();
        int i = 0;
        for (int[] nums : testNums) {
            System.out.println("No." + i++ + " result: " + s.countPartitions(nums));
        }
    }
}