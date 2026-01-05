public class Solution {
    // 观察： 在2x2的矩阵中，只要一开始有偶数个负数，总能够通过翻转行列使得矩阵中所有数都为正数， 如果有奇数个负数，无论如何翻转最终仍会最少剩一个负数
    //        扩展至更大的矩阵，发现无论负数的位置是否相邻，依然满足上面的情况
    // 猜想： 矩阵相邻两元素能够无限次数翻转的情况下，矩阵内任意的两个负数均可翻转为正数而不影响其他元素的正负
    // 证明： a. 当两元素相邻时，自然可以直接翻转使二者变为正数
    //       b. 当两元素不相邻时，分别选择相互靠近的两个方向组成一对翻转，最终一定能够变为 2x2矩阵内的情况，而该情况前面已经观察到，偶数个负数一定能全部翻转为正数不影响其他位置

    // [法一]：数学推导后简化
    //         由于任意偶数个负数均可翻转为正数，因此只需要考虑最终矩阵中负数的个数是奇数还是偶数
    //         1. 若为偶数，则所有数均可翻转为正数，最终和即为所有数的绝对值之和
    //         2. 若为奇数，则最终矩阵中必有一个负数，且为了使得和最大化，应当选择绝对值最小的数作为负数，因此最终和为所有数的绝对值之和减去该最小绝对值的两倍
    public long maxMatrixSum(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        long sum = 0;
        int negativeCount = 0;
        int minAbsValue = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int value = matrix[i][j];
                if (value < 0) {
                    negativeCount++;
                }
                int absValue = Math.abs(value);
                sum += absValue;
                minAbsValue = Math.min(minAbsValue, absValue);
            }
        }

        if (negativeCount % 2 == 1) {
            sum -= 2L * minAbsValue;
        }

        return sum;
    }

    public static void main(String[] args) {
        int[][][] testMatrixes = {
            {{1,-1},{-1,1}},
            {{1,2,3},{-1,-2,-3},{1,2,3}},
            {{-1,-2,-3},{-4,5,6},{7,8,9}},
        };

        Solution s = new Solution();
        for (int i =0; i < testMatrixes.length; i++) {
            long result = s.maxMatrixSum(testMatrixes[i]);
            System.out.println("No." + (i + 1) + " test case: " + result);
        }
    }
}