import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


public class Solution {
    // [法一]：DFS
    public boolean pyramidTransition(String bottom, List<String> allowed) {
        // 由于模式中的字母是有限的，即ABCDEF
        // 使用数组存储合法组合的三个字母，便于查找遍历
        @SuppressWarnings("unchecked")
        List<Character>[][] allowedMap = new ArrayList[6][6];
        for (List<Character>[] row : allowedMap) {
            Arrays.setAll(row, _->new ArrayList<>());
        }
        for (String p : allowed) {
            allowedMap[p.charAt(0) - 'A'][p.charAt(1) - 'A'].add(p.charAt(2));
        }


        int n = bottom.length(); // 最底层有n个数字，说明金字塔一共有n层
        char[][] pyramid = new char[n][];
        for (int i = 0; i < n-1; i++) {
            pyramid[i] = new char[i+1];
        }
        pyramid[n-1] = bottom.toCharArray();

        return dfs(n-2, 0, pyramid, allowedMap);
    }

    // 对金字塔每一个block求解，（i,j）即第i层第j个, 且由于是金字塔，有j <= i
    private boolean dfs(int i, int j, char[][] pyramid, List<Character>[][] patterns) {
        if (i < 0) return true;

        if (j == i + 1) return dfs(i-1, 0, pyramid, patterns); // 说明此行排列完毕，需要排列上一行

        for (char c : patterns[pyramid[i+1][j] - 'A'][pyramid[i+1][j+1] - 'A']) { // 当前(i,j)处block，取决于下一层的(i-1,j)(i-1,j+1)两个block，从pattern中寻找合法值
            pyramid[i][j] = c;
            if (dfs(i, j+1, pyramid, patterns)) return true; // 当前block确定后，求解同一行的下一个block(i,j+1)，若求解成功，则该方案可行
        }

        return false; // 若当前block的所有可能取值均无解，则返回false
    }

    public static void main(String[] args) {
        String[] testBottoms = {
            "BCD",
            "AAAA"
        };
        List<List<String>> testAlloweds = Stream.of(
            Arrays.asList("BCC","CDE","CEA","FFF"),
            Arrays.asList("AAB","AAC","BCD","BBE","DEF")
        ).toList();

        Solution s = new Solution();
        for (int i = 0; i < testBottoms.length; i++) {
            System.out.println("No."+i+" test case: "+s.pyramidTransition(testBottoms[i], testAlloweds.get(i)));
        }
    }

}