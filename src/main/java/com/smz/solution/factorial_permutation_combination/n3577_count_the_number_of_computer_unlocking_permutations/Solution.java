public class Solution {
    public static final int MOD = 1_000_000_007;
    public int countPermutations(int[] complexity) {
        int root = complexity[0];
        for (int i = 1; i < complexity.length; i++) {
            if (complexity[i] <= root) return 0;
        }

        int n = complexity.length;
        // 其余n-1位置任意排列，共有A(n-1, n-1)中排列方式
        // 即 (n-1)!/(n-1-(n-1))! = (n-1)!
        return factorialWithMod(n - 1, MOD);
    }

    public int factorialWithMod(int n, int mod) {
        long res = 1;
        for (int i = 2; i <= n; i++) {
            res = (res * i) % MOD;
        }
        return (int) res;
    }

    public static void main(String[] args) {
        int[][] testComplexity = {
            {1, 2, 3},
            {3, 3, 4, 4}
        };

        for (int i = 0; i < testComplexity.length; i++) {
            Solution sol = new Solution();
            System.out.println("No." + i + " testcase: " + sol.countPermutations(testComplexity[i]));
        }
    }
}