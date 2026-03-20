public class Solution {
    // 本原勾股数：
    // a = 2mn, b = m^2 - n^2, c = m^2 + n^2
    // 其中m，n为互质且奇偶性不同的正整数
    // 其他勾股数由本原勾股数倍数产生
    public int countTriples(int n) {
        int res = 0;
        for (int i = 2; i * i < n; i++) {
            for (int j = 1; j < i && i*i + j*j <= n; j++) {
                if ((i-j)%2 != 0 && gcd(i, j)==1) { // i, j 奇偶性不同且互质
                    res += n / (i*i + j*j);
                }
            }
        }

        return res * 2;
    }

    public int gcd(int a, int b) {
        return b==0 ? a : gcd(b, a%b);
    }

    //[法一]: 暴力解法
    public int countTriples1(int n) {
        int res = 0;
        int largestC = n*n;
        int epsilon = (int) 1e-9;

        for (int i = 1; i < n - 1; i++) {
            int a = i;
            for (int j = i + 1; j < n; j++) {
                int b = j;
                int c2 = a*a + b*b;
                double remainder = Math.sqrt(c2) % 1.0;
                System.out.println(remainder);
                if (remainder > epsilon) continue;
                if (c2 <= largestC) {
                    res += 2;
                } else {
                    break;
                }
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int[] testN = {5, 10, 12, 14, 20};

        Solution s = new Solution();
        for (int i = 0; i < testN.length; i++) {
            System.out.println("No." + i + " result: " + s.countTriples(testN[i]));
        }
    }
}