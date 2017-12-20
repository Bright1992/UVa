import java.util.Scanner;

public class p10129 {
    static int dfs(int[][] graph, int u, int[] mark) {
        int ret = 1;
        mark[u] = 1;
        for (int i = 0; i < 26; ++i) {
            if (graph[u][i] > 0 && mark[i] == 0)
                ret += dfs(graph, i, mark);
        }
        return ret;
    }

    public static void main(String[] argv) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        while (N-- > 0) {
            int n = scanner.nextInt();
            scanner.nextLine();
            int graph[][] = new int[26][26];
            while (n-- > 0) {
                String line = scanner.nextLine();
//                graph[line.charAt(line.length() - 1) - 'a'][line.charAt(0) - 'a']++;
                graph[line.charAt(0) - 'a'][line.charAt(line.length() - 1) - 'a']++;
            }
            int nodes = 0, odds = 0, first = -1;
            for (int i = 0; i < 26; ++i) {
                int cnt1 = 0, cnt2 = 0;
                for (int j = 0; j < 26; ++j) {
                    cnt1 += graph[i][j];
                    cnt2 += graph[j][i];
                }
                if (Math.abs(cnt1 - cnt2) == 1) odds++;
                if (Math.abs(cnt1 - cnt2) > 1 || odds > 2) {
                    odds = 3; //mark
                    break;
                }
                if (cnt1 > 0 && first == -1)
                    first = i;
                if(cnt1>0)  nodes++;
                if (cnt1 > cnt2) {
                    first = i;
                }
            }
            int mark[] = new int[26];
            if (odds > 2 || first < 0 || dfs(graph, first, mark) < nodes)
                System.out.println("The door cannot be opened.");
            else
                System.out.println("Ordering is possible.");
        }
    }
}
