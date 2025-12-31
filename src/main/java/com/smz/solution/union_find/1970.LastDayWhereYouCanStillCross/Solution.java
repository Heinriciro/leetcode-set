public class Solution {
    private static final int[][] DIRS = {{0,-1},{0,1},{-1,0},{1,0}};

    // [法一]：并查集 + 时光倒流
    public int latestDayToCross(int row, int col, int[][] cells) {
        // 为每一个矩阵格子编号，创建并查集，共row*col个，同时添加两个超节点用于表示最上层和最下层
        UnionFind uf = new UnionFind(row * col + 2);
        boolean[][] isLand = new boolean[row][col];

        int top = row * col;
        int btm = row * col + 1;

        for (int day = cells.length - 1; day >= 0; day--) {
            int x = cells[day][0] - 1;
            int y = cells[day][1] - 1;
            int idx = x * col + y;
            isLand[x][y] = true;

            if (x == 0) uf.merge(idx, top);
            if (x == row - 1) uf.merge(idx, btm);

            for (int[] dir : DIRS) {
                int r = x + dir[0];
                int c = y + dir[1];

                if (r >= 0 && r < row && c >= 0 && c < col && isLand[r][c]) {
                    uf.merge(idx, r * col + c);
                }
            }

            if (uf.connected(top, btm)) return day;
        }

        return 0;
    }

    public static void main(String[] args) {
        int[] testRows = {2,2,3};
        int[] testCols = {2,2,3};
        int[][][] testCells = {
            {{1,1},{2,1},{1,2},{2,2}},
            {{1,1},{1,2},{2,1},{2,2}},
            {{1,2},{2,1},{3,3},{2,2},{1,1},{1,3},{2,3},{3,2},{3,1}},
        };

        Solution s = new Solution();
        for (int i = 0; i < testRows.length; i++) {
            System.out.println("No."+i+" test case: "+s.latestDayToCross(testRows[i], testCols[i], testCells[i]));
        }
    }

    public class UnionFind {
        int[] parent;
        public UnionFind(int n) {
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }

            return parent[x];
        }

        public void merge(int from, int to) {
            int x = find(from);
            int y = find(to);
            if (x != y) {
                parent[x] = y;
            }
        }

        public boolean connected(int x, int y) {
            return find(x) == find(y);
        }
    }
}