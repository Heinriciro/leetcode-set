public class Solution {
    public int minDeletionSize(String[] strs) {
        int n = strs.length;
        int len = strs[0].length();

        int res = 0;
        for (int i = 0; i < len; i++) {
            for (int j = 1; j < n; j++) {
                if (strs[j].charAt(i) < strs[j - 1].charAt(i)) {
                    res++;
                    break;
                }
            }
        }

        return res;
    }


    public static void main(String[] args) {
        String[][] testStrs = {
            {"cba", "daf", "ghi"},
            {"a", "b"},
            {"zyx", "wvu", "tsr"}
        };
        
        Solution s = new Solution();
        for (int i = 0; i < testStrs.length; i++) {
            System.out.println("No." + i + " test case: " + s.minDeletionSize(testStrs[i]));
        }
    }
}