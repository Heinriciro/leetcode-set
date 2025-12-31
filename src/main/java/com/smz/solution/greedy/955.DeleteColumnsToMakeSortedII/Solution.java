
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;



public class Solution {
    // [法一]：贪心思路
    //     题目要求删除列后，整个字符串数组是按字典序排序的，即排序后从左到右对比每一位字符，应是非严格递增的，如果字符相同，则按照后一位字符大小排序。对于当前列分析可得：
    //     1）如果这一列满足严格字典序，则不需要对后面的字符排序
    //     2）如果这一列出现不满足字典序的情况，必然要删去（因为既然已经枚举到这一列，说明前面的列也不满足严格字典序）
    //     3）如果这一列满足非严格字典序，说明其中有部分元素相等，这一列必然需要保留，因为：
    //         反证法：假设不保留这一列，则开始比较下一列的元素，分为几种情况：
    //             a.如果下一列元素符合严格字典序，那么保留上一列也符合总体要求，这样需要删除的列数更少。
    //             b.如果下一列元素符合非严格字典序，依然需要判断下一列元素的情况，那么就算这一列保留，也不影响结果，需要删除的列数还更少
    //             c.如果下一列元素完全不符合字典序，则必然要删除，那么这一列保留也不影响结果。
   public int minDeletionSize(String[] strs) {
        int n = strs.length;
        int len = strs[0].length();

        Deque<List<Integer>> sameElementsArea = new ArrayDeque<>();
        List<Integer> initList = new ArrayList<>();

        for (int i = 1; i < n; i++) {
            initList.add(i);
        }

        sameElementsArea.add(initList);

        int res = 0;
        for (int i = 0; i < len; i++) {
            List<Integer> lastList = sameElementsArea.pollLast();
            List<Integer> sameE = new ArrayList<>();
            for (int j : lastList) {
                if (strs[j].charAt(i) < strs[j-1].charAt(i)) {
                    res++;
                    sameE = lastList;
                    break;
                }
                if (strs[j].charAt(i) == strs[j-1].charAt(i)) {
                    sameE.add(j);
                }
            }

            if (!sameE.isEmpty()) {
                sameElementsArea.addLast(sameE);
            } else {
                break;
            }
        }

        return res;
    } 

    public static void main(String[] args) {
        String[][] testStrs = {
            // {"ca", "bb", "ac"},
            // {"xc", "yb", "za"},
            {"zyx", "wvu", "tsr"},
            {"xga","xfb","yfa"},
        };

        Solution s = new Solution();
        for (int i = 0; i < testStrs.length; i++) {
            System.out.println("No." + i + " test case: " + s.minDeletionSize(testStrs[i]));
        }
    }
}