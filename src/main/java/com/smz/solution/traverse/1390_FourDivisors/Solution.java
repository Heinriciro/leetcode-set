import java.util.HashMap;

public class Solution {
    // [法一]：暴力+平方根剪枝
    private static HashMap<Integer, Integer> divisorNum = new HashMap<>();
    public int sumFourDivisors(int[] nums) {
        int res = 0;

        for (int num : nums) {
            if (divisorNum.containsKey(num)) {
                res += divisorNum.get(num);
            }
            else {
                double boundry = Math.sqrt(num);
                int count = 2; // 1 和 自身
                int sum = 1 + num;
                for (int i = 2; i <= boundry; i++) {
                    if (i == boundry) {
                        count++;
                        sum += boundry;
                    }
                    else {
                        if (num % i == 0) {
                            count += 2;
                            sum += i;
                            sum += num / i;
                        }
                    }
                }
                if (count == 4) {
                    res += sum;
                    divisorNum.put(num, sum);
                }
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int[][] testNums = {
            {21,4,7},
            {21,21},
            {1,2,3,4,5},
        };

        Solution s = new Solution();
        for (int i = 0; i < testNums.length; i++) {
            System.out.println("No."+i+" test case: "+s.sumFourDivisors(testNums[i]));
        }
    }
}