package Hiho.MS2017;

import java.util.ArrayList;
import java.util.Scanner;

public class TreeRestoration {
    static int[][] dist;
    static int[] depthN;
    static int[][] nodes;
    static int[] leaves, leavesMap;

    static int nextNonLeaf(int level, int cur) {
        for (int i = cur; i < depthN[level]; ++i)
            if (leavesMap[nodes[level][i]] == 0) return i;
        return -1;
    }

    public static void main(String[] argv) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt()) {
            int N = scanner.nextInt(), M = scanner.nextInt(), K = scanner.nextInt();
            if (N == 0) continue;
            dist = new int[N + 1][N + 1];
            for (int i = 0; i <= N; ++i)
                for (int j = 0; j <= N; ++j)
                    if (i != j)
                        dist[i][j] = -1;
            scanner.nextLine();
            String line = scanner.nextLine();
            String s[] = line.split(" ");
            depthN = new int[s.length];
            for (int i = 0; i < s.length; ++i)
                depthN[i] = Integer.valueOf(s[i]);
            nodes = new int[depthN.length][];
            for (int i = 0; i < depthN.length; ++i) {
                nodes[i] = new int[depthN[i]];
                for (int j = 0; j < depthN[i]; ++j)
                    nodes[i][j] = scanner.nextInt();
            }
            scanner.nextLine();
            s = scanner.nextLine().split(" ");
            leaves = new int[s.length];
            leavesMap = new int[N + 1];
            for (int i = 0; i < s.length; ++i) {
                leaves[i] = Integer.valueOf(s[i]);
                leavesMap[leaves[i]] = 1;
            }
            for (int i = 0; i < s.length; ++i)
                for (int j = 0; j < s.length; ++j)
                    dist[leaves[i]][leaves[j]] = dist[leaves[j]][leaves[i]] = scanner.nextInt();
            int par[] = new int[N + 1];
            for (int level = depthN.length - 1; level > 0; --level) {
                int i = 0, j = 0;
                while (i < depthN[level]) {
                    int k = i + 1;
                    while (k < depthN[level] && dist[nodes[level][i]][nodes[level][k]] == 2) k++;
                    j = nextNonLeaf(level - 1, j);
                    if (j == -1) {
                        throw new RuntimeException("cannot find parents?");
                    }
                    for (int p = i; p < k; ++p)
                        par[nodes[level][p]] = nodes[level - 1][j];
                    for (int l : leaves) {    //update dist to leaves
                        if(l!=nodes[level][i])
                        dist[l][nodes[level - 1][j]] = dist[nodes[level - 1][j]][l] = dist[nodes[level][i]][l] - 1;
                    }
                    i = k;
                    j = j + 1;
                }
                for (i = 0; i < depthN[level]; ++i) {       //update dist to non leaves
                    for (j = i + 1; j < depthN[level]; ++j)
                        dist[par[nodes[level][i]]][par[nodes[level][j]]] = dist[par[nodes[level][j]]][par[nodes[level][i]]] = dist[nodes[level][i]][nodes[level][j]] - 2;
                }
            }
            for (int i = 1; i < N + 1; ++i)
                System.out.print("" + par[i] + ' ');
            System.out.println();
        }
    }
}
