import java.util.Arrays;
import java.util.Comparator;

public class Solution {
    // [法一]：排序+贪心
    //    对于任意两个数，在题目要求的情况， 即后选的数会减一的情况下，可以按任意顺序挑选而不影响结果。 可知若想使得总体和最大，需要优先挑选大数
    public long maximumHappinessSum(int[] happiness, int k) {
        Integer[] sorted = Arrays.stream(happiness).boxed().sorted(Comparator.reverseOrder()).toArray(Integer[]::new);

        long res = 0l;
        int select = 0;
        for (int i = 0; i < k; i++) {
            if (sorted[select] > i) {
                res += sorted[select] - i;
            }
            select++;
        }

        return res;
    }

    public static void main(String[] args) {
        int[][] testHappiness = {
            {12,1,42},
            {1,1,1,1},
            {2,3,4,5},
        };
        int[] testKs = {3,2,1};

        Solution s = new Solution();
        for (int i = 0; i < testHappiness.length; i++) {
            System.out.println("No."+i+" test case: "+s.maximumHappinessSum(testHappiness[i], testKs[i]));
            
        }
    }
}