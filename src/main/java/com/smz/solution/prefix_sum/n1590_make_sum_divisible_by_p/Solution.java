import java.util.HashMap;
import java.util.Map;

public class Solution {

    // 去除子数组，使得剩下的数组和能被p整除  --> (S-s)%p = 0 --> S%p = s%p = x
    // 子数组和一般可使用前缀和计算 --> s = prefix[j] - prefix[i] (i < j) --> j一定，i越大，子数组越短
    // s%p = x
    // --> (prefix[j]-prefix[i])%p = x
    // --> prefix[j] - prefix[i] = kp + x
    // --> prefix[j] - kp - x = prefix[i] (0=< kp+x < prefix[j])
    // 假如将数组遍历，遍历至下标j时，可以得知prefix[j], 以及prefix[j]%p,可记录此时余数为x时，最大的下标m，即maxReminder[x]=m
    public int minSubarray(int[] nums, int p) {
        int n = nums.length;
        long totalSum = 0;
        for (int i = 0; i < n; i++) {
            totalSum += nums[i];
        }
        int targetRemainder = (int)(totalSum % p);
        if (targetRemainder == 0) {
            return 0; // 总和本身就能被p整除
        }

        int result = n;
        // 这里使用数组可能导致内存
        // int biggestIndexOfRemainder[] = new int[p]; // 余数可能值从0到p-1
        // for (int i = 0; i < p; i++) {
        //     biggestIndexOfRemainder[i] = -1; // 初始化为-1，表示还未出现过该余数
        // }
        Map<Integer, Integer> biggestIndexOfRemainder = new HashMap<>();

        int curRemainder = 0;
        for (int i = 0; i < n; i++) {
            // 更新操作放在最开始，记录f(0)时的子数组，其不包含下标为0时的元素，故和为0; 此时记录下标为0表明此后记录的下标，均是不包含该下标的子数组，故计算长度时需+1
            // biggestIndexOfRemainder[curRemainder] = i;
            biggestIndexOfRemainder.put(curRemainder, i);

            curRemainder = (curRemainder + nums[i]) % p;
            int calRemainder = (curRemainder - targetRemainder + p) % p;
            // if (biggestIndexOfRemainder[calRemainder] != -1) {
            if (biggestIndexOfRemainder.containsKey(calRemainder)) {
                result = Math.min(result, i - biggestIndexOfRemainder.get(calRemainder) + 1);
                // result = Math.min(result, i - biggestIndexOfRemainder[calRemainder] + 1);
            }
        }
        
        return result == n ? -1 : result;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        int p = 7;
        System.out.println("Solution:" + new Solution().minSubarray(nums, p));
    }
}
