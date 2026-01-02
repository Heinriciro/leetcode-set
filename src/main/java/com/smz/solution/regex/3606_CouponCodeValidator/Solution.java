
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Solution {
    // [法一]：正则 + 排序
    public List<String> validateCoupons(String[] code, String[] businessLine, boolean[] isActive) {
        Pattern codePattern = Pattern.compile("^[\\w]+$");

        List<Integer> validCodes = new ArrayList<>();
        for (int i = 0; i < code.length; i++) {
            if (isActive[i] && codePattern.matcher(code[i]).matches() && businessLine[i] != null && !businessLine[i].isEmpty()) {
                switch (businessLine[i]) {
                    case "electronics":
                    case "grocery":
                    case "pharmacy":
                    case "restaurant":
                        validCodes.add(i);
                    default:
                        break;
                }
            }
        }

        validCodes.sort((a, b) -> {
            if (businessLine[a].equals(businessLine[b])) {
                return code[a].compareTo(code[b]);
            } else {
                return businessLine[a].compareTo(businessLine[b]);
            }    
        });
        List<String> result = new ArrayList<>();
        for (int index : validCodes) {
            result.add(code[index]);
        }

        return result;
    }

    public static void main(String[] args) {
        String[][] testCode = {
            {"SAVE20", "", "PHARMA5", "SAVE@20"},
            {"GROCERY15", "ELECTRONICS_50", "DISCOUNT10"},
        };
        String[][] testBusinessLine = {
            {"restaurant", "grocery", "pharmacy", "restaurant"},
            {"grocery","electronics","invalid"},
        };
        boolean[][] testIsActive = {
            {true, true, true, true},
            {false, true, true},
        };

        Solution s = new Solution();
        for (int i = 0; i < testCode.length; i++) {
            List<String> validatedCoupons = s.validateCoupons(testCode[i], testBusinessLine[i], testIsActive[i]);
            System.out.println("No." +i+ " test case: " + validatedCoupons);
        }
    }
}