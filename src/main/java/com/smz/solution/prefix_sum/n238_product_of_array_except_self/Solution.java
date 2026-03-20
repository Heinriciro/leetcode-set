import java.util.Arrays;

class Solution {
    // [法一]：前缀后缀和(积)
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] prefixP = new int[n];
        int[] suffixP = new int[n];

        prefixP[0] = 1;
        suffixP[n-1] = 1;
        for (int i = 1; i < n; i++) {
            prefixP[i] = prefixP[i-1]*nums[i-1];
        }
        for (int i = n-2; i >=0 ; i--) {
            suffixP[i] = suffixP[i+1]*nums[i+1];
        }

        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = suffixP[i] * prefixP[i];
        }

        return res;
    }

    public static void main(String[] args) {
        int[][] testNums = {
            {1,2,3,4},
            {-1,1,0,-3,3},
        };
        
        Solution s = new Solution();
        for (int i = 0; i < testNums.length; i++){
            System.out.println("No."+i+" test case: "+Arrays.toString(s.productExceptSelf(testNums[i])));
        }
    }
}