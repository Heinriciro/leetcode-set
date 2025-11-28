import java.util.ArrayList;
import java.util.List;

// 树是一个无向无环连通图 --> 切断任意一条边都会形成两个子树
// 整个树的节点值和S可被k整除 --> 两条子树，若其中一个A能被k整除，则由(S-A)%k = S%k - A%k = 0 --> 另一个子树B也能被k整除
// 深度优先搜索遍历节点，对于当前节点 --> 若以它为根节点的这一子树能够被k整除，那切断当前节点和父节点之间的边所形成的两个部分都可以被k整除，因而合法，计数+1
// 随后以当前节点为根的子树，又是一个无向无环连通图，且节点值和可被k整除 --> 可以继续递归处理

public class Solution {
    private List<Integer>[] graph;
    private int[] values;
    private int k;
    private int count;

    public int maxKDivisibleComponents(int n, int[][] edges, int[] values, int k) {
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            graph[u].add(v);
            graph[v].add(u);
        }

        this.graph = graph;
        this.values = values;
        this.k = k;
        this.count = 0;

        dfs(0, -1);
        return count;
    }

    private long dfs(int cur, int parent) {
        long sum = values[cur];
        for (int neighbor : graph[cur]) {
            if (neighbor != parent) {
                sum += dfs(neighbor, cur);
            }
        }
        if (sum % k == 0) {
            count++;
        }

        return sum;
    }

}
