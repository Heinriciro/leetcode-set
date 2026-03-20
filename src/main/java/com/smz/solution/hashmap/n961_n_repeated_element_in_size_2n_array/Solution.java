
import java.util.HashSet;

public class Solution {
    public int repeatedNTimes(int[] nums) {
        HashSet<Integer> elements = new HashSet<>();

        for (int num : nums) {
            if (elements.contains(num)) {
                return num;
            } else {
                elements.add(num);
            }
        }
        return -1; // This line should never be reached given the problem constraints
    }

    public static void main(String[] args) {
        int[][] testNums = {
            {1,2,3,3},
            {2,1,2,5,3,2},
            {5,1,5,2,5,3,5,4},
        };

        Solution s = new Solution();
        for (int i = 0; i < testNums.length; i++) {
            System.out.println("No."+i+" test case:"+s.repeatedNTimes(testNums[i]));
        }
    }
}