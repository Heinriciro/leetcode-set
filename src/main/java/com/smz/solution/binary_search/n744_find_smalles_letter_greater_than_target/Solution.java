package com.smz.solution.binary_search.n744_find_smalles_letter_greater_than_target;
import java.util.Arrays;

public class Solution {
    // 由于数据量为10^4, 且数组已经是非递减，所以可以直接暴力枚举。当然，显然也可以使用二分查找。
    
    // [法二]：二分查找
    //   手写二分查找，直接查找大于target的最近字母位置
    public char nextGreatestLetter(char[] letters, char target) {
        int lo = 0, hi = letters.length - 1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            if (letters[mid] <= target) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return lo == letters.length ? letters[0] : letters[lo];
    }

    // [法一]：二分查找
    //    首先可以使用自带查找，由于target有可能重复，在找到之后再做一次判断即可
    public char nextGreatestLetter1(char[] letters, char target) {
        int idx = Arrays.binarySearch(letters, target);
        if (idx < 0) { // 说明数组中没有target这个字母
            idx = -idx - 1; // 得到插入位置，插入位置即为大于target的最小字母位置
        } else {
            while (idx < letters.length && letters[idx] == target) {
                idx++;
            }
        }

        return idx == letters.length ? letters[0] : letters[idx];
    }

    public static void main(String[] args) {
        char[][] testLetters = {
            {'c','f','j'},
            {'c','f','j'},
            {'x','x','y','y'},
        };
        char[] testTargets = {
            'a',
            'c',
            'z',
        };

        Solution sol = new Solution();
        for (int i = 0; i < testLetters.length; i++) {
            System.out.println("No."+i+" test case: "+sol.nextGreatestLetter(testLetters[i], testTargets[i]));
        }
    }

}