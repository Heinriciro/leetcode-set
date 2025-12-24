import java.util.Arrays;

public class Solution {
    // [法一]：贪心+排序
    public int minimumBoxes(int[] apple, int[] capacity) {
        Integer[] sortedCapacity = Arrays.stream(capacity).boxed().toArray(Integer[]::new);
        Arrays.sort(sortedCapacity, (a, b) -> b - a);
        int total = Arrays.stream(apple).sum();
        int res = 0;

        int allCap = 0;
        for (int i = 0; i < sortedCapacity.length && allCap < total; i++) {
            allCap += sortedCapacity[i];
            res++;
        }
        return res;
    } 

    public static void main(String[] args) {
        int[][] testApples = {
            {1,8,3,3,5},
            {1,3,2},
            {5,5,5},
        };

        int[][] testCapacities = {
            {3, 9, 5, 1, 9},
            {4,3,1,5,2},
            {2,4,2,7},
        };

        Solution s = new Solution();
        for (int i = 0; i < testApples.length; i++) {
                System.out.println("No." + i + " test result: " + s.minimumBoxes(testApples[i], testCapacities[i]));
        }
    }
}