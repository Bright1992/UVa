import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class p10305 {
    static class Node{
        int in=0,no;
        ArrayList<Edge> arcs = new ArrayList<>();
        Node(int no){
            this.no=no;
        }
    }
    static class Edge{
        int from,to;
        public Edge(int f,int t){
            from=f;to=t;
        }
    }
    public static void main(String[] argv){
        Scanner scanner = new Scanner(System.in);
        while(true) {
            int n = scanner.nextInt(), m = scanner.nextInt();
            if (n == 0 && m == 0) return;
            Node[] graph = new Node[n+1];
            for(int i=1;i<=n;++i)    graph[i]=new Node(i);
            while(m-->0){
                int f=scanner.nextInt(), t=scanner.nextInt();
                graph[f].arcs.add(new Edge(f,t));
                graph[t].in++;
            }
            LinkedList<Node> q = new LinkedList<>();
            for(int i=1;i<=n;++i)
                if(graph[i].in==0) q.addLast(graph[i]);
            int cnt=0;
            while(!q.isEmpty()){
                Node N=q.pollFirst();
                N.in--;
                if(++cnt>1)
                    System.out.print(" ");
                System.out.print(N.no);
                for(Edge e:N.arcs){
                    if(--graph[e.to].in==0)
                        q.addLast(graph[e.to]);
                }
            }
            System.out.println();
        }
    }
}
