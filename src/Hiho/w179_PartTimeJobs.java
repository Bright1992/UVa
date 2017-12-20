package Hiho;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class w179_PartTimeJobs {
    static final int MAX_POSITIVE = 0x7FFFFFFF;

    static class Quest {
        int L, S, E, T, C;

        public Quest(int l, int s, int e, int t, int c) {
            L = l;
            S = s;
            E = e;
            T = t;
            C = c;
        }
    }

    static class Node {
        ArrayList<Edge> edges = new ArrayList<>();
        ArrayList<Integer> quests = new ArrayList<>();
    }

    static class Edge {
        int from, to;
        int cost;

        public Edge(int f, int t, int c) {
            from = f;
            to = t;
            cost = c;
        }
    }

    static Quest[] quests;
    static Node[] graph;
    static int[][] questDist;
    static int[][] dp;

    static void relax(LinkedList<Integer> q, Edge e, int[] distTo) {
        if (distTo[e.to] > distTo[e.from] + e.cost) {
            q.addLast(e.to);
            distTo[e.to] = distTo[e.from] + e.cost;
        }
    }

    static void SPFA(int src) {
        LinkedList<Integer> q = new LinkedList<>();
        q.addLast(src);
        int[] distTo = new int[graph.length];
        for (int i = 0; i < distTo.length; ++i)
            distTo[i] = MAX_POSITIVE;
        distTo[src] = 0;
        int cnt = 0;
        while (!q.isEmpty()) {
            int idx = q.pollFirst();
            for (Edge e : graph[idx].edges) {
                relax(q, e, distTo);
            }
            cnt++;
            if (cnt > graph.length * graph.length) {
                System.err.println("Negative Cycle??");
                throw new RuntimeException();
            }
        }
        for (int qi : graph[src].quests)
            for (int qj = 0; qj < quests.length; ++qj)
                questDist[qi][qj] = distTo[quests[qj].L];
        if (src == 1)
            for (int qi = 0; qi < quests.length; ++qi)
                questDist[quests.length][qi] = distTo[quests[qi].L];
    }


    public static void main(String[] argv) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt()) {
            int N = scanner.nextInt(), M = scanner.nextInt(), Q = scanner.nextInt();
            quests = new Quest[Q];
            graph = new Node[N + 1];
            questDist = new int[Q + 1][Q + 1];
            for (int i = 1; i < N + 1; ++i)
                graph[i] = new Node();
            for (int i = 0; i < M; ++i) {
                Edge e = new Edge(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
                graph[e.from].edges.add(e);
            }
            for (int i = 0; i < Q; ++i) {
                quests[i] = new Quest(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
                graph[quests[i].L].quests.add(i);
            }
            for (Quest q : quests)
                SPFA(q.L);
            SPFA(1);
            dp = new int[1 << Q][Q + 1];
            for (int i = 0; i <= Q; ++i) {
                for (int j = 1; j < (1 << Q); ++j) {
                    if (i < Q)
                        dp[j][i] = MAX_POSITIVE;
                    else
                        dp[j][i] = 0;
                }
            }
            int maxc = 0, maxs = 0;
            for (int i = 0; i < Q; ++i) {
                if (questDist[Q][i] <= quests[i].E) {
                    int c = dp[1 << i][Q] = quests[i].C;
                    if (maxc < c) {
                        maxc = c;
                        maxs = 1 << i;
                    }
                    dp[1 << i][i] = quests[i].T + Math.max(questDist[Q][i], quests[i].S);
                }
            }
            for (int i = 1; i < (1 << Q); ++i) {
                for (int j = 0; j < Q; ++j) {
                    if ((i & (1 << j)) == 0) {
                        int minw = MAX_POSITIVE;
                        for (int k = 0; k < Q; ++k) {
                            if ((i & (1 << k)) != 0 && dp[i][k] < MAX_POSITIVE && dp[i][k] + questDist[k][j] <= quests[j].E) {
                                if (minw > questDist[k][j] + dp[i][k])
                                    minw = questDist[k][j] + dp[i][k];
                            }
                        }
                        if (minw == MAX_POSITIVE) continue;
                        int c = dp[i][Q] + quests[j].C;
                        if (maxc < c) {
                            maxc = c;
                            maxs = i | (1 << j);
                        }
                        int t = Math.max(quests[j].S, minw) + quests[j].T;
                        if (t < dp[i | (1 << j)][j]) {
                            dp[i | (1 << j)][Q] = c;
                            dp[i | (1 << j)][j] = t;
                        }
                        if (t == dp[i | (1 << j)][j])
                            if(c>dp[i|(1<<j)][Q])
                                dp[i|(1<<j)][Q]=c;

                    }
                }
            }
            System.out.println(maxc);
        }
    }
}
