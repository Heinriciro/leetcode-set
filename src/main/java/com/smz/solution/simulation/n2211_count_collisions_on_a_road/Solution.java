class Solution {
    public int countCollisions(String directions) {
        char[] dirL2R = directions.toCharArray();
        int resL2R = 0;

        // 当前车辆向右，下一辆车也向右时，无法立即判断是否相撞，故记录连续向右的车辆数量，当最终碰撞时一并计算
        int adajcentR = 0;

        for (int i = 0; i < dirL2R.length; i++) {
            char curStatus = dirL2R[i];
            if (curStatus == 'S') continue;
            if (curStatus == 'L') {
                if (i - 1 >= 0 && (dirL2R[i - 1] == 'R' || dirL2R[i - 1] == 'S')) {
                    resL2R += 1;
                    dirL2R[i] = 'S';
                }
            } else if (curStatus == 'R') {
                if (i + 1 < dirL2R.length && (dirL2R[i + 1] == 'L' || dirL2R[i + 1] == 'S')) {
                    resL2R += 1 + adajcentR;
                    adajcentR = 0;
                    dirL2R[i] = 'S';
                }

                if (i + 1 < dirL2R.length && dirL2R[i + 1] == 'R') {
                    adajcentR += 1;
                }
            }
        }

        return resL2R;
    }

    public static void main(String[] args) {
        String[] directions = {
            "RLRSLL",
            "LLRR",
            "SSRSSRLLRSLLRSRSSRLRRRRLLRRLSSRR"
        };

        Solution s = new Solution();
        int i = 0;
        for (String dir: directions) {
            System.out.println("No."+ i++ + " result: " + s.countCollisions(dir));
        }
    }
}