import java.util.ArrayList;
import java.util.List;

public class Solution {
    private List<Integer>[] h;
    private int[] present;
    private int[] future;
    private int n;

    public int maxProfit(int n, int[] present, int[] future, int[][] hierarchy, int budget) {
        ArrayList<Integer>[] h = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            h[i] = new ArrayList<>();
        }

        for (int[] relation : hierarchy) {
            h[relation[0]-1].add(relation[1]-1);
        }
        this.present = present;
        this.future = future;
        this.n = n;
        this.h = h;

        return dfs(0, budget)[0][budget];
        
    }

    public int[][] dfs(int root, int budget) {
        // 计算以`root`为根节点的子树, 在预算为budget下的最大利润
        // dp(root, status, budget)， status表明该root节点的父节点是否购买 -> root节点是否应用折扣


        // status = 0，root不购买->  各个子树的根节点不享受折扣
        // status = 1, root购买-> 各子树根节点享受折扣
        int[][] allSubsProfit = new int[2][budget+1];
        // 开始动态规划，依次枚举子树，更新所有子树综合下来的最大利润
        // 状态转移公式:
        //     f[i][budget] = Math.max(f[i][budget], f[i-1][budget-b] + s[i][b])
        for (int sub : h[root]) {
            // 子问题：计算每一个以子节点为根节点的子树在对应预算下的最大利润
            int[][] singleSubProfit = dfs(sub, budget);
            // 从0到目标预算`budget`中，依次计算更新预算为`b`时所有子树的综合最大利润
            for (int b = budget; b >= 0; b--) {
                // 注意这里必须为正序，因为否则当i逆序从b取值到0时，此时`allSubsProfit[status][b]`值为`Math.max(allSubsProfit[status][b], allSubsProfit[status][b] + singleSubProfit[status][0])`
                // 此时的`allSubsProfit[status][b]`已经被更新，而由于折扣的存在导致股票购入价格可以是0 (= floor(1/0)), 即singleSubProfit[status][0]非0，所以此时进行原地更新并不符合状态转移公式
                for (int i = 0; i <= b; i++) {
                    // 假如当前root节点不购买，sub子树使用status=0的数据
                    for (int status = 0; status < 2; status++) {
                        allSubsProfit[status][b] = Math.max(allSubsProfit[status][b], allSubsProfit[status][b-i] + singleSubProfit[status][i]);
                    }
                }
            }
        }

        // 考虑root的父节点是否购买->即root节点是否享受优惠的两种情况，更新最终的结果
        int[][] dp = new int[2][budget + 1];
        for (int b = 0; b <= budget; b++) {
            dp[1][b] = allSubsProfit[0][b];
            dp[0][b] = allSubsProfit[0][b];
            int cost = present[root];
            int dCost = present[root]/2;
            if (b >= dCost) dp[1][b] = Math.max(dp[1][b], allSubsProfit[1][b-dCost] + future[root] - dCost);
            if (b >= cost) dp[0][b] = Math.max(dp[0][b], allSubsProfit[1][b-cost] + future[root] - cost);
            
        }
        return dp;
    }
    // private HashMap<Integer, List<Integer>> hierarchyMap;
    // private int[] present;
    // private int[] future;

    // public int maxProfit(int n, int[] present, int[] future, int[][] hierarchy, int budget) {
    //     HashMap<Integer, List<Integer>> map = new HashMap<>();
    //     for (int[] h : hierarchy) {
    //         map.computeIfAbsent(h[0], _-> new ArrayList<>()).add(h[1]);
    //     }
    //     this.hierarchyMap = map;
    //     this.present = present;
    //     this.future = future;

    //    return getMaxProfitFromNode(1, budget, false); 
    // }

    // private int getMaxProfitFromNode(int root, int budget, boolean discounted) {
    //     int cost = discounted? present[root-1]/2 : present[root-1];
    //     int sumWhenRootBuy = future[root - 1] - cost;
    //     int sumWhenRootNotBuy = 0;
    //     if (!hierarchyMap.containsKey(root)) {
    //         if (budget >= cost) return Math.max(sumWhenRootNotBuy, sumWhenRootBuy);
    //         return sumWhenRootNotBuy;
    //     }

    //     for (Integer subs : hierarchyMap.get(root)) {
    //         sumWhenRootNotBuy += getMaxProfitFromNode(subs, budget, false);
    //         if (budget >= cost)
    //         sumWhenRootBuy += getMaxProfitFromNode(subs, budget - cost, true);
    //     }

    //     return Math.max(sumWhenRootNotBuy, sumWhenRootBuy);
    // }


    public static void main(String[] args) {
        int[] testN = {2,2,3,3};
        int[][] testPresents = {
            {1,2},
            {3,4},
            {4,6,8},
            {5,2,3}
        };
        int[][] testFutures = {
            {4,3},
            {5,8},
            {7,9,11},
            {8,5,6}
        };
        int[][][] testHierarchies = {
            {{1,2}},
            {{1,2}},
            {{1,2},{1,3}},
            {{1,2},{2,3}}
        };
        int[] testBudgets = {3,4,10,7};

        Solution s = new Solution();
        for (int i = 0; i < testN.length; i++) {
            System.out.println("No."+i+" test case: "+s.maxProfit(testN[i], testPresents[i], testFutures[i], testHierarchies[i], testBudgets[i]));
        }
    }

}