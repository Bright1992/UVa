package Hiho;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class w178_StableMembers {
    static class Node{
        Set<Integer> critical = new HashSet<>();
        ArrayList<Edge> edges = new ArrayList<>();
    }
    static class Edge{
        int src,dst;
        public Edge(int src, int dst){
            this.src=src;this.dst=dst;
        }
    }

    static Node[] graph;
    static int [] topo;
    static int [] vis;
    static int cur;

    static void topo_sort(){
        topo = new int[graph.length];
        vis = new int[graph.length];
        cur=graph.length-1;
        for(int i=0;i<graph.length;++i){
            dfs(i);
        }
    }

    static void dfs(int node){
        if(vis[node]==0){
            vis[node]=1;
            for(Edge e : graph[node].edges){
                dfs(e.dst);
            }
            topo[cur--]=node;
        }
    }

    public static void main(String[] argv){
        Scanner scanner = new Scanner(System.in);
        int N=scanner.nextInt();
        graph = new Node[N+1];
        for(int i=0;i<N+1;++i)
            graph[i]=new Node();
        for(int i=1;i<=N;++i){
            int n=scanner.nextInt();
            for(int j=0;j<n;++j)
                graph[i].edges.add(new Edge(i,scanner.nextInt()));
        }
        topo_sort();
        int stable=0;
        for(int i=N-1;i>=0;--i){    //reverse topo order, skip master
            Node tmp = graph[topo[i]];
            tmp.critical=new HashSet<>(graph[tmp.edges.get(0).dst].critical);
            int cnt=0;
            for(Edge e:tmp.edges){
                if(++cnt==1)    continue;
                tmp.critical.retainAll(graph[e.dst].critical);
                if(tmp.critical.isEmpty())
                    break;
            }
            if(tmp.critical.isEmpty())
                stable++;
            tmp.critical.add(topo[i]);
        }
        System.out.println(stable);
    }

}
