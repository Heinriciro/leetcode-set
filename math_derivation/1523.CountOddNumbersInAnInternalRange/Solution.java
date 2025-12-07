public class Solution {
    public int countOdds(int low, int high) {
        int n = high - low + 1;
        
        if (low % 2 != 0 && n % 2 !=0) { 
            return n / 2 + 1;
        } else {
            return n / 2;
        }
    }

    public static void main(String[] args) {
        int[] testLows = {3, 8, 5, 6};
        int[] testHighs = {7, 10, 5, 6};

        Solution s = new Solution();
        for (int i = 0; i<testHighs.length; i++) {
            System.out.println("No." + i + " results: " + s.countOdds(testLows[i], testHighs[i]));
        }
    }
}