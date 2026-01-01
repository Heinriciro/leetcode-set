import java.util.Arrays;

public class Solution {
    public int[] plusOne(int[] digits) {
        boolean morebit = addOnePerDigit(digits, 0);
        if (morebit) {
            int[] newDigits = new int[digits.length + 1];
            newDigits[0] = 1;
            System.arraycopy(digits, 0, newDigits, 1, digits.length);
            return newDigits;
        } else {
            return digits;
        }
    }

    private boolean addOnePerDigit(int[] digits, int index) {
        if (index == digits.length - 1) {
            if (digits[index] < 9) {
                digits[index]++;
                return false;
            } else {
                digits[index] = 0;
                return true;
            }
        } else {
            if (addOnePerDigit(digits, index + 1)) {
                if (digits[index] < 9) {
                    digits[index]++;
                    return false;
                } else {
                    digits[index] = 0;
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    public static void main(String[] args) {
        int[][] testDigits = {
            {1,2,3},
            {4,3,2,1},
            {9},
            {9,9,9},
        };

        Solution s = new Solution();
        for (int i = 0; i < testDigits.length; i++) {
            System.out.println("No."+i+" test case:"+ Arrays.toString(s.plusOne(testDigits[i])));
        }
    }
}