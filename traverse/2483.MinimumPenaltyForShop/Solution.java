public class Solution {
    // [法一]：一次遍历
    public int bestClosingTime(String customers) {
        int prevP = 0;
        int lattP = 0;
        for (int i=0; i<customers.length(); i++) {
            if (customers.charAt(i) == 'Y') lattP++;
        }
        int totP = lattP;
        int res = 0;

        for (int i=1; i<customers.length() + 1; i++) {
            if (customers.charAt(i-1) == 'Y') {
                lattP--;
            } else {
                prevP++;
            }

            if (totP > lattP + prevP) {
                totP = lattP + prevP;
                res = i;
            }

        }

        return res;
    }

    public static void main(String[] args) {
        String[] testCustomers = {
            "YYNY",
            "NNNNN",
            "YYYY",
        };

        Solution s = new Solution();
        for (int i=0; i < testCustomers.length; i++) {
            System.out.println("No."+i+" test case: "+s.bestClosingTime(testCustomers[i]));
        }
    }
}