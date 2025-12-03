import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class Solution {
    // 点(xi, yi)
    public int countTrapezoids(int[][] points) {
        Map<Point, List<Point>> slope2intercep = new HashMap<>();
        Map<Integer, List<Point>> mid2slope = new HashMap<>();

        int x1, y1, x2, y2, dx, dy, dxAbs, dyAbs, bNum, bNumAbs;
        for (int i = 0; i < points.length - 1; i++) {
            x1 = points[i][0];
            y1 = points[i][1];
            for (int j = i+1; j < points.length; j++) {
                x2 = points[j][0];
                y2 = points[j][1];
                dy = y2-y1;
                dx = x2-x1;
                dxAbs = Math.abs(dx);
                dyAbs = Math.abs(dy);

                // y = kx + b --> b = y - kx = y1 - dy/dx * x1 = (dx*y1 - dy*x1)/dx
                // 使用分子分母表示截距
                bNum = dx*y1 - dy*x1;
                bNumAbs = Math.abs(bNum);
                Point intercep;
                if (dx == 0) {
                    intercep = new Point(x1, Integer.MAX_VALUE);
                } else if (bNum == 0) {
                    intercep = new Point(0, 0);
                } else {
                    int gcd_factor = gcd(bNumAbs, dxAbs);

                    if ((float)bNum / (float)dx > 0.0) {
                        intercep = new Point(dxAbs / gcd_factor, bNumAbs / gcd_factor);
                    } else if ((float)bNum / (float)dx < 0.0) {
                        intercep = new Point(-dxAbs / gcd_factor, bNumAbs / gcd_factor);
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
                
                // 使用分子分母表示斜率
                Point slope;
                if (dx == 0) {
                    slope = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
                } else if (dy == 0) {
                    slope = new Point(0, 0);
                } else {
                    int gcd_factor = gcd(dyAbs, dxAbs);

                    if ((float)dy / (float)dx > 0.0) {
                        slope = new Point(dxAbs / gcd_factor, dyAbs / gcd_factor);
                    } else if ((float)dy / (float)dx < 0.0) {
                        slope = new Point(-dxAbs / gcd_factor, dyAbs / gcd_factor);
                    } else {
                        throw new IllegalArgumentException();
                    }
                }

                slope2intercep.computeIfAbsent(slope, _ -> new ArrayList<Point>()).add(intercep);
                int mid = (x1 + x2 + 2000) * 10000 + (y1 + y2 + 2000); // 将坐标表示为一维的数字，由于点的坐标值在-1000~1000间，加2000使其必定为正从而方便映射
                mid2slope.computeIfAbsent(mid, _ -> new ArrayList<Point>()).add(slope);
            }
        }

        // 计算不共线的平行线段能组成的所有四边形数量
        int res = 0;
        for (List<Point> interceps : slope2intercep.values()) {
            if (interceps.size() == 1) continue; // 同一斜率下，不共线线段只有一个，无法组成梯形
            
            HashMap<Point, Integer> cnt = new HashMap<>();
            for (Point b : interceps) {
                cnt.merge(b, 1, Integer::sum);
            }

            int curSum = 0;
            for (int count : cnt.values()) {
                res += curSum * count;
                curSum += count;
            }
        }

        // 由于存在平行四边形，之前按一对平行线组合计算的梯形数量，可能多算了一次平行四边形，故减去
        // 与之前的计算方法完全一样，只是物理意义不同。此时，通过计算不同斜率但中点不同的一对线段来算出最终有多少个平行四边形
        // 之前是通过计算斜率相同但截距不同的一对线段来计算一共有多少个广义梯形
        int repeat = 0;
        for (List<Point> slopes : mid2slope.values()) {
            if (slopes.size() == 1) continue; //相同中点若是仅有一种斜率的线段，则无法组成平行四边形

            HashMap<Point, Integer> cnt = new HashMap<>();
            for (Point k : slopes) {
                cnt.merge(k, 1, Integer::sum);
            }

            int curSum = 0;
            for (int count : cnt.values()) {
                repeat += curSum * count;
                curSum += count;
            }
        }

        return res - repeat;

    }


    public int gcd(int a, int b) {
        return b==0 ? a : gcd(b, a%b);
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[][][] points = {
            {{-3, 2}, {3, 0}, {2, 3}, {3, 2}, {2, -3}},
            {{0, 0}, {1, 0}, {0, 1}, {2, 1}}
        };

        int i = 0;
        for (int[][] point : points) {
            System.out.println("No."+ i++ + " case: " + s.countTrapezoids(point));
        }
    }
}