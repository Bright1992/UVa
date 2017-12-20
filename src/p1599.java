import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class p1599 {
    static final int MAX_COLOR=1000000001;
    static class Node{
        int level=-1;
        ArrayList<Edge> edges=new ArrayList<>();
        int next=-1;
        int color=MAX_COLOR;
    }
    static class Edge{
        int src,dst,color;
        public Edge(int src, int dst, int color){
            this.src=src;this.dst=dst;this.color=color;
        }
    }

    static Node[] graph;

    static int bfs(int dst){
        LinkedList<Integer> q = new LinkedList<>();
        graph[dst].level=0;
        q.addLast(dst);
        while(!q.isEmpty()){
            int node=q.pollFirst();
            if(node==1) return graph[node].level;
            for(Edge e:graph[node].edges){
                if(graph[e.dst].level==-1)
                    graph[e.dst].level=graph[node].level+1;
                if(graph[e.dst].level==graph[node].level+1) {
                    if(graph[e.dst].color==MAX_COLOR)
                        q.addLast(e.dst);
                    if(graph[e.dst].color>e.color){
                        graph[e.dst].color=e.color;
                        graph[e.dst].next=node;
                    }
                }
            }
        }
        return -1;
    }

    public static void main(String[] argv){
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextInt()){
            int n=scanner.nextInt(), m=scanner.nextInt();
            graph = new Node[n+1];
            for(int i=1;i<n+1;++i)
                graph[i] = new Node();
            while(m-->0){
                int a=scanner.nextInt(), b=scanner.nextInt(), c= scanner.nextInt();
                graph[a].edges.add(new Edge(a,b,c));
                graph[b].edges.add(new Edge(b,a,c));
            }
            int dis=bfs(n);
            System.out.println(dis);
            for(int i=1;i!=n;i=graph[i].next){
                if(i!=1)    System.out.print(" ");
                System.out.print(graph[i].color);
            }
            System.out.println();
        }
    }
}
